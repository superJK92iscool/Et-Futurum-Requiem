package ganymedes01.etfuturum.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Random;

@SideOnly(Side.CLIENT)
public enum ParticleHandler {

	INVISIBLE_BLOCK_FX(InnerBlockParticleFX.class, ParticleData.VX_VY_VZ, int.class, float.class, int.class, int.class) {
		@Override
		protected Object[] getAdditionalArgs(World world, Object... data) {
			return new Object[]{80, 4.5F, 0xFFFFFFFF, 1};
		}
	},

	WAX_ON(CopperGlowFX.class, ParticleData.VX_VY_VZ, int.class, float.class, int.class, ResourceLocation.class,
			int.class) {
		private final ResourceLocation texture = new ResourceLocation(
				"textures/particle/glow.png");

		@Override
		protected Object[] getAdditionalArgs(World world, Object... data) {
			return new Object[]{30 + rand.nextInt(26), 1, 0xFFFFA32C, texture, 1};
		}
	},

	WAX_OFF(CopperGlowFX.class, ParticleData.VX_VY_VZ, int.class, float.class, int.class, ResourceLocation.class,
			int.class) {
		private final ResourceLocation texture = new ResourceLocation(
				"textures/particle/glow.png");

		@Override
		protected Object[] getAdditionalArgs(World world, Object... data) {
			return new Object[]{30 + rand.nextInt(26), 1, 0xFFFFFFFF, texture, 1};
		}
	},

	COPPER_SCRAPE(CopperGlowFX.class, ParticleData.VX_VY_VZ, int.class, float.class, int.class, ResourceLocation.class,
			int.class) {
		private final ResourceLocation texture = new ResourceLocation(
				"textures/particle/glow.png");

		@Override
		protected Object[] getAdditionalArgs(World world, Object... data) {
			return new Object[]{30 + rand.nextInt(26), 1, 0xFF78DAC1, texture, 1};
		}
	},

	DAMAGE_HEART(BlackHeartFX.class, ParticleData.VX_VY_VZ, int.class, float.class, int.class, ResourceLocation.class,
			int.class) {
		private final ResourceLocation texture = new ResourceLocation(
				"textures/particle/damage.png");

		@Override
		protected Object[] getAdditionalArgs(World world, Object... data) {
			return new Object[]{15, 1 + (rand.nextFloat() * 0.125F), 0xFFFFFFFF, texture, 1};
		}
	},

	END_ROD(EndRodFX.class, ParticleData.VX_VY_VZ, int.class, float.class, int.class, ResourceLocation.class,
			int.class) {
		private final ResourceLocation texture = new ResourceLocation(
				"textures/particle/glitter.png");

		@Override
		protected Object[] getAdditionalArgs(World world, Object... data) {
			return new Object[]{60 + rand.nextInt(12), 1, 0xFFFFFFFF, texture, 8};
		}
	},

	FALLING_DUST(FallingDustFX.class, ParticleData.VX_VY_VZ, int.class, float.class, int.class, ResourceLocation.class) {
		private final ResourceLocation texture = new ResourceLocation(
				"textures/particle/particles.png");

		@Override
		protected Object[] getAdditionalArgs(World world, Object... data) {
			return new Object[]{60 + rand.nextInt(12), 1F, data[0], texture};
		}
	},

	DRIP(CustomDripFX.class, ParticleData.VX_VY_VZ, String.class, int.class) {
		@Override
		protected Object[] getAdditionalArgs(World world, Object... data) {
			return new Object[]{data[0], data[1]};
		}
	},

	BEE_NECTAR(BeeNectarFX.class, ParticleData.NONE);

	protected static Random rand = new Random();

	private static final int REGULAR_ARG_NUM = 4;

	private final Constructor<? extends EntityFX> constructor;

	private final ParticleData args;

	private final Class<?>[] additionalArgTypes;

	private boolean shouldAssignColor = false;

	private float r;

	private float g;

	private float b;

	ParticleHandler(Class<? extends EntityFX> fxClass) {
		this(fxClass, -1, 0xDEAD, 0xC0DE);
	}

	ParticleHandler(Class<? extends EntityFX> fxClass, float r, float g, float b) {
		this(fxClass, r, g, b, ParticleData.VX_VY_VZ);
	}

	ParticleHandler(Class<? extends EntityFX> fxClass, ParticleData args, Class<?>... additionalArgTypes) {
		this(fxClass, -1, 0xDEAD, 0xC0DE, args, additionalArgTypes);
	}

	ParticleHandler(Class<? extends EntityFX> fxClass, float r, float g, float b, ParticleData args,
					Class<?>... additionalArgTypes) {
		if (r != -1) {
			shouldAssignColor = true;
			this.r = r;
			this.g = g;
			this.b = b;
		}
		try {
			constructor = fxClass.getConstructor(getArgumentTypes(args, additionalArgTypes));
		} catch (Exception e) {
			CrashReport crash = CrashReport.makeCrashReport(e, "Constructing EtFuturumParticle");
			CrashReportCategory categoryArguments = crash.makeCategory("Arguments");
			categoryArguments.addCrashSection("Class", fxClass);
			categoryArguments.addCrashSection("Particle Arg Types", args);
			categoryArguments.addCrashSection("Additional Arg Types", Arrays.toString(additionalArgTypes));
			throw new ReportedException(crash);
		}
		this.args = args;
		this.additionalArgTypes = additionalArgTypes;
	}

	private static Class<?>[] getArgumentTypes(ParticleData args, Class<?>[] additionalArgTypes) {
		Class<?>[] argumentTypes = new Class<?>[REGULAR_ARG_NUM + args.getArgumentCount() + additionalArgTypes.length];
		argumentTypes[0] = World.class;
		argumentTypes[1] = double.class;
		argumentTypes[2] = double.class;
		argumentTypes[3] = double.class;
		System.arraycopy(args.getArgumentTypes(), 0, argumentTypes, REGULAR_ARG_NUM, args.getArgumentCount());
		System.arraycopy(additionalArgTypes, 0, argumentTypes, REGULAR_ARG_NUM + args.getArgumentCount(),
				additionalArgTypes.length);
		return argumentTypes;
	}

	protected Object[] getAdditionalArgs(World world, Object... data) {
		return data;
	}

	protected void onSpawn(EntityFX entityFX) {
	}

	public final EntityFX spawn(World world, double x, double y, double z) {
		return spawn(world, x, y, z, 0, 0, 0, 1);
	}

	public final EntityFX spawn(World world, double x, double y, double z, double motionX, double motionY, double motionZ,
								float scale, Object... data) {
		Object[] arguments = getArguments(world, x, y, z, motionX, motionY, motionZ, scale, data);
		try {
			EntityFX entityFX = constructor.newInstance(arguments);
			if (shouldAssignColor) {
				entityFX.setRBGColorF(r, g, b);
			}
			onSpawn(entityFX);
			Minecraft.getMinecraft().effectRenderer.addEffect(entityFX);
			return entityFX;
		} catch (Exception e) {
			CrashReport crash = CrashReport.makeCrashReport(e, "Constructing EntityFX");
			CrashReportCategory categorySpawnArguments = crash.makeCategory("Spawn Arguments");
			categorySpawnArguments.addCrashSection("World", world);
			categorySpawnArguments.addCrashSection("X", x);
			categorySpawnArguments.addCrashSection("Y", y);
			categorySpawnArguments.addCrashSection("Z", z);
			categorySpawnArguments.addCrashSection("Motion X", motionX);
			categorySpawnArguments.addCrashSection("Motion Y", motionY);
			categorySpawnArguments.addCrashSection("Motion Z", motionZ);
			categorySpawnArguments.addCrashSection("Scale", scale);
			categorySpawnArguments.addCrashSection("Data", Arrays.deepToString(data));
			CrashReportCategory categoryEtFuturumParticle = crash.makeCategory("EtFuturumParticle");
			categoryEtFuturumParticle.addCrashSection("Constructor", constructor);
			categoryEtFuturumParticle.addCrashSection("Particle Arg Types", args);
			categoryEtFuturumParticle.addCrashSection("Additional Arg Types", Arrays.toString(additionalArgTypes));
			CrashReportCategory categoryArguments = crash.makeCategory("Arguments");
			categoryArguments.addCrashSection("Arguments", Arrays.deepToString(arguments));
			throw new ReportedException(crash);
		}
	}

	private Object[] getArguments(World world, double x, double y, double z, double motionX, double motionY,
								  double motionZ, float scale, Object... data) {
		Object[] particleArgs = args.getArguments(motionX, motionY, motionZ, scale);
		Object[] additionalArgs = getAdditionalArgs(world, data);
		Object[] arguments = new Object[REGULAR_ARG_NUM + args.getArgumentCount() + additionalArgs.length];
		arguments[0] = world;
		arguments[1] = x;
		arguments[2] = y;
		arguments[3] = z;
		System.arraycopy(particleArgs, 0, arguments, REGULAR_ARG_NUM, args.getArgumentCount());
		System.arraycopy(additionalArgs, 0, arguments, REGULAR_ARG_NUM + args.getArgumentCount(),
				additionalArgs.length);
		return arguments;
	}

}
