package ganymedes01.etfuturum.client.renderer.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public class ItemLodestoneCompassRenderer implements IItemRenderer {

	private final RenderItem renderItem = new RenderItem();
	private int frameTimer;

	@Override
	public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
		return ItemRenderType.ENTITY == type;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
//		renderItem.doRender((EntityItem) data[1], 0, 0, 0, 1, 1);
	}
}