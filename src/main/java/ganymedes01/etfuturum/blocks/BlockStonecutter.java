package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockStonecutter extends Block implements IConfigurable {

    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;
    @SideOnly(Side.CLIENT)
    private IIcon bottomIcon;
    @SideOnly(Side.CLIENT)
    public IIcon sawIcon;

	public BlockStonecutter() {
		super(Material.rock);
		this.setHarvestLevel("pickaxe", 0);
		this.setStepSound(soundTypeStone);
		this.setHardness(3.5F);
		this.setResistance(3.5F);
		this.setBlockName(Utils.getUnlocalisedName("stonecutter"));
		this.setBlockTextureName("stonecutter");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
        this.setLightOpacity(0);
        this.useNeighborBrightness = true;
	}
	
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    }
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_1_ == 1 ? this.blockIcon : p_149691_1_ == 0 ? bottomIcon : this.sideIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.sideIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.bottomIcon = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
        this.sawIcon = p_149651_1_.registerIcon(this.getTextureName() + "_saw");
    }

	@Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
		int ordinal = MathHelper.floor_double((double)(p_149689_5_.rotationYaw / 90.0F) + 0.5D) & 3;

        if (ordinal == 1)
        {
        	ordinal = 3;
        } else if (ordinal == 2)
        {
        	ordinal = 1;
        } else if (ordinal == 3)
        {
        	ordinal = 2;
        }
        
		p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, ordinal, 2);
    }

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
    public int getRenderType()
    {
        return RenderIDs.STONECUTTER;
    }

	@Override
	public boolean isEnabled() {
		return EtFuturum.isTesting;
	}

}
