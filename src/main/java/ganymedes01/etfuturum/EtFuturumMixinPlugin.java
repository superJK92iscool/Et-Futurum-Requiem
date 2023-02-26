package ganymedes01.etfuturum;

import ganymedes01.etfuturum.configuration.configs.*;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EtFuturumMixinPlugin implements IMixinConfigPlugin {
	
	public static boolean launchConfigWarning;
	protected static boolean serverSide;
	public static final MixinEnvironment.Side side = MixinEnvironment.getCurrentEnvironment().getSide();
	
	@Override
	public void onLoad(String mixinPackage) {
		final String configDir = "config" + File.separator + Reference.MOD_ID;
		
//      File from before Et Futurum Requiem (Not in a subdirectory)
		File olderFile = new File(Launch.minecraftHome, "config" + File.separator + "etfuturum.cfg");
//      File from before Et Futurum Requiem 2.2.2
		File oldFile = new File(Launch.minecraftHome, configDir + File.separator + "etfuturum.cfg");

		oldFile.getParentFile().mkdirs();
		if(olderFile.exists()) {            
			try {
				Files.copy(olderFile.toPath(), oldFile.toPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			olderFile.delete();
			launchConfigWarning = true;
		}
		
		if(oldFile.exists()) {
			launchConfigWarning = true;
		}

		ConfigBlocksItems.configInstance.syncConfig();
		ConfigEnchantsPotions.configInstance.syncConfig();
		ConfigFunctions.configInstance.syncConfig();
		ConfigTweaks.configInstance.syncConfig();
		ConfigWorld.configInstance.syncConfig();
		ConfigEntities.configInstance.syncConfig();
		ConfigSounds.configInstance.syncConfig();
		ConfigModCompat.configInstance.syncConfig();

		ConfigMixins.configInstance.syncConfig();
		
//      if(oldFile.exists()) {
//          ConfigBase.loadBaseConfig(oldFile);
//      }
		
		CompatMisc.doLwjgl3ifyCompat();
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins() {
		List<String> mixins = new ArrayList<>();
		
		if(ConfigMixins.endPortalFix) {
			mixins.add("MixinBlockEndPortal");
		}
		
		if(ConfigMixins.fenceWallConnectFix) {
			mixins.add("MixinBlockWall");
			mixins.add("MixinBlockFence");
		}

		if(ConfigMixins.stepHeightFix) {
			mixins.add("MixinEntity");
		}

		if(ConfigMixins.enableSpectatorMode) {
			mixins.add("MixinGameType");
			mixins.add("MixinEntity_Spectator");
			mixins.add("MixinWorld_Spectator");
			mixins.add("MixinEntityPlayer_Spectator");
			mixins.add("MixinNetHandlerPlayServer");
		}

		if(ConfigMixins.avoidDroppingItemsWhenClosing) {
			mixins.add("MixinEntityPlayerMP");
		}

		if(ConfigMixins.enableElytra) {
			mixins.add("elytra.MixinEntityPlayer");
			mixins.add("elytra.MixinEntityLivingBase");
			mixins.add("elytra.MixinNetHandlerPlayServer");
			mixins.add("elytra.MixinEntityTrackerEntry");
		}
		
		if(ConfigMixins.enableDoWeatherCycle) {
			mixins.add("doweathercycle.MixinCommandHandler");
			mixins.add("doweathercycle.MixinWorldInfo");
		}

		if(ConfigMixins.creativeFlightSpeedModifier > 1) {
			mixins.add("MixinEntityPlayer_CreativeFlightSpeed");
		}
		
		if(ConfigMixins.bouncyBeds) {
			mixins.add("MixinBlockBed");
		}
		
		if(ConfigMixins.newHurtSounds) {
			mixins.add("sounds.MixinEntityPlayer");
			if(side == MixinEnvironment.Side.CLIENT) {
				mixins.add("sounds.client.MixinEntityClientPlayerMP");
			}
		}
		
		if(ConfigMixins.newMobSounds) {
			mixins.add("sounds.MixinEntitySnowman");
			mixins.add("sounds.MixinEntitySkeleton");
			mixins.add("sounds.MixinEntitySquid");
			mixins.add("sounds.MixinEntityWitch");
		}
		
		if(ConfigMixins.floorCeilingButtons) {
			mixins.add("MixinBlockButton");
		}
		
		if(ConfigMixins.newEnderEyeSounds) {
			mixins.add("sounds.MixinItemEnderEye");
			mixins.add("sounds.MixinEntityEnderEye");
		}

		if(ConfigMixins.newEnchantingSounds) {
			mixins.add("sounds.MixinContainerEnchantment");
		}

		if(ConfigMixins.newFishingRodSounds) {
			mixins.add("sounds.MixinItemFishingRod");
		}

		if(ConfigMixins.newBeaconSounds) {
			mixins.add("sounds.MixinTileEntityBeacon");
		}

		if(ConfigMixins.enableObservers) {
			mixins.add("MixinWorld_Observer");
			mixins.add("MixinWorldServer_Observer");
		}

		if(ConfigMixins.arrowFallingFix) {
			mixins.add("MixinEntityArrow");
		}
		
		if(side == MixinEnvironment.Side.CLIENT) {

			if(ConfigMixins.newBeaconSounds) {
				mixins.add("sounds.client.MixinTileEntityBeacon_AmbienceOnly");
			}
			
			if(ConfigMixins.boundedBlockBreakingParticles) {
				mixins.add("client.MixinEffectRenderer");
			}
			
			if(ConfigMixins.furnaceCrackle) {
				mixins.add("client.MixinBlockFurnace");
			}
			
			if(ConfigMixins.enableSpectatorMode) {
				mixins.add("client.MixinEntityRenderer");
				mixins.add("client.MixinEntityPlayer");
				mixins.add("client.MixinWorldRenderer_Spectator");
			}
			if(ConfigEnchantsPotions.enableSwiftSneak) {
				mixins.add("client.MixinMovementInputFromOptions");
			}
			if(ConfigMixins.enableElytra) {
				mixins.add("elytra.client.MixinAbstractClientPlayer");
				mixins.add("elytra.client.MixinEntityPlayerSP");
				mixins.add("elytra.client.MixinRenderPlayer");
				mixins.add("elytra.client.MixinModelBiped");
				mixins.add("elytra.client.MixinEntityRenderer");
			}
		}
		
		return mixins;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

}
