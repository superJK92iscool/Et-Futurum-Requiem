package ganymedes01.etfuturum.recipes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class SmokerRecipes
{
    private static final SmokerRecipes smeltingBase = new SmokerRecipes();
    /** The list of smelting results. */
    private Map smeltingList = new HashMap();
    private Map experienceList = new HashMap();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static SmokerRecipes smelting()
    {
        return smeltingBase;
    }
    
    private SmokerRecipes() {
    }

    public static void init() {
    	if(ConfigurationHandler.enableAutoAddSmoker) {
        	Map recipes = FurnaceRecipes.smelting().getSmeltingList();
        	ItemStack input;
        	ItemStack result;
        	float exp;
        	Entry entry;
        	Iterator iterator = recipes.entrySet().iterator();
        	while (iterator.hasNext()) {
        		entry = (Entry)iterator.next();
        		input = (ItemStack)entry.getKey();
    			result = FurnaceRecipes.smelting().getSmeltingResult((ItemStack)entry.getKey());
        		if(input.getItem() instanceof ItemFood) {
        			smeltingBase.addRecipe(input, result, result.getItem().getSmeltingExperience(result));
        		}
        	}
    	} else {
            smeltingBase.addRecipe(Items.porkchop, new ItemStack(Items.cooked_porkchop), 0.35F);
            smeltingBase.addRecipe(Items.beef, new ItemStack(Items.cooked_beef), 0.35F);
            smeltingBase.addRecipe(Items.chicken, new ItemStack(Items.cooked_chicken), 0.35F);
            smeltingBase.addRecipe(Items.potato, new ItemStack(Items.baked_potato), 0.35F);
            smeltingBase.addRecipe(ModItems.raw_mutton, new ItemStack(ModItems.cooked_mutton), 0.35F);
            ItemFishFood.FishType[] afishtype = ItemFishFood.FishType.values();
            int i = afishtype.length;

            for (int j = 0; j < i; ++j)
            {
                ItemFishFood.FishType fishtype = afishtype[j];

                if (fishtype.func_150973_i())
                {
                    smeltingBase.addRecipe(new ItemStack(Items.fish, 1, fishtype.func_150976_a()), new ItemStack(Items.cooked_fished, 1, fishtype.func_150976_a()), 0.35F);
                }
            }
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
