package ganymedes01.etfuturum.mixins.sounds;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntitySquid.class)
public class MixinEntitySquid extends EntityWaterMob {

	public MixinEntitySquid(World p_i1695_1_) {
		super(p_i1695_1_);
	}

	protected String getHurtSound()
	{
		return Reference.MCAssetVer + ":entity.squid.hurt";
	}

	protected String getDeathSound()
	{
		return Reference.MCAssetVer + ":entity.squid.death";
	}
	
	protected String getLivingSound()
	{
		return Reference.MCAssetVer + ":entity.squid.ambient";
	}

}
