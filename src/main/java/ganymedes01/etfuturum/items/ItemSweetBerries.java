package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BlockBerryBush;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSweetBerries extends ItemFood implements IConfigurable {

	public ItemSweetBerries() {
		super(2, false);
		setTextureName("sweet_berries");
		setUnlocalizedName(Utils.getUnlocalisedName("sweet_berries"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableSweetBerryBushes;
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. Copied from ItemBlock
	 */
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);

		if (block == Blocks.snow_layer && (p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_) & 7) < 1)
		{
			p_77648_7_ = 1;
		}
		
		else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_))
		{
			if (p_77648_7_ == 0)
			{
				--p_77648_5_;
			}

			if (p_77648_7_ == 1)
			{
				++p_77648_5_;
			}

			if (p_77648_7_ == 2)
			{
				--p_77648_6_;
			}

			if (p_77648_7_ == 3)
			{
				++p_77648_6_;
			}

			if (p_77648_7_ == 4)
			{
				--p_77648_4_;
			}

			if (p_77648_7_ == 5)
			{
				++p_77648_4_;
			}
		}

		if (p_77648_1_.stackSize == 0)
		{
			return false;
		}
		else if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_))
		{
			return false;
		}
		else if (p_77648_3_.canPlaceEntityOnSide(ModBlocks.sweet_berry_bush, p_77648_4_, p_77648_5_, p_77648_6_, false, p_77648_7_, p_77648_2_, p_77648_1_))
		{
			int j1 = ModBlocks.sweet_berry_bush.onBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, 0);

			if (placeBlockAt(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, j1))
			{
				p_77648_3_.playSoundEffect((double)((float)p_77648_4_ + 0.5F), (double)((float)p_77648_5_ + 0.5F), (double)((float)p_77648_6_ + 0.5F), ModBlocks.sweet_berry_bush.stepSound.func_150496_b(), (ModBlocks.sweet_berry_bush.stepSound.getVolume() + 1.0F) / 2.0F, ModBlocks.sweet_berry_bush.stepSound.getPitch() * 0.8F);
				--p_77648_1_.stackSize;
			}

			return true;
		}
		else
		{
			return super.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
		}
	}

	/**
	 * Called to actually place the block, after the location is determined
	 * and all permission checks have been made.
	 * 
	 * Copied from ItemBlock
	 *
	 * @param stack The item stack that was used to place the block. This can be changed inside the method.
	 * @param player The player who is placing the block. Can be null if the block is not being placed by a player.
	 * @param side The side the player (or machine) right-clicked on.
	 */
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{

	   if (!world.setBlock(x, y, z, ModBlocks.sweet_berry_bush, metadata, 3))
	   {
		   return false;
	   }

	   if (world.getBlock(x, y, z) == ModBlocks.sweet_berry_bush)
	   {
		   ModBlocks.sweet_berry_bush.onBlockPlacedBy(world, x, y, z, player, stack);
		   ModBlocks.sweet_berry_bush.onPostBlockPlaced(world, x, y, z, metadata);
	   }

	   return true;
	}
}
