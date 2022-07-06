package ganymedes01.etfuturum.core.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModEnchantments;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.blocks.BlockMagma;
import ganymedes01.etfuturum.blocks.BlockWitherRose;
import ganymedes01.etfuturum.blocks.ExternalContent;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigEnchantsPotions;
import ganymedes01.etfuturum.configuration.configs.ConfigEntities;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.configuration.configs.ConfigTweaks;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.HoeHelper;
import ganymedes01.etfuturum.core.utils.RawOreRegistry;
import ganymedes01.etfuturum.core.utils.StrippedLogRegistry;
import ganymedes01.etfuturum.core.utils.helpers.BlockAndMetadataMapping;
import ganymedes01.etfuturum.core.utils.helpers.RawOreDropMapping;
import ganymedes01.etfuturum.entities.EntityBrownMooshroom;
import ganymedes01.etfuturum.entities.EntityEndermite;
import ganymedes01.etfuturum.entities.EntityNewBoat;
import ganymedes01.etfuturum.entities.EntityNewSnowGolem;
import ganymedes01.etfuturum.entities.EntityRabbit;
import ganymedes01.etfuturum.entities.EntityShulker;
import ganymedes01.etfuturum.entities.EntityTippedArrow;
import ganymedes01.etfuturum.entities.EntityZombieVillager;
import ganymedes01.etfuturum.entities.ai.EntityAIOpenCustomDoor;
import ganymedes01.etfuturum.inventory.ContainerEnchantment;
import ganymedes01.etfuturum.items.ItemArrowTipped;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.network.BlackHeartParticlesMessage;
import ganymedes01.etfuturum.recipes.ModRecipes;
import ganymedes01.etfuturum.tileentities.TileEntityGateway;
import ganymedes01.etfuturum.world.EtFuturumWorldListener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ServerEventHandler {

	public static final ServerEventHandler INSTANCE = new ServerEventHandler();
	public static HashSet<EntityPlayerMP> playersClosedContainers = new HashSet<>();
	
	private ServerEventHandler() {
	}

	@SubscribeEvent
	public void onPlayerPickXP(PlayerPickupXpEvent event) {
		ModEnchantments.onPlayerPickupXP(event);
	}

	@SubscribeEvent
	public void livingUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (entity.worldObj == null) return;
		
		ModEnchantments.onLivingUpdate(entity);
		
		double x = entity.posX;
		double y = entity.posY;
		double z = entity.posZ;
		if(ConfigBlocksItems.enableMagmaBlock) {
			if(!entity.isImmuneToFire() && !entity.isSneaking() && entity.onGround && entity.worldObj.getBlock(MathHelper.floor_double(x), (int)(y - .45D), MathHelper.floor_double(z)) == ModBlocks.magma_block) {
				if(ConfigEnchantsPotions.enableFrostWalker && EnchantmentHelper.getEnchantmentLevel(ConfigEnchantsPotions.frostWalkerID, entity.getEquipmentInSlot(1)) == 0) {
					entity.attackEntityFrom(BlockMagma.HOT_FLOOR, 1);
				}
			}
		}
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = ((EntityPlayer)entity);
			if(ConfigEntities.flySprintSpeed > 0.05F) {
				if(player.isSprinting() && player.capabilities.isFlying) {
					player.capabilities.flySpeed = ConfigEntities.flySprintSpeed;
				} else {
					player.capabilities.flySpeed = 0.05F;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void entityAdded(EntityJoinWorldEvent event) {
		if(event.world.isRemote) return;

		Chunk chunk = event.world.getChunkFromChunkCoords(MathHelper.floor_double(event.entity.posX) >> 4, MathHelper.floor_double(event.entity.posZ) >> 4);
		
		if (ConfigBlocksItems.enableNewBoats && ConfigBlocksItems.replaceOldBoats) {
			if (event.entity.getClass() == EntityBoat.class) {
				EntityNewBoat boat = new EntityNewBoat(event.world);
				event.entity.rotationYaw += 90;
				replaceEntity(event.entity, boat, event.world, chunk);
				boat.setBoatType(EntityNewBoat.Type.OAK);
				event.setCanceled(true);
				return;
			}
		}
		
		if (ConfigEntities.enableVillagerZombies && event.entity.getClass() == EntityZombie.class && ((EntityZombie)event.entity).isVillager()) {
			replaceEntity(event.entity, new EntityZombieVillager(event.world), event.world, chunk);
			event.setCanceled(true);
			return;
		}

		if (ConfigEntities.enableShearableSnowGolems && event.entity.getClass() == EntitySnowman.class) {
			Entity entity = new EntityNewSnowGolem(event.world);
			replaceEntity(event.entity, entity, event.world, chunk);
			entity.getDataWatcher().updateObject(12, (byte)1);
			event.setCanceled(true);
			return;
		}
	}
	
	private Set<Chunk> loadedChunks = Collections.newSetFromMap(new WeakHashMap<Chunk, Boolean>());
	private Set<Long> debugCoords = new HashSet();
	
	@SubscribeEvent
	public void chunkLoad(ChunkEvent.Load event) {
		loadedChunks.add(event.getChunk());
	}
	
	@SubscribeEvent
	public void chunkUnload(ChunkEvent.Unload event) {
		loadedChunks.remove(event.getChunk());
	}
	
	private void replaceEntity(Entity oldEntity, Entity newEntity, World world, Chunk chunk) {
		newEntity.copyDataFrom(oldEntity, true);
		if(loadedChunks.contains(chunk)) { // Use this list because somehow chunk.isChunkLoaded is always true here...
			// World#addLoadedEntities has already run for the chunk, we don't have to worry about conflicting with it
			world.spawnEntityInWorld(newEntity);
		} else {
			// don't add to tracker, because World#addLoadedEntities will also do it
			chunk.addEntity(newEntity);
		}
		oldEntity.setDead();
	}

	@SubscribeEvent
	public void livingAttack(LivingAttackEvent event) {
		if(event.source == DamageSource.wither && event.entityLiving instanceof EntitySkeleton && ((EntitySkeleton)event.entityLiving).getSkeletonType() == 1) {
			event.setCanceled(true);
			return;
		}
		
		if (event.source instanceof EntityDamageSourceIndirect) {
			EntityDamageSourceIndirect dmgSrc = (EntityDamageSourceIndirect) event.source;
			if (dmgSrc.getSourceOfDamage() instanceof EntityTippedArrow) {
				EntityTippedArrow tippedArrow = (EntityTippedArrow) dmgSrc.getSourceOfDamage();
				if (!tippedArrow.worldObj.isRemote)
					event.entityLiving.addPotionEffect(tippedArrow.getEffect());
			}
		}
	}

	@SubscribeEvent
	public void arrowNock(ArrowNockEvent event) {
		if (event.result == null)
			return;
		IInventory invt = event.entityPlayer.inventory;
		for (int i = 0; i < invt.getSizeInventory(); i++) {
			ItemStack stack = invt.getStackInSlot(i);
			if (stack == null || stack.stackSize <= 0)
				continue;
			if (stack.getItem() == Items.arrow)
				return;
			if (stack.getItem() == ModItems.tipped_arrow) {
				event.setCanceled(true);
				event.entityPlayer.setItemInUse(event.result, event.result.getItem().getMaxItemUseDuration(event.result));
				return;
			}
		}
	}

	@SubscribeEvent
	public void arrowLoose(ArrowLooseEvent event) {
		if (event.bow == null)
			return;

		IInventory invt = event.entityPlayer.inventory;
		for (int i = 0; i < invt.getSizeInventory(); i++) {
			ItemStack arrow = invt.getStackInSlot(i);
			if (arrow != null && arrow.stackSize > 0 && arrow.getItem() == ModItems.tipped_arrow) {
				float charge = event.charge / 20.0F;
				charge = (charge * charge + charge * 2.0F) / 3.0F;

				if (charge < 0.1D)
					return;
				if (charge > 1.0F)
					charge = 1.0F;

				EntityTippedArrow arrowEntity = new EntityTippedArrow(event.entityPlayer.worldObj, event.entityPlayer, charge * 2.0F);
				arrowEntity.setEffect(ItemArrowTipped.getEffect(arrow));

				if (charge == 1.0F)
					arrowEntity.setIsCritical(true);

				int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, event.bow);
				if (power > 0)
					arrowEntity.setDamage(arrowEntity.getDamage() + power * 0.5D + 0.5D);

				int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, event.bow);
				if (punch > 0)
					arrowEntity.setKnockbackStrength(punch);

				if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, event.bow) > 0)
					arrowEntity.setFire(100);

				event.bow.damageItem(1, event.entityPlayer);
				event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.bow", 1.0F, 1.0F / (event.entityPlayer.worldObj.rand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);

				if (!event.entityPlayer.capabilities.isCreativeMode && --arrow.stackSize <= 0)
					event.entityPlayer.inventory.setInventorySlotContents(i, null);

				if (!event.entityPlayer.worldObj.isRemote)
					event.entityPlayer.worldObj.spawnEntityInWorld(arrowEntity);
				event.setCanceled(true);
				return;
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLoadFromFileEvent(PlayerEvent.LoadFromFile event) {
		if (!ConfigBlocksItems.enableEnchantingTable)
			return;
		try {
			File file = event.getPlayerFile(Reference.MOD_ID);
			if (!file.exists()) {
				file.createNewFile();
				return;
			}

			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			if (line != null) {
				int seed = Integer.parseInt(line);
				ContainerEnchantment.seeds.put(event.playerUUID, seed);
			}
			br.close();
		} catch (Exception e) {
		}
	}

	@SubscribeEvent
	public void onPlayerSaveFromFileEvent(PlayerEvent.SaveToFile event) {
		if (!ConfigBlocksItems.enableEnchantingTable)
			return;
		try {
			File file = event.getPlayerFile(Reference.MOD_ID);
			if (!file.exists()) {
				file.createNewFile();
				return;
			}

			Integer seed = ContainerEnchantment.seeds.get(event.playerUUID);
			if (seed != null) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				bw.write(seed.toString());
				bw.close();
			}
		} catch (IOException e) {
		}
	}

	@SubscribeEvent
	public void harvestEvent(BlockEvent.HarvestDropsEvent event) {
		if(ConfigFunctions.enableSilkTouchingMushrooms && event.isSilkTouching)
			if (event.block == Blocks.brown_mushroom_block) {
				event.drops.clear();
				event.drops.add(new ItemStack(ModBlocks.brown_mushroom_block));
			} else if (event.block == Blocks.red_mushroom_block) {
				event.drops.clear();
				event.drops.add(new ItemStack(ModBlocks.red_mushroom_block));
			}

		if(ConfigFunctions.enableSticksFromDeadBushes) {
			if(event.block == Blocks.deadbush) {
				boolean isShears = event.harvester != null && event.harvester.getCurrentEquippedItem() != null && event.harvester.getCurrentEquippedItem().getItem() instanceof ItemShears;
				if(event.harvester == null || event.harvester.getCurrentEquippedItem() == null || !isShears)
					for(int i = 0; i < event.world.rand.nextInt(3); i++)
						event.drops.add(new ItemStack(Items.stick));
			}
		}

//      if(event.block == Blocks.iron_ore) {
//          event.drops.add(new ItemStack(ModBlocks.copper_ore, 1, 1));
//      } //Debug code, see below
		
		if(ConfigBlocksItems.enableRawOres && !event.isSilkTouching) {
			RawOreDropMapping mapping = null;
			//Looks at the list of drops, and replaces all drops with its respective raw ore
			//For example all oreIron in the drops turn into Raw Iron
			for(int i = 0; i < event.drops.size(); i++) {
				ItemStack stack = event.drops.get(i);
				for(String oreName : EtFuturum.getOreStrings(stack)) {
					//For some reason this list is always empty for items which were added during the event being fired (see above)
					mapping = RawOreRegistry.getOreMap().get(oreName);
					if(mapping != null) {
						event.drops.set(i, new ItemStack(mapping.getItem(), mapping.getDropAmount(event.world.rand, event.fortuneLevel), mapping.getMeta()));
						break;
					}
				}
			}
		}
		
		if (ConfigFunctions.enableShearableCobwebs) {
			if (event.block == Blocks.web && event.harvester != null) {
				ItemStack stack = event.harvester.getCurrentEquippedItem();
				if (stack != null && stack.getItem() instanceof ItemShears) {
					event.drops.clear();
					event.drops.add(new ItemStack(Blocks.web));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockBroken(BlockEvent.BreakEvent event) {
		if(ConfigFunctions.enableHoeMining && event.block.getBlockHardness(event.world, event.x, event.y, event.z) != 0.0D) {
			ItemStack itemstack = event.getPlayer().getHeldItem();
			if(itemstack != null && itemstack.getItem() instanceof ItemHoe) {
				itemstack.damageItem(1, event.getPlayer());
			}
		}
	}
	
	@SubscribeEvent
	public void breakSpeedEvent(PlayerEvent.BreakSpeed event) {
		boolean flag = false;
		float toolSpeed = 0;
		float speedModifier = 0;
		if(ConfigFunctions.enableHoeMining && HoeHelper.hoeArrayHas(event.block)) {
			ItemStack stack = event.entityPlayer.getHeldItem();
			if(stack != null && stack.getItem() instanceof ItemHoe) {
				try {
					Item hoe = stack.getItem();
					toolSpeed = getHoeSpeed(hoe);
					speedModifier = this.speedModifier(event.entityPlayer, event.block, event.metadata, toolSpeed);
					flag = true;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
			
		}
		if(flag)
			event.newSpeed = event.originalSpeed + toolSpeed + speedModifier;
	}

	/**
	 * Return 0 if the input is not a tool.
	 * Gets private tool speed value from tool material.
	 * Now cleaned up and uses Access Transformers for better performance!
	 * @param item
	 */
	public float getHoeSpeed(Item item) {
		float returnValue = 0;
		try {
			if(item instanceof ItemHoe || item instanceof ItemTool) {
				Item.ToolMaterial theToolMaterial;
				if(item instanceof ItemTool) {
					theToolMaterial = ((ItemTool)item).toolMaterial;
				} else {
					theToolMaterial = ((ItemHoe)item).theToolMaterial;
				}
				returnValue = theToolMaterial.getEfficiencyOnProperMaterial();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	public float speedModifier(EntityPlayer entity, Block block, int meta, float digSpeed) {
		float moddedDigSpeed = 1;
		
		int i = EnchantmentHelper.getEfficiencyModifier(entity);
		ItemStack itemstack = entity.inventory.getCurrentItem();

		if (i > 0 && itemstack != null)
		{
			float f1 = i * i + 1;

			//boolean canHarvest = ForgeHooks.canToolHarvestBlock(block, meta, itemstack); // TODO Do you even care if it is harvestable?
			moddedDigSpeed += f1;
		}

		if (entity.isPotionActive(Potion.digSpeed))
		{
			moddedDigSpeed *= 1.0F + (entity.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
		}

		if (entity.isPotionActive(Potion.digSlowdown))
		{
			moddedDigSpeed *= 1.0F - (entity.getActivePotionEffect(Potion.digSlowdown).getAmplifier() + 1) * 0.2F;
		}

		if (entity.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(entity))
		{
			moddedDigSpeed /= 5.0F;
		}

		if (!entity.onGround)
		{
			moddedDigSpeed /= 5.0F;
		}
		
		return moddedDigSpeed - 1 < 0 ? 0 : moddedDigSpeed - 1;
	}

	@SubscribeEvent
	public void onPlaceBlock(BlockEvent.PlaceEvent event) {
		if(ConfigFunctions.enableFloatingTrapDoors && event.blockSnapshot.y - event.y != 0 && event.placedBlock instanceof BlockTrapDoor) {
			int l = (MathHelper.floor_double(event.player.rotationYaw * 4.0F / 360.0F + 0.5D) + 1) & 3;
			if(l == 0) {
				l = 1;
			} else if(l == 1) {
				l = 2;
			} else if(l == 2) {
				l = 0;
			}
			event.world.setBlockMetadataWithNotify(event.x, event.y, event.z, l, 3);
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		EntityPlayer player = event.entityPlayer;
		if (event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR) {
			if (player != null) {
				ItemStack heldStack = player.getHeldItem();
				World world = event.world;
				int x = event.x;
				int y = event.y;
				int z = event.z;
				Block oldBlock = world.getBlock(x, y, z);
				int meta = world.getBlockMetadata(x, y, z);
				int side = event.face;
				if (player.canPlayerEdit(x, y, z, side, heldStack))
				{
					if(event.getResult() == event.useItem) {
						
						//Eye of Ender place sounds
						if(heldStack != null && !event.world.isRemote && ConfigWorld.enableNewMiscSounds && heldStack.getItem() == Items.ender_eye && oldBlock == Blocks.end_portal_frame && !BlockEndPortalFrame.isEnderEyeInserted(meta))
						{
							world.playSoundEffect(x + .5F, y + .5F, z + .5F, Reference.MCv118 + ":block.end_portal_frame.fill", 1, 1);
							int j1 = meta & 3;
							int j2 = 0;
							int k1 = 0;
							boolean flag1 = false;
							boolean flag = true;
							int k2 = Direction.rotateRight[j1];
							int l1;
							int i2;
							int l2;

							for (l1 = -2; l1 <= 2; ++l1)
							{
								l2 = x + Direction.offsetX[k2] * l1;
								i2 = z + Direction.offsetZ[k2] * l1;

								if (world.getBlock(l2, y, i2) == Blocks.end_portal_frame)
								{
									if (!BlockEndPortalFrame.isEnderEyeInserted(world.getBlockMetadata(l2, y, i2)))
									{
										if(l2 != x || i2 != z) { //We add this so it doesn't care for the block clicked at, as the eye won't be there quite yet.
											flag = false;
											break;
										}
									}

									k1 = l1;

									if (!flag1)
									{
										j2 = l1;
										flag1 = true;
									}
								}
							}
							if (flag && k1 == j2 + 2)
							{
								for (l1 = j2; l1 <= k1; ++l1)
								{
									l2 = x + Direction.offsetX[k2] * l1;
									i2 = z + Direction.offsetZ[k2] * l1;
									l2 += Direction.offsetX[j1] * 4;
									i2 += Direction.offsetZ[j1] * 4;

									if (world.getBlock(l2, y, i2) != Blocks.end_portal_frame || (!BlockEndPortalFrame.isEnderEyeInserted(world.getBlockMetadata(l2, y, i2))))
									{
										if(l2 != x || i2 != z) { //We add this so it doesn't care for the block clicked at, as the eye won't be there quite yet.
											flag = false;
											break;
										}
									}
								}

								int i3;
								
								for (l1 = j2 - 1; l1 <= k1 + 1; l1 += 4)
								{
									for (l2 = 1; l2 <= 3; ++l2)
									{
										i2 = x + Direction.offsetX[k2] * l1;
										i3 = z + Direction.offsetZ[k2] * l1;
										i2 += Direction.offsetX[j1] * l2;
										i3 += Direction.offsetZ[j1] * l2;

										if (world.getBlock(i2, y, i3) != Blocks.end_portal_frame || (!BlockEndPortalFrame.isEnderEyeInserted(world.getBlockMetadata(i2, y, i3))))
										{
											if(i2 != x || i3 != z) { //We add this so it doesn't care for the block clicked at, as the eye won't be there quite yet.
												flag = false;
												break;
											}
										}
									}
								}
								if(flag)
								{
									for(WorldServer worldserver : FMLCommonHandler.instance().getMinecraftServerInstance().worldServers) {
										for(Object playerobj : worldserver.playerEntities) {
											if(playerobj instanceof EntityPlayerMP) {
												EntityPlayerMP playermp = (EntityPlayerMP)playerobj;
												playermp.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(Reference.MCv118 + ":block.end_portal.spawn",
														playermp.posX, playermp.lastTickPosY, playermp.posZ, 1F, 1F));
											}
										}
									}
								}
							}
						}
						
						//Seeds/Wart placing sounds
						if(ConfigWorld.enableNewBlocksSounds && side == 1 && heldStack != null && heldStack.getItem() instanceof IPlantable && player.canPlayerEdit(x, y + 1, z, side, heldStack)) {
							/*
							 * This code was adapted from AstroTibs' ASMC.
							 * Used with permission!
							 */
							if (world.getBlock(x, y, z).canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable)heldStack.getItem()) && world.isAirBlock(x, y + 1, z))
							{
								// Mundane seeds
								if (oldBlock instanceof BlockFarmland)
								{
									world.playSoundEffect(x + 0.5, y + 1F , z + 0.5, ModSounds.soundCrops.func_150496_b(), ModSounds.soundCrops.getVolume(), ModSounds.soundCrops.getPitch());
									return;
								}
								// Nether wart
								else if (oldBlock instanceof BlockSoulSand)
								{
									world.playSoundEffect(x + 0.5, y + 1F, z + 0.5, ModSounds.soundCropWarts.func_150496_b(), ModSounds.soundCropWarts.getVolume(), ModSounds.soundCropWarts.getPitch());
									return;
								}
							}
						}
						
						//Lava cauldron filling
						if (ConfigBlocksItems.enableLavaCauldrons && heldStack != null && heldStack.getItem() == Items.lava_bucket) {
							if(oldBlock == Blocks.cauldron && canUse(player, world, x, y, z) && meta == 0) {
								event.setResult(Result.DENY);
								player.swingItem();
								world.setBlock(x, y, z, ModBlocks.lava_cauldron);
								if (!player.capabilities.isCreativeMode) {
									if (heldStack.stackSize <= 1) {
										player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
									} else {
										--heldStack.stackSize;
									}
								}
							}
						}
						
						if(ConfigBlocksItems.enableInvertedDaylightSensor && oldBlock == Blocks.daylight_detector && canUse(player, world, x, y, z)) {
							player.swingItem();
							world.setBlock(x, y, z, ModBlocks.inverted_daylight_detector, 15 - meta, 2);
							if(!world.isRemote) {
								event.setResult(Result.DENY);
								event.setCanceled(true);
							}
						}
					
						
						//Grass pathing/Log Stripping
						//This is nested into the same function since they use similar checks
						if(heldStack != null) {
							Set<String> toolClasses = heldStack.getItem().getToolClasses(heldStack);
							if (toolClasses != null) {
								if (ConfigBlocksItems.enableGrassPath && toolClasses.contains("shovel") && !world.getBlock(x, y + 1, z).getMaterial().isSolid() && (oldBlock == Blocks.grass || oldBlock == Blocks.dirt || oldBlock == Blocks.mycelium)) {
									player.swingItem();
									if(!world.isRemote) {
										world.setBlock(x, y, z, ModBlocks.grass_path);
										heldStack.damageItem(1, player);
										world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, Reference.MCv118 + ":item.shovel.flatten", 1.0F, 1.0F);
									}
								} else if (ConfigBlocksItems.enableStrippedLogs && toolClasses.contains("axe")) {
									BlockAndMetadataMapping newBlock = StrippedLogRegistry.getLog(oldBlock, world.getBlockMetadata(x, y, z) % 4);
									if (newBlock != null) {
										player.swingItem();
										if(!world.isRemote) {
											world.setBlock(x, y, z, newBlock.getBlock(), newBlock.getMeta() + ((meta / 4) * 4), 2);
											heldStack.damageItem(1, player);
											world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, Reference.MCv118 + ":item.axe.strip", 1.0F, 0.8F);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static boolean canUse(EntityPlayer player, World world, int x, int y, int z) {
		return !player.isSneaking() || player.getHeldItem() == null || player.getHeldItem().getItem().doesSneakBypassUse(world, x, y, z, player);
	}
	
	@SubscribeEvent
	public void onHoeUseEvent(UseHoeEvent event) {
		if (ConfigBlocksItems.enableCoarseDirt) {
			World world = event.world;
			int x = event.x;
			int y = event.y;
			int z = event.z;
			if (world.getBlock(x, y, z) == ModBlocks.coarse_dirt) {
				world.setBlock(x, y, z, Blocks.dirt);
				world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, Block.soundTypeGravel.getStepResourcePath(), 1.0F, 0.8F);
				event.setResult(Result.ALLOW);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void dropEvent(LivingDropsEvent event) {
		if (event.entityLiving.worldObj.isRemote)
			return;

		if (ConfigFunctions.enableSkullDrop)
			dropHead(event.entityLiving, event.source, event.lootingLevel, event.drops);

		Random rand = event.entityLiving.worldObj.rand;
		if (ConfigBlocksItems.enableMutton && event.entityLiving instanceof EntitySheep) {
			int amount = rand.nextInt(3) + 1 + rand.nextInt(1 + event.lootingLevel);
			for (int i = 0; i < amount; i++)
				if (event.entityLiving.isBurning())
					addDrop(new ItemStack(ModItems.cooked_mutton), event.entityLiving, event.drops);
				else
					addDrop(new ItemStack(ModItems.raw_mutton), event.entityLiving, event.drops);
		}
		
		if (ConfigBlocksItems.enableWitherRose && event.entity instanceof EntityLivingBase && event.source.getEntity() instanceof EntityWither) {
			World world = event.entity.worldObj;
			Entity entity = event.entity;
			if(world.getGameRules().getGameRuleBooleanValue("mobGriefing") && ((BlockWitherRose)ModBlocks.wither_rose).canPlaceBlockAt(world, (int)entity.posX, (int)entity.posY, (int)entity.posZ)) {
				world.setBlock((int)entity.posX, (int)entity.posY, (int)entity.posZ, ModBlocks.wither_rose);
			} else {
				addDrop(new ItemStack(ModBlocks.wither_rose, 1, 0), event.entityLiving, event.drops);
			}
		}
	}

	private void dropHead(EntityLivingBase entity, DamageSource source, int looting, List<EntityItem> drops) {
		if (isPoweredCreeper(source)) {
			int meta = getHeadMetadata(entity);
			if (meta >= 0)
				addDrop(new ItemStack(Items.skull, 1, meta), entity, drops);
		}
	}

	private void addDrop(ItemStack stack, EntityLivingBase entity, List<EntityItem> list) {
		if (stack.stackSize <= 0)
			return;

		EntityItem entityItem = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, stack);
		entityItem.delayBeforeCanPickup = 10;
		list.add(entityItem);
	}

	private boolean isPoweredCreeper(DamageSource source) {
		if (source.isExplosion() && source instanceof EntityDamageSource) {
			Entity entity = ((EntityDamageSource) source).getEntity();
			if (entity != null && entity instanceof EntityCreeper)
				return ((EntityCreeper) entity).getPowered();
		}

		return false;
	}

	private int getHeadMetadata(EntityLivingBase entity) {
		if (entity.getClass() == EntityZombie.class)
			return 2;
		else if (entity.getClass() == EntitySkeleton.class && ((EntitySkeleton) entity).getSkeletonType() == 0)
			return 0;
		else if (entity.getClass() == EntityCreeper.class)
			return 4;

		return -1;
	}

	@SubscribeEvent
	public void teleportEvent(EnderTeleportEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (entity instanceof EntityPlayerMP) {
			if (ConfigEntities.enableEndermite) {
				if (entity.getRNG().nextFloat() < 0.05F && entity.worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")) {
					EntityEndermite entityendermite = new EntityEndermite(entity.worldObj);
					entityendermite.setLocationAndAngles(event.targetX, event.targetY, event.targetZ, entity.rotationYaw, entity.rotationPitch);
					entity.worldObj.spawnEntityInWorld(entityendermite);
					entityendermite.setSpawnedByPlayer(true);
					entityendermite.aggroEndermen(64);
				}
			}
			
			ForgeDirection[] horizontal = {ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST};
			
			for (ForgeDirection enumfacing : horizontal) {
				TileEntity tile = event.entity.worldObj.getTileEntity(MathHelper.floor_double(event.targetX + enumfacing.offsetX), MathHelper.floor_double(event.targetY), MathHelper.floor_double(event.targetZ + enumfacing.offsetZ));
				if(!event.entity.worldObj.isRemote && tile instanceof TileEntityGateway && !((TileEntityGateway)tile).isCoolingDown()) {
					((TileEntityGateway)tile).teleportEntity(event.entity);
					tile.markDirty();
					event.setCanceled(true);
					break;
				}
			}
		}
	}

	@SubscribeEvent
	public void naturalSpawnEvent(SpecialSpawn event) {
		if(event.entityLiving instanceof EntityShulker) {
			EntityShulker shulker = ((EntityShulker)event.entityLiving);
			shulker.persistenceRequired = false;
			if(ConfigTweaks.spawnAnywhereShulkerColors) {
				int x = MathHelper.floor_double(event.x);
				int y = MathHelper.floor_double(event.y);
				int z = MathHelper.floor_double(event.z);
				World world = event.world;
				
				if(ConfigTweaks.spawnAnywhereShulkerColors) {
					for(EnumFacing facing : EnumFacing.values()) {
						Block block = world.getBlock(x + facing.getFrontOffsetX(), y + facing.getFrontOffsetY(), z + facing.getFrontOffsetZ());
						byte color = -1;
						int meta = world.getBlockMetadata(x + facing.getFrontOffsetX(), y + facing.getFrontOffsetY(), z + facing.getFrontOffsetZ());
						
						if(facing == EnumFacing.DOWN && block == ExternalContent.hee_end_stone) {
							color = (byte) (meta == 2 ? 10 : meta == 1 ? 1 : 14);
						} else if (block == ExternalContent.enderlicious_sand) {
							color = (byte) (meta == 1 ? 15 : 0);
						} else if (block == ExternalContent.enderlicious_end_rock) {
							color = (byte) (meta % 4 == 1 ? 13 : -1);
						}
							
						if(color > -1) {
							shulker.setColor(color);
							break;
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void spawnEvent(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPig) {
			EntityPig pig = (EntityPig) event.entity;
			if (ConfigBlocksItems.enableBeetroot)
				pig.tasks.addTask(4, new EntityAITempt(pig, 1.2, ModItems.beetroot, false));
		} else if (event.entity instanceof EntityChicken) {
			EntityChicken chicken = (EntityChicken) event.entity;
			if (ConfigBlocksItems.enableBeetroot)
				chicken.tasks.addTask(3, new EntityAITempt(chicken, 1.0D, ModItems.beetroot_seeds, false));
		} else if (event.entity instanceof EntityWolf) {
			EntityWolf wolf = (EntityWolf) event.entity;
			if (ConfigEntities.enableRabbit)
				wolf.targetTasks.addTask(4, new EntityAITargetNonTamed(wolf, EntityRabbit.class, 200, false));
		} else if (event.entity instanceof EntityEnderman) {
			EntityEnderman enderman = (EntityEnderman) event.entity;
//          if (ConfigurationHandler.enableEndermite)
				enderman.targetTasks.addTask(3, new EntityAINearestAttackableTarget(enderman, EntityEndermite.class, 100, false));
		} else if (event.entity instanceof EntityVillager) {
			EntityVillager villager = (EntityVillager) event.entity;
			for (Object obj : villager.tasks.taskEntries) {
				EntityAITaskEntry entry = (EntityAITaskEntry) obj;
				if (entry.action instanceof EntityAIOpenDoor) {
					villager.tasks.removeTask(entry.action);
					villager.tasks.addTask(entry.priority, new EntityAIOpenCustomDoor(villager, true));
					break;
				}
			}
		}
		
		if (event.entity instanceof EntityPlayer && ConfigMixins.stepHeightFix) {
			event.entity.stepHeight = .6F;
		}
	}

	@SubscribeEvent
	public void interactEntityEvent(EntityInteractEvent event) {
		ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
		if (stack == null)
			return;
		if (!(event.target instanceof EntityAnimal))
			return;

		EntityAnimal animal = (EntityAnimal) event.target;
		if (!animal.isChild()) {
			if (animal instanceof EntityPig) {
				if (stack.getItem() == ModItems.beetroot && ConfigBlocksItems.enableBeetroot)
					setAnimalInLove(animal, event.entityPlayer, stack);
			} else if (animal instanceof EntityChicken)
				if (stack.getItem() == ModItems.beetroot_seeds && ConfigBlocksItems.enableBeetroot)
					setAnimalInLove(animal, event.entityPlayer, stack);
		} else if (ConfigEntities.enableBabyGrowthBoost && isFoodItem(animal, stack))
			feedBaby(animal, event.entityPlayer, stack);
		
		if(animal instanceof EntitySheep && stack.getItem() != Items.dye && !animal.worldObj.isRemote) {
			EntitySheep sheep = ((EntitySheep)animal);
			for(int oreID : OreDictionary.getOreIDs(stack)) {
				int fleeceColour = ~ArrayUtils.indexOf(ModRecipes.ore_dyes, OreDictionary.getOreName(oreID)) & 15;
				if(ArrayUtils.contains(ModRecipes.ore_dyes, OreDictionary.getOreName(oreID)) && sheep.getFleeceColor() != fleeceColour
						&& !sheep.getSheared()) {
					sheep.setFleeceColor(fleeceColour);
					if(!event.entityPlayer.capabilities.isCreativeMode)
						--stack.stackSize;
					break;
				}
			}
		}
	}

	private void setAnimalInLove(EntityAnimal animal, EntityPlayer player, ItemStack stack) {
		if (!animal.isInLove()) {
			animal.func_146082_f(player);
			if (!player.capabilities.isCreativeMode)
				if (--stack.stackSize <= 0)
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
		}
	}

	private void feedBaby(EntityAnimal animal, EntityPlayer player, ItemStack stack) {
		int currentAge = animal.getGrowingAge();
		int age = (int) (-currentAge * 0.1F);
		animal.setGrowingAge(currentAge + age);
		player.swingItem();

		Random itemRand = animal.worldObj.rand;
		for (int i = 0; i < 3; i++) {
			double d0 = itemRand.nextGaussian() * 0.02D;
			double d1 = itemRand.nextGaussian() * 0.02D;
			double d2 = itemRand.nextGaussian() * 0.02D;
			animal.worldObj.spawnParticle("happyVillager", animal.posX + itemRand.nextFloat() * 0.5, animal.posY + 0.5 + itemRand.nextFloat() * 0.5, animal.posZ + itemRand.nextFloat() * 0.5, d0, d1, d2);
		}

		if (!player.capabilities.isCreativeMode)
			if (--stack.stackSize <= 0)
				player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
	}

	private boolean isFoodItem(EntityAnimal animal, ItemStack food) {
		if (animal.isBreedingItem(food))
			return true;
		else if (animal instanceof EntityPig && food.getItem() == ModItems.beetroot && ConfigBlocksItems.enableBeetroot)
			return true;
		else if (animal instanceof EntityChicken && food.getItem() == ModItems.beetroot_seeds && ConfigBlocksItems.enableBeetroot)
			return true;
		else
			return false;
	}

	@SubscribeEvent
	public void entityHurtEvent(LivingHurtEvent event) {
		if (!ConfigWorld.enableDmgIndicator)
			return;
		int amount = MathHelper.floor_float(Math.min(event.entityLiving.getHealth(), event.ammount) / 2F);
		amount = (int) applyArmorCalculations(event.entityLiving, event.source, amount);
		amount = (int) applyPotionDamageCalculations(event.entityLiving, event.source, amount);
		if (amount <= 0)
			return;

		// If the attacker is a player spawn the hearts aligned and facing it
		if (event.source instanceof EntityDamageSource) {
			EntityDamageSource src = (EntityDamageSource) event.source;
			Entity attacker = src.getSourceOfDamage();
			if (attacker instanceof EntityPlayer && !(attacker instanceof FakePlayer)) {
				Random random = event.entity.worldObj.rand;
				EntityPlayer player = (EntityPlayer) attacker;
				Vec3 look = player.getLookVec();
				look.rotateAroundY((float) Math.PI / 2);
				for (int i = 0; i < amount; i++) {
					double x = event.entityLiving.posX - (0.35D * look.xCoord / 2 * look.xCoord) + (i / 3);
					double y = event.entityLiving.posY + (event.entityLiving.height * 0.75D);
					double z = event.entityLiving.posZ - (0.35D * look.zCoord / 2 * look.zCoord) + (i / 3);
					EtFuturum.networkWrapper.sendToAllAround(new BlackHeartParticlesMessage(x, y, z), new TargetPoint(player.worldObj.provider.dimensionId, x, y, z, 64));
				}
			}
		}
	}
	
	private float applyArmorCalculations(EntityLivingBase entity, DamageSource p_70655_1_, float p_70655_2_)
	{
		if (!p_70655_1_.isUnblockable())
		{
			int i = 25 - entity.getTotalArmorValue();
			float f1 = p_70655_2_ * i;
			p_70655_2_ = f1 / 25.0F;
		}

		return p_70655_2_;
	}
	
	private float applyPotionDamageCalculations(EntityLivingBase entity, DamageSource p_70672_1_, float p_70672_2_)
	{
		if (p_70672_1_.isDamageAbsolute())
		{
			return p_70672_2_;
		}
		if (entity instanceof EntityZombie)
		{
			//par2 = par2; // Forge: Noop Warning
		}

		int i;
		int j;
		float f1;

		if (entity.isPotionActive(Potion.resistance) && p_70672_1_ != DamageSource.outOfWorld)
		{
			i = (entity.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
			j = 25 - i;
			f1 = p_70672_2_ * j;
			p_70672_2_ = f1 / 25.0F;
		}

		if (p_70672_2_ <= 0.0F)
		{
			return 0.0F;
		}
		i = EnchantmentHelper.getEnchantmentModifierDamage(entity.getLastActiveItems(), p_70672_1_);

		if (i > 20)
		{
			i = 20;
		}

		if (i > 0 && i <= 20)
		{
			j = 25 - i;
			f1 = p_70672_2_ * j;
			p_70672_2_ = f1 / 25.0F;
		}

		return p_70672_2_;
	}

	@SubscribeEvent
	public void entityStruckByLightning(EntityStruckByLightningEvent event) {
		if (ConfigEntities.enableVillagerTurnsIntoWitch && event.entity instanceof EntityVillager) {
			EntityVillager villager = (EntityVillager) event.entity;
			if (!villager.worldObj.isRemote) {
				EntityWitch witch = new EntityWitch(villager.worldObj);
				witch.copyLocationAndAnglesFrom(villager);
				witch.onSpawnWithEgg(null);
				villager.worldObj.spawnEntityInWorld(witch);
				villager.setDead();
			}
		} else
			if (ConfigEntities.enableBrownMooshroom && event.entity.ticksExisted > 40 && event.entity.getClass() == EntityMooshroom.class)  {
				EntityMooshroom mooshroom = (EntityMooshroom) event.entity;
				if (!mooshroom.worldObj.isRemote) {
					EntityBrownMooshroom brownmooshroom = new EntityBrownMooshroom(mooshroom.worldObj);
					brownmooshroom.copyLocationAndAnglesFrom(mooshroom);
					brownmooshroom.onSpawnWithEgg(null);
					mooshroom.worldObj.spawnEntityInWorld(brownmooshroom);
					mooshroom.setDead();
					brownmooshroom.attackEntityFrom(DamageSource.onFire, 0);
					//TODO: Cow won't flee for some reason
				}                    
			} else
			if (ConfigEntities.enableBrownMooshroom && event.entity.ticksExisted > 40 && event.entity.getClass() == EntityBrownMooshroom.class) {
				EntityBrownMooshroom brownmooshroom = (EntityBrownMooshroom) event.entity;
				if (!brownmooshroom.worldObj.isRemote) {
					EntityMooshroom mooshroom = new EntityMooshroom(brownmooshroom.worldObj);
					mooshroom.copyLocationAndAnglesFrom(brownmooshroom);
					mooshroom.onSpawnWithEgg(null);
					brownmooshroom.worldObj.spawnEntityInWorld(mooshroom);
					brownmooshroom.setDead();
					mooshroom.attackEntityFrom(DamageSource.onFire, 0);
				}
			}
	}
	@SubscribeEvent
	public void livingHurtEvent(LivingHurtEvent event) {
		Entity entity = event.entity;
		if(ConfigFunctions.enableHayBaleFalls && entity != null
				&& entity.worldObj.getBlock(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY - 0.20000000298023224D - entity.yOffset), MathHelper.floor_double(entity.posZ)) == Blocks.hay_block
				&& event.source == DamageSource.fall) {
			event.ammount *= ((float)ConfigFunctions.hayBaleReducePercent / (float)100);
		}
		if ((entity == null) || (!(entity instanceof EntityLivingBase))) {
			return;
		}
		EntityLivingBase livingEntity = (EntityLivingBase)entity;
		
		if(ConfigBlocksItems.enableTotemUndying)
			handleTotemCheck(livingEntity, event);
	}
	
	public void handleTotemCheck(final EntityLivingBase entity, final LivingHurtEvent event) {
		if (entity.getHealth() > Math.round(event.ammount)) {
			return;
		}
		if (entity.getHeldItem() == null || entity.getHeldItem().getItem() != ModItems.totem) {
			return;
		}
		
		if(entity instanceof EntityLiving || entity instanceof EntityPlayer) {
			//this.spawnTotemParticles(player);
			entity.worldObj.playSoundEffect(entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, Reference.MCv118 + ":item.totem.use", 1.0f, entity.worldObj.rand.nextFloat() * 0.1f + 0.9f);
			
			entity.clearActivePotions();
			float healpercent = (float)ConfigFunctions.totemHealPercent / 100;
			float sethp = entity.getMaxHealth() * healpercent;
			entity.setHealth(sethp < .5F ? .5F : sethp);
			event.ammount = 0;
			entity.addPotionEffect(new PotionEffect(Potion.regeneration.id, 900, 1));
			entity.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 800, 1));
			entity.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 100, 1));
			//TODO: Make it respect a stack size
			
			if(entity instanceof EntityLiving) {
				((EntityLiving)entity).setCurrentItemOrArmor(0, (ItemStack)null);
			}
			if(entity instanceof EntityPlayer) {
				((EntityPlayer)entity).addChatMessage(new ChatComponentText(StatCollector.translateToLocal("util.totemBreak")));
				((EntityPlayer)entity).destroyCurrentEquippedItem();
			}
		}
	}
	
	@SubscribeEvent
	public void onDrops(BlockEvent.HarvestDropsEvent event) {
		if(ConfigBlocksItems.enableSmoothStone && event.block == Blocks.double_stone_slab && event.blockMetadata == 8) {
			event.drops.clear();
			event.drops.add(new ItemStack(ModBlocks.smooth_stone, 1));
		}
	}
	
	@SubscribeEvent
	public void loadWorldEvent(WorldEvent.Load event)
	{
		event.world.addWorldAccess(new EtFuturumWorldListener(event.world));
	}

	@SubscribeEvent
	public void onItemToss(ItemTossEvent event)
	{
		if(ConfigMixins.avoidDroppingItemsWhenClosing && event.player instanceof EntityPlayerMP && playersClosedContainers.contains(event.player)) {
			if(event.player.inventory.addItemStackToInventory(event.entityItem.getEntityItem())) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onTickEnd(TickEvent.ServerTickEvent event)
	{
		if(event.phase == TickEvent.Phase.END)
			playersClosedContainers.clear();
	}
	
	// UNUSED FUNCTIONS
	//private boolean playerHasItem(final EntityPlayer player, final ItemStack ist, final boolean checkEnabled) {
	//  for (int slot = 0; slot < player.inventory.mainInventory.length; ++slot) {
	//      if (player.inventory.mainInventory[slot] != null && player.inventory.mainInventory[slot].isItemEqual(ist)) {
	//          return true;
	//      }
	//  }
	//  return false;
	//}
	// UNUSED FUNCTIONS
	//private void decreaseItemByOne(final EntityPlayer player, final Item item) {
	//  for (int slot = 0; slot < player.inventory.mainInventory.length; ++slot) {
	//      if (player.inventory.mainInventory[slot] != null) {
	//          if (player.inventory.mainInventory[slot].getItem() == item) {
	//              player.inventory.decrStackSize(slot, 1);
	//              return;
	//          }
	//      }
	//  }
	//}
}
