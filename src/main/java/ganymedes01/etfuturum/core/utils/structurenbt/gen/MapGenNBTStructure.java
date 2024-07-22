package ganymedes01.etfuturum.core.utils.structurenbt.gen;

import com.google.common.collect.Lists;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;

import java.util.List;

public abstract class MapGenNBTStructure extends MapGenStructure {

	protected final List<StructureNBTComponent> structurePieces = Lists.newArrayList();

	public MapGenNBTStructure() {
		registerStructurePieces();
		for(StructureNBTComponent struct : structurePieces) {
			MapGenStructureIO.func_143031_a(struct.getClass(), struct.pieceName);
		}
	}

	/**
	 * Add stuff to the structurePieces list here
	 */
	protected abstract void registerStructurePieces();
}