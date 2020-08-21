package mvf314.mvflib.setup;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * The RegistryMap class is a container for registry entries
 * @author Mvf314
 * @version 0.0.4
 * @since 0.0.3
 */
public class RegistryMap {
	private Map<String, String> map;

	private Item.Properties itemGroup;

	/**
	 * Create a new registry map with an item group
	 * @param itemGroup The item group
	 */
	public RegistryMap(Item.Properties itemGroup) {
		this.map = new HashMap<>();
		this.itemGroup = itemGroup;
	}

	/**
	 * A simple way to generate a registry key from a class
	 * @param o Class
	 * @return Generated registry key
	 */
	public static String getRegistryKey(Object o) {
		return o.getClass().getName();
	}

	/**
	 * Put an entry in the registry map
	 * @param key	Entry key
	 * @param name	Entry value
	 */
	public void put(String key, String name) {
		map.put(key, name);
	}

	/**
	 * Get the registry value of a certain class
	 * @param o Class
	 * @return	Registry value
	 */
	public String getValue(Object o) {
		return map.get(getRegistryKey(o));
	}

	/**
	 * Get registry item group
	 * @return Registry item group
	 */
	public Item.Properties getItemGroup() {
		return itemGroup;
	}

	// For debugging purposes
	public void print() {
		System.out.println("Printing registry map contents");
		for (Map.Entry<String, String> pair: map.entrySet()) {
			System.out.println(pair.getKey() + ": " + pair.getValue());
		}
	}
}
