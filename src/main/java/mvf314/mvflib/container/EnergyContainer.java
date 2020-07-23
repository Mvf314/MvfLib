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

/**
 * The EnergyContainer is a container that supports Forge Energy
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.3
 */
public class EnergyContainer extends BaseContainer {

	/**
	 * Create an EnergyContainer with the given properties
	 * @param type      The ContainerType object
	 * @param id        Container id
	 * @param world     World object
	 * @param pos       Block position
	 * @param inventory Player inventory
	 * @param baseBlock Block the container links to
	 * @param name      Container name
	 */
	public EnergyContainer(@Nullable ContainerType<?> type, int id, World world, BlockPos pos, PlayerInventory inventory, BaseBlock baseBlock, String name) {
		super(type, id, world, pos, inventory, baseBlock, name);

		// Track the energy value
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

	/**
	 * Get the stored energy
	 * @return Energy stored
	 */
	public int getEnergy() {
		return te.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored)
				.orElse(0);
	}

	/**
	 * Initialize other capability handlers here (for example item handlers)
	 */
	@Override
	protected void initContainer() {

	}
}
