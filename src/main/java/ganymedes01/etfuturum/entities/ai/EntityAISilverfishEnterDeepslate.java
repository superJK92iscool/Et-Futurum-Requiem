package ganymedes01.etfuturum.entities.ai;

import ganymedes01.etfuturum.blocks.BlockInfestedDeepslate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;

public class EntityAISilverfishEnterDeepslate extends EntityAIBase {

    private final EntitySilverfish theEntity;
    
	public EntityAISilverfishEnterDeepslate(EntitySilverfish entity) {
		theEntity = entity;
        setMutexBits(0);
	}

	@Override
	public boolean shouldExecute() {
		return theEntity.getEntityToAttack() == null && !theEntity.hasPath();
	}
	
	@Override
    public boolean continueExecuting() {
        return shouldExecute();
    }
	
	@Override
    public void updateTask() {
//        System.out.println(shouldExecute());
        int i = MathHelper.floor_double(theEntity.posX);
        int j = MathHelper.floor_double(theEntity.posY + 0.5D);
        int k = MathHelper.floor_double(theEntity.posZ);
        int l1 = theEntity.getRNG().nextInt(6);
        Block block = theEntity.worldObj.getBlock(i + Facing.offsetsXForSide[l1], j + Facing.offsetsYForSide[l1], k + Facing.offsetsZForSide[l1]);
        int i1 = theEntity.worldObj.getBlockMetadata(i + Facing.offsetsXForSide[l1], j + Facing.offsetsYForSide[l1], k + Facing.offsetsZForSide[l1]);

        if (BlockInfestedDeepslate.func_150196_a(block))
        {
            theEntity.worldObj.setBlock(i + Facing.offsetsXForSide[l1], j + Facing.offsetsYForSide[l1], k + Facing.offsetsZForSide[l1], Blocks.monster_egg, BlockSilverfish.func_150195_a(block, i1), 3);
            theEntity.spawnExplosionParticle();
            theEntity.setDead();
        }
    }

}
