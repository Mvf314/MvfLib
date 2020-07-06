package mvf314.mvflib.item;

import net.minecraft.item.Item;

public abstract class BaseItem extends Item {

	public final String NAME;

	public BaseItem(Item.Properties prop, String name) {
		super(prop);
		NAME = name;
		setRegistryName(name);
	}
}
