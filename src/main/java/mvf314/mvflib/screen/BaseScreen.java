package mvf314.mvflib.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mvf314.mvflib.container.BaseContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * BaseScreen simplifies the rendering of GUIs
 * @param <T> The container to link this screen to
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.3
 */
public class BaseScreen<T extends BaseContainer> extends ContainerScreen<T> {
	/**
	 * Link to GUI texture
	 */
	private final ResourceLocation GUI;

	/**
	 * Create the screen and link it to a container
	 * @param screenContainer Container to link to
	 * @param inv           Player inventory
	 * @param titleIn       Text component
	 * @param modid         Mod ID
	 * @param guiTexture    GUI texture location
	 */
	public BaseScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn, String modid, String guiTexture) {
		super(screenContainer, inv, titleIn);
		GUI = new ResourceLocation(modid, guiTexture);
	}

	/**
	 * Render the screen
	 * @param mouseX        X coordinate of mouse
	 * @param mouseY        Y coordinate of mouse
	 * @param partialTicks  Partial ticks
	 */
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	/**
	 * Draws the transparent gray background
	 * @param partialTicks  Partial ticks
	 * @param mouseX    X coordinate of mouse
	 * @param mouseY    Y coordinate of mouse
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int relX = (this.width - this.xSize) / 2;
		int relY = (this.height - this.ySize) / 2;
		this.blit(relX, relY, 0, 0, this.xSize, this.ySize);
	}
}
