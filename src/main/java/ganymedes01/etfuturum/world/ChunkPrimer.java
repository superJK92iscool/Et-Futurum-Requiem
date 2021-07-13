package ganymedes01.etfuturum.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ChunkPrimer {
    private static final Block DEFAULT_STATE = Blocks.air;
    private final Block[] blockList = new Block[32768];
    private final byte[] metaList = new byte[32768];

    public Block getBlockState(int x, int y, int z)
    {
        Block Block = this.blockList[getBlockIndex(x, y, z)];
        return Block == null ? DEFAULT_STATE : Block;
    }

    public void setBlockState(int x, int y, int z, Block state)
    {
        this.setBlockState(x, y, z, state, 0);
    }

    public void setBlockState(int x, int y, int z, Block state, int meta)
    {
        this.blockList[getBlockIndex(x, y, z)] = state;
        this.metaList[getBlockIndex(x, y, z)] = (byte) meta;
    }

    private static int getBlockIndex(int x, int y, int z)
    {
        return x << 12 | z << 8 | y;
    }

    /**
     * Counting down from the highest block in the sky, find the first non-air block for the given location
     * (actually, looks like mostly checks x, z+1? And actually checks only the very top sky block of actual x, z)
     */
    public int findGroundBlockIdx(int x, int z)
    {
        int i = (x << 12 | z << 8) + 256 - 1;

        for (int j = 255; j >= 0; --j)
        {
            Block Block = this.blockList[i + j];

            if (Block != null && Block != DEFAULT_STATE)
            {
                return j;
            }
        }

        return 0;
    }
    
    public Block[] getBlocks() {
    	return blockList;
    }
    
    public byte[] getMetas() {
    	return metaList;
    }
}
