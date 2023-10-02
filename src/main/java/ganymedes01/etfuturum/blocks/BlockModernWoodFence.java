package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BlockModernWoodFence extends BlockFence implements ISubBlocksBlock {

	final BlockModernWoodPlanks basePlanks;
	final String[] types;

	public BlockModernWoodFence() {
		super(null, Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypeWood);
		basePlanks = (BlockModernWoodPlanks) ModBlocks.WOOD_PLANKS.get();
		ArrayList<String> typesList = new ArrayList<>(Arrays.asList(basePlanks.getTypes()));
		if (typesList.size() >= 6) {
			typesList.remove(5); // Remove Mosaic
		}

		types = new String[typesList.size()];
		for (int i = 0; i < typesList.size(); i++) {
			types[i] = typesList.get(i).replace("planks", "fence");
		}
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs p_149666_2_, List list) {
		if (ConfigBlocksItems.enableCrimsonBlocks) {
			list.add(new ItemStack(item, 1, 0));
		}
		if (ConfigBlocksItems.enableWarpedBlocks) {
			list.add(new ItemStack(item, 1, 1));
		}
		if (ConfigBlocksItems.enableMangroveBlocks) {
			list.add(new ItemStack(item, 1, 2));
		}
		if (ConfigBlocksItems.enableCherryBlocks) {
			list.add(new ItemStack(item, 1, 3));
		}
		if (ConfigBlocksItems.enableBambooBlocks) {
			list.add(new ItemStack(item, 1, 4));
		}
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return basePlanks.getIcon(p_149691_1_, p_149691_2_);
	}

	@Override
	public void registerBlockIcons(IIconRegister ignored) {}

	@Override
	public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_) {
		return damageDropped(p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_));
	}

	@Override
	public int damageDropped(int p_149692_1_) {
		return p_149692_1_ % getTypes().length;
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean canConnectFenceTo(IBlockAccess world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		return block instanceof BlockWoodFence || block instanceof BlockModernWoodFence || block instanceof BlockWoodFenceGate || super.canConnectFenceTo(world, x, y, z);
	}

	@Override
	public IIcon[] getIcons() {
		return basePlanks.getIcons();
	}

	@Override
	public String[] getTypes() {
		return types;
	}

	@Override
	public String getNameFor(ItemStack stack) {
		return types[stack.getItemDamage() % types.length];
	}

	@Override
	public int getRenderType() {
		return RenderIDs.FENCE;
	}

	@Override
	public boolean isFlammable(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		if (ConfigFunctions.enableExtraBurnableBlocks) {
			int meta = aWorld.getBlockMetadata(aX, aY, aZ) % getTypes().length;
			return meta > 1;
		}
		return false;
	}

	@Override
	public int getFlammability(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return isFlammable(aWorld, aX, aY, aZ, aSide) ? 20 : 0;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return isFlammable(aWorld, aX, aY, aZ, aSide) ? 5 : 0;
	}
}
