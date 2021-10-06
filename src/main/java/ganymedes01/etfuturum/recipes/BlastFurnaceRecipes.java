package ganymedes01.etfuturum.recipes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
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
	public Map<ItemStack, ItemStack> smeltingList = new HashMap<ItemStack, ItemStack>();
	public Map<ItemStack, Float> experienceList = new HashMap<ItemStack, Float>();

	/**
	 * Used to call methods addSmelting and getSmeltingResult.
	 */
	public static BlastFurnaceRecipes smelting()
	{
		return smeltingBase;
	}
	
	private BlastFurnaceRecipes() {
	}

	@SuppressWarnings("unchecked")
	public static void init() {
		if(ConfigFunctions.enableMeltGear) {
			Item[][] crafts = new Item[][] {
				{Items.golden_helmet, Items.golden_chestplate, Items.golden_leggings, Items.golden_boots, Items.golden_sword, Items.golden_pickaxe,
					Items.golden_axe, Items.golden_shovel, Items.golden_hoe, Items.golden_horse_armor,
				Items.iron_helmet, Items.iron_chestplate, Items.iron_leggings, Items.iron_boots, Items.iron_sword, Items.iron_pickaxe,
					Items.iron_axe, Items.iron_shovel, Items.iron_hoe, Items.iron_horse_armor},
				{Items.gold_nugget, ModItems.iron_nugget}};
			for(int i = 0; i < crafts[0].length; i++) {
				if(!ConfigBlocksItems.enableIronNugget && i > 9)
					break;
				smeltingBase.addRecipe(crafts[0][i], new ItemStack(crafts[1][i > 9 ? 1 : 0]), .1F);
			}
		}
		if(ConfigFunctions.enableAutoAddBlastFurnace) {
			Iterator<Entry<ItemStack, ItemStack>> iterator = FurnaceRecipes.smelting().getSmeltingList().entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<ItemStack, ItemStack> entry = iterator.next();
				ItemStack input = entry.getKey(), result = entry.getValue();
				if (input != null && result != null) {
					boolean registerrecipe = false;
					for (int id : OreDictionary.getOreIDs(input)) {
						String oreName = OreDictionary.getOreName(id);
						// It should not be possible for this to be null, but better safe than sorry...
						if (oreName == null) continue;
						// Any Type of Ores.
						if (oreName.startsWith("ore")) {registerrecipe = true; break;}
						// Thaumcraft Ore Cluster Items.
						if (oreName.startsWith("cluster")) {registerrecipe = true; break;}
						// IndustrialCraft2 Crushed Ore Items.
						if (oreName.startsWith("crushed")) {registerrecipe = true; break;}
					}
					if (!registerrecipe) {
						for (int id : OreDictionary.getOreIDs(result)) {
							String oreName = OreDictionary.getOreName(id);
							// It should not be possible for this to be null, but better safe than sorry...
							if (oreName == null) continue;
							// Outputs Ingots.
							if (oreName.startsWith("ingot")) {registerrecipe = true; break;}
							// Outputs GregTechs Quarter-of-Ingot Nuggets.
							if (oreName.startsWith("chunkGt")) {registerrecipe = true; break;}
							// Outputs normal Nuggets.
							if (oreName.startsWith("nugget")) {registerrecipe = true; break;}
							// Outputs Thaumcrafts Quicksilver Item, which weirdly enough is not registered as an Ingot, while still having Nuggets.
							if (oreName.equalsIgnoreCase("quicksilver")) {registerrecipe = true; break;}
						}
					}
					if (registerrecipe) {
						smeltingBase.addRecipe(input, result, result.getItem().getSmeltingExperience(result));
					}
				}
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
		Iterator<Entry<ItemStack, ItemStack>> iterator = this.smeltingList.entrySet().iterator();
		Entry<ItemStack, ItemStack> entry;

		do
		{
			if (!iterator.hasNext())
			{
				return null;
			}

			entry = iterator.next();
		}
		while (!this.func_151397_a(p_151395_1_, entry.getKey()));

		return entry.getValue();
	}

	private boolean func_151397_a(ItemStack p_151397_1_, ItemStack p_151397_2_)
	{
		return p_151397_2_.getItem() == p_151397_1_.getItem() && (p_151397_2_.getItemDamage() == 32767 || p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage());
	}

	public Map<ItemStack, ItemStack> getSmeltingList()
	{
		return this.smeltingList;
	}

	public float func_151398_b(ItemStack p_151398_1_)
	{
		float ret = p_151398_1_.getItem().getSmeltingExperience(p_151398_1_);
		if (ret != -1) return ret;

		Iterator<Entry<ItemStack, Float>> iterator = this.experienceList.entrySet().iterator();
		Entry<ItemStack, Float> entry;

		do
		{
			if (!iterator.hasNext())
			{
				return 0.0F;
			}

			entry = iterator.next();
		}
		while (!this.func_151397_a(p_151398_1_, entry.getKey()));

		return entry.getValue().floatValue();
	}
}
