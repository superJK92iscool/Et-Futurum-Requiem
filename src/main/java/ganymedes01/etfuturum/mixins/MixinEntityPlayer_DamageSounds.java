package ganymedes01.etfuturum.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ganymedes01.etfuturum.blocks.BlockBerryBush;
import ganymedes01.etfuturum.core.utils.Logger;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer_DamageSounds extends EntityLivingBase {
	
	public MixinEntityPlayer_DamageSounds(World p_i1594_1_) {
		super(p_i1594_1_);
	}

	private DamageSource lastDamageSource;

	@Inject(method = "damageEntity", at = @At("HEAD"), cancellable = true)
    public void captureLastDamageSource(DamageSource p_70097_1_, float p_70097_2_, CallbackInfo ci)
    {
    	lastDamageSource = p_70097_1_;
    	Logger.info("captured " + lastDamageSource);
    }

    //Just in case this function is called elsewhere such as by another mod we add checks and reset the variable to prevent the wrong sound from being played.
	@Overwrite
    protected String getHurtSound()
    {
    	if(lastDamageSource != null) {
        	Logger.info("hurt sound " + lastDamageSource.getDamageType());
        	if(lastDamageSource.isFireDamage()) {
        		Logger.info("Using fire sound");
        		lastDamageSource = null;
        		return Reference.MCAssetVer + ":entity.player.hurt_on_fire";
        	}
        	if(lastDamageSource == DamageSource.drown) {
        		Logger.info("Using water sound");
        		lastDamageSource = null;
        		return Reference.MCAssetVer + ":entity.player.hurt_drown";
        	}
        	if(lastDamageSource == BlockBerryBush.SWEET_BERRY_BUSH) {
        		Logger.info("Using bush sound");
        		lastDamageSource = null;
        		return Reference.MCAssetVer + ":entity.player.hurt_sweet_berry_bush";
        	}
    	}
    	Logger.info("SOUND WAS NULL!");
        return super.getHurtSound();
    }
}
