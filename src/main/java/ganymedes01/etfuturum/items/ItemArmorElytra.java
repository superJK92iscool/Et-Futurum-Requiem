package ganymedes01.etfuturum.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.client.model.ModelElytra;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.world.generate.feature.WorldGenFossil;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemArmorElytra extends ItemArmor implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon broken;

	public ItemArmorElytra() {
		super(EnumHelper.addArmorMaterial("elytra", 27, new int[] { 0, 0, 0, 0 }, 0), 0, 1);
		setMaxDamage(432);
		setMaxStackSize(1);
		setTextureName("elytra");
		setUnlocalizedName(Utils.getUnlocalisedName("elytra"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack material) {
		return ArmorMaterial.CLOTH.func_151685_b() == material.getItem();
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "textures/entity/elytra.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return new ModelElytra();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return meta >= getMaxDamage() ? broken : super.getIconFromDamage(meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		broken = reg.registerIcon("broken_elytra");
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableElytra;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		new WorldGenFossil().generate(world, world.rand, x, y, z);
		return false;
	}
}