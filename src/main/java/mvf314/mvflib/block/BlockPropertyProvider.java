package mvf314.mvflib.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class BlockPropertyProvider {

	public static Block.Properties get(
			Material mat, MaterialColor matColor,
			SoundType sound,
			int harvestLevel, ToolType harvestTool,
			int lightValue,
			float resistance, float hardness,
			float slipperiness, float speedFactor, float jumpFactor,
			boolean blocksMovement, boolean isSolid, boolean variableOpacity,
			boolean ticksRandomly) {
		Block.Properties prop = Block.Properties.create(mat, matColor)
				.sound(sound)
				.harvestLevel(harvestLevel)
				.harvestTool(harvestTool)
				.lightValue(lightValue)
				.hardnessAndResistance(hardness, resistance)
				.slipperiness(slipperiness)
				.speedFactor(speedFactor)
				.jumpFactor(jumpFactor);
		if (!blocksMovement) {
			prop = prop.doesNotBlockMovement();
		}
		if (!isSolid) {
			prop = prop.notSolid();
		}
		if (ticksRandomly) {
			prop = prop.tickRandomly();
		}
		return prop;
	}

	public static Block.Properties get(
			Material mat, MaterialColor matColor,
			SoundType sound,
			int harvestLevel, ToolType harvestTool,
			int lightValue,
			float resistance, float hardness) {
		return get(
				mat, matColor,
				sound,
				harvestLevel, harvestTool,
				lightValue,
				resistance, hardness,
				0.6f, 1.0f, 1.0f,
				true, true, false,
				false);
	}

	public static Block.Properties get(
			Material mat,
			SoundType sound,
			int harvestLevel, ToolType harvestTool) {
		return get(
				mat, mat.getColor(),
				sound,
				harvestLevel, harvestTool,
				0,
				6, 1.5f);
		// Match resistance and hardness of smooth stone
	}
}
