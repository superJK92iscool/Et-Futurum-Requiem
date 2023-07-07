package ganymedes01.etfuturum.client;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Logger;
import ganymedes01.etfuturum.lib.Reference;
import makamys.mclib.json.JsonUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

public class DynamicLangSoundsResourcePack implements IResourcePack {

	@Override
	public Set<String> getResourceDomains() {
		// No modded namespace support for now
		return ImmutableSet.of("etfuturum");
	}

	@Override
	public IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
		return null;
	}

	@Override
	public BufferedImage getPackImage() throws IOException {
		return null;
	}

	@Override
	public String getPackName() {
		return "Et Futurum Requiem dynamic sounds.json and lang";
	}

	public InputStream getInputStream(ResourceLocation resLoc) throws IOException {
//		InputStream original = Minecraft.getMinecraft().getResourceManager().getResource(resLoc).getInputStream();
		Logger.info(new JsonCreator().getJson().toString());
		return new ByteArrayInputStream(new JsonCreator().getJson().toString().getBytes());
	}

	public boolean resourceExists(ResourceLocation resLoc) {
		if(resLoc.getResourcePath().startsWith("sounds.json")) {
			return true;
		}
		return false;
	}

	private boolean resourceExistsSomewhere(ResourceLocation resLoc) {
		try {
			return Minecraft.getMinecraft().getResourceManager().getResource(resLoc) != null;
		} catch (IOException e) {}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static void inject() {
		IResourcePack dynamicResourcePack = new DynamicLangSoundsResourcePack();
		Minecraft.getMinecraft().defaultResourcePacks.add(dynamicResourcePack);
		IResourceManager resMan = Minecraft.getMinecraft().getResourceManager();
		if (resMan instanceof SimpleReloadableResourceManager) {
			((SimpleReloadableResourceManager) resMan).reloadResourcePack(dynamicResourcePack);
		}
	}

	public class JsonCreator {
		private final JsonObject rootObject = new JsonObject();

		private void addSoundsToCategory(String cat, String... sounds) {
			JsonObject soundCat = JsonUtil.getOrCreateObject(rootObject, cat);
			JsonArray soundList = JsonUtil.getOrCreateArray(soundCat, "sounds");
			for (String sound : sounds) {
				soundList.add(new JsonPrimitive(sound));
			}
		}

		public JsonObject getJson() {
			if(ConfigSounds.caveAmbience) {
				addSoundsToCategory("minecraft:ambient.cave.cave",
						Reference.MCAssetVer + ":ambient/cave/cave14",
						Reference.MCAssetVer + ":ambient/cave/cave15",
						Reference.MCAssetVer + ":ambient/cave/cave16",
						Reference.MCAssetVer + ":ambient/cave/cave17",
						Reference.MCAssetVer + ":ambient/cave/cave18",
						Reference.MCAssetVer + ":ambient/cave/cave19");
			}
			return rootObject;
		}
	}
}
