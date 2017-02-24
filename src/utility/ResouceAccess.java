package utility;

import java.util.ResourceBundle;

/**
 * A class to allow easy access to resource bundles throughout the project.
 * @author Weston
 *
 */
public class ResouceAccess {
	
	public static final String ERROR_BUNDLE_NAME = "resources/Error";
	public static final ResourceBundle ERROR_BUNDLE = ResourceBundle.getBundle(ERROR_BUNDLE_NAME);
	
	public static String getError(String key){
		
		return ERROR_BUNDLE.getString(key);
	}
	
}
