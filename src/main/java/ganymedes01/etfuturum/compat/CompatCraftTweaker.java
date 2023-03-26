package ganymedes01.etfuturum.compat;

import cpw.mods.fml.relauncher.ReflectionHelper;
import ganymedes01.etfuturum.compat.cthandlers.CTBlastFurnace;
import ganymedes01.etfuturum.compat.cthandlers.CTSmoker;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.recipes.BlastFurnaceRecipes;
import ganymedes01.etfuturum.recipes.SmokerRecipes;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IngredientStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.util.IEventHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class CompatCraftTweaker {

    public static void onPostInit() {
        MineTweakerAPI.registerClass(CTBlastFurnace.class);
        MineTweakerAPI.registerClass(CTSmoker.class);
    }

    public static Object toObject(IIngredient iStack) {
        if (iStack == null)
            return null;
        else {
            if (iStack instanceof IOreDictEntry)
                return ((IOreDictEntry) iStack).getName();
            else if (iStack instanceof IItemStack)
                return minetweaker.api.minecraft.MineTweakerMC.getItemStack((IItemStack) iStack);
            else if (iStack instanceof IngredientStack) {
                IIngredient ingr = ReflectionHelper.getPrivateValue(IngredientStack.class, (IngredientStack) iStack, "ingredient");
                return toObject(ingr);
            } else
                return null;
        }
    }

    public static FluidStack toFluidStack(ILiquidStack iStack) {
        return minetweaker.api.minecraft.MineTweakerMC.getLiquidStack(iStack);
    }

    public static ItemStack getItemStack(IItemStack item) {
        return minetweaker.api.minecraft.MineTweakerMC.getItemStack(item);
    }

    public static ItemStack[] getItemStacks(List<IItemStack> item) {
        return minetweaker.api.minecraft.MineTweakerMC.getItemStacks(item);
    }
}
