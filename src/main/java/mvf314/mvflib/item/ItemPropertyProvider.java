package mvf314.mvflib.item;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class ItemPropertyProvider {

	public static Item.Properties get(
			int maxStackSize,
			int maxDamage,
			Item containerItem,
			ItemGroup itemGroup,
			Rarity rarity,
			Food food,
			boolean canRepair) {
		Item.Properties prop = new Item.Properties()
				.maxDamage(maxDamage)
				.maxStackSize(maxStackSize)
				.containerItem(containerItem)
				.group(itemGroup)
				.rarity(rarity)
				.food(food);
		if (!canRepair) {
			prop = prop.setNoRepair();
		}
		return prop;
	}

	public static Item.Properties get(ItemGroup group) {
		return get(64, 0, null, group, Rarity.COMMON, null, false);
	}

}
