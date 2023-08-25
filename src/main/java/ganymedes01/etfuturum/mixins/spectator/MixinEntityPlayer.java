package ganymedes01.etfuturum.mixins.spectator;

import ganymedes01.etfuturum.spectator.SpectatorMode;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {

	@Shadow
	public InventoryPlayer inventory;

	public MixinEntityPlayer(World p_i1595_1_) {
		super(p_i1595_1_);
	}

	@Override
	public boolean canBePushed() {
		return !SpectatorMode.isSpectator((EntityPlayer) (Object) this);
	}

	@Override
	public boolean canBeCollidedWith() {
		return !SpectatorMode.isSpectator((EntityPlayer)(Object)this);
	}

	@Override
	protected void collideWithNearbyEntities() {
		if(!SpectatorMode.isSpectator((EntityPlayer)(Object)this))
			super.collideWithNearbyEntities();
	}

	@Override
	public boolean isOnLadder() {
		if(SpectatorMode.isSpectator((EntityPlayer)(Object)this))
			return false;
		return super.isOnLadder();
	}

	@Override
	public boolean handleWaterMovement() {
		if (SpectatorMode.isSpectator((EntityPlayer) (Object) this))
			return false;
		return super.handleWaterMovement();
	}

	public ItemStack getEquipmentInSlot(int p_71124_1_) {
		return SpectatorMode.isSpectator((EntityPlayer) (Object) this) ? null : p_71124_1_ == 0 ? this.inventory.getCurrentItem() : this.inventory.armorInventory[p_71124_1_ - 1];
	}

	@Inject(method = "isCurrentToolAdventureModeExempt", at = @At(value = "HEAD"), cancellable = true)
	public void isSpectating(int p_82246_1_, int p_82246_2_, int p_82246_3_, CallbackInfoReturnable<Boolean> cir) {
		if (SpectatorMode.isSpectator((EntityPlayer) (Object) this)) {
			cir.setReturnValue(false);
		}
	}
}
