package mvf314.mvflib.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * The WorldTools class contains static methods that provide some utilities for changing blockstates, spawning items, etc.
 * @author Mvf314
 * @version 0.0.5
 * @since 0.0.5
 */
public class WorldTools {

	/**
	 * Spawn a single item in the world
	 * @param world World object
	 * @param blockPos Block position
	 * @param item Item to spawn
	 * @return Success
	 */
	public static boolean spawnItem(World world, BlockPos blockPos, Item item) {
		return spawnItem(world, blockPos, new ItemStack(item));
	}

	/**
	 * Spawn an item stack in the world
	 * @param world World object
	 * @param blockPos Block position
	 * @param itemStack Item stack to spawn
	 * @return Success
	 */
	public static boolean spawnItem(World world, BlockPos blockPos, ItemStack itemStack) {
		return world.addEntity(new ItemEntity(world, blockPos.getX() + 0.5d, blockPos.getY() + 0.5d, blockPos.getZ() + 0.5d, itemStack));
	}

	/**
	 * Update a block state property of a position
	 * @param world World object
	 * @param blockPos Block position
	 * @param state Original block state
	 * @param property Block state property
	 * @param value Block state property value
	 * @return The updated block state
	 */
	public static <T extends Comparable<T>> BlockState setBlockState(World world, BlockPos blockPos, BlockState state, IProperty<T> property, T value) {
		state = state.with(property, value);
		world.setBlockState(blockPos, state);
		return state;
	}

	/**
	 * Set a block in the world
	 * @param world World object
	 * @param blockPos Block position
	 * @param block New block
	 * @return Success
	 */
	public static boolean setBlock(World world, BlockPos blockPos, Block block) {
		return world.setBlockState(blockPos, block.getDefaultState());
	}
}
