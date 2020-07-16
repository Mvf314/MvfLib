package mvf314.mvflib.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mvf314.mvflib.block.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * The BaseLootTableProvider class can be extended to generate loot tables
 * @author Mvf314
 * @version 0.0.2
 */
public abstract class BaseLootTableProvider extends LootTableProvider {

	/**
	 * Logger object
	 */
	public static final Logger LOGGER = LogManager.getLogger();
	/**
	 * JSON generator
	 */
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	/**
	 * Loot table holder
	 */
	protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
	/**
	 * Data generator
	 */
	private final DataGenerator gen;

	/**
	 * Couple data generator to this loot table provider
	 * @param gen Data generator to couple
	 */
	public BaseLootTableProvider(DataGenerator gen) {
		super(gen);
		this.gen = gen;
	}

	/**
	 * This method specifies which loot tables should be generated
	 */
	protected abstract void addTables();

	/**
	 * Create a loot table that drops the block without NBT data when mined
	 * @param block Block object
	 * @return      The loot table builder
	 */
	protected LootTable.Builder createSimpleTable(BaseBlock block) {
		LootPool.Builder builder = LootPool.builder()
				.name(block.NAME)
				.rolls(ConstantRange.of(1))
				.addEntry(ItemLootEntry.builder(block));
		return LootTable.builder().addLootPool(builder);
	}

	// Execute loot table generation
	@Override
	public void act(DirectoryCache cache) {
		addTables();

		Map<ResourceLocation, LootTable> tables = new HashMap<>();
		for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
			tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
		}
		writeTables(cache, tables);
	}

	// Write loot tables to generated dir
	private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
		Path outFolder = this.gen.getOutputFolder();
		tables.forEach((key, lootTable) -> {
			Path path = outFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
			try {
				IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
			} catch (IOException e) {
				LOGGER.error("Couldn't write loot table {}", path, e);
			}
		});
	}

	/**
	 * Loot table provider name, used in logging. Override to return your mod name.
	 * @return Provider name for use in logging.
	 */
	@Override
	public abstract String getName();
}
