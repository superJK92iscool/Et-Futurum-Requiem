package ganymedes01.etfuturum.compat;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class ExternalContent {

	public enum Blocks {
		CFB_CAMPFIRE(() -> GameRegistry.findBlock("campfirebackport", "campfire")),
		CFB_SOUL_CAMPFIRE(() -> GameRegistry.findBlock("campfirebackport", "soul_campfire")),
		CFB_CAMPFIRE_BASE(() -> GameRegistry.findBlock("campfirebackport", "campfire_base")),
		CFB_SOUL_CAMPFIRE_BASE(() -> GameRegistry.findBlock("campfirebackport", "soul_campfire_base")),

		TCON_GRAVEL_ORE(() -> GameRegistry.findBlock("TConstruct", "GravelOre")),

		NATURA_HEAT_SAND(() -> GameRegistry.findBlock("Natura", "heatsand")),
		NATURA_TAINTED_SOIL(() -> GameRegistry.findBlock("Natura", "soil.tainted")),

		NETHERLICIOUS_NETHER_GRAVEL(() -> GameRegistry.findBlock("netherlicious", "Nether_Gravel")),
		NETHERLICIOUS_SOUL_SOIL(() -> GameRegistry.findBlock("netherlicious", "SoulSoil")),
		NETHERLICIOUS_BONE_BLOCK(() -> GameRegistry.findBlock("netherlicious", "BoneBlock")),

		ENDERLICIOUS_END_ROCK(() -> GameRegistry.findBlock("enderlicious", "EndRock")),
		ENDERLICIOUS_SAND(() -> GameRegistry.findBlock("enderlicious", "EndSand")),

		HEE_END_STONE(() -> GameRegistry.findBlock("HardcoreEnderExpansion", "end_stone_terrain")),

		AE2_CERTUS_QUARTZ_ORE(() -> GameRegistry.findBlock("appliedenergistics2", "tile.OreQuartz")),
		AE2_CHARGED_CERTUS_QUARTZ_ORE(() -> GameRegistry.findBlock("appliedenergistics2", "tile.OreQuartzCharged")),

		ARS_MAGICA_2_ORE(() -> GameRegistry.findBlock("arsmagica2", "vinteumOre")),

		THAUMCRAFT_ORE(() -> GameRegistry.findBlock("Thaumcraft", "blockCustomOre")),

		BOP_GEM_ORE(() -> GameRegistry.findBlock("BiomesOPlenty", "gemOre")),

		DRACONIUM_ORE(() -> GameRegistry.findBlock("DraconicEvolution", "draconiumOre")),

		PROJECT_RED_ORE(() -> GameRegistry.findBlock("ProjRed|Exploration", "projectred.exploration.ore")),

		BR_YELLORITE_ORE(() -> GameRegistry.findBlock("BigReactors", "YelloriteOre")),

		BP_TESLATITE_ORE(() -> GameRegistry.findBlock("bluepower", "teslatite_ore")),
		BP_RUBY_ORE(() -> GameRegistry.findBlock("bluepower", "ruby_ore")),
		BP_SAPPHIRE_ORE(() -> GameRegistry.findBlock("bluepower", "sapphire_ore")),
		BP_AMETHYST_ORE(() -> GameRegistry.findBlock("bluepower", "amethyst_ore")),
		/*BP_MALACHITE_ORE(() -> GameRegistry.findBlock("bluepower", "malachite_ore")), Unused malachite texture in files? I'll have this set up here in case a fork or something uses it*/

		FISK_TUTRIDIUM_ORE(() -> GameRegistry.findBlock("fiskheroes", "tutridium_ore")),
		FISK_TUTRIDIUM_SPECKLED_STONE(() -> GameRegistry.findBlock("fiskheroes", "tutridium_stone")),
		FISK_VIBRANIUM_ORE(() -> GameRegistry.findBlock("fiskheroes", "vibranium_ore")),
		FISK_DWARF_STAR_ORE(() -> GameRegistry.findBlock("fiskheroes", "dwarf_star_ore")),
		FISK_OLIVINE_ORE(() -> GameRegistry.findBlock("fiskheroes", "olivine_ore")),
		FISK_ETERNIUM_ORE(() -> GameRegistry.findBlock("fiskheroes", "eternium_ore")),
		FISK_ETERNIUM_INFUSED_STONE(() -> GameRegistry.findBlock("fiskheroes", "eternium_stone")),

		FISK_NEXUS_BRICKS(() -> GameRegistry.findBlock("fiskheroes", "nexus_bricks")),

		DBC_WARENAI_ORE(() -> GameRegistry.findBlock("jinryuudragonblockc", "tile.BlockOreWrenai")),
		DBC_JJAY_ORE(() -> GameRegistry.findBlock("jinryuudragonblockc", "tile.JJayore")),
		DBC_DLOG_ORE(() -> GameRegistry.findBlock("jinryuudragonblockc", "tile.Dlogore")),
		DBC_LEHNORI_ORE(() -> GameRegistry.findBlock("jinryuudragonblockc", "tile.Lehnoriore")),

		SIMPLEORES_ADAMANTIUM_ORE(() -> GameRegistry.findBlock("simpleores", "adamantium_ore")),

		SIMPLEORES_ADAMANTIUM_BLOCK(() -> GameRegistry.findBlock("simpleores", "adamantium_block")),

		DQ_ROCKBOMB_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreBakudanisi")),
		DQ_BRIGHTEN_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreHikarinoisi")),
		DQ_LUCIDA_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreHosinokakera")),
		DQ_RESURROCK_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreInotinoisi")),
		DQ_MIRRORSTONE_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreKagaminoisi")),
		DQ_ICE_CRYSTAL_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreKoorinokessyou")),
		DQ_MINIMEDAL_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreLittlemedal")),
		DQ_DENSINIUM_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreMetaru")),
		DQ_GLASS_FRIT_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreMigakizuna")),
		DQ_MYTHRIL_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreMisuriru")),
		DQ_LUNAR_DIAMOND_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreMoon")),
		DQ_PLATINUM_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOrePuratina")),
		DQ_CORUNDUM_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreRubi")),
		DQ_SUNSTONE_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreTaiyounoisi")),
		DQ_ALLOYED_IRON_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreTekkouseki")),
		DQ_CHRONOCRYSTAL_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreTokinosuisyou")),
		DQ_VOLCANIC_ORE(() -> GameRegistry.findBlock("DQMIIINext", "BlockOreYougansekinokakera")),
		;

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

		public ItemStack newItemStack() {
			return newItemStack(1);
		}

		public ItemStack newItemStack(int count) {
			return newItemStack(count, 0);
		}

		public ItemStack newItemStack(int count, int meta) {
			return new ItemStack(this.get(), count, meta);
		}
	}

	public enum Items {
		EXTRAUTILS_WATERING_CAN(() -> GameRegistry.findItem("ExtraUtilities", "watering_can")),

		SIMPLEORES_ADAMANTIUM_INGOT(() -> GameRegistry.findItem("simpleores", "adamantium_ingot")),
		;

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

		public ItemStack newItemStack() {
			return newItemStack(1);
		}

		public ItemStack newItemStack(int count) {
			return newItemStack(count, 0);
		}

		public ItemStack newItemStack(int count, int meta) {
			return new ItemStack(this.get(), count, meta);
		}
	}
}
