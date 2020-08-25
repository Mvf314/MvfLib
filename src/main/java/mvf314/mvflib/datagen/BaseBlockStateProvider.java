package mvf314.mvflib.datagen;

import mvf314.mvflib.block.BaseBlock;
import mvf314.mvflib.setup.RegistryMap;
import mvf314.mvflib.tools.FileIO;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a data provider for block states and block models
 * @author Mvf314
 * @version 0.0.5
 * @since 0.0.2
 */
public abstract class BaseBlockStateProvider extends BlockStateProvider {

	/**
	 * Logger object
	 */
	public static final Logger LOGGER = LogManager.getLogger();

	/**
	 * Map of block states
	 */
	protected final Map<ResourceLocation, String> blockStates = new HashMap<>();

	/**
	 * Mod ID
	 */
	private final String modid;

	/**
	 * Data generator
	 */
	private DataGenerator generator;

	/**
	 * Couple data generator to this class
	 * @param gen           Data generator
	 * @param modid         Mod ID
	 * @param exFileHelper  Existing file helper, pass GatherDataEvent.getExistingFileHelper()
	 */
	public BaseBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper, RegistryMap registryMap) {
		super(gen, modid, exFileHelper);
		this.generator = gen;
		this.modid = modid;
	}

	/**
	 * This method will be executed by the data generator
	 */
	@Override
	protected abstract void registerStatesAndModels();

	/**
	 * Generate the blockstate JSON as defined in IBlockState#getBlockState
	 * @param block Block
	 */
	protected void generateBlockState(BaseBlock block) {
		blockStates.put(block.getRegistryName(), block.getBlockState(modid));
	}

	/**
	 * Execute block state generation
	 * @param cache	Directory cache
	 * @throws IOException Only for override reasons
	 */
	@Override
	public void act(DirectoryCache cache) throws IOException {
		registerStatesAndModels();

		writeStates(cache, blockStates);
	}

	// write block states to disk
	private void writeStates(DirectoryCache cache, Map<ResourceLocation, String> states) {
		Path outFolder = this.generator.getOutputFolder();
		states.forEach((key, state) -> {
			Path path = outFolder.resolve("assets/" + key.getNamespace() + "/blockstates/" + key.getPath() + ".json");
			try {
				FileIO.save(cache, state, path);
			} catch (IOException e) {
				LOGGER.error("Couldn't write block state {}", path, e);
			}
		});
	}

	/**
	 * Name of the data provider. Override to return your mod id.
	 * @return Name of blockstate provider
	 */
	@Nonnull
	@Override
	public abstract String getName();
}
