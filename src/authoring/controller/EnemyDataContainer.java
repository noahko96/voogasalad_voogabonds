package authoring.controller;
import authoring.model.EnemyData;
import engine.IObserver;
import engine.IObservable;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.ArrayList;

@Deprecated
public class EnemyDataContainer extends Container implements IObservable<Container>{
	private AbstractMap<String, EnemyData> myEnemyDataMap = new HashMap<String, EnemyData>();
	private transient ArrayList<IObserver<Container>> myListeners = new ArrayList<IObserver<Container>>();
	
	
	public AbstractMap<String, EnemyData> finalizeEnemyDataMap(){
		//TODO: Error checking to make sure that enemies at least exist
		return myEnemyDataMap;
	}
	
	public void createEnemyData(EnemyData enemy){
		myEnemyDataMap.put(enemy.getName(), enemy);
		notifyObservers();
	}
	
	public EnemyData getEnemyData(String enemyName){
		return myEnemyDataMap.get(enemyName);
	}
	
	public AbstractMap<String, EnemyData> getEnemyDataMap(){
		return myEnemyDataMap;
	}
	
	public void updateEnemyData(String originalName, EnemyData updatedEnemy){
		myEnemyDataMap.remove(originalName);
		createEnemyData(updatedEnemy);
		notifyObservers();
	}
	
	
	/**
	 * IObserver functions
	 */
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
