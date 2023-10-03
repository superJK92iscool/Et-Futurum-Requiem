package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockWoodSign extends BlockSign {

	protected static BlockWoodSign prevSign;
	private BlockWoodSign wallSign;
	private final BlockWoodSign standingSign;

	private final Block baseBlock;
	private final int meta;
	public final String type;

	public final boolean standing;

	public BlockWoodSign(Class<?> p_i45426_1_, boolean p_i45426_2_, String type, Block block, int meta) {
		super(p_i45426_1_, p_i45426_2_);
		this.meta = meta;
		baseBlock = block;
		this.type = type;
		standing = p_i45426_2_;
		if (standing) {
			prevSign = this;
			standingSign = this;
		} else {
			standingSign = prevSign;
			wallSign = this;
			prevSign.wallSign = this;
		}
		setHardness(1.0F);
		disableStats();
		setBlockName(Utils.getUnlocalisedName(type + "_sign"));
		if (type.equals("crimson") || type.equals("warped")) {
			Utils.setBlockSound(this, ModSounds.soundNetherWood);
		} else if (type.equals("cherry")) {
			Utils.setBlockSound(this, ModSounds.soundCherryWood);
		} else if (type.equals("bamboo")) {
			Utils.setBlockSound(this, ModSounds.soundBambooWood);
		} else {
			setStepSound(Block.soundTypeWood);
		}
		if (block != Blocks.planks && standing) { //Only apply this logic to new signs; old ones use a separate item.
			setCreativeTab(EtFuturum.creativeTabBlocks);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return baseBlock.getIcon(side, this.meta);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		if (baseBlock == Blocks.planks) {
			return ModItems.OLD_SIGN_ITEMS[meta - 1].get();
		}
		//Only apply this logic to new signs; old ones use a separate item.
		return Item.getItemFromBlock(standingSign);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
		return getItemDropped(0, null, 0);
	}

	public BlockWoodSign getWallSign() {
		return wallSign;
	}

	@Override
	public String getItemIconName() {
		return type + "_sign";
	}
}
