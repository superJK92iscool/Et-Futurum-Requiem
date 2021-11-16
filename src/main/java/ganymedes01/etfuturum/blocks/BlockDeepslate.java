package ganymedes01.etfuturum.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.ai.BlockPos;
import ganymedes01.etfuturum.world.EtFuturumLateWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockDeepslate extends BlockRotatedPillar implements IConfigurable {

	public BlockDeepslate() {
		super(Material.rock);
		this.setHardness(3);
		this.setResistance(6);
		this.setBlockName(Utils.getUnlocalisedName("deepslate"));
		this.setBlockTextureName("deepslate");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		this.setStepSound(ConfigWorld.enableNewBlocksSounds ? ModSounds.soundDeepslate : soundTypeStone);
	}

	@Override
	protected IIcon getSideIcon(int p_150163_1_) {
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected IIcon getTopIcon(int p_150161_1_)
	{
		return field_150164_N;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		blockIcon = p_149651_1_.registerIcon(getTextureName());
		field_150164_N = p_149651_1_.registerIcon(getTextureName() + "_top");
	}
	
	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(ModBlocks.cobbled_deepslate);
	}

	@Override
	/**
	 * We need to do this in order to make sure deepslate generates, as veins crossing a chunk border may not get replaced fully.
	 * This is used to detect those ores since they'd generate over deepslate, not the other way around.
	 * Then we add them to a list of BlockPos objects which the check is re-done to.
	 * The last save time is checked for 0 to ensure that only fresh chunks get deepslated.
	 */
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		boolean flag = target == Blocks.stone || this == target;
		if(flag) {
			doDeepslateRedoCheck(world, x, y, z);
		}
		return flag;
	}
	
	public static void doDeepslateRedoCheck(World world, int x, int y, int z) {
		if(!EtFuturumLateWorldGenerator.stopRecording && world.checkChunksExist(x, 0, z, x, 0, z) && world.getChunkFromBlockCoords(x, z).lastSaveTime == 0) {
			BlockPos pos = new BlockPos(x, y, z);
			List<BlockPos> redo = EtFuturumLateWorldGenerator.getRedoList(world.provider.dimensionId);
			if(!redo.contains(pos)) {
				redo.add(pos);
			}
		}
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableDeepslate;
	}
}
