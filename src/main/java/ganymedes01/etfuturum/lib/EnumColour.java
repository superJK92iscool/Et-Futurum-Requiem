package ganymedes01.etfuturum.lib;

import net.minecraft.block.BlockColored;
import net.minecraft.block.material.MapColor;
import net.minecraft.entity.passive.EntitySheep;

import java.awt.*;

public enum EnumColour {

	BLACK("black", MapColor.blackColor),
	RED("red", MapColor.redColor),
	GREEN("green", MapColor.greenColor),
	BROWN("brown", MapColor.brownColor),
	BLUE("blue", MapColor.blueColor),
	PURPLE("purple", MapColor.purpleColor),
	CYAN("cyan", MapColor.cyanColor),
	LIGHT_GREY("light_gray", MapColor.silverColor),
	GREY("gray", MapColor.grayColor),
	PINK("pink", MapColor.pinkColor),
	LIME("lime", MapColor.limeColor),
	YELLOW("yellow", MapColor.yellowColor),
	LIGHT_BLUE("light_blue", MapColor.lightBlueColor),
	MAGENTA("magenta", MapColor.magentaColor),
	ORANGE("orange", MapColor.adobeColor),
	WHITE("white", MapColor.snowColor);

	final String dye;
	final String name;
	final MapColor mapColor;

	EnumColour(String name, MapColor mapColor) {
		dye = "dye" + name;
		this.name = name;
		this.mapColor = mapColor;
	}

	public String getColorName() {
		return name;
	}

	public String getOreName() {
		return dye;
	}

	public MapColor getMapColour() {
		return mapColor;
	}

	public int getDamage() {
		return BlockColored.func_150031_c(ordinal());
	}

	public int getRGB() {
		int i = getDamage();
		return new Color(EntitySheep.fleeceColorTable[i][0], EntitySheep.fleeceColorTable[i][1], EntitySheep.fleeceColorTable[i][2]).getRGB();
	}

	public static EnumColour fromDamage(int meta) {
		meta = BlockColored.func_150031_c(meta);
		return values()[Math.min(Math.max(0, meta), values().length - 1)];
	}

}
