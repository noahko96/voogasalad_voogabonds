package authoring.authoring_interfaces;

import java.util.Map;

/**
 * @author Christopher Lu
 * This interface provides the controller with access to the traits of the wave such as wave number, wave type, size of wave, etc.
 */

public interface IWave {
	
	/**
	 * Gives the number of the current wave.
	 */
	public int getWaveNumber();
	
	/**
	 * Gives a map of enemy types such as "air" or "ground" as the keys with the number of each of those types as
	 * the values which will be a wave that contains those values of air and ground units.
	 */
	public Map<String, Integer> getWaveEnemyNames();
	
	/**
	 * Gives the type of arrangement pattern the enemies will be in. For example, alternating, random, solid, etc.
	 */
	public String getWavePattern();
	
}
