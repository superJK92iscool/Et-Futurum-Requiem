package ganymedes01.etfuturum.mixins.entitysounds;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ganymedes01.etfuturum.blocks.BlockBerryBush;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {
	
	public MixinEntityPlayer(World p_i1594_1_) {
		super(p_i1594_1_);
	}

	private DamageSource lastDamageSource;

	@Inject(method = "damageEntity", at = @At("HEAD"), cancellable = true)
    public void captureLastDamageSource(DamageSource p_70097_1_, float p_70097_2_, CallbackInfo ci)
    {
    	lastDamageSource = p_70097_1_;
    }

    //Just in case this function is called elsewhere such as by another mod we add checks and reset the variable to prevent the wrong sound from being played.
	@Overwrite
    protected String getHurtSound()
    {
    	if(lastDamageSource != null) {
        	if(lastDamageSource.isFireDamage()) {
        		return Reference.MCAssetVer + ":entity.player.hurt_on_fire";
        	}
        	if(lastDamageSource == DamageSource.drown) {
        		return Reference.MCAssetVer + ":entity.player.hurt_drown";
        	}
        	if(lastDamageSource == BlockBerryBush.SWEET_BERRY_BUSH) {
        		return Reference.MCAssetVer + ":entity.player.hurt_sweet_berry_bush";
        	}
    	}
        return super.getHurtSound();
    }

	@Inject(method = "playSound", at = @At("HEAD"), cancellable = true)
    public void overrideDamageSound(String p_85030_1_, float p_85030_2_, float p_85030_3_, CallbackInfo ci)
    {
		if(p_85030_1_.equals(getHurtSound())) {
			this.worldObj.playSoundAtEntity(this, p_85030_1_, p_85030_2_, p_85030_3_);
    		lastDamageSource = null;
    		ci.cancel();
		}
    }
}
