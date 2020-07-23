package mvf314.mvflib.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mvf314.mvflib.container.BaseContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BaseScreen<T extends BaseContainer> extends ContainerScreen<T> {
	private final ResourceLocation GUI;
	public BaseScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn, String modid, String guiTexture) {
		super(screenContainer, inv, titleIn);
		GUI = new ResourceLocation(modid, guiTexture);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int relX = (this.width - this.xSize) / 2;
		int relY = (this.height - this.ySize) / 2;
		this.blit(relX, relY, 0, 0, this.xSize, this.ySize);
	}
}
