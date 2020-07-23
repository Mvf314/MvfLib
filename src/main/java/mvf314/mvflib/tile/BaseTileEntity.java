package mvf314.mvflib.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

/**
 * The BaseTileEntity class is a simple wrapper for tile entities
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.2
 */
public abstract class BaseTileEntity extends TileEntity {

	/**
	 * Initialize tile entity
	 * @param tile  Tile entity
	 */
	public BaseTileEntity(TileEntityType<?> tile) {
		super(tile);
	}
}
