package mvf314.mvflib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DirectionalBlock extends BaseBlock {

	public DirectionalBlock(Properties prop, String name) {
		super(prop, name);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		if (entity != null) {
			world.setBlockState(pos, state.with(BlockStateProperties.FACING, getFacingFromEntity(pos, entity)), 0b11);
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.FACING);
	}

	private Direction getFacingFromEntity(BlockPos pos, LivingEntity entity) {
		return Direction.getFacingFromVector(
				(float) (entity.getPosX() - pos.getX()),
				(float) (entity.getPosY() - pos.getY()),
				(float) (entity.getPosZ() - pos.getZ()));
	}
}
