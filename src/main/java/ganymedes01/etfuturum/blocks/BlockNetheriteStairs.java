package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.EntityItemUninflammable;
import ganymedes01.etfuturum.items.block.ItemBlockUninflammable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockNetheriteStairs extends BlockGenericStairs implements ISubBlocksBlock {

    public BlockNetheriteStairs() {
        super(ModBlocks.netherite_block, 0);
        setBlockName(Utils.getUnlocalisedName("netherite_stairs"));
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockUninflammable.class;
    }
    
    protected void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack) {
    	// do not drop items while restoring blockstates, prevents item dupe
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops") && !world.restoringBlockSnapshots) {
            if (captureDrops.get()) {
                capturedDrops.get().add(stack);
                return;
            }
            float f = 0.7F;
            double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItemUninflammable(world, (double)x + d0, (double)y + d1, (double)z + d2, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }

}
