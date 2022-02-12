package ganymedes01.etfuturum.entities;

import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;

public class EntityFallingDripstone extends EntityFallingBlock {

	public EntityFallingDripstone(World p_i45318_1_) {
		super(p_i45318_1_);
	}
	
	public EntityFallingDripstone(World p_i45318_1_, double p_i45318_2_, double p_i45318_4_, double p_i45318_6_) {
		super(p_i45318_1_, p_i45318_2_, p_i45318_4_, p_i45318_6_, ModBlocks.pointed_dripstone);
	}

}
