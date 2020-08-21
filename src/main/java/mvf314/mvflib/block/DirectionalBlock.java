package mvf314.mvflib.block;

import mvf314.mvflib.setup.RegistryMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

/**
 * The DirectionalBlock is an extension of the BaseBlock which adds metadata to the block so that is faces a distinct side.
 * This is useful for blocks with different side textures.
 * @author Mvf314
 * @version 0.0.4
 * @since 0.0.2
 */
public abstract class DirectionalBlock extends BaseBlock {

	/**
	 * Create a DirectionalBlock with properties and a registry name
	 * @param prop  Block properties
	 * @param map   Registry map
	 */
	public DirectionalBlock(Properties prop, RegistryMap map) {
		super(prop, map);
	}

	// If the block is placed in the world, set the correct value for the "facing" metadata
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		if (entity != null) {
			// Use standard flags (send block update and notify neighbors), override if you want different flags.
			world.setBlockState(pos, state.with(BlockStateProperties.FACING, getFacingFromEntity(pos, entity)), Constants.BlockFlags.BLOCK_UPDATE | Constants.BlockFlags.NOTIFY_NEIGHBORS);
		}
	}

	// Adds "facing" metadata
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.FACING);
	}

	/**
	 * Utility method that converts the entity position and placed block position to the correct block facing direction
	 * @param pos       Placed block position
	 * @param entity    Entity that places the block
	 * @return          Block facing direction
	 */
	private Direction getFacingFromEntity(BlockPos pos, LivingEntity entity) {
		return Direction.getFacingFromVector(
				(float) (entity.getPosX() - pos.getX()),
				(float) (entity.getPosY() - pos.getY()),
				(float) (entity.getPosZ() - pos.getZ()));
	}
}
