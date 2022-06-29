package ganymedes01.etfuturum.client.renderer.entity;

import ganymedes01.etfuturum.entities.EntityNewBoat;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityChest;
import org.lwjgl.opengl.GL11;

public class ChestBoatRenderer extends NewBoatRenderer {
    private final TileEntityChest chest = new TileEntityChest();
    @Override
    protected void renderExtraBoatContents(EntityNewBoat boat, float partialTicks) {
        GL11.glRotatef(180f, 0f, 1f, 0f);
        GL11.glScalef(0.8f, 0.8f, 0.8f);
        GL11.glTranslatef(-0.5f, -0.2f, -1.1f);
        TileEntityRendererDispatcher.instance.renderTileEntityAt(chest, 0, 0, 0, partialTicks);
    }
}
