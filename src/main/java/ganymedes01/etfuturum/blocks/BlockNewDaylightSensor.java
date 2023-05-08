package ganymedes01.etfuturum.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockNewDaylightSensor extends BlockDaylightDetector {

	public BlockNewDaylightSensor() {
		setHardness(0.2F);
		setStepSound(soundTypeWood);
		setBlockTextureName("daylight_detector");
		setBlockName(Utils.getUnlocalisedName("daylight_sensor"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.daylight_detector);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(Blocks.daylight_detector);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote)
			world.setBlock(x, y, z, ModBlocks.DAYLIGHT_DETECTOR_INVERTED.get());
		return true;
	}
}