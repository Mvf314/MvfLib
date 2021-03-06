package mvf314.mvflib.item;

import mvf314.mvflib.datagen.ItemModelGenerator;
import mvf314.mvflib.setup.RegistryMap;
import mvf314.mvflib.tools.IItemModel;
import mvf314.mvflib.tools.ITranslatable;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * The BaseItem abstract class is a wrapper for the Item class and binds a registry name to the object.
 * @author Mvf314
 * @version 0.0.5
 * @since 0.0.1
 */
public abstract class BaseItem extends Item implements ITranslatable, IItemModel {

	/**
	 * Language map
	 */
	protected Map<String, String> lang = new HashMap<>();

	/**
	 * Construct an Item object with the given properties. These can be generated by an {@link ItemPropertyProvider}.
	 * @param prop  Properties of the item
	 * @param map   Registry map
	 */
	public BaseItem(Item.Properties prop, RegistryMap map) {
		super(prop);
		setRegistryName(map.getValue(this));
	}

	/**
	 * Get the language map
	 * @return Language map
	 */
	public Map<String, String> getLang() {
		return lang;
	}

	/**
	 * Get standard item model
	 * @param modid Mod ID
	 * @return JSON String
	 */
	@Override
	public String getItemModel(String modid) {
		return ItemModelGenerator.getSimple(modid, getRegistryName().getPath());
	}
}
