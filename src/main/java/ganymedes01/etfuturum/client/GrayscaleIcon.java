package ganymedes01.etfuturum.client;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.data.AnimationMetadataSection;

public class GrayscaleIcon extends TextureAtlasSprite {

	public GrayscaleIcon(String p_i1282_1_) {
		super(p_i1282_1_);
	}


	//Note for makamys
	//Even if you comment out this code entirely it still happens, doubt it's specifically the class, I may have registered it wrong.
	//To place a potion cauldron: /setblock ~ ~ ~ etfuturum:potion_cauldron 1 false {potionID:3}
	//You can replace the 3 potionID with any potion ID you want.
    public void loadSprite(BufferedImage[] p_147964_1_, AnimationMetadataSection p_147964_2_, boolean p_147964_3_)
    {
    	super.loadSprite(p_147964_1_, p_147964_2_, p_147964_3_);
    	for(int[][] colorFrameData : (ArrayList<int[][]>)framesTextureData) {
    		for(int[] frame : colorFrameData) {
    			for(int colorData : frame) {
                    int r = colorData >> 16 & 255;
                    int g = colorData >> 8 & 255;
                    int b = colorData >> 0 & 255;
                    r = g = b = (r+g+b)/3;
                    colorData = r << 16 | g << 8 | b;
    			}
    		}
    	}
    }
}
