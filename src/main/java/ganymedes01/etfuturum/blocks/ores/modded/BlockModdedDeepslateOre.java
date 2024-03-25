package ganymedes01.etfuturum.blocks.ores.modded;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BaseSubtypesBlock;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.DummyWorld;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class BlockModdedDeepslateOre extends BaseSubtypesBlock {

	public static final List<BlockModdedDeepslateOre> loaded = Lists.newArrayList(); //Basically stores the block instances for recipe reasons.
	public final String[] ores;
	private final float[] hardnesses;
	private final float[] resistances;

	public BlockModdedDeepslateOre(String... names) {
		super(Material.rock, names);
		ores = new String[names.length];
		for (int i = 0; i < names.length; i++) {
			ores[i] = "ore" + StringUtils.capitalize(names[i].replaceFirst("^deepslate_", "").replace("_ore", ""));
		}
		hardnesses = new float[ores.length];
		resistances = new float[ores.length];
		setBlockName(Utils.getUnlocalisedName("modded_deepslate_ore"));
		setBlockSound(ModSounds.soundDeepslate);
		setHardness(4.5F);
		setResistance(3);
		setHarvestLevel("pickaxe", 1);
		if (ModBlocks.enableModdedDeepslateOres()) {
			loaded.add(this);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		for (int i = 0; i < ores.length; i++) {
			ItemStack stack = new ItemStack(item, 1, i);
			if (!EtFuturum.getOreStrings(stack).isEmpty()) {
				list.add(stack);
			}
		}
	}

	@Override
	public String getTextureDomain() {
		return Reference.MOD_ID;
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return hardnesses[Math.min(world.getBlockMetadata(x, y, z), hardnesses.length - 1)];
	}

	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		return resistances[Math.min(world.getBlockMetadata(x, y, z), resistances.length - 1)];
	}

	/**
	 * Calls Utils.getFirstBlockFromTag
	 *
	 * @param oreDictTag
	 * @return The first BLOCK in the tags list. If none are found or the tags list is empty, returns iron ore instead.
	 */
	public static ItemStack getFirstBlockFromTagOrIron(String oreDictTag) {
		ItemStack stack = Utils.getFirstFromTag(oreDictTag, itemStack -> itemStack.getItem() instanceof ItemBlock
				&& !(Block.getBlockFromItem(itemStack.getItem()) instanceof BlockModdedDeepslateOre));
		return stack == null ? new ItemStack(Blocks.iron_ore) : stack;
	}

	public void init() {
		DummyWorld world = new DummyWorld();
		for (int i = 0; i < ores.length; i++) {
			ItemStack stack = getFirstBlockFromTagOrIron(ores[i]);
			Block block = Block.getBlockFromItem(stack.getItem());
			//We do this cursed shit because these functions have no meta input. So we have to place it to "simulate" the method to get an accurate output for that specific meta.
			//Since many mod ores are based on meta we need to do this to ensure the returned resistance of the block is accurate. There is probably a better way to do this.
			//This also won't be "accurate" if the block returns different stuff depending on the input functions, but we can document and make special code for such cases as they come along.
			world.setBlock(0, 0, 0, block, stack.getItemDamage(), 0);
			try {
				setHarvestLevel("pickaxe", block.getHarvestLevel(stack.getItemDamage()), i);
				hardnesses[i] = block.getBlockHardness(world, 0, 0, 0) * 1.5F;
				resistances[i] = block.getExplosionResistance(null, world, 0, 0, 0, 0, 0, 0);
			} catch (Exception e) {
				setHarvestLevel("pickaxe", 1, i);
				hardnesses[i] = Blocks.iron_ore.blockHardness * 1.5F;
				resistances[i] = Blocks.iron_ore.blockResistance;
			}
		}
		world.clearBlocksCache();
	}
}
