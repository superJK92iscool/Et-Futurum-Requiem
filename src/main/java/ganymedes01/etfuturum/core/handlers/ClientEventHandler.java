package ganymedes01.etfuturum.core.handlers;

import cpw.mods.fml.client.FMLClientHandler;
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
import ganymedes01.etfuturum.configuration.configs.*;
import ganymedes01.etfuturum.entities.EntityNewBoatWithChest;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.network.ChestBoatOpenInventoryMessage;
import ganymedes01.etfuturum.tileentities.TileEntityShulkerBox;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
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
import net.minecraft.util.AxisAlignedBB;
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
import org.apache.commons.lang3.tuple.MutablePair;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

import static ganymedes01.etfuturum.spectator.SpectatorMode.isSpectator;

public class ClientEventHandler {

	public static final ClientEventHandler INSTANCE = new ClientEventHandler();
	private Random rand = new Random();
	private boolean showedDebugWarning;
	public static boolean showDebugWarning;
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
	private static final ItemStack STORAGE_STACK = new ItemStack((Item)null, 1, 0);

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

		if(ConfigSounds.netherAmbience && !EtFuturum.netherAmbienceNetherlicious && player.dimension == -1) {
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
		
		if(mc.currentScreen == null && currPage > -1) {
			currPage = -1;
		}
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
		if(event.sound != null && event.name != null && FMLClientHandler.instance().getWorldClient() != null) {
			final World world = FMLClientHandler.instance().getWorldClient();
			final float soundX = event.sound.getXPosF();
			final float soundY = event.sound.getYPosF();
			final float soundZ = event.sound.getZPosF();
			final int x = MathHelper.floor_float(soundX);
			final int y = MathHelper.floor_float(soundY);
			final int z = MathHelper.floor_float(soundZ);
			final Block block = world.getBlock(x, y, z);
			final int meta = world.getBlockMetadata(x, y, z);
			
			if(event.sound.getPitch() < 1.0F && world.getBlock(x, y, z) instanceof IMultiStepSound && (!((IMultiStepSound)block).requiresNewBlockSounds() || ConfigSounds.newBlockSounds)) {
				
				IMultiStepSound multiSoundBlock = (IMultiStepSound)block;
				Block.SoundType newSound = multiSoundBlock.getStepSound(world, x, y, z, meta);
				
				if(newSound == null) return;
				
				Block.SoundType blockSound = block.stepSound;
				
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
			
			String[] eventwithprefix = event.name.split("\\.");
			if (ConfigSounds.newBlockSounds && eventwithprefix.length > 1 && eventwithprefix[1].equals(Blocks.stone.stepSound.soundName) && event.sound.getPitch() < 1.0F) {
				String blockName = "";
				Item item = Item.getItemFromBlock(block);
				if(item != null && item.getHasSubtypes()) {
					try {
						STORAGE_STACK.func_150996_a(item);
						STORAGE_STACK.setItemDamage(world.getBlockMetadata(x, y, z) % 8);
						blockName = item.getUnlocalizedName(STORAGE_STACK).toLowerCase();
					} catch(Exception e) {/*In case a mod doesn't have a catch for invalid meta states and throws an error, just ignore it and proceed*/}
				}

				if(blockName.equals("")) {
					blockName = block.getUnlocalizedName().toLowerCase();
				}
				if(blockName.contains("slab") && blockName.contains("nether") && blockName.contains("brick")) {
					float soundVol = (block.stepSound.getVolume() + 1.0F) / (eventwithprefix[0].contains("step") ? 8F : 2F);
					float soundPit = (block.stepSound.getPitch()) * (eventwithprefix[0].contains("step") ? 0.5F : 0.8F);

					boolean step = event.name.contains("step");
					
					event.result = new PositionedSoundRecord(new ResourceLocation(step ? ModSounds.soundNetherBricks.getStepResourcePath() : ModSounds.soundNetherBricks.getBreakSound()), soundVol, soundPit, x + 0.5F, y + 0.5F, z + 0.5F);
					return;
				}
			}


			//Opening and closing doors/chests
			if(ConfigSounds.doorOpenClose && event.name.contains("random.door")) {
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

				if(!s.equals(event.name)) {
					event.result = new PositionedSoundRecord(new ResourceLocation(s), event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
					return;
				}
			}

			if(ConfigSounds.pressurePlateButton) {
				// --- Wooden Button --- //
				if (block instanceof BlockButton && (block.stepSound == Block.soundTypeWood || block.getClass().getSimpleName().toLowerCase().contains("wood")) && event.name.equals("random.click")) {
					String s = Reference.MCAssetVer + ":block.wooden_button.click_" + (meta > 7 ? "on" : "off");
					event.result = new PositionedSoundRecord(new ResourceLocation(s), event.sound.getVolume(), event.sound.getPitch(), soundX, soundY, soundZ);
					return;
				}

				// --- Wooden/Metal Pressure plate --- //
				if (block instanceof BlockBasePressurePlate && (block.getMaterial() == Material.wood || block.getMaterial() == Material.iron) && event.name.equals("random.click")) {
					String material = block.getMaterial() == Material.wood ? "wooden" : "metal";
					String s = Reference.MCAssetVer + ":block." + material + "_pressure_plate.click_" + (meta == 1 ? "on" : "off");
					//By default, the pitch is 0.6F when on and 0.5F off. Wood pressure plates are 0.8F and 0.7F off. Metal is 0.9F on and 0.75F off.
					//Just in case another mod wants to change the pitch we'll just add our own on top of it.
					float soundAddition = meta == 1 && block.getMaterial() == Material.iron ? 0.25F : 0.2F;
					event.result = new PositionedSoundRecord(new ResourceLocation(s), event.sound.getVolume(), event.sound.getPitch() + soundAddition, soundX, soundY, soundZ);
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
						MathHelper.floor_float(soundY)-1,
						MathHelper.floor_float(soundZ));
				Item item = Item.getItemFromBlock(blockBeneath);
				if(item != null && item.getHasSubtypes()) {
					try {
						STORAGE_STACK.func_150996_a(item);
						STORAGE_STACK.setItemDamage(world.getBlockMetadata(MathHelper.floor_float(soundX), MathHelper.floor_float(soundY), MathHelper.floor_float(soundZ)));
						blockName = item.getUnlocalizedName(STORAGE_STACK).toLowerCase();
					} catch(Exception e) {/*In case a mod doesn't have a catch for invalid meta states and throws an error, just ignore it and proceed*/}
				}

				if(blockName.equals("")) {
					blockName = blockBeneath.getUnlocalizedName().toLowerCase();
				}

				// Specific blocks
				if (blockBeneath==Blocks.soul_sand)											{instrumentToPlay = Reference.MCAssetVer+":block.note_block.cow_bell";}
				else if (blockName.contains("hay"))											{instrumentToPlay = Reference.MCAssetVer+":block.note_block.banjo";}
				else if (EtFuturum.hasDictTag(blockBeneath, "blockGold"))			{instrumentToPlay = Reference.MCAssetVer+":block.note_block.bell";}
				else if (EtFuturum.hasDictTag(blockBeneath, "blockEmerald"))		{instrumentToPlay = Reference.MCAssetVer+":block.note_block.bit";}
				else if (blockName.contains("packed") && blockName.contains("ice"))			{instrumentToPlay = Reference.MCAssetVer+":block.note_block.chime";}
				else if (blockName.contains("pumpkin"))										{instrumentToPlay = Reference.MCAssetVer+":block.note_block.didgeridoo";}
				else if (blockBeneath.getMaterial() == Material.clay)						{instrumentToPlay = Reference.MCAssetVer+":block.note_block.flute";}
				else if (EtFuturum.hasDictTag(blockBeneath, "blockIron"))			{instrumentToPlay = Reference.MCAssetVer+":block.note_block.iron_xylophone";}
				else if (blockBeneath.getMaterial()==Material.cloth)						{instrumentToPlay = Reference.MCAssetVer+":block.note_block.guitar";}
				else if (blockName.contains("bone") || blockName.contains("ivory"))			{instrumentToPlay = Reference.MCAssetVer+":block.note_block.xylophone";}
				if(event.name.equals(instrumentToPlay)) return;

				event.result = new PositionedSoundRecord(new ResourceLocation(instrumentToPlay), instrumentToPlay.equals(Reference.MCAssetVer+":block.note_block.iron_xylophone") ? 1F : event.sound.getVolume(), event.sound.getPitch(), soundX, soundY, soundZ);
				return;
			}


			// --- Book page turn --- //
			if (Minecraft.getMinecraft().currentScreen instanceof GuiScreenBook && event.name.equals("gui.button.press")) {
				GuiScreenBook gui = (GuiScreenBook)Minecraft.getMinecraft().currentScreen;
				// If there is a disagreement on page, play the page-turning sound
				if (gui.currPage != this.currPage)
				{
					this.currPage = gui.currPage;
					EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
					player.playSound(Reference.MCAssetVer+":item.book.page_turn", 1.0F, 1.0F);
					event.result = null;
					return;
				}
			}

			if(!EtFuturum.netherMusicNetherlicious) {
				Minecraft mc = FMLClientHandler.instance().getClient();
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
			
			if(ConfigSounds.rainSounds) {
				if (event.name.equals("ambient.weather.rain")) {
					event.result = new PositionedSoundRecord(new ResourceLocation(Reference.MCAssetVer + ":weather.rain" + (event.sound.getPitch() < 1.0F ? ".above" : "")),
							event.sound.getVolume(), event.sound.getPitch(), x + 0.5F, y + 0.5F, z + 0.5F);
				} else if (event.name.equals("ambient.cave.cave")) {
					if(ConfigSounds.netherAmbience && FMLClientHandler.instance().getClientPlayerEntity().dimension == -1) {
						BiomeGenBase biome = FMLClientHandler.instance().getWorldClient().getChunkFromBlockCoords(x, z).getBiomeGenForWorldCoords(x & 15, z & 15, FMLClientHandler.instance().getWorldClient().getWorldChunkManager());
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
		Entity entity = event.entity;
		if(!event.entity.worldObj.isRemote) {
			// --- Horse eat --- //
			if (ConfigSounds.horseEatCowMilk && event.entity instanceof EntityHorse && event.name.equals("eating")) {
				event.name = Reference.MCAssetVer + ":entity.horse.eat";
			}
			return;//This is the only code I want to run if !isRemote
		}

		// --- Player Splash --- //
		if (event.name.equals("game.player.swim.splash") && ConfigSounds.waterSplashing)
		{

			// Water-striking speed to determine whether to play the large splash sound
			float doWaterSplashEffect_f1 = (MathHelper.sqrt_double(entity.motionX * entity.motionX * 0.20000000298023224D + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ * 0.20000000298023224D)) * (entity.riddenByEntity==null ? 0.2F : 0.9F);

			if (doWaterSplashEffect_f1 > 1.0F) {doWaterSplashEffect_f1 = 1.0F;}

			// Play fast splash sound instead
			if (doWaterSplashEffect_f1 >= 0.25D)
			{
				event.name = Reference.MCAssetVer+":entity.player.splash.high_speed";
				return;
			}
		}


		if(event.name != null) {
			int x = MathHelper.floor_double(event.entity.posX);
			int y = MathHelper.floor_double(event.entity.posY - 0.20000000298023224D - event.entity.yOffset);
			int z = MathHelper.floor_double(event.entity.posZ);
			World world = FMLClientHandler.instance().getWorldClient();
			Block block = world.getBlock(x, y, z);

			if(world.getBlock(x, y, z) instanceof IMultiStepSound && (!((IMultiStepSound)block).requiresNewBlockSounds() || ConfigSounds.newBlockSounds)) {
				Block.SoundType stepSound = ((IMultiStepSound)block).getStepSound(world, x, y, z, world.getBlockMetadata(x, y, z));
				if(stepSound == null) return;
				
				String stepSoundString = block.stepSound.getStepResourcePath();
				
				int index = stepSoundString.indexOf(':');
				if(index != -1) {
					stepSoundString = stepSoundString.substring(index+1);
				}
				
				if(event.name.equals(stepSoundString)) {
					event.name = stepSound.getStepResourcePath();
					return;
				}
			} else if(ConfigSounds.newBlockSounds) {
				if(event.name.equals(Block.soundTypePiston.getStepResourcePath())) {
					String[] eventwithprefix = event.name.split("\\.");
					if(eventwithprefix.length > 1) {
						String blockName = "";
						Item item = Item.getItemFromBlock(block);
						if(item != null && item.getHasSubtypes()) {
							try {
								STORAGE_STACK.func_150996_a(item);
								STORAGE_STACK.setItemDamage(world.getBlockMetadata(x, y, z) % 8);
								blockName = item.getUnlocalizedName(STORAGE_STACK).toLowerCase();
							} catch(Exception e) {/*In case a mod doesn't have a catch for invalid meta states and throws an error, just ignore it and proceed*/}
						}

						if(blockName.equals("")) {
							blockName = block.getUnlocalizedName().toLowerCase();
						}
						if(blockName.contains("slab") && blockName.contains("nether") && blockName.contains("brick")) {
							event.name = ModSounds.soundNetherBricks.getStepResourcePath();
							return;
						}
					}
				} else if(ModSounds.soundAmethystBlock.getStepResourcePath().equals(event.name)) {
					MutablePair<Float, Integer> pair = AMETHYST_CHIME_CACHE.get(event.entity);
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
						AMETHYST_CHIME_CACHE.put(event.entity, pair);
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
	public void openMainMenu(GuiOpenEvent event) {
		if(event.gui instanceof GuiMainMenu) {
			this.showedDebugWarning = false;
			
			if (EtFuturumMixinPlugin.launchConfigWarning && main_menu_display_count++ < 20) {
				EtFuturumMixinPlugin.launchConfigWarning = false;
				Configuration oldConfig = new Configuration(new File(Launch.minecraftHome, ConfigBase.PATH));
				oldConfig.setCategoryComment("warned", "This is added if we've warned you this file exists.\nUsed by versions that split the config into different files, rendering this file unused.\nThis was done because the current file was becoming difficult to navigate.");
				if(!oldConfig.getBoolean("configWarningShown", "warned", false, "")) {
					event.gui = new GuiConfigWarning(event.gui, oldConfig);
				}
				oldConfig.getCategory("warned").get("configWarningShown").comment = "";
				oldConfig.save();
			}
		} else if(ConfigBlocksItems.enableNewBoats && event.gui instanceof GuiInventory) {
			if(Minecraft.getMinecraft().thePlayer.ridingEntity instanceof EntityNewBoatWithChest) {
				event.setCanceled(true);
				EtFuturum.networkWrapper.sendToServer(new ChestBoatOpenInventoryMessage());
			}
		}
	}

}
