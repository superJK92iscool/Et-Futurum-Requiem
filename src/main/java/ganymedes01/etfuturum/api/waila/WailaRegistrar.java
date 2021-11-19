package ganymedes01.etfuturum.api.waila;

import cpw.mods.fml.common.event.FMLInterModComms;
import ganymedes01.etfuturum.blocks.BlockShulkerBox;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaRegistrar {
	
	public static void wailaCallback(IWailaRegistrar registrar)
	{
		registrar.registerStackProvider(new ShulkerDataProvider(), BlockShulkerBox.class);
	}
	
	public static void register() {
		FMLInterModComms.sendMessage("Waila", "register", "ganymedes01.etfuturum.api.waila.WailaRegistrar.wailaCallback");
	}
}
