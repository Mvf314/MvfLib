package mvf314.mvflib.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldTools {

	public static boolean spawnItem(World world, BlockPos blockPos, Item item) {
		return spawnItem(world, blockPos, new ItemStack(item));
	}

	public static boolean spawnItem(World world, BlockPos blockPos, ItemStack itemStack) {
		return world.addEntity(new ItemEntity(world, blockPos.getX() + 0.5d, blockPos.getY() + 0.5d, blockPos.getZ() + 0.5d, itemStack));
	}

	public static <T extends Comparable<T>> BlockState setBlockState(World world, BlockPos blockPos, BlockState state, IProperty<T> property, T value) {
		state = state.with(property, value);
		world.setBlockState(blockPos, state);
		return state;
	}

	public static boolean setBlock(World world, BlockPos blockPos, Block block) {
		return world.setBlockState(blockPos, block.getDefaultState());
	}
}
