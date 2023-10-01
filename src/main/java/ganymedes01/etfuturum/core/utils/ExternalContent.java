package ganymedes01.etfuturum.core.utils;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class ExternalContent {

	public enum Blocks {

		CFB_CAMPFIRE(() -> GameRegistry.findBlock("campfirebackport", "campfire")),
		CFB_SOUL_CAMPFIRE(() -> GameRegistry.findBlock("campfirebackport", "soul_campfire")),
		CFB_CAMPFIRE_BASE(() -> GameRegistry.findBlock("campfirebackport", "campfire_base")),
		CFB_SOUL_CAMPFIRE_BASE(() -> GameRegistry.findBlock("campfirebackport", "soul_campfire_base")),

		TCON_GRAVEL_ORE(() -> GameRegistry.findBlock("TConstruct", "GravelOre")),

		NATURA_HEAT_SAND(() -> GameRegistry.findBlock("Natura", "heatsand")),

		NETHERLICIOUS_NETHER_GRAVEL(() -> GameRegistry.findBlock("netherlicious", "Nether_Gravel")),

		ENDERLICIOUS_END_ROCK(() -> GameRegistry.findBlock("enderlicious", "EndRock")),
		ENDERLICIOUS_SAND(() -> GameRegistry.findBlock("enderlicious", "EndSand")),

		HEE_END_STONE(() -> GameRegistry.findBlock("HardcoreEnderExpansion", "end_stone_terrain"));

		private Block block;
		private final Supplier<Block> blockSupplier;

		Blocks(Supplier<Block> blockSupplier) {
			this.blockSupplier = blockSupplier;
		}

		public Block get() {
			if (block == null) {
				block = blockSupplier.get();
			}
			return block;
		}
	}

	public enum Items {
		EXTRAUTILS_WATERING_CAN(() -> GameRegistry.findItem("ExtraUtilities", "watering_can"));

		private Item item;
		private final Supplier<Item> blockSupplier;

		Items(Supplier<Item> blockSupplier) {
			this.blockSupplier = blockSupplier;
		}

		public Item get() {
			if (item == null) {
				item = blockSupplier.get();
			}
			return item;
		}
	}
}
