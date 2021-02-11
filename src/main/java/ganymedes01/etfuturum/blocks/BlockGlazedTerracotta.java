package ganymedes01.etfuturum.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.proxy.ClientProxy;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockGlazedTerracotta extends Block implements IConfigurable {

	// These are used to reference texture names
    public static final String[] subBlock = {
    		"white", //0
    		"orange", //1
    		"magenta", //2
    		"light_blue", //3
    		"yellow", //4
    		"lime", //5
    		"pink", //6
    		"gray", //7
    		"light_gray", //8
    		"cyan", //9
    		"purple", //10
    		"blue", //11
    		"brown", //12
    		"green", //13
    		"red", //14
    		"black" //15
    		};
    
    private final int meta;
    
	public BlockGlazedTerracotta(int meta) {
		super(Material.rock);
		setBlockName(Utils.getUnlocalisedName(subBlock[meta] + "_glazed_terracotta"));
		setBlockTextureName(subBlock[meta] + "_glazed_terracotta");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
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
	public MapColor getMapColor(int meta)
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

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableGlazedTerracotta;
	}

}
