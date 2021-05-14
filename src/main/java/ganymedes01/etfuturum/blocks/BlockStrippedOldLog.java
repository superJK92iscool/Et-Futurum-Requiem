package ganymedes01.etfuturum.blocks;

import java.lang.reflect.Field;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemBlockGeneric;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.BlockOldLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class BlockStrippedOldLog extends BlockOldLog implements IConfigurable, ISubBlocksBlock {

	public BlockStrippedOldLog() {
		setBlockName(Utils.getUnlocalisedName("log_stripped"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.field_150167_a = new IIcon[field_150168_M.length];
		this.field_150166_b = new IIcon[field_150168_M.length];

		for (int i = 0; i < this.field_150167_a.length; ++i) {
			this.field_150167_a[i] = p_149651_1_.registerIcon("stripped_" + field_150168_M[i] + "_log");
			this.field_150166_b[i] = p_149651_1_.registerIcon("stripped_" + field_150168_M[i] + "_log" + "_top");
		}
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableStrippedLogs;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}

}
