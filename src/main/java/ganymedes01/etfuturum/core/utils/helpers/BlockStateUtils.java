package ganymedes01.etfuturum.core.utils.helpers;

import net.minecraft.block.Block;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockStateUtils {
	/**
	 * This function is for getting the equivalent meta value for a BlockState value when unflattening an ID in structure NBT.
	 * For example an up-down log is meta 0 in unflattened versions, x-facing log is 1 and z is 2.
	 *
	 * Wall and fence states are completely discarded as all of their connections and states are done directly in the renderer.
	 */
	public static int getMetaFromState(String stateName, String state, ForgeDirection facing) {
		//For rotatable pillars like logs
		if(stateName.equals("axis")) {
			switch(state) {
				case "y": return 0;
				case "x": return facing == ForgeDirection.NORTH || facing == ForgeDirection.SOUTH ? 4 : 8; //<= 4 is 2 and 3, north and sounth, other side would be 4 and 5, east and west.
				case "z": return facing == ForgeDirection.NORTH || facing == ForgeDirection.SOUTH ? 8 : 4;
			}
		}
		//For blocks that can face in different directions
		//Note not every block that uses this state has the same meta values in 1.7.10, be sure to subtract/add accordingly.
		if(stateName.equals("facing")) {
			switch(state) {
				case "down": return 0;
				case "up": return 1;
				case "north": return ((facing.ordinal() - 2) % 4) + 2;
				case "south": return (((facing.ordinal() - 2) + 1) % 4) + 2;
				case "west": return (((facing.ordinal() - 2) + 2) % 4) + 2;
				case "east": return (((facing.ordinal() - 2) + 3) % 4) + 2;
			}
		}
		return 0;
	}
}
