package mvf314.mvflib.item;

import mvf314.mvflib.datagen.ItemModelGenerator;
import mvf314.mvflib.setup.RegistryMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

/**
 * The BaseToolItem is an extension of the BaseItem which allows this item to have durability and attack properties
 * @author Mvf314
 * @version 0.0.5
 * @since 0.0.4
 */
public abstract class BaseToolItem extends BaseItem {

	private int hitDamage = 1;
	private float attackDamage;
	private float attackSpeed;

	/**
	 * Construct a BaseToolItem
	 * @param prop		Item properties, can be generated by ItemPropertyProvider
	 * @param map		Registry map
	 * @param attackDamageIn	Attack damage of the tool
	 * @param attackSpeedIn		Attack speed of the tool
	 */
	public BaseToolItem(Properties prop, RegistryMap map, float attackDamageIn, float attackSpeedIn) {
		super(prop, map);
		this.attackDamage = attackDamageIn;
		this.attackSpeed = attackSpeedIn;
	}

	/**
	 * Set the amount of points that an enemy hit will damage the tool for
	 * @param dmg	Damage points
	 */
	protected void setHitDamage(int dmg) {
		this.hitDamage = dmg;
	}

	/**
	 * Damage the tool
	 * @param damage	Amount of damage to deal
	 * @param itemStack	Itemstack of the tool
	 * @param player	Player using the tool
	 */
	protected final void addDamage(int damage, ItemStack itemStack, LivingEntity player) {
		itemStack.damageItem(damage, player, entity -> {
			entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
	}

	/**
	 * Damage the tool
	 * @param damage	Amount of damage to deal
	 * @param player	Player using the tool
	 */
	protected final void addDamage(int damage, PlayerEntity player) {
		player.getHeldItem(Hand.MAIN_HAND).damageItem(damage, player, entity -> {
			entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
	}

	// Apply damage on enemy hit
	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		addDamage(hitDamage, stack, attacker);
		return super.hitEntity(stack, target, attacker);
	}

	/**
	 * Get standard item model
	 * @param modid Mod ID
	 * @return JSON String
	 */
	@Override
	public String getItemModel(String modid) {
		return ItemModelGenerator.getHandheld(modid, getRegistryName().getPath());
	}
}

