package ganymedes01.etfuturum.core.proxy;

import java.io.File;

import com.mojang.authlib.minecraft.MinecraftSessionService;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.model.ModelShulker;
import ganymedes01.etfuturum.client.renderer.block.BlockAmethystClusterRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockBarrelRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockChestRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockChorusFlowerRender;
import ganymedes01.etfuturum.client.renderer.block.BlockChorusPlantRender;
import ganymedes01.etfuturum.client.renderer.block.BlockColoredWaterCauldronRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockComposterRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockDoorRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockEndRodRender;
import ganymedes01.etfuturum.client.renderer.block.BlockGlazedTerracottaRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockLanternRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockLavaCauldronRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockLoomRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockPointedDripstoneRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockSlimeBlockRender;
import ganymedes01.etfuturum.client.renderer.block.BlockStonecutterRenderer;
import ganymedes01.etfuturum.client.renderer.block.BlockTrapDoorRenderer;
import ganymedes01.etfuturum.client.renderer.entity.ArmourStandRenderer;
import ganymedes01.etfuturum.client.renderer.entity.BrownMooshroomRenderer;
import ganymedes01.etfuturum.client.renderer.entity.ChestBoatRenderer;
import ganymedes01.etfuturum.client.renderer.entity.EndermiteRenderer;
import ganymedes01.etfuturum.client.renderer.entity.HuskRenderer;
import ganymedes01.etfuturum.client.renderer.entity.LingeringEffectRenderer;
import ganymedes01.etfuturum.client.renderer.entity.LingeringPotionRenderer;
import ganymedes01.etfuturum.client.renderer.entity.NewBoatRenderer;
import ganymedes01.etfuturum.client.renderer.entity.NewSnowGolemRenderer;
import ganymedes01.etfuturum.client.renderer.entity.PlacedEndCrystalRenderer;
import ganymedes01.etfuturum.client.renderer.entity.RabbitRenderer;
import ganymedes01.etfuturum.client.renderer.entity.ShulkerBulletRenderer;
import ganymedes01.etfuturum.client.renderer.entity.ShulkerRenderer;
import ganymedes01.etfuturum.client.renderer.entity.StrayOverlayRenderer;
import ganymedes01.etfuturum.client.renderer.entity.TechnobladeCrownRenderer;
import ganymedes01.etfuturum.client.renderer.entity.VillagerZombieRenderer;
import ganymedes01.etfuturum.client.renderer.item.ItemBannerRenderer;
import ganymedes01.etfuturum.client.renderer.item.ItemBowRenderer;
import ganymedes01.etfuturum.client.renderer.item.ItemShulkerBoxRenderer;
import ganymedes01.etfuturum.client.renderer.item.ItemSkullRenderer;
import ganymedes01.etfuturum.client.renderer.tileentity.TileEntityBannerRenderer;
import ganymedes01.etfuturum.client.renderer.tileentity.TileEntityFancySkullRenderer;
import ganymedes01.etfuturum.client.renderer.tileentity.TileEntityGatewayRenderer;
import ganymedes01.etfuturum.client.renderer.tileentity.TileEntityNewBeaconRenderer;
import ganymedes01.etfuturum.client.renderer.tileentity.TileEntityShulkerBoxRenderer;
import ganymedes01.etfuturum.client.renderer.tileentity.TileEntityWoodSignRenderer;
import ganymedes01.etfuturum.client.skins.NewRenderPlayer;
import ganymedes01.etfuturum.client.skins.NewSkinManager;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigEntities;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.core.handlers.ClientEventHandler;
import ganymedes01.etfuturum.core.utils.VersionChecker;
import ganymedes01.etfuturum.entities.EntityArmourStand;
import ganymedes01.etfuturum.entities.EntityBoostingFireworkRocket;
import ganymedes01.etfuturum.entities.EntityBrownMooshroom;
import ganymedes01.etfuturum.entities.EntityEndermite;
import ganymedes01.etfuturum.entities.EntityHusk;
import ganymedes01.etfuturum.entities.EntityLingeringEffect;
import ganymedes01.etfuturum.entities.EntityLingeringPotion;
import ganymedes01.etfuturum.entities.EntityNewBoat;
import ganymedes01.etfuturum.entities.EntityNewBoatWithChest;
import ganymedes01.etfuturum.entities.EntityNewSnowGolem;
import ganymedes01.etfuturum.entities.EntityPlacedEndCrystal;
import ganymedes01.etfuturum.entities.EntityRabbit;
import ganymedes01.etfuturum.entities.EntityShulker;
import ganymedes01.etfuturum.entities.EntityShulkerBullet;
import ganymedes01.etfuturum.entities.EntityStray;
import ganymedes01.etfuturum.entities.EntityZombieVillager;
import ganymedes01.etfuturum.spectator.SpectatorModeClient;
import ganymedes01.etfuturum.tileentities.TileEntityBanner;
import ganymedes01.etfuturum.tileentities.TileEntityGateway;
import ganymedes01.etfuturum.tileentities.TileEntityNewBeacon;
import ganymedes01.etfuturum.tileentities.TileEntityShulkerBox;
import ganymedes01.etfuturum.tileentities.TileEntityWoodSign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerEvents() {
		super.registerEvents();
		FMLCommonHandler.instance().bus().register(ClientEventHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ClientEventHandler.INSTANCE);
		if(ConfigMixins.enableSpectatorMode) {
			FMLCommonHandler.instance().bus().register(SpectatorModeClient.INSTANCE);
			MinecraftForge.EVENT_BUS.register(SpectatorModeClient.INSTANCE);
		}
		
		if(ConfigFunctions.enableUpdateChecker && !EtFuturum.SNAPSHOT_BUILD && !EtFuturum.DEV_ENVIRONMENT) {
			FMLCommonHandler.instance().bus().register(VersionChecker.instance);
		}
//        MinecraftForge.EVENT_BUS.register(BiomeFogEventHandler.INSTANCE);
	}

	@Override
	public void registerRenderers() {
		registerItemRenderers();
		registerBlockRenderers();
		registerEntityRenderers();
	}

	private void registerItemRenderers() {
		if (ConfigBlocksItems.enableBanners) {
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.banner), new ItemBannerRenderer());
		}
		if (ConfigFunctions.enableFancySkulls) {
			MinecraftForgeClient.registerItemRenderer(Items.skull, new ItemSkullRenderer());
		}
		if (ConfigFunctions.enableBowRendering) {
			MinecraftForgeClient.registerItemRenderer(Items.bow, new ItemBowRenderer());
		}
		if(ConfigBlocksItems.enableShulkerBoxes) {
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.shulker_box), new ItemShulkerBoxRenderer());
		}
	}

	private void registerBlockRenderers() {
		if (ConfigBlocksItems.enableSlimeBlock)
			RenderingRegistry.registerBlockHandler(new BlockSlimeBlockRender());

		if (ConfigBlocksItems.enableDoors)
			RenderingRegistry.registerBlockHandler(new BlockDoorRenderer());

		if (ConfigBlocksItems.enableBanners)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBanner.class, new TileEntityBannerRenderer());

		if (ConfigFunctions.enableFancySkulls)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkull.class, new TileEntityFancySkullRenderer());

		if (ConfigBlocksItems.enableChorusFruit) {
			RenderingRegistry.registerBlockHandler(new BlockEndRodRender());
			RenderingRegistry.registerBlockHandler(new BlockChorusFlowerRender());
			RenderingRegistry.registerBlockHandler(new BlockChorusPlantRender());
		}

		if (ConfigBlocksItems.enableColourfulBeacons)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNewBeacon.class, new TileEntityNewBeaconRenderer());

		if (ConfigBlocksItems.enableLantern)
			RenderingRegistry.registerBlockHandler(new BlockLanternRenderer());
		
		if (ConfigBlocksItems.enableBarrel)
			RenderingRegistry.registerBlockHandler(new BlockBarrelRenderer());
		
		if (ConfigBlocksItems.enableTrapdoors)
			RenderingRegistry.registerBlockHandler(new BlockTrapDoorRenderer());
		
		if(ConfigBlocksItems.enableSigns)
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodSign.class, new TileEntityWoodSignRenderer());

		if(ConfigBlocksItems.enableGlazedTerracotta)
			RenderingRegistry.registerBlockHandler(new BlockGlazedTerracottaRenderer());
		
		if(ConfigBlocksItems.enableLavaCauldrons)
			RenderingRegistry.registerBlockHandler(new BlockLavaCauldronRenderer());

		if(ConfigBlocksItems.enableShulkerBoxes) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShulkerBox.class, new TileEntityShulkerBoxRenderer(new ModelShulker()));
		}
		
		if(ConfigBlocksItems.enableStonecutter) {
			RenderingRegistry.registerBlockHandler(new BlockStonecutterRenderer());
		}
		
		if(ConfigBlocksItems.enableComposter) {
			RenderingRegistry.registerBlockHandler(new BlockComposterRenderer());
		}
		
		if(ConfigBlocksItems.enableLoom) {
			RenderingRegistry.registerBlockHandler(new BlockLoomRenderer());
		}
		
		if(ConfigBlocksItems.enableAmethyst) {
			RenderingRegistry.registerBlockHandler(new BlockAmethystClusterRenderer());
		}
		
		{
			RenderingRegistry.registerBlockHandler(new BlockPointedDripstoneRenderer());
			RenderingRegistry.registerBlockHandler(new BlockColoredWaterCauldronRenderer());
		}
		
		RenderingRegistry.registerBlockHandler(new BlockChestRenderer());
		
		if(EtFuturum.TESTING) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGateway.class, new TileEntityGatewayRenderer());
		}
	}

	@SuppressWarnings("unchecked")
	private void registerEntityRenderers() {
		if (ConfigBlocksItems.enableArmourStand)
			RenderingRegistry.registerEntityRenderingHandler(EntityArmourStand.class, new ArmourStandRenderer());
		if (ConfigEntities.enableEndermite)
			RenderingRegistry.registerEntityRenderingHandler(EntityEndermite.class, new EndermiteRenderer());
		if (ConfigEntities.enableRabbit)
			RenderingRegistry.registerEntityRenderingHandler(EntityRabbit.class, new RabbitRenderer());
		
		if (ConfigEntities.enableHusk)
			RenderingRegistry.registerEntityRenderingHandler(EntityHusk.class, new HuskRenderer());
		if (ConfigEntities.enableStray) {
			RenderingRegistry.registerEntityRenderingHandler(EntityStray.class, new StrayOverlayRenderer());
		}
		
		if (ConfigBlocksItems.enableLingeringPotions) {
			RenderingRegistry.registerEntityRenderingHandler(EntityLingeringPotion.class, new LingeringPotionRenderer());
			RenderingRegistry.registerEntityRenderingHandler(EntityLingeringEffect.class, new LingeringEffectRenderer());
		}
		if (ConfigEntities.enableVillagerZombies)
			RenderingRegistry.registerEntityRenderingHandler(EntityZombieVillager.class, new VillagerZombieRenderer());
		
		if (ConfigEntities.enableDragonRespawn)
			RenderingRegistry.registerEntityRenderingHandler(EntityPlacedEndCrystal.class, new PlacedEndCrystalRenderer());

		if (ConfigEntities.enableBrownMooshroom)
			RenderingRegistry.registerEntityRenderingHandler(EntityBrownMooshroom.class, new BrownMooshroomRenderer());

		if(ConfigBlocksItems.enableNewBoats) {
			RenderingRegistry.registerEntityRenderingHandler(EntityNewBoat.class, new NewBoatRenderer());
			RenderingRegistry.registerEntityRenderingHandler(EntityNewBoatWithChest.class, new ChestBoatRenderer());
		}
		
		if(ConfigEntities.enableShulker) {
			RenderingRegistry.registerEntityRenderingHandler(EntityShulker.class, new ShulkerRenderer());
			RenderingRegistry.registerEntityRenderingHandler(EntityShulkerBullet.class, new ShulkerBulletRenderer());
		}
		
		RenderingRegistry.registerEntityRenderingHandler(EntityPig.class, new TechnobladeCrownRenderer());
		
//      {
//          RenderingRegistry.registerEntityRenderingHandler(EntityFallingDripstone.class, new FallingDripstoneRenderer());
//      }
		
		if (ConfigFunctions.enablePlayerSkinOverlay) {
			TextureManager texManager = Minecraft.getMinecraft().renderEngine;
			File skinFolder = new File(Minecraft.getMinecraft().fileAssets, "skins");
			MinecraftSessionService sessionService = Minecraft.getMinecraft().func_152347_ac();
			Minecraft.getMinecraft().field_152350_aA = new NewSkinManager(Minecraft.getMinecraft().func_152342_ad(), texManager, skinFolder, sessionService);

			RenderManager.instance.entityRenderMap.put(EntityPlayer.class, new NewRenderPlayer());
		}
		if (ConfigEntities.enableShearableSnowGolems)
			RenderingRegistry.registerEntityRenderingHandler(EntityNewSnowGolem.class, new NewSnowGolemRenderer());
	}
}