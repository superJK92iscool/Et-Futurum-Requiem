package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.InterpolatedIcon;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class BlockNylium extends BaseSubtypesBlock implements IGrowable {

	private IIcon[] topIcons;

	public BlockNylium() {
		super(Material.rock, "crimson_nylium", "warped_nylium");
		setHardness(0.4F);
		setResistance(0.4F);
		setNames("nylium");
		setCreativeTab(EtFuturum.creativeTabBlocks);
		setBlockSound(ModSounds.soundNylium);
		setTickRandomly(true);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);
		Block block = world.getBlock(x, y + 1, z);
		if (block.isOpaqueCube() || block.getMaterial() == Material.snow || block.getMaterial() == Material.craftedSnow) {
			world.setBlock(x, y, z, Blocks.netherrack, 0, 2);
		}
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(Blocks.netherrack);
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
		return plantable.getPlantType(world, x, y, z) == EnumPlantType.Nether || plantable instanceof BlockMushroom;
	}

	public void onPlantGrow(World world, int x, int y, int z, int sourceX, int sourceY, int sourceZ) {
		if (world.rand.nextBoolean()) {
			world.setBlock(x, y, z, Blocks.netherrack, 0, 2);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		if (ConfigBlocksItems.enableCrimsonBlocks) {
			list.add(new ItemStack(item, 1, 0));
		}
		if (ConfigBlocksItems.enableWarpedBlocks) {
			list.add(new ItemStack(item, 1, 1));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		switch (side) {
			case 1:
				return topIcons[meta % topIcons.length];
			case 0:
				return blockIcon;
			default:
				return getIcons()[meta % getIcons().length];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		setIcons(new IIcon[getTypes().length]);
		topIcons = new IIcon[getTypes().length];
		for (int i = 0; i < getIcons().length; i++) {
			getIcons()[i] = reg.registerIcon(getTypes()[i] + "_side");
			topIcons[i] = reg.registerIcon(getTypes()[i]);
		}
		blockIcon = Blocks.netherrack.getIcon(0, 0);
	}

	@Override
	public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_) {
		return true;
	}

	@Override
	public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_) {
		return true;
	}

	@Override
	public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_) {
		int l = 0;

		while (l < 128) {
			int i1 = p_149853_3_;
			int j1 = p_149853_4_ + 1;
			int k1 = p_149853_5_;
			int l1 = 0;

			while (true) {
				if (l1 < l / 16) {
					i1 += p_149853_2_.nextInt(3) - 1;
					j1 += (p_149853_2_.nextInt(3) - 1) * p_149853_2_.nextInt(3) / 2;
					k1 += p_149853_2_.nextInt(3) - 1;

					if (p_149853_1_.getBlock(i1, j1 - 1, k1) == Blocks.grass && !p_149853_1_.getBlock(i1, j1, k1).isNormalCube()) {
						++l1;
						continue;
					}
				} else if (p_149853_1_.getBlock(i1, j1, k1).blockMaterial == Material.air) {
					if (p_149853_2_.nextInt(8) != 0) {
						if (Blocks.tallgrass.canBlockStay(p_149853_1_, i1, j1, k1)) {
							p_149853_1_.setBlock(i1, j1, k1, Blocks.tallgrass, 1, 3);
						}
					} else {
						p_149853_1_.getBiomeGenForCoords(i1, k1).plantFlower(p_149853_1_, p_149853_2_, i1, j1, k1);
					}
				}

				++l;
				break;
			}
		}
	}
}
