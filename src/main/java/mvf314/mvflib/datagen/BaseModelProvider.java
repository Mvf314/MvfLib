package mvf314.mvflib.datagen;

import mvf314.mvflib.block.BaseBlock;
import mvf314.mvflib.item.BaseItem;
import mvf314.mvflib.setup.RegistryMap;
import mvf314.mvflib.tools.FileIO;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Data provider for item models
 * @author Mvf314
 * @version 0.0.5
 * @since 0.0.2
 */
public abstract class BaseModelProvider extends ItemModelProvider {

	/**
	 * Logger object
	 */
	public static final Logger LOGGER = LogManager.getLogger();

	/**
	 * Item model map
	 */
	protected final Map<ResourceLocation, String> itemModels = new HashMap<>();

	/**
	 * Mod ID
	 */
	private final String modid;

	/**
	 * Couple Data generator to model provider
	 * @param generator     Data generator
	 * @param modid         Mod ID
	 * @param existingFileHelper Existing file helper, pass GatherDataEvent.getExistingFileHelper()
	 */
	public BaseModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper, RegistryMap registryMap) {
		super(generator, modid, existingFileHelper);
		this.modid = modid;
	}

	/**
	 * This method specifies which item models need to be generated
	 */
	@Override
	protected abstract void registerModels();

	/**
	 * Generate item model as set by IItemModel#getItemModel
	 * @param item Item to generate model for
	 */
	protected void generateItemModel(BaseItem item) {
		itemModels.put(item.getRegistryName(), item.getItemModel(modid));
	}

	/**
	 * Generate item model for a block as set by IItemModel#getItemModel
	 * @param block Block to generate item model for
	 */
	protected void generateItemModel(BaseBlock block) {
		itemModels.put(block.getRegistryName(), block.getItemModel(modid));
	}

	/**
	 * Execute model generation
	 * @param cache directory cache
	 */
	@Override
	public void act(DirectoryCache cache) {
		registerModels();

		writeItemModels(cache, itemModels);
	}

	// write item models to disk
	private void writeItemModels(DirectoryCache cache, Map<ResourceLocation, String> models) {
		Path outFolder = this.generator.getOutputFolder();
		models.forEach((key, model) -> {
			Path path = outFolder.resolve("assets/" + key.getNamespace() + "/models/item/" + key.getPath() + ".json");
			try {
				FileIO.save(cache, model, path);
			} catch (IOException e) {
				LOGGER.error("Couldn't write item model {}", path, e);
			}
		});
	}

	/**
	 * Override to return your modid
	 * @return Name of the model provider
	 */
	@Nonnull
	@Override
	public abstract String getName();
}
