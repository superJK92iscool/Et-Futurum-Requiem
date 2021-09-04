package ganymedes01.etfuturum.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class PotionLevitation extends Potion {

	protected PotionLevitation(int p_i1573_1_, boolean p_i1573_2_, int p_i1573_3_) {
		super(p_i1573_1_, p_i1573_2_, p_i1573_3_);
		setPotionName("potion.levitation");
		setIconIndex(0, 0);
		setEffectiveness(1);
	}

    public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_)
    {
//    	p_76394_1_.moveEntity(0, 1, 0);
    	if(!(p_76394_1_ instanceof EntityPlayer) || !((EntityPlayer)p_76394_1_).capabilities.isFlying) {
    		p_76394_1_.addVelocity(0, (0.05D * (double)(p_76394_2_ + 8) - p_76394_1_.motionY) * 0.2D, 0);
    		p_76394_1_.velocityChanged = true;
    	}
    }
    public boolean isReady(int p_76397_1_, int p_76397_2_)
    {
    	return true;
    }
}
