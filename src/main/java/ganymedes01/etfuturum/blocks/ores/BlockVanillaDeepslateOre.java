package ganymedes01.etfuturum.blocks.ores;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.api.DeepslateOreRegistry;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import net.minecraft.block.Block;

public class BlockVanillaDeepslateOre extends BlockBaseDeepslateOre {

	public Block base;

	public BlockVanillaDeepslateOre(Block block, boolean defaultMapping) {
		super(block.getMaterial());
		copyAttribs(block);
		base = block;
		if (getClass().getName().startsWith("ganymedes01.etfuturum")) { //We only want to do this on my own stuff, not mods that extend it.
			//We use the texture name because texture naming conventions look just like namespaced IDs.
			//Block.blockRegistry.getNameFor does not work in preInit
			setNames("deepslate_" + block.textureName);
		}
		if (defaultMapping && ConfigBlocksItems.enableDeepslate && ConfigBlocksItems.enableDeepslateOres) {
			addDeepslateMappings();
		}
	}

	public BlockVanillaDeepslateOre(Block block) {
		this(block, true);
	}

	/**
	 * Deprecated in favor of a function in the class itself instead.
	 * This is done so mods that extend this class can just override it if they don't want the default copied settings.
	 *
	 * @param to
	 * @param from
	 */
	@Deprecated
	public static void setAttribs(Block to, Block from) {
		EtFuturum.copyAttribs(to, from);
		to.setHardness(from.blockHardness * 1.5F);
		to.setStepSound(ConfigSounds.newBlockSounds ? ModSounds.soundDeepslate : soundTypeStone);
	}

	protected void copyAttribs(Block from) {
		EtFuturum.copyAttribs(this, from);
		setHardness(from.blockHardness * 1.5F);
		setBlockSound(ModSounds.soundDeepslate);
	}

	protected void addDeepslateMappings() {
		DeepslateOreRegistry.addOre(base, 0, this, 0);
	}

	@Override
	protected Block getBase() {
		return base;
	}

	@Override
	protected int getBaseMeta() {
		return 0;
	}
}
