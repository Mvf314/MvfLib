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

public abstract class BaseBlockStateProvider extends BlockStateProvider {

	public BaseBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
		super(gen, modid, exFileHelper);
	}

	@Override
	protected abstract void registerStatesAndModels();

	protected void createSimpleBlockstate(BaseBlock block) {
		ResourceLocation loc = modLoc("block/" + block.NAME);
		ModelFile model = models().cubeAll(block.NAME, loc);
		simpleBlock(block, model);
	}

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

	@Nonnull
	@Override
	public abstract String getName();
}
