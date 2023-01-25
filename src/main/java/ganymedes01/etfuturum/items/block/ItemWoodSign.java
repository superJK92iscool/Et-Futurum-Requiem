package ganymedes01.etfuturum.items.block;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.network.WoodSignOpenMessage;
import ganymedes01.etfuturum.tileentities.TileEntityWoodSign;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemWoodSign extends Item implements IConfigurable {

	private final int meta;
	public ItemWoodSign(int i)
	{
		meta = i - 1;
		setUnlocalizedName(Utils.getUnlocalisedName("item_sign_" + ModBlocks.woodTypes[i]));
		setTextureName("minecraft:" + ModBlocks.woodTypes[i] + "_sign");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		this.maxStackSize = Items.sign.getItemStackLimit(new ItemStack(Items.sign));
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		
		if (p_77648_7_ == 0)
		{
			return false;
		}
		else if (!p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_).getMaterial().isSolid())
		{
			return false;
		}
		else
		{
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

			if (!player.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, itemStack))
			{
				return false;
			}
			else if (!Blocks.standing_sign.canPlaceBlockAt(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_))
			{
				return false;
			}
			else if (p_77648_3_.isRemote)
			{
				return true;
			}
			else
			{
				Block block;
				if (p_77648_7_ == 1)
				{
					int i1 = MathHelper.floor_double((player.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 15;
					block = ModBlocks.signs[meta];
					p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, ModBlocks.signs[meta], i1, 3);
				}
				else
				{
					block = ModBlocks.wall_signs[meta];
					p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, ModBlocks.wall_signs[meta], p_77648_7_, 3);
				}
				
				//Disable the sound for continuity, so it doesn't play when the event-based player would not
                if(ConfigWorld.enableSilentPlaceSounds)p_77648_3_.playSoundEffect((double)((float)p_77648_4_ + 0.5F), (double)((float)p_77648_5_ + 0.5F), (double)((float)p_77648_6_ + 0.5F), block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);

				--itemStack.stackSize;
				TileEntityWoodSign tileentitysign = (TileEntityWoodSign)p_77648_3_.getTileEntity(p_77648_4_, p_77648_5_, p_77648_6_);

				if (tileentitysign != null)
				{
					if(player instanceof EntityPlayerMP) {
						tileentitysign.func_145912_a(player);
						EtFuturum.networkWrapper.sendTo(new WoodSignOpenMessage(tileentitysign, (byte) (meta + (p_77648_7_ != 1 ? ModBlocks.signs.length : 0))), (EntityPlayerMP) player);
					}
				}
				return true;
			}
		}
	}

	@Override
	public boolean isEnabled() {
//      if(meta == 5 && !EtFuturum.enableCrimsonBlocks)
//          return false;
//      if(meta == 6 && !EtFuturum.enableWarpedBlocks)
//          return false;
		return ConfigBlocksItems.enableSigns;
	}
}
