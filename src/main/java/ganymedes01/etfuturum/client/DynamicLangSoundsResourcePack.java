package ganymedes01.etfuturum.client;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.math.NumberUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

public class DynamicLangSoundsResourcePack implements IResourcePack {

	private static final String GRAYSCALE_SUFFIX = "_grayscale";
	private static final HashMap<String, GrayscaleWaterResourcePack.GrayscaleType> grayscaleTypes = new HashMap<>();

	@Override
	public Set<String> getResourceDomains() {
		// No modded namespace support for now
		return ImmutableSet.of("etfuturum");
	}

	public InputStream getInputStream(ResourceLocation resLoc) throws IOException {
		InputStream original = Minecraft.getMinecraft().getResourceManager().getResource(toNonGrayscaleLocation(resLoc)).getInputStream();
//		if(resLoc.getResourcePath().endsWith(".png")) {
//			BufferedImage image = ImageIO.read(original);
//			String[] fileName = resLoc.getResourcePath().split("/");
//			image = convertImageToGrayscale(image, grayscaleTypes.get(fileName[fileName.length - 1].replace(".png", "")));
//			byte[] data = null;
//			try(ByteArrayOutputStream os = new ByteArrayOutputStream()){
//				ImageIO.write(image, "png", os);
//				data = os.toByteArray();
//			}
//			return new ByteArrayInputStream(data);
//		}
		return original;
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

	private ResourceLocation toNonGrayscaleLocation(ResourceLocation resLoc) {
		return new ResourceLocation(resLoc.getResourceDomain(), resLoc.getResourcePath().replaceFirst("_grayscale.png", ".png"));
	}

	public static String createGrayscaleName(String name) {
		return createGrayscaleName(name, GrayscaleWaterResourcePack.GrayscaleType.LUMINOSITY);
	}

	public static String createGrayscaleName(String name, GrayscaleWaterResourcePack.GrayscaleType type) {
		grayscaleTypes.put(name + GRAYSCALE_SUFFIX, type);
		return name + GRAYSCALE_SUFFIX;
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
		protected HashMap<String, String> publisher = new HashMap<String, String>();

		public JsonCreator() {
		}

		public JsonObject getJson() {
			JsonObject json = new JsonObject();
			return json;
		}
	}
}
