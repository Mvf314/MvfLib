package mvf314.mvflib.tools;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

/**
 * Utility class for creating configs
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.2
 */
public abstract class BaseConfig {

	/**
	 * Build a config
	 * @param builder   Config builder
	 * @return The built config
	 */
	protected static ForgeConfigSpec build(ForgeConfigSpec.Builder builder) {
		return builder.build();
	}

	/**
	 * Create a new config builder
	 * @return New builder
	 */
	protected static ForgeConfigSpec.Builder getBuilder() {
		return new ForgeConfigSpec.Builder();
	}

	/**
	 * Load configuration file
	 * @param spec Configuration object
	 * @param path Configuration file
	 */
	public static void loadConfig(ForgeConfigSpec spec, Path path) {
		final CommentedFileConfig cfgData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();
		cfgData.load();
		spec.setConfig(cfgData);
	}

	/**
	 * Begin a category in the config file
	 * @param builder   Config builder
	 * @param text      Category description
	 * @param category  Category tracker
	 */
	protected static void startCategory(ForgeConfigSpec.Builder builder, String text, String category) {
		builder.comment(text).push(category);
	}

	/**
	 * End a category in the config file
	 * @param builder   Config builder
	 */
	protected static void endCategory(ForgeConfigSpec.Builder builder) {
		builder.pop();
	}

	/**
	 * Add a configurable int from 0 to Integer.MAX_VALUE in the config
	 * @param builder   Config builder
	 * @param comment   Description
	 * @param name      Identifier
	 * @param defaultValue  Default value
	 * @return  Config builder with this int added
	 */
	protected static ForgeConfigSpec.IntValue positiveInt(ForgeConfigSpec.Builder builder, String comment, String name, int defaultValue) {
		return builder.comment(comment).defineInRange(name, defaultValue, 0, Integer.MAX_VALUE);
	}

	protected static ForgeConfigSpec.DoubleValue positiveDouble(ForgeConfigSpec.Builder builder, String comment, String name, double defaultValue) {
		return builder.comment(comment).defineInRange(name, defaultValue, 0, Double.MAX_VALUE);
	}
}
