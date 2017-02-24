package authoring.model;

/**
 * Class representing the overall information for a player.
 * @author philipfoo
 */

public class PlayerData implements IReadableData{
	private String myName;

	private int startingLives;
	private int startingCash;
	private String winStrategyName;
	private String loseStrategyName;
	
	@Override
	public String getName()
	{
		return myName;
	}
	
	public int getStartingLives() {
		return startingLives;
	}
	
	public void setStartingLives(String startingLives) throws Exception{
		int startingLivesInt = 0;
		try {
			startingLivesInt = Integer.parseInt(startingLives);
		} catch(Exception e){
			throw new Exception("Starting lives must be an integer.");
		}
		setStartingLives(startingLivesInt);
	}
	
	public void setStartingLives(int startingLives) throws Exception{
		if (startingLives <= 0){
			throw new Exception("Starting lives must be a positive number.");
		}
		this.startingLives = startingLives;
	}
	
	public int getStartingCash() {
		return startingCash;
	}
	
	public void setStartingCash(String startingCash) throws Exception{
		int startingCashInt = 0;
		try {
			startingCashInt = Integer.parseInt(startingCash);
		} catch(Exception e){
			throw new Exception("Starting cash must be an integer.");
		}
		setStartingCash(startingCashInt);
	}
	
	public void setStartingCash(int startingCash) throws Exception{
		if (startingCash <= 0){
			throw new Exception("Starting cash must be a positive number.");
		}
		this.startingCash = startingCash;
	}
	
	public String getWinStrategyName() {
		return winStrategyName;
	}
	public void setWinStrategyName(String winStrategyName) throws Exception{
		if (winStrategyName.equals("") || winStrategyName == null){
			throw new Exception("User must define a winning strategy.");
		}
		this.winStrategyName = winStrategyName;
	}
	
	public String getLoseStrategyName() {
		return loseStrategyName;
	}
	public void setLoseStrategyName(String loseStrategyName) throws Exception{
		if (loseStrategyName.equals("") || loseStrategyName == null){
			throw new Exception("User must define a losing strategy.");
		}
		this.loseStrategyName = loseStrategyName;
	}
	
	
}
