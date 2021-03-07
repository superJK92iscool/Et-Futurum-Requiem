package ganymedes01.etfuturum.world.nether.biome.decorator;

import java.util.Random;

import net.minecraft.world.World;

public class EtFuturumBiomeDecorator {

	protected World world;
	protected Random rand;
	protected int x, z;
	protected int xx, yy, zz, attempt;


	protected EtFuturumBiomeDecorator() {
	}

	public final void populate(World world, Random rand, int x, int z) {
		this.world = world;
		this.rand = rand;
		this.x = x;
		this.z = z;
		populate();
	}
	
	public final void populateBig(World world, Random rand, int x, int z) {
		this.world = world;
		this.rand = rand;
		this.x = x;
		this.z = z;
		populateBig();
	}

	public final void decorate(World world, Random rand, int x, int z) {

		this.world = world;
		this.rand = rand;
		this.x = x;
		this.z = z;

		decorate();

//		isDecorating = false;
	}

	protected void populate() {
	}
	
	protected void populateBig() {
	}

	protected void decorate() {
	}

	protected final int offsetXZ() {
		return rand.nextInt(16) + 8;
	}

}