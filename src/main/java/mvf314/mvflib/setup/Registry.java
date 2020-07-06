package mvf314.mvflib.setup;

import mvf314.mvflib.block.BaseBlock;
import mvf314.mvflib.item.BaseItem;
import mvf314.mvflib.tile.BaseTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;

import java.util.function.Supplier;

/**
 * The Registry class has classes which simplify object registration
 * @author Mvf314
 * @version 0.0.2
 */
public class Registry {
	public static class Blocks {
		/**
		 * Register a block
		 * @param event The registry event that is passed to the subscribed method
		 * @param block Block to register
		 */
		public static void register(final RegistryEvent.Register<Block> event, BaseBlock block) {
			event.getRegistry().register(block);
		}

		/**
		 * Register the item/inventory version of a block
		 * @param event     The registry event that is passed to the subscribed method
		 * @param block     Block to register
		 * @param itemGroup Creative tab to put the block in
		 */
		public static void registerItem(final RegistryEvent.Register<Item> event, BaseBlock block, Item.Properties itemGroup) {
			event.getRegistry().register(new BlockItem(block, itemGroup).setRegistryName(block.NAME));
		}
	}

	public static class Items {
		/**
		 * Register an item
		 * @param event The registry event that is passed to the subscribed method
		 * @param item  Item to register
		 */
		public static void register(final RegistryEvent.Register<Item> event, BaseItem item) {
			event.getRegistry().register(item);
		}
	}

	public static class TileEntities {
		/**
		 * Register a tile entity
		 * @param event The registry event that is passed to the subscribed method
		 * @param te    Tile entity to register
		 * @param block The block that is paired with this tile entity
		 */
		public static void register(final RegistryEvent.Register<TileEntityType<?>> event, Supplier<? extends BaseTileEntity> te, BaseBlock block) {
			event.getRegistry().register(TileEntityType.Builder.create(te, block).build(null).setRegistryName(block.NAME));

		}
	}
}
