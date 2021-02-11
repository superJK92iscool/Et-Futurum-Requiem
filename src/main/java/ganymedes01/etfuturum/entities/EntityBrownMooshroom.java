package ganymedes01.etfuturum.entities;

import java.util.ArrayList;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntityBrownMooshroom extends EntityMooshroom {

	public EntityBrownMooshroom(World p_i1687_1_) {
		super(p_i1687_1_);
	}

    public boolean interact(EntityPlayer player)
    {
        ItemStack itemstack = player.inventory.getCurrentItem();

        return super.interact(player);
    }
    
    public EntityMooshroom createChild(EntityAgeable p_90011_1_)
    {
        return new EntityBrownMooshroom(this.worldObj);
    }
    
    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune)
    {
        setDead();
        EntityCow entitycow = new EntityCow(worldObj);
        entitycow.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
        entitycow.setHealth(this.getHealth());
        entitycow.renderYawOffset = renderYawOffset;
        worldObj.spawnEntityInWorld(entitycow);
        worldObj.spawnParticle("largeexplode", posX, posY + (double)(height / 2.0F), posZ, 0.0D, 0.0D, 0.0D);

        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        for (int i = 0; i < 5; i++)
        {
            ret.add(new ItemStack(Blocks.brown_mushroom));
        }
        playSound("mob.sheep.shear", 1.0F, 1.0F);
        return ret;
    }
}
