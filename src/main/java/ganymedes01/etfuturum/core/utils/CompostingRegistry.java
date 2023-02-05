package ganymedes01.etfuturum.core.utils;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompostingRegistry {

    private static final Map<ItemStack, Integer> COMPOSTING_REGISTRY = new HashMap<>();

    public static Map.Entry<ItemStack, Integer> getEntry(ItemStack stack) {
        if(stack == null) {
            return null;
        }
        for(Map.Entry<ItemStack, Integer> entry : COMPOSTING_REGISTRY.entrySet()) {
            if(entry.getKey().getItem() == stack.getItem() && (entry.getKey().getItemDamage() == OreDictionary.WILDCARD_VALUE || entry.getKey().getItemDamage() == stack.getItemDamage())) {
                return entry;
            }
        }
        return null;
    }

    public static void removeCompostingResult(ItemStack stack) {
        Map.Entry<ItemStack, Integer> entry = getEntry(stack);
        if(entry != null) {
            getCompostingRegistry().remove(entry.getKey());
            return;
        }
        Logger.error("Mod " + Utils.getModContainer() + " tried to remove " + Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage() + " but it was not registered to the composter?");
    }

    public static int getCompostingPercent(ItemStack stack) {
        Map.Entry<ItemStack, Integer> entry = getEntry(stack);
        if(entry != null) {
            return entry.getValue();
        }
        return 0;
    }

    public static void registerComposting(Object itemObj, int percent) {
        if(percent <= 0 || percent > 100) {
            throw new IllegalArgumentException("Mod " + Utils.getModContainer() + " tried to add a composter entry with percent value " + percent + " which is not allowed, should be above 0 and equal to or below 100!");
        }
        if(itemObj instanceof ItemStack) {
            COMPOSTING_REGISTRY.put(((ItemStack) itemObj).copy(), percent);
       } else if(itemObj instanceof String) {
//            OreDictionary.getOres((String) itemObj).forEach(itemStack -> COMPOSTING_REGISTRY.put(itemStack.copy(), percent));//For some reason this line does nothing
            for(ItemStack oreStack : OreDictionary.getOres((String) itemObj)) { //This should be the same as the forEach above but for some reason the above just does nothing?
                COMPOSTING_REGISTRY.put(oreStack.copy(), percent);
            }
        } else if(itemObj instanceof Item) {
            COMPOSTING_REGISTRY.put(new ItemStack((Item)itemObj, 1, OreDictionary.WILDCARD_VALUE), percent);
        } else if(itemObj instanceof Block && Item.getItemFromBlock((Block) itemObj) != null) {
            COMPOSTING_REGISTRY.put(new ItemStack(Item.getItemFromBlock((Block) itemObj), 1, OreDictionary.WILDCARD_VALUE), percent);
        }
    }

    public static void registerComposting(List<Object> list, int percent) {
        list.forEach(o -> registerComposting(o, percent));
    }

    public static Map<ItemStack, Integer> getCompostingRegistry() {
        return COMPOSTING_REGISTRY;
    }

    public static void init() {
            registerComposting(ImmutableList.of(
                    new ItemStack(ModItems.beetroot_seeds),
                    new ItemStack(Blocks.tallgrass, 1, 1),
                    new ItemStack(Blocks.leaves, 1, OreDictionary.WILDCARD_VALUE),
                    new ItemStack(Items.melon_seeds),
                    new ItemStack(Items.pumpkin_seeds),
                    "treeSapling",
                    "treeLeaves",
                    new ItemStack(Items.wheat_seeds),
                    new ItemStack(ModItems.sweet_berries)
            ), 30);

            registerComposting(ImmutableList.of(
                    new ItemStack(Blocks.cactus),
                    new ItemStack(Items.melon),
                    new ItemStack(Items.reeds),
                    new ItemStack(Blocks.double_plant, 1, 2),
                    new ItemStack(Blocks.vine)
            ), 50);

            registerComposting(ImmutableList.of(
                    new ItemStack(Items.apple),
                    new ItemStack(ModItems.beetroot),
                    "cropCarrot",
                    new ItemStack(Blocks.cocoa),
                    new ItemStack(Blocks.tallgrass, 1, 2),
                    new ItemStack(Blocks.double_plant, 1, 3),
                    BlockFlower.class,
                    BlockLilyPad.class,
                    new ItemStack(Blocks.melon_block),
                    new ItemStack(Blocks.brown_mushroom),
                    new ItemStack(Blocks.red_mushroom),
                    new ItemStack(Items.nether_wart),
                    "cropPotato",
                    new ItemStack(Blocks.pumpkin),
                    "cropWheat"
            ), 65);

            registerComposting(ImmutableList.of(
                    new ItemStack(Items.baked_potato),
                    new ItemStack(Items.bread),
                    new ItemStack(Items.cookie),
                    new ItemStack(Blocks.hay_block),
                    new ItemStack(Blocks.red_mushroom_block, 1, OreDictionary.WILDCARD_VALUE),
                    new ItemStack(Blocks.brown_mushroom_block, 1, OreDictionary.WILDCARD_VALUE)
            ), 85);

            registerComposting(ImmutableList.of(
                    new ItemStack(Items.cake),
                    new ItemStack(Items.pumpkin_pie)
            ), 100);
    }

    /**
     * Print all entries in composting registry. Used for debugging purposes.
     */
    public void printCompostingRegistry() {
        getCompostingRegistry().forEach((key, value) -> Logger.info("Composter entry: " + Item.itemRegistry.getNameForObject(key.getItem()) + " Meta: " + (key.getItemDamage() == OreDictionary.WILDCARD_VALUE ? "any" : key.getItemDamage())));
    }

}
