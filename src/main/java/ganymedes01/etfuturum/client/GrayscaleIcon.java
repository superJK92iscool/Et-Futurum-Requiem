package ganymedes01.etfuturum.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

/** Creates a grayscale copy of the texture with the provided name called {originalName}_gray. */
public class GrayscaleIcon extends TextureAtlasSprite {

	private final String originalName;
	
	public GrayscaleIcon(String name) {
		super(name + "_gray");
		originalName = name;
	}
    
    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
    	return true;
    }
    
    private int[][] createGrayscaleCopyOfFrame(int[][] original) {
    	int[][] copy = new int[original.length][];
    	
    	for(int mipMapLevelMaybe = 0; mipMapLevelMaybe < original.length; mipMapLevelMaybe++) {
    		int[] originalImage = original[mipMapLevelMaybe];
    		int[] image = copy[mipMapLevelMaybe] = new int[originalImage.length];
			for(int i = 0; i < image.length; i++) {
				int a = originalImage[i] >> 24 & 0xFF;
                int r = originalImage[i] >> 16 & 0xFF;
                int g = originalImage[i] >>  8 & 0xFF;
                int b = originalImage[i] >>  0 & 0xFF;
                r = g = b = (r+g+b)/3;
				image[i] = a << 24 | r << 16 | g << 8 | b;
			}
    	}
    	
    	return copy;
    }
    
    @Override
    public boolean load(IResourceManager manager, ResourceLocation location) {
    	TextureAtlasSprite original = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(originalName);
    	this.copyFrom(original);
    	this.animationMetadata = original.animationMetadata;
    	for(int i = 0; i < original.getFrameCount(); i++) {
    		this.framesTextureData.add(createGrayscaleCopyOfFrame(original.getFrameTextureData(i)));
    	}
    	
    	return false;
    }
}
