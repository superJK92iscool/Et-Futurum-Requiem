package ganymedes01.etfuturum;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.launchwrapper.Launch;

public class EtFuturumMixinPlugin implements IMixinConfigPlugin {

	public static ConfigBase baseConfig;
	
	public static ConfigMixins mixinConfig;
	
	@Override
	public void onLoad(String mixinPackage) {
		final String configDir = "config" + File.separator + Reference.MOD_ID;
		
		mixinConfig = new ConfigMixins((new File(Launch.minecraftHome, configDir + File.separator + "mixins.cfg")));
		
//		File from before Et Futurum Requiem (Not in a subdirectory)
		File olderFile = new File(Launch.minecraftHome, "config" + File.separator + "etfuturum.cfg");
//		File from before Et Futurum Requiem 2.2.2
		File oldFile = new File(Launch.minecraftHome, configDir + File.separator + "etfuturum.cfg");

		oldFile.getParentFile().mkdirs();
		if(olderFile.exists()) {
			try {
				Files.copy(olderFile.toPath(), oldFile.toPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			olderFile.delete();
		}
		
		if(oldFile.exists()) {
			baseConfig = new ConfigBase(oldFile);
		}
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
		
		if(ConfigMixins.fireArrowsDetonateTNTCarts) {
			mixins.add("MixinEntityMinecartTNT");
		}
		
		if(ConfigMixins.deepslateLayerOptimization && ConfigBase.deepslateGenerationMode == 0) {
			mixins.add("MixinChunkProviderGenerate");
			if(ConfigBase.deepslateReplacesDirt || ConfigBase.deepslateReplacesStones || ConfigBlocksItems.enableDeepslateOres) {
				mixins.add("MixinWorldGenMinable");
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
