package authoring.model;

public class WaveData implements IReadableData {
/**
 * WaveData represents all the information needed for one wave of enemies at a time.
 * A game will have multiple Waves, and as a result, multiple WaveDatas.
 * @author philipfoo
 */
	/**
	 * A string which represents the name of the wave. To be used for access in the map
	 */
	private String name;
	
	/**
	 * A String corresponding to the enemy name, which should have an EnemyData map entry
	 */
	private String waveEntity;
	
	/**
	 * Time between each enemy (so they don't all show up at once) in milliseconds.
	 */
	private double timeBetweenEnemy;
	
	/**
	 * The amount of time before the next wave, in seconds.
	 */
	private double timeUntilNextWave;
	
	/**
	 * Number of enemies in this wave.
	 */
	private int numEnemies;
	
	/**
	 * A String corresponding to the spawn point name, which should have an entry in the map.
	 */
	private String spawnPointName;
	
	/**
	 * A String corresponding to the sink point name, which should have an entry in the map.
	 */
	private String sinkPointName;
	
	@Override
	public String getName(){
		return name;
	}
	public void setName(String name) throws Exception{
		if (name == null || name.length() == 0){
			throw new Exception("Wave name must be specified.");
		}
		this.name = name;
	}
	
	public String getWaveEntity() {
		return waveEntity;
	}
	public void setWaveEntity(String waveEnemy) throws Exception{
		if (waveEnemy == null || waveEnemy.length() == 0){
			throw new Exception("Enemy in wave must be specified.");
		}
		this.waveEntity = waveEnemy;
	}
	
	public double getTimeBetweenEntity() {
		return timeBetweenEnemy;
	}
	
	public void setTimeBetweenEnemy(String timeBetweenEnemy) throws Exception{
		double timeBetweenEnemyDouble = 0;
		try {
			timeBetweenEnemyDouble = Double.parseDouble(timeBetweenEnemy);
		} catch (Exception e){
			throw new Exception("Time between enemy must be a number.");
		}
		setTimeBetweenEnemy(timeBetweenEnemyDouble);
	}
	
	public void setTimeBetweenEnemy(double timeBetweenEnemy) throws Exception{
		if (timeBetweenEnemy < 0){
			throw new Exception("Time between enemy cannot be a negative number.");
		}
		this.timeBetweenEnemy = timeBetweenEnemy;
	}
	
	public double getTimeUntilNextWave() {
		return timeUntilNextWave;
	}
	
	public void setTimeForWave(String timeForWave) throws Exception{
		double timeForWaveDouble = 0;
		try {
			timeForWaveDouble = Double.parseDouble(timeForWave);
		} catch(Exception e){
			throw new Exception("Time between waves must be a number.");
		}
		setTimeForWave(timeForWaveDouble);
	}
	
	public void setTimeForWave(double timeBeforeWave) throws Exception{
		if (timeBeforeWave < 0){
			throw new Exception("Time between waves cannot be a negative number.");
		}
		this.timeUntilNextWave = timeBeforeWave;
	}
	
	public int getNumEntities(){
		return numEnemies;
	}
	
	public void setNumEnemies(String numEnemies) throws Exception{
		int numEnemiesInt = 0;
		try {
			numEnemiesInt = Integer.parseInt(numEnemies);
		} catch(Exception e){
			throw new Exception("Number of enemies must be an integer.");
		}
		setNumEnemies(numEnemiesInt);
	}
	
	public void setNumEnemies(int numEnemies) throws Exception{
		if (numEnemies < 0){
			throw new Exception("Number of enemies cannot be a negative number.");
		}
		this.numEnemies = numEnemies;
	}
	
	public String getSpawnPointName() {
		return spawnPointName;
	}
	public void setSpawnPointName(String spawnPointName) throws Exception{
		if (spawnPointName == null || spawnPointName.length() == 0){
			throw new Exception("Spawn point name must be specified.");
		}
		this.spawnPointName = spawnPointName;
	}
	
	public String getSinkPointName() {
		return sinkPointName;
	}
	public void setSinkPointName(String sinkPointName) throws Exception{
		if (sinkPointName == null || sinkPointName.length() == 0){
			throw new Exception("Sink point name must be specified.");
		}
		this.sinkPointName = sinkPointName;
	}
	
}
