package ganymedes01.etfuturum.core.utils;

import com.google.common.collect.ImmutableList;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class CompostingRegistry {

    private static final ItemStackMap<Integer> COMPOSTING_REGISTRY = new ItemStackMap<>();

    public static void init() {
        registerComposting(ImmutableList.of(
                new ItemStack(Blocks.tallgrass, 1, 1),
                new ItemStack(Blocks.leaves, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(Items.melon_seeds),
                new ItemStack(Items.pumpkin_seeds),
                new ItemStack(Items.wheat_seeds),
                new ItemStack(ModItems.beetroot_seeds),
                "listAllSeeds",
                "treeSapling",
                "treeLeaves",
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
                new ItemStack(Items.dye, 1, 3),
                new ItemStack(Blocks.tallgrass, 1, 2),
                new ItemStack(Blocks.double_plant, 1, 0),
                new ItemStack(Blocks.double_plant, 1, 1),
                new ItemStack(Blocks.double_plant, 1, 3),
                new ItemStack(Blocks.double_plant, 1, 4),
                new ItemStack(Blocks.double_plant, 1, 5),
                new ItemStack(Blocks.red_flower, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(Blocks.yellow_flower, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(ModBlocks.rose),
                new ItemStack(ModBlocks.cornflower),
                new ItemStack(ModBlocks.lily_of_the_valley),
                new ItemStack(ModBlocks.wither_rose),
                new ItemStack(Blocks.waterlily),
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
                new ItemStack(Blocks.brown_mushroom_block, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(ModBlocks.red_mushroom_block, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(ModBlocks.brown_mushroom_block, 1, OreDictionary.WILDCARD_VALUE)
        ), 85);

        registerComposting(ImmutableList.of(
                new ItemStack(Blocks.cake),
                new ItemStack(Items.cake),
                new ItemStack(Items.pumpkin_pie)
        ), 100);
    }

    public static void registerComposting(Object itemObj, int percent) {
        if(percent <= 0 || percent > 100) {
            throw new IllegalArgumentException("Tried to add a composter entry with percent value " + percent + " which is not allowed, should be above 0 and equal to or below 100!");
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
        } else {
            throw new IllegalArgumentException("Tried to add " + itemObj + " as a compostable, which is not an Itemstack, item, block or string.");
        }
    }

    public static void registerComposting(List<Object> list, int percent) {
        list.forEach(o -> registerComposting(o, percent));
    }

    public static ItemStackMap<Integer> getComposts() {
        return COMPOSTING_REGISTRY;
    }

    public static void remove(String stackOreDict) {
        for(ItemStack stack : OreDictionary.getOres(stackOreDict)) {
            remove(stack);
        }
    }

    public static void remove(ItemStack stack) {
        COMPOSTING_REGISTRY.remove(stack);
    }

    public static boolean isCompostable(String stackOreDict) {
        for(ItemStack stack : OreDictionary.getOres(stackOreDict)) {
            if(isCompostable(stack)) return true;
        }
        return false;
    }

    public static boolean isCompostable(ItemStack stack) {
        return getCompostChance(stack) > 0;
    }

    public static int getCompostChance(ItemStack stack) {
        Integer percent = COMPOSTING_REGISTRY.get(stack);
        if(percent != null) {
            return percent;
        }

        return 0;
    }

    /**
     * Print all entries in composting registry. Used for debugging purposes.
     */
    public void printCompostables() {
        getComposts().forEach((key, value) -> Logger.info("Composter entry: " + Item.itemRegistry.getNameForObject(key.getItem()) + " Meta: " + (key.getItemDamage() == OreDictionary.WILDCARD_VALUE ? "any" : key.getItemDamage())));
    }

}
