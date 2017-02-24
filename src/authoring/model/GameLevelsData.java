package authoring.model;

import java.util.ArrayList;
import java.util.List;
/**
 * This class wraps different levels of waves
 * @author owenchung
 *
 */
public class GameLevelsData {
	
	private List<OneLevelData> wavesInLevel;
	
	public GameLevelsData() {
		wavesInLevel = new ArrayList<OneLevelData>();
	}
	
	public void addGameLevels(OneLevelData aLevelData) {
		wavesInLevel.add(aLevelData);
	}
	
	public OneLevelData getLevelData(int currLevel) {
		if (wavesInLevel.contains(currLevel)) {
			return wavesInLevel.get(currLevel);
		} else {
			return null;
		}
	}
	
}
