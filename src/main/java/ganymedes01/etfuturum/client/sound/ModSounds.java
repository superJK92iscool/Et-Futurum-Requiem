package ganymedes01.etfuturum.client.sound;

import ganymedes01.etfuturum.client.sound.step.CustomSoundBerryBush;
import ganymedes01.etfuturum.client.sound.step.CustomSoundDeepslate;
import ganymedes01.etfuturum.client.sound.step.CustomSoundFungus;
import ganymedes01.etfuturum.client.sound.step.CustomSoundNetherite;
import ganymedes01.etfuturum.lib.Reference;
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
	public static final SoundType soundCrops = new CustomSound("crops", 0.9F, 1, true, true); //SoundEvent for placing uses volume 0.45 and pitch 1 or 1.2
	public static final SoundType soundCropWarts = new CustomSound("netherwart", 0.9F, 0.9F, true, true); //SoundEvent for placing uses volume 0.9 and pitch 1 or 1.12
	public static final SoundType soundHoneyBlock = new CustomSound("honey_block", 1, 1, true, true);
	public static final SoundType soundFungus = new CustomSoundFungus("fungus", 1, 1);
	public static final SoundType soundStem = new CustomSound("stem", 1, 1, true, true);
	public static final SoundType soundShroomlight = new CustomSound("shroomlight", 1, 1, true, true);
	public static final SoundType soundCopper = new CustomSound("copper", 1, 1, true, true);
	public static final SoundType soundBerryBush = new CustomSoundBerryBush("sweet_berry_bush", 0.8F, 1);
	public static final SoundType soundDeepslate = new CustomSoundDeepslate("deepslate", 1, 1);
	public static final SoundType soundDeepslateBricks = new CustomSoundDeepslate("deepslate_bricks", 1.3F, 1.3F);
	public static final SoundType soundTuff = new CustomSoundDeepslate("tuff", 1, 1);
	public static final SoundType soundVines = new CustomSound("vine", 0.9F, 1, true, true);
	
	private static final class CustomSound extends SoundType {

		private final boolean useDefaults;
		//private final boolean fromEF; // Never actually used! Uncomment if you want to use
		private final String prefix;

		public CustomSound(String name, float volume, float pitch, boolean useDefaults, boolean fromEF) {
			super(name, volume, pitch);
			this.useDefaults = useDefaults;
			//this.fromEF = fromEF; // Never actually used! Uncomment if you want to use
			this.prefix = fromEF ? (Reference.MOD_ID + ":") : "";
		}

		public CustomSound(String name) {
			this(name, false);
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