package authoring.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class TowerData extends MachineData {

	/**
	 * Initial buying price for a tower. A positive number means this tower 
	 * can be bought without upgrading (essentially is a root). If buyPrice is -1 this 
	 *  means that this tower cannot be bought and must be upgraded to.
	 */
	private int myBuyPrice;
	
	/**
	 * Amount of money you get for selling.
	 */
	private int mySellPrice;
	
	/**
	 * Map of towers which this particular tower can upgrade from. The Integer is the cost of updating
	 * from the previous tower to this one.
	 */
	private Map<String, Integer> myUpgrades;
	
	private List<String> traversableTerrain;

	public TowerData() {
		myUpgrades = new HashMap<String, Integer>();
	}
	
	public void setBuyPrice(int buyPrice) {
		myBuyPrice = buyPrice;
	}
	public int getBuyPrice(){
		return myBuyPrice;
	}
	
	public void setSellPrice(int sellPrice) {
		mySellPrice = sellPrice;
	}
	public int getSellPrice(){
		return mySellPrice;
	}
	
	public void addUpgrades(String name, Integer upgradeCost){
		//this.upgradeFrom = upgrades;
		myUpgrades.put(name, upgradeCost);
	}
	
	public Map<String, Integer> getUpgrades(){
		return myUpgrades;
	}
	
	public void setTraversableTerrain(List<String> traversableTerrain)
	{
		this.traversableTerrain = traversableTerrain;
	}
	public List<String> getTraversableTerrain()
	{
		return traversableTerrain;
	}
	
	/*
	 * This doesn't work at the moment, partially because uninitialized doubles/ints default to 0.
	 * 
	 * 
	public boolean allFieldsFilled() throws IllegalAccessException {
		boolean result = true;
		
		for (Field f: getClass().getDeclaredFields()) {
			if (f.get(this) == null) {
				result = false;
				break;
			} else {
				//System.out.println("Here!");
			}
			//System.out.println(f.get(this).toString());
		}
	}
	
	public void setImagePath(String imagePath) throws Exception{
	    //System.out.println("image path: "+imagePath);
		if (!imagePath.endsWith(".png") || !(new File(imagePath).exists())){
			throw new Exception("Image file is invalid.");
		}
	}
	*/
}
