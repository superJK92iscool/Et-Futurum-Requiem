package ganymedes01.etfuturum.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

public class BlastFurnaceRecipes
{
    private static final BlastFurnaceRecipes smeltingBase = new BlastFurnaceRecipes();
    /** The list of smelting results. */
    private Map smeltingList = new HashMap();
    private Map experienceList = new HashMap();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static BlastFurnaceRecipes smelting()
    {
        return smeltingBase;
    }
    
    private BlastFurnaceRecipes() {
    }

    public static void init() {
		if(ConfigurationHandler.enableMeltGear) {
			Item[][] crafts = new Item[][] {
				{Items.golden_helmet, Items.golden_chestplate, Items.golden_leggings, Items.golden_boots, Items.golden_sword, Items.golden_pickaxe,
					Items.golden_axe, Items.golden_shovel, Items.golden_hoe, Items.golden_horse_armor,
				Items.iron_helmet, Items.iron_chestplate, Items.iron_leggings, Items.iron_boots, Items.iron_sword, Items.iron_pickaxe,
					Items.iron_axe, Items.iron_shovel, Items.iron_hoe, Items.iron_horse_armor},
				{Items.gold_nugget, ModItems.iron_nugget}};
			for(int i = 0; i < crafts[0].length; i++) {
				if(!ConfigurationHandler.enableIronNugget && i > 9)
					break;
				smeltingBase.addRecipe(crafts[0][i], new ItemStack(crafts[1][i > 9 ? 1 : 0]), .1F);
			}
		}
    	if(ConfigurationHandler.enableAutoAddBlastFurnace) {
    		String[] oreNames = OreDictionary.getOreNames();
        	List<ItemStack> oresToSmelt = new ArrayList();
    		int i = 0;
    		while(i < oreNames.length) {
    			if(oreNames[i].startsWith("ore")) {
    				ArrayList<ItemStack> oreItems = OreDictionary.getOres(oreNames[i]);
    				int o = 0;
    				while(o < oreItems.size()) {
    					ItemStack input = oreItems.get(o);
    					if(input != null) {
        					ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(input);
        					if(result != null)
        	        			smeltingBase.addRecipe(input, result, result.getItem().getSmeltingExperience(result));
    					}
    					o++;
    				}
    			}
    			i++;
    		}
        } else {
        	smeltingBase.addRecipe(Blocks.iron_ore, new ItemStack(Items.iron_ingot), 0.7F);
        	smeltingBase.addRecipe(Blocks.gold_ore, new ItemStack(Items.gold_ingot), 1.0F);
        	smeltingBase.addRecipe(Blocks.diamond_ore, new ItemStack(Items.diamond), 1.0F);
        	smeltingBase.addRecipe(Blocks.emerald_ore, new ItemStack(Items.emerald), 1.0F);
        	smeltingBase.addRecipe(Blocks.coal_ore, new ItemStack(Items.coal), 0.1F);
        	smeltingBase.addRecipe(Blocks.redstone_ore, new ItemStack(Items.redstone), 0.7F);
        	smeltingBase.addRecipe(Blocks.lapis_ore, new ItemStack(Items.dye, 1, 4), 0.2F);
        	smeltingBase.addRecipe(Blocks.quartz_ore, new ItemStack(Items.quartz), 0.2F);
        }
    }

    public void addRecipe(Block p_151393_1_, ItemStack p_151393_2_, float p_151393_3_)
    {
        this.addRecipe(Item.getItemFromBlock(p_151393_1_), p_151393_2_, p_151393_3_);
    }

    public void addRecipe(Item p_151396_1_, ItemStack p_151396_2_, float p_151396_3_)
    {
        this.addRecipe(new ItemStack(p_151396_1_, 1, 32767), p_151396_2_, p_151396_3_);
    }

    public void addRecipe(ItemStack p_151394_1_, ItemStack p_151394_2_, float p_151394_3_)
    {
        this.smeltingList.put(p_151394_1_, p_151394_2_);
        this.experienceList.put(p_151394_2_, Float.valueOf(p_151394_3_));
    }

    /**
     * Returns the smelting result of an item.
     */
    public ItemStack getSmeltingResult(ItemStack p_151395_1_)
    {
        Iterator iterator = this.smeltingList.entrySet().iterator();
        Entry entry;

        do
        {
            if (!iterator.hasNext())
            {
                return null;
            }

            entry = (Entry)iterator.next();
        }
        while (!this.func_151397_a(p_151395_1_, (ItemStack)entry.getKey()));

        return (ItemStack)entry.getValue();
    }

    private boolean func_151397_a(ItemStack p_151397_1_, ItemStack p_151397_2_)
    {
        return p_151397_2_.getItem() == p_151397_1_.getItem() && (p_151397_2_.getItemDamage() == 32767 || p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage());
    }

    public Map getSmeltingList()
    {
        return this.smeltingList;
    }

    public float func_151398_b(ItemStack p_151398_1_)
    {
        float ret = p_151398_1_.getItem().getSmeltingExperience(p_151398_1_);
        if (ret != -1) return ret;

        Iterator iterator = this.experienceList.entrySet().iterator();
        Entry entry;

        do
        {
            if (!iterator.hasNext())
            {
                return 0.0F;
            }

            entry = (Entry)iterator.next();
        }
        while (!this.func_151397_a(p_151398_1_, (ItemStack)entry.getKey()));

        return ((Float)entry.getValue()).floatValue();
    }
}
