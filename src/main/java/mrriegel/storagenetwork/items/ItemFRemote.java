package mrriegel.storagenetwork.items;

import java.util.List;

import mrriegel.storagenetwork.handler.GuiHandler;
import mrriegel.storagenetwork.helper.NBTHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemFRemote extends ItemRemote {

	public ItemFRemote() {
		super();
	}

	@Override
	protected int getGui() {
		return GuiHandler.FREMOTE;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(I18n.format("tooltip.storagenetwork.fremote_" + stack.getItemDamage()));
		if (stack.hasTagCompound() && NBTHelper.getBoolean(stack, "bound")) {
			tooltip.add("Dimension: " + NBTHelper.getInteger(stack, "dim") + ", x: " + NBTHelper.getInteger(stack, "x") + ", y: " + NBTHelper.getInteger(stack, "y") + ", z: " + NBTHelper.getInteger(stack, "z"));
		}
	}
}
