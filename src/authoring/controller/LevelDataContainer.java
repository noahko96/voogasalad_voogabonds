package authoring.controller;

import java.util.*;

import authoring.model.LevelData;
import authoring.model.WaveData;
import engine.IObservable;
import engine.IObserver;

public class LevelDataContainer extends Container implements IObservable<Container>{
	private transient ArrayList<IObserver<Container>> myObservers = new ArrayList<IObserver<Container>>();
	
	private List<LevelData> myLevelList;
	
	public LevelDataContainer()
	{
		myLevelList = new ArrayList<LevelData>();
	}
	
	/**
	 * 
	 * @param LevelData
	 * Adds new LevelData object to list of LevelData
	 */
	public void createNewLevelData(LevelData ld){
		for (LevelData level: myLevelList){
			if (ld.getLevelName().equals(level.getLevelName())){
				myLevelList.remove(level);
				break;
			}
		}
		myLevelList.add(ld);
		notifyObservers();
	}
	
	public void updateLevel(LevelData level, LevelData originalLevel){
		myLevelList.remove(originalLevel);
		myLevelList.add(level);
		notifyObservers();
	}
	
	public void removeLevelData(String levelName) throws Exception{
		for (LevelData level: myLevelList){
			if (level.getLevelName().equals(levelName)){
				myLevelList.remove(level);
				return;
			}
		}
		throw new Exception("Level not in list");
	}
	
	/**
	 *@param String: Level Name
	 *Returns specific level by input of its specified name 
	 */
	public LevelData getLevelData(String name){
		for (LevelData LD : myLevelList){
			if (LD.getLevelName().equals(name)){
				return LD;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return List<LevelData>
	 * Returns current list of all levels
	 */
	public List<LevelData> finalizeLevelDataMap(){
		return myLevelList;
	}

	/**
	 * get the level data corresponding to the specified level
	 * 
	 * This method assumes that the requested level exists
	 * 
	 * @param aLevel
	 * @return the level data at the specified index, if it exists
	 */
	public LevelData getLevelData(int aLevel) {
		return myLevelList.get(aLevel);
	}
	
	/**
	 * A method that returns whether a level at the specified index exists
	 * @param aLevel
	 * @return true if aLevel is within the level list
	 */
	public boolean hasLevel(int aLevel)
	{
		return aLevel < myLevelList.size();
	}

	@Override
	public void attach(IObserver<Container> observer) {
		myObservers.add(observer);
	}

	@Override
	public void detach(IObserver<Container> observer) {
		myObservers.remove(observer);	
	}

	@Override
	public void notifyObservers() {
		for (IObserver<Container> observer: myObservers){
			observer.update(this);
		}
	}

}
