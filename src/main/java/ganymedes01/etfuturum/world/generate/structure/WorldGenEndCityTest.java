package ganymedes01.etfuturum.world.generate.structure;

import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import ganymedes01.etfuturum.core.utils.structurenbt.BlockState;
import ganymedes01.etfuturum.entities.EntityShulker;
import ganymedes01.etfuturum.world.generate.NBTStructure;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WorldGenEndCityTest extends WorldGenerator {
	@Override
	public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {
		return new Test().buildStructure(p_76484_1_, p_76484_2_, p_76484_3_, p_76484_4_, p_76484_5_, ForgeDirection.NORTH);
	}

	private class Test extends NBTStructure {
		public Test() {
			super("/data/structure/end_city/third_floor_2.nbt");
			//This file is not in the jar, do not initialize the class without adding it back first or it will crash the game
		}

		@Override
		public Map<Integer, BlockState> createPalette(ForgeDirection facing) {
			Map<Integer, BlockState> map = new HashMap<>();
			for(Pair<Integer, NBTTagCompound> pair : getPaletteNBT()) {
				//This was basically just a scapegoat to test some structure block functionality.
				//None of these are mapped because I didn't ant to do a bunch of mapping work for a test.
				BlockState state = getBlockNamespaceFromPaletteEntry(pair.getLeft()).equals("minecraft:air") ? new BlockState(Blocks.air) : getBlockNamespaceFromPaletteEntry(pair.getLeft()).equals("minecraft:chest") ? new BlockState(Blocks.chest, 1) : new BlockState(Blocks.stone);
				map.put(pair.getLeft(), state);
			}
			return map;
		}

		@Override
		public BlockState setStructureBlockAction(BlockPos pos, BlockState below, String data, ForgeDirection facing) {
			if(data.equals("Chest")) {
				below.setLootTable(ChestGenHooks.getInfo("netherFortress"));
				return null;
			}
			if(data.equals("Sentry")) {
				NBTTagCompound comp = new NBTTagCompound();
				comp.setByte("Color", (byte) 16);
				return new BlockState(EntityShulker.class, comp);
			}
			return null;
		}
	}
}
