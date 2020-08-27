package mvf314.mvflib.setup;

import mvf314.mvflib.container.BaseContainer;
import mvf314.mvflib.item.BaseItem;
import mvf314.mvflib.tile.BaseTileEntity;
import mvf314.mvflib.tools.IClientColor;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.network.IContainerFactory;

import java.util.function.Supplier;

/**
 * The Registry class has classes which simplify object registration
 * @author Mvf314
 * @version 0.0.6
 * @since 0.0.1
 */
public class Registry {
	public static class Blocks {
		/**
		 * Register a block
		 * @param event The registry event that is passed to the subscribed method
		 * @param block Block to register
		 */
		public static void register(final RegistryEvent.Register<Block> event, Block block) {
			event.getRegistry().register(block);
		}

		/**
		 * Register the item/inventory version of a block
		 * @param event     The registry event that is passed to the subscribed method
		 * @param block     Block to register
		 */
		public static void registerItem(final RegistryEvent.Register<Item> event, Block block, RegistryMap registryMap) {
			event.getRegistry().register(new BlockItem(block, registryMap.getItemGroup()).setRegistryName(block.getRegistryName()));
		}
	}

	public static class Items {
		/**
		 * Register an item
		 * @param event The registry event that is passed to the subscribed method
		 * @param item  Item to register
		 */
		public static void register(final RegistryEvent.Register<Item> event, Item item) {
			event.getRegistry().register(item);
		}

		/**
		 * Register item color, call this in client registration
		 * @param event Registry event that is passed to the subscribed method
		 * @param item  Item to register (needs to implement IClientColor)
		 */
		public static <T extends BaseItem & IClientColor> void registerItemColor(ColorHandlerEvent.Item event, T item) {
			event.getItemColors().register((stack, i) -> item.getColor(), item);
		}
	}

	public static class TileEntities {
		/**
		 * Register a tile entity
		 * @param event The registry event that is passed to the subscribed method
		 * @param te    Tile entity to register
		 * @param block The block that is paired with this tile entity
		 */
		public static void register(final RegistryEvent.Register<TileEntityType<?>> event, Supplier<? extends BaseTileEntity> te, Block block, RegistryMap map) {
			event.getRegistry().register(TileEntityType.Builder.create(te, block).build(null).setRegistryName(map.getValue(block)));

		}
	}

	public static class Containers {
		/**
		 * Register a container
		 * @param event The registry event that is passed to the subscribed method
		 * @param factory A container factory
		 * @param name  Container name
		 */
		public static void register(final RegistryEvent.Register<ContainerType<?>> event, IContainerFactory<BaseContainer> factory, String name) {
			event.getRegistry().register(IForgeContainerType.create(factory).setRegistryName(name));
		}
	}
}
