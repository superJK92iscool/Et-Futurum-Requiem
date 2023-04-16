package ganymedes01.etfuturum.mixins.sounds;

import org.spongepowered.asm.mixin.Mixin;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityWitch.class)
public class MixinEntityWitch extends EntityMob {

	public MixinEntityWitch(World p_i1738_1_) {
		super(p_i1738_1_);
	}

	@Inject(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemPotion;getEffects(Lnet/minecraft/item/ItemStack;)Ljava/util/List;"))
	public void playDrinkSound(CallbackInfo ci)
	{
		this.playSound(Reference.MCAssetVer+":entity.witch.drink", getSoundVolume(), getSoundPitch());
	}

	@Overwrite
	protected String getHurtSound()
	{
		return Reference.MCAssetVer + ":entity.witch.hurt";
	}

	@Overwrite
	protected String getDeathSound()
	{
		return Reference.MCAssetVer + ":entity.witch.death";
	}

	@Overwrite
	protected String getLivingSound()
	{
		return Reference.MCAssetVer + ":entity.witch.ambient";
	}

}
