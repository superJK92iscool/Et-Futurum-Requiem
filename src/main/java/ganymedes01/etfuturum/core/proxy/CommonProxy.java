package ganymedes01.etfuturum.core.proxy;

import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.gui.inventory.GuiAnvil;
import ganymedes01.etfuturum.client.gui.inventory.GuiBlastFurnace;
import ganymedes01.etfuturum.client.gui.inventory.GuiEnchantment;
import ganymedes01.etfuturum.client.gui.inventory.GuiNewBrewingStand;
import ganymedes01.etfuturum.client.gui.inventory.GuiShulkerBox;
import ganymedes01.etfuturum.client.gui.inventory.GuiSmoker;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigEntities;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.configuration.configs.ConfigTweaks;
import ganymedes01.etfuturum.core.handlers.SculkEventHandler;
import ganymedes01.etfuturum.core.handlers.ServerEventHandler;
import ganymedes01.etfuturum.core.handlers.WorldEventHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.*;
import ganymedes01.etfuturum.inventory.ContainerAnvil;
import ganymedes01.etfuturum.inventory.ContainerBlastFurnace;
import ganymedes01.etfuturum.inventory.ContainerEnchantment;
import ganymedes01.etfuturum.inventory.ContainerNewBrewingStand;
import ganymedes01.etfuturum.inventory.ContainerShulkerBox;
import ganymedes01.etfuturum.inventory.ContainerSmoker;
import ganymedes01.etfuturum.lib.GUIsID;
import ganymedes01.etfuturum.spectator.SpectatorMode;
import ganymedes01.etfuturum.tileentities.*;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements IGuiHandler {

	public void registerEvents() {
		FMLCommonHandler.instance().bus().register(ServerEventHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ServerEventHandler.INSTANCE);
		
		FMLCommonHandler.instance().bus().register(WorldEventHandler.INSTANCE);
		MinecraftForge.TERRAIN_GEN_BUS.register(WorldEventHandler.INSTANCE);

		if(ConfigMixins.enableSpectatorMode) {
			FMLCommonHandler.instance().bus().register(SpectatorMode.INSTANCE);
			MinecraftForge.EVENT_BUS.register(SpectatorMode.INSTANCE);
		}

		if(ConfigBlocksItems.enableSculk) {
			FMLCommonHandler.instance().bus().register(SculkEventHandler.INSTANCE);
			MinecraftForge.EVENT_BUS.register(SculkEventHandler.INSTANCE);
		}
	}

	public void registerEntities() {
		if (ConfigBlocksItems.enableBanners)
			GameRegistry.registerTileEntity(TileEntityBanner.class, Utils.getUnlocalisedName("banner"));
		if (ConfigBlocksItems.enableArmourStand)
			ModEntityList.registerEntity(EntityArmourStand.class, "wooden_armorstand", 0, EtFuturum.instance, 64, 1, true);
		if (ConfigEntities.enableEndermite)
			ModEntityList.registerEntity(EntityEndermite.class, "endermite", 1, EtFuturum.instance, 64, 1, true, 0x161616, 0x6E6E6E);
		if (ConfigBlocksItems.enableTippedArrows)
			ModEntityList.registerEntity(EntityTippedArrow.class, "tipped_arrow", 2, EtFuturum.instance, 64, 20, true);
		if (ConfigBlocksItems.enableBrewingStands)
			GameRegistry.registerTileEntity(TileEntityNewBrewingStand.class, Utils.getUnlocalisedName("brewing_stand"));
		if (ConfigBlocksItems.enableColourfulBeacons)
			GameRegistry.registerTileEntity(TileEntityNewBeacon.class, Utils.getUnlocalisedName("beacon"));

		if (ConfigBlocksItems.enableBarrel)
			GameRegistry.registerTileEntity(TileEntityBarrel.class, Utils.getUnlocalisedName("barrel"));
		
		if (ConfigBlocksItems.enableSmoker)
			GameRegistry.registerTileEntity(TileEntitySmoker.class, Utils.getUnlocalisedName("smoker"));
		if (ConfigBlocksItems.enableBlastFurnace)
			GameRegistry.registerTileEntity(TileEntityBlastFurnace.class, Utils.getUnlocalisedName("blast_furnace"));
		
		if(ConfigBlocksItems.enableSigns)
			GameRegistry.registerTileEntity(TileEntityWoodSign.class, Utils.getUnlocalisedName("sign"));

		if(ConfigBlocksItems.enableSculk)
			GameRegistry.registerTileEntity(TileEntitySculkCatalyst.class, Utils.getUnlocalisedName("sculk_catalyst"));
		
		//Come back to
			GameRegistry.registerTileEntity(TileEntityGateway.class, Utils.getUnlocalisedName("end_gateway"));

			//Come back to
			GameRegistry.registerTileEntity(TileEntityShulkerBox.class, Utils.getUnlocalisedName("shulker_box"));
		
		if (ConfigEntities.enableRabbit) {
			ModEntityList.registerEntity(EntityRabbit.class, "rabbit", 3, EtFuturum.instance, 80, 3, true, 0x995F40, 0x734831);

			List<BiomeGenBase> biomes = new LinkedList<BiomeGenBase>();
			label: for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray())
				if (biome != null)
					// Check if pigs can spawn on this biome
					for (Object obj : biome.getSpawnableList(EnumCreatureType.creature))
						if (obj instanceof SpawnListEntry) {
							SpawnListEntry entry = (SpawnListEntry) obj;
							if (entry.entityClass == EntityPig.class) {
								biomes.add(biome);
								continue label;
							}
						}
			EntityRegistry.addSpawn(EntityRabbit.class, 10, 3, 3, EnumCreatureType.creature, biomes.toArray(new BiomeGenBase[biomes.size()]));
		}
		
		if (ConfigEntities.enableHusk) {
			ModEntityList.registerEntity(EntityHusk.class, "husk", 4, EtFuturum.instance, 80, 3, true, 0x777561, 0xE0D991);
			//change spawn weights
			EntityRegistry.removeSpawn(EntityZombie.class, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills });
			
			EntityRegistry.addSpawn(EntityZombie.class, 19, 4, 4, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills });
			EntityRegistry.addSpawn(EntityHusk.class, 80, 4, 4, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills });
		}
		if (ConfigEntities.enableStray) {
			ModEntityList.registerEntity(EntityStray.class, "stray", 5, EtFuturum.instance, 80, 3, true, 0x617778, 0xE6EAEA);
			//change spawn weights
			EntityRegistry.removeSpawn(EntitySkeleton.class, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.icePlains, BiomeGenBase.iceMountains });
			
			EntityRegistry.addSpawn(EntitySkeleton.class, 20, 4, 4, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.icePlains, BiomeGenBase.iceMountains });
			EntityRegistry.addSpawn(EntityStray.class, 80, 4, 4, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.icePlains, BiomeGenBase.iceMountains });
		}
		if (ConfigEntities.enableNetherEndermen) {
			EntityRegistry.addSpawn(EntityEnderman.class, 1, 4, 4, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.hell });
			EntityEnderman.setCarriable(Blocks.netherrack, true);
		}
		
		if (ConfigBlocksItems.enableLingeringPotions) {
			ModEntityList.registerEntity(EntityLingeringPotion.class, "lingering_potion", 6, EtFuturum.instance, 64, 10, true);
			ModEntityList.registerEntity(EntityLingeringEffect.class, "lingering_effect", 7, EtFuturum.instance, 64, 1, true);
		}

		if (ConfigEntities.enableVillagerZombies)
			ModEntityList.registerEntity(EntityZombieVillager.class, "villager_zombie", 8, EtFuturum.instance, 80, 3, true, 0x00AFAF, 0x799C65);

		if (ConfigEntities.enableDragonRespawn) {
			ModEntityList.registerEntity(EntityPlacedEndCrystal.class, "end_crystal", 9, EtFuturum.instance, 256, Integer.MAX_VALUE, false);
			ModEntityList.registerEntity(EntityRespawnedDragon.class, "ender_dragon", 10, EtFuturum.instance, 160, 3, true);
		}

		if (ConfigEntities.enableShearableSnowGolems)
			ModEntityList.registerEntity(EntityNewSnowGolem.class, "snow_golem", 11, EtFuturum.instance, 80, 3, true);
		
		if (ConfigEntities.enableBrownMooshroom)
			ModEntityList.registerEntity(EntityBrownMooshroom.class, "brown_mooshroom", 12, EtFuturum.instance, 80, 3, true);

		if(ConfigBlocksItems.enableNewBoats)
			ModEntityList.registerEntity(EntityNewBoat.class, "new_boat", 13, EtFuturum.instance, 64, 1, true);
		
		ModEntityList.registerEntity(EntityNewBoatSeat.class, "new_boat_seat", 14, EtFuturum.instance, 64, 1, false);
		
		ModEntityList.registerEntity(EntityItemUninflammable.class, "fireproof_item", 15, EtFuturum.instance, 64, 1, true);
		
		if(ConfigEntities.enableShulker) {
			ModEntityList.registerEntity(EntityShulker.class, "shulker", 16, EtFuturum.instance, 64, 1, false, 0x946794, 0x4D3852);
			ModEntityList.registerEntity(EntityShulkerBullet.class, "shulker_candy", 17, EtFuturum.instance, 64, 1, true);
			
			if(ConfigTweaks.shulkersSpawnAnywhere) {
				EntityRegistry.addSpawn(EntityShulker.class, 2, 1, 2, EnumCreatureType.monster, BiomeDictionary.getBiomesForType(Type.END));
			}
		}
		
//      {
//          ModEntityList.registerEntity(EntityFallingDripstone.class, "falling_dripstone", 18, EtFuturum.instance, 64, 1, true);
//      }

		if(ConfigBlocksItems.enableNewBoats) {
			ModEntityList.registerEntity(EntityNewBoatWithChest.class, "chest_boat", 19, EtFuturum.instance, 64, 1, true);
		}
		
		//make magmas slightly more common, hopefully.
		EntityRegistry.removeSpawn(EntityMagmaCube.class, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.hell });
		EntityRegistry.addSpawn(EntityMagmaCube.class, 2, 4, 4, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.hell });
	}
	
	public void registerRenderers() {};

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case GUIsID.ENCHANTING_TABLE:
				return new ContainerEnchantment(player.inventory, world, x, y, z);
			case GUIsID.ANVIL:
				return new ContainerAnvil(player, world, x, y, z);
			case GUIsID.BREWING_STAND:
				return new ContainerNewBrewingStand(player.inventory, (TileEntityNewBrewingStand) world.getTileEntity(x, y, z));
			case GUIsID.BARREL:
				return new ContainerChest(player.inventory, (TileEntityBarrel) world.getTileEntity(x, y, z));
			case GUIsID.SMOKER:
				return new ContainerSmoker(player.inventory, (TileEntitySmoker) world.getTileEntity(x, y, z));
			case GUIsID.BLAST_FURNACE:
				return new ContainerBlastFurnace(player.inventory, (TileEntityBlastFurnace) world.getTileEntity(x, y, z));
			case GUIsID.SHULKER_BOX:
				return new ContainerShulkerBox(player.inventory, (TileEntityShulkerBox) world.getTileEntity(x, y, z));
			default:
				return null;
		}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case GUIsID.ENCHANTING_TABLE:
				return new GuiEnchantment(player.inventory, world, null);
			case GUIsID.ANVIL:
				return new GuiAnvil(player, world, x, y, z);
			case GUIsID.BREWING_STAND:
				return new GuiNewBrewingStand(player.inventory, (TileEntityNewBrewingStand) world.getTileEntity(x, y, z));
			case GUIsID.BARREL:
				return new GuiChest(player.inventory, (TileEntityBarrel) world.getTileEntity(x, y, z));
			case GUIsID.SMOKER:
				return new GuiSmoker(player.inventory, (TileEntitySmoker) world.getTileEntity(x, y, z));
			case GUIsID.BLAST_FURNACE:
				return new GuiBlastFurnace(player.inventory, (TileEntityBlastFurnace) world.getTileEntity(x, y, z));
			case GUIsID.SHULKER_BOX:
				return new GuiShulkerBox(player.inventory, (TileEntityShulkerBox) world.getTileEntity(x, y, z));
			default:
				return null;
		}
	}
}