package ganymedes01.etfuturum.client.sound;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class BeaconAmbientSound extends PositionedSound implements ITickableSound {
	private final TileEntityBeacon beacon;

	public BeaconAmbientSound(TileEntityBeacon beacon) {
		super(new ResourceLocation(Reference.MCAssetVer + ":block.beacon.ambient"));
		this.beacon = beacon;
		this.xPosF = this.beacon.xCoord + 0.5F;
		this.yPosF = this.beacon.yCoord + 0.5F;
		this.zPosF = this.beacon.zCoord + 0.5F;
		this.field_147666_i = AttenuationType.NONE; //field_147666_i is attenuationType
	}

	@Override
	public void update() {
		//Somehow this class throws an NPE randomly so I there's a few NPE checks to be safe
		if (!isDonePlaying()) {
			EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
			float distance = MathHelper.sqrt_double((this.xPosF - player.posX) * (this.xPosF - player.posX) + (this.yPosF - player.posY) * (this.yPosF - player.posY) + (this.zPosF - player.posZ) * (this.zPosF - player.posZ));
			this.volume = MathHelper.clamp_float((1F - (distance / 7F)) * 0.9F, 0F, 1F);
			//Because sounds can't have a custom attenuation distance in 1.7.10?
		}
	}

	@Override
	public boolean isDonePlaying() {
		return this.beacon == null || this.beacon.isInvalid() || Minecraft.getMinecraft().thePlayer == null;
	}
}
