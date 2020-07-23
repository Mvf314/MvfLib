package mvf314.mvflib.container;

import mvf314.mvflib.block.BaseBlock;
import mvf314.mvflib.tile.BaseTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

/**
 * The BaseContainer is an abstract class that handles things regarding GUI containers
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.3
 */
public abstract class BaseContainer extends Container {

	/**
	 * Container name
	 */
	public final String NAME;
	/**
	 * Tile entity to link container to
	 */
	protected BaseTileEntity te;
	/**
	 * Player inventory
	 */
	private IItemHandler playerInventory;
	/**
	 * Block the tile entity links to
	 */
	private BaseBlock block;

	/**
	 * Create a BaseContainer with the given properties
	 * @param type      The ContainerType object
	 * @param id        Container id
	 * @param world     World object
	 * @param pos       Block position
	 * @param inventory Player inventory
	 * @param baseBlock Block the container links to
	 * @param name      Container name
	 */
	public BaseContainer(@Nullable ContainerType<?> type, int id, World world, BlockPos pos, PlayerInventory inventory, BaseBlock baseBlock, String name) {
		super(type, id);
		te = (BaseTileEntity) world.getTileEntity(pos);
		playerInventory = new InvWrapper(inventory);
		block = baseBlock;
		NAME = name;

		initContainer();
	}

	/**
	 * Initialize capability handlers in here
	 */
	protected abstract void initContainer();

	/**
	 * Check if the player can interact with the tile entity
	 * @param playerIn  Player entity
	 * @return If the player can interact with the tile entity
	 */
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(IWorldPosCallable.of(te.getWorld(), te.getPos()), playerIn, block);
	}

	// Utility methods for creating the player inventory slots

	/**
	 * Add multiple item slots and link them to the item handler
	 * @param handler   The item handler
	 * @param idx       Index of first slot
	 * @param x         X coordinate of top left corner
	 * @param y         Y coordinate of top left corner
	 * @param amount    Amount of slots to add
	 * @param dx        Space to leave between slots
	 * @return New slot index
	 */
	protected int addSlotRange(IItemHandler handler, int idx, int x, int y, int amount, int dx) {
		for (int i = 0; i < amount; i++) {
			addSlot(new SlotItemHandler(handler, idx, x, y));
			x += dx;
			idx++;
		}
		return idx;
	}

	/**
	 * Add a box of item slots
	 * @param handler   Item handler
	 * @param idx       Index of first slot
	 * @param x         X coordinate of top left corner
	 * @param y         Y coordinate of top left corner
	 * @param horAmt    Horizontal amount of slots
	 * @param dx        Horizontal space between slots
	 * @param vertAmt   Vertical amount of slots
	 * @param dy        Vertical space between slots
	 * @return  New slot index
	 */
	protected int addSlotBox(IItemHandler handler, int idx, int x, int y, int horAmt, int dx, int vertAmt, int dy) {
		for (int j = 0; j < vertAmt; j++) {
			idx = addSlotRange(handler, idx, x, y, horAmt, dx);
			y += dy;
		}
		return idx;
	}

	/**
	 * Add the player inventory to the item handler and container
	 * @param leftCol X coordinate of top left corner
	 * @param topRow Y coordinate of top left corner
	 */
	protected void layoutPlayerInventorySlots(int leftCol, int topRow) {
		// Top 3 rows of player inventory
		addSlotBox(playerInventory, 9, leftCol, topRow, 9 ,18, 3, 18);

		// Hotbar slots
		topRow += 58;
		addSlotRange(playerInventory, 0, leftCol, topRow, 9 ,18);
	}
}
