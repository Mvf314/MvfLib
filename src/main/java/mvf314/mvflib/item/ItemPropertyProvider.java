package mvf314.mvflib.item;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

/**
 * Utilities for generating item properties
 * @author Mvf314
 * @version 0.0.2
 */
public class ItemPropertyProvider {

	/**
	 * Generate item properties
	 * @param maxStackSize      Maximum items in a single stack
	 * @param maxDamage         Maximum damage value for the item
	 * @param containerItem     Item which this item is contained in (useful for buckets)
	 * @param itemGroup         Item tab for creative menu
	 * @param rarity            Item rarity, used for display color
	 * @param food              Food properties if edible
	 * @param canRepair         If the item can be repaired
	 * @return                  An Item.Properties object with the given properties
	 */
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

	/**
	 * Generate item properties using default values
	 * @param group Item tab for creative menu
	 * @return      An Item.Properties object with the given properties
	 */
	public static Item.Properties get(ItemGroup group) {
		return get(64, 0, null, group, Rarity.COMMON, null, false);
	}

}
