package ganymedes01.etfuturum.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.imageio.ImageIO;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

/** This resource pack will generate a grayscale version for any texture. The grayscale version of {name} is called {name}_grayscale. */
public class DynamicResourcePack implements IResourcePack {
    
	private static final String GRAYSCALE_SUFFIX = "_grayscale";
	
    @Override
    public Set<String> getResourceDomains() {
    	// No modded namespace support for now
        return ImmutableSet.of("minecraft");
    }
    
    public InputStream getInputStream(ResourceLocation resLoc) throws IOException {
    	InputStream original = Minecraft.getMinecraft().getResourceManager().getResource(toNonGrayscaleLocation(resLoc)).getInputStream();
        if(resLoc.getResourcePath().endsWith(".png")) {
	    	BufferedImage image = ImageIO.read(original);
	        convertImageToGrayscale(image);
	        byte[] data = null;
	        try(ByteArrayOutputStream os = new ByteArrayOutputStream()){
	        	ImageIO.write(image, "png", os);
	        	data = os.toByteArray();
	        }
	        return new ByteArrayInputStream(data);
        } else {
        	return original;
        }
    }
    
    private static void convertImageToGrayscale(BufferedImage image) {
    	for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				int rgb = image.getRGB(x, y);
				int a = rgb >> 24 & 0xFF;
                int r = rgb >> 16 & 0xFF;
                int g = rgb >>  8 & 0xFF;
                int b = rgb >>  0 & 0xFF;
                r = g = b = (r+g+b)/3;
				image.setRGB(x, y, a << 24 | r << 16 | g << 8 | b);
			}
		}
    }

    public boolean resourceExists(ResourceLocation resLoc) {
    	if(resLoc.getResourcePath().startsWith("textures/blocks") && resLoc.getResourcePath().contains(GRAYSCALE_SUFFIX + ".png")) {
    		return resourceExistsSomewhere(toNonGrayscaleLocation(resLoc));
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
        return "Et Futurum Requiem dynamic resources";
    }
    
    @SuppressWarnings("unchecked")
	public static void inject() {
        IResourcePack dynamicResourcePack = new DynamicResourcePack();
        Minecraft.getMinecraft().defaultResourcePacks.add(dynamicResourcePack);
        IResourceManager resMan = Minecraft.getMinecraft().getResourceManager();
        if(resMan instanceof SimpleReloadableResourceManager) {
            ((SimpleReloadableResourceManager)resMan).reloadResourcePack(dynamicResourcePack);
        }
    }
}
