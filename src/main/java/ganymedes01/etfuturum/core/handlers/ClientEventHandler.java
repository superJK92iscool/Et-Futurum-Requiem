package ganymedes01.etfuturum.core.handlers;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.OpenGLHelper;
import ganymedes01.etfuturum.client.sound.NetherAmbience;
import ganymedes01.etfuturum.client.sound.NetherAmbienceLoop;
import ganymedes01.etfuturum.client.sound.WeightedSoundPool;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ClientEventHandler {

	public static final ClientEventHandler INSTANCE = new ClientEventHandler();
	private Random rand = new Random();

	@SideOnly(Side.CLIENT)
	public WeightedSoundPool netherWastes = new WeightedSoundPool();
	@SideOnly(Side.CLIENT)
	public WeightedSoundPool basaltDeltas = new WeightedSoundPool();
	@SideOnly(Side.CLIENT)
	public WeightedSoundPool crimsonForest = new WeightedSoundPool();
	@SideOnly(Side.CLIENT)
	public WeightedSoundPool warpedForest = new WeightedSoundPool();
	@SideOnly(Side.CLIENT)
	public WeightedSoundPool soulSandValley = new WeightedSoundPool();
	
	private ClientEventHandler() {
		netherWastes.addEntry("ambient.nether_wastes.additions.w1", 9);
		netherWastes.addEntry("ambient.nether_wastes.additions.w3", 3);
		netherWastes.addEntry("ambient.nether_wastes.additions.w5", 15);
		
		basaltDeltas.addEntry("ambient.basalt_deltas.additions.w1", 4);
		basaltDeltas.addEntry("ambient.basalt_deltas.additions.w10", 60);
		basaltDeltas.addEntry("ambient.basalt_deltas.additions.w20", 160);
		basaltDeltas.addEntry("ambient.basalt_deltas.additions.w25", 150);
		basaltDeltas.addEntry("ambient.basalt_deltas.additions.w40", 240);

		crimsonForest.addEntry("ambient.crimson_forest.additions.w2", 8);
		crimsonForest.addEntry("ambient.crimson_forest.additions.w3", 6);
		crimsonForest.addEntry("ambient.crimson_forest.additions.w4", 16);
		crimsonForest.addEntry("ambient.crimson_forest.additions.w6", 18);
		crimsonForest.addEntry("ambient.crimson_forest.additions.w35", 105);
		
		warpedForest.addEntry("ambient.warped_forest.additions.w1", 4);
		warpedForest.addEntry("ambient.warped_forest.additions.w2", 4);
		warpedForest.addEntry("ambient.warped_forest.additions.w3", 12);
		warpedForest.addEntry("ambient.warped_forest.additions.w3_p0.8", 3);
		warpedForest.addEntry("ambient.warped_forest.additions.w3_p0.7", 6);
		warpedForest.addEntry("ambient.warped_forest.additions.w3_p0.1", 3);
		warpedForest.addEntry("ambient.warped_forest.additions.w6", 12);
		warpedForest.addEntry("ambient.warped_forest.additions.w10", 10);
		warpedForest.addEntry("ambient.warped_forest.additions.w40", 120);

		soulSandValley.addEntry("ambient.soul_sand_valley.additions.w2", 2);
		soulSandValley.addEntry("ambient.soul_sand_valley.additions.w4", 4);
		soulSandValley.addEntry("ambient.soul_sand_valley.additions.w5", 65);
		soulSandValley.addEntry("ambient.soul_sand_valley.additions.w5_p0.7", 5);
		soulSandValley.addEntry("ambient.soul_sand_valley.additions.w25", 150);
		soulSandValley.addEntry("ambient.soul_sand_valley.additions.w25_p0.75", 100);
	}
	
	@SideOnly(Side.CLIENT)
	NetherAmbienceLoop ambience = null;
	@SideOnly(Side.CLIENT)
	int ticksToNextAmbience = rand.nextInt(80) + 1;
	@SideOnly(Side.CLIENT)
	String biomeName = null;
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event)
	{
		World world = FMLClientHandler.instance().getWorldClient();
		EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
		if(!ConfigurationHandler.enableNewMiscSounds || world == null || event.phase != Phase.START || Minecraft.getMinecraft().isGamePaused()) {
			return;
		}
		
		Chunk chunk = world.getChunkFromBlockCoords((int)player.posX, (int)player.posZ);
		if(!chunk.isChunkLoaded) {
			if(ambience != null && FMLClientHandler.instance().getClient().getSoundHandler().isSoundPlaying(ambience))
				FMLClientHandler.instance().getClient().getSoundHandler().stopSound(ambience);
			ambience = null;
			biomeName = null;
			return;
		}

		if(player.dimension == -1 && ConfigurationHandler.enableNetherAmbience && !EtFuturum.netherAmbienceNetherlicious) {
			Minecraft mc = FMLClientHandler.instance().getClient();
			int x = MathHelper.floor_double(player.posX);
			//int y = MathHelper.floor_double(player.posY); // unused variable
			int z = MathHelper.floor_double(player.posZ);
			biomeName = chunk.getBiomeGenForWorldCoords(x & 15, z & 15, world.getWorldChunkManager()).biomeName;
			if(getAmbienceLoopForBiome(biomeName) != null && !mc.getSoundHandler().isSoundPlaying(ambience)) {
				Boolean flag = ambience == null || ambience.getVolume() <= 0;
					ambience = new NetherAmbienceLoop(Reference.MOD_ID + ":ambient." + getAmbienceLoopForBiome(biomeName) + ".loop");
					mc.getSoundHandler().playSound(ambience);
				if(flag)
					ambience.fadeIn();
			} else if (biomeName == null || getAmbienceLoopForBiome(biomeName) == null || !ambience.getPositionedSoundLocation().getResourcePath().split("\\.")[1].equals(getAmbienceLoopForBiome(biomeName))) {
				if(ambience != null)
					ambience.stop();
			} else if (mc.getSoundHandler().isSoundPlaying(ambience) && ambience.isStopping && ambience.getPositionedSoundLocation().getResourcePath().split("\\.")[1].equals(getAmbienceLoopForBiome(biomeName))){
				ambience.isStopping = false;
			}
			if(getAmbienceLoopForBiome(biomeName) != null && ambience != null && ticksToNextAmbience-- <= 0) {
				Minecraft.getMinecraft().getSoundHandler().playSound(new NetherAmbience(Reference.MOD_ID + ":" + getAmbienceForBiome().getRandom(), 0.5F, 1));
				ticksToNextAmbience = 40 + rand.nextInt(40) + 1;
			}
		}

		boolean tickchecktime = world.rand.nextInt(Math.toIntExact((world.getTotalWorldTime() % 10) + 1)) == 0;
		if(tickchecktime) {
			for(TileEntity tile : (List<TileEntity>)world.loadedTileEntityList) {
				if(!(tile instanceof TileEntityFurnace)) //Don't use getBlock or get tile coord info if the tile isn't a furnace, so we only get the block when we need to
					continue;
				
				int x = tile.xCoord;
				int y = tile.yCoord;
				int z = tile.zCoord;
				
				if(world.getBlock(x, y, z) != Blocks.lit_furnace) //Don't play sound if the furnace isn't lit
					continue;
				
				if(world.rand.nextDouble() < 0.1D)
					world.playSound(x + .5D, y + .5D, z + .5D,
							Reference.MOD_ID + ":block.furnace.fire_crackle", 1,
							(world.rand.nextFloat() * 0.1F) + 0.9F, false);
			}
		}
	}

	public static String getAmbienceLoopForBiome(String string) {
		if(string.contains("Basalt Deltas")) {
			return "basalt_deltas";
		}
		if(string.contains("Warped Forest")) {
			return "warped_forest";
		}
		if(string.contains("Crimson Forest") || string.contains("Foxfire Swamp")) {
			return "crimson_forest";
		}
		if(string.contains("Soul Sand Valley")) {
			return "soul_sand_valley";
		}
		if(string.contains("Abyssal Shadowland")) {
			return null;
		}
		return "nether_wastes";
	}

	private WeightedSoundPool getAmbienceForBiome() {
		String string = ambience.getPositionedSoundLocation().getResourcePath().split("\\.")[1];
		if(string.contains("null"))
			return null;
		
		if(string.contains("basalt_deltas")) {
			return basaltDeltas;
		}
		if(string.contains("warped_forest")) {
			return warpedForest;
		}
		if(string.contains("crimson_forest")) {
			return crimsonForest;
		}
		if(string.contains("soul_sand_valley")) {
			return soulSandValley;
		}
		return netherWastes;
	}
	@SubscribeEvent
	public void toolTipEvent(ItemTooltipEvent event) {
		if(ConfigurationHandler.enableExtraF3HTooltips && event.showAdvancedItemTooltips) {
			event.toolTip.add("");
			event.toolTip.add("\u00a78" + Item.itemRegistry.getNameForObject(event.itemStack.getItem()));
			if(event.itemStack.stackTagCompound != null && !event.itemStack.stackTagCompound.hasNoTags())
				event.toolTip.add("\u00a78NBT: " + event.itemStack.stackTagCompound.func_150296_c().size() + " Tag(s)");
		}
	}
	
	@SubscribeEvent
	public void renderPlayerEventPre(RenderPlayerEvent.Pre event) {
		if (ConfigurationHandler.enableTransparentAmour) {
			OpenGLHelper.enableBlend();
			OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
	}

	@SubscribeEvent
	public void renderPlayerSetArmour(SetArmorModel event) {
		if (ConfigurationHandler.enableTransparentAmour) {
			OpenGLHelper.enableBlend();
			OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
	}

	@SubscribeEvent
	public void renderPlayerEventPost(RenderPlayerEvent.Post event) {
		if (ConfigurationHandler.enableTransparentAmour)
			OpenGLHelper.disableBlend();
	}
}