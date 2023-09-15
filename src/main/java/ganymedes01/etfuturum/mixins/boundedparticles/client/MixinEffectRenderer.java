package ganymedes01.etfuturum.mixins.boundedparticles.client;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(EffectRenderer.class)
public class MixinEffectRenderer {

	@Shadow
	protected World worldObj;

	@Shadow
	public void addEffect(EntityFX p_78873_1_) {
	}


	/**
	 * The addBlockDestroyEffects code from 1.18, ported to 1.7.10. Makes block destroy particles stay within the outline of the block.
	 * Code found by makamys and ported by Roadhog360.
	 *
	 * @author Roadhog360
	 * @author makamys
	 * @reason This is the function that handles the block breaking particles. It needs to be completely overwritten as we are rewriting it.
	 */
	@Overwrite
	public void addBlockDestroyEffects(int x, int y, int z, Block block, int p_147215_5_) {
		if (!block.isAir(worldObj, x, y, z) && !block.addDestroyEffects(worldObj, x, y, z, p_147215_5_, (EffectRenderer) (Object) this)) {
			AxisAlignedBB FULL_AABB = AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1, (double) y + 1, (double) z + 1);
			List<AxisAlignedBB> list = new ArrayList<>();
			try {
				//Check if the block's collission boxes are within a single block estate.
				block.addCollisionBoxesToList(worldObj, x, y, z, FULL_AABB, list, null);
			} catch (NullPointerException e) {
				list.clear(); //If any elements were already added to the list, let's clear them so we don't get half-assed breaking particles
			}
			//If it throws an NPE (the collision code wanted an entity) or has no collision that means we cannot reasonably determine the block's bounds.
			//In that case this just falls back to code similar to the old one which only bounds it by the block's base bounding box.
			if (list.isEmpty()) {
				list.add(block.getSelectedBoundingBoxFromPool(worldObj, x, y, z));
			}
			for (AxisAlignedBB box : list) {
				box.minX -= x;
				box.maxX -= x;
				box.minY -= y;
				box.maxY -= y;
				box.minZ -= z;
				box.maxZ -= z;
				double d = Math.min(1.0, box.maxX - box.minX);
				double e = Math.min(1.0, box.maxY - box.minY);
				double f = Math.min(1.0, box.maxZ - box.minZ);
				int i = Math.max(2, MathHelper.ceiling_double_int(d * 4));
				int j = Math.max(2, MathHelper.ceiling_double_int(e * 4));
				int k = Math.max(2, MathHelper.ceiling_double_int(f * 4));
				for (int l = 0; l < i; ++l) {
					for (int m = 0; m < j; ++m) {
						for (int n = 0; n < k; ++n) {
							double g = ((double) l + 0.5D) / (double) i;
							double h = ((double) m + 0.5D) / (double) j;
							double o = ((double) n + 0.5D) / (double) k;
							double p = g * d + box.minX;
							double q = h * e + box.minY;
							double r = o * f + box.minZ;
							this.addEffect(new EntityDiggingFX(this.worldObj, x + p, y + q, z + r, g - 0.5, h - 0.5, o - 0.5, block, p_147215_5_).applyColourMultiplier(x, y, z));
						}
					}
				}
			}
		}
	}
}