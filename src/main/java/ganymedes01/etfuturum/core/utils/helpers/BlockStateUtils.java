package ganymedes01.etfuturum.core.utils.helpers;

import net.minecraft.block.Block;

public class BlockStateUtils {
    //TODO: Add crashes when the wrong args are used like inputting a block ID in the getBlock or getMeta functions, if there is not exactly 1 ":"
    /**
     * This function is for getting the equivalent meta value for a BlockState value when unflattening an ID in structure NBT.
     * For example an up-down log is meta 0 in unflattened versions, x-facing log is 1 and z is 2.
     *
     * Wall and fence states are completely discarded as all of their connections and states are done directly in the renderer.
     */
    public static int getMetaFromState(String stateName, String state) {
        if(stateName.equals("axis")) { //For rotatable pillars like logs
            switch(state) {
                case "y": return 0;
                case "x": return 4;
                case "z": return 8;
            }
        }
        return 0;
    }
}
