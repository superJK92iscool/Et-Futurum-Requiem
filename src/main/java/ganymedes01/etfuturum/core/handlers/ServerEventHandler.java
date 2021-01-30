package ganymedes01.etfuturum.core.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModEnchantments;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.blocks.BlockWitherRose;
import ganymedes01.etfuturum.blocks.MagmaBlock;
import ganymedes01.etfuturum.client.sound.WeightedSoundPool;
import ganymedes01.etfuturum.command.SetPlayerModelCommand;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.HoeHelper;
import ganymedes01.etfuturum.entities.EntityEndermite;
import ganymedes01.etfuturum.entities.EntityNewSnowGolem;
import ganymedes01.etfuturum.entities.EntityRabbit;
import ganymedes01.etfuturum.entities.EntityTippedArrow;
import ganymedes01.etfuturum.entities.EntityZombieVillager;
import ganymedes01.etfuturum.entities.ai.EntityAIOpenCustomDoor;
import ganymedes01.etfuturum.inventory.ContainerEnchantment;
import ganymedes01.etfuturum.items.TippedArrow;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.network.BlackHeartParticlesMessage;
import ganymedes01.etfuturum.network.SetPlayerModelMessage;
import ganymedes01.etfuturum.recipes.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ServerEventHandler {

    public static final ServerEventHandler INSTANCE = new ServerEventHandler();

	public WeightedSoundPool netherWastesMusic = new WeightedSoundPool();
	public WeightedSoundPool basaltDeltasMusic = new WeightedSoundPool();
	public WeightedSoundPool crimsonForestMusic = new WeightedSoundPool();
	public WeightedSoundPool warpedForestMusic = new WeightedSoundPool();
	public WeightedSoundPool soulSandValleyMusic = new WeightedSoundPool();
	public WeightedSoundPool underWaterMusic = new WeightedSoundPool();
    
    private ServerEventHandler() {
    	if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
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

    private Integer playerLoggedInCooldown = null;

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        if (ConfigurationHandler.enablePlayerSkinOverlay)
            playerLoggedInCooldown = 20;
    }
    
    @SubscribeEvent
    public void onWorldTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.side != Side.SERVER)
            return;

        if (ConfigurationHandler.enablePlayerSkinOverlay)
            if (playerLoggedInCooldown != null)
                if (--playerLoggedInCooldown <= 0) {
                    for (World world : MinecraftServer.getServer().worldServers)
                        for (EntityPlayer player : (List<EntityPlayer>) world.playerEntities) {
                            NBTTagCompound nbt = player.getEntityData();
                            if (nbt.hasKey(SetPlayerModelCommand.MODEL_KEY, Constants.NBT.TAG_BYTE)) {
                                boolean isAlex = nbt.getBoolean(SetPlayerModelCommand.MODEL_KEY);
                                EtFuturum.networkWrapper.sendToAll(new SetPlayerModelMessage(player, isAlex));
                            }
                        }
                    playerLoggedInCooldown = null;
                }
    }

    @SubscribeEvent
    public void onPlayerPickXP(PlayerPickupXpEvent event) {
        ModEnchantments.onPlayerPickupXP(event);
    }

    @SubscribeEvent
    public void livingUpdate(LivingUpdateEvent event) {
        ModEnchantments.onLivingUpdate(event.entityLiving);

		Entity entity = event.entityLiving;
		double x = event.entityLiving.posX;
		double y = event.entityLiving.posY;
		double z = event.entityLiving.posZ;
		if(ConfigurationHandler.enableMagmaBlock)
			if(!entity.worldObj.isRemote && !entity.isImmuneToFire() && !entity.isSneaking() && entity.onGround
					&& entity.worldObj.getBlock(MathHelper.floor_double(x), (int)(y - .45), MathHelper.floor_double(z)) == ModBlocks.magma_block) {
				NBTTagList enchants;
				boolean flag = true;
				if(entity instanceof EntityPlayer && ((EntityPlayer) entity).inventory.getStackInSlot(36) != null
						&& ((EntityPlayer) entity).inventory.getStackInSlot(36).getEnchantmentTagList() != null) {
					enchants = ((EntityPlayer) entity).inventory.getStackInSlot(36).getEnchantmentTagList();
					for(int i = 0; i < enchants.tagCount(); i++) {
						NBTTagCompound nbt = enchants.getCompoundTagAt(i);
						short id = nbt.getShort("id");
						if(id == ModEnchantments.frostWalker.effectId) {
							flag = false;
							break;
						}
					}
				}
				if(flag)
					entity.attackEntityFrom(MagmaBlock.HOT_FLOOR, 1);
			}
		
        if (ConfigurationHandler.enableVillagerZombies)
            if (!event.entityLiving.worldObj.isRemote && event.entityLiving.getClass() == EntityZombie.class) {
                EntityZombie zombie = (EntityZombie) event.entityLiving;
                if (zombie.isVillager()) {
                    EntityZombieVillager villagerZombie = new EntityZombieVillager(zombie.worldObj);
                    villagerZombie.copyLocationAndAnglesFrom(zombie);
                    villagerZombie.onSpawnWithEgg(null);
                    villagerZombie.worldObj.spawnEntityInWorld(villagerZombie);

                    zombie.setDead();
                }
            }

        if (ConfigurationHandler.enableShearableGolems)
            if (!event.entityLiving.worldObj.isRemote && event.entityLiving.getClass() == EntitySnowman.class) {
                EntityNewSnowGolem golen = new EntityNewSnowGolem(event.entityLiving.worldObj);
                golen.copyLocationAndAnglesFrom(event.entityLiving);
                golen.onSpawnWithEgg(null);
                golen.worldObj.spawnEntityInWorld(golen);

                event.entityLiving.setDead();
            }
    }

    @SubscribeEvent
    public void livingAttack(LivingAttackEvent event) {
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
                arrowEntity.setEffect(TippedArrow.getEffect(arrow));

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
        if (!ConfigurationHandler.enableEnchants)
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
                br.close();
            }
        } catch (Exception e) {
        }
    }

    @SubscribeEvent
    public void onPlayerSaveFromFileEvent(PlayerEvent.SaveToFile event) {
        if (!ConfigurationHandler.enableEnchants)
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
        if (ConfigurationHandler.enableSilkTouchingMushrooms && event.isSilkTouching)
            if (event.block == Blocks.brown_mushroom_block) {
                event.drops.clear();
                event.drops.add(new ItemStack(ModBlocks.brown_mushroom_block));
            } else if (event.block == Blocks.red_mushroom_block) {
                event.drops.clear();
                event.drops.add(new ItemStack(ModBlocks.red_mushroom_block));
            }

        if (ConfigurationHandler.enableSticksFromDeadBushes)
            if (event.block == Blocks.deadbush) {
                boolean isShears = event.harvester != null && event.harvester.getCurrentEquippedItem() != null && event.harvester.getCurrentEquippedItem().getItem() instanceof ItemShears;
                if (event.harvester == null || event.harvester.getCurrentEquippedItem() == null || !isShears)
                    for (int i = 0; i < event.world.rand.nextInt(3); i++)
                        event.drops.add(new ItemStack(Items.stick));
            }

        if (ConfigurationHandler.enableShearableCobwebs)
            if (event.block == Blocks.web && event.harvester != null) {
                ItemStack stack = event.harvester.getCurrentEquippedItem();
                if (stack != null && stack.getItem() instanceof ItemShears) {
                    event.drops.clear();
                    event.drops.add(new ItemStack(Blocks.web));
                }
            }
    }
	
	@SubscribeEvent
	public void onBlockBroken(BlockEvent.BreakEvent event) {
		if(ConfigurationHandler.enableHoeMining && (double)event.block.getBlockHardness(event.world, event.x, event.y, event.z) != 0.0D) {
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
		if(ConfigurationHandler.enableHoeMining && HoeHelper.hoeArrayHas(event.block)) {
			ItemStack stack = event.entityPlayer.getHeldItem();
			if(stack != null && stack.getItem() instanceof ItemHoe) {
				try {
					Item hoe = stack.getItem();
					toolSpeed = HoeHelper.getToolSpeed(hoe);
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
	
	public float speedModifier(EntityPlayer entity, Block block, int meta, float digSpeed) {
		float moddedDigSpeed = 1;
		
        int i = EnchantmentHelper.getEfficiencyModifier(entity);
        ItemStack itemstack = entity.inventory.getCurrentItem();

        if (i > 0 && itemstack != null)
        {
            float f1 = (float)(i * i + 1);

            boolean canHarvest = ForgeHooks.canToolHarvestBlock(block, meta, itemstack);
            moddedDigSpeed += f1;
        }

        if (entity.isPotionActive(Potion.digSpeed))
        {
            moddedDigSpeed *= 1.0F + (float)(entity.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
        }

        if (entity.isPotionActive(Potion.digSlowdown))
        {
            moddedDigSpeed *= 1.0F - (float)(entity.getActivePotionEffect(Potion.digSlowdown).getAmplifier() + 1) * 0.2F;
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


	@SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR) {
            EntityPlayer entityPlayer = event.entityPlayer;
            if (entityPlayer != null) {
            	ItemStack heldStack = entityPlayer.getHeldItem();
                World world = entityPlayer.worldObj;
                int x = event.x;
                int y = event.y;
                int z = event.z;
                Block oldBlock = world.getBlock(x, y, z);
            	if (ConfigurationHandler.enableAnvil && oldBlock == Blocks.anvil) {
            		world.setBlock(x, y, z, ModBlocks.anvil, world.getBlockMetadata(x, y, z), 3);
            	} else if(oldBlock != null && heldStack != null && heldStack.getItem() != null) {
                    Set<String> toolClasses = heldStack.getItem().getToolClasses(heldStack);
                    if (toolClasses != null) {
                    	if (ConfigurationHandler.enableGrassPath && toolClasses.contains("shovel") && oldBlock == Blocks.grass) {
                        	world.setBlock(x, y, z, ModBlocks.grass_path);
                            entityPlayer.swingItem();
                            heldStack.damageItem(1, entityPlayer);
                            world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, Block.soundTypeGravel.getStepResourcePath(), 1.0F, 0.8F);
                        } else if (ConfigurationHandler.enableStrippedLogs && toolClasses.contains("axe")) {
                        	Block newBlock = null;
                            if (oldBlock == Blocks.log) {
                            	newBlock = ModBlocks.log_stripped;
                            } else if(oldBlock == Blocks.log2) {
                            	newBlock = ModBlocks.log2_stripped;
                            } else if (ConfigurationHandler.enableBarkLogs) {
                            	if (oldBlock == ModBlocks.log_bark) {
                            		newBlock = ModBlocks.wood_stripped;
                            	} else if (oldBlock == ModBlocks.log2_bark) {
                            		newBlock = ModBlocks.wood2_stripped;
                            	}
                            }
                            if (newBlock != null) {
                            	int logMeta = world.getBlockMetadata(x, y, z);
                            	world.setBlock(x, y, z, newBlock, logMeta, 2);
                            	entityPlayer.swingItem();
                            	heldStack.damageItem(1, entityPlayer);
                                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, Reference.MOD_ID + ":item.axe.strip", 1.0F, 0.8F);
                            }
                        }
                    }
                }
            }
        }
    }
	
    @SubscribeEvent
    public void onHoeUseEvent(UseHoeEvent event) {
        if (ConfigurationHandler.enableCoarseDirt) {
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

        if (ConfigurationHandler.enableSkullDrop)
            dropHead(event.entityLiving, event.source, event.lootingLevel, event.drops);

        Random rand = event.entityLiving.worldObj.rand;
        if (ConfigurationHandler.enableMutton && event.entityLiving instanceof EntitySheep) {
            int amount = rand.nextInt(3) + 1 + rand.nextInt(1 + event.lootingLevel);
            for (int i = 0; i < amount; i++)
                if (event.entityLiving.isBurning())
                    addDrop(new ItemStack(ModItems.cooked_mutton), event.entityLiving, event.drops);
                else
                    addDrop(new ItemStack(ModItems.raw_mutton), event.entityLiving, event.drops);
        }
        
        if (ConfigurationHandler.enableNewFlowers && event.entity instanceof EntityLivingBase && event.source.getEntity() instanceof EntityWither) {
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
        if (ConfigurationHandler.enableEndermite) {
            EntityLivingBase entity = event.entityLiving;
            if (entity instanceof EntityPlayerMP)
                if (entity.getRNG().nextFloat() < 0.05F && entity.worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")) {
                    EntityEndermite entityendermite = new EntityEndermite(entity.worldObj);
                    entityendermite.setSpawnedByPlayer(true);
                    entityendermite.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
                    entity.worldObj.spawnEntityInWorld(entityendermite);
                }
        }
    }

    @SubscribeEvent
    public void spawnEvent(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPig) {
            EntityPig pig = (EntityPig) event.entity;
            if (ConfigurationHandler.enableBeetroot)
                pig.tasks.addTask(4, new EntityAITempt(pig, 1.2, ModItems.beetroot, false));
        } else if (event.entity instanceof EntityChicken) {
            EntityChicken chicken = (EntityChicken) event.entity;
            if (ConfigurationHandler.enableBeetroot)
                chicken.tasks.addTask(3, new EntityAITempt(chicken, 1.0D, ModItems.beetroot_seeds, false));
        } else if (event.entity instanceof EntityWolf) {
            EntityWolf wolf = (EntityWolf) event.entity;
            if (ConfigurationHandler.enableRabbit)
                wolf.targetTasks.addTask(4, new EntityAITargetNonTamed(wolf, EntityRabbit.class, 200, false));
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
                if (stack.getItem() == ModItems.beetroot && ConfigurationHandler.enableBeetroot)
                    setAnimalInLove(animal, event.entityPlayer, stack);
            } else if (animal instanceof EntityChicken)
                if (stack.getItem() == ModItems.beetroot_seeds && ConfigurationHandler.enableBeetroot)
                    setAnimalInLove(animal, event.entityPlayer, stack);
        } else if (ConfigurationHandler.enableBabyGrowthBoost && isFoodItem(animal, stack))
            feedBaby(animal, event.entityPlayer, stack);
        
        if(animal instanceof EntitySheep && stack.getItem() != Items.dye && !animal.worldObj.isRemote) {
        	EntitySheep sheep = ((EntitySheep)animal);
        	for(int oreID : OreDictionary.getOreIDs(stack)) {
            	int fleeceColour = ~ArrayUtils.indexOf(ModRecipes.dyes, OreDictionary.getOreName(oreID)) & 15;
            	if(ArrayUtils.contains(ModRecipes.dyes, OreDictionary.getOreName(oreID)) && sheep.getFleeceColor() != fleeceColour
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
        else if (animal instanceof EntityPig && food.getItem() == ModItems.beetroot && ConfigurationHandler.enableBeetroot)
            return true;
        else if (animal instanceof EntityChicken && food.getItem() == ModItems.beetroot_seeds && ConfigurationHandler.enableBeetroot)
            return true;
        else
            return false;
    }

    @SubscribeEvent
    public void entityHurtEvent(LivingHurtEvent event) {
        if (!ConfigurationHandler.enableDmgIndicator)
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
                EntityPlayer player = (EntityPlayer) attacker;
                Vec3 look = player.getLookVec();
                look.rotateAroundY((float) Math.PI / 2);
                for (int i = 0; i < amount; i++) {
                    double x = event.entityLiving.posX - amount * 0.35 * look.xCoord / 2 + i * 0.35 * look.xCoord;
                    double y = event.entityLiving.posY + 1.5 + event.entityLiving.worldObj.rand.nextGaussian() * 0.05;
                    double z = event.entityLiving.posZ - amount * 0.35 * look.zCoord / 2 + i * 0.35 * look.zCoord;
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
            float f1 = p_70655_2_ * (float)i;
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
        else
        {
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
                f1 = p_70672_2_ * (float)j;
                p_70672_2_ = f1 / 25.0F;
            }

            if (p_70672_2_ <= 0.0F)
            {
                return 0.0F;
            }
            else
            {
                i = EnchantmentHelper.getEnchantmentModifierDamage(entity.getLastActiveItems(), p_70672_1_);

                if (i > 20)
                {
                    i = 20;
                }

                if (i > 0 && i <= 20)
                {
                    j = 25 - i;
                    f1 = p_70672_2_ * (float)j;
                    p_70672_2_ = f1 / 25.0F;
                }

                return p_70672_2_;
            }
        }
    }

    @SubscribeEvent
    public void entityStruckByLightning(EntityStruckByLightningEvent event) {
        if (ConfigurationHandler.enableVillagerTurnsIntoWitch && event.entity instanceof EntityVillager) {
            EntityVillager villager = (EntityVillager) event.entity;
            if (!villager.worldObj.isRemote) {
                EntityWitch witch = new EntityWitch(villager.worldObj);
                witch.copyLocationAndAnglesFrom(villager);
                witch.onSpawnWithEgg(null);
                villager.worldObj.spawnEntityInWorld(witch);
                villager.setDead();
            }
        }
    }
    @SubscribeEvent
    public void livingHurtEvent(LivingHurtEvent event) {
        Entity entity = event.entity;
        if(ConfigurationHandler.enableHayBaleFalls && entity != null
        		&& entity.worldObj.getBlock(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY - 0.20000000298023224D - entity.yOffset), MathHelper.floor_double(entity.posZ)) == Blocks.hay_block
        		&& event.source == DamageSource.fall) {
        	event.ammount *= ((float)ConfigurationHandler.hayBaleReducePercent / (float)100);
        }
        if ((entity == null) || (!(entity instanceof EntityLivingBase))) {
            return;
        }
        EntityLivingBase player = (EntityLivingBase)entity;
        
        if(ConfigurationHandler.enableTotemUndying)
            handleTotemCheck(player, event);
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
            entity.worldObj.playSoundEffect(entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, Reference.MOD_ID + ":item.totem_use", 1.0f, entity.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            
            entity.clearActivePotions();
            float healpercent = (float)ConfigurationHandler.totemHealPercent / 100;
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
    	if(ConfigurationHandler.enableSmoothStone && event.block == Blocks.double_stone_slab && event.blockMetadata == 8) {
    		event.drops.clear();
    		event.drops.add(new ItemStack(ModBlocks.smooth_stone, 1));
    	}
    }
    
    private boolean playerHasItem(final EntityPlayer player, final ItemStack ist, final boolean checkEnabled) {
        for (int slot = 0; slot < player.inventory.mainInventory.length; ++slot) {
            if (player.inventory.mainInventory[slot] != null && player.inventory.mainInventory[slot].isItemEqual(ist)) {
                return true;
            }
        }
        return false;
    }
    
    private void decreaseItemByOne(final EntityPlayer player, final Item item) {
        for (int slot = 0; slot < player.inventory.mainInventory.length; ++slot) {
            if (player.inventory.mainInventory[slot] != null) {
                if (player.inventory.mainInventory[slot].getItem() == item) {
                    player.inventory.decrStackSize(slot, 1);
                    return;
                }
            }
        }
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
                	String classname = block.getClass().getName().toLowerCase();
                	if((classname.contains("chest")) && classname.contains("ender") && block.getMaterial().equals(Material.rock))
                		s = Reference.MOD_ID + ":" + "block.ender_chest." + (event.name.contains("close") ? "close" : "open");
                	else if((classname.contains("chest")) && block.getMaterial().equals(Material.wood) && event.name.contains("close"))
                		s = Reference.MOD_ID + ":" + "block.chest.close";
                	
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
                        World world = mc.theWorld;
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
                    World world = cpw.mods.fml.client.FMLClientHandler.instance().getWorldClient();
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
    	if(string.equals("Basalt Deltas") || string.equals("Crystalline Crag")) {
    		return basaltDeltasMusic;
    	}
    	if(string.equals("Warped Forest") || string.equals("Abyssal Shadowland")) {
    		return warpedForestMusic;
    	}
    	if(string.equals("Crimson Forest") || string.equals("Foxfire Swamp")) {
    		return crimsonForestMusic;
    	}
    	if(string.equals("Soul Sand Valley")) {
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
                String prefix = event.name.substring(0, event.name.indexOf(".") + 1);
                float soundPit = (block.stepSound.getPitch()) * (prefix.contains("step") ? 0.5F : 0.8F);
                float soundVol = (block.stepSound.getVolume() + 1.0F) / (prefix.contains("step") ? 8F : 2F);
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
