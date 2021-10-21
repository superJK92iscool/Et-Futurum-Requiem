package ganymedes01.etfuturum.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHay;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item.ToolMaterial;

public class HoeHelper {
	
	private static ArrayList<Block> hoeBlocks = new ArrayList<Block>();
	
	public static void addToHoeArray(Block block) {
		hoeBlocks.add(block);
	}
	
	public static boolean hoeArrayHas(Block block) {
		return hoeBlocks.contains(block);
	}
	
	public static ArrayList<Block> getHoeArray() {
		return hoeBlocks;
	}
	
	public static Field getField(Class<?> clazz, String fieldName)
				throws NoSuchFieldException {
			try {
			  return clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			  Class<?> superClass = clazz.getSuperclass();
			  if (superClass == null) {
				throw e;
			  }
			return getField(superClass, fieldName);
			}
	  }

	  static void setFinalField(Field field, Object newValue) throws Exception {
			field.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(null, newValue);
		}
	
	public static void forceSetMaterial(Block block, Material material) {
		try {
			Field field = Block.class.getDeclaredFields()[34];
			field.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			field.set(block, material);
		} catch (Exception e) {
			e.printStackTrace(); //"Should" never happen
		}   
	}
}
