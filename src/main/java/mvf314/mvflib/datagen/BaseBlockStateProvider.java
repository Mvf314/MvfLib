package mvf314.mvflib.datagen;

import mvf314.mvflib.block.BaseBlock;
import mvf314.mvflib.block.DirectionalBlock;
import mvf314.mvflib.block.DirectionalXZBlock;
import mvf314.mvflib.setup.RegistryMap;
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
import java.security.InvalidParameterException;
import java.util.function.Function;

/**
 * This class is a data provider for block states and block models
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.2
 */
public abstract class BaseBlockStateProvider extends BlockStateProvider {

	private final RegistryMap map;

	/**
	 * Couple data generator to this class
	 * @param gen           Data generator
	 * @param modid         Mod ID
	 * @param exFileHelper  Existing file helper, pass GatherDataEvent.getExistingFileHelper()
	 */
	public BaseBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper, RegistryMap registryMap) {
		super(gen, modid, exFileHelper);
		map = registryMap;
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
		String name = map.getValue(block);
		ResourceLocation loc = modLoc("block/" + name);
		ModelFile model = models().cubeAll(name, loc);
		simpleBlock(block, model);
	}

	// can be used with either a DirectionalBlock of DirectionalXZBlock
	protected void createDirectionalModelBlockstate(BaseBlock block, ModelFile modelFile) {
		ModelFile model = modelFile;
		Function<BlockState, ModelFile> modelFunc = $ -> model;
		if (block instanceof DirectionalBlock) {
			getVariantBuilder(block)
					.forAllStates(state -> {
						Direction dir = state.get(BlockStateProperties.FACING);
						return ConfiguredModel.builder()
								.modelFile(modelFunc.apply(state))
								.rotationX(dir == Direction.DOWN ? 90 : (dir == Direction.UP ? -90 : 0))
								.rotationY(dir == Direction.EAST ? 90 : (dir == Direction.SOUTH ? 180 : (dir == Direction.WEST ? 270 : 0)))
								.build();
					});
		} else if (block instanceof DirectionalXZBlock) {
			getVariantBuilder(block)
					.forAllStates(state -> {
						Direction dir = state.get(BlockStateProperties.HORIZONTAL_FACING);
						return ConfiguredModel.builder()
								.modelFile(modelFunc.apply(state))
								.rotationX(dir == Direction.DOWN ? 90 : (dir == Direction.UP ? -90 : 0))
								.rotationY(dir == Direction.EAST ? 90 : (dir == Direction.SOUTH ? 180 : (dir == Direction.WEST ? 270 : 0)))
								.build();
					});
		} else {
			throw new InvalidParameterException("Only pass directional block object to createDirectionalModelBlockstate!");
		}
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
		String name = map.getValue(block);
		ResourceLocation locUp = modLoc("block/" + name + up);
		ResourceLocation locDown = modLoc("block/" + name + down);
		ResourceLocation locFront = modLoc("block/" + name + front);
		ResourceLocation locBack = modLoc("block/" + name + back);
		ResourceLocation locLeft = modLoc("block/" + name + left);
		ResourceLocation locRight = modLoc("block/" + name + right);

		createDirectionalModelBlockstate(block, models().cube(name, locDown, locUp, locFront, locBack, locLeft, locRight));
	}

	/**
	 * Name of the data provider. Override to return your mod id.
	 * @return Name of blockstate provider
	 */
	@Nonnull
	@Override
	public abstract String getName();
}
