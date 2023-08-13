package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import ganymedes01.etfuturum.recipes.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockGlazedTerracotta extends Block {
	
	private final int meta;
	
	public BlockGlazedTerracotta(int meta) {
		super(Material.rock);
		setStepSound(soundTypeStone);
		setHardness(1.4F);
		setResistance(1.4F);
		setBlockName(Utils.getUnlocalisedName(ModRecipes.dye_names[meta] + "_glazed_terracotta"));
		setBlockTextureName(ModRecipes.dye_names[meta] + "_glazed_terracotta");
		setCreativeTab(EtFuturum.creativeTabBlocks);
		this.meta = meta;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{
		return RenderIDs.GLAZED_TERRACOTTA;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
	{
		int direction = MathHelper.floor_double(living.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, direction, 2);
	}
	
	@Override
	public MapColor getMapColor(int _meta)
	{
		return MapColor.getMapColorForBlockColored(meta);
	}

}
