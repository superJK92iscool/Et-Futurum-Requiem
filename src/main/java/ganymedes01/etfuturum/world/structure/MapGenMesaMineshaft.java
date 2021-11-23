package ganymedes01.etfuturum.world.structure;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.StructureMineshaftStart;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

/**
 * 
 * This still allows regular mineshafts to generate as well as Mesa ones.
 * We do this so mixins are not required for overriding normal Mineshafts
 * 
 * @author roadhog360
 *
 */
public class MapGenMesaMineshaft extends MapGenMineshaft {
	
	public boolean isMesa;

	@Override
    protected StructureStart getStructureStart(int p_75049_1_, int p_75049_2_)
    {
		if(isMesa) {
	        return new StructureMesaMineshaftStart(this.worldObj, this.rand, p_75049_1_, p_75049_2_);
		}
        return new StructureMineshaftStart(this.worldObj, this.rand, p_75049_1_, p_75049_2_);
    }

    protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_)
    {
    	if(ArrayUtils.contains(BiomeDictionary.getTypesForBiome(this.worldObj.getBiomeGenForCoords(p_75047_1_ * 16, p_75047_2_ * 16)), Type.MESA)) {
        	isMesa = true;
    	}
        return super.canSpawnStructureAtCoords(p_75047_1_, p_75047_2_);
    }
}
