package mvf314.mvflib.block;

import mvf314.mvflib.datagen.BlockStateGenerator;
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
 * The DirectionalXZBlock is like the DirectionalBlock, but without the option of facing up and down (locked to the horizontal plane).
 * Useful for machines.
 * @author Mvf314
 * @version 0.0.5
 * @since 0.0.4
 */
public abstract class DirectionalXZBlock extends BaseBlock {
	/**
	 * Create a DirectionalXZBlock with properties and a registry name
	 * @param prop  Block properties
	 * @param map   Registry map
	 */
	public DirectionalXZBlock(Properties prop, RegistryMap map) {
		super(prop, map);
	}

	// If the block is placed in the world, set the correct value for the "facing" metadata
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		if (entity != null) {
			// Use standard flags (send block update and notify neighbors), override if you want different flags.
			world.setBlockState(pos, state.with(BlockStateProperties.HORIZONTAL_FACING, getFacingFromEntity(pos, entity)), Constants.BlockFlags.BLOCK_UPDATE | Constants.BlockFlags.NOTIFY_NEIGHBORS);
		}
	}

	// Adds "facing" metadata
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING);
	}

	/**
	 * Utility method that converts the entity position and placed block position to the correct block facing direction
	 * @param pos       Placed block position
	 * @param entity    Entity that places the block
	 * @return          Block facing direction
	 */
	public static Direction getFacingFromEntity(BlockPos pos, LivingEntity entity) {
		Direction dir = Direction.getFacingFromVector(
				(float) (entity.getPosX() - pos.getX()),
				(float) (entity.getPosY() - pos.getY()),
				(float) (entity.getPosZ() - pos.getZ()));
		if (dir == Direction.UP || dir == Direction.DOWN) {
			return Direction.NORTH;
		}
		return dir;
	}

	/**
	 * Set default block state
	 * @param modid Mod ID
	 * @return Block state JSON
	 */
	@Override
	public String getBlockState(String modid) {
		return BlockStateGenerator.generateFromStates(
				BlockStateGenerator.getDirectionalXZ(modid, getRegistryName().getPath())
		);
	}
}
