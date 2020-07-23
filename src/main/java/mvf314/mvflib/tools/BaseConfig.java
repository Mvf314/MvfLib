package mvf314.mvflib.tools;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public abstract class BaseConfig {

	protected static ForgeConfigSpec build(ForgeConfigSpec.Builder builder) {
		return builder.build();
	}

	protected static ForgeConfigSpec.Builder getBuilder() {
		return new ForgeConfigSpec.Builder();
	}

	public static void loadConfig(ForgeConfigSpec spec, Path path) {
		final CommentedFileConfig cfgData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();
		cfgData.load();
		spec.setConfig(cfgData);
	}

	protected static void startCategory(ForgeConfigSpec.Builder builder, String text, String category) {
		builder.comment(text).push(category);
	}
	protected static void endCategory(ForgeConfigSpec.Builder builder) {
		builder.pop();
	}

	protected static ForgeConfigSpec.IntValue unboundedInt(ForgeConfigSpec.Builder builder, String comment, String name, int defaultValue) {
		return builder.comment(comment).defineInRange(name, defaultValue, 0, Integer.MAX_VALUE);
	}
}
