package ganymedes01.etfuturum.potion;

import net.minecraft.potion.Potion;

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
}
