package mvf314.mvflib.item;

import mvf314.mvflib.setup.RegistryMap;
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

/**
 * Item for spawn eggs.
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.3
 */
public abstract class BaseSpawnEggItem extends BaseItem {
	/**
	 * Initialize the spawn egg item
	 * @param prop Properties generated by {@link ItemPropertyProvider}
	 * @param color Color of the spawn egg
	 * @param map  Registry map
	 */
	public BaseSpawnEggItem(Properties prop, int color, RegistryMap map) {
		super(prop, map);
		COLOR = color;
	}

	/**
	 * Spawn egg color
	 */
	public final int COLOR;

	/**
	 * Try to spawn the entity. Call this in {@link BaseSpawnEggItem#onItemUse(ItemUseContext)}
	 * @param context Item use context
	 * @param entity Entity to spawn
	 * @return
	 */
	protected ActionResultType tryToSpawn(ItemUseContext context, EntityType entity) {
		World world = context.getWorld();
		if (world.isRemote) {
			// Only spawn server side
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

	/**
	 * Call {@link BaseSpawnEggItem#tryToSpawn(ItemUseContext, EntityType)} here
	 * @param context Item use context
	 * @return Result of item use
	 */
	@Override
	public abstract ActionResultType onItemUse(ItemUseContext context);
}
