package engine.controller.waves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import authoring.controller.LevelDataContainer;
import authoring.model.EntityData;
import authoring.model.GameLevelsData;
import authoring.model.LevelData;
import authoring.model.OneLevelData;
import authoring.model.WaveData;
import engine.model.data_stores.DataStore;

/**
 * A class intended to manage which waves are currently active
 * Provides a simple interface to retrieve which Enemies to be constructed
 * at a given time step 
 * @author matthewfaw 
 * @author owenchung
 *
 */
public class ActiveWaveManager {
	private LevelData myLevelData;
	private List<WaveData> myWaveDataList;
	private DataStore<EntityData> myEntityDataStore;
	private List<WaveState> myWaveStates;
	private double myCurrentTime;
	private double myTimeToAddMoreWaves;
//	private LinkedHashMap<WaveData, Integer> myUnreleasedEnemyCountForActiveWave;

	
	public ActiveWaveManager(DataStore<EntityData> aEntityDataStore, LevelData aLevelData, double startTime)
	{
		myEntityDataStore = aEntityDataStore;
		myLevelData = aLevelData;
		myWaveDataList = myLevelData.getWaveDataList();
//		myUnreleasedEnemyCountForActiveWave = new LinkedHashMap<WaveData, Integer>();
		myWaveStates = new ArrayList<WaveState>();
		setCurrentTime(startTime);
		setNextRoundOfWaveDataAsActive();
	}
	
	/**
	 * A method that returns the Enemies to construct, given a current time
	 * @param aTotalTimeElapsed
	 * @return a map from enemy data to the spawn point corresponding to that enemy
	 * 
	 * TODO: This return type is kinda hacky... maybe make a custom class for this?
	 */
	public List<PathFollowerData> getEntitiesToConstruct(double aTotalTimeElapsed)
	{
		//1. Update the current time
		setCurrentTime(aTotalTimeElapsed);

		//2. dispatch next waves, if it's time
		if (isTimeToAddMoreWaves()) {
			setNextRoundOfWaveDataAsActive();
		}
		
		//3. get all the entities
		List<PathFollowerData> enemiesToConstruct = new ArrayList<PathFollowerData>();

		for (Iterator<WaveState> iterator = myWaveStates.iterator(); iterator.hasNext();) {
			WaveState activeWave = iterator.next();
			if (activeWave.canReleaseEnemy(aTotalTimeElapsed)) {
				EntityData enemy = myEntityDataStore.getData(activeWave.releaseWaveEntity(aTotalTimeElapsed));
				enemiesToConstruct.add(new PathFollowerData(enemy, activeWave.getSpawnPointName(), activeWave.getSinkPointName()));
			} else if (!activeWave.hasEnemiesToRelease()){
				iterator.remove();
//				myWaveStates.remove(activeWave);
			}
		}
		
		return enemiesToConstruct;
	}

	/**
	 * A method to add the next round of wave data to be set as active
	 * Assumes that multiple waves can be active at the same time
	 */
	private void setNextRoundOfWaveDataAsActive() {
		while (!myWaveDataList.isEmpty()) {
			WaveData waveData = myWaveDataList.remove(0);
			myWaveStates.add(new WaveState(waveData, myCurrentTime));
			if (waveData.getTimeUntilNextWave() != 0) {
				updateTimeUntilNextTransition(waveData.getTimeUntilNextWave());
				break;
			}
		}
	}
	
	/**
	 * A method to determine if we can set more waves as active
	 * @return true if we can, false otherwise
	 */
	private boolean isTimeToAddMoreWaves()
	{
		return myCurrentTime > myTimeToAddMoreWaves;
	}
	
	/**
	 * Sets the current time to the input value
	 * Note that the time is specified in milliseconds
	 * @param aTotalTimeElapsed: a new current time
	 */
	private void setCurrentTime(double aTotalTimeElapsed)
	{
		myCurrentTime = aTotalTimeElapsed;
	}
	/**
	 * sets the time until the next wave is to be added, relative
	 * to the current time
	 * @param aDeltaTime: a wave duration
	 */
	private void updateTimeUntilNextTransition(double aDeltaTime)
	{
		myTimeToAddMoreWaves = myCurrentTime + aDeltaTime;
	}

	public boolean hasEnemiesToRelease() {
		for (WaveState ws : myWaveStates) {
			if (ws.hasEnemiesToRelease()) {
				return true;
			}
		}
		return false;
	}
}
