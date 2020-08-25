package mvf314.mvflib.datagen;

import mvf314.jsontools.JsonBuilder;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * The BlockStateGenerator class contains utilities for generating block state JSON
 * @author Mvf314
 * @version 0.0.5
 * @since 0.0.5
 */
public class BlockStateGenerator {

	/**
	 * Generate a suffix from a block state string
	 * @param state Block state string
	 * @return Generated suffix
	 */
	private static String getSuffix(String state) {
		StringBuilder sb = new StringBuilder();
		String[] states = state.split(",");
		for (String singleState : states) {
			String[] pair = singleState.split("=");
			if (NumberUtils.isCreatable(pair[1])) {
				sb.append("_").append(pair[0]).append(pair[1]);
			}
			if (pair[1].equals("true")) {
				sb.append("_").append(pair[0]);
			}
		}
		return sb.toString();
	}

	/**
	 * Get a default block state
	 * @param modid	Mod ID
	 * @param resource Block model
	 * @return Block state JSON String
	 */
	public static String get(String modid, String resource) {
		return new JsonBuilder()
				.add("variants", new JsonBuilder()
					.add("", new JsonBuilder()
						.add("model", modid + ":block/" + resource))).get();
	}

	// get a json object for a single state
	private static JsonBuilder getVariant(String modState, String modid, String resource) {
		return new JsonBuilder()
				.add(modState, new JsonBuilder()
					.add("model", modid + ":block/" + resource));
	}

	// get a json object for a single state with direction
	private static JsonBuilder getDirectionalSingleStateBase(String modState, String modid, String resource) {
		return new JsonBuilder()
				.add("facing=down" + modState, new JsonBuilder()
						.add("model", modid + ":block/" + resource)
						.add("x", 90))
				.add("facing=up" + modState, new JsonBuilder()
						.add("model", modid + ":block/" + resource)
						.add("x", -90))
				.add("facing=north" + modState, new JsonBuilder()
						.add("model", modid + ":block/" + resource))
				.add("facing=east" + modState, new JsonBuilder()
						.add("model", modid + ":block/" + resource)
						.add("y", 90))
				.add("facing=south" + modState, new JsonBuilder()
						.add("model", modid + ":block/" + resource)
						.add("y", 180))
				.add("facing=west" + modState, new JsonBuilder()
						.add("model", modid + ":block/" + resource)
						.add("y", 270));

	}

	// get a json object for a single state with X-Z direction
	private static JsonBuilder getDirectionalXZSingleStateBase(String modState, String modid, String resource) {
		return new JsonBuilder()
				.add("facing=north" + modState, new JsonBuilder()
						.add("model", modid + ":block/" + resource))
				.add("facing=east" + modState, new JsonBuilder()
						.add("model", modid + ":block/" + resource)
						.add("y", 90))
				.add("facing=south" + modState, new JsonBuilder()
						.add("model", modid + ":block/" + resource)
						.add("y", 180))
				.add("facing=west" + modState, new JsonBuilder()
						.add("model", modid + ":block/" + resource)
						.add("y", 270));
	}

	/**
	 * Get JSON for a directional block without any other blockstate properties
	 * @param modid Mod ID
	 * @param resource Block model
	 * @return JSON object
	 */
	public static JsonBuilder getDirectional(String modid, String resource) {
		return getDirectionalSingleStateBase("", modid, resource);
	}

	/**
	 * Get JSON for a X-Z directional block without any other blockstate properties
	 * @param modid Mod ID
	 * @param resource Block model
	 * @return JSON object
	 */
	public static JsonBuilder getDirectionalXZ(String modid, String resource) {
		return getDirectionalXZSingleStateBase("", modid, resource);
	}

	/**
	 * Get JSON for a directional block with other blockstate properties
	 * @param state Other properties (for example, stage=1,enabled=true)
	 * @param modid	Mod ID
	 * @param resource Block model
	 * @return JSON Object
	 */
	public static JsonBuilder getDirectionalSingleState(String state, String modid, String resource) {
		return getDirectionalSingleStateBase("," + state, modid, resource);
	}
	/**
	 * Get JSON for a X-Z directional block with other blockstate properties
	 * @param state Other properties (for example, stage=1,enabled=true)
	 * @param modid	Mod ID
	 * @param resource Block model
	 * @return JSON Object
	 */
	public static JsonBuilder getDirectionalXZSingleState(String state, String modid, String resource) {
		return getDirectionalXZSingleStateBase("," + state, modid, resource);
	}

	/**
	 * Get JSON for a block state that links to a custom model
	 * @param state Other properties
	 * @param modid Mod ID
	 * @param baseResource Block model base
	 * @param suffix Model suffix
	 * @return JSON Object
	 */
	public static JsonBuilder getVariantCustom(String state, String modid, String baseResource, String suffix) {
		return getVariant(state, modid, baseResource + suffix);
	}

	/**
	 * Get JSON for a block state that links to a custom model with direction
	 * @param state Other properties
	 * @param modid Mod ID
	 * @param baseResource Block model base
	 * @param suffix Model suffix
	 * @return JSON Object
	 */
	public static JsonBuilder getDirectionalSingleStateCustom(String state, String modid, String baseResource, String suffix) {
		return getDirectionalSingleState(state, modid, baseResource + suffix);
	}

	/**
	 * Get JSON for a block state that links to a custom model with X-Z direction
	 * @param state Other properties
	 * @param modid Mod ID
	 * @param baseResource Block model base
	 * @param suffix Model suffix
	 * @return JSON Object
	 */
	public static JsonBuilder getDirectionalXZSingleStateCustom(String state, String modid, String baseResource, String suffix) {
		return getDirectionalXZSingleState(state, modid, baseResource + suffix);
	}

	/**
	 * Get JSON for a block state with other blockstate properties with auto-generated suffix
	 * @param state Other properties
	 * @param modid Mod ID
	 * @param baseResource Block model base
	 * @return JSON Object
	 */
	public static JsonBuilder generateVariant(String state, String modid, String baseResource) {
		return getVariantCustom(state, modid, baseResource, getSuffix(state));
	}

	/**
	 * Get JSON for a directional block state with other blockstate properties with auto-generated suffix
	 * @param state Other properties
	 * @param modid Mod ID
	 * @param baseResource Block model base
	 * @return JSON Object
	 */
	public static JsonBuilder generateDirectionalSingleState(String state, String modid, String baseResource) {
		return getDirectionalSingleStateCustom(state, modid, baseResource, getSuffix(state));
	}

	/**
	 * Get JSON for a X-Z directional block state with other blockstate properties with auto-generated suffix
	 * @param state Other properties
	 * @param modid Mod ID
	 * @param baseResource Block model base
	 * @return JSON Object
	 */
	public static JsonBuilder generateDirectionalXZSingleState(String state, String modid, String baseResource) {
		return getDirectionalXZSingleStateCustom(state, modid, baseResource, getSuffix(state));
	}

	/**
	 * Generate JSON from JSON objects (that can be generated using BlockStateGenerator)
	 * @param builders JSON Objects
	 * @return JSON string
	 */
	public static String generateFromStates(JsonBuilder... builders) {
		JsonBuilder builder = new JsonBuilder();
		for (JsonBuilder b : builders) {
			builder = builder.merge(b);
		}
		return new JsonBuilder()
				.add("variants", builder).get();
	}

	/**
	 * Generate JSON from block state strings (block model suffixes are automatically generated using BlockStateGenerator#getSuffix)
	 * @param modid	Mod ID
	 * @param baseResource Block model base
	 * @param states Block states
	 * @return JSON String
	 */
	public static String generateFromStates(String modid, String baseResource, String... states) {
		JsonBuilder builder = new JsonBuilder();
		for (String state : states) {
			builder = builder.merge(generateVariant(state, modid, baseResource));
		}
		return new JsonBuilder()
				.add("variants", builder).get();
	}

	/**
	 * Generate JSON from block state strings with direction (block model suffixes are automatically generated using BlockStateGenerator#getSuffix)
	 * @param modid	Mod ID
	 * @param baseResource Block model base
	 * @param states Block states
	 * @return JSON String
	 */
	public static String generateDirectionalFromStates(String modid, String baseResource, String... states) {
		JsonBuilder builder = new JsonBuilder();
		for (String state : states) {
			builder = builder.merge(generateDirectionalSingleState(state, modid, baseResource));
		}
		return new JsonBuilder()
				.add("variants", builder).get();
	}

	/**
	 * Generate JSON from block state strings with X-Z direction (block model suffixes are automatically generated using BlockStateGenerator#getSuffix)
	 * @param modid	Mod ID
	 * @param baseResource Block model base
	 * @param states Block states
	 * @return JSON String
	 */
	public static String generateDirectionalXZXFromStates(String modid, String baseResource, String... states) {
		JsonBuilder builder = new JsonBuilder();
		for (String state : states) {
			builder = builder.merge(generateDirectionalXZSingleState(state, modid, baseResource));
		}
		return new JsonBuilder()
				.add("variants", builder).get();
	}

}
