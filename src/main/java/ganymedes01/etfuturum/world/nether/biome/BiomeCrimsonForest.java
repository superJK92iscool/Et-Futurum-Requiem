package ganymedes01.etfuturum.world.nether.biome;

import ganymedes01.etfuturum.world.nether.biome.decorator.CrimsonForestDecorator;
import ganymedes01.etfuturum.world.nether.biome.utils.IBiomeColor;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeCrimsonForest extends NetherBiomeBase implements IBiomeColor {

	private int FogSkyColor = 100000000; // Come back to

	public BiomeCrimsonForest(int id) {

		super(id, new CrimsonForestDecorator());
		this.setColor(0xFA9418);
		this.topBlock = Blocks.grass;
		this.fillerBlock = Blocks.netherrack;
		
		BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.NETHER);
	}

	@Override
	public int getBiomeColour(int i, int playerY, int j) {
		// TODO Auto-generated method stub
		return this.FogSkyColor;
	}
	
	@Override
	public int getSkyColorByTemp(float par1)
	{
		return this.FogSkyColor;
		
	}

}