package mvf314.mvflib.container;

import mvf314.mvflib.block.BaseBlock;
import mvf314.mvflib.tools.CustomEnergyStorage;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class EnergyContainer extends BaseContainer {
	public EnergyContainer(@Nullable ContainerType<?> type, int id, World world, BlockPos pos, PlayerInventory inventory, BaseBlock baseBlock, String name) {
		super(type, id, world, pos, inventory, baseBlock, name);
		trackInt(new IntReferenceHolder() {
			@Override
			public int get() {
				return getEnergy();
			}

			@Override
			public void set(int value) {
				te.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
					((CustomEnergyStorage) h).setEnergy(value);
				});
			}
		});
	}

	public int getEnergy() {
		return te.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored)
				.orElse(0);
	}

	@Override
	protected void initContainer() {

	}
}
