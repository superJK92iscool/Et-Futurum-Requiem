package ganymedes01.etfuturum.compat.cthandlers;

import ganymedes01.etfuturum.compat.CompatCraftTweaker;
import ganymedes01.etfuturum.recipes.BlastFurnaceRecipes;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.recipes.FurnaceRecipe;
import minetweaker.api.recipes.IFurnaceRecipe;
import minetweaker.mc1710.furnace.MCFurnaceManager;
import minetweaker.mc1710.item.MCItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.*;

/** Remember blast furnace recipes are output, THEN input, not input then output
 * mods.etfuturum.blastFurnace.addRecipe(<minecraft:gold_block>, <minecraft:iron_block>, 4.0D);
 * mods.etfuturum.blastFurnace.remove(<minecraft:gold_ore>, <minecraft:gold_ingot>);
 */

@ZenClass("mods.etfuturum.blastFurnace")
public class CTBlastFurnace {

    @ZenMethod
    public static void remove(IIngredient output, IIngredient input) {
        if (output == null) throw new IllegalArgumentException("output cannot be null");

        Map<ItemStack, ItemStack> smeltingList = BlastFurnaceRecipes.smelting().getSmeltingList();

        List<ItemStack> toRemove = new ArrayList<ItemStack>();
        List<ItemStack> toRemoveValues = new ArrayList<ItemStack>();
        for (Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
            if (output.matches(new MCItemStack(entry.getValue()))
                    && (input == null || input.matches(new MCItemStack(entry.getKey())))) {
                toRemove.add(entry.getKey());
                toRemoveValues.add(entry.getValue());
            }
        }

        if (toRemove.isEmpty()) {
            MineTweakerAPI.logWarning("No blast furnace recipes for " + output.toString());
        } else {
            MineTweakerAPI.apply(new RemoveAction(toRemove, toRemoveValues));
        }
    }

    @ZenMethod
    public static void remove(IIngredient output) {
        remove(output, null);
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input) {
        addRecipe(output, input, 0);
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, double xp) {
        List<IItemStack> items = input.getItems();
        if (items == null) {
            MineTweakerAPI.logError("Cannot turn " + input.toString() + " into a blast furnace recipe");
        }

        ItemStack[] items2 = CompatCraftTweaker.getItemStacks(items);
        ItemStack output2 = CompatCraftTweaker.getItemStack(output);
        MineTweakerAPI.apply(new AddRecipeAction(input, items2, output2, xp));
    }

    public List<IFurnaceRecipe> getAll() {
        List<IFurnaceRecipe> retList = new ArrayList<IFurnaceRecipe>();
        for (Map.Entry<ItemStack, ItemStack> ent : (Set<Map.Entry<ItemStack, ItemStack>>) BlastFurnaceRecipes.smelting().getSmeltingList().entrySet()) {
            retList.add(
                    new FurnaceRecipe(
                            new MCItemStack(ent.getKey()),
                            new MCItemStack(ent.getValue()),
                            BlastFurnaceRecipes.smelting().func_151398_b(ent.getValue())));
        }
        return retList;
    }

    // ######################
    // ### Action classes ###
    // ######################

    private static class RemoveAction implements IUndoableAction {

        private final List<ItemStack> items;
        private final List<ItemStack> values;

        public RemoveAction(List<ItemStack> items, List<ItemStack> values) {
            this.items = items;
            this.values = values;
        }

        @Override
        public void apply() {
            for (ItemStack item : items) {
                BlastFurnaceRecipes.smelting().getSmeltingList().remove(item);
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (int i = 0; i < items.size(); i++) {
                BlastFurnaceRecipes.smelting().getSmeltingList().put(items.get(i), values.get(i));
            }
        }

        @Override
        public String describe() {
            return "Removing " + items.size() + " blast furnace recipes";
        }

        @Override
        public String describeUndo() {
            return "Restoring " + items.size() + " blast furnace recipes";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class AddRecipeAction implements IUndoableAction {

        private final IIngredient ingredient;
        private final ItemStack[] input;
        private final ItemStack output;
        private final double xp;

        public AddRecipeAction(IIngredient ingredient, ItemStack[] input, ItemStack output, double xp) {
            this.ingredient = ingredient;
            this.input = input;
            this.output = output;
            this.xp = xp;
        }

        @Override
        public void apply() {
            for (ItemStack inputStack : input) {
                BlastFurnaceRecipes.smelting().addRecipe(inputStack, output, (float) xp);
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (ItemStack inputStack : input) {
                BlastFurnaceRecipes.smelting().getSmeltingList().remove(inputStack);
            }
        }

        @Override
        public String describe() {
            return "Adding blast furnace recipe for " + ingredient;
        }

        @Override
        public String describeUndo() {
            return "Removing blast furnace recipe for " + ingredient;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
