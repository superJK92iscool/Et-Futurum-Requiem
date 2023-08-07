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
		MapColor mapColor = MapColor.airColor;
		switch (this.meta) {
		case 0: mapColor =  MapColor.snowColor; break;
		case 1: mapColor =  MapColor.adobeColor; break;
		case 2: mapColor =  MapColor.magentaColor; break;
		case 3: mapColor =  MapColor.lightBlueColor; break;
		case 4: mapColor =  MapColor.yellowColor; break;
		case 5: mapColor =  MapColor.limeColor; break;
		case 6: mapColor =  MapColor.pinkColor; break;
		case 7: mapColor =  MapColor.grayColor; break;
		case 8: mapColor =  MapColor.silverColor; break;
		case 9: mapColor =  MapColor.cyanColor; break;
		case 10: mapColor =  MapColor.purpleColor; break;
		case 11: mapColor =  MapColor.blueColor; break;
		case 12: mapColor =  MapColor.brownColor; break;
		case 13: mapColor =  MapColor.greenColor; break;
		case 14: mapColor =  MapColor.redColor; break;
		case 15: mapColor =  MapColor.blackColor; break;
		}
		return mapColor;
	}

}
