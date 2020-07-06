package mvf314.mvflib.tile;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class TickableTileEntity extends BaseTileEntity implements ITickableTileEntity {

	public TickableTileEntity(TileEntityType<?> tile) {
		super(tile);
	}

	@Override
	public abstract void tick();
}
