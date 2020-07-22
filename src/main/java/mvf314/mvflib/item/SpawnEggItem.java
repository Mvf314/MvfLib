package mvf314.mvflib.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public abstract class SpawnEggItem extends BaseItem {
	public SpawnEggItem(Properties prop, String name, EntityType<?> entity) {
		super(prop, name);
		this.entity = entity;
	}

	private EntityType<?> entity;

	protected ActionResultType tryToSpawn(ItemUseContext context, EntityType entity) {
		World world = context.getWorld();
		if (world.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			ItemStack itemStack = context.getItem();
			BlockPos blockPos = context.getPos();
			Direction direction = context.getFace();
			BlockState blockState = world.getBlockState(blockPos);

			BlockPos blockPos1;
			if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
				blockPos1 = blockPos;
			} else {
				blockPos1 = blockPos.offset(direction);
			}

			if (entity.spawn(world, itemStack, context.getPlayer(), blockPos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos1) && direction == Direction.UP) != null) {
				itemStack.shrink(1);
			}
			return ActionResultType.SUCCESS;
		}
	}

	@Override
	public abstract ActionResultType onItemUse(ItemUseContext context);
}
