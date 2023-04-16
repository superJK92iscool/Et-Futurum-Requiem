package ganymedes01.etfuturum.client.nei;

import cpw.mods.fml.common.event.FMLInterModComms;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.nbt.NBTTagCompound;

public class IMCSenderGTNH {

    public static void IMCSender() {

        if(ConfigBlocksItems.enableSmoker) {
            sendHandler("ganymedes01.etfuturum.NEI_EtFuturum_Config$NEI_Recipes_Smoker", "etfuturum:smoker");
            sendCatalyst("ganymedes01.etfuturum.NEI_EtFuturum_Config$NEI_Recipes_Smoker", "etfuturum:smoker");

            sendCatalyst("codechicken.nei.recipe.FuelRecipeHandler", "etfuturum:smoker");
        }

        if(ConfigBlocksItems.enableBlastFurnace) {
            sendHandler("ganymedes01.etfuturum.NEI_EtFuturum_Config$NEI_Recipes_BlastFurnace", "etfuturum:blast_furnace");
            sendCatalyst("ganymedes01.etfuturum.NEI_EtFuturum_Config$NEI_Recipes_BlastFurnace", "etfuturum:blast_furnace");

            sendCatalyst("codechicken.nei.recipe.FuelRecipeHandler", "etfuturum:blast_furnace");
        }

        if (ConfigBlocksItems.enableBanners) {
            sendHandler("ganymedes01.etfuturum.client.nei.BannerPatternhandler", "etfuturum:banner");
            sendCatalyst("ganymedes01.etfuturum.client.nei.BannerPatternhandler", "etfuturum:banner");
        }
    }

    /*
     * These were copied from GTNewHorizons/GoodGenerator (Fork of GlodBlock/GoodGenerator)
     * Author: GlodBlock
     */

    private static void sendHandler(String aName, String aBlock) {
        sendHandler(aName, aBlock, 5);
    }

    private static void sendHandler(String aName, String aBlock, int maxRecipesPerPage) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handler", aName);
        aNBT.setString("modName", Reference.MOD_NAME);
        aNBT.setString("modId", Reference.MOD_ID);
        aNBT.setBoolean("modRequired", true);
        aNBT.setString("itemName", aBlock);
        aNBT.setInteger("handlerHeight", 65);
        aNBT.setInteger("handlerWidth", 166);
        aNBT.setInteger("maxRecipesPerPage", maxRecipesPerPage);
        aNBT.setInteger("yShift", 6);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerHandlerInfo", aNBT);

    }

    private static void sendCatalyst(String aName, String aStack, int aPriority) {
        NBTTagCompound aNBT = new NBTTagCompound();
        aNBT.setString("handlerID", aName);
        aNBT.setString("itemName", aStack);
        aNBT.setInteger("priority", aPriority);
        FMLInterModComms.sendMessage("NotEnoughItems", "registerCatalystInfo", aNBT);
    }

    private static void sendCatalyst(String aName, String aStack) {
        sendCatalyst(aName, aStack, 0);
    }
}
