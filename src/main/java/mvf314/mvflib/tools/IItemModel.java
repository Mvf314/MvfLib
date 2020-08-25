package mvf314.mvflib.tools;

/**
 * Implement this interface for things that have an item model
 * @author Mvf314
 * @version 0.0.5
 * @since 0.0.5
 */
public interface IItemModel {

	/**
	 * Override this to return the desired item model JSON String
	 * @param modid Mod ID
	 * @return JSON string
	 */
	public String getItemModel(String modid);

}
