package ganymedes01.etfuturum.core.utils.helpers;

import net.minecraft.block.Block;

/**Used to store the results of an unflattening in meta, to easily store it in a map after reading a structure NBT file.*/
public class BlockState {
    private final Block theBlock;
    private final int theMeta;
    public BlockState(Block block, int meta) {
        theBlock = block;
        theMeta = meta;
    }

    public Block getBlock() {
        return theBlock;
    }

    public int getMeta() {
        return theMeta;
    }
}
