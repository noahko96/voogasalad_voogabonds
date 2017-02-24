package authoring.controller;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.ArrayList;

import authoring.model.EnemyData;
import authoring.model.WeaponData;
import engine.IObservable;
import engine.IObserver;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

@Deprecated
public class WeaponDataContainer extends Container implements IObservable<Container>{

	private AbstractMap<String, WeaponData> myWeaponDataMap = new HashMap<String, WeaponData>();
	private transient ArrayList<IObserver<Container>> myListeners = new ArrayList<IObserver<Container>>();

	public AbstractMap<String, WeaponData> finalizeWeaponDataMap(){
		//TODO: Error checking to make sure that enemies at least exist
		return myWeaponDataMap;
	}
	
	public void createWeaponData(WeaponData weaponData){
		myWeaponDataMap.put(weaponData.getName(), weaponData);
		notifyObservers();
	}
	
	public WeaponData getWeaponData(String weaponName){
		return myWeaponDataMap.get(weaponName);
	}
	
	public AbstractMap<String, WeaponData> getWeaponDataMap(){
		return myWeaponDataMap;
	}
	
	public void updateWeaponData(String originalName, WeaponData updatedWeapon){
		myWeaponDataMap.remove(originalName);
		createWeaponData(updatedWeapon);
		notifyObservers();
	}
	
	public void attach(IObserver<Container> listener){
		myListeners.add(listener);
	}
	
	public void detach(IObserver<Container> listener){
		myListeners.remove(listener);
	}
	
	public void notifyObservers(){
		for (IObserver<Container> observer: myListeners){
			observer.update(this);
		}
	}
	
	
}