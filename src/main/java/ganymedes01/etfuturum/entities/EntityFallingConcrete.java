package ganymedes01.etfuturum.entities;

import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFallingConcrete extends EntityFallingBlock {

	private static final int META_DATA_WATCHER = 2;
	private static final int BLOCKID_DATA_WATCHER = 3;

	public EntityFallingConcrete(World worldIn) {
		super(worldIn);
		getDataWatcher().addObject(META_DATA_WATCHER, 0);
		getDataWatcher().addObject(BLOCKID_DATA_WATCHER, 0);
	}

	public EntityFallingConcrete(World worldIn, double x, double y, double z, Block block, int meta) {
		super(worldIn, x, y, z, block, meta);
		getDataWatcher().addObject(META_DATA_WATCHER, field_145814_a);
		getDataWatcher().addObject(BLOCKID_DATA_WATCHER, Block.getIdFromBlock(field_145811_e));
	}    

	@Override
	public void onUpdate() {
		if (field_145811_e == null) {
			field_145811_e = Block.getBlockById(getDataWatcher().getWatchableObjectInt(BLOCKID_DATA_WATCHER));
			field_145814_a = getDataWatcher().getWatchableObjectInt(META_DATA_WATCHER);
		}
		super.onUpdate();
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posY);
		int k = MathHelper.floor_double(this.posZ);

		for (int jOff = 0; jOff <= (motionY < -1.0 ? 1 : 0); jOff++) { // If it's moving downward faster than a threshold speed: 1 in this case
			if (this.worldObj.getBlock(i, j - jOff, k).getMaterial() == Material.water) {
				this.worldObj.setBlock(i, j - jOff, k, ModBlocks.CONCRETE.get(), field_145814_a, 3);
				this.setDead();
			}
		}
	}
}
	
