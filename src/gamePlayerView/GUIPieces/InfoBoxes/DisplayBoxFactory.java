package gamePlayerView.GUIPieces.InfoBoxes;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

/**
 * 
 * @author Guhan Muruganandam
 *
 */

public class DisplayBoxFactory {
	
	//private ResourceBundle mytext=ResourceBundle.getBundle("Resources/textfiles");
	private static final String PACKAGE_NAME = "gamePlayerView.GUIPieces.InfoBoxes";
	
	public DisplayBoxFactory(){
		
	}
	public InfoBox createBox(String type){
		InfoBox box = null;
		if(type.equals("cash")){
			box = new CashBox();
		}
		else if(type.equals("lives")){
			box = new LivesBox();
		}
		else if(type.equals("enemieskilled")){
			box = new EnemiesKilledBox();
		}
		return box;
	}
	public Object createBoxReflection(String type) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		String className =PACKAGE_NAME + "."+ type + "Box";
		return Class.forName(className).getConstructor().newInstance();
	}
	
}
