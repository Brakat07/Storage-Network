package mrriegel.storagenetwork.render;

import mrriegel.storagenetwork.StorageNetwork;
import mrriegel.storagenetwork.blocks.BlockKabel;
import mrriegel.storagenetwork.tile.TileKabel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class CableRenderer extends TileEntitySpecialRenderer<TileKabel> {

	ModelCable model;
	private final ResourceLocation link = new ResourceLocation(StorageNetwork.MODID + ":textures/tile/link.png");
	private final ResourceLocation ex = new ResourceLocation(StorageNetwork.MODID + ":textures/tile/ex.png");
	private final ResourceLocation im = new ResourceLocation(StorageNetwork.MODID + ":textures/tile/im.png");
	private final ResourceLocation storage = new ResourceLocation(StorageNetwork.MODID + ":textures/tile/storage.png");
	private final ResourceLocation vacuum = new ResourceLocation(StorageNetwork.MODID + ":textures/tile/vacuum.png");
	private final ResourceLocation fex = new ResourceLocation(StorageNetwork.MODID + ":textures/tile/fex.png");
	private final ResourceLocation fim = new ResourceLocation(StorageNetwork.MODID + ":textures/tile/fim.png");
	private final ResourceLocation fstorage = new ResourceLocation(StorageNetwork.MODID + ":textures/tile/fstorage.png");

	public CableRenderer() {
		model = new ModelCable();
	}

	@Override
	public void renderTileEntityAt(TileKabel te, double x, double y, double z, float partialTicks, int destroyStage) {
		// if(true)return;
		boolean show = Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null && Block.getBlockFromItem(Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem()) instanceof BlockKabel;
		if (te == null || te.getKind() == null || !(te.getWorld().getBlockState(te.getPos()).getBlock() instanceof BlockKabel))
			return;
		if (te.getCover() != null && !show) {
			if (te.getCover() == Blocks.GLASS)
				return;
			this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
			World world = te.getWorld();
			BlockPos blockpos = te.getPos();
			IBlockState iblockstate = te.getCover().getStateFromMeta(te.getCoverMeta());

			GlStateManager.pushMatrix();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.translate((float) x, (float) y, (float) z);

			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer worldrenderer = tessellator.getBuffer();
			worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

			int i = blockpos.getX();
			int j = blockpos.getY();
			int k = blockpos.getZ();

			worldrenderer.setTranslation(((-i)), (-j), ((-k)));
			worldrenderer.color(1F, 1F, 1F, 1F);
			IBakedModel ibakedmodel = blockrendererdispatcher.getModelForState(iblockstate);
			blockrendererdispatcher.getBlockModelRenderer().renderModel(world, ibakedmodel, iblockstate, blockpos, worldrenderer, true);

			worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
			tessellator.draw();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.popMatrix();
			return;
		}

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		switch (te.getKind()) {
		case kabel:
			Minecraft.getMinecraft().renderEngine.bindTexture(link);
			break;
		case exKabel:
			Minecraft.getMinecraft().renderEngine.bindTexture(ex);
			break;
		case imKabel:
			Minecraft.getMinecraft().renderEngine.bindTexture(im);
			break;
		case storageKabel:
			Minecraft.getMinecraft().renderEngine.bindTexture(storage);
			break;
		case vacuumKabel:
			Minecraft.getMinecraft().renderEngine.bindTexture(vacuum);
			break;
		case fexKabel:
			Minecraft.getMinecraft().renderEngine.bindTexture(fex);
			break;
		case fimKabel:
			Minecraft.getMinecraft().renderEngine.bindTexture(fim);
			break;
		case fstorageKabel:
			Minecraft.getMinecraft().renderEngine.bindTexture(fstorage);
			break;
		default:
			break;

		}

		GlStateManager.pushMatrix();
		GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushAttrib();
		RenderHelper.disableStandardItemLighting();
		model.render(te);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
