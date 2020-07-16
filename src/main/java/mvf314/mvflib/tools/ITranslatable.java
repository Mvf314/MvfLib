package mvf314.mvflib.tools;

import java.util.Map;

public interface ITranslatable {
	default void setLocale(Map<String, String> map, String locale, String name) {
		map.put(locale, name);
	}

	default void setLocale(Map<String, String> map, String name) {
		setLocale(map, "en_us", name);
	}

	default String getTranslation(Map<String, String> map, String locale) {
		return map.get(locale);
	}

	default String getTranslation(Map<String, String> map) {
		return getTranslation(map, "en_us");
	}
}
