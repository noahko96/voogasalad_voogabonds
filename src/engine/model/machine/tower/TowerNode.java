package engine.model.machine.tower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.model.TowerData;
import engine.model.resourcestore.IMoney;
import engine.model.resourcestore.Money;

@Deprecated
public class TowerNode {
	private TowerData myTowerData;

	private String myName;
	private IMoney myBuyPrice;
	private IMoney mySellPrice;
	private int myMaxHealth;
	private List<TowerNode> myUpgradeFroms;
	private List<TowerNode> myUpgradeTos;
	private Map<TowerNode, IMoney> myUpgradeCostMap;
	
	public TowerNode(TowerData towerData) {
		myTowerData = towerData;
		initData(towerData);
		
		myUpgradeFroms = new ArrayList<TowerNode>();
		myUpgradeTos = new ArrayList<TowerNode>();
		myUpgradeCostMap = new HashMap<TowerNode, IMoney>();
	}
	
	private void initData(TowerData towerdata) {
		myName = towerdata.getName();
		myBuyPrice = new Money(towerdata.getBuyPrice());
		mySellPrice = new Money(towerdata.getSellPrice());
	}
	
	public TowerData getTowerData()
	{
		return myTowerData;
	}

	public List<TowerNode> getUpgradeTos(){
		return myUpgradeTos;
	}
	
	public List<TowerNode> getUpgradeFroms(){
		return myUpgradeFroms;
	}
	
	public String getName(){
		return myName;
	}
	
	public IMoney getBuyPrice(){
		return myBuyPrice;
	}
	
	public IMoney getSellPrice(){
		return mySellPrice;
	}
	
	public int getMaxHealth(){
		return myMaxHealth;
	}

	
	public void setSellPrice(int sellPrice) {
		mySellPrice = new Money(sellPrice);
	}
	
	public void setPrice(int price) {
		myBuyPrice = new Money(price);
	}
	
	public void addUpgradeTo(TowerNode aNode)
	{
		myUpgradeTos.add(aNode);
		int upgradeCost = myTowerData.getUpgrades().get(aNode.getName());
		myUpgradeCostMap.put(aNode, new Money(upgradeCost));
		aNode.addUpgradeFrom(this);
	}

	private void addUpgradeFrom(TowerNode aNode)
	{
		myUpgradeFroms.add(aNode);
	}
}
