package ganymedes01.etfuturum.lib;

import ganymedes01.etfuturum.lib.sounds.CustomSoundFungus;
import ganymedes01.etfuturum.lib.sounds.CustomSoundNetherite;
import net.minecraft.block.Block.SoundType;

public class ModSounds {
	
	public static final SoundType soundSlime = new CustomSound("mob.slime.big");
	public static final SoundType soundLantern = new CustomSound("lantern", 1, 1, true, true);
	public static final SoundType soundWartBlock = new CustomSound("wart_block", 1, 1, true, true);
	public static final SoundType soundSoulSand = new CustomSound("soul_sand", 1, 1, true, true);
	public static final SoundType soundNetherBricks = new CustomSound("nether_bricks", 1, 1, true, true);
	public static final SoundType soundBoneBlock = new CustomSound("bone_block", 1, 1, true, true);
	public static final SoundType soundNetherrack = new CustomSound("netherrack", 1, 1, true, true);
	public static final SoundType soundNetherOre = new CustomSound("nether_ore", 1, 1, true, true);
	public static final SoundType soundAncientDebris = new CustomSound("ancient_debris", 1, 1, true, true);
	public static final SoundType soundNylium = new CustomSound("nylium", 1, 1, true, true);
	public static final SoundType soundBasalt = new CustomSound("basalt", 1, 1, true, true);
	public static final SoundType soundNetherite = new CustomSoundNetherite("netherite_block", 1, 1);
	public static final SoundType soundCrops = new CustomSound("crops", 1, 1, true, true);
	public static final SoundType soundCropWarts = new CustomSound("nether_wart", 1, 1, true, true);
	public static final SoundType soundHoneyBlock = new CustomSound("honey_block", 1, 1, true, true);
	public static final SoundType soundFungus = new CustomSoundFungus("fungus", 1, 1);
	public static final SoundType soundStem = new CustomSound("stem", 1, 1, true, true);
	public static final SoundType soundShroomlight = new CustomSound("shroomlight", 1, 1, true, true);
	public static final SoundType soundCopper = new CustomSound("copper", 1, 1, true, true);

	private static final class CustomSound extends SoundType {

		private final boolean useDefaults;
		private final boolean fromEF;
		private final String prefix;

		public CustomSound(String name, float volume, float pitch, boolean useDefaults, boolean fromEF) {
			super(name, volume, pitch);
			this.useDefaults = useDefaults;
			this.fromEF = fromEF;
			this.prefix = fromEF ? (Reference.MOD_ID + ":") : "";
		}

		public CustomSound(String name) {
			this(name, 1.0F, 1.0F, false, false);
		}
		public CustomSound(String name, boolean fromEF) {
			this(name, 1.0F, 1.0F, false, fromEF);
		}

		@Override
		public String getBreakSound() {
			return useDefaults ? prefix + super.getBreakSound() : prefix + soundName;
		}

		@Override
		public String getStepResourcePath() {
			return useDefaults ? prefix + super.getStepResourcePath() : prefix + soundName;
		}
	}
}