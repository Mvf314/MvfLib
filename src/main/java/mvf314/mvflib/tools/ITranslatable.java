package mvf314.mvflib.tools;

import java.util.Map;

/**
 * Implement this interface for things with translation keys (blocks, items) for correct localization
 * @author Mvf314
 * @version 0.0.4
 * @since 0.0.2
 */
public interface ITranslatable {
	/**
	 * Add translation
	 * @param map Translation map
	 * @param locale Locale
	 * @param name Translation key
	 */
	default void setLocale(Map<String, String> map, String locale, String name) {
		map.put(locale, name);
	}

	/**
	 * Add English translation
	 * @param map   Translation map
	 * @param name  Translation key
	 */
	default void setLocale(Map<String, String> map, String name) {
		setLocale(map, "en_us", name);
	}

	/**
	 * Get translation
	 * @param map       Translation map
	 * @param locale    Locale
	 * @return  The translation
	 */
	default String getTranslation(Map<String, String> map, String locale) {
		return map.get(locale);
	}

	/**
	 * Get English translation
	 * @param map   Translation map
	 * @return  The translation
	 */
	default String getTranslation(Map<String, String> map) {
		return getTranslation(map, "en_us");
	}
}
