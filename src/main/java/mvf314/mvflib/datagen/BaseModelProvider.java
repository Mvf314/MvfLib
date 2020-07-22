package mvf314.mvflib.datagen;

import mvf314.mvflib.block.BaseBlock;
import mvf314.mvflib.item.BaseItem;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;

import javax.annotation.Nonnull;

public abstract class BaseModelProvider extends ItemModelProvider {

	public BaseModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}

	@Override
	protected abstract void registerModels();

	protected void createBlockItemModel(BaseBlock block) {
		getBuilder("item/" + block.NAME)
			.parent(getExistingFile(modLoc("block/" + block.NAME)));
	}

	protected void createSimpleItemModel(BaseItem item) {
		getBuilder("item/" + item.NAME)
				.parent(getExistingFile(mcLoc("item/handheld")))
				.texture("layer0", "item/" + item.NAME);
	}

	protected void createSpawnEggModel(BaseItem item) {
		getBuilder("item/" + item.NAME)
				.parent(getExistingFile(mcLoc("item/template_spawn_egg")));
	}

	@Nonnull
	@Override
	public abstract String getName();
}
