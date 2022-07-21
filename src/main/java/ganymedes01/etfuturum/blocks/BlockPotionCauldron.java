package ganymedes01.etfuturum.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.client.GrayscaleIcon;
import ganymedes01.etfuturum.client.InterpolatedIcon;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import ganymedes01.etfuturum.tileentities.TileEntityCauldronPotion;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockPotionCauldron extends BlockContainer implements ISubBlocksBlock, IConfigurable {

	public BlockPotionCauldron() {
		super(Material.iron);
		this.setStepSound(Blocks.cauldron.stepSound);
		this.setHardness(2);
		this.setResistance(2);
		this.setBlockName(Utils.getUnlocalisedName("potion_cauldron"));
	}

	@Override
	public int getRenderBlockPass() {
		return 0;
	}
	
	@Override
	public int getRenderType()
	{
		return RenderIDs.COLORED_CAULDRON;
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.cauldron.getIcon(side, meta);
	}

    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
    {
        int l = BlockCauldron.func_150027_b(p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_));
        float f = (float)p_149670_3_ + (6.0F + (float)(3 * (l + 1))) / 16.0F;

        if (!p_149670_1_.isRemote && p_149670_5_.isBurning() && p_149670_5_.boundingBox.minY <= (double)f)
        {
            p_149670_5_.extinguish();
            if(--l < 0) {
            	p_149670_1_.setBlock(p_149670_2_, p_149670_3_, p_149670_4_, Blocks.cauldron, 0, 3);
            } else {
                Blocks.cauldron.func_150024_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, l);
            }
        }
    }
    
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
    {
        Blocks.cauldron.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
    }
    
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Items.cauldron;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
    public IIcon greyscaleWaterIcon() {
    	return blockIcon;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		blockIcon = new GrayscaleIcon("water_still");
		if(p_149651_1_ instanceof TextureMap) {
			((TextureMap)p_149651_1_).setTextureEntry("water_still", (GrayscaleIcon)blockIcon);
		}
	}
    
    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Items.cauldron;
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCauldronPotion();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return null;
	}

}
