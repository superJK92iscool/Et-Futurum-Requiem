package ganymedes01.etfuturum.mixins.early.sounds;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityEnderEye.class)
public abstract class MixinEntityEnderEye extends Entity {

	public MixinEntityEnderEye(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	@Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityEnderEye;setDead()V"))
	public void playDropSound(CallbackInfo ci) {
		worldObj.playSoundAtEntity(this, Reference.MCAssetVer + ":entity.ender_eye.death", 1.0F, 1.0F);
	}

}
