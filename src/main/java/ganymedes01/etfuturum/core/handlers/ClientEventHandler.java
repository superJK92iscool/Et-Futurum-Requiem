package ganymedes01.etfuturum.core.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
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
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ClientEventHandler {

	public static final ClientEventHandler INSTANCE = new ClientEventHandler();
	private Random rand = new Random();

	public WeightedSoundPool netherWastes = new WeightedSoundPool();
	public WeightedSoundPool basaltDeltas = new WeightedSoundPool();
	public WeightedSoundPool crimsonForest = new WeightedSoundPool();
	public WeightedSoundPool warpedForest = new WeightedSoundPool();
	public WeightedSoundPool soulSandValley = new WeightedSoundPool();

	public WeightedSoundPool netherWastesMusic = new WeightedSoundPool();
	public WeightedSoundPool basaltDeltasMusic = new WeightedSoundPool();
	public WeightedSoundPool crimsonForestMusic = new WeightedSoundPool();
	public WeightedSoundPool warpedForestMusic = new WeightedSoundPool();
	public WeightedSoundPool soulSandValleyMusic = new WeightedSoundPool();
	public WeightedSoundPool underWaterMusic = new WeightedSoundPool();
	
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
		
		addToMusicPools();
	}
	
	@SideOnly(Side.CLIENT)
	private void addToMusicPools() {
		netherWastesMusic.addEntry(Reference.MOD_ID + ":music.nether.nether_wastes", 6);
		netherWastesMusic.addEntry("music.game.nether", 4);

		basaltDeltasMusic.addEntry(Reference.MOD_ID + ":music.nether.basalt_deltas", 7);
		basaltDeltasMusic.addEntry("music.game.nether", 4);

		crimsonForestMusic.addEntry(Reference.MOD_ID + ":music.nether.crimson_forest", 7);
		crimsonForestMusic.addEntry("music.game.nether", 4);

		warpedForestMusic.addEntry(Reference.MOD_ID + ":music.nether.warped_forest", 1);

		soulSandValleyMusic.addEntry(Reference.MOD_ID + ":music.nether.soul_sand_valley", 7);
		soulSandValleyMusic.addEntry("music.game.nether", 4);
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
		if(world == null || event.phase != Phase.START || Minecraft.getMinecraft().isGamePaused()) {
			return;
		}
		

		if(ConfigurationHandler.enableNetherAmbience && !EtFuturum.netherAmbienceNetherlicious && player.dimension == -1) {
			Chunk chunk = world.getChunkFromBlockCoords((int)player.posX, (int)player.posZ);
			if(!chunk.isChunkLoaded) {
				if(ambience != null && FMLClientHandler.instance().getClient().getSoundHandler().isSoundPlaying(ambience))
					FMLClientHandler.instance().getClient().getSoundHandler().stopSound(ambience);
				ambience = null;
				biomeName = null;
				return;
			}
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

		if(ConfigurationHandler.enableNewMiscSounds && world.rand.nextInt(Math.toIntExact((world.getTotalWorldTime() % 10) + 1)) == 0) {
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

	PositionedSound netherMusic;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlaySoundEvent(PlaySoundEvent17 event)
	{
		if(event.sound != null && event.name != null && cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient() != null
				&& FMLCommonHandler.instance().getSide() == Side.CLIENT) {
				String[] eventwithprefix = event.name.split("\\.");
			if (ConfigurationHandler.enableNewBlocksSounds && event.name.contains(".") &&
					eventwithprefix[1].equals(Blocks.stone.stepSound.soundName)
					&& event.sound.getPitch() < 1.0F) {
				World world = cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient();
				int x = MathHelper.floor_float(event.sound.getXPosF());
				int y = MathHelper.floor_float(event.sound.getYPosF());
				int z = MathHelper.floor_float(event.sound.getZPosF());
				Block block = world.getBlock(x, y, z);
				Item itemblock = Item.getItemFromBlock(block);
				if(itemblock == null)
					return;
				String name = itemblock.getUnlocalizedName(new ItemStack(itemblock, 1, world.getBlockMetadata(x, y, z) % 8)).toLowerCase();
				if(name.contains("slab") && name.contains("nether") && name.contains("brick")) {
					String prefix = event.name.substring(0, event.name.indexOf(".") + 1);
					float soundPit = (block.stepSound.getPitch()) * (prefix.contains("step") ? 0.5F : 0.8F);
					float soundVol = (block.stepSound.getVolume() + 1.0F) / (prefix.contains("step") ? 8F : 2F);

					event.result = new PositionedSoundRecord(new ResourceLocation(Reference.MOD_ID + ":" + eventwithprefix[0] + ".nether_bricks"), soundVol, soundPit, x + 0.5F, y + 0.5F, z + 0.5F);
					return;
				}
			}
			
			if(ConfigurationHandler.enableNewMiscSounds) {
				if(event.name.contains("random.door")) {
					World world = cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient();
					int x = MathHelper.floor_float(event.sound.getXPosF());
					int y = MathHelper.floor_float(event.sound.getYPosF());
					int z = MathHelper.floor_float(event.sound.getZPosF());

					Block block = world.getBlock(x, y, z);
					event.result = new PositionedSoundRecord(new ResourceLocation(getReplacementDoorSound(block, event.name)),
							event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
				} else if (event.name.contains("random.chest")) {
					World world = cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient();
					int x = MathHelper.floor_float(event.sound.getXPosF());
					int y = MathHelper.floor_float(event.sound.getYPosF());
					int z = MathHelper.floor_float(event.sound.getZPosF());

					Block block = world.getBlock(x, y, z);
					String s = event.name;
					String blockID = Block.blockRegistry.getNameForObject(block).split(":")[1].toLowerCase();
					if(blockID.contains("chest") && (event.name.contains("open") || event.name.contains("close"))) {
						if((blockID.contains("ender") && block.getMaterial().equals(Material.rock)))
							s = Reference.MOD_ID + ":" + "block.ender_chest." + (event.name.contains("close") ? "close" : "open");
						else if(block.getMaterial().equals(Material.wood) && event.name.contains("close"))
							s = Reference.MOD_ID + ":" + "block.chest.close";
					}
					
					if(!s.equals(event.name)) {
						event.result = new PositionedSoundRecord(new ResourceLocation(s), 
								event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
					}
				}
			}

			if(!EtFuturum.netherMusicNetherlicious) {
				Minecraft mc = cpw.mods.fml.client.FMLClientHandler.instance().getClient();
				if (mc.thePlayer.dimension == -1 && event.name.equals("music.game.nether")) {
					if(netherMusic == null || !mc.getSoundHandler().isSoundPlaying(netherMusic)) {
						//World world = mc.theWorld; // unused variable
						WeightedSoundPool pool = getMusicForBiome(mc.theWorld.getChunkFromBlockCoords((int)mc.thePlayer.posX, (int)mc.thePlayer.posZ).getBiomeGenForWorldCoords((int)mc.thePlayer.posX & 15, (int)mc.thePlayer.posZ & 15, mc.theWorld.getWorldChunkManager()).biomeName);
						netherMusic = PositionedSoundRecord.func_147673_a(new ResourceLocation(pool.getRandom()));
						event.result = netherMusic;
					} else {
						event.result = null;
					}
				}
			}
			
			if(ConfigurationHandler.enableNewAmbientSounds) {
				if (event.name.equals("ambient.weather.rain")) {
					//World world = cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient(); // unused variable
					int x = MathHelper.floor_float(event.sound.getXPosF());
					int y = MathHelper.floor_float(event.sound.getYPosF());
					int z = MathHelper.floor_float(event.sound.getZPosF());
					event.result = new PositionedSoundRecord(new ResourceLocation(Reference.MOD_ID + ":" + "weather.rain" + (event.sound.getPitch() < 1.0F ? ".above" : "")), 
							event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
				} else if (event.name.equals("ambient.cave.cave")) {
					int x = MathHelper.floor_float(event.sound.getXPosF());
					int y = MathHelper.floor_float(event.sound.getYPosF());
					int z = MathHelper.floor_float(event.sound.getZPosF());
					if(ConfigurationHandler.enableNetherAmbience && cpw.mods.fml.client.FMLClientHandler.instance().getClientPlayerEntity().dimension == -1) {
						String biomeName = cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient().getChunkFromBlockCoords(x, z).getBiomeGenForWorldCoords(x & 15, z & 15, cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient().getWorldChunkManager()).biomeName;
						if(ClientEventHandler.getAmbienceLoopForBiome(biomeName) != null)
							event.result = new PositionedSoundRecord(new ResourceLocation(Reference.MOD_ID + ":ambient." + ClientEventHandler.getAmbienceLoopForBiome(biomeName) + ".mood"), 
									event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
						else
							event.result = null;
					} else if(new Random().nextInt(19) >= 13) {
						event.result = new PositionedSoundRecord(new ResourceLocation(Reference.MOD_ID + ":ambient.cave"), 
								event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
					}
				}
			}
		}
	}
	
	private WeightedSoundPool getMusicForBiome(String string) {
		if(string.contains("Basalt Deltas") || string.contains("Crystalline Crag")) {
			return basaltDeltasMusic;
		}
		if(string.contains("Warped Forest") || string.contains("Abyssal Shadowland")) {
			return warpedForestMusic;
		}
		if(string.contains("Crimson Forest") || string.contains("Foxfire Swamp")) {
			return crimsonForestMusic;
		}
		if(string.contains("Soul Sand Valley")) {
			return soulSandValleyMusic;
		}
		return netherWastesMusic;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlaySoundAtEntityEvent(PlaySoundAtEntityEvent event)
	{
		if (ConfigurationHandler.enableNewBlocksSounds && event.name != null
				&& event.entity != null && event.name.contains("step") && event.name.equals("step.stone")
				&& event.name.contains(".")) {
			String[] eventwithprefix = event.name.split("\\.");
			int x = MathHelper.floor_double(event.entity.posX);
			int y = MathHelper.floor_double(event.entity.posY - 0.20000000298023224D - event.entity.yOffset);
			int z = MathHelper.floor_double(event.entity.posZ);
			World world = event.entity.worldObj;
			Block block = world.getBlock(x, y, z);
			Item itemblock = Item.getItemFromBlock(block);
			if(itemblock == null)
				return;
			String name = itemblock.getUnlocalizedName(new ItemStack(itemblock, 1, world.getBlockMetadata(x, y, z) % 8)).toLowerCase();
			if(name.contains("slab") && name.contains("nether") && name.contains("brick")) {
				//String prefix = event.name.substring(0, event.name.indexOf(".") + 1); // unused variable
				//float soundPit = (block.stepSound.getPitch()) * (prefix.contains("step") ? 0.5F : 0.8F); // unused variable
				//float soundVol = (block.stepSound.getVolume() + 1.0F) / (prefix.contains("step") ? 8F : 2F); // unused variable
				event.name = Reference.MOD_ID + ":" + eventwithprefix[0] + ".nether_bricks";
				return;
			}
		}
	}
	
	private String getReplacementDoorSound(Block block, String string) {
		Random random = new Random();
		String closeOrOpen = random.nextInt(2) == 0 ? "open" : "close";
		if(block instanceof BlockDoor)
			if (block.getMaterial() == Material.wood/* || block.getMaterial() == EtFuturum.netherwood */)
				return Reference.MOD_ID + ":block.wooden_door." + closeOrOpen;
			else if(block.getMaterial() == Material.iron)
				return Reference.MOD_ID + ":block.iron_door." + closeOrOpen;
		
		if(block instanceof BlockTrapDoor)
			if (block.getMaterial() == Material.wood/* || block.getMaterial() == EtFuturum.netherwood */)
				return Reference.MOD_ID + ":block.wooden_trapdoor." + closeOrOpen;
			else if(block.getMaterial() == Material.iron)
				return Reference.MOD_ID + ":block.iron_trapdoor." + closeOrOpen;
		
		if(block instanceof BlockFenceGate)
			if (block.getMaterial() == Material.wood/* || block.getMaterial() == EtFuturum.netherwood */)
				return Reference.MOD_ID + ":block.fence_gate." + closeOrOpen;
				
		return string;
	}
}