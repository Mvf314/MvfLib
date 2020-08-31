package mvf314.mvflib.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mvf314.mvflib.block.BaseBlock;
import mvf314.mvflib.setup.RegistryMap;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.item.Item;
import net.minecraft.state.IProperty;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * The BaseLootTableProvider class can be extended to generate loot tables
 * @author Mvf314
 * @version 0.0.6
 * @since 0.0.1
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

	private final RegistryMap map;

	/**
	 * Couple data generator to this loot table provider
	 * @param gen Data generator to couple
	 */
	public BaseLootTableProvider(DataGenerator gen, RegistryMap registryMap) {
		super(gen);
		this.gen = gen;
		this.map = registryMap;
	}

	/**
	 * This method specifies which loot tables should be generated
	 */
	protected abstract void addTables();

	protected ItemLootEntry.Builder<?> itemEntry(IItemProvider item) {
		return ItemLootEntry.builder(item);
	}

	protected LootTable.Builder makeFromPools(LootPool.Builder... pools) {
		LootTable.Builder builder = LootTable.builder();
		for (LootPool.Builder pool : pools) {
			builder = builder.addLootPool(pool);
		}
		return builder;
	}

	protected LootPool.Builder getPool(String name, IRandomRange range) {
		return LootPool.builder().name(name)
				.rolls(range);
	}

	protected LootPool.Builder getPool(String name) {
		return getPool(name, ConstantRange.of(1));
	}

	protected LootPool.Builder getSimplePool(BaseBlock block) {
		return getPool(map.getValue(block))
				.addEntry(itemEntry(block));
	}

	protected BlockStateProperty.Builder boolBlockStateCondition(BaseBlock block, IProperty<Boolean> property, boolean value) {
		return BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withBoolProp(property, value));
	}

	protected void addTable(BaseBlock block, LootTable.Builder lootTable) {
		lootTables.put(block, lootTable);
	}


	/**
	 * Add a simple loot table to be generated
	 * @param block Block
	 * @see BaseLootTableProvider#createSimpleTable(BaseBlock)
	 */
	protected void addSimpleTable(BaseBlock block) {
		addTable(block, createSimpleTable(block));
	}

	/**
	 * Create a loot table that drops the block without NBT data when mined
	 * @param block Block object
	 * @return      The loot table builder
	 */
	protected LootTable.Builder createSimpleTable(BaseBlock block) {
		return makeFromPools(getSimplePool(block));
	}

	/**
	 * Create a loot table that drops an item
	 * @param block Block object
	 * @param item  Item to drop
	 * @return		Loot table builder
	 */
	protected LootTable.Builder createItemTable(BaseBlock block, IItemProvider item) {
		return makeFromPools(getPool(map.getValue(block)).addEntry(itemEntry(item)));
	}

	/**
	 * Add a gem table to be generated
	 * @param block Block object
	 * @param item  Item to drop
	 */
	protected void addItemTable(BaseBlock block, Item item) {
		lootTables.put(block, createItemTable(block, item));
	}

	/**
	 * Execute loot table generation
	 * @param cache Directory cache
	 */
	@Override
	public void act(DirectoryCache cache) {
		addTables();

		Map<ResourceLocation, LootTable> tables = new HashMap<>();
		for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
			tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
		}
		writeTables(cache, tables);
	}

	/**
	 * Write loot tables to file
	 * @param cache  Directory cache
	 * @param tables Loot table map
	 */
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
