package mvf314.mvflib.datagen;

import mvf314.jsontools.JsonBuilder;

public class ItemModel {
	private static String get(String parent) {
		return new JsonBuilder()
				.add("parent", parent).get();
	}
	private static String get(String parent, String modid, String resource) {
		return new JsonBuilder()
				.add("parent", parent)
				.add("textures", new JsonBuilder()
						.add("layer0", modid + ":item/" + resource)).get();
	}
	public static String getSimple(String modid, String resource) {
		return get("item/generated", modid, resource);
	}

	public static String getHandheld(String modid, String resource) {
		return get("item/handheld", modid, resource);
	}

	public static String getSpawnEgg() {
		return get("item/template_spawn_egg");
	}

	public static String getBlock(String modid, String resource) {
		return get(modid + ":block/" + resource);
	}
}
