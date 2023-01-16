package ganymedes01.etfuturum.mixins.entitysounds;

import org.spongepowered.asm.mixin.Mixin;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.world.World;

@Mixin(EntityWitch.class)
public class MixinEntityWitch extends EntityMob {

	public MixinEntityWitch(World p_i1738_1_) {
		super(p_i1738_1_);
	}

    protected String getHurtSound()
    {
        return Reference.MCAssetVer + ":entity.witch.hurt";
    }

    protected String getDeathSound()
    {
        return Reference.MCAssetVer + ":entity.witch.death";
    }
    
    protected String getLivingSound()
    {
        return Reference.MCAssetVer + ":entity.witch.ambient";
    }

}
