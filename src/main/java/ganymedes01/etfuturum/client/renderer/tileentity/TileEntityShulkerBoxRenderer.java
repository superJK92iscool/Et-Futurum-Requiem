package ganymedes01.etfuturum.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;

import ganymedes01.etfuturum.client.model.ModelShulker;
import ganymedes01.etfuturum.tileentities.TileEntityShulkerBox;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityShulkerBoxRenderer extends TileEntitySpecialRenderer {

    public static final ResourceLocation[] TEMP = new ResourceLocation[] {new ResourceLocation("textures/entity/shulker/shulker.png"),
    		new ResourceLocation("textures/entity/shulker/shulker_white.png"), new ResourceLocation("textures/entity/shulker/shulker_orange.png"),
    		new ResourceLocation("textures/entity/shulker/shulker_magenta.png"), new ResourceLocation("textures/entity/shulker/shulker_light_blue.png"),
    		new ResourceLocation("textures/entity/shulker/shulker_yellow.png"), new ResourceLocation("textures/entity/shulker/shulker_lime.png"),
    		new ResourceLocation("textures/entity/shulker/shulker_pink.png"), new ResourceLocation("textures/entity/shulker/shulker_gray.png"),
    		new ResourceLocation("textures/entity/shulker/shulker_silver.png"), new ResourceLocation("textures/entity/shulker/shulker_cyan.png"),
    		new ResourceLocation("textures/entity/shulker/shulker_purple.png"), new ResourceLocation("textures/entity/shulker/shulker_blue.png"),
    		new ResourceLocation("textures/entity/shulker/shulker_brown.png"), new ResourceLocation("textures/entity/shulker/shulker_green.png"),
    		new ResourceLocation("textures/entity/shulker/shulker_red.png"), new ResourceLocation("textures/entity/shulker/shulker_black.png")};

    protected static final ResourceLocation[] DESTROY_STAGES = new ResourceLocation[] {new ResourceLocation("textures/blocks/destroy_stage_0.png"), new ResourceLocation("textures/blocks/destroy_stage_1.png"), new ResourceLocation("textures/blocks/destroy_stage_2.png"), new ResourceLocation("textures/blocks/destroy_stage_3.png"), new ResourceLocation("textures/blocks/destroy_stage_4.png"), new ResourceLocation("textures/blocks/destroy_stage_5.png"), new ResourceLocation("textures/blocks/destroy_stage_6.png"), new ResourceLocation("textures/blocks/destroy_stage_7.png"), new ResourceLocation("textures/blocks/destroy_stage_8.png"), new ResourceLocation("textures/blocks/destroy_stage_9.png")};
    private final ModelShulker field_191285_a;

    public TileEntityShulkerBoxRenderer(ModelShulker p_i47216_1_)
    {
        this.field_191285_a = p_i47216_1_;
    }

    public void renderTileEntityAt(TileEntityShulkerBox te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        ForgeDirection enumfacing = ForgeDirection.UP;

        if (te.hasWorldObj())
        {
            int meta = te.getBlockMetadata();
            enumfacing = ForgeDirection.values()[meta];
        }

//        GlStateManager.enableDepth();
//        GlStateManager.depthFunc(515);
//        GlStateManager.depthMask(true);
//        GlStateManager.disableCull();
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glScalef(4.0F, 4.0F, 1.0F);
            GL11.glTranslatef(0.0625F, 0.0625F, 0.0625F);
            GL11.glMatrixMode(5888);
        }
        else
        {
            this.bindTexture(TEMP[te.color % TEMP.length]);
        }

        GL11.glPushMatrix();
//        GlStateManager.enableRescaleNormal();

        if (destroyStage < 0)
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        float f = 0.9995F;
        GL11.glScalef(0.9995F, 0.9995F, 0.9995F);
        GL11.glTranslatef(0.0F, -1.0F, 0.0F);

        switch (enumfacing)
        {
            case DOWN:
                GL11.glTranslatef(0.0F, 2.0F, 0.0F);
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);

            case UP:
            default:
                break;

            case NORTH:
                GL11.glTranslatef(0.0F, 1.0F, 1.0F);
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                break;

            case SOUTH:
                GL11.glTranslatef(0.0F, 1.0F, -1.0F);
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                break;

            case WEST:
                GL11.glTranslatef(-1.0F, 1.0F, 0.0F);
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
                break;

            case EAST:
                GL11.glTranslatef(1.0F, 1.0F, 0.0F);
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        }

        this.field_191285_a.base.render(0.0625F);
        GL11.glTranslatef(0.0F, -te.func_190585_a(partialTicks) * 0.5F, 0.0F);
        GL11.glRotatef(270.0F * te.func_190585_a(partialTicks), 0.0F, 1.0F, 0.0F);
        this.field_191285_a.lid.render(0.0625F);
//        GlStateManager.enableCull();
        GL11.glEnable(GL11.GL_CULL_FACE);
//        GlStateManager.disableRescaleNormal();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (destroyStage >= 0)
        {
            GL11.glMatrixMode(5890);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
    }

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,	float partialTicks) {
		this.renderTileEntityAt((TileEntityShulkerBox)te, x, y, z, partialTicks, -1);
	}

}
