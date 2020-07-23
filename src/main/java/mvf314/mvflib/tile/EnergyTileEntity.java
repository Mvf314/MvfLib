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

public abstract class EnergyTileEntity extends TickableTileEntity {
	protected LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(this::createEnergyHandler);

	public static int MAX_SEND;

	public EnergyTileEntity(TileEntityType<?> tile, int maxSend) {
		super(tile);
		MAX_SEND = maxSend;
	}

	@Override
	public abstract void tick();

	protected void sendOutPower() {
		energyHandler.ifPresent(energy -> {
			AtomicInteger capacity = new AtomicInteger(energy.getEnergyStored());
			if (capacity.get() > 0) {
				for (Direction direction : Direction.values()) {
					TileEntity te = world.getTileEntity(pos.offset(direction));
					if (te != null) {
						boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(energyHandler -> {
							if (energyHandler.canReceive()) {
								int received = energyHandler.receiveEnergy(Math.min(capacity.get(), MAX_SEND), false);
								capacity.addAndGet(-received);
								((CustomEnergyStorage) energy).consumeEnergy(received);
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

	public abstract void readNBT(CompoundNBT tag);

	public abstract void writeNBT(CompoundNBT tag);

	@Override
	public final void read(CompoundNBT tag) {
		CompoundNBT energyTag = tag.getCompound("energy");
		energyHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(energyTag));
		readNBT(tag);
		super.read(tag);
	}

	@Override
	public final CompoundNBT write(CompoundNBT tag) {
		energyHandler.ifPresent(h -> {
			CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
			tag.put("energy", compound);
		});
		writeNBT(tag);
		return super.write(tag);
	}

	private IEnergyStorage createEnergyHandler() {
		return new CustomEnergyStorage(MAX_SEND, 0);
	}

	protected abstract <T> LazyOptional<T> getOtherCapabilities(@Nonnull Capability<T> cap, @Nullable Direction side);

	@Nonnull
	@Override
	public final<T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityEnergy.ENERGY) {
			return energyHandler.cast();
		}
		return getOtherCapabilities(cap, side);
	}
}
