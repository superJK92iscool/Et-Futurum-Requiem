package ganymedes01.etfuturum.potion;

import net.minecraft.potion.Potion;

public class EtFuturumPotions {
	public static Potion levitation;
	
	public static void init() {
		levitation = new PotionLevitation(24, true, 0xFFFFFF);
	}
}
