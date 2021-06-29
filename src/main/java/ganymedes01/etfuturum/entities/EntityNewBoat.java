package ganymedes01.etfuturum.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNewBoat extends EntityBoat {

	public EntityNewBoat(World p_i1582_1_) {
		super(p_i1582_1_);
        this.setSize(1.5F, 0.6F);
        this.yOffset = this.height / 2.0F;
	}
	
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

	public float getRowingTime(float dummy1, float dummy2) {
		return 0;
	}
}
