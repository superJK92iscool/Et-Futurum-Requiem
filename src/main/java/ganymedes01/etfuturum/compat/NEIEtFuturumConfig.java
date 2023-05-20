package ganymedes01.etfuturum.compat;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.compat.nei.BannerPatternHandler;
import ganymedes01.etfuturum.compat.nei.BlastFurnaceRecipeHandler;
import ganymedes01.etfuturum.compat.nei.ComposterHandler;
import ganymedes01.etfuturum.compat.nei.SmokerRecipeHandler;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.item.ItemStack;

public class NEIEtFuturumConfig implements IConfigureNEI {

	private static final BlastFurnaceRecipeHandler BLAST_FURNACE_HANDLER = new BlastFurnaceRecipeHandler();
	private static final SmokerRecipeHandler SMOKER_HANDLER = new SmokerRecipeHandler();

	@Override
	public void loadConfig() {
		if (ConfigBlocksItems.enableBanners) {
			API.registerRecipeHandler(new BannerPatternHandler());
			API.registerUsageHandler(new BannerPatternHandler());
		}

		if (ConfigBlocksItems.enableBeetroot) {
			API.hideItem(new ItemStack(ModBlocks.BEETROOTS.get()));
		}
		
		if(ConfigBlocksItems.enableSmoker) {
			// Smoker
			FurnaceRecipeHandler handler = SMOKER_HANDLER;
			// make it possible to use the "R" Key
			GuiCraftingRecipe.craftinghandlers.add(handler);
			// make it possible to use the "U" Key
			GuiUsageRecipe.usagehandlers.add(handler);
		}

		if(ConfigBlocksItems.enableBlastFurnace) {
			// Blast Furnace
			FurnaceRecipeHandler handler = BLAST_FURNACE_HANDLER;
			// make it possible to use the "R" Key
			GuiCraftingRecipe.craftinghandlers.add(handler);
			// make it possible to use the "U" Key
			GuiUsageRecipe.usagehandlers.add(handler);
		}

		if(ConfigBlocksItems.enableComposter) {
			ComposterHandler handler = new ComposterHandler();
			GuiCraftingRecipe.craftinghandlers.add(handler);
			GuiUsageRecipe.usagehandlers.add(handler);
		}
	}

	public static void clearCaches() {
		BLAST_FURNACE_HANDLER.clearCache();
		SMOKER_HANDLER.clearCache();
	}

	@Override
	public String getName() {
		return Reference.MOD_NAME;
	}

	@Override
	public String getVersion() {
		return Reference.VERSION_NUMBER;
	}
}