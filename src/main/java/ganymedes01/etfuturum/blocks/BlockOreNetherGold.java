package ganymedes01.etfuturum.blocks;

import java.util.Random;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class BlockOreNetherGold extends Block implements IConfigurable {

	public BlockOreNetherGold() {
		super(Material.rock);
		setStepSound(ConfigurationHandler.enableNewBlocksSounds ? ModSounds.soundNetherOre : Block.soundTypeStone);
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		setHardness(3.0F);
		setResistance(5.0F);
		setBlockTextureName("nether_gold_ore");
		setBlockName(Utils.getUnlocalisedName("nether_gold_ore"));
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableNetherGold;
	}
	
    private Random rand = new Random();
    @Override
    public int getExpDrop(IBlockAccess p_149690_1_, int p_149690_5_, int p_149690_7_)
    {
        return MathHelper.getRandomIntegerInRange(rand, 2, 5);
    }
    
    public int quantityDropped(Random p_149745_1_)
    {
        return 2 + p_149745_1_.nextInt(5);
    }
    
    public int quantityDroppedWithBonus(int fortune, Random p_149679_2_)
    {
        if (fortune > 0)
        {
            int j = p_149679_2_.nextInt(fortune + 2) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return this.quantityDropped(p_149679_2_) * (j + 1);
        }
        return this.quantityDropped(p_149679_2_);
    }

    protected boolean canSilkHarvest()
    {
        return true;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Items.gold_nugget;
    }
    
}
