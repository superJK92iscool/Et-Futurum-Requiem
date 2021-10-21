package ganymedes01.etfuturum.plugins.waila;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;
import ganymedes01.etfuturum.blocks.BlockShulkerBox;
import ganymedes01.etfuturum.lib.Reference;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fluids.FluidStack;

public class WailaRegistrar {
	
    public static void wailaCallback(IWailaRegistrar registrar)
    {
        registrar.registerStackProvider(new ShulkerDataProvider(), BlockShulkerBox.class);
    }
    
    public static void register() {
        FMLInterModComms.sendMessage("Waila", "register", "ganymedes01.etfuturum.plugins.waila.WailaRegistrar.wailaCallback");
    }
}
