package ganymedes01.etfuturum.core.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.world.structure.MapGenMesaMineshaft;
import ganymedes01.etfuturum.world.structure.StructureMesaMineshaftPieces;
import net.minecraftforge.event.terraingen.InitMapGenEvent;

public class WorldEventHandler {

	private int prevSize;
	
	public static final WorldEventHandler INSTANCE = new WorldEventHandler();
	
	private WorldEventHandler() {
	}
	
	private static boolean hasRegistered;
	
	@SubscribeEvent
	public void TerrainRegisterOverride(InitMapGenEvent event) {
		if(ConfigWorld.enableMesaMineshaft && event.type == InitMapGenEvent.EventType.MINESHAFT) {
			if(!hasRegistered) {
				StructureMesaMineshaftPieces.registerStructurePieces();
				hasRegistered = true;
			}
			event.newGen = new MapGenMesaMineshaft();
		}
	}
}