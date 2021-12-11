package ganymedes01.etfuturum.core.handlers;

import java.io.File;
import java.util.List;
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
import ganymedes01.etfuturum.EtFuturumMixinPlugin;
import ganymedes01.etfuturum.blocks.BlockShulkerBox;
import ganymedes01.etfuturum.client.OpenGLHelper;
import ganymedes01.etfuturum.client.gui.GuiConfigWarning;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.client.sound.NetherAmbienceLoop;
import ganymedes01.etfuturum.client.sound.NetherAmbienceSound;
import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.tileentities.TileEntityShulkerBox;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ClientEventHandler {

	public static final ClientEventHandler INSTANCE = new ClientEventHandler();
	private Random rand = new Random();
	private boolean showedDebugWarning;
	
	private ClientEventHandler() {
	}
	
	@SideOnly(Side.CLIENT)
	NetherAmbienceLoop ambience = null;
	@SideOnly(Side.CLIENT)
	int ticksToNextAmbience = rand.nextInt(80) + 1;
	@SideOnly(Side.CLIENT)
	BiomeGenBase biome = null;
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event)
	{
		World world = FMLClientHandler.instance().getWorldClient();
		EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
		
		if(world == null || event.phase != Phase.START || Minecraft.getMinecraft().isGamePaused()) {
			return;
		}
		
		if(!showedDebugWarning && player.ticksExisted == 40) {
			if(Reference.VERSION_NUMBER.contains("dev") || Reference.VERSION_NUMBER.contains("snapshot")
						|| Reference.VERSION_NUMBER.contains("alpha") || Reference.VERSION_NUMBER.contains("beta") || EtFuturum.isTesting) {
				ChatComponentText text = new ChatComponentText("\u00a7c\u00a7l[Debug]: \u00a7rYou are using a pre-release version of \u00a7bEt \u00a7bFuturum \u00a7bRequiem\u00a7r. There might be more bugs, please click on this message to report them!");
				text.getChatStyle().setChatClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/Roadhog360/Et-Futurum-Requiem/issues"));
			player.addChatComponentMessage(text);
						}
			showedDebugWarning = true;
		}

		if(ConfigWorld.enableNetherAmbience && !EtFuturum.netherAmbienceNetherlicious && player.dimension == -1) {
			Chunk chunk = world.getChunkFromBlockCoords(MathHelper.floor_double((int)player.posX), MathHelper.floor_double((int)player.posZ));
			if(!chunk.isChunkLoaded) {
				if(ambience != null && FMLClientHandler.instance().getClient().getSoundHandler().isSoundPlaying(ambience)) {
					FMLClientHandler.instance().getClient().getSoundHandler().stopSound(ambience);
				}
				ambience = null;
				biome = null;
				return;
			}
			Minecraft mc = FMLClientHandler.instance().getClient();
			int x = MathHelper.floor_double(player.posX);
			//int y = MathHelper.floor_double(player.posY); // unused variable
			int z = MathHelper.floor_double(player.posZ);
			biome = chunk.getBiomeGenForWorldCoords(x & 15, z & 15, world.getWorldChunkManager());
			
			String soundLoc = "";
			if(ambience != null ) {
				soundLoc = ambience.getPositionedSoundLocation().getResourceDomain() + ":" + ambience.getPositionedSoundLocation().getResourcePath();
			}
			
			if(getAmbienceLoop(biome) != null  && !mc.getSoundHandler().isSoundPlaying(ambience)) {
				Boolean flag = ambience == null || ambience.getVolume() <= 0;
					ambience = new NetherAmbienceLoop(getAmbienceLoop(biome));
					mc.getSoundHandler().playSound(ambience);
				if(flag) {
					ambience.fadeIn();
				}
				System.out.println("test1");
			} else if (biome == null || getAmbienceLoop(biome) == null || !soundLoc.equals(getAmbienceLoop(biome))) {
				ambience.stop();
				System.out.println("test2");
			} else if (mc.getSoundHandler().isSoundPlaying(ambience) && ambience.isStopping && soundLoc.equals(getAmbienceLoop(biome))){
				ambience.isStopping = false;
				System.out.println("test3");
			}
			if(getAmbienceAdditions(biome) != null && ambience != null && ticksToNextAmbience-- <= 0) {
				Minecraft.getMinecraft().getSoundHandler().playSound(new NetherAmbienceSound(new ResourceLocation(getAmbienceAdditions(biome))));
				ticksToNextAmbience = 50 + rand.nextInt(30) + 1;
			}
		}

		if(ConfigWorld.enableNewMiscSounds && world.rand.nextInt(Math.toIntExact((world.getTotalWorldTime() % 10) + 1)) == 0) {
			for(TileEntity tile : (List<TileEntity>)world.loadedTileEntityList) {
				if(!(tile instanceof TileEntityFurnace) && tile.getBlockType() != Blocks.lit_furnace) {
					//Don't use getBlock or get tile coord info if the tile isn't a furnace, so we only get the block when we need to
					//Don't run code if block is not lit furnace
					continue;
				}
				
				int x = tile.xCoord;
				int y = tile.yCoord;
				int z = tile.zCoord;
				
				if(world.rand.nextDouble() < 0.1D)
					world.playSound(x + .5D, y + .5D, z + .5D,
							Reference.MCv118 + ":block.furnace.fire_crackle", 1,
							(world.rand.nextFloat() * 0.1F) + 0.9F, false);
			}
		}
	}
	private String getStringFor(BiomeGenBase biome) {
		if(biome == null)
			return null;
		
//		if(string.contains("basalt_deltas")) {
//			return basaltDeltas;
//		}
//		if(string.contains("warped_forest")) {
//			return warpedForest;
//		}
//		if(string.contains("crimson_forest")) {
//			return crimsonForest;
//		}
//		if(string.contains("soul_sand_valley")) {
//			return soulSandValley;
//		}
		return "nether_wastes";
		
	}

	private String getAmbience(BiomeGenBase biome) {
		if(biome == null)
			return null;
		
//		if(string.contains("basalt_deltas")) {
//			return basaltDeltas;
//		}
//		if(string.contains("warped_forest")) {
//			return warpedForest;
//		}
//		if(string.contains("crimson_forest")) {
//			return crimsonForest;
//		}
//		if(string.contains("soul_sand_valley")) {
//			return soulSandValley;
//		}
		return Reference.MCv118 + ":ambient." + getStringFor(biome);
	}

	private String getAmbienceLoop(BiomeGenBase biome) {
		if(biome == null) {
			return null;
		}
		return getAmbience(biome) + ".loop";
	}

	private String getAmbienceMood(BiomeGenBase biome) {
		if(biome == null) {
			return null;
		}
		return getAmbience(biome) + ".mood";
	}

	private String getAmbienceAdditions(BiomeGenBase biome) {
		if(biome == null) {
			return null;
		}
		return getAmbience(biome) + ".additions";
	}

	private String getMusic(BiomeGenBase biome) {
		if(biome == null) {
			return null;
		}
		return Reference.MCv118 + ":music.nether." + getStringFor(biome);
	}
	
	@SubscribeEvent
	public void toolTipEvent(ItemTooltipEvent event) {
		if(ConfigFunctions.enableExtraF3HTooltips && event.showAdvancedItemTooltips) {
			event.toolTip.add("\u00a78" + Item.itemRegistry.getNameForObject(event.itemStack.getItem()));
			if(event.itemStack.stackTagCompound != null && !event.itemStack.stackTagCompound.hasNoTags()) {
				event.toolTip.add("\u00a78NBT: " + event.itemStack.stackTagCompound.func_150296_c().size() + " Tag(s)");
			}
		}
	}
	
	@SubscribeEvent
	public void renderPlayerEventPre(RenderPlayerEvent.Pre event) {
		if (ConfigFunctions.enableTransparentAmour) {
			OpenGLHelper.enableBlend();
			OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
	}

	@SubscribeEvent
	public void renderPlayerSetArmour(SetArmorModel event) {
		if (ConfigFunctions.enableTransparentAmour) {
			OpenGLHelper.enableBlend();
			OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
	}

	@SubscribeEvent
	public void renderPlayerEventPost(RenderPlayerEvent.Post event) {
		if (ConfigFunctions.enableTransparentAmour)
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
			if (ConfigWorld.enableNewBlocksSounds && eventwithprefix.length > 1 &&
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
					float soundVol = (block.stepSound.getVolume() + 1.0F) / (eventwithprefix[0].contains("step") ? 8F : 2F);
					float soundPit = (block.stepSound.getPitch()) * (eventwithprefix[0].contains("step") ? 0.5F : 0.8F);

					boolean step = event.name.contains("step");
					
					event.result = new PositionedSoundRecord(new ResourceLocation(step ? ModSounds.soundNetherBricks.getStepResourcePath() : ModSounds.soundNetherBricks.getBreakSound()), soundVol, soundPit, x + 0.5F, y + 0.5F, z + 0.5F);
					return;
				}
			}
			
			if(ConfigWorld.enableNewMiscSounds) {
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
							s = Reference.MCv118 + ":" + "block.ender_chest." + (event.name.contains("close") ? "close" : "open");
						else if(block.getMaterial().equals(Material.wood) && event.name.contains("close"))
							s = Reference.MCv118 + ":" + "block.chest.close";
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
						String music = getMusic(mc.theWorld.getChunkFromBlockCoords((int)mc.thePlayer.posX, (int)mc.thePlayer.posZ).getBiomeGenForWorldCoords((int)mc.thePlayer.posX & 15, (int)mc.thePlayer.posZ & 15, mc.theWorld.getWorldChunkManager()));
						if(music != null) {
							netherMusic = PositionedSoundRecord.func_147673_a(new ResourceLocation(music));
							event.result = netherMusic;
						}
					} else {
						event.result = null;
					}
				}
			}
			
			if(ConfigWorld.enableNewAmbientSounds) {
				if (event.name.equals("ambient.weather.rain")) {
					//World world = cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient(); // unused variable
					int x = MathHelper.floor_float(event.sound.getXPosF());
					int y = MathHelper.floor_float(event.sound.getYPosF());
					int z = MathHelper.floor_float(event.sound.getZPosF());
					event.result = new PositionedSoundRecord(new ResourceLocation(Reference.MCv118 + ":" + "weather.rain" + (event.sound.getPitch() < 1.0F ? ".above" : "")), 
							event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
				} else if (event.name.equals("ambient.cave.cave")) {
					int x = MathHelper.floor_float(event.sound.getXPosF());
					int y = MathHelper.floor_float(event.sound.getYPosF());
					int z = MathHelper.floor_float(event.sound.getZPosF());
					if(ConfigWorld.enableNetherAmbience && cpw.mods.fml.client.FMLClientHandler.instance().getClientPlayerEntity().dimension == -1) {
						BiomeGenBase biome = cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient().getChunkFromBlockCoords(x, z).getBiomeGenForWorldCoords(x & 15, z & 15, cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient().getWorldChunkManager());
						if(getAmbienceMood(biome) != null) {
							event.result = new PositionedSoundRecord(new ResourceLocation(getAmbienceMood(biome)), 
									event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
						}
						else {
							event.result = null;
						}
					} else if(new Random().nextInt(19) >= 13) {
						event.result = new PositionedSoundRecord(new ResourceLocation(Reference.MOD_ID + ":ambient.cave"), 
								event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlaySoundAtEntityEvent(PlaySoundAtEntityEvent event)
	{
		if (ConfigWorld.enableNewBlocksSounds && event.name != null
				&& event.entity != null && event.name.contains("step") && event.name.equals("step.stone")
				&& event.name.contains(".")) {
			String[] eventwithprefix = event.name.split("\\.");
			if(eventwithprefix.length > 1) {
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
					event.name = ModSounds.soundNetherBricks.getBreakSound();
					return;
				}
			}
		}
	}
	
	private String getReplacementDoorSound(Block block, String string) {
		Random random = new Random();
		String closeOrOpen = random.nextBoolean() ? "open" : "close";
		if(block instanceof BlockDoor)
			if (block.getMaterial() == Material.wood/* || block.getMaterial() == EtFuturum.netherwood */)
				return Reference.MCv118 + ":block.wooden_door." + closeOrOpen;
			else if(block.getMaterial() == Material.iron)
				return Reference.MCv118 + ":block.iron_door." + closeOrOpen;
		
		if(block instanceof BlockTrapDoor)
			if (block.getMaterial() == Material.wood/* || block.getMaterial() == EtFuturum.netherwood */)
				return Reference.MCv118 + ":block.wooden_trapdoor." + closeOrOpen;
			else if(block.getMaterial() == Material.iron)
				return Reference.MCv118 + ":block.iron_trapdoor." + closeOrOpen;
		
		if(block instanceof BlockFenceGate)
			if (block.getMaterial() == Material.wood/* || block.getMaterial() == EtFuturum.netherwood */)
				return Reference.MCv118 + ":block.fence_gate." + closeOrOpen;
				
		return string;
	}
	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event)
	{
		Entity entity = event.entityLiving;
		World world = entity.worldObj;

		/*
		 * The purpose of the function is to manifest sprint particles
		 * and adjust slipperiness when entity is moving on block, so check
		 * that the conditions are met first.
		 */
		if (entity.onGround && (entity.motionX != 0 || entity.motionZ != 0))
		{
			int x = MathHelper.floor_double(entity.posX);
			int y = MathHelper.floor_double(entity.posY - 0.20000000298023224D - entity.yOffset);
			int z = MathHelper.floor_double(entity.posZ);
			
			if(entity.worldObj.getBlock(x, y, z) instanceof BlockShulkerBox) {
				TileEntityShulkerBox TE = (TileEntityShulkerBox) entity.worldObj.getTileEntity(x, y, z);
				if (TE != null) {
					if (world.isRemote && entity.isSprinting() && !entity.isInWater()) {
						EntityDiggingFX dig = new EntityDiggingFX(world, entity.posX + (entity.worldObj.rand.nextFloat() - 0.5D) * entity.width, entity.boundingBox.minY + 0.1D, entity.posZ + (entity.worldObj.rand.nextFloat() - 0.5D) * entity.width, -entity.motionX * 4.0D, 1.5D, -entity.motionZ * 4.0D, TE.getBlockType(), 0);
						dig.setParticleIcon(((BlockShulkerBox)TE.getBlockType()).colorIcons[TE.color]);
						Minecraft.getMinecraft().effectRenderer.addEffect((dig).applyColourMultiplier(x, y, z));
					}
				}
			}
		}
	}

	public static int main_menu_display_count = 0;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void openMainMenu(GuiOpenEvent event)
	{
		if(event != null && event.gui instanceof GuiMainMenu) {
			this.showedDebugWarning = false;
			
			if (EtFuturumMixinPlugin.launchConfigWarning && main_menu_display_count++ < 20)
			{
				EtFuturumMixinPlugin.launchConfigWarning = false;
				Configuration oldConfig = new Configuration(new File(Launch.minecraftHome, ConfigBase.PATH));
				oldConfig.setCategoryComment("warned", "This is added if we've warned you this file exists.\nUsed by versions that split the config into different files, rendering this file unused.\nThis was done because the current file was becoming difficult to navigate.");
				if(!oldConfig.getBoolean("configWarningShown", "warned", false, "")) {
					event.gui = new GuiConfigWarning(event.gui, oldConfig);
				}
				oldConfig.getCategory("warned").get("configWarningShown").comment = "";
				oldConfig.save();
				
				return;
			}
		}
	}

}