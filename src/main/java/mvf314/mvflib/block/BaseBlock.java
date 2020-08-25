package mvf314.mvflib.block;

import mvf314.mvflib.datagen.BlockStateGenerator;
import mvf314.mvflib.datagen.ItemModel;
import mvf314.mvflib.setup.RegistryMap;
import mvf314.mvflib.tools.IBlockState;
import mvf314.mvflib.tools.IItemModel;
import mvf314.mvflib.tools.ITranslatable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * The BaseBlock class is a simple abstract wrapper for the Block class and couples a registry name to the block object
 * @author Mvf314
 * @version 0.0.5
 * @since 0.0.1
 */
public abstract class BaseBlock extends Block implements ITranslatable, IItemModel, IBlockState {

	/**
	 * Language map
	 */
	protected Map<String, String> lang = new HashMap<>();

	/**
	 * Construct a Block object with given properties and name. The Properties can be generated by a {@link BlockPropertyProvider}
	 * @param prop Properties of the block
	 * @param map  Registry map
	 */
	public BaseBlock(Block.Properties prop, RegistryMap map) {
		super(prop);
		setRegistryName(map.getValue(this));
	}

	/**
	 * Construct a Block object with the minimal amount of required properties.
	 * @param mat   Material type of the block
	 * @param map  Registry map
	 */
	public BaseBlock(Material mat, RegistryMap map) {
		super(Properties.create(mat));
		setRegistryName(map.getValue(this));
	}

	/**
	 * Create an ItemStack from this block
	 * @return ItemStack containing this block
	 */
	public ItemStack getItemStack() {
		return new ItemStack(this);
	}

	/**
	 * Return the language map
	 * @return Language map
	 */
	public Map<String, String> getLang() {
		return lang;
	}

	@Override
	public String getItemModel(String modid) {
		return ItemModel.getBlock(modid, getRegistryName().getPath());
	}

	@Override
	public String getBlockState(String modid) {
		return BlockStateGenerator.get(modid, getRegistryName().getPath());
	}
}
