package ganymedes01.etfuturum.world.nether.biome;

import java.util.Random;

import ganymedes01.etfuturum.world.nether.biome.decorator.EtFuturumBiomeDecorator;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EtFuturumNetherBiomeBase extends BiomeGenBase {

	private final EtFuturumBiomeDecorator decorator;

	public EtFuturumNetherBiomeBase(int id, EtFuturumBiomeDecorator decorator) {

		super(id);
		this.decorator = decorator;
		this.setDisableRain();
		this.setTemperatureRainfall(2.0F, 0.0F);
		
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		
		this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
	}

	public void populate(World world, Random rand, int x, int z) {
		decorator.populate(world, rand, x, z);
	}

	public void populateBig(World world, Random rand, int x, int z) {
		decorator.populateBig(world, rand, x, z);
	}
	
	@Override
	public void decorate(World world, Random rand, int x, int z) {
		decorator.decorate(world, rand, x, z);
	}

}