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

public abstract class BaseContainer extends Container {

	public final String NAME;
	protected BaseTileEntity te;
	private IItemHandler playerInventory;
	private BaseBlock block;

	public BaseContainer(@Nullable ContainerType<?> type, int id, World world, BlockPos pos, PlayerInventory inventory, BaseBlock baseBlock, String name) {
		super(type, id);
		te = (BaseTileEntity) world.getTileEntity(pos);
		playerInventory = new InvWrapper(inventory);
		block = baseBlock;
		NAME = name;

		initContainer();
	}

	protected abstract void initContainer();

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(IWorldPosCallable.of(te.getWorld(), te.getPos()), playerIn, block);
	}

	protected int addSlotRange(IItemHandler handler, int idx, int x, int y, int amount, int dx) {
		for (int i = 0; i < amount; i++) {
			addSlot(new SlotItemHandler(handler, idx, x, y));
			x += dx;
			idx++;
		}
		return idx;
	}

	protected int addSlotBox(IItemHandler handler, int idx, int x, int y, int horAmt, int dx, int verAmt, int dy) {
		for (int j = 0; j < verAmt; j++) {
			idx = addSlotRange(handler, idx, x, y, horAmt, dx);
			y += dy;
		}
		return idx;
	}

	protected void layoutPlayerInventorySlots(int leftCol, int topRow) {
		addSlotBox(playerInventory, 9, leftCol, topRow, 9 ,18, 3, 18);

		topRow += 58;
		addSlotRange(playerInventory, 0, leftCol, topRow, 9 ,18);
	}
}
