package mvf314.mvflib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

/**
 * The TileEntityBlock class is for blocks that have an associated tile entity.
 * @author Mvf314
 * @version 0.0.2
 */
public abstract class TileEntityBlock extends BaseBlock {

	/**
	 * Construct the block with the given properties.
	 * @param prop Block properties
	 * @param name Registry name of the block
	 */
	public TileEntityBlock(Block.Properties prop, String name) {
		super(prop, name);
	}

	// This block always has an associated tile entity, so it must always return true
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	/**
	 * Create the tile entity. Should be overriden to return a new TileEntity()
	 * @param state Block state
	 * @param world Block reader
	 * @return      The created tile entity
	 */
	@Nullable
	@Override
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);
}
