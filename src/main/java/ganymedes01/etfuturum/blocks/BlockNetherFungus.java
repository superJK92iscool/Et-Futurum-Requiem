package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigExperiments;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.world.generate.decorate.WorldGenHugeFungus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class BlockNetherFungus extends BlockBush implements ISubBlocksBlock, IGrowable {

	private IIcon[] icons;
	private final String[] types = new String[]{"crimson_fungus", "warped_fungus"};
	private static final String name = "roots"; //Bypass stupid MC dev warning for no translation key

	public BlockNetherFungus() {
		Utils.setBlockSound(this, ModSounds.soundFungus);
		setBlockName(name);
		setBlockTextureName(name);
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return EnumPlantType.Nether;
	}

	public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_) {
		Block block = p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_);
		return block == Blocks.mycelium || block.canSustainPlant(p_149718_1_, p_149718_2_, p_149718_3_ - 1, p_149718_4_, ForgeDirection.UP, this)
				|| block.canSustainPlant(p_149718_1_, p_149718_2_, p_149718_3_ - 1, p_149718_4_, ForgeDirection.UP, Blocks.tallgrass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[meta % icons.length];
	}

	@Override
	public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_) {
		return damageDropped(p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_));
	}

	@Override
	public int damageDropped(int p_149692_1_) {
		return p_149692_1_;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		if (ConfigExperiments.enableCrimsonBlocks) {
			list.add(new ItemStack(item, 1, 0));
		}
		if (ConfigExperiments.enableWarpedBlocks) {
			list.add(new ItemStack(item, 1, 1));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[2];

		blockIcon = icons[0] = reg.registerIcon("crimson_fungus");
		icons[1] = reg.registerIcon("warped_fungus");
	}

	@Override
	public IIcon[] getIcons() {
		return icons;
	}

	@Override
	public String[] getTypes() {
		return types;
	}

	@Override
	public String getNameFor(ItemStack stack) {
		return getTypes()[stack.getItemDamage() % types.length];
	}

	public boolean func_149851_a(World world, int x, int y, int z, boolean p_149851_5_) {
		return world.getBlock(x, y - 1, z) == ModBlocks.NYLIUM.get() && world.getBlockMetadata(x, y, z) == world.getBlockMetadata(x, y - 1, z);
	}

	public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_) {
		return (double) p_149852_1_.rand.nextFloat() < 0.40D;
	}

	public void func_149853_b(World world, Random rand, int x, int y, int z) {
		boolean crimson = world.getBlockMetadata(x, y, z) == 0;
		WorldGenAbstractTree fungus = new WorldGenHugeFungus(true, 0, crimson ? 0 : 1,
				crimson ? ModBlocks.CRIMSON_STEM.get() : ModBlocks.WARPED_STEM.get(), ModBlocks.NETHER_WART.get(), true);
		fungus.generate(world, rand, x, y, z);
	}
}
