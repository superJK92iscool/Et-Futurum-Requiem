package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.Utils;
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
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class BlockWoodFenceNew extends BlockFence implements ISubBlocksBlock {

	final BlockWoodPlanks basePlanks;
	final String[] types;

	public BlockWoodFenceNew() {
		super(null, Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypeWood);
		basePlanks = (BlockWoodPlanks) ModBlocks.WOOD_PLANKS.get();
		types = ArrayUtils.clone(basePlanks.getTypes()); //We need to clone it to not ruin the regular plank type icons
		for (int i = 0; i < types.length; i++) {
			types[i] = types[i].replace("planks", "fence");
		}
	}

	@Override
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
		ModBlocks.WOOD_PLANKS.get().getSubBlocks(p_149666_1_, p_149666_2_, p_149666_3_);
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return basePlanks.getIcon(p_149691_1_, p_149691_2_);
	}

	@Override
	public void registerBlockIcons(IIconRegister ignored) {
	}

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
		return super.canConnectFenceTo(world, x, y, z) || block instanceof BlockWoodFence || block instanceof BlockWoodFenceNew || block instanceof BlockWoodFenceGate;
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
}
