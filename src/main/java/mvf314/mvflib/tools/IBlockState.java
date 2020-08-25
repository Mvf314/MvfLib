package mvf314.mvflib.tools;

/**
 * Implement this interface for things that have a block state
 * @author Mvf314
 * @version 0.0.5
 * @since 0.0.5
 */
public interface IBlockState {

	/**
	 * Override this to return the desired block state JSON String
	 * @param modid Mod ID
	 * @return JSON string
	 */
	public String getBlockState(String modid);
}
