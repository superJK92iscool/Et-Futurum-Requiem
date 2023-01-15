package ganymedes01.etfuturum.core.handlers;

import static ganymedes01.etfuturum.spectator.SpectatorMode.isSpectator;

import java.io.File;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

import org.apache.commons.lang3.tuple.MutablePair;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.EtFuturumMixinPlugin;
import ganymedes01.etfuturum.api.elytra.IElytraPlayer;
import ganymedes01.etfuturum.blocks.BlockShulkerBox;
import ganymedes01.etfuturum.blocks.IMultiStepSound;
import ganymedes01.etfuturum.client.OpenGLHelper;
import ganymedes01.etfuturum.client.gui.GuiConfigWarning;
import ganymedes01.etfuturum.client.gui.GuiGamemodeSwitcher;
import ganymedes01.etfuturum.client.sound.ElytraSound;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.client.sound.NetherAmbienceLoop;
import ganymedes01.etfuturum.client.sound.NetherAmbienceSound;
import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.entities.EntityNewBoatWithChest;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.network.ChestBoatOpenInventoryMessage;
import ganymedes01.etfuturum.tileentities.TileEntityShulkerBox;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
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
	public static boolean showDebugWarning;
	/*
	 * Represents the two values that govern the last chime age in 1.17 and up.
	 * Left = field_26997 (Seems to be related to pitch)
	 * Right = lastChimeAge
	 */
	private static final Map<Entity, MutablePair<Float, Integer>> amethystChimeCache = new WeakHashMap();

	private static final boolean forceHideSnapshotWarning = Boolean.parseBoolean(System.getProperty("etfuturum.hideSnapshotWarning"));

	
	private ClientEventHandler() {
	}
	
	@SideOnly(Side.CLIENT)
	NetherAmbienceLoop ambienceLoop = null;
	@SideOnly(Side.CLIENT)
	int ticksToNextAmbience = rand.nextInt(80) + 1;
	@SideOnly(Side.CLIENT)
	BiomeGenBase ambienceBiome = null;

	private boolean wasShowingDebugInfo, wasShowingProfiler;
	private boolean pressedF3;
	private boolean eligibleForDebugInfoSwap = false;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if(ConfigFunctions.enableNewF3Behavior) {
			Minecraft mc = FMLClientHandler.instance().getClient();
			if(Keyboard.getEventKey() == Keyboard.KEY_F3) {
				pressedF3 = Keyboard.getEventKeyState();
				if(pressedF3)
					eligibleForDebugInfoSwap = true;
				if(!pressedF3 && eligibleForDebugInfoSwap) {
					mc.gameSettings.showDebugInfo = !mc.gameSettings.showDebugInfo;
					mc.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
				}
			} else if(Keyboard.getEventKeyState()) {
				/* Another key changed states besides F3 */
				int key = Keyboard.getEventKey();
				if(key != Keyboard.KEY_LSHIFT && key != Keyboard.KEY_RSHIFT)
					eligibleForDebugInfoSwap = false;
			}
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event)
	{
		World world = FMLClientHandler.instance().getWorldClient();
		EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
		Minecraft mc = FMLClientHandler.instance().getClient();
		
		if(world == null || event.phase == Phase.START || Minecraft.getMinecraft().isGamePaused()) {
			return;
		}
		
		if(!EtFuturum.DEV_ENVIRONMENT && EtFuturum.SNAPSHOT_BUILD && !showedDebugWarning && player.ticksExisted == 40) {
			if(!forceHideSnapshotWarning) {
				ChatComponentText text = new ChatComponentText("\u00a7c\u00a7l[Debug]: \u00a7rYou are using a pre-release version of \u00a7bEt \u00a7bFuturum \u00a7bRequiem\u00a7r. This version might not be stable, click here to go to GitHub to report bugs.");
				text.getChatStyle().setChatClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/Roadhog360/Et-Futurum-Requiem/issues"));
				player.addChatComponentMessage(text);
			} else {
				System.out.println("WARNING: a pre-release version of Et Futurum Requiem is in use! This build may not be stable, expect more bugs than usual.");
				System.out.println("Be sure to report bugs at https://github.com/Roadhog360/Et-Futurum-Requiem/issues");
			}
			showedDebugWarning = true;
		}

		if(ConfigWorld.enableNetherAmbience && !EtFuturum.netherAmbienceNetherlicious && player.dimension == -1) {
			Chunk chunk = world.getChunkFromBlockCoords(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posZ));
			if(!chunk.isChunkLoaded) {
				if(ambienceLoop != null && FMLClientHandler.instance().getClient().getSoundHandler().isSoundPlaying(ambienceLoop)) {
					FMLClientHandler.instance().getClient().getSoundHandler().stopSound(ambienceLoop);
				}
				ambienceLoop = null;
				ambienceBiome = null;
				return;
			}

			int x = MathHelper.floor_double(player.posX);
			//int y = MathHelper.floor_double(player.posY); // unused variable
			int z = MathHelper.floor_double(player.posZ);
			ambienceBiome = world.getBiomeGenForCoords(x, z);
			
			String soundLoc = "";
			if(ambienceLoop != null) {
				soundLoc = ambienceLoop.getPositionedSoundLocation().getResourceDomain() + ":" + ambienceLoop.getPositionedSoundLocation().getResourcePath();
			}
			
			if(getAmbienceLoop(ambienceBiome) != null  && !mc.getSoundHandler().isSoundPlaying(ambienceLoop)) {
				Boolean flag = ambienceLoop == null || ambienceLoop.getVolume() <= 0;
					ambienceLoop = new NetherAmbienceLoop(getAmbienceLoop(ambienceBiome));
					mc.getSoundHandler().playSound(ambienceLoop);
				if(flag) {
					ambienceLoop.fadeIn();
				}
			} else if (ambienceBiome == null || getAmbienceLoop(ambienceBiome) == null || !soundLoc.equals(getAmbienceLoop(ambienceBiome))) {
				ambienceLoop.stop();
			} else if (mc.getSoundHandler().isSoundPlaying(ambienceLoop) && ambienceLoop.isStopping && soundLoc.equals(getAmbienceLoop(ambienceBiome))){
				ambienceLoop.isStopping = false;
			}
			if(getAmbienceAdditions(ambienceBiome) != null && ambienceLoop != null && ticksToNextAmbience-- <= 0) {
				Minecraft.getMinecraft().getSoundHandler().playSound(new NetherAmbienceSound(new ResourceLocation(getAmbienceAdditions(ambienceBiome))));
				ticksToNextAmbience = 50 + rand.nextInt(30) + 1;
			}
		}

		if(ConfigFunctions.enableGamemodeSwitcher && Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_F3) && Keyboard.isKeyDown(Keyboard.KEY_F4)) {
			if(mc.currentScreen == null) {
				mc.displayGuiScreen(new GuiGamemodeSwitcher());
			}
		}

		if(ConfigFunctions.enableNewF3Behavior && mc.gameSettings.showDebugInfo != wasShowingDebugInfo && Keyboard.isKeyDown(Keyboard.KEY_F3)) {
			mc.gameSettings.showDebugInfo = wasShowingDebugInfo;
			mc.gameSettings.showDebugProfilerChart = wasShowingProfiler;
		}
		wasShowingDebugInfo = mc.gameSettings.showDebugInfo;
		wasShowingProfiler = mc.gameSettings.showDebugProfilerChart;
	}
	private String getStringFor(BiomeGenBase biome) {
		if(biome == null)
			return null;
		
//      if(string.contains("basalt_deltas")) {
//          return basaltDeltas;
//      }
//      if(string.contains("warped_forest")) {
//          return warpedForest;
//      }
//      if(string.contains("crimson_forest")) {
//          return crimsonForest;
//      }
//      if(string.contains("soul_sand_valley")) {
//          return soulSandValley;
//      }
		return "nether_wastes";
		
	}

	private String getAmbience(BiomeGenBase biome) {
		if(biome == null)
			return null;
		
//      if(string.contains("basalt_deltas")) {
//          return basaltDeltas;
//      }
//      if(string.contains("warped_forest")) {
//          return warpedForest;
//      }
//      if(string.contains("crimson_forest")) {
//          return crimsonForest;
//      }
//      if(string.contains("soul_sand_valley")) {
//          return soulSandValley;
//      }
		return Reference.MCAssetVer + ":ambient." + getStringFor(biome);
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
		return Reference.MCAssetVer + ":music.nether." + getStringFor(biome);
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
		if(isSpectator(event.entityPlayer)) {
			event.result = 0;
		} else
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
			World world = cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient();
			int x = MathHelper.floor_float(event.sound.getXPosF());
			int y = MathHelper.floor_float(event.sound.getYPosF());
			int z = MathHelper.floor_float(event.sound.getZPosF());
			Block block = world.getBlock(x, y, z);
			
			String[] eventwithprefix = event.name.split("\\.");
			if (ConfigWorld.enableNewBlocksSounds && eventwithprefix.length > 1 &&
					eventwithprefix[1].equals(Blocks.stone.stepSound.soundName)
					&& event.sound.getPitch() < 1.0F) {
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
			
			if(event.sound.getPitch() < 1.0F && world.getBlock(x, y, z) instanceof IMultiStepSound && (!((IMultiStepSound)block).requiresNewBlockSounds() || ConfigWorld.enableNewBlocksSounds)) {
				
				IMultiStepSound multiSoundBlock = (IMultiStepSound)block;
				Block.SoundType blockSound = block.stepSound;
				Block.SoundType newSound = multiSoundBlock.getStepSound(world, x, y, z, world.getBlockMetadata(x, y, z));
				
				//Check if we're replacing a step sound, a break sound or a place sound? We also truncate the mod prefix so the string can actually match
				//We'll use a variable to store the current block sound and truncate the prefix if it has one.
				String newSoundString = null;
				
				String[][] blockSounds = new String[][] {{blockSound.getStepResourcePath(), blockSound.getBreakSound(), blockSound.func_150496_b()},
					{newSound.getStepResourcePath(), newSound.getBreakSound(), newSound.func_150496_b()}};
					
				for(int i = 0; i < blockSounds[0].length; i++) {
					String currentSound = blockSounds[0][i];
					int index = currentSound.indexOf(':');
					if(index != -1) {
						currentSound = currentSound.substring(index+1);
					}
					if(event.name.equals(currentSound)) {
						newSoundString = blockSounds[1][i];
						break;
					}
				}
				
				if(newSoundString != null) {
					float soundVol = (block.stepSound.getVolume() + 1.0F) / (event.name.contains("step") ? 8F : 2F);
					float soundPit = (block.stepSound.getPitch()) * (event.name.contains("step") ? 0.5F : 0.8F);
					event.result = new PositionedSoundRecord(new ResourceLocation(newSoundString), soundVol, soundPit, x + 0.5F, y + 0.5F, z + 0.5F);
				}
				return;
			}
			
			if(ConfigWorld.enableNewMiscSounds) {
				if(event.name.contains("random.door")) {
					event.result = new PositionedSoundRecord(new ResourceLocation(getReplacementDoorSound(block, event.name)),
							event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
				} else if (event.name.contains("random.chest")) {

					String s = event.name;
					String blockID = Block.blockRegistry.getNameForObject(block).split(":")[1].toLowerCase();
					if(blockID.contains("chest") && (event.name.contains("open") || event.name.contains("close"))) {
						if((blockID.contains("ender") && block.getMaterial().equals(Material.rock)))
							s = Reference.MCAssetVer + ":" + "block.ender_chest." + (event.name.contains("close") ? "close" : "open");
						else if(block.getMaterial().equals(Material.wood) && event.name.contains("close"))
							s = Reference.MCAssetVer + ":" + "block.chest.close";
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
					event.result = new PositionedSoundRecord(new ResourceLocation(Reference.MCAssetVer + ":" + "weather.rain" + (event.sound.getPitch() < 1.0F ? ".above" : "")), 
							event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
				} else if (event.name.equals("ambient.cave.cave")) {
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
		if(!event.entity.worldObj.isRemote) return;
		
		if(event.name != null && event.entity != null) {
			int x = MathHelper.floor_double(event.entity.posX);
			int y = MathHelper.floor_double(event.entity.posY - 0.20000000298023224D - event.entity.yOffset);
			int z = MathHelper.floor_double(event.entity.posZ);
			World world = cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient();
			Block block = world.getBlock(x, y, z);
			
			if(world.getBlock(x, y, z) instanceof IMultiStepSound && (!((IMultiStepSound)block).requiresNewBlockSounds() || ConfigWorld.enableNewBlocksSounds)) {
				if(event.name.equals(block.stepSound.getStepResourcePath().substring(block.stepSound.getStepResourcePath().indexOf(':')+1))) {
					Block.SoundType stepSound = ((IMultiStepSound)block).getStepSound(world, x, y, z, world.getBlockMetadata(x, y, z));
					
					event.name = stepSound.getStepResourcePath();
					return;
				}
			} else
			if(ConfigWorld.enableNewBlocksSounds) {
				if(event.name.equals(Block.soundTypePiston.getStepResourcePath())) {
					String[] eventwithprefix = event.name.split("\\.");
					if(eventwithprefix.length > 1) {
						Item itemblock = Item.getItemFromBlock(block);
						if(itemblock == null)
							return;
						String name = itemblock.getUnlocalizedName(new ItemStack(itemblock, 1, world.getBlockMetadata(x, y, z) % 8)).toLowerCase();
						if(name.contains("slab") && name.contains("nether") && name.contains("brick")) {
							event.name = ModSounds.soundNetherBricks.getStepResourcePath();
							return;
						}
					}
				} else if(ModSounds.soundAmethystBlock.getStepResourcePath().equals(event.name)) {
					MutablePair<Float, Integer> pair = amethystChimeCache.get(event.entity);
					if(pair == null) {
						pair = new MutablePair(0.0F, 0);
					}
					float field_26997 = pair.getLeft();
					int lastChimeAge = pair.getRight();
					if (event.entity.ticksExisted >= lastChimeAge + 20) {
						field_26997 = (float)((double)field_26997 * Math.pow(0.996999979019165D, (double)(event.entity.ticksExisted - lastChimeAge)));
						field_26997 = Math.min(1.0F, field_26997 + 0.07F);
						float f = 0.5F + field_26997 * event.entity.worldObj.rand.nextFloat() * 1.2F;
						float g = 0.1F + field_26997 * 1.2F;
						event.entity.playSound(Reference.MCAssetVer + ":block.amethyst_block.chime", g, f);
						lastChimeAge = event.entity.ticksExisted;
						pair.setLeft(field_26997);
						pair.setRight(lastChimeAge);
						amethystChimeCache.put(event.entity, pair);
					}
				}
			}
		}
	}
	
	private String getReplacementDoorSound(Block block, String string) {
		Random random = new Random();
		String closeOrOpen = random.nextBoolean() ? "open" : "close";
		if(block instanceof BlockDoor)
			if (block.getMaterial() == Material.wood/* || block.getMaterial() == EtFuturum.netherwood */)
				return Reference.MCAssetVer + ":block.wooden_door." + closeOrOpen;
			else if(block.getMaterial() == Material.iron)
				return Reference.MCAssetVer + ":block.iron_door." + closeOrOpen;
		
		if(block instanceof BlockTrapDoor)
			if (block.getMaterial() == Material.wood/* || block.getMaterial() == EtFuturum.netherwood */)
				return Reference.MCAssetVer + ":block.wooden_trapdoor." + closeOrOpen;
			else if(block.getMaterial() == Material.iron)
				return Reference.MCAssetVer + ":block.iron_trapdoor." + closeOrOpen;
		
		if(block instanceof BlockFenceGate)
			if (block.getMaterial() == Material.wood/* || block.getMaterial() == EtFuturum.netherwood */)
				return Reference.MCAssetVer + ":block.fence_gate." + closeOrOpen;
				
		return string;
	}

	private float prevYOffset;
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		if (!ConfigMixins.enableElytra)
			return;
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		if(!(player instanceof IElytraPlayer))
			return;
		if(((IElytraPlayer)player).etfu$isElytraFlying()) {
			if(event.phase == Phase.START) {
				prevYOffset = player.yOffset;
				/* TODO find the right number here */
				if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 0)
					player.yOffset = 3.02f;
			} else {
				player.yOffset = prevYOffset;
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event)
	{
		Entity entity = event.entityLiving;
		World world = entity.worldObj;

		if (ConfigMixins.enableElytra && entity instanceof EntityPlayerSP) {
			IElytraPlayer e = (IElytraPlayer) entity;
			if (e.etfu$isElytraFlying() && !e.etfu$lastElytraFlying()) {
				Minecraft.getMinecraft().getSoundHandler().playSound(new ElytraSound((EntityPlayerSP) entity));
			}
			/* lastElytraFlying is set by the shared handler in ServerEventHandler */
		}
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
		if(event.gui instanceof GuiMainMenu) {
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
		} else if(ConfigBlocksItems.enableNewBoats && event.gui instanceof GuiInventory) {
			if(Minecraft.getMinecraft().thePlayer.ridingEntity instanceof EntityNewBoatWithChest) {
				event.setCanceled(true);
				EtFuturum.networkWrapper.sendToServer(new ChestBoatOpenInventoryMessage());
			}
		}
	}

}