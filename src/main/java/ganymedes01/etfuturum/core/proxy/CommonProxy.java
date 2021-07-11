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
import ganymedes01.etfuturum.client.gui.inventory.GuiSmoker;
import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.handlers.ServerEventHandler;
import ganymedes01.etfuturum.core.handlers.WorldTickEventHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.EntityArmourStand;
import ganymedes01.etfuturum.entities.EntityBrownMooshroom;
import ganymedes01.etfuturum.entities.EntityEndermite;
import ganymedes01.etfuturum.entities.EntityHusk;
import ganymedes01.etfuturum.entities.EntityItemUninflammable;
import ganymedes01.etfuturum.entities.EntityLingeringEffect;
import ganymedes01.etfuturum.entities.EntityLingeringPotion;
import ganymedes01.etfuturum.entities.EntityNewBoat;
import ganymedes01.etfuturum.entities.EntityNewBoatSeat;
import ganymedes01.etfuturum.entities.EntityNewSnowGolem;
import ganymedes01.etfuturum.entities.EntityPlacedEndCrystal;
import ganymedes01.etfuturum.entities.EntityRabbit;
import ganymedes01.etfuturum.entities.EntityRespawnedDragon;
import ganymedes01.etfuturum.entities.EntityStray;
import ganymedes01.etfuturum.entities.EntityTippedArrow;
import ganymedes01.etfuturum.entities.EntityZombieVillager;
import ganymedes01.etfuturum.entities.ModEntityList;
import ganymedes01.etfuturum.inventory.ContainerAnvil;
import ganymedes01.etfuturum.inventory.ContainerBlastFurnace;
import ganymedes01.etfuturum.inventory.ContainerEnchantment;
import ganymedes01.etfuturum.inventory.ContainerNewBrewingStand;
import ganymedes01.etfuturum.inventory.ContainerSmoker;
import ganymedes01.etfuturum.lib.GUIsID;
import ganymedes01.etfuturum.tileentities.TileEntityBanner;
import ganymedes01.etfuturum.tileentities.TileEntityBarrel;
import ganymedes01.etfuturum.tileentities.TileEntityBlastFurnace;
import ganymedes01.etfuturum.tileentities.TileEntityNewBeacon;
import ganymedes01.etfuturum.tileentities.TileEntityNewBrewingStand;
import ganymedes01.etfuturum.tileentities.TileEntitySmoker;
import ganymedes01.etfuturum.tileentities.TileEntityWoodSign;
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
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements IGuiHandler {

	public void registerEvents() {
		FMLCommonHandler.instance().bus().register(ConfigBase.INSTANCE);
		FMLCommonHandler.instance().bus().register(ServerEventHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ServerEventHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(new WorldTickEventHandler());
	}

	public void registerEntities() {
		int id = 0;
		
		if (ConfigBlocksItems.enableBanners)
			GameRegistry.registerTileEntity(TileEntityBanner.class, Utils.getUnlocalisedName("banner"));
		if (ConfigBase.enableArmourStand)
			ModEntityList.registerEntity(EntityArmourStand.class, "wooden_armorstand", id++, EtFuturum.instance, 64, 1, true);
		if (ConfigBase.enableEndermite)
			ModEntityList.registerEntity(EntityEndermite.class, "endermite", id++, EtFuturum.instance, 64, 1, true, 1447446, 7237230);
		if (ConfigBlocksItems.enableTippedArrows)
			ModEntityList.registerEntity(EntityTippedArrow.class, "tipped_arrow", id++, EtFuturum.instance, 64, 20, true);
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
		
		if (ConfigBlocksItems.enableRabbit) {
			ModEntityList.registerEntity(EntityRabbit.class, "rabbit", id++, EtFuturum.instance, 80, 3, true, 10051392, 7555121);

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
		
		if (ConfigBase.enableHusk) {
			ModEntityList.registerEntity(EntityHusk.class, "husk", id++, EtFuturum.instance, 80, 3, true, 7828833, 14735761);
			//change spawn weights
			EntityRegistry.removeSpawn(EntityZombie.class, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills });
			
			EntityRegistry.addSpawn(EntityZombie.class, 19, 4, 4, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills });
			EntityRegistry.addSpawn(EntityZombieVillager.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills });
			EntityRegistry.addSpawn(EntityHusk.class, 80, 4, 4, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills });
		}
		if (ConfigBase.enableStray) {
			ModEntityList.registerEntity(EntityStray.class, "stray", id++, EtFuturum.instance, 80, 3, true, 6387576, 15133418);
			//change spawn weights
			EntityRegistry.removeSpawn(EntitySkeleton.class, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.icePlains, BiomeGenBase.iceMountains });
			
			EntityRegistry.addSpawn(EntitySkeleton.class, 20, 4, 4, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.icePlains, BiomeGenBase.iceMountains });
			EntityRegistry.addSpawn(EntityStray.class, 80, 4, 4, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.icePlains, BiomeGenBase.iceMountains });
		}
		if (ConfigBase.enableNetherEndermen) {
			EntityRegistry.addSpawn(EntityEnderman.class, 1, 4, 4, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.hell });
			EntityEnderman.setCarriable(Blocks.netherrack, true);
		}
		
		if (ConfigBlocksItems.enableLingeringPotions) {
			ModEntityList.registerEntity(EntityLingeringPotion.class, "lingering_potion", id++, EtFuturum.instance, 64, 10, true);
			ModEntityList.registerEntity(EntityLingeringEffect.class, "lingering_effect", id++, EtFuturum.instance, 64, 1, true);
		}

		if (ConfigBase.enableVillagerZombies)
			ModEntityList.registerEntity(EntityZombieVillager.class, "villager_zombie", id++, EtFuturum.instance, 80, 3, true, 44975, 7969893);

		if (ConfigBase.enableDragonRespawn) {
			ModEntityList.registerEntity(EntityPlacedEndCrystal.class, "end_crystal", id++, EtFuturum.instance, 256, Integer.MAX_VALUE, false);
			ModEntityList.registerEntity(EntityRespawnedDragon.class, "ender_dragon", id++, EtFuturum.instance, 160, 3, true);
		}

		if (ConfigBase.enableShearableGolems)
			ModEntityList.registerEntity(EntityNewSnowGolem.class, "snow_golem", id++, EtFuturum.instance, 80, 3, true);
		
		if (ConfigBase.enableBrownMooshroom)
			ModEntityList.registerEntity(EntityBrownMooshroom.class, "brown_mooshroom", id++, EtFuturum.instance, 80, 3, true);

		if(ConfigBase.enableNewBoats)
			ModEntityList.registerEntity(EntityNewBoat.class, "new_boat", id++, EtFuturum.instance, 64, 1, true);
		
		ModEntityList.registerEntity(EntityNewBoatSeat.class, "new_boat_seat", id++, EtFuturum.instance, 64, 1, false);
		
		ModEntityList.registerEntity(EntityItemUninflammable.class, "fireproof_item", id++, EtFuturum.instance, 64, 1, true);
		//make magmas slightly more common, hopefully. 
		EntityRegistry.removeSpawn(EntityMagmaCube.class, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.hell });
		EntityRegistry.addSpawn(EntityMagmaCube.class, 2, 4, 4, EnumCreatureType.monster, new BiomeGenBase[] { BiomeGenBase.hell });
	}

	public void registerRenderers() {
	}

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
			default:
				return null;
		}
	}
}