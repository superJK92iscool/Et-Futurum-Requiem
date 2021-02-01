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
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemTool;

public class HoeHelper {
	
	public static void init() {
		Iterator iterator = Block.blockRegistry.iterator();
		while(iterator.hasNext()) {
			Block block = (Block) iterator.next();
			if(block == null)
				continue;
			
			if(block instanceof BlockLeaves)
				addToHoeArray(block);
			
			if(block instanceof BlockHay)
				addToHoeArray(block);
			
			if(block instanceof BlockSponge)
				addToHoeArray(block);
		}
		addToHoeArray(ModBlocks.sponge);
		addToHoeArray(ModBlocks.nether_wart_block);
	}
	
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
	
	/**
	 * Return 0 if the input is not a tool.
	 * Gets private tool speed value from tool material.
	 * @param item
	 * @return
	 */
	public static float getToolSpeed(Item item) {
		float returnValue = 0;
		try {
			if(item instanceof ItemHoe || item instanceof ItemTool) {
				Field theToolMaterialField;
				if(item instanceof ItemTool)
					theToolMaterialField = ItemTool.class.getDeclaredFields()[3];
				else
					theToolMaterialField = ItemHoe.class.getDeclaredFields()[0];
				theToolMaterialField.setAccessible(true);
				Item.ToolMaterial theToolMaterial = (Item.ToolMaterial)theToolMaterialField.get(item);
				Field efficiencyOnProperMaterialField = Item.ToolMaterial.class.getDeclaredFields()[7];
				efficiencyOnProperMaterialField.setAccessible(true);
				returnValue = efficiencyOnProperMaterialField.getFloat(theToolMaterial);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	  public static Field getField(Class clazz, String fieldName)
		        throws NoSuchFieldException {
		    try {
		      return clazz.getDeclaredField(fieldName);
		    } catch (NoSuchFieldException e) {
		      Class superClass = clazz.getSuperclass();
		      if (superClass == null) {
		        throw e;
		      } else {
		        return getField(superClass, fieldName);
		      }
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
