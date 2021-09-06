package ganymedes01.etfuturum.potion;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;

public class CustomPotionEffect extends PotionEffect {
	
	private boolean hasPacket;
	private boolean firstTick;
	/**
	 * Potion effects sometimes are "Stuck" to the client and don't go down in durability.
	 * If the duration remains the same for more than 5 ticks, remove the effect.
	 */
	private int removeTicks;

	public CustomPotionEffect(PotionEffect p_i1577_1_) {
		super(p_i1577_1_);
		firstTick = true;
		if(Potion.potionTypes[getPotionID()] instanceof EtFuturumPotion && ((EtFuturumPotion)Potion.potionTypes[getPotionID()]).hasPacket()) {
			hasPacket = true;
		}
	}
	
    public CustomPotionEffect(int p_i1574_1_, int p_i1574_2_)
    {
        super(p_i1574_1_, p_i1574_2_, 0);
    }

    public CustomPotionEffect(int p_i1575_1_, int p_i1575_2_, int p_i1575_3_)
    {
        super(p_i1575_1_, p_i1575_2_, p_i1575_3_, false);
    }

    public CustomPotionEffect(int p_i1576_1_, int p_i1576_2_, int p_i1576_3_, boolean p_i1576_4_)
    {
    	super(p_i1576_1_, p_i1576_2_, p_i1576_3_, p_i1576_4_);
    }

	/**
	 * Has an extra bit to handle the hasPacket variable on the first tick.
	 * Used by levitation to notify the client that the effect is on the entity.
	 * We don't need to do this for players at the moment as the one effect that uses this is motion-based.
	 * Motion is not handled by the client for players on a multiplayer server, except yourself when your client always knows your effects.
	 */
    public boolean onUpdate(EntityLivingBase entity)
    {
    	boolean result = super.onUpdate(entity);
    	if(result) {
        	if(!entity.worldObj.isRemote && firstTick && !(entity instanceof EntityPlayer) && hasPacket) {
                PotionEffect effect = new PotionEffect(getPotionID(), getDuration());
        		MinecraftServer.getServer().getConfigurationManager().sendToAllNear(entity.posX, entity.posY, entity.posZ, 80, entity.dimension, new S1DPacketEntityEffect(entity.getEntityId(), effect));
        	}
    		firstTick = false;
    	}
    	return result;
    }

}
