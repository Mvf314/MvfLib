package mvf314.mvflib.block;

import mvf314.mvflib.setup.RegistryMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

/**
 * The TileEntityBlock class is for blocks that have an associated tile entity.
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.2
 */
public abstract class TileEntityBlock extends BaseBlock {

	/**
	 * Construct the block with the given properties.
	 * @param prop Block properties
	 * @param map  Registry map
	 */
	public TileEntityBlock(Block.Properties prop, RegistryMap map) {
		super(prop, map);
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

	/**
	 * This method opens the GUI associated with the tile entity.
	 * @param player Player entity
	 * @param te     Associated tile entity
	 */
	protected void openGui(PlayerEntity player, TileEntity te) {
		if (te instanceof INamedContainerProvider) {
			NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, te.getPos());
		}
	}
}
