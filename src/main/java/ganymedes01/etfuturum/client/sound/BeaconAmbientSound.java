package ganymedes01.etfuturum.client.sound;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.BlockBeacon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class BeaconAmbientSound extends MovingSound {
    private final TileEntityBeacon beacon;
    private boolean isOn;
    public BeaconAmbientSound(TileEntityBeacon beacon) {
        super(new ResourceLocation(Reference.MCAssetVer+":block.beacon.ambient"));
        this.beacon = beacon;
        this.xPosF = this.beacon.xCoord;
        this.yPosF = this.beacon.yCoord;
        this.zPosF = this.beacon.zCoord;
        this.field_147666_i = AttenuationType.NONE; //field_147666_i is attenuationType
        this.repeat = false;
        this.field_147665_h = 0; //field_147665_h is repeatDelay

    }

    @Override
    public void update() {
        if (this.beacon != null && (beacon.getWorldObj().getBlock(beacon.xCoord, beacon.yCoord, beacon.zCoord) instanceof BlockBeacon)) {//Even though we already check for the level in the block, we check this to be sure there's still a beacon at the sound we're playing. If it's no longer there, the sound should stop immediately.
            EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
            float distance = MathHelper.sqrt_double((this.xPosF - player.posX) * (this.xPosF - player.posX) + (this.yPosF - player.posY) * (this.yPosF - player.posY) + (this.zPosF - player.posZ) * (this.zPosF - player.posZ));
            this.volume = MathHelper.clamp_float((1F - (distance / 7F)) * 0.9F, 0F, 1F);
            //Because sounds can't have a custom attenuation distance in 1.7.10?
        } else {
            this.donePlaying = true;
        }
    }

}
