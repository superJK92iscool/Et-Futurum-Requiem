package ganymedes01.etfuturum.core.handlers;

import com.google.common.collect.Lists;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.api.MultiBlockSoundRegistry;
import ganymedes01.etfuturum.api.mappings.MultiBlockSoundContainer;
import ganymedes01.etfuturum.blocks.BlockShulkerBox;
import ganymedes01.etfuturum.client.OpenGLHelper;
import ganymedes01.etfuturum.client.gui.GuiConfigWarning;
import ganymedes01.etfuturum.client.gui.GuiGamemodeSwitcher;
import ganymedes01.etfuturum.client.sound.*;
import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.elytra.IElytraPlayer;
import ganymedes01.etfuturum.entities.EntityBee;
import ganymedes01.etfuturum.entities.EntityNewBoatWithChest;
import ganymedes01.etfuturum.items.ItemHoneyBottle;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.network.ChestBoatOpenInventoryMessage;
import ganymedes01.etfuturum.tileentities.TileEntityShulkerBox;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
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
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.apache.commons.lang3.tuple.MutablePair;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

import static ganymedes01.etfuturum.spectator.SpectatorMode.isSpectator;

public class ClientEventHandler {

	public static final ClientEventHandler INSTANCE = new ClientEventHandler();
	private final Minecraft mc = FMLClientHandler.instance().getClient();
	private final Random rand = new Random();
	private boolean showedDebugWarning;
	private int currPage;
	/**
	 * Represents the two values that govern the last chime age in 1.17 and up.
	 * Left = field_26997 (Seems to be related to pitch)
	 * Right = lastChimeAge
	 */
	private static final Map<Entity, MutablePair<Float, Integer>> AMETHYST_CHIME_CACHE = new WeakHashMap<>();
	/**
	 * Used by sound events to get the unlocalized name for the specific state of a block. This is handled on the item's end of things.
	 * So I use this "storage" stack to store the block I want the meta-name for, so I don't create new ItemStack instances constantly.
	 */
	private static final ItemStack STORAGE_STACK = new ItemStack((Item) null, 1, 0);

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
	private boolean eligibleForDebugInfoSwap = false;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (ConfigFunctions.enableNewF3Behavior) {
			if (Keyboard.getEventKey() == Keyboard.KEY_F3) {
				boolean pressedF3 = Keyboard.getEventKeyState();
				if (pressedF3)
					eligibleForDebugInfoSwap = true;
				if (!pressedF3 && eligibleForDebugInfoSwap) {
					mc.gameSettings.showDebugInfo = !mc.gameSettings.showDebugInfo;
					mc.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
				}
			} else if (Keyboard.getEventKeyState()) {
				/* Another key changed states besides F3 */
				int key = Keyboard.getEventKey();
				if (key != Keyboard.KEY_LSHIFT && key != Keyboard.KEY_RSHIFT)
					eligibleForDebugInfoSwap = false;
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event) {
		World world = FMLClientHandler.instance().getWorldClient();
		EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();

		if (world == null || event.phase == Phase.START || mc.isGamePaused()) {
			return;
		}

		if (!EtFuturum.DEV_ENVIRONMENT && EtFuturum.SNAPSHOT_BUILD && !showedDebugWarning && player.ticksExisted == 40) {
			if (!forceHideSnapshotWarning) {
				ChatComponentText text = new ChatComponentText("\u00a7c\u00a7l[Debug]: \u00a7rYou are using a pre-release version of \u00a7bEt \u00a7bFuturum \u00a7bRequiem\u00a7r. This version might not be stable, click here to go to GitHub to report bugs.");
				text.getChatStyle().setChatClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/Roadhog360/Et-Futurum-Requiem/issues"));
				player.addChatComponentMessage(text);
			} else {
				System.out.println("WARNING: a pre-release version of Et Futurum Requiem is in use! This build may not be stable, expect more bugs than usual.");
				System.out.println("Be sure to report bugs at https://github.com/Roadhog360/Et-Futurum-Requiem/issues");
			}
			showedDebugWarning = true;
		}

		applyNextEntitySound();

		if (ConfigSounds.netherAmbience && !EtFuturum.netherAmbienceNetherlicious && world.provider.dimensionId == -1) {
			Chunk chunk = world.getChunkFromBlockCoords(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posZ));
			if (!chunk.isChunkLoaded) {
				if (ambienceLoop != null && mc.getSoundHandler().isSoundPlaying(ambienceLoop)) {
					mc.getSoundHandler().stopSound(ambienceLoop);
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
			if (ambienceLoop != null) {
				soundLoc = ambienceLoop.getPositionedSoundLocation().getResourceDomain() + ":" + ambienceLoop.getPositionedSoundLocation().getResourcePath();
			}

			if (getAmbienceLoop(ambienceBiome) != null && !mc.getSoundHandler().isSoundPlaying(ambienceLoop)) {
				ambienceLoop = new NetherAmbienceLoop(getAmbienceLoop(ambienceBiome));
				mc.getSoundHandler().playSound(ambienceLoop);
			} else if (ambienceBiome == null || !soundLoc.equals(getAmbienceLoop(ambienceBiome))) {
				ambienceLoop.stop();
			} else if (mc.getSoundHandler().isSoundPlaying(ambienceLoop) && ambienceLoop.isStopping && soundLoc.equals(getAmbienceLoop(ambienceBiome))) {
				ambienceLoop.isStopping = false;
			}
			if (getAmbienceAdditions(ambienceBiome) != null && ambienceLoop != null && ticksToNextAmbience-- <= 0) {
				mc.getSoundHandler().playSound(new NetherAmbienceSound(new ResourceLocation(getAmbienceAdditions(ambienceBiome))));
				ticksToNextAmbience = 50 + rand.nextInt(30) + 1;
			}
		}

		if (ConfigFunctions.enableGamemodeSwitcher && Keyboard.isCreated() && Keyboard.isKeyDown(Keyboard.KEY_F3) && Keyboard.isKeyDown(Keyboard.KEY_F4)) {
			if (mc.currentScreen == null) {
				mc.displayGuiScreen(new GuiGamemodeSwitcher());
			}
		}

		if (ConfigFunctions.enableNewF3Behavior && mc.gameSettings.showDebugInfo != wasShowingDebugInfo && Keyboard.isKeyDown(Keyboard.KEY_F3)) {
			mc.gameSettings.showDebugInfo = wasShowingDebugInfo;
			mc.gameSettings.showDebugProfilerChart = wasShowingProfiler;
		}
		wasShowingDebugInfo = mc.gameSettings.showDebugInfo;
		wasShowingProfiler = mc.gameSettings.showDebugProfilerChart;

		if (mc.currentScreen == null && currPage > -1) {
			currPage = -1;
		}
	}

	private String getStringFor(BiomeGenBase biome) {
		if (biome == null)
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
		if (biome == null)
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
		if (biome == null) {
			return null;
		}
		return getAmbience(biome) + ".loop";
	}

	private String getAmbienceMood(BiomeGenBase biome) {
		if (biome == null) {
			return null;
		}
		return getAmbience(biome) + ".mood";
	}

	private String getAmbienceAdditions(BiomeGenBase biome) {
		if (biome == null) {
			return null;
		}
		return getAmbience(biome) + ".additions";
	}

	private String getMusic(BiomeGenBase biome) {
		if (biome == null) {
			return null;
		}
		return Reference.MCAssetVer + ":music.nether." + getStringFor(biome);
	}

	@SubscribeEvent
	public void toolTipEvent(ItemTooltipEvent event) {
		if (ConfigFunctions.enableExtraF3HTooltips && event.showAdvancedItemTooltips) {
			event.toolTip.add("\u00a78" + Item.itemRegistry.getNameForObject(event.itemStack.getItem()));
			if (event.itemStack.stackTagCompound != null && !event.itemStack.stackTagCompound.hasNoTags()) {
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
		if (isSpectator(event.entityPlayer)) {
			event.result = 0;
		} else if (ConfigFunctions.enableTransparentAmour) {
			OpenGLHelper.enableBlend();
			OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
	}

	@SubscribeEvent
	public void renderPlayerEventPost(RenderPlayerEvent.Post event) {
		if (ConfigFunctions.enableTransparentAmour) {
			OpenGLHelper.disableBlend();
		}
	}

	PositionedSound netherMusic;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlaySoundEvent(PlaySoundEvent17 event) {
		if (event.sound != null && event.name != null && FMLClientHandler.instance().getWorldClient() != null) {
			final World world = FMLClientHandler.instance().getWorldClient();
			final float soundX = event.sound.getXPosF();
			final float soundY = event.sound.getYPosF();
			final float soundZ = event.sound.getZPosF();
			final int x = MathHelper.floor_float(soundX);
			final int y = MathHelper.floor_float(soundY);
			final int z = MathHelper.floor_float(soundZ);
			final Block block = world.getBlock(x, y, z);

			final boolean hitSound = block.stepSound.getStepResourcePath().endsWith(event.name);
			final boolean breakSound = block.stepSound.getBreakSound().endsWith(event.name);
			final boolean placeSound = block.stepSound.func_150496_b().endsWith(event.name);

			if (MultiBlockSoundRegistry.multiBlockSounds.containsKey(block) && (hitSound || breakSound || placeSound)) {
				MultiBlockSoundContainer obj = MultiBlockSoundRegistry.multiBlockSounds.get(block);
				MultiBlockSoundRegistry.BlockSoundType type;
				if (hitSound) {
					type = MultiBlockSoundRegistry.BlockSoundType.HIT;
				} else if (placeSound) {
					type = MultiBlockSoundRegistry.BlockSoundType.PLACE;
				} else {
					type = MultiBlockSoundRegistry.BlockSoundType.BREAK;
				}
				String newSoundString = obj.getSound(world, x, y, z, event.name, type);
				float volume = obj.getVolume(world, x, y, z, event.sound.getVolume(), type);
				float pitch = obj.getPitch(world, x, y, z, event.sound.getPitch(), type);
				if (newSoundString != null || volume != -1 || pitch != -1) {
					if (newSoundString == null) newSoundString = event.name;
					if (volume == -1) volume = event.sound.getVolume();
					if (pitch == -1) pitch = event.sound.getPitch();
					event.result = new PositionedSoundRecord(new ResourceLocation(newSoundString), volume, pitch, soundX, soundY, soundZ);
					return;
				}
			}


			//Opening and closing doors/chests
			if (ConfigSounds.doorOpenClose && event.name.contains("random.door")) {
				event.result = new PositionedSoundRecord(new ResourceLocation(getReplacementDoorSound(block, event.name)),
						event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
				return;
			}
			if (ConfigSounds.chestOpenClose && event.name.contains("random.chest")) {
				String s = event.name;
				String blockID = Block.blockRegistry.getNameForObject(block).split(":")[1].toLowerCase();
				if (blockID.contains("chest") && (event.name.contains("open") || event.name.contains("close"))) {
					if ((blockID.contains("ender") && block.getMaterial().equals(Material.rock)))
						s = Reference.MCAssetVer + ":" + "block.ender_chest." + (event.name.contains("close") ? "close" : "open");
					else if (block.getMaterial().equals(Material.wood) && event.name.contains("close"))
						s = Reference.MCAssetVer + ":" + "block.chest.close";
				}

				if (!s.equals(event.name)) {
					event.result = new PositionedSoundRecord(new ResourceLocation(s), event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
					return;
				}
			}

			//We check what sound event to use by the pitch. These blocks fire 0.6F when turning on, and 0.5F when turning off. We use > 0.5F instead of == 0.6F to account for floating point precision.w
			if (ConfigSounds.pressurePlateButton) {
				// --- Wooden Button --- //
				if (block instanceof BlockButton && event.name.equals("random.click")) {
					String s = null;
					if (block.stepSound == Block.soundTypeWood) {
						s = Reference.MCAssetVer + ":block.wooden_button.click";
					} else if (block.stepSound == ModSounds.soundNetherWood) {
						s = Reference.MCAssetVer + ":block.nether_wood_button.click";
					}
					if (s != null) {
						event.result = new PositionedSoundRecord(new ResourceLocation(s + "_" + (event.sound.getPitch() > 0.5F ? "on" : "off")), 1, 1, soundX, soundY, soundZ);
					}
					return;
				}

				// --- Wooden/Metal Pressure plate --- //
				if (block instanceof BlockBasePressurePlate && event.name.equals("random.click")) {
					String material = block.getMaterial() == Material.wood ? "wooden" : "metal";

					String s = null;
					if (block.stepSound == Block.soundTypeMetal) {
						s = Reference.MCAssetVer + ":block.metal_pressure_plate.click";
					} else if (block.stepSound == Block.soundTypeWood) {
						s = Reference.MCAssetVer + ":block.wooden_pressure_plate.click";
					} else if (block.stepSound == ModSounds.soundNetherWood) {
						s = Reference.MCAssetVer + ":block.nether_wood_pressure_plate.click";
					}

					if (s != null) {
						event.result = new PositionedSoundRecord(new ResourceLocation(s + "_" + (event.sound.getPitch() > 0.5F ? "on" : "off")), 1, 1, soundX, soundY, soundZ);
					}
					return;
				}
			}

			// --- Note Blocks --- //
			if (ConfigSounds.noteBlockNotes && world.getBlock(MathHelper.floor_float(soundX), MathHelper.floor_float(soundY), MathHelper.floor_float(soundZ)) instanceof BlockNote &&
					(event.name.equals("note.harp") || event.name.equals("note.snare") || event.name.equals("note.hat") || event.name.equals("note.bd"))) {
				String instrumentToPlay = event.name;
				String blockName = "";

				Block blockBeneath = world.getBlock(
						MathHelper.floor_float(soundX),
						MathHelper.floor_float(soundY) - 1,
						MathHelper.floor_float(soundZ));
				Item item = Item.getItemFromBlock(blockBeneath);
				if (item != null && item.getHasSubtypes()) {
					try {
						STORAGE_STACK.func_150996_a(item);
						STORAGE_STACK.setItemDamage(world.getBlockMetadata(MathHelper.floor_float(soundX), MathHelper.floor_float(soundY), MathHelper.floor_float(soundZ)));
						blockName = item.getUnlocalizedName(STORAGE_STACK).toLowerCase();
					} catch (
							Exception e) {/*In case a mod doesn't have a catch for invalid meta states and throws an error, just ignore it and proceed*/}
				}

				if (blockName.equals("")) {
					blockName = blockBeneath.getUnlocalizedName().toLowerCase();
				}

				// Specific blocks
				if (blockBeneath == Blocks.soul_sand) {
					instrumentToPlay = Reference.MCAssetVer + ":block.note_block.cow_bell";
				} else if (blockName.contains("hay")) {
					instrumentToPlay = Reference.MCAssetVer + ":block.note_block.banjo";
				} else if (EtFuturum.hasDictTag(blockBeneath, "blockGold")) {
					instrumentToPlay = Reference.MCAssetVer + ":block.note_block.bell";
				} else if (EtFuturum.hasDictTag(blockBeneath, "blockEmerald")) {
					instrumentToPlay = Reference.MCAssetVer + ":block.note_block.bit";
				} else if (blockName.contains("packed") && blockName.contains("ice")) {
					instrumentToPlay = Reference.MCAssetVer + ":block.note_block.chime";
				} else if (blockName.contains("pumpkin")) {
					instrumentToPlay = Reference.MCAssetVer + ":block.note_block.didgeridoo";
				} else if (blockBeneath.getMaterial() == Material.clay) {
					instrumentToPlay = Reference.MCAssetVer + ":block.note_block.flute";
				} else if (EtFuturum.hasDictTag(blockBeneath, "blockIron")) {
					instrumentToPlay = Reference.MCAssetVer + ":block.note_block.iron_xylophone";
				} else if (blockBeneath.getMaterial() == Material.cloth) {
					instrumentToPlay = Reference.MCAssetVer + ":block.note_block.guitar";
				} else if (blockName.contains("bone") || blockName.contains("ivory")) {
					instrumentToPlay = Reference.MCAssetVer + ":block.note_block.xylophone";
				}
				if (event.name.equals(instrumentToPlay)) return;

				event.result = new PositionedSoundRecord(new ResourceLocation(instrumentToPlay), instrumentToPlay.equals(Reference.MCAssetVer + ":block.note_block.iron_xylophone") ? 1F : event.sound.getVolume(), event.sound.getPitch(), soundX, soundY, soundZ);
				return;
			}


			// --- Book page turn --- //
			if (mc.currentScreen instanceof GuiScreenBook && event.name.equals("gui.button.press")) {
				GuiScreenBook gui = (GuiScreenBook) mc.currentScreen;
				// If there is a disagreement on page, play the page-turning sound
				if (gui.currPage != this.currPage) {
					this.currPage = gui.currPage;
					EntityClientPlayerMP player = mc.thePlayer;
					player.playSound(Reference.MCAssetVer + ":item.book.page_turn", 1.0F, 1.0F);
					event.result = null;
					return;
				}
			}

			if (!EtFuturum.netherMusicNetherlicious) {
				if (event.name.equals("music.game.nether") && world.provider.dimensionId == -1) {
					if (netherMusic == null || !mc.getSoundHandler().isSoundPlaying(netherMusic)) {
						//World world = mc.theWorld; // unused variable
						String music = getMusic(mc.theWorld.getChunkFromBlockCoords((int) mc.thePlayer.posX, (int) mc.thePlayer.posZ).getBiomeGenForWorldCoords((int) mc.thePlayer.posX & 15, (int) mc.thePlayer.posZ & 15, mc.theWorld.getWorldChunkManager()));
						if (music != null) {
							netherMusic = PositionedSoundRecord.func_147673_a(new ResourceLocation(music));
							event.result = netherMusic;
						}
					} else {
						event.result = null;
					}
				}
			}

			if (ConfigSounds.rainSounds && event.name.equals("ambient.weather.rain")) {
				event.result = new PositionedSoundRecord(new ResourceLocation(Reference.MCAssetVer + ":weather.rain" + (event.sound.getPitch() < 1.0F ? ".above" : "")),
						event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
			}
			if (event.name.equals("ambient.cave.cave")) {
				if (ConfigSounds.netherAmbience && FMLClientHandler.instance().getClientPlayerEntity().dimension == -1) {
					BiomeGenBase biome = FMLClientHandler.instance().getWorldClient().getChunkFromBlockCoords(x, z).getBiomeGenForWorldCoords(x & 15, z & 15, FMLClientHandler.instance().getWorldClient().getWorldChunkManager());
					if (getAmbienceMood(biome) != null) {
						event.result = new PositionedSoundRecord(new ResourceLocation(getAmbienceMood(biome)),
								event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
					} else {
						event.result = null;
					}
				}
			}
		}
	}

	private String getReplacementDoorSound(Block block, String string) {
		Random random = new Random();
		String closeOrOpen = random.nextBoolean() ? "open" : "close";
		if (block instanceof BlockDoor) {
			if (block.getMaterial() == Material.wood) {
				if (block.stepSound == ModSounds.soundNetherWood) {
					return Reference.MCAssetVer + ":block.nether_wood_door." + closeOrOpen;
				}
				return Reference.MCAssetVer + ":block.wooden_door." + closeOrOpen;
			} else if (block.getMaterial() == Material.iron) {
				return Reference.MCAssetVer + ":block.iron_door." + closeOrOpen;
			}
		}

		if (block instanceof BlockTrapDoor) {
			if (block.getMaterial() == Material.wood) {
				if (block.stepSound == ModSounds.soundNetherWood) {
					return Reference.MCAssetVer + ":block.nether_wood_trapdoor." + closeOrOpen;
				}
				return Reference.MCAssetVer + ":block.wooden_trapdoor." + closeOrOpen;
			} else if (block.getMaterial() == Material.iron) {
				return Reference.MCAssetVer + ":block.iron_trapdoor." + closeOrOpen;
			}
		}

		if (block instanceof BlockFenceGate) {
			if (block.getMaterial() == Material.wood) {
				if (block.stepSound == ModSounds.soundNetherWood) {
					return Reference.MCAssetVer + ":block.nether_wood_fence_gate." + closeOrOpen;
				}
				return Reference.MCAssetVer + ":block.fence_gate." + closeOrOpen;
			}
		}

		return string;
	}

	private static final String ignore_suffix = "$etfuturum:ignore";

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlaySoundAtEntityEvent(PlaySoundAtEntityEvent event) {
		if (event.name == null) return; //Some mods fire null sounds, blech

		/**
		 * We have to do this because we don't want our new sound to be caught in the below logic, and get freeze in a loop of repeatedly replacing itself and firing events forever.
		 * And no, unlike the regular play sound event, the result isn't a PositionedSoundRecord so we can't just supply a new one in the result. We can only override the name.
		 * Because SOMEONE THOUGHT IT WOULD BE A GOOD IDEA TO MAKE IT SO YOU CAN ONLY CHANGE THE EVENT NAME AND NOT VOLUME OR PITCH???
		 * So as a solution I need to play a new sound instead of modifying the event one, and I do this to "mark" the sound, making sure it doesn't get run through this logic.
		 * Then we strip the ignore suffix and skip the custom sound logic. I don't like the way this works either but I don't think I have a choice lol, blame Forge
		 */
		if (event.name.endsWith(ignore_suffix)) {
			event.name = event.name.replace(ignore_suffix, "");
			return;
		}

		Entity entity = event.entity;
		if (!entity.worldObj.isRemote) {
			// --- Horse eat --- //
			if (ConfigSounds.horseEatCowMilk && entity instanceof EntityHorse && event.name.equals("eating")) {
				event.name = Reference.MCAssetVer + ":entity.horse.eat";
			}
			return;//This is the only code I want to run if !isRemote
		}

		if (entity instanceof EntityPlayer && event.name.equals("random.drink")) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isUsingItem() && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemHoneyBottle) {
				entity.playSound(Reference.MCAssetVer + ":item.honey_bottle.drink" + ignore_suffix, 1, 1);
				event.setCanceled(true);
				return;
			}
		}

		// --- Player Splash --- //
		if (ConfigSounds.heavyWaterSplashing && event.name.equals("game.player.swim.splash")) {

			// Water-striking speed to determine whether to play the large splash sound
			float doWaterSplashEffect_f1 = (MathHelper.sqrt_double(entity.motionX * entity.motionX * 0.20000000298023224D + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ * 0.20000000298023224D)) * (entity.riddenByEntity == null ? 0.2F : 0.9F);

			if (doWaterSplashEffect_f1 > 1.0F) {
				doWaterSplashEffect_f1 = 1.0F;
			}

			// Play fast splash sound instead
			if (doWaterSplashEffect_f1 >= 0.25D) {
				event.name = Reference.MCAssetVer + ":entity.player.splash.high_speed";
				return;
			}
		}

		int x = MathHelper.floor_double(entity.posX);
		int y = MathHelper.floor_double(entity.posY - 0.20000000298023224D - entity.yOffset);
		int z = MathHelper.floor_double(entity.posZ);
		World world = FMLClientHandler.instance().getWorldClient();
		Block block = world.getBlock(x, y, z);

		if (MultiBlockSoundRegistry.multiBlockSounds.containsKey(block) && block.stepSound.getStepResourcePath().equals(event.name)) {
			MultiBlockSoundContainer obj = MultiBlockSoundRegistry.multiBlockSounds.get(block);
			String newSoundString = obj.getSound(world, x, y, z, event.name, MultiBlockSoundRegistry.BlockSoundType.WALK);
			float volume = obj.getVolume(world, x, y, z, event.volume, MultiBlockSoundRegistry.BlockSoundType.WALK);
			float pitch = obj.getPitch(world, x, y, z, event.volume, MultiBlockSoundRegistry.BlockSoundType.WALK);
			if (newSoundString != null || volume != -1 || pitch != -1) {
				if (newSoundString == null) newSoundString = event.name;
				if (volume == -1) volume = event.volume;
				if (pitch == -1) pitch = event.pitch;
				entity.playSound(newSoundString + ignore_suffix, volume, pitch);
				event.setCanceled(true);
			}
		} else if (ConfigSounds.newBlockSounds && ModSounds.soundAmethystBlock.getStepResourcePath().equals(event.name)) {
			MutablePair<Float, Integer> pair = AMETHYST_CHIME_CACHE.get(entity);
			if (pair == null) {
				pair = new MutablePair<>(0.0F, 0);
			}
			float field_26997 = pair.getLeft();
			int lastChimeAge = pair.getRight();
			if (entity.ticksExisted >= lastChimeAge + 20) {
				field_26997 = (float) ((double) field_26997 * Math.pow(0.996999979019165D, entity.ticksExisted - lastChimeAge));
				field_26997 = Math.min(1.0F, field_26997 + 0.07F);
				float f = 0.5F + field_26997 * entity.worldObj.rand.nextFloat() * 1.2F;
				float g = 0.1F + field_26997 * 1.2F;
				entity.playSound(Reference.MCAssetVer + ":block.amethyst_block.chime", g, f);
				lastChimeAge = entity.ticksExisted;
				pair.setLeft(field_26997);
				pair.setRight(lastChimeAge);
				AMETHYST_CHIME_CACHE.put(entity, pair);
			}
		}
	}

	private float prevYOffset;

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		if (!ConfigMixins.enableElytra)
			return;
		EntityPlayerSP player = mc.thePlayer;
		if (!(player instanceof IElytraPlayer))
			return;
		if (((IElytraPlayer) player).etfu$isElytraFlying()) {
			if (event.phase == Phase.START) {
				prevYOffset = player.yOffset;
				/* TODO find the right number here */
				if (mc.gameSettings.thirdPersonView == 0)
					player.yOffset = 3.02f;
			} else {
				player.yOffset = prevYOffset;
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event) {
		Entity entity = event.entityLiving;
		World world = entity.worldObj;

		if (ConfigMixins.enableElytra && entity instanceof EntityPlayerSP) {
			IElytraPlayer e = (IElytraPlayer) entity;
			if (e.etfu$isElytraFlying() && !e.etfu$lastElytraFlying()) {
				mc.getSoundHandler().playSound(new ElytraSound((EntityPlayerSP) entity));
			}
			/* lastElytraFlying is set by the shared handler in ServerEventHandler */
		}
		/*
		 * The purpose of the function is to manifest sprint particles
		 * and adjust slipperiness when entity is moving on block, so check
		 * that the conditions are met first.
		 */
		if (entity.onGround && (entity.motionX != 0 || entity.motionZ != 0)) {
			int x = MathHelper.floor_double(entity.posX);
			int y = MathHelper.floor_double(entity.posY - 0.20000000298023224D - entity.yOffset);
			int z = MathHelper.floor_double(entity.posZ);

			if (entity.worldObj.getBlock(x, y, z) instanceof BlockShulkerBox) {
				TileEntityShulkerBox TE = (TileEntityShulkerBox) entity.worldObj.getTileEntity(x, y, z);
				if (TE != null) {
					if (world.isRemote && entity.isSprinting() && !entity.isInWater()) {
						EntityDiggingFX dig = new EntityDiggingFX(world, entity.posX + (entity.worldObj.rand.nextFloat() - 0.5D) * entity.width, entity.boundingBox.minY + 0.1D, entity.posZ + (entity.worldObj.rand.nextFloat() - 0.5D) * entity.width, -entity.motionX * 4.0D, 1.5D, -entity.motionZ * 4.0D, TE.getBlockType(), 0);
						dig.setParticleIcon(((BlockShulkerBox) TE.getBlockType()).colorIcons[TE.color]);
						mc.effectRenderer.addEffect((dig).applyColourMultiplier(x, y, z));
					}
				}
			}
		}
	}

	public static int main_menu_display_count = 0;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void openMainMenu(GuiOpenEvent event) {
		if (event.gui instanceof GuiMainMenu) {
			this.showedDebugWarning = false;
			if (Reference.launchConfigWarning && main_menu_display_count++ < 20) {
				Reference.launchConfigWarning = false;
				Configuration oldConfig = new Configuration(new File(Launch.minecraftHome, ConfigBase.configDir + "etfuturum.cfg"));
				oldConfig.setCategoryComment("warned", "This is added if we've warned you this file exists.\nUsed by versions that split the config into different files, rendering this file unused.\nThis was done because the current file was becoming difficult to navigate.");
				if (!oldConfig.getBoolean("configWarningShown", "warned", false, "")) {
					event.gui = new GuiConfigWarning(event.gui, oldConfig);
				}
				oldConfig.getCategory("warned").get("configWarningShown").comment = "";
				oldConfig.save();
			}
		} else if (ConfigBlocksItems.enableNewBoats && event.gui instanceof GuiInventory) {
			if (mc.thePlayer.ridingEntity instanceof EntityNewBoatWithChest) {
				event.setCanceled(true);
				EtFuturum.networkWrapper.sendToServer(new ChestBoatOpenInventoryMessage());
			}
		}
	}

	private final List<ISound> soundList = Lists.newArrayList();

	/**
	 * We queue up the sounds like this for three reasons.
	 * One: mc.thePlayer is NULL on first tick of world load even though sounds can play there, causing possible crashes with mod sound handlers
	 * Two: SoundHandler is pretty sensitive and likes to CRASH when you play a new sound alongside others sometimes. This especially happens when playing multiple sounds at the same time
	 * Somehow even though SoundHandler uses an iterator instance to iterate, which should be safe to modify, it gives a ConcurrentModificationException error sometimes.
	 * This can happen in various places during their iterators, even during iterator.remove() which should never throw a comod error (?????)
	 * And sometimes it just crashes when queueing multiple sounds. Of course when loading a world this will happen with bees and other sounds I add like this. Geez, SoundHandler is messed up...
	 * Three: Ear blasting. Not only do we have the handful of SoundHandler problems above, but you get an earful too! Sometimes you can hear tickable sounds on the loading screen!
	 * So that means if there were many bees without that fix your ears would get blasted on world load!
	 */
	private void applyNextEntitySound() {
		if (!soundList.isEmpty()) {
			mc.getSoundHandler().playSound(soundList.remove(0));
		}
	}

	@SubscribeEvent
	public void spawnEvent(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityBee) {
			soundList.add(new BeeFlySound((EntityBee) event.entity));
		}
	}

}
