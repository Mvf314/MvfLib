package mvf314.mvflib.datagen;

import mvf314.mvflib.block.BaseBlock;
import mvf314.mvflib.item.BaseItem;
import mvf314.mvflib.item.BaseSpawnEggItem;
import mvf314.mvflib.setup.RegistryMap;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;

import javax.annotation.Nonnull;

/**
 * Data provider for item models
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.2
 */
public abstract class BaseModelProvider extends ItemModelProvider {

	private final RegistryMap map;

	/**
	 * Couple Data generator to model provider
	 * @param generator     Data generator
	 * @param modid         Mod ID
	 * @param existingFileHelper Existing file helper, pass GatherDataEvent.getExistingFileHelper()
	 */
	public BaseModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper, RegistryMap registryMap) {
		super(generator, modid, existingFileHelper);
		map = registryMap;
	}

	/**
	 * This method specifies which item models need to be generated
	 */
	@Override
	protected abstract void registerModels();

	/**
	 * Create item model for a block (in inventory)
	 * @param block Block
	 */
	protected void createBlockItemModel(BaseBlock block) {
		String name = map.getValue(block);
		getBuilder("item/" + name)
			.parent(getExistingFile(modLoc("block/" + name)));
	}

	/**
	 * Create item model for normal item
	 * @param item Item
	 */
	protected void createSimpleItemModel(BaseItem item) {
		String name = map.getValue(item);
		getBuilder("item/" + name)
				.parent(getExistingFile(mcLoc("item/handheld")))
				.texture("layer0", "item/" + name);
	}

	/**
	 * Create item model for spawn egg item
	 * @param item Spawn Egg Item
	 */
	protected void createSpawnEggModel(BaseSpawnEggItem item) {
		getBuilder("item/" + map.getValue(item))
				.parent(getExistingFile(mcLoc("item/template_spawn_egg")));
	}

	/**
	 * Override to return your modid
	 * @return Name of the model provider
	 */
	@Nonnull
	@Override
	public abstract String getName();
}
