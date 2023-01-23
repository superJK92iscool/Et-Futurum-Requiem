package ganymedes01.etfuturum.mixins.client;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

@Mixin(EffectRenderer.class)
public class MixinEffectRenderer {
	
	@Shadow
	protected World worldObj;
	
	@Shadow
    public void addEffect(EntityFX p_78873_1_) {}
	
	
	/**
	 * The addBlockDestroyEffects code from 1.18, ported to 1.7.10. Makes block destroy particles stay within the outline of the block.
	 * Code found by makamys and ported by Roadhog360.
	 * 
	 * @author Roadhog360
	 * @author makamys
	 */
	@Overwrite
    public void addBlockDestroyEffects(int x, int y, int z, Block block, int p_147215_5_)
    {
		if (!block.isAir(worldObj, x, y, z) && !block.addDestroyEffects(worldObj, x, y, z, p_147215_5_, (EffectRenderer)(Object)this)) {
        	double maxX = block.getBlockBoundsMaxX();
        	double maxY = block.getBlockBoundsMaxY();
        	double maxZ = block.getBlockBoundsMaxZ();
        	double minX = block.getBlockBoundsMinX();
        	double minY = block.getBlockBoundsMinY();
        	double minZ = block.getBlockBoundsMinZ();
            double d = Math.min(1.0, maxX - minX);
            double e = Math.min(1.0, maxY - minY);
            double f = Math.min(1.0, maxZ - minZ);
            int i = Math.max(2, MathHelper.ceiling_double_int(d / 0.25));
            int j = Math.max(2, MathHelper.ceiling_double_int(e / 0.25));
            int k = Math.max(2, MathHelper.ceiling_double_int(f / 0.25));
            for (int l = 0; l < i; ++l) {
                for (int m = 0; m < j; ++m) {
                    for (int n = 0; n < k; ++n) {
                        double g = ((double)l + 0.5) / (double)i;
                        double h = ((double)m + 0.5) / (double)j;
                        double o = ((double)n + 0.5) / (double)k;
                        double p = g * d + minX;
                        double q = h * e + minY;
                        double r = o * f + minZ;
                        this.addEffect(new EntityDiggingFX(this.worldObj, x + p, y + q, z + r, g - 0.5, h - 0.5, o - 0.5, block, p_147215_5_).applyColourMultiplier(x, y, z));
                    }
                }
            }
        }
    }
}