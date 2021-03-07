package ganymedes01.etfuturum.blocks;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.ai.BlockPos;
import ganymedes01.etfuturum.items.block.ItemBlockCopper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCopper extends BlockGeneric implements IConfigurable {

	public BlockCopper() {
		super(Material.iron, "", "exposed", "weathered", "oxidized", "cut", "exposed_cut", "weathered_cut", "oxidized_cut", "waxed", "waxed_exposed", "waxed_weathered", "unused", "waxed_cut", "waxed_exposed_cut", "waxed_weathered_cut", "unused");
		setHardness(3);
		setResistance(6);
		setHarvestLevel("pickaxe", 1);
		setBlockName(Utils.getUnlocalisedName("copper_block"));
		setBlockTextureName("copper_block");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		setFlippedNames(true);
		setStepSound(ConfigurationHandler.enableNewBlocksSounds ? ModSounds.soundCopper : Block.soundTypeMetal);
		setTickRandomly(true);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableCopper;
	}
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isRemote)
            return;
        int meta = world.getBlockMetadata(x, y, z);
        if (meta > 7 || meta % 4 == 3)
        	return;
        tickDegradation(world, x, y, z, rand);
    }

    private void tickDegradation(World world, int x, int y, int z, Random random) {
       float f = 0.05688889F;
       if (random.nextFloat() < 0.05688889F) {
          this.tryDegrade(world, x, y, z, random);
       }
    }

    private void tryDegrade(World world, int x, int y, int z, Random random) {
       int i = world.getBlockMetadata(x, y, z) % 4;
       int j = 0;
       int k = 0;
       
       for(int x1 = -4; x1 <= 4; x1++) {
           for(int y1 = -4; y1 <= 4; y1++) {
               for(int z1 = -4; z1 <= 4; z1++) {
                   if(world.getBlock(x1 + x, y1 + y, z1 + z) instanceof BlockCopper && (x1 != 0 || y1 != 0 || z1 != 0) && Math.abs(x1) + Math.abs(y1) + Math.abs(z1) <= 4) {
                       int m = world.getBlockMetadata(x1 + x, y1 + y, z1 + z);
                       
                       if(m > 7)
                    	   continue;
                       else
                    	   m %= 4;
                       
                       if (m < i) {
                          return;
                       }
              
                       if (m > i) {
                          ++k;
                       } else {
                          ++j;
                       }
                   }
               }
           }
       }

       float f = (float)(k + 1) / (float)(k + j + 1);
       float g = f * f * (i == 0 ? 0.75F : 1F);
       if (random.nextFloat() < g) {
          world.setBlockMetadataWithNotify(x, y, z, (world.getBlockMetadata(x, y, z) % 8) + 1, 2);
       }
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
            	if(i > 0) {
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
