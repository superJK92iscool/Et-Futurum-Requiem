package ganymedes01.etfuturum.core.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WorldTickEventHandler {

    private static Map<Block, Block> replacements;

    static {
        if (replacements == null) {
            replacements = new HashMap<Block, Block>();
            if (ConfigurationHandler.enableBrewingStands)
                replacements.put(Blocks.brewing_stand, ModBlocks.brewing_stand);
            if (ConfigurationHandler.enableColourfulBeacons)
                replacements.put(Blocks.beacon, ModBlocks.beacon);
            if (ConfigurationHandler.enableEnchants)
                replacements.put(Blocks.enchanting_table, ModBlocks.enchantment_table);
            if (ConfigurationHandler.enableInvertedDaylightSensor)
                replacements.put(Blocks.daylight_detector, ModBlocks.daylight_sensor);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientTick(ClientTickEvent event)
    {
        World world = Minecraft.getMinecraft().theWorld;
        if(world == null || event.phase != Phase.START)
        	return;
        
        boolean tickchecktime = world.getTotalWorldTime() % 5 == 0;
        Iterator<TileEntity> iterator = world.loadedTileEntityList.iterator();
        while(iterator.hasNext()) {
        	TileEntity tile = iterator.next();
			if (ConfigurationHandler.enableNewMiscSounds && tile.getBlockType() == Blocks.lit_furnace) {
	            int x = tile.xCoord;
	            int y = tile.yCoord;
	            int z = tile.zCoord;
				if(world.rand.nextDouble() < 0.1D && tickchecktime)
					world.playSound(x + .5D, y + .5D, z + .5D,
							Reference.MOD_ID + ":block.furnace.fire_crackle", 1,
							(world.rand.nextFloat() * 0.1F) + 0.9F, false);
			}
        }
    }
    
    @SubscribeEvent
    public void tick(WorldTickEvent event) {
        if (event.side != Side.SERVER || event.phase != Phase.END)
            return;

        if (replacements.isEmpty() && !ConfigurationHandler.enableNewMiscSounds)
            return;

        World world = event.world;

        if(world.loadedTileEntityList.isEmpty())
        	return;

        Iterator<TileEntity> iterator = world.loadedTileEntityList.iterator();
        while(iterator.hasNext()) {
        	TileEntity tile = iterator.next();
            if (!replacements.isEmpty()) {
            	Block replacement = replacements.get(tile.getBlockType());
            	if(replacement != null && ((IConfigurable) replacement).isEnabled()) {
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
        }
    }
}