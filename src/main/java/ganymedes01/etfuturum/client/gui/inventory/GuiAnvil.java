package ganymedes01.etfuturum.client.gui.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.inventory.ContainerAnvil;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class GuiAnvil extends GuiRepair {

	public GuiAnvil(EntityPlayer player, World world, int x, int y, int z) {
		super(player.inventory, world, x, y, z);

		ContainerAnvil container = new ContainerAnvil(player, world, x, y, z);
		field_147092_v = container;
		inventorySlots = container;
	}
}