package mvf314.mvflib.tools;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT> {

	protected int maxConsume;

	public CustomEnergyStorage(int capacity) {
		this(capacity, capacity);
	}

	public CustomEnergyStorage(int capacity, int maxTransfer) {
		this(capacity, maxTransfer, maxTransfer);
	}

	public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract) {
		this(capacity, maxReceive, maxExtract, maxExtract);
	}

	public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract, int maxConsume) {
		super(capacity, maxReceive, maxExtract);
		this.maxConsume = maxConsume;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void addEnergy(int energy) {
		this.energy += Math.min(energy, maxReceive);
		if (this.energy > getMaxEnergyStored()) {
			this.energy = getMaxEnergyStored();
		}
	}

	public void consumeEnergy(int energy) {
		this.energy -= Math.min(energy, maxConsume);
		if (this.energy < 0) {
			this.energy = 0;
		}
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putInt("energy", getEnergyStored());
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		setEnergy(nbt.getInt("energy"));
	}
}
