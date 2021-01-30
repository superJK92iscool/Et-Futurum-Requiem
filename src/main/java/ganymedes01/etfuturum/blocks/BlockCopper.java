package ganymedes01.etfuturum.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemBlockCopper;
import ganymedes01.etfuturum.lib.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockCopper extends BlockGeneric implements IConfigurable {

	public BlockCopper() {
		super(Material.iron, "", "lightly_weathered", "semi_weathered", "weathered", "cut", "lightly_weathered_cut", "semi_weathered_cut", "weathered_cut", "waxed", "waxed_lightly_weathered", "waxed_semi_weathered", "unused", "waxed_cut", "waxed_lightly_weathered_cut", "waxed_semi_weathered_cut", "unused");
		setHardness(3);
		setResistance(6);
		setHarvestLevel("pickaxe", 1);
		setBlockName(Utils.getUnlocalisedName("copper_block"));
		setBlockTextureName("copper_block");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		setFlippedNames(true);
		setStepSound(ConfigurationHandler.enableNewBlocksSounds ? ModSounds.soundCopper : Block.soundTypeMetal);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableCopper;
	}

    public void onBlockAdded(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta > 7 || meta % 4 == 3)
        	return;
        world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
    	
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isRemote)
            return;
        int meta = world.getBlockMetadata(x, y, z);
        if (meta > 7 || meta % 4 == 3)
        	return;
        if(world.getBlock(x, y, z) == this) {
        	world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
        }
        if(!(meta + 1 > 7 || (meta + 1) % 4 == 3))
            world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
    }
    
    public int tickRate(World world) {
    	return ConfigurationHandler.minCopperOxidationTime + world.rand.nextInt(ConfigurationHandler.maxCopperOxidationTime + 1);
    }
    
    public String getNameFor(int meta) {
    	String name = types[Math.max(Math.min(meta, types.length - 1), 0)];
        return name.equals("unused") ? types[0] : name;
    }

    @Override
    public int damageDropped(int meta) {
    	String name = types[Math.max(Math.min(meta, types.length - 1), 0)];
        return name.equals("unused") ? 0 : meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = startMeta; i < types.length; i++) {
        	if(types[i].equals("unused"))
        		continue;
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
    	int iconPos = Math.max(Math.min(meta, types.length - 1), startMeta);
        return icons[iconPos % 8] == null || types[iconPos].contains("unused") ? icons[0] : icons[iconPos % 8];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        icons = new IIcon[types.length];
        for (int i = 0; i < types.length; i++) {
        	if(i > 7)
        		break;
        	if ("unused".equals(types[i]))
        		continue;
            if ("".equals(types[i])) {
                icons[i] = reg.registerIcon(getTextureName());
            } else {
            	String name = types[i];
            	String textName = getTextureName();
            	if(name.contains("cut")) {
            		textName = textName.replace("_block", "");
            	}
                icons[i] = reg.registerIcon(name + "_" + textName);
            }
        }
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockCopper.class;
    }
}
