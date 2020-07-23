package mvf314.mvflib.tile;

import mvf314.mvflib.tools.CustomEnergyStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Tile Entity that supports the usage of Forge Energy
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.3
 */
public abstract class EnergyTileEntity extends TickableTileEntity {
	/**
	 * The energy handler
	 */
	protected LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(this::createEnergyHandler);

	/**
	 * Maximum power sent per tick
	 */
	public static int MAX_SEND;
	/**
	 * Maximum power received per tick
	 */
	public static int MAX_RECEIVE;
	/**
	 * Power capacity
	 */
	public static int CAPACITY;

	/**
	 * Create tile entity
	 * @param tile      Tile entity
	 * @param capacity  Capacity
	 * @param maxSend   Maximum energy send per tick
	 * @param maxReceive Maximum energy receive per tick
	 */
	public EnergyTileEntity(TileEntityType<?> tile, int capacity, int maxSend, int maxReceive) {
		super(tile);
		MAX_SEND = maxSend;
		MAX_RECEIVE = maxReceive;
		CAPACITY = capacity;
	}

	/**
	 * Tick tile entity
	 */
	@Override
	public abstract void tick();

	/**
	 * Send power to neighbots
	 */
	protected void sendOutPower() {
		energyHandler.ifPresent(energy -> {
			AtomicInteger capacity = new AtomicInteger(energy.getEnergyStored());
			// If there is energy stored
			if (capacity.get() > 0) {
				// for all directions
				for (Direction direction : Direction.values()) {
					TileEntity te = world.getTileEntity(pos.offset(direction));
					// if neighbor is a tile entity
					if (te != null) {
						boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(energyHandler -> {
							// If neigbor can receive energy
							if (energyHandler.canReceive()) {
								// transfer energy
								int received = energyHandler.receiveEnergy(Math.min(capacity.get(), MAX_SEND), false);
								capacity.addAndGet(-received);
								((CustomEnergyStorage) energy).consumeEnergy(received);
								// data changed
								markDirty();
								return capacity.get() > 0;
							} else {
								return true;
							}
						}).orElse(true);
						if (!doContinue) {
							return;
						}
					}
				}
			}
		});
	}

	/**
	 * Read other NBT data here
	 * @param tag NBT tag
	 */
	public abstract void readNBT(CompoundNBT tag);

	/**
	 * Write other NBT data here
	 * @param tag NBT tag
	 */
	public abstract void writeNBT(CompoundNBT tag);

	/**
	 * Read energy from NBT
	 * @param tag NBT tag
	 */
	@Override
	public final void read(CompoundNBT tag) {
		CompoundNBT energyTag = tag.getCompound("energy");
		energyHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(energyTag));
		readNBT(tag);
		super.read(tag);
	}

	/**
	 * Write energy to NBT
	 * @param tag NBT tag
	 * @return The written NBT tag
	 */
	@Override
	public final CompoundNBT write(CompoundNBT tag) {
		energyHandler.ifPresent(h -> {
			CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
			tag.put("energy", compound);
		});
		writeNBT(tag);
		return super.write(tag);
	}

	/**
	 * Initialize energy handler
	 * @return Energy Handler
	 */
	private IEnergyStorage createEnergyHandler() {
		return new CustomEnergyStorage(CAPACITY, MAX_RECEIVE, MAX_SEND);
	}

	/**
	 * Put other capabilities here
	 * @param cap   Capability
	 * @param side  Side
	 * @param <T>   Capability type
	 * @return  Casted capability
	 */
	protected abstract <T> LazyOptional<T> getOtherCapabilities(@Nonnull Capability<T> cap, @Nullable Direction side);

	/**
	 * Add energy capability support
	 * @param cap   Capability
	 * @param side  Side
	 * @param <T>   Capability type
	 * @return  Casted capability
	 */
	@Nonnull
	@Override
	public final<T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityEnergy.ENERGY) {
			return energyHandler.cast();
		}
		return getOtherCapabilities(cap, side);
	}
}
