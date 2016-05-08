package mrriegel.storagenetwork;

import mrriegel.storagenetwork.gui.request.ContainerRequest;
import mrriegel.storagenetwork.init.ModBlocks;
import mrriegel.storagenetwork.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = StorageNetwork.MODID, name = StorageNetwork.MODNAME, version = StorageNetwork.VERSION, guiFactory = "mrriegel.storagenetwork.config.GuiFactory")
public class StorageNetwork {
	public static final String MODID = "storagenetwork";
	public static final String VERSION = "1.9.2";
	public static final String MODNAME = "Storage Network";

	@Instance(StorageNetwork.MODID)
	public static StorageNetwork instance;

	@SidedProxy(clientSide = "mrriegel.storagenetwork.proxy.ClientProxy", serverSide = "mrriegel.storagenetwork.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs tab1 = new CreativeTabs(StorageNetwork.MODID) {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ModBlocks.kabel);
		}

		@Override
		public String getTranslatedTabLabel() {
			return StorageNetwork.MODNAME;
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		NBTTagCompound tagCompound = new NBTTagCompound();
		tagCompound.setString("ContainerClass", ContainerRequest.class.getName());
		tagCompound.setBoolean("PhantomItems", false);
		tagCompound.setString("AlignToGrid", "left");
		FMLInterModComms.sendMessage("craftingtweaks", "RegisterProvider", tagCompound);
		MinecraftForge.EVENT_BUS.register(this);

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		// ItemStack s = new ItemStack(ModItems.ssdd);
		// System.out.println("one: " + s.getTagCompound());
		// IInventory i = ItemSSDD.getInventory(s);
		// System.out.println("zip: " + i.getSizeInventory());
		// i.setInventorySlotContents(3, new ItemStack(Blocks.furnace));
		// ItemSSDD.setInventory(i, s);
		// System.out.println("two: " + s.getTagCompound());
	}

	@SubscribeEvent
	public void task(PlayerInteractEvent e) {
		if (!e.world.isRemote && e.action == Action.RIGHT_CLICK_BLOCK && e.world.getBlockState(e.pos).getBlock() == ModBlocks.itemBox) {
			// System.out.println(e.world.getTileEntity(e.pos).serializeNBT());
		}
		// System.out.println(e.entityPlayer.getUniqueID());
	}

}
