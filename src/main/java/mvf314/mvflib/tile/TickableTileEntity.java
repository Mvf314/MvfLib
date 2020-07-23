package mvf314.mvflib.tile;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

/**
 * The TickableTileEntity class is for tile entities that tick (or update) every frame.
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.2
 */
public abstract class TickableTileEntity extends BaseTileEntity implements ITickableTileEntity {

	/**
	 * Initialize tickable tile entity
	 * @param tile Tile entity
	 */
	public TickableTileEntity(TileEntityType<?> tile) {
		super(tile);
	}

	/**
	 * Tick tile entity
	 */
	// This code gets run every tick, so only put absolute neccesary code in here!
	@Override
	public abstract void tick();
}
