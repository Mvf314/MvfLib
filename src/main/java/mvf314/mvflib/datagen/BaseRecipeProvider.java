package mvf314.mvflib.datagen;

import net.minecraft.data.*;
import net.minecraft.util.IItemProvider;

import java.util.function.Consumer;

public abstract class BaseRecipeProvider extends RecipeProvider {

	private String modid;

	public BaseRecipeProvider(DataGenerator gen, String modid) {
		super(gen);
	}

	protected ShapedRecipeBuilder addShapedRecipe(IItemProvider in, String top, String mid, String bottom) {
		return ShapedRecipeBuilder.shapedRecipe(in)
				.patternLine(top)
				.patternLine(mid)
				.patternLine(bottom)
				.setGroup(modid);
	}

	protected ShapelessRecipeBuilder addShapelessRecipe(IItemProvider in) {
		return ShapelessRecipeBuilder.shapelessRecipe(in)
				.setGroup(modid);
	}

	@Override
	protected abstract void registerRecipes(Consumer<IFinishedRecipe> consumer);

	@Override
	public abstract String getName();
}
