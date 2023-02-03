package ganymedes01.etfuturum.blocks;

import java.util.Random;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockBuddingAmethyst extends BlockAmethystBlock {
	
	public BlockBuddingAmethyst() {
		setHardness(1.5F);
		setResistance(1.5F);
		setStepSound(ConfigSounds.newBlockSounds ? ModSounds.soundAmethystBlock : soundTypeGlass);
		setBlockTextureName("budding_amethyst");
		setBlockName(Utils.getUnlocalisedName("budding_amethyst"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		setTickRandomly(true);
	}
	
	public int getMobilityFlag()
	{
		return ConfigWorld.buddingAmethystMode == 0 ? 1 : 0;
	}

	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
	{
		return ConfigWorld.buddingAmethystMode != 0;
	}
	
	public int quantityDropped(Random p_149745_1_)
	{
		return ConfigWorld.buddingAmethystMode == 2 ? 1 : 0;
	}

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return ConfigWorld.buddingAmethystMode == 2 ? super.getItemDropped(p_149650_1_, p_149650_2_, p_149650_3_) : null;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (rand.nextInt(5) == 0) {
			EnumFacing facing = EnumFacing.getFront(rand.nextInt(EnumFacing.values().length));
			Block block = world.getBlock(x + facing.getFrontOffsetX(), y + facing.getFrontOffsetY(), z + facing.getFrontOffsetZ());
			int meta = world.getBlockMetadata(x + facing.getFrontOffsetX(), y + facing.getFrontOffsetY(), z + facing.getFrontOffsetZ());
			
			if(block instanceof BlockAmethystCluster && meta % 6 == facing.ordinal()) {
				if(meta < 6) {
					world.setBlockMetadataWithNotify(x + facing.getFrontOffsetX(), y + facing.getFrontOffsetY(), z + facing.getFrontOffsetZ(), meta + 6, 3);
				} else if(block == ModBlocks.amethyst_cluster_1) {
					world.setBlock(x + facing.getFrontOffsetX(), y + facing.getFrontOffsetY(), z + facing.getFrontOffsetZ(), ModBlocks.amethyst_cluster_2, meta - 6, 3);
				}
			} else if(canGrowIn(block)) {
				world.setBlock(x + facing.getFrontOffsetX(), y + facing.getFrontOffsetY(), z + facing.getFrontOffsetZ(), ModBlocks.amethyst_cluster_1, facing.ordinal(), 3);
			}
		}
	}

	private boolean canGrowIn(Block state) {
		return state.getMaterial() == Material.air || state.getMaterial() == Material.water;
	}
}
