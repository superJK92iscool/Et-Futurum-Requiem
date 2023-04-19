package ganymedes01.etfuturum.compat.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ganymedes01.etfuturum.EtFuturumLootTables;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.CompostingRegistry;
import ganymedes01.etfuturum.core.utils.ItemStackMap;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static codechicken.lib.gui.GuiDraw.*;

public class ComposterHandler extends TemplateRecipeHandler implements ICraftingHandler, IUsageHandler {
    public final PositionedStack compooster = new PositionedStack(new ItemStack(ModBlocks.composter), 75, 80);

    public class CompostedRecipe extends CachedRecipe {
        public final ItemStackMap<TableProps> props;

        public CompostedRecipe() {
            List<ItemStack> inputs = new ArrayList<>(CompostingRegistry.getComposts().keySet());

            List<List<ItemStack>> stacks = new ArrayList<>();
            for (int i = 0; i < Math.min(inputs.size(), 36); i++) {
                List<ItemStack> stack = new ArrayList<>();
                stack.add(inputs.get(i));
                int k = i;
                while (k + 36 < inputs.size()) {
                    k += 36;
                    stack.add(inputs.get(k));
                }
                stacks.add(stack);
            }

            List<PositionedStack> in = new ArrayList<>();
            int i = 0, x = 3, y = 3;
            for (List<ItemStack> stack : stacks) {
                in.add(new PermutedStack(stack, x, y, stack.size() > 1));
                x += 18;
                i++;
                if (i == 9) {
                    i = 0;
                    x = 3;
                    y += 18;
                }
            }
            this.inputs = in;

            WeightedRandomChestContent[] tables = EtFuturumLootTables.COMPOSTER_LOOT.getItems(Minecraft.getMinecraft().thePlayer.worldObj.rand);
            List<PositionedStack> out = new ArrayList<>();
            this.props = new ItemStackMap<>();
            int max = tables.length;
            i = 0;
            x = (166 - (18 * (max % 9))) >> 1;
            y = 98;
            for (WeightedRandomChestContent table : tables) {
                out.add(new PositionedStack(table.theItemId, x, y, false));
                this.props.put(table.theItemId, new TableProps(table.theMinimumChanceToGenerateItem, table.theMaximumChanceToGenerateItem, table.itemWeight));
                x += 18;
                i++;
                if (i == 9) {
                    max -= 9;
                    x = (166 - (18 * (max % 9))) >> 1;
                    y += 18;
                    i = 0;
                }
            }
            this.outputs = out;
        }

        public List<PositionedStack> inputs;
        public List<PositionedStack> outputs;

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, inputs);
        }

        @Override
        public PositionedStack getResult() {
            return compooster;
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            return outputs;
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(this.getOverlayIdentifier())) arecipes.add(new CompostedRecipe());
        else loadCraftingRecipes((ItemStack) results[0]);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (WeightedRandomChestContent item : EtFuturumLootTables.COMPOSTER_LOOT.getItems(Minecraft.getMinecraft().thePlayer.worldObj.rand)) {
            if (NEIClientUtils.areStacksSameType(item.theItemId, result)) arecipes.add(new CompostedRecipe());
        }
        if (CompostingRegistry.getComposts().containsKey(result)) arecipes.add(new CompostedRecipe());
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
        if (inputId.equals(this.getOverlayIdentifier())) arecipes.add(new CompostedRecipe());
        else loadUsageRecipes((ItemStack) ingredients[0]);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (CompostingRegistry.getComposts().containsKey(ingredient) || this.compooster.contains(ingredient))
            arecipes.add(new CompostedRecipe());
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 0, 0, 166, 100);
    }

    @Override
    public void drawExtras(int recipe) {
        CompostedRecipe cached = (CompostedRecipe) arecipes.get(recipe);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 1f);
        for (PositionedStack stack : cached.getIngredients()) {
            PermutedStack perm = (PermutedStack) stack;
            int chance = CompostingRegistry.getCompostChance(perm.items[perm.permutation]);
            String str = chance + "%";
            GuiDraw.drawString(str, (perm.relx + 16 - (fontRenderer.getStringWidth(str) >> 1)) << 1, (perm.rely + 12) << 1, 0xFFFFFF, true);
        }
        GL11.glPopMatrix();
    }

    @Override
    public List<String> handleItemTooltip(GuiRecipe gui, ItemStack stack, List<String> currenttip, int recipe) {
        CompostedRecipe cached = (CompostedRecipe) arecipes.get(recipe);
        if (stack != null && cached.props.containsKey(stack)) {
            TableProps props = cached.props.get(stack);
            currenttip.add(currenttip.size() - 1, "§9" + I18n.format("efr.nei.amount") + ":§6 " + props.minSize + "§7~§6" + props.maxSize);
            currenttip.add(currenttip.size() - 1, "§9" + I18n.format("efr.nei.weight") + ":§6 " + props.wt);
        }
        return super.handleItemTooltip(gui, stack, currenttip, recipe);
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    @Override
    public String getOverlayIdentifier() {
        return Reference.MOD_ID + ".composter";
    }

    @Override
    public String getGuiTexture() {
        return Reference.MOD_ID + ":textures/gui/nei/board.png";
    }

    @Override
    public String getRecipeName() {
        return I18n.format("efr.nei.composter");
    }

    /**
     * Bogus class that captures the generated permutation for percentages
     */
    public static class PermutedStack extends PositionedStack {
        public int permutation;

        public PermutedStack(Object object, int x, int y, boolean genPerms) {
            super(object, x, y, genPerms);
            this.permutation = 0;
        }

        @Override
        public void setPermutationToRender(int index) {
            super.setPermutationToRender(index);
            this.permutation = index;
        }
    }


    /**
     * Bogus class that captures loot table properties
     */
    public static class TableProps {
        public final int minSize, maxSize, wt;

        public TableProps(int minSize, int maxSize, int wt) {
            this.minSize = minSize;
            this.maxSize = maxSize;
            this.wt = wt;
        }
    }
}
