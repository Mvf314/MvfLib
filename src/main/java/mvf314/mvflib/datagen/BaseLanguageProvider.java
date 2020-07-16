package mvf314.mvflib.datagen;

import mvf314.mvflib.block.BaseBlock;
import mvf314.mvflib.item.BaseItem;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public abstract class BaseLanguageProvider extends LanguageProvider {

	private String locale;

	public BaseLanguageProvider(DataGenerator gen, String modid, String locale) {
		super(gen, modid, locale);
		this.locale = locale;
	}

	@Override
	protected abstract void addTranslations();

	protected void createItemGroupTranslation(String modid, String name) {
		add("itemGroup." + modid, name);
	}

	protected void createBlockTranslation(BaseBlock block) {
		add(block, block.getTranslation(block.getLang(), locale));
	}

	protected void createItemTranslation(BaseItem item) {
		add(item, item.getTranslation(item.getLang(), locale));
	}

	@Override
	public abstract String getName();

	public String getLocale() {
		return locale;
	}
}
