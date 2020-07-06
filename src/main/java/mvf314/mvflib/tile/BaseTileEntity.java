package mvf314.mvflib.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class BaseTileEntity extends TileEntity {
	public BaseTileEntity(TileEntityType<?> tile) {
		super(tile);
	}
}
