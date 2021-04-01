package ganymedes01.etfuturum.core.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import codechicken.lib.math.MathHelper;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.NetherAmbience;
import ganymedes01.etfuturum.client.sound.NetherAmbienceLoop;
import ganymedes01.etfuturum.client.sound.WeightedSoundPool;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WorldTickEventHandler {

    private static Map<Block, Block> replacements;
    private int prevHash;
    private Random rand = new Random();
    
    public WorldTickEventHandler() {
    }

    static {
        if (replacements == null) {
            replacements = new HashMap<Block, Block>();
            if (ConfigurationHandler.enableBrewingStands && ConfigurationHandler.enableTileReplacement)
                replacements.put(Blocks.brewing_stand, ModBlocks.brewing_stand);
            else
                replacements.put(ModBlocks.brewing_stand, Blocks.brewing_stand);
            
            if (ConfigurationHandler.enableColourfulBeacons && ConfigurationHandler.enableTileReplacement)
                replacements.put(Blocks.beacon, ModBlocks.beacon);
            else
                replacements.put(ModBlocks.beacon, Blocks.beacon);
            
            if (ConfigurationHandler.enableEnchants && ConfigurationHandler.enableTileReplacement)
                replacements.put(Blocks.enchanting_table, ModBlocks.enchantment_table);
            else
                replacements.put(ModBlocks.enchantment_table, Blocks.enchanting_table);
            
            if (ConfigurationHandler.enableInvertedDaylightSensor && ConfigurationHandler.enableTileReplacement)
                replacements.put(Blocks.daylight_detector, ModBlocks.daylight_sensor);
            else
                replacements.put(ModBlocks.daylight_sensor, Blocks.daylight_detector);
        }
    }
    
    @SubscribeEvent
    public void tick(WorldTickEvent event) {
        if (event.side != Side.SERVER || event.phase != Phase.END)
            return;

        if (replacements.isEmpty())
            return;

        World world = event.world;

        if(world.loadedTileEntityList.isEmpty() || prevHash == world.loadedTileEntityList.hashCode())
        	return;
        
        List<TileEntity> tileList = world.loadedTileEntityList;	
        
        for(TileEntity tile : tileList) {
        	Block replacement = replacements.get(tile.getBlockType());
        	if(replacement != null && (!(replacement instanceof IConfigurable) || ((IConfigurable) replacement).isEnabled())) {
                NBTTagCompound nbt = new NBTTagCompound();
                tile.writeToNBT(nbt);
                if (tile instanceof IInventory) {
                    IInventory invt = (IInventory) tile;
                    for (int j = 0; j < invt.getSizeInventory(); j++)
                        invt.setInventorySlotContents(j, null);
                }
                int x = tile.xCoord;
                int y = tile.yCoord;
                int z = tile.zCoord;
                world.setBlock(x, y, z, replacement);
                TileEntity newTile = world.getTileEntity(x, y, z);
                newTile.readFromNBT(nbt);
                break;
        	}
        }
        prevHash = tileList.hashCode();	
    }
}