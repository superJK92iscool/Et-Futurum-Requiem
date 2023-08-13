package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.EntityItemUninflammable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNetherite extends Block {

	public BlockNetherite() {
		super(Material.iron);
		setHarvestLevel("pickaxe", 3);
		setHardness(50F);
		setResistance(1200F);
		Utils.setBlockSound(this, ModSounds.soundNetherite);
		setBlockTextureName("netherite_block");
		setBlockName(Utils.getUnlocalisedName("netherite_block"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}
	
	@Override
	public boolean isBeaconBase(IBlockAccess world, int x, int y, int z, int bX, int bY, int bZ) {
		return true;
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
