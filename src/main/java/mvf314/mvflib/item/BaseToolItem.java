package mvf314.mvflib.item;

import mvf314.mvflib.setup.RegistryMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class BaseToolItem extends BaseItem {

	private int hitDamage = 1;
	private float attackDamage;
	private float attackSpeed;

	public BaseToolItem(Properties prop, RegistryMap map, float attackDamageIn, float attackSpeedIn) {
		super(prop, map);
		this.attackDamage = attackDamageIn;
		this.attackSpeed = attackSpeedIn;
	}

	protected void setHitDamage(int dmg) {
		this.hitDamage = dmg;
	}

	protected final void addDamage(int damage, ItemStack itemStack, LivingEntity player) {
		itemStack.damageItem(damage, player, entity -> {
			entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		addDamage(hitDamage, stack, attacker);
		return super.hitEntity(stack, target, attacker);
	}
}

