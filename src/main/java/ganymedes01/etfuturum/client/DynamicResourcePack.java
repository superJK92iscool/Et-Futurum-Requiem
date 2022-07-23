package ganymedes01.etfuturum.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.math.NumberUtils;

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
	private static final HashMap<String, GrayscaleType> grayscaleTypes = new HashMap<>();
	
    @Override
    public Set<String> getResourceDomains() {
    	// No modded namespace support for now
        return ImmutableSet.of("minecraft");
    }
    
    public InputStream getInputStream(ResourceLocation resLoc) throws IOException {
    	InputStream original = Minecraft.getMinecraft().getResourceManager().getResource(toNonGrayscaleLocation(resLoc)).getInputStream();
        if(resLoc.getResourcePath().endsWith(".png")) {
	    	BufferedImage image = ImageIO.read(original);
	    	String[] fileName = resLoc.getResourcePath().split("/");
	        convertImageToGrayscale(image, grayscaleTypes.get(fileName[fileName.length - 1].replace(".png", "")));
	        byte[] data = null;
	        try(ByteArrayOutputStream os = new ByteArrayOutputStream()){
	        	ImageIO.write(image, "png", os);
	        	data = os.toByteArray();
	        }
	        return new ByteArrayInputStream(data);
        }
		return original;
    }
    
    private static void convertImageToGrayscale(BufferedImage image, GrayscaleType type) {
    	boolean iteratedOnce = false; //Used by HIGHEST_LUMINOSITY to determine if we should iterate again.
    	int highest = 0; //Used by HIGHEST_LUMINOSITY to find the brightest pixel.
    	reloop:
    	for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++){
				int rgb = image.getRGB(x, y);
				int a = rgb >> 24 & 0xFF;
                int r = rgb >> 16 & 0xFF;
                int g = rgb >>  8 & 0xFF;
                int b = rgb >>  0 & 0xFF;
                switch(type) {
            		case HIGHEST_LUMINOSITY:
                        r = (int) (r * 0.299);
                        g = (int) (g * 0.587);
                        b = (int) (b * 0.114);
        				int maxrgb = r+g+b;
            			if(!iteratedOnce) {
            				if(maxrgb > highest) {
            					highest = maxrgb;
            				}
            				//Check if we're on the last iteration and if we are, reset the iterator.
            				if(x == image.getWidth() - 1 && y == image.getHeight() - 1) {
            					iteratedOnce = true;
            					highest = 255 - highest;
            					//For some reason even if we use a continue we have to reset these for variables anyways?
            					//Also without the continue for some reason x starts at 1 instead of 0, skipping the top-left pixel.
            					//This is probably because the x loop sees it's reached the end of its loop and increments the number.
            					y = x = 0; 
                				continue reloop;
            				}
            				continue;
            			}

    					r = b = g = maxrgb + highest;
    					
            			break;
            		case AVERAGE:
            			r = g = b = (r+g+b)/3;
            			break;
                	case LIGHTNESS:
                		r = g = b = (NumberUtils.min(r, g, b) + NumberUtils.max(r, g, b)) / 2;
            			break;
                	case LUMINOSITY:
                	default:
                        r = (int) (r * 0.299);
                        g = (int) (g * 0.587);
                        b = (int) (b * 0.114);
                        r = g = b = r+g+b;
                }
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
		return createGrayscaleName(name, GrayscaleType.LUMINOSITY);
	}
    
    public static String createGrayscaleName(String name, GrayscaleType type) {
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

    public enum GrayscaleType {
    	/**
    	 * Grayscaling based on the lightness of the pixel
    	 * (min(r, g, b) + max(r, g, b)) / 2
    	 */
    	LIGHTNESS(),
    	/**
    	 * Grayscaling based on the average RGB values of the pixel
    	 * (r+g+b)/3
    	 */
    	AVERAGE(),
    	/**
    	 * Grayscaling based on the luminosity of the colors, weighing the RGB values based on the human eye's sensitivity to the color.
    	 * (r * 0.299) + (g * 0.587) + (b * 0.114)
    	 * This is the default for grayscaling if no choice is made.
    	 */
    	LUMINOSITY(),
    	/**
    	 * A custom formula that takes the brightest luminosity grayscale value in the given pixel and uses it as the baseline for the grayscaling.
    	 * This ensures there's at least one white pixel in the texture, this is good for generating an image used for coloring.
    	 * Uses LUMINOSITY as a baseline.
    	 * highest = highest luminosity gray value
    	 * (r * 0.299) + (g * 0.587) + (b * 0.114) + highest
    	 */
    	HIGHEST_LUMINOSITY()
    	
    }
}
