package ganymedes01.etfuturum;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.launchwrapper.Launch;

public class EtFuturumMixinPlugin implements IMixinConfigPlugin {

	static {
		ConfigMixins.loadMixinConfig(new File(Launch.minecraftHome, "config" + File.separator + Reference.MOD_ID + File.separator + "mixins.cfg"));
	}
	
	@Override
	public void onLoad(String mixinPackage) {
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
		
		if(ConfigMixins.fireArrowsDetonateTNTCarts) mixins.add("MixinEntityMinecartTNT");
		
		mixins.add("MixinChunkProviderGenerate");
		
		return mixins;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

}
