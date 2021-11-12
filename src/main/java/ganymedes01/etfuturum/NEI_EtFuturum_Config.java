package ganymedes01.etfuturum;

import java.awt.Rectangle;
import java.util.Map;
import java.util.Map.Entry;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import ganymedes01.etfuturum.client.gui.inventory.GuiBlastFurnace;
import ganymedes01.etfuturum.client.gui.inventory.GuiSmoker;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.recipes.BlastFurnaceRecipes;
import ganymedes01.etfuturum.recipes.SmokerRecipes;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

/** DO NOT RENAME OR MOVE THIS FILE, IT HAS TO BE IN THIS LOCATION AND HAVE THIS NAME!!! (I wish that was not the case, but that is not my choice lol) */
public class NEI_EtFuturum_Config implements codechicken.nei.api.IConfigureNEI {
	@Override
	public void loadConfig() {
		FurnaceRecipeHandler
		// Smoker
		handler = new NEI_Recipes_Smoker();
		// make it possible to use the "R" Key
		GuiCraftingRecipe.craftinghandlers.add(handler);
		// make it possible to use the "U" Key
		GuiUsageRecipe.usagehandlers.add(handler);
		// Blast Furnace
		handler = new NEI_Recipes_BlastFurnace();
		// make it possible to use the "R" Key
		GuiCraftingRecipe.craftinghandlers.add(handler);
		// make it possible to use the "U" Key
		GuiUsageRecipe.usagehandlers.add(handler);
	}
	@Override
	public String getName() {
		return Reference.MOD_NAME + " NEI Plugin";
	}
	@Override
	public String getVersion() {
		return Reference.VERSION_NUMBER;
	}
	
	public static class NEI_Recipes_Smoker extends FurnaceRecipeHandler {
		public NEI_Recipes_Smoker() {}
		
		@Override
		public void loadTransferRects() {
			// Location of the "Recipes" Button on the Progress Bar of the Furnace.
			transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), getOverlayIdentifier()));
			// Location of the "Fuel" Button on the Fuel Bar of the Furnace.
			transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "fuel"));
		}
		@Override
		public void loadCraftingRecipes(String outputId, Object... results) {
			if (outputId.equals(getOverlayIdentifier())) {
				Map<ItemStack, ItemStack> recipes = SmokerRecipes.smelting().getSmeltingList();
				for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
					arecipes.add(new SmeltingPair(recipe.getKey(), recipe.getValue()));
			} else
				super.loadCraftingRecipes(outputId, results);
		}
		@Override
		public void loadCraftingRecipes(ItemStack result) {
			Map<ItemStack, ItemStack> recipes = SmokerRecipes.smelting().getSmeltingList();
			for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
				if (NEIServerUtils.areStacksSameType(recipe.getValue(), result))
					arecipes.add(new SmeltingPair(recipe.getKey(), recipe.getValue()));
		}
		@Override
		public void loadUsageRecipes(ItemStack ingredient) {
			Map<ItemStack, ItemStack> recipes = SmokerRecipes.smelting().getSmeltingList();
			for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
				if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
					SmeltingPair arecipe = new SmeltingPair(recipe.getKey(), recipe.getValue());
					//arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingred), ingredient);
					arecipes.add(arecipe);
				}
		}
		
		// TODO insert actually localized Name of the Smoker here, so that this works with Language Packs.
		@Override public String getRecipeName() {return "Smoker";}
		@Override public String getOverlayIdentifier() {return "etfuturum.smoker";}
		@Override public Class<? extends GuiContainer> getGuiClass() {return GuiSmoker.class;}
	}
	
	public static class NEI_Recipes_BlastFurnace extends FurnaceRecipeHandler {
		public NEI_Recipes_BlastFurnace() {}
		
		@Override
		public void loadTransferRects() {
			// Location of the "Recipes" Button on the Progress Bar of the Furnace.
			transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), getOverlayIdentifier()));
			// Location of the "Fuel" Button on the Fuel Bar of the Furnace.
			transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "fuel"));
		}
		@Override
		public void loadCraftingRecipes(String outputId, Object... results) {
			if (outputId.equals(getOverlayIdentifier())) {
				Map<ItemStack, ItemStack> recipes = BlastFurnaceRecipes.smelting().getSmeltingList();
				for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
					arecipes.add(new SmeltingPair(recipe.getKey(), recipe.getValue()));
			} else
				super.loadCraftingRecipes(outputId, results);
		}
		@Override
		public void loadCraftingRecipes(ItemStack result) {
			Map<ItemStack, ItemStack> recipes = BlastFurnaceRecipes.smelting().getSmeltingList();
			for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
				if (NEIServerUtils.areStacksSameType(recipe.getValue(), result))
					arecipes.add(new SmeltingPair(recipe.getKey(), recipe.getValue()));
		}
		@Override
		public void loadUsageRecipes(ItemStack ingredient) {
			Map<ItemStack, ItemStack> recipes = BlastFurnaceRecipes.smelting().getSmeltingList();
			for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
				if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
					SmeltingPair arecipe = new SmeltingPair(recipe.getKey(), recipe.getValue());
					//arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingred), ingredient);
					arecipes.add(arecipe);
				}
		}
		
		// TODO insert actually localized Name of the Blast Furnace here, so that this works with Language Packs.
		@Override public String getRecipeName() {return "Blast Furnace";}
		@Override public String getOverlayIdentifier() {return "etfuturum.blastfurnace";}
		@Override public Class<? extends GuiContainer> getGuiClass() {return GuiBlastFurnace.class;}
	}
}
