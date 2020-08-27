package mvf314.mvflib.datagen;

import mvf314.jsontools.JsonBuilder;

/**
 * The ItemModelGenerator class contains utilities for generating item model JSON
 * @author Mvf314
 * @version 0.0.6
 * @since 0.0.5
 */
public class ItemModelGenerator {
	/**
	 * Get a simple item model
	 * @param parent Item model parent
	 * @return JSON String
	 */
	public static String get(String parent) {
		return new JsonBuilder()
				.add("parent", parent).get();
	}

	/**
	 * Get a textured simple item model
	 * @param parent Item model parent
	 * @param modid Mod ID
	 * @param resource Texture location
	 * @return
	 */
	public static String get(String parent, String modid, String resource) {
		return new JsonBuilder()
				.add("parent", parent)
				.add("textures", new JsonBuilder()
						.add("layer0", modid + ":item/" + resource)).get();
	}

	/**
	 * Get a normal item model
	 * @param modid Mod ID
	 * @param resource Item texture
	 * @return JSON String
	 */
	public static String getSimple(String modid, String resource) {
		return get("item/generated", modid, resource);
	}

	/**
	 * Get an item model for a handheld item (like a tool or weapon)
	 * @param modid Mod ID
	 * @param resource Item texture
	 * @return JSON String
	 */
	public static String getHandheld(String modid, String resource) {
		return get("item/handheld", modid, resource);
	}

	/**
	 * Get an item model for a spawn egg
	 * @return JSON String
	 */
	public static String getSpawnEgg() {
		return get("item/template_spawn_egg");
	}

	/**
	 * Get an item model for a block
	 * @param modid Mod ID
	 * @param resource Item texture
	 * @return JSON String
	 */
	public static String getBlock(String modid, String resource) {
		return get(modid + ":block/" + resource);
	}
}
