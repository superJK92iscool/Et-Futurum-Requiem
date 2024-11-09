package ganymedes01.etfuturum.compat;

import com.falsepattern.rple.api.common.block.RPLEBlockRenamed;
import com.falsepattern.rple.api.common.colorizer.RPLEBlockColorRegistry;
import com.falsepattern.rple.api.common.event.BlockColorRegistrationEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import static com.falsepattern.rple.api.common.color.DefaultColor.*;

public class CompatRPLEEventHandler {

	private static final CompatRPLEEventHandler instance = new CompatRPLEEventHandler();

	public static final short SOUL_FIRE = 0x49A;

	public static void registerRPLECompat() {
		FMLCommonHandler.instance().bus().register(instance);
	}

	@SubscribeEvent
	public void kolorsLol(BlockColorRegistrationEvent event) {
		RPLEBlockColorRegistry registry = event.registry();

		registry.colorizeBlock(ModBlocks.SEA_LANTERN.get()).brightness(0xF00).apply();
		registry.colorizeBlock(ModBlocks.END_ROD.get()).brightness(0xFAF).apply();
		registry.colorizeBlock(ModBlocks.MAGMA.get()).brightness(0x510).apply();
		registry.colorizeBlock(ModBlocks.BEACON.get()).brightness(getDefaultColorFor(Blocks.beacon)).apply();
		registry.colorizeBlock(ModBlocks.DEEPSLATE_LIT_REDSTONE_ORE.get()).brightness(getDefaultColorFor(Blocks.lit_redstone_ore)).apply();
		registry.colorizeBlock(ModBlocks.LANTERN.get()).brightness(0xFC5).apply();
		registry.colorizeBlock(ModBlocks.SOUL_LANTERN.get()).brightness(SOUL_FIRE).apply();
		registry.colorizeBlock(ModBlocks.SOUL_TORCH.get()).brightness(SOUL_FIRE).apply();
		registry.colorizeBlock(ModBlocks.LIT_BLAST_FURNACE.get()).brightness(getDefaultColorFor(Blocks.lit_furnace)).apply();
		registry.colorizeBlock(ModBlocks.LIT_SMOKER.get()).brightness(getDefaultColorFor(Blocks.lit_furnace)).apply();

		registerAmethystColor(registry, 0, 0x101);
		registerAmethystColor(registry, 1, 0x212);
		registerAmethystColor(registry, 2, 0x324);
		registerAmethystColor(registry, 3, 0x435);

		registerBulbColor(registry, 0, 0xFC8);
		registerBulbColor(registry, 1, 0xCA7);
		registerBulbColor(registry, 2, 0x863);
		registerBulbColor(registry, 3, 0x443);

		registry.colorizeBlock(ModBlocks.SLIME.get()).translucency(LIME).apply();
		registry.colorizeBlock(ModBlocks.HONEY_BLOCK.get()).translucency(0xFB7).apply();
	}

	private void registerAmethystColor(RPLEBlockColorRegistry registry, int clusterSize, int color) {
		Block cluster = clusterSize < 2 ? ModBlocks.AMETHYST_CLUSTER_1.get() : ModBlocks.AMETHYST_CLUSTER_2.get();
		boolean secondHalf = clusterSize % 2 == 0;
		for(int i = clusterSize % 2 == 0 ? 0 : 6; i < (clusterSize % 2 == 0 ? 6 : 12); i++) {
			registry.colorizeBlock(cluster, i).brightness(color).apply();
		}
	}

	private void registerBulbColor(RPLEBlockColorRegistry registry, int level, int color) {
		registry.colorizeBlock(ModBlocks.COPPER_BULB.get(), level + 4).brightness(color).apply();
		registry.colorizeBlock(ModBlocks.COPPER_BULB.get(), level + 12).brightness(color).apply();
		registry.colorizeBlock(ModBlocks.POWERED_COPPER_BULB.get(), level + 4).brightness(color).apply();
		registry.colorizeBlock(ModBlocks.POWERED_COPPER_BULB.get(), level + 12).brightness(color).apply();
	}

	private int getDefaultColorFor(Block block) {
		return RPLEBlockRenamed.of(block).rple$renamed$getLightValue();
	}
}
