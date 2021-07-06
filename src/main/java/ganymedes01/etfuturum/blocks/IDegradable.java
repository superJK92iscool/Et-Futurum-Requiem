package ganymedes01.etfuturum.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public interface IDegradable {

	/**
	 * Returns an int relative to the meta values regular coppers use.
	 * Used to streamline the process of copper waxing, dewaxing and degrading.
	 * 
	 * Default copper blocks:
	 * 0:  Regular, No degredation
	 * 1:  Regular, Exposed
	 * 2:  Regular, Weathered
	 * 3:  Regular, Oxidized
	 * 4:  Cut, No degredation
	 * 5:  Cut, Exposed
	 * 6:  Cut, Weathered
	 * 7:  Cut, Oxidized
	 * 8:  Regular Waxed, No degredation
	 * 9:  Regular Waxed, Exposed
	 * 10: Regular Waxed, Weathered
	 * 11: Regular Waxed, Oxidized
	 * 12: Cut Waxed, No degredation
	 * 13: Cut Waxed, Exposed
	 * 14: Cut Waxed, Weathered
	 * 15: Cut Waxed, Oxidized
	 * 
	 * @param meta World meta
	 * @return A copper meta value from above.
	 */
	public int getCopperMeta(int meta);
	
	/**
	 * A getCopperMeta copy that takes the actual meta value of the block into account.
	 * Used by slabs to wrap the meta data from 0-7 or 8-15.
	 * 
	 * @param meta Copper meta
	 * @param worldMeta World meta (Is always the meta value from before the copper changed and is the actual block meta, not the copper ID)
	 * @return A copper value from getCopperMeta.
	 */
	public default int getFinalCopperMeta(int meta, int worldMeta) {
		return getCopperMeta(meta);
	}
	
	
	/**
	 * Returns a block ID depending on the meta input.
	 * Used for copper stairs to return the blocks, since they're different blocks and not meta.
	 * 
	 * @param meta
	 * @return
	 */
	public Block getCopperBlockFromMeta(int meta);

	default void tickDegradation(World world, int x, int y, int z, Random random) {
		float f = 0.05688889F;
		if (random.nextFloat() < f) {
			this.tryDegrade(world, x, y, z, random);
		}
	}
	default void tryDegrade(World world, int x, int y, int z, Random random) {
		   int i = getCopperMeta(world.getBlockMetadata(x, y, z));
		   int j = 0;
		   int k = 0;
		   
		   if(i < 7 && i % 4 != 3) {
			   for(int x1 = -4; x1 <= 4; x1++) {
				   for(int y1 = -4; y1 <= 4; y1++) {
					   for(int z1 = -4; z1 <= 4; z1++) {
						   Block block = world.getBlock(x1 + x, y1 + y, z1 + z);
						   if(block instanceof IDegradable && (x1 != 0 || y1 != 0 || z1 != 0) && Math.abs(x1) + Math.abs(y1) + Math.abs(z1) <= 4) {
							   int m = ((IDegradable)block).getCopperMeta(world.getBlockMetadata(x1 + x, y1 + y, z1 + z));
							   
							   if(m > 7) //TODO: Are fully oxidized blocks counted?
								   continue;
							   
							   m %= 4;
							   
							   if (m < i % 4) {
								  return;
							   }
					  
							   if (m > i % 4) {
								  ++k;
							   } else {
								  ++j;
							   }
						   }
					   }
				   }
			   }

			   float f = (float)(k + 1) / (float)(k + j + 1);
			   float g = f * f * (i % 4 == 0 ? 0.75F : 1F);
			   if (random.nextFloat() < g) {
				   Block block = getCopperBlockFromMeta(i + 1);
				   world.setBlock(x, y, z, block, block instanceof BlockStairs ? world.getBlockMetadata(x, y, z) : getFinalCopperMeta(i + 1, world.getBlockMetadata(x, y, z)), 2);
			   }
		   }
	}
	
	default boolean tryWaxOnWaxOff(World world, int x, int y, int z, EntityPlayer entityPlayer) {
		boolean flag = false;
		boolean flag2 = false;
		int meta = getCopperMeta(world.getBlockMetadata(x, y, z));
		if(entityPlayer.getCurrentEquippedItem() != null) {
			ItemStack heldStack = entityPlayer.getCurrentEquippedItem();
			if(meta < 8) {
				for(int oreID : OreDictionary.getOreIDs(heldStack)) {
					if((OreDictionary.doesOreNameExist("materialWax") || OreDictionary.doesOreNameExist("materialWaxcomb")) ?
							OreDictionary.getOreName(oreID).equals("materialWax") || OreDictionary.getOreName(oreID).equals("materialWaxcomb") :
								OreDictionary.getOreName(oreID).equals("slimeball")) {
						flag = true;
						
						if (!entityPlayer.capabilities.isCreativeMode && --heldStack.stackSize <= 0)
						{
							entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, (ItemStack)null);
						}
						
						entityPlayer.inventoryContainer.detectAndSendChanges();
						break;
					}
				}
			}
			if(heldStack.getItem().getToolClasses(heldStack).contains("axe") && (meta % 4 != 0 || meta > 7)) {
				heldStack.damageItem(1, entityPlayer);
				if(meta < 8) {
					flag2 = true;
				} else {
					flag = true;
				}
			}
			int waxMeta;
			Block block;
			if(flag && !flag2) {
				waxMeta = meta > 7 ? meta % 8 : (meta % 8 + 8);
				block = getCopperBlockFromMeta(waxMeta);
				world.setBlock(x, y, z, block, block instanceof BlockStairs ? world.getBlockMetadata(x, y, z) : getFinalCopperMeta(waxMeta, world.getBlockMetadata(x, y, z)), 3);
				spawnParticles(world, x, y, z, false);
			} else if (!flag && flag2) {
				block = getCopperBlockFromMeta(meta - 1);
				world.setBlock(x, y, z, block, block instanceof BlockStairs ? world.getBlockMetadata(x, y, z) : getFinalCopperMeta(meta - 1, world.getBlockMetadata(x, y, z)), 3);
				spawnParticles(world, x, y, z, true);
			}
		}
		return flag || flag2;
	}
	
	default void spawnParticles(World p_150186_1_, int p_150186_2_, int p_150186_3_, int p_150186_4_, boolean oxidize)
	{
		Random random = p_150186_1_.rand;
		double d0 = 0.0625D;

		for (int l = 0; l < 10; ++l)
		{
			double d1 = p_150186_2_ + random.nextFloat();
			double d2 = p_150186_3_ + random.nextFloat();
			double d3 = p_150186_4_ + random.nextFloat();

			if (l == 0 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_ + 1, p_150186_4_).isOpaqueCube())
			{
				d2 = p_150186_3_ + 1 + d0;
			}

			if (l == 1 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_ - 1, p_150186_4_).isOpaqueCube())
			{
				d2 = p_150186_3_ + 0 - d0;
			}

			if (l == 2 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ + 1).isOpaqueCube())
			{
				d3 = p_150186_4_ + 1 + d0;
			}

			if (l == 3 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ - 1).isOpaqueCube())
			{
				d3 = p_150186_4_ + 0 - d0;
			}

			if (l == 4 && !p_150186_1_.getBlock(p_150186_2_ + 1, p_150186_3_, p_150186_4_).isOpaqueCube())
			{
				d1 = p_150186_2_ + 1 + d0;
			}

			if (l == 5 && !p_150186_1_.getBlock(p_150186_2_ - 1, p_150186_3_, p_150186_4_).isOpaqueCube())
			{
				d1 = p_150186_2_ + 0 - d0;
			}

			if (d1 < p_150186_2_ || d1 > p_150186_2_ + 1 || d2 < 0.0D || d2 > p_150186_3_ + 1 || d3 < p_150186_4_ || d3 > p_150186_4_ + 1)
			{
				p_150186_1_.spawnParticle("fireworksSpark", d1, d2, d3, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}
