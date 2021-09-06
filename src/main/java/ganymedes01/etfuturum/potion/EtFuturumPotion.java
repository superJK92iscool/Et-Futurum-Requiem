package ganymedes01.etfuturum.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;

public class EtFuturumPotion extends Potion {
    protected EtFuturumPotion(int p_i1573_1_, boolean p_i1573_2_, int p_i1573_3_) {
        super(p_i1573_1_, p_i1573_2_, p_i1573_3_);
    }

    public static Potion levitation;
    
    public static void init() {
        levitation = new PotionLevitation(24, true, 0xFFFFFF);
    }
    
    /**
     * If true, the following potion which is an instance of EtFuturumPotion will have a packet sent to all players nearby.
     * We do this because some effects like levitation require the client to actually know the effect is on the entity.
     */
    public boolean hasPacket() {
        return false;
    }
    
    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entity, BaseAttributeMap attributeMap, int potency) {
        super.applyAttributesModifiersToEntity(entity, attributeMap, potency);
        if(shouldSync(entity)) {
            PotionEffect effect = entity.getActivePotionEffect(this);
            if(effect != null) { // this could only be null if non-vanilla code called this method at the wrong time
                MinecraftServer.getServer().getConfigurationManager().sendToAllNear(
                        entity.posX, entity.posY, entity.posZ, 80, entity.dimension,
                        new S1DPacketEntityEffect(entity.getEntityId(), effect));
            }
        }
    }
    
    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entity, BaseAttributeMap attributeMap, int potency) {
        super.removeAttributesModifiersFromEntity(entity, attributeMap, potency);
        if(shouldSync(entity)) {
            // This packet only cares about the ID, the duration doesn't matter.
            // (The potion has been removed from activePotionsMap at this point so we can't reference that.) 
            MinecraftServer.getServer().getConfigurationManager().sendToAllNear(
                    entity.posX, entity.posY, entity.posZ, 80, entity.dimension,
                    new S1EPacketRemoveEntityEffect(entity.getEntityId(), new PotionEffect(id, 0)));
        }
    }
    
    private boolean shouldSync(EntityLivingBase entity) {
        // EntityPlayer's potion effects are already synced in EntityPlayerMP.
        return hasPacket() && !(entity instanceof EntityPlayer);
    }
}
