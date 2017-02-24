package engine.model.data_stores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import authoring.model.IReadableData;

/**
 * A class that encapulates the data for all available objects of type T
 * Provides methods for retrieving a data object
 * @author matthewfaw
 *
 *@param T: any class that implements the IReadableData interface
 */
public class DataStore<T extends IReadableData> {
	private HashMap<String, T> myData;

	public DataStore(List<T> aWeaponDataList) {
		myData = new HashMap<String, T>();
		aWeaponDataList.forEach(e -> myData.put(e.getName(), e));
	}
	
	public T getData(String aWeaponName) {
		if (myData.containsKey(aWeaponName)) {
			return myData.get(aWeaponName);
		} else {
			return null;
		}
	}
	
	public boolean hasKey(String aWeaponName) {
		return myData.containsKey(aWeaponName);
	}
	
	public T getRandom() {
		List<T> values = new ArrayList<T>(myData.values());
		Random r = new Random();
		
		return values.get(r.nextInt(values.size()));
	}
}