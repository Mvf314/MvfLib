package mvf314.mvflib.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

/**
 * Utilities for generating block properties
 * @author Mvf314
 * @version 0.0.2
 */
public class BlockPropertyProvider {

	/**
	 * Generate block properties
	 * @param mat               The material type
	 * @param matColor          The material display color for maps
	 * @param sound             Sound type when walked over
	 * @param harvestLevel      Tool level needed to break
	 * @param harvestTool       Tool type needed to break
	 * @param lightValue        Amount of light emitted
	 * @param resistance        Blast resistance
	 * @param hardness          Block breaking hardness
	 * @param slipperiness      Block slipperiness
	 * @param speedFactor       Block speed modifier
	 * @param jumpFactor        Block jump modifier
	 * @param blocksMovement    If the block should block players from moving through it
	 * @param isSolid           If the block is solid
	 * @param variableOpacity   If the block's opacity is variable
	 * @param ticksRandomly     If the block should tick randomly
	 * @return                  A Block.Properties object with the given properties
	 */
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

	/**
	 * Generate block properties using default values
	 * @param mat           The material type
	 * @param matColor      The material display color for maps
	 * @param sound         Sound type when walked over
	 * @param harvestLevel  Tool level needed to break
	 * @param harvestTool   Tool type needed to break
	 * @param lightValue    Amount of light emitted
	 * @param resistance    Blast resistance
	 * @param hardness      Block breaking hardness
	 * @return              A Block.Properties object with the given properties
	 */
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

	/**
	 * Generate block properties using default values
	 * @param mat           The material type
	 * @param sound         Sound type when walked over
	 * @param harvestLevel  Tool level needed to break
	 * @param harvestTool   Tool type needed to break
	 * @return              A Block.Properties object with the given properties
	 */
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
