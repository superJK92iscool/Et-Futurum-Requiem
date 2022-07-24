package ganymedes01.etfuturum.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.client.DynamicResourcePack;
import ganymedes01.etfuturum.client.DynamicResourcePack.GrayscaleType;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import ganymedes01.etfuturum.tileentities.TileEntityCauldronColoredWater;
import ganymedes01.etfuturum.tileentities.TileEntityCauldronPotion;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
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
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		double d0 = x + (random.nextFloat() * 0.875F) + 0.125F;
		double d1 = z + (random.nextFloat() * 0.875F) + 0.125F;
		
		if (random.nextInt(1) == 0) {
			int color = ((TileEntityCauldronColoredWater)world.getTileEntity(x, y, z)).getWaterColor();
	        float r = (float)(color >> 16 & 255) / 255.0F;
	        float g = (float)(color >> 8 & 255) / 255.0F;
	        float b = (float)(color & 255) / 255.0F;
	        int l = BlockCauldron.func_150027_b(world.getBlockMetadata(x, y, z));
	        float f = (float)y + (6.0F + (float)(3 * (l + 1))) / 16.0F;
			world.spawnParticle("mobSpell", d0, y + f, d1, r, g, b);
		}
	}
	
	

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		ItemStack stack = entityPlayer.getHeldItem();
		if(stack != null) {
			Item item = stack.getItem();
			TileEntityCauldronPotion potionCauldron = (TileEntityCauldronPotion) world.getTileEntity(x, y, z);
			if(item == Items.potionitem) {
				boolean shouldFill = true;
				if(potionCauldron.potion == null) {
					potionCauldron.potion = stack;
					potionCauldron.potion.stackSize = 1;
					potionCauldron.potion.func_135074_t();
					shouldFill = false;
				}

				if(!((ItemPotion)item).getEffects(stack).equals(((ItemPotion)potionCauldron.potion.getItem()).getEffects(potionCauldron.potion))) {
					world.setBlock(x, y, z, Blocks.cauldron, 0, 3);
					//TODO: Add smoke particles and sound
					return true;
				} else if(world.getBlockMetadata(x, y, z) < 2) {
					ItemStack bottle = new ItemStack(Items.glass_bottle);
					if(stack.stackSize <= 1 && !entityPlayer.capabilities.isCreativeMode) {
						entityPlayer.setCurrentItemOrArmor(0, bottle);
					} else {
						if(!entityPlayer.capabilities.isCreativeMode) {
							entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
							if(!entityPlayer.inventory.addItemStackToInventory(bottle)) {
								entityPlayer.dropPlayerItemWithRandomChoice(bottle, false);
							}
						}
					}
					if(shouldFill) {
						//TODO: Add filling sound
						world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) + 1, 3);
					}
					return true;
				}
			} else if(item == Items.glass_bottle) {
				if(world.getBlockMetadata(x, y, z) <= 0) {
					world.setBlock(x, y, z, Blocks.cauldron, 0, 3);
				} else {
					world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) - 1, 3);
				}
				//TODO: Add taking sound
				ItemStack newPotion = potionCauldron.potion.copy();
				if(stack.stackSize <= 1 && !entityPlayer.capabilities.isCreativeMode) {
					entityPlayer.setCurrentItemOrArmor(0, newPotion);
				} else {
					if(!entityPlayer.capabilities.isCreativeMode) {
						entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
					}
					if(!entityPlayer.inventory.addItemStackToInventory(newPotion)) {
						entityPlayer.dropPlayerItemWithRandomChoice(newPotion, false);
					}
				}
				return true;
			} else if(item == Items.arrow) {
				if(world.getBlockMetadata(x, y, z) <= 0) {
					world.setBlock(x, y, z, Blocks.cauldron, 0, 3);
				} else {
					world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) - 1, 3);
				}
				//TODO: Tipped arrows.
				//1 Water level (meta 0) = 16 or less arrows
				//2 Water level (meta 1) = 32 or less arrows
				//3 Water level (meta 2) = 64 or less arrows
			}
		}
		return false;
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
		blockIcon = p_149651_1_.registerIcon(DynamicResourcePack.createGrayscaleName("water_still", GrayscaleType.TINT_INVERSE));
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
