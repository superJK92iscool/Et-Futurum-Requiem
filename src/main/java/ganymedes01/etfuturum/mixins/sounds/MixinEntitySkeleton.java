package ganymedes01.etfuturum.mixins.sounds;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntitySkeleton.class)
public class MixinEntitySkeleton extends EntityMob {

	public MixinEntitySkeleton(World p_i1738_1_) {
		super(p_i1738_1_);
	}
	
	@Overwrite
	protected String getLivingSound()
	{
		return getSkeletonType() == 1 ? Reference.MCAssetVer + ":entity.wither_skeleton.ambient" : "mob.skeleton.say";
	}

	@Overwrite
	protected String getHurtSound()
	{
		return getSkeletonType() == 1 ? Reference.MCAssetVer + ":entity.wither_skeleton.hurt" : "mob.skeleton.hurt";
	}

	@Overwrite
	protected String getDeathSound()
	{
		return getSkeletonType() == 1 ? Reference.MCAssetVer + ":entity.wither_skeleton.death" : "mob.skeleton.death";
	}

	@Overwrite
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		this.playSound(getSkeletonType() == 1 ? Reference.MCAssetVer + ":entity.wither_skeleton.step" : "mob.skeleton.step", 0.15F, 1.0F);
	}
	
	@Shadow
	public int getSkeletonType() {
		return 0;
	}

}
