package ganymedes01.etfuturum.blocks;

import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.recipes.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockCutCopperStairs extends BlockGenericStairs implements IDegradable {

	public BlockCutCopperStairs(int p_i45428_2_) {
		super(ModBlocks.copper_block, p_i45428_2_);
		String name = "cut_copper_stairs";
		String subtype = "";
		switch(meta) {
			case 5: subtype = "exposed"; break;
			case 6: subtype = "weathered"; break;
			case 7: subtype = "oxidized"; break;
			case 12: subtype = "waxed"; break;
			case 13: subtype = "waxed_exposed"; break;
			case 14: subtype = "waxed_weathered"; break;
		}
		setBlockName(Utils.getUnlocalisedName(subtype + (subtype.equals("") ? "" : "_") + name));
		setTickRandomly(meta < 7 ? true : false);
	}
	
	public Block getWeatherStage(int i) {
		switch(i) {
		case 4: return ModBlocks.cut_copper_stairs;
		case 5: return ModBlocks.exposed_cut_copper_stairs;
		case 6: return ModBlocks.weathered_cut_copper_stairs;
		case 7: return ModBlocks.oxidized_cut_copper_stairs;
		default: return null;
		}
	}

	public ImmutablePair getWax() {
		if(this == ModBlocks.cut_copper_stairs || this == ModBlocks.waxed_cut_copper_stairs)
			return new ImmutablePair(ModBlocks.cut_copper_stairs, ModBlocks.waxed_cut_copper_stairs);
		if(this == ModBlocks.exposed_cut_copper_stairs || this == ModBlocks.waxed_exposed_cut_copper_stairs)
			return new ImmutablePair(ModBlocks.exposed_cut_copper_stairs, ModBlocks.waxed_exposed_cut_copper_stairs);
		if(this == ModBlocks.weathered_cut_copper_stairs || this == ModBlocks.waxed_weathered_cut_copper_stairs)
			return new ImmutablePair(ModBlocks.weathered_cut_copper_stairs, ModBlocks.waxed_weathered_cut_copper_stairs);
		return null;
	}
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isRemote)
            return;
        if (getDegredationState(0) == -1)
        	return;
        tickDegradation(world, x, y, z, rand);
    }

    private void tickDegradation(World world, int x, int y, int z, Random random) {
    	float f = 0.05688889F;
    	if (random.nextFloat() < f) {
    		this.tryDegrade(world, x, y, z, random);
    	}
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
    	boolean flag = false;
    	boolean flag2 = false;
    	if(entityPlayer.getCurrentEquippedItem() != null) {
    		ItemStack heldStack = entityPlayer.getCurrentEquippedItem();
    		if(meta <= 7 && getWax() != null) {
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
    		if(heldStack.getItem().getToolClasses(heldStack).contains("axe") && (getWeatherStage(meta - 1) != null || meta > 7)) {
    			if(meta > 0)
                	heldStack.damageItem(1, entityPlayer);
            	if(meta <= 7) {
            		flag2 = true;
            	} else {
            		flag = true;
            	}
    		}
    		if(flag && !flag2) {
        		Block block = (Block)(meta <= 7 ? getWax().getRight() : getWax().getLeft());
        		world.setBlock(x, y, z, block, world.getBlockMetadata(x, y, z), 2);
        		BlockCopper.spawnParticles(world, x, y, z, false);
    		} else if (!flag && flag2) {
        		Block block = getWeatherStage(meta - 1);
        		world.setBlock(x, y, z, block, world.getBlockMetadata(x, y, z), 2);
        		BlockCopper.spawnParticles(world, x, y, z, true);
    		}
     	}
        return flag || flag2;
    }
    
	private void tryDegrade(World world, int x, int y, int z, Random random) {
	   int i = getDegredationState(0); //Different since stairs are blocks and not meta variations. Using 0 since the state checker uses the block instance.
	   int j = 0;
	   int k = 0;
	   
	   for(int x1 = -4; x1 <= 4; x1++) {
	       for(int y1 = -4; y1 <= 4; y1++) {
	           for(int z1 = -4; z1 <= 4; z1++) {
	        	   Block block = world.getBlock(x1 + x, y1 + y, z1 + z);
	               if(block instanceof IDegradable && (x1 != 0 || y1 != 0 || z1 != 0) && Math.abs(x1) + Math.abs(y1) + Math.abs(z1) <= 4) {
	            	   int m = ((IDegradable)block).getDegredationState(0);
	                   if(m == -1)
	                	   continue;
	                   
	                   if (m < i) {
	                      return;
	                   }
	          
	                   if (m > i) {
	                      ++k;
	                   } else {
	                      ++j;
	                   }
	               }
	           }
	       }
	   }

       float f = (float)(k + 1) / (float)(k + j + 1);
       float g = f * f * (i == 0 ? 0.75F : 1F);
       if (random.nextFloat() < g) {
          world.setBlock(x, y, z, getWeatherStage(meta + 1), world.getBlockMetadata(x, y, z), 2);
       }
    }

	@Override
	public int getDegredationState(int meta) {
		return this == ModBlocks.cut_copper_stairs ? 0 : this == ModBlocks.exposed_cut_copper_stairs ? 1 : this == ModBlocks.weathered_cut_copper_stairs ? 2 : this == ModBlocks.oxidized_cut_copper_stairs ? 3 : -1;
	}
}
