package ganymedes01.etfuturum.mixins.early.sounds;

import ganymedes01.etfuturum.blocks.BlockBerryBush;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {

	@Shadow
	protected abstract String getDeathSound();

	@Shadow
	protected abstract String getHurtSound();

	public MixinEntityPlayer(World p_i1594_1_) {
		super(p_i1594_1_);
	}

	@Unique
	private DamageSource etfuturum$lastDamageSource;

	@Inject(method = "damageEntity", at = @At("HEAD"))
	public void captureLastDamageSource(DamageSource p_70097_1_, float p_70097_2_, CallbackInfo ci) {
		etfuturum$lastDamageSource = p_70097_1_;
	}

	@Unique
	private String etfuturum$getUniqueHurtSound() {
		if (etfuturum$lastDamageSource != null) {
			if (etfuturum$lastDamageSource.isFireDamage()) {
				return Reference.MCAssetVer + ":entity.player.hurt_on_fire";
			}
			if (etfuturum$lastDamageSource == DamageSource.drown) {
				return Reference.MCAssetVer + ":entity.player.hurt_drown";
			}
			if (etfuturum$lastDamageSource == BlockBerryBush.SWEET_BERRY_BUSH) {
				return Reference.MCAssetVer + ":entity.player.hurt_sweet_berry_bush";
			}
		}
		return null;
	}

	@Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
	protected void getHurtSound(CallbackInfoReturnable<String> cir) {
		if (etfuturum$getUniqueHurtSound() != null) {
			cir.setReturnValue(etfuturum$getUniqueHurtSound());
		}
	}

	@Inject(method = "getDeathSound", at = @At("HEAD"), cancellable = true)
	protected void getDeathSound(CallbackInfoReturnable<String> cir) {
		if (etfuturum$getUniqueHurtSound() != null) {
			cir.setReturnValue(etfuturum$getUniqueHurtSound());
		}
	}

	@Inject(method = "playSound", at = @At("HEAD"), cancellable = true)
	public void overrideDamageSound(String p_85030_1_, float p_85030_2_, float p_85030_3_, CallbackInfo ci) {
		if (etfuturum$lastDamageSource != null && (p_85030_1_.equals(getHurtSound()) || p_85030_1_.equals(getDeathSound()))) {
			this.worldObj.playSoundAtEntity(this, p_85030_1_, p_85030_2_, p_85030_3_);
			etfuturum$lastDamageSource = null;
			ci.cancel();
		}
	}
}
