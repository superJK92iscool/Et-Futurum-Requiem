package ganymedes01.etfuturum.mixins.sounds;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFishingRod.class)
public class MixinItemFishingRod extends Item {

	@Inject(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/EntityFishHook;func_146034_e()I"))
	private void playReelSound(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_, CallbackInfoReturnable<ItemStack> cir) {
		p_77659_2_.playSoundAtEntity(p_77659_3_, Reference.MCAssetVer+":entity.fishing_bobber.retrieve", 1F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
	}

	@Redirect(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSoundAtEntity(Lnet/minecraft/entity/Entity;Ljava/lang/String;FF)V"))
	private void playCastSound(World world, Entity entity, String soundName, float volume, float pitch) {
		world.playSoundAtEntity(entity, Reference.MCAssetVer+":entity.fishing_bobber.throw", volume, pitch);
	}
}