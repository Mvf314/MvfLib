package mvf314.mvflib.datagen;

import mvf314.mvflib.block.BaseBlock;
import mvf314.mvflib.item.BaseItem;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * Data generator for language file
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.2
 */
public abstract class BaseLanguageProvider extends LanguageProvider {

	/**
	 * Locale of this language provider
	 */
	private String locale;

	/**
	 * Couple data generator to data provider
	 * @param gen       Data generator
	 * @param modid     Mod ID
	 * @param locale    Locale of this language provider
	 */
	public BaseLanguageProvider(DataGenerator gen, String modid, String locale) {
		super(gen, modid, locale);
		this.locale = locale;
	}

	/**
	 * Method that the data generator will execute
	 */
	@Override
	protected abstract void addTranslations();

	/**
	 * Create translation for your mod's item group
	 * @param modid Mod ID
	 * @param name  Translation key
	 */
	protected void createItemGroupTranslation(String modid, String name) {
		add("itemGroup." + modid, name);
	}

	/**
	 * Create translation for a block
	 * @param block Block
	 */
	protected void createBlockTranslation(BaseBlock block) {
		add(block, block.getTranslation(block.getLang(), locale));
	}

	/**
	 * Create translation for an item
	 * @param item Item
	 */
	protected void createItemTranslation(BaseItem item) {
		add(item, item.getTranslation(item.getLang(), locale));
	}

	/**
	 * Override to return your modid.
	 * @return Name of this language provider
	 */
	@Override
	public abstract String getName();

	/**
	 * Get locale of this provider
	 * @return Locale of this provider
	 */
	public String getLocale() {
		return locale;
	}
}
