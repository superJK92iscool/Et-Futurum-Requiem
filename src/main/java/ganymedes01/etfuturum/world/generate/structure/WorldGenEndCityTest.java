package ganymedes01.etfuturum.world.generate.structure;

import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import ganymedes01.etfuturum.core.utils.structurenbt.BlockStateContainer;
import ganymedes01.etfuturum.core.utils.structurenbt.EFRBlockStateConverter;
import ganymedes01.etfuturum.core.utils.structurenbt.NBTStructure;
import ganymedes01.etfuturum.entities.EntityShulker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class WorldGenEndCityTest extends WorldGenerator {
	@Override
	public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {
		return new Test().placeStructure(p_76484_1_, p_76484_2_, p_76484_3_, p_76484_4_, p_76484_5_, ForgeDirection.NORTH);
	}

	private class Test extends NBTStructure {
		public Test() {
			super("/data/structure/end_city/tower_top.nbt", EFRBlockStateConverter.INSTANCE);
		}

		@Override
		public BlockStateContainer setStructureBlockAction(BlockPos pos, BlockStateContainer below, String data, ForgeDirection facing) {
			if (data.equals("Chest")) {
				below.setLootTable(ChestGenHooks.getInfo("netherFortress"));
				return null;
			}
			if (data.equals("Sentry")) {
				NBTTagCompound comp = new NBTTagCompound();
				comp.setByte("Color", (byte) 16);
				return new BlockStateContainer(EntityShulker.class, comp);
			}
			return null;
		}
	}
}
