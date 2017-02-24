package engine.controller.waves;

import authoring.model.WaveData;

//TODO: move to the model
/**
 * A class intended to store the states of waves and WaveData 
 * @author matthewfaw
 *
 */
public class WaveState {
	private WaveData myWaveData;
	private int myUnreleasedEnemyCount;
	private double myTimeSinceLastEnemyCreation;
	
	/**
	 * @param aWaveData
	 * @param aCurrentTime in milliseconds
	 */
	public WaveState(WaveData aWaveData, double aCurrentTime)
	{
		myWaveData = aWaveData;
		myUnreleasedEnemyCount = myWaveData.getNumEntities();
		myTimeSinceLastEnemyCreation = aCurrentTime;
	}

	/**
	 * A method to determine if an enemy can be released
	 * @param aTotalElapsedTime
	 * @return true if there are enough enemies to be released 
	 * in the wave and the time between releases is sufficient 
	 */
	public boolean canReleaseEnemy(double aTotalElapsedTime)
	{
		return hasEnemiesToRelease() && creationTimeoutHasBeenSatisfied(aTotalElapsedTime);
	}
	
	/**
	 * A method to get the string of the enemy to create
	 * When this method is called, the total enemy count of the
	 * wave is decremented by 1, and the time of creation is updated
	 * @param aTotalElapsedTime
	 * @return the name of the enemy to be created
	 */
	public String releaseWaveEntity(double aTotalElapsedTime)
	{
		myUnreleasedEnemyCount -= 1;
		myTimeSinceLastEnemyCreation = aTotalElapsedTime;
		return myWaveData.getWaveEntity();
	}

	/**
	 * @return the name of the spawn point at which the enemy will be placed
	 */
	public String getSpawnPointName()
	{
		return myWaveData.getSpawnPointName();
	}
	
	public String getSinkPointName() {
		return myWaveData.getSinkPointName();
	}

	/**
	 * Determines if there are enemies that can be released in the wave
	 * @return true if there are, false otherwise
	 */
	public boolean hasEnemiesToRelease()
	{
		return myUnreleasedEnemyCount > 0;
	}

	/**
	 * determines if the time between enemy creations is sufficient
	 * @param aTotalElapsedTime
	 * @return true if the time is sufficient, false otherwise
	 */
	private boolean creationTimeoutHasBeenSatisfied(double aTotalElapsedTime)
	{
		double minimumValidTime = myTimeSinceLastEnemyCreation + myWaveData.getTimeBetweenEntity();
		return aTotalElapsedTime > minimumValidTime;
	}
}
