package mvf314.mvflib.setup;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class RegistryMap {
	private Map<String, String> map;

	private Item.Properties itemGroup;

	public RegistryMap(Item.Properties itemGroup) {
		this.map = new HashMap<>();
		this.itemGroup = itemGroup;
	}

	public static String getRegistryKey(Object o) {
		return o.getClass().getName();
	}

	public void put(String key, String name) {
		map.put(key, name);
	}

	private String getRegistryKey(String key) {
		return map.get(key);
	}

	public String getValue(Object o) {
		return map.get(getRegistryKey(o));
	}

	public Item.Properties getItemGroup() {
		return itemGroup;
	}

	public void print() {
		for (Map.Entry<String, String> pair: map.entrySet()) {
			System.out.println(pair.getKey() + ": " + pair.getValue());
		}
	}
}
