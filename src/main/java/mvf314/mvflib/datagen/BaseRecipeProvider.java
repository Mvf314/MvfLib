package mvf314.mvflib.datagen;

import net.minecraft.data.*;
import net.minecraft.util.IItemProvider;

import java.util.function.Consumer;

/**
 * Data provider for recipes
 * @author Mvf314
 * @version 0.0.5
 * @since 0.0.3
 */
public abstract class BaseRecipeProvider extends RecipeProvider {

	/**
	 * Mod ID
	 */
	private String modid;

	/**
	 * Couple data generator to this provider
	 * @param gen   Data generator
	 * @param modid Mod ID
	 */
	public BaseRecipeProvider(DataGenerator gen, String modid) {
		super(gen);
		this.modid = modid;
	}

	/**
	 * Add shaped crafting recipe
	 * @param in    Crafting result
	 * @param top   Top row
	 * @param mid   Middle row
	 * @param bottom     Bottom row
	 * @return  Recipe builder, add the keys and criteria on this object
	 */
	protected ShapedRecipeBuilder addShapedRecipe(IItemProvider in, String top, String mid, String bottom) {
		return ShapedRecipeBuilder.shapedRecipe(in)
				.patternLine(top)
				.patternLine(mid)
				.patternLine(bottom)
				.setGroup(modid);
	}

	/**
	 * Add shapeless crafting recipe
	 * @param in    Crafting result
	 * @return  Recipe builder, add materials and criteria on this object
	 */
	protected ShapelessRecipeBuilder addShapelessRecipe(IItemProvider in) {
		return ShapelessRecipeBuilder.shapelessRecipe(in)
				.setGroup(modid);
	}

	/**
	 * Put the recipes you want to generate in here
	 * @param consumer Recipe Consumer
	 */
	@Override
	protected abstract void registerRecipes(Consumer<IFinishedRecipe> consumer);

	/**
	 * Override to return your modid
	 * @return Name of recipe provider
	 */
	@Override
	public abstract String getName();
}
