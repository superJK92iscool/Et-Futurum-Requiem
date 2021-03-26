package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.IConfigurable;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockDeepslateSlab extends BlockGenericSlab implements IConfigurable {

	public BlockDeepslateSlab(boolean isDouble) {
		super(isDouble, Material.rock, "cobbled", "polished");
		// TODO Auto-generated constructor stub
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
