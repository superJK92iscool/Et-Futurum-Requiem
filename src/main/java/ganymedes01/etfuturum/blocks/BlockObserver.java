package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockObserver extends Block implements IConfigurable {
    @SideOnly(Side.CLIENT)
    private IIcon observerFront, observerBack, observerTop, observerBackLit;

    public BlockObserver() {
        super(Material.rock);
        this.setHardness(3F);
        this.setHarvestLevel("pickaxe", 0);
        this.setResistance(3F);
        this.setBlockName(Utils.getUnlocalisedName("observer"));
        this.setBlockTextureName("observer");
        this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        int k = BlockPistonBase.getPistonOrientation(meta);
        boolean powered = (meta & 0x8) != 0;
        if(side == k)
            return this.observerFront;
        else if(side == Facing.oppositeSide[k])
            return powered ? this.observerBackLit : this.observerBack;
        else {
            int topSide;
            switch(k) {
                case 2:
                case 3:
                case 4:
                case 5:
                    topSide = 1;
                    break;
                default:
                case 0:
                case 1:
                    topSide = 2;
                    break;
            }
            return (side == topSide || side == Facing.oppositeSide[topSide]) ? this.observerTop : this.blockIcon;
        }
    }

    @Override
    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = BlockPistonBase.determineOrientation(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_);
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, Facing.oppositeSide[l], 2);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon("observer_side");
        this.observerFront = p_149651_1_.registerIcon("observer_front");
        this.observerTop = p_149651_1_.registerIcon( "observer_top");
        this.observerBack = p_149651_1_.registerIcon( "observer_back");
        this.observerBackLit = p_149651_1_.registerIcon( "observer_back_lit");
    }

    @Override
    public int getRenderType() {
        return RenderIDs.OBSERVER;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return ConfigMixins.enableObservers;
    }
}
