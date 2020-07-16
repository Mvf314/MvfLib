package mvf314.mvflib.block;

import mvf314.mvflib.tools.ITranslatable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * The BaseBlock class is a simple abstract wrapper for the Block class and couples a registry name to the block object
 * @author Mvf314
 * @version 0.0.2
 */
public abstract class BaseBlock extends Block implements ITranslatable {

	/**
	 * Registry name of the block
	 */
	public final String NAME;

	protected Map<String, String> lang = new HashMap<>();

	/**
	 * Construct a Block object with given properties and name. The Properties can be generated by a {@link BlockPropertyProvider}
	 * @param prop Properties of the block
	 * @param name Registry name of the block
	 */
	public BaseBlock(Block.Properties prop, String name) {
		super(prop);
		NAME = name;
		setRegistryName(NAME);
	}

	/**
	 * Construct a Block object with the minimal amount of required properties.
	 * @param mat   Material type of the block
	 * @param name  Registry name of the block
	 */
	public BaseBlock(Material mat, String name) {
		super(Properties.create(mat));
		NAME = name;
		setRegistryName(NAME);
	}

	/**
	 * Create an ItemStack from this block
	 * @return ItemStack containing this block
	 */
	public ItemStack getItemStack() {
		return new ItemStack(this);
	}

	public Map<String, String> getLang() {
		return lang;
	}
}
