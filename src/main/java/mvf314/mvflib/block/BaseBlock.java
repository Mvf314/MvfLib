package mvf314.mvflib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public abstract class BaseBlock extends Block {

	public final String NAME;

	public BaseBlock(Block.Properties prop, String name) {
		super(prop);
		NAME = name;
		setRegistryName(NAME);
	}

	public BaseBlock(Material mat, String name) {
		super(Properties.create(mat));
		NAME = name;
		setRegistryName(NAME);
	}

	public ItemStack getItemStack() {
		return new ItemStack(this);
	}
}
