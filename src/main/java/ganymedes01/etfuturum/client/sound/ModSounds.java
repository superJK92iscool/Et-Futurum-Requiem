package ganymedes01.etfuturum.client.sound;

import ganymedes01.etfuturum.client.sound.step.CustomSoundBerryBush;
import ganymedes01.etfuturum.client.sound.step.CustomSoundPlants;
import ganymedes01.etfuturum.client.sound.step.CustomSoundSlimeBlock;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;

public class ModSounds {
	
	public static final SoundType soundSlime = new CustomSoundSlimeBlock();
	public static final SoundType soundLantern = new CustomSound("lantern", true);
	public static final SoundType soundWartBlock = new CustomSound("wart_block");
	public static final SoundType soundSoulSand = new CustomSound("soul_sand");
	public static final SoundType soundNetherBricks = new CustomSound("nether_bricks");
	public static final SoundType soundBoneBlock = new CustomSound("bone_block");
	public static final SoundType soundNetherrack = new CustomSound("netherrack");
	public static final SoundType soundNetherOre = new CustomSound("nether_ore");
	public static final SoundType soundAncientDebris = new CustomSound("ancient_debris");
	public static final SoundType soundBasalt = new CustomSound("basalt");
	public static final SoundType soundNetherite = new CustomSound("netherite_block");
	public static final SoundType soundCrops = new CustomSoundPlants("crop", 1, 1, Block.soundTypeGrass);
	public static final SoundType soundCropWarts = new CustomSoundPlants("nether_wart", 1, 1, Block.soundTypeStone);
	public static final SoundType soundCopper = new CustomSound("copper");
	public static final SoundType soundBerryBush = new CustomSoundBerryBush();
	public static final SoundType soundDeepslate = new CustomSound("deepslate", true);
	public static final SoundType soundDeepslateBricks = new CustomSound("deepslate_bricks");
	public static final SoundType soundTuff = new CustomSound("tuff");
	public static final SoundType soundVines = new CustomSound("vine");
//	public static final SoundType soundNylium = new CustomSound("nylium");
//	public static final SoundType soundHoneyBlock = new CustomSound("honey_block");
//	public static final SoundType soundFungus = new CustomSound("fungus");
//	public static final SoundType soundStem = new CustomSound("stem");
//	public static final SoundType soundShroomlight = new CustomSound("shroomlight");
	
	public static class CustomSound extends SoundType {

		private final boolean placeSound;

		public CustomSound(String name, boolean placeSound) {
			this(name, 1, 1, placeSound);
		}
		
		public CustomSound(String name, float volume, float pitch, boolean placeSound) {
			super(name, volume, pitch);
			this.placeSound = placeSound;
		}
		
		public CustomSound(String name, float volume, float pitch) {
			this(name, volume, pitch, false);
		}
		
		public CustomSound(String name) {
			this(name, 1, 1, false);
		}

		@Override
		public String getBreakSound() {
			return Reference.MCv118 + ":block." + soundName + ".break";
		}

		@Override
		public String getStepResourcePath() {
			return Reference.MCv118 + ":block." + soundName + ".step";
		}

		@Override
		public String func_150496_b()
		{
			return placeSound ? Reference.MCv118 + ":block." + soundName + ".place" : this.getBreakSound();
		}
	}
}