package ganymedes01.etfuturum.mixins.early.sounds;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntitySnowman.class)
public class MixinEntitySnowman extends EntityGolem {

	public MixinEntitySnowman(World p_i1686_1_) {
		super(p_i1686_1_);
	}

	@Override
	protected String getHurtSound() {
		return Reference.MCAssetVer + ":entity.snow_golem.hurt";
	}

	@Override
	protected String getDeathSound() {
		return Reference.MCAssetVer + ":entity.snow_golem.death";
	}

	@Override
	protected String getLivingSound() {
		return Reference.MCAssetVer + ":entity.snow_golem.ambient";
	}
}
