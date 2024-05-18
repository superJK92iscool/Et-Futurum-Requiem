package ganymedes01.etfuturum.mixins.glasspane;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.minecraftforge.common.util.ForgeDirection.*;
import static net.minecraftforge.common.util.ForgeDirection.EAST;

@Mixin(BlockPane.class)
public abstract class MixinGlassPane extends Block {

	protected MixinGlassPane(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Inject(method = "addCollisionBoxesToList", at = @At("HEAD"), cancellable = true)
	private void remapCollisionsBoxes(World worldIn, int posX, int posY, int posZ, AxisAlignedBB bb, List boxList, Entity entity, CallbackInfo ci) {
		boolean flag  = this.canPaneConnectTo(worldIn, posX, posY, posZ - 1, NORTH);
		boolean flag1 = this.canPaneConnectTo(worldIn, posX, posY, posZ + 1, SOUTH);
		boolean flag2 = this.canPaneConnectTo(worldIn, posX - 1, posY, posZ, WEST );
		boolean flag3 = this.canPaneConnectTo(worldIn, posX + 1, posY, posZ, EAST );

		if(!flag && !flag1 && !flag2 && !flag3){
			this.setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
			super.addCollisionBoxesToList(worldIn, posX, posY, posZ, bb, boxList, entity);
			ci.cancel();
			return;
		}

		if (!flag2 || !flag3)
		{
			if (flag2)
			{
				this.setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
				super.addCollisionBoxesToList(worldIn, posX, posY, posZ, bb, boxList, entity);
			}
			else if (flag3)
			{
				this.setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
				super.addCollisionBoxesToList(worldIn, posX, posY, posZ, bb, boxList, entity);
			}
		}
		else
		{
			this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
			super.addCollisionBoxesToList(worldIn, posX, posY, posZ, bb, boxList, entity);
		}

		if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1))
		{
			if (flag)
			{
				this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
				super.addCollisionBoxesToList(worldIn, posX, posY, posZ, bb, boxList, entity);
			}
			else if (flag1)
			{
				this.setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(worldIn, posX, posY, posZ, bb, boxList, entity);
			}
		}
		else
		{
			this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(worldIn, posX, posY, posZ, bb, boxList, entity);
		}

		ci.cancel();
	}

	@Inject(method = "setBlockBoundsBasedOnState", at = @At("HEAD"), cancellable = true)
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int posX, int posY, int posZ, CallbackInfo ci)
	{
		float f = 0.4375F;
		float f1 = 0.5625F;
		float f2 = 0.4375F;
		float f3 = 0.5625F;
		boolean flag  = this.canPaneConnectToBlock(worldIn.getBlock(posX, posY, posZ - 1));
		boolean flag1 = this.canPaneConnectToBlock(worldIn.getBlock(posX, posY, posZ + 1));
		boolean flag2 = this.canPaneConnectToBlock(worldIn.getBlock(posX - 1, posY, posZ));
		boolean flag3 = this.canPaneConnectToBlock(worldIn.getBlock(posX + 1, posY, posZ ));

		if(!flag2 && !flag3 && !flag && !flag1){
			this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
			ci.cancel();
			return;
		}

		if (!flag2 || !flag3)
		{
			if (flag2)
			{
				f = 0.0F;
			}
			else if (flag3)
			{
				f1 = 1.0F;
			}
		}
		else
		{
			f = 0.0F;
			f1 = 1.0F;
		}

		if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1))
		{
			if (flag)
			{
				f2 = 0.0F;
			}
			else if (flag1)
			{
				f3 = 1.0F;
			}
		}
		else
		{
			f2 = 0.0F;
			f3 = 1.0F;
		}

		this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
		ci.cancel();
	}

	@Shadow
	public boolean canPaneConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir){
		return true;
	}

	@Shadow
	public final boolean canPaneConnectToBlock(Block blockIN)
	{
		return blockIN.func_149730_j() || blockIN == this || blockIN == Blocks.glass || blockIN == Blocks.stained_glass || blockIN == Blocks.stained_glass_pane || blockIN instanceof BlockPane;
	}

}
