package mvf314.mvflib.tools;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

/**
 * Custom Energy storage for Forge Energy
 * @author Mvf314
 * @version 0.0.3
 * @since 0.0.3
 */
public class CustomEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT> {

	/**
	 * Max power to consume per tick
	 */
	protected int maxConsume;

	/**
	 * Constructor
	 * @param capacity  Capacity
	 */
	public CustomEnergyStorage(int capacity) {
		this(capacity, capacity);
	}

	/**
	 * Constructor
	 * @param capacity      Capacity
	 * @param maxTransfer   Maximum transfer per tick
	 */
	public CustomEnergyStorage(int capacity, int maxTransfer) {
		this(capacity, maxTransfer, maxTransfer);
	}

	/**
	 * Constructor
	 * @param capacity      Capacity
	 * @param maxReceive    Maximum input per tick
	 * @param maxExtract    Maximum output per tick
	 */
	public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract) {
		this(capacity, maxReceive, maxExtract, maxExtract);
	}

	/**
	 * Full constructor
	 * @param capacity      Capacity
	 * @param maxReceive    Maximum input per tick
	 * @param maxExtract    Maximum output per tick
	 * @param maxConsume    Maximum consumed per tick
	 */
	public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract, int maxConsume) {
		super(capacity, maxReceive, maxExtract);
		this.maxConsume = maxConsume;
	}

	/**
	 * Set energy
	 * @param energy Energy
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
	}

	/**
	 * Add energy, with the given constraints
	 * @param energy Energy to try to add
	 */
	public void addEnergy(int energy) {
		this.energy += Math.min(energy, maxReceive);
		if (this.energy > getMaxEnergyStored()) {
			this.energy = getMaxEnergyStored();
		}
	}

	/**
	 * Consume energy with the given constraints
	 * @param energy Energy to try to consume
	 */
	public void consumeEnergy(int energy) {
		this.energy -= Math.min(energy, maxConsume);
		if (this.energy < 0) {
			this.energy = 0;
		}
	}

	/**
	 * Serialize energy NBT
	 * @return Serialized NBT Tag
	 */
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putInt("energy", getEnergyStored());
		return tag;
	}

	/**
	 * Deserialize energy NBT
	 * @param nbt NBT tag to deserialize
	 */
	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		setEnergy(nbt.getInt("energy"));
	}
}
