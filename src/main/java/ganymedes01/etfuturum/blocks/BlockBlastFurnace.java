package ganymedes01.etfuturum.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.InterpolatedIcon;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.GUIsID;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.tileentities.TileEntityBlastFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBlastFurnace extends BlockFurnace implements IConfigurable {

	private boolean isCooking;
	private IIcon blockTop;
	public IIcon blockFront;
	private static boolean field_149934_M;
	
	public BlockBlastFurnace(boolean cooking) {
		super(cooking);
		isCooking = cooking;
		this.setHardness(3.5F);
		this.setResistance(3.5F);
		this.setLightLevel(cooking ? .875F : 0);
		this.setStepSound(soundTypePiston);
		this.setBlockName(Utils.getUnlocalisedName((cooking ? "lit_" : "") + "blast_furnace"));
		this.setCreativeTab(isEnabled() && !cooking ? EtFuturum.creativeTabBlocks : null);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	{
		return p_149691_1_ == 1 ? this.blockTop : (p_149691_1_ == 0 ? this.blockTop : (p_149691_1_ != p_149691_2_ ? (p_149691_1_ == 3 && p_149691_2_ == 0 ? this.blockFront : this.blockIcon) : this.blockFront));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
	{
		if (this.isCooking)
		{
			if (ConfigSounds.furnaceCrackling && p_149734_5_.nextDouble() < 0.1D)
			{
				p_149734_1_.playSound(p_149734_2_ + .5D, p_149734_3_ + .5D, p_149734_4_ + .5D,
						Reference.MCAssetVer + ":block.blastfurnace.fire_crackle", 1,
						(p_149734_1_.rand.nextFloat() * 0.1F) + 0.9F, false);
			}
			
			int l = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
			float r = .0625F;
			float f = p_149734_2_ + (r * 8);
			float f1 = p_149734_3_ + (r * 3) + p_149734_5_.nextFloat() * 5.0F / 16.0F;
			float f2 = p_149734_4_ + (r * 8);
			float f3 = r * 9;
			float f4 = p_149734_5_.nextFloat() * 0.6F - 0.3F;
			p_149734_1_.spawnParticle("smoke", f + (l == 4 ? -f3 : l == 5 ? f3 : f4), f1, f2 + (l == 3 ? f3 : l == 2 ? -f3 : f4), 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.blockIcon = p_149651_1_.registerIcon("blast_furnace_side");
		if(isCooking) {
			blockFront = new InterpolatedIcon("blast_furnace_front_on");
			if(p_149651_1_ instanceof TextureMap) {
				((TextureMap)p_149651_1_).setTextureEntry("blast_furnace_front_on", (InterpolatedIcon)blockFront);
			}
		} else {
			this.blockFront = p_149651_1_.registerIcon("blast_furnace_front");
		}
		this.blockTop = p_149651_1_.registerIcon("blast_furnace_top");
	}
	
	/**
	 * Update which block the furnace is using depending on whether or not it is burning
	 */
	public static void updateFurnaceBlockState(boolean p_149931_0_, World p_149931_1_, int p_149931_2_, int p_149931_3_, int p_149931_4_)
	{
		int l = p_149931_1_.getBlockMetadata(p_149931_2_, p_149931_3_, p_149931_4_);
		TileEntity tileentity = p_149931_1_.getTileEntity(p_149931_2_, p_149931_3_, p_149931_4_);
		field_149934_M = true;

		if (p_149931_0_)
		{
			p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, ModBlocks.LIT_BLAST_FURNACE.get());
		}
		else
		{
			p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, ModBlocks.BLAST_FURNACE.get());
		}

		field_149934_M = false;
		p_149931_1_.setBlockMetadataWithNotify(p_149931_2_, p_149931_3_, p_149931_4_, l, 2);

		if (tileentity != null)
		{
			tileentity.validate();
			p_149931_1_.setTileEntity(p_149931_2_, p_149931_3_, p_149931_4_, tileentity);
		}
	}
	
	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
	{
		Random random = new Random();
		if (!field_149934_M)
		{
			TileEntityBlastFurnace tileentityfurnace = (TileEntityBlastFurnace)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

			if (tileentityfurnace != null)
			{
				for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1)
				{
					ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

					if (itemstack != null)
					{
						float f = random.nextFloat() * 0.8F + 0.1F;
						float f1 = random.nextFloat() * 0.8F + 0.1F;
						float f2 = random.nextFloat() * 0.8F + 0.1F;

						while (itemstack.stackSize > 0)
						{
							int j1 = random.nextInt(21) + 10;

							if (j1 > itemstack.stackSize)
							{
								j1 = itemstack.stackSize;
							}

							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1, p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

							if (itemstack.hasTagCompound())
							{
								entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
							}

							float f3 = 0.05F;
							entityitem.motionX = (float)random.nextGaussian() * f3;
							entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float)random.nextGaussian() * f3;
							p_149749_1_.spawnEntityInWorld(entityitem);
						}
					}
				}

				p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
			}
		}
		p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return Item.getItemFromBlock(ModBlocks.BLAST_FURNACE.get());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
	{
		return Item.getItemFromBlock(ModBlocks.BLAST_FURNACE.get());
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityBlastFurnace();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		if (world.isRemote)
		{
			return true;
		}
		player.openGui(EtFuturum.instance, GUIsID.BLAST_FURNACE, world, x, y, z);
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return ConfigBlocksItems.enableBlastFurnace;
	}

}