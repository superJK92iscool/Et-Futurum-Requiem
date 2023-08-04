package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.entities.EntityItemUninflammable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockNetheriteStairs extends BasicStairs {

	public BlockNetheriteStairs() {
		super(ModBlocks.NETHERITE_BLOCK.get(), 0);
		setBlockName("netherite");
	}

	@Override
	protected void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack) {
		// do not drop items while restoring blockstates, prevents item dupe
		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops") && !world.restoringBlockSnapshots) {
			if (captureDrops.get()) {
				capturedDrops.get().add(stack);
				return;
			}
			float f = 0.7F;
			double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItemUninflammable(world, x + d0, y + d1, z + d2, stack);
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}
	}

}
