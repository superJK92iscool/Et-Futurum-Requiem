package ganymedes01.etfuturum.core.handlers;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.world.structure.MapGenMesaMineshaft;
import ganymedes01.etfuturum.world.structure.StructureMesaMineshaftPieces;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
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