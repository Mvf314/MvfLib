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

public class Registry {
	public static class Blocks {
		public static void register(final RegistryEvent.Register<Block> event, BaseBlock block) {
			event.getRegistry().register(block);
		}

		public static void registerItem(final RegistryEvent.Register<Item> event, BaseBlock block, Item.Properties itemGroup) {
			event.getRegistry().register(new BlockItem(block, itemGroup).setRegistryName(block.NAME));
		}
	}

	public static class Items {
		public static void register(final RegistryEvent.Register<Item> event, BaseItem item) {
			event.getRegistry().register(item);
		}
	}

	public static class TileEntities {
		public static void register(final RegistryEvent.Register<TileEntityType<?>> event, Supplier<? extends BaseTileEntity> te, BaseBlock block) {
			event.getRegistry().register(TileEntityType.Builder.create(te, block).build(null).setRegistryName(block.NAME));

		}
	}
}
