package ganymedes01.etfuturum.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import ganymedes01.etfuturum.client.model.ModelNewBoat;
import ganymedes01.etfuturum.entities.EntityNewBoat;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class NewBoatRenderer extends RenderBoat {
	
	public NewBoatRenderer() {
		super();
        this.shadowSize = 0.5F;
	}
	
    protected ModelNewBoat modelBoat = new ModelNewBoat();

    public void doRender(EntityBoat p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	
    }
	@Override
	public void doRender(Entity uncastedentity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		if(!(uncastedentity instanceof EntityNewBoat)) return;
		EntityNewBoat entity = (EntityNewBoat)uncastedentity;
        GL11.glPushMatrix();
        this.setupTranslation(x, y, z);
        this.setupRotation(entity, entityYaw, partialTicks);
        this.bindEntityTexture(entity);

//        if (this.renderOutlines)
//        {
//            GL11.enableColorMaterial();
//            GL11.enableOutlineMode(this.getTeamColor(entity));
//        }

        this.modelBoat.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

//        if (this.renderOutlines)
//        {
//            GL11.disableOutlineMode();
//            GL11.disableColorMaterial();
//        }

        GL11.glPopMatrix();
        this.renderMultipass(entity, x, y, z, entityYaw, partialTicks);
	}

    public void setupRotation(EntityNewBoat p_188311_1_, float p_188311_2_, float p_188311_3_)
    {
        GL11.glRotatef(180.0F - p_188311_2_, 0.0F, 1.0F, 0.0F);
        float f = (float)p_188311_1_.getTimeSinceHit() - p_188311_3_;
        float f1 = p_188311_1_.getDamageTaken() - p_188311_3_;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f > 0.0F)
        {
            GL11.glRotatef(MathHelper.sin(f) * f * f1 / 10.0F * (float)p_188311_1_.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }

        GL11.glScalef(-1.0F, -1.0F, 1.0F);
    }

    public void setupTranslation(double p_188309_1_, double p_188309_3_, double p_188309_5_)
    {
        GL11.glTranslatef((float)p_188309_1_, (float)p_188309_3_ + 0.375F, (float)p_188309_5_);
    }

    public void renderMultipass(EntityNewBoat p_188300_1_, double p_188300_2_, double p_188300_4_, double p_188300_6_, float p_188300_8_, float p_188300_9_)
    {
        GL11.glPushMatrix();
        this.setupTranslation(p_188300_2_, p_188300_4_, p_188300_6_);
        this.setupRotation(p_188300_1_, p_188300_8_, p_188300_9_);
        this.bindEntityTexture(p_188300_1_);
        modelBoat.renderMultipass(p_188300_1_, p_188300_9_, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
