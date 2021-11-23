package ganymedes01.etfuturum.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import ganymedes01.etfuturum.core.utils.helpers.CachedChunkCoords;
import ganymedes01.etfuturum.world.EtFuturumLateWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

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
	public static final Map<CachedChunkCoords, Chunk> chunkCache = new HashMap<CachedChunkCoords, Chunk>();
	
	public static void doDeepslateRedoCheck(World world, int x, int y, int z) {
		
		if(!EtFuturumLateWorldGenerator.stopRecording) {
			Chunk chunk;
			
			CachedChunkCoords cacheEntry  = new CachedChunkCoords(x >> 4, z >> 4, world.provider.dimensionId);
			
			if(!chunkCache.containsKey(cacheEntry)) {
				chunkCache.put(cacheEntry, chunk = world.getChunkFromChunkCoords(cacheEntry.xPos(), cacheEntry.zPos()));
			} else {
				chunk = chunkCache.get(cacheEntry);
			}
			
			/*
			 * We check if the chunk has this flag as false, which means it's still generating and the client hasn't received it yet.
			 * This prevents mods that use isReplaceableOreGen for non-worldgen purposes from converting blocks to deepslate versions.
			 */
			if(!chunk.sendUpdates) {
				BlockPos pos = new BlockPos(x, y, z);
				List<BlockPos> redo = EtFuturumLateWorldGenerator.getRedoList(cacheEntry);
				if(!redo.contains(pos)) {
					redo.add(pos);
				}
			}
		}
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableDeepslate;
	}
}
