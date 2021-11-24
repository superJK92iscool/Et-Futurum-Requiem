package ganymedes01.etfuturum.core.handlers;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.IConfigurable;
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

	private static final Map<Block, Block> replacements = new HashMap<>();
	private int prevSize;
	
	public static final WorldEventHandler INSTANCE = new WorldEventHandler();
	
	private WorldEventHandler() {
		if(ConfigBlocksItems.enableBrewingStands) {
			if (ConfigWorld.tileReplacementMode == 0)
				replacements.put(Blocks.brewing_stand, ModBlocks.brewing_stand);
			else if(ConfigWorld.tileReplacementMode == 1)
				replacements.put(ModBlocks.brewing_stand, Blocks.brewing_stand);
		}
		

		if(ConfigBlocksItems.enableColourfulBeacons) {
			if (ConfigWorld.tileReplacementMode == 0)
				replacements.put(Blocks.beacon, ModBlocks.beacon);
			else if(ConfigWorld.tileReplacementMode == 1)
				replacements.put(ModBlocks.beacon, Blocks.beacon);
		}

		if(ConfigBlocksItems.enableEnchantingTable) {
			if (ConfigWorld.tileReplacementMode == 0)
				replacements.put(Blocks.enchanting_table, ModBlocks.enchantment_table);
			else if(ConfigWorld.tileReplacementMode == 1)
				replacements.put(ModBlocks.enchantment_table, Blocks.enchanting_table);
		}
		
		if(ConfigBlocksItems.enableInvertedDaylightSensor) {
			if (ConfigWorld.tileReplacementMode == 0 || ConfigWorld.tileReplacementMode == 1)
				replacements.put(ModBlocks.daylight_detector, Blocks.daylight_detector);
		}
	}
	
	@SubscribeEvent
	public void tick(WorldTickEvent event) {
		if (!event.side.isServer() || event.phase != Phase.END)
			return;

		if (replacements.isEmpty())
			return;

		World world = event.world;
		
		if(world.getWorldTime() % 5 != 0)
			return;
		
		if(world.loadedTileEntityList.isEmpty() || prevSize == world.loadedTileEntityList.size())
			return;
		
		TileEntity tile;
		Block replacement;
		for(int i = 0; i < world.loadedTileEntityList.size(); i++) {
			tile = (TileEntity)world.loadedTileEntityList.get(i);
			replacement = replacements.get(tile.getBlockType());
			
			if(replacement == null) continue;
			
			NBTTagCompound nbt = new NBTTagCompound();
			tile.writeToNBT(nbt);
			if (tile instanceof IInventory) {
				IInventory invt = (IInventory) tile;
				for (int j = 0; j < invt.getSizeInventory(); j++) {
					invt.setInventorySlotContents(j, null);
				}
			}
			
			int x = tile.xCoord;
			int y = tile.yCoord;
			int z = tile.zCoord;
			world.setBlock(x, y, z, replacement);
			TileEntity newTile = world.getTileEntity(x, y, z);
			if(newTile != null) {
				newTile.readFromNBT(nbt);
			}
		}
		
		prevSize = world.loadedTileEntityList.size();
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