package authoring.model;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author owenchung and alanguo
 *
 */

public class ComponentData {
	private Map<String, String> myFields;
	private String myComponentName;
	
	public ComponentData()
	{
		myFields = new HashMap<String, String>();
	}
	
	public void addField(String fieldName, String fieldValue){
		myFields.put(fieldName, fieldValue);
	}

	public String getComponentName() {
		return myComponentName;
	}

	public void setComponentName(String myComponentName) {
		this.myComponentName = myComponentName;
	}

	public Map<String, String> getFields() {
	    return myFields;
	}
}
