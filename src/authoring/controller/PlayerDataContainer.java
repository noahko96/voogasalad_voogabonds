package authoring.controller;
import authoring.model.PlayerData;

public class PlayerDataContainer{
	private PlayerData myPlayerData = new PlayerData();
	
	
	public PlayerData getPlayerData(){
		return myPlayerData;
	}
	
	/**
	 * Might try to abstract this out to an interface
	 */
	public void finalizeData() throws Exception{
		if (myPlayerData.getStartingCash() == 0){
			throw new Exception("Player's starting cash is 0 or unspecified.");
		}
		if (myPlayerData.getStartingLives() == 0){
			throw new Exception("Player's starting lives is 0 or unspecified");
		}
		if (myPlayerData.getWinStrategyName() == null){
			throw new Exception("Player has an unspecified winning strategy.");
		}
		if (myPlayerData.getLoseStrategyName() == null){
			throw new Exception("Player has an unspecified losing strategy.");
		}
	}
}
