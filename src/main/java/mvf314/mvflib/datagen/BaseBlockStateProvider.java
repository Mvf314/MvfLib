package mvf314.mvflib.datagen;

import mvf314.mvflib.block.BaseBlock;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * This class is a data provider for block states and block models
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.2
 */
public abstract class BaseBlockStateProvider extends BlockStateProvider {

	/**
	 * Couple data generator to this class
	 * @param gen           Data generator
	 * @param modid         Mod ID
	 * @param exFileHelper  Existing file helper, pass GatherDataEvent.getExistingFileHelper()
	 */
	public BaseBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
		super(gen, modid, exFileHelper);
	}

	/**
	 * This method will be executed by the data generator
	 */
	@Override
	protected abstract void registerStatesAndModels();

	/**
	 * Create a basic block state without any properties
	 * @param block Block to create block state for
	 */
	protected void createSimpleBlockstate(BaseBlock block) {
		ResourceLocation loc = modLoc("block/" + block.NAME);
		ModelFile model = models().cubeAll(block.NAME, loc);
		simpleBlock(block, model);
	}

	/**
	 * Create a block state for a block which has different textures for different faces. Use for DirectionalBlocks.
	 * @param block The block to create block state for
	 * @param up    Suffix for texture file for top side
	 * @param down  Suffix for texture for bottom side
	 * @param front Suffix for texture for front side
	 * @param back  Suffix for texture for back side
	 * @param left  Suffix for texture for left side
	 * @param right Suffix for texture for right side
	 */
	protected void createDirectionalBlockstate(BaseBlock block, String up, String down, String front, String back, String left, String right) {
		ResourceLocation locUp = modLoc("block/" + block.NAME + up);
		ResourceLocation locDown = modLoc("block/" + block.NAME + down);
		ResourceLocation locFront = modLoc("block/" + block.NAME + front);
		ResourceLocation locBack = modLoc("block/" + block.NAME + back);
		ResourceLocation locLeft = modLoc("block/" + block.NAME + left);
		ResourceLocation locRight = modLoc("block/" + block.NAME + right);

		ModelFile model = models().cube(block.NAME, locDown, locUp, locFront, locBack, locLeft, locRight);
		Function<BlockState, ModelFile> modelFunc = $ -> model;
		getVariantBuilder(block)
				.forAllStates(state -> {
					Direction dir = state.get(BlockStateProperties.FACING);
					return ConfiguredModel.builder()
							.modelFile(modelFunc.apply(state))
							.rotationX(dir == Direction.DOWN ? 90 : (dir == Direction.UP ? -90 : 0))
							.rotationY(dir == Direction.EAST ? 90 : (dir == Direction.SOUTH ? 180 : (dir == Direction.WEST ? 270 : 0)))
							.build();
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
