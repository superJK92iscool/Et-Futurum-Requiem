package ganymedes01.etfuturum.mixins.spectator;

import ganymedes01.etfuturum.spectator.SpectatorMode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContainerChest.class)
public abstract class MixinContainerChest extends Container {

//  @Inject(method = "<init>", at = @At(value = "HEAD"))
//  private void capturePlayerInventory(IInventory p_i1806_1_, IInventory p_i1806_2_, CallbackInfo ci) {
//  }

	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/IInventory;openInventory()V"))
	//To capture the constructor params it requires me to have both IInventory args in the signature. chestInv == chestInvAgain
	private void ignoreOpenInventoryInSpectator(IInventory chestInv, IInventory playerInv, IInventory chestInvAgain) {
		if (!(playerInv instanceof InventoryPlayer) || !SpectatorMode.isSpectator(((InventoryPlayer) playerInv).player)) {
			chestInv.openInventory();
		}
	}

	@Inject(method = "onContainerClosed", at = @At(value = "HEAD"), cancellable = true)
	private void ignoreCloseInventoryInSpectator(EntityPlayer p_75134_1_, CallbackInfo ci) {
		if (SpectatorMode.isSpectator(p_75134_1_)) {
			ci.cancel();
		}
	}
}
