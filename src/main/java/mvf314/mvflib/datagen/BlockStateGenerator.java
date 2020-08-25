package mvf314.mvflib.datagen;

import mvf314.jsontools.JsonBuilder;
import org.apache.commons.lang3.math.NumberUtils;

public class BlockStateGenerator {

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

	public static String get(String modid, String resource) {
		return new JsonBuilder()
				.add("variants", new JsonBuilder()
					.add("", new JsonBuilder()
						.add("model", modid + ":block/" + resource))).get();
	}

	private static JsonBuilder getVariant(String modState, String modid, String resource) {
		return new JsonBuilder()
				.add(modState, new JsonBuilder()
					.add("model", modid + ":block/" + resource));
	}

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

	public static JsonBuilder getDirectional(String modid, String resource) {
		return getDirectionalSingleStateBase("", modid, resource);
	}

	public static JsonBuilder getDirectionalXZ(String modid, String resource) {
		return getDirectionalXZSingleStateBase("", modid, resource);
	}

	public static JsonBuilder getDirectionalSingleState(String state, String modid, String resource) {
		return getDirectionalSingleStateBase("," + state, modid, resource);
	}

	public static JsonBuilder getDirectionalXZSingleState(String state, String modid, String resource) {
		return getDirectionalXZSingleStateBase("," + state, modid, resource);
	}

	public static JsonBuilder getVariantCustom(String state, String modid, String baseResource, String suffix) {
		return getVariant(state, modid, baseResource + suffix);
	}

	public static JsonBuilder getDirectionalSingleStateCustom(String state, String modid, String baseResource, String suffix) {
		return getDirectionalSingleState(state, modid, baseResource + suffix);
	}

	public static JsonBuilder getDirectionalXZSingleStateCustom(String state, String modid, String baseResource, String suffix) {
		return getDirectionalXZSingleState(state, modid, baseResource + suffix);
	}

	public static JsonBuilder generateVariant(String state, String modid, String baseResource) {
		return getVariantCustom(state, modid, baseResource, getSuffix(state));
	}

	public static JsonBuilder generateDirectionalSingleState(String state, String modid, String baseResource) {
		return getDirectionalSingleStateCustom(state, modid, baseResource, getSuffix(state));
	}

	public static JsonBuilder generateDirectionalXZSingleState(String state, String modid, String baseResource) {
		return getDirectionalXZSingleStateCustom(state, modid, baseResource, getSuffix(state));
	}

	public static String generateFromStates(JsonBuilder... builders) {
		JsonBuilder builder = new JsonBuilder();
		for (JsonBuilder b : builders) {
			builder = builder.merge(b);
		}
		return new JsonBuilder()
				.add("variants", builder).get();
	}

	public static String generateFromStates(String modid, String baseResource, String... states) {
		JsonBuilder builder = new JsonBuilder();
		for (String state : states) {
			builder = builder.merge(generateVariant(state, modid, baseResource));
		}
		return new JsonBuilder()
				.add("variants", builder).get();
	}

	public static String generateDirectionalFromStates(String modid, String baseResource, String... states) {
		JsonBuilder builder = new JsonBuilder();
		for (String state : states) {
			builder = builder.merge(generateDirectionalSingleState(state, modid, baseResource));
		}
		return new JsonBuilder()
				.add("variants", builder).get();
	}

	public static String generateDirectionalXZXFromStates(String modid, String baseResource, String... states) {
		JsonBuilder builder = new JsonBuilder();
		for (String state : states) {
			builder = builder.merge(generateDirectionalXZSingleState(state, modid, baseResource));
		}
		return new JsonBuilder()
				.add("variants", builder).get();
	}

}
