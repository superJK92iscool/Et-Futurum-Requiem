package ganymedes01.etfuturum.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.core.utils.Logger;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class SmithingTableRecipes {

    private static final SmithingTableRecipes instance = new SmithingTableRecipes();
    private List<SmithingTableRecipe> recipes = new ArrayList();

    public static final SmithingTableRecipes getInstance()
    {
        return instance;
    }
    
    //TODO: Add Netherite Plus support
    public static void init() {
    	SmithingTableRecipes.getInstance().addRecipe(Items.diamond_pickaxe, "ingotNetherite", new ItemStack(ModItems.netherite_pickaxe));
    	SmithingTableRecipes.getInstance().addRecipe(Items.diamond_axe, "ingotNetherite", new ItemStack(ModItems.netherite_axe));
    	SmithingTableRecipes.getInstance().addRecipe(Items.diamond_shovel, "ingotNetherite", new ItemStack(ModItems.netherite_spade));
    	SmithingTableRecipes.getInstance().addRecipe(Items.diamond_sword, "ingotNetherite", new ItemStack(ModItems.netherite_sword));
    	SmithingTableRecipes.getInstance().addRecipe(Items.diamond_hoe, "ingotNetherite", new ItemStack(ModItems.netherite_hoe));

    	SmithingTableRecipes.getInstance().addRecipe(Items.diamond_helmet, "ingotNetherite", new ItemStack(ModItems.netherite_helmet));
    	SmithingTableRecipes.getInstance().addRecipe(Items.diamond_chestplate, "ingotNetherite", new ItemStack(ModItems.netherite_chestplate));
    	SmithingTableRecipes.getInstance().addRecipe(Items.diamond_leggings, "ingotNetherite", new ItemStack(ModItems.netherite_leggings));
    	SmithingTableRecipes.getInstance().addRecipe(Items.diamond_boots, "ingotNetherite", new ItemStack(ModItems.netherite_boots));
    }
    
    public void addRecipe(Object toolSlot, Object materialSlot, ItemStack result)
    {
    	instance.recipes.add(new SmithingTableRecipe(toolSlot, materialSlot, result));
    }
    
    public void addRecipe(SmithingTableRecipe recipe)
    {
    	instance.recipes.add(recipe);
    }

    public ItemStack findMatchingRecipe(InventoryCrafting p_82787_1_, World p_82787_2_)
    {
		for (int j = 0; j < this.recipes.size(); ++j)
		{
			SmithingTableRecipe irecipe = (SmithingTableRecipe)this.recipes.get(j);

		    if (irecipe.matches(p_82787_1_, p_82787_2_))
		    {
		        return irecipe.getCraftingResult(p_82787_1_);
		    }
		}

		return null;
    }
    
    public class SmithingTableRecipe implements IRecipe {
        private int copySlot = 0;
        private Object input;
        private Object material;
        private ItemStack output;

		public SmithingTableRecipe(Object toolSlot, Object materialSlot, ItemStack result) {
			output = result;
			
			Object[] recipe = new Object[] {toolSlot, materialSlot};

	        for (int idx = 0; idx < 2; idx++)
	        {
	            Object in = recipe[idx];

	            if (in instanceof ItemStack)
	            {
	                recipe[idx] = ((ItemStack)in).copy();
	            }
	            else if (in instanceof Item)
	            {
	            	recipe[idx] = new ItemStack((Item)in);
	            }
	            else if (in instanceof Block)
	            {
	            	recipe[idx] = new ItemStack((Block)in, 1, OreDictionary.WILDCARD_VALUE);
	            }
	            else if (in instanceof String)
	            {
	            	recipe[idx] = OreDictionary.getOres((String)in);
	            }
	            else
	            {
	                String ret = "Invalid smithing table recipe: ";
	                for (Object tmp :  recipe)
	                {
	                    ret += tmp + ", ";
	                }
	                ret += output;
	                throw new RuntimeException(ret);
	            }
	        }
	        
	        input = recipe[0];
	        material = recipe[1];
		}

        /**
         * Which slot to copy NBT from?
         * -1: None
         *  0: Tool slot
         *  1: Material slot
         */
		public SmithingTableRecipe setCopySlot(int slot) {
			copySlot = Math.min(slot, 1);
			return this;
		}
		
		public int getCopySlot() {
			return copySlot;
		}
		
		//TODO: Check if vanilla copies the whole tag compound or just the name and enchantments.
	    @Override
	    public ItemStack getCraftingResult(InventoryCrafting var1) {
	    	ItemStack stack = output.copy();
	    	if(copySlot > -1) {
	    		ItemStack toCopy = var1.getStackInSlot(copySlot);
	    		if(toCopy.hasTagCompound()) {
    				stack.setTagCompound(toCopy.getTagCompound());
	    		}
	    	}
	    	return stack;
	    }

	    /**
	     * Returns the size of the recipe area
	     */
	    @Override
	    public int getRecipeSize(){ return 2; }

	    @Override
	    public ItemStack getRecipeOutput(){ return output; }

	    /**
	     * Used to check if a recipe matches current crafting inventory
	     */
	    @Override
	    public boolean matches(InventoryCrafting inv, World world)
	    {
			Object[] recipe = new Object[] {input, material};
	        for (int x = 0; x < 2; x++)
	        {
                Object target = x == 0 ? input : x == 1 ? material : null;

                if (x >= 0 && x < 2)
                {
                    target = recipe[x];
                }

                ItemStack slot = inv.getStackInSlot(x);

                if (target instanceof ItemStack)
                {
                    if (!OreDictionary.itemMatches((ItemStack)target, slot, false))
                    {
                        return false;
                    }
                }
                else if (target instanceof ArrayList)
                {
                    boolean matched = false;

                    Iterator<ItemStack> itr = ((ArrayList<ItemStack>)target).iterator();
                    while (itr.hasNext() && !matched)
                    {
                        matched = OreDictionary.itemMatches(itr.next(), slot, false);
                    }

                    if (!matched)
                    {
                        return false;
                    }
                }
                else if (target == null && slot != null)
                {
                    return false;
                }
	        }

	        return true;
	    }
    }
}
