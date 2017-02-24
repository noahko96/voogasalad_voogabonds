package engine.model.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.model.EntityData;
import engine.model.resourcestore.IMoney;
import engine.model.resourcestore.Money;
/**
 * 
 * @author owenchung
 * @author matthewfaw
 */
public class EntityNode {
	private EntityData myEntityData;

	private String myName;
	private IMoney myBuyPrice;
	private IMoney mySellPrice;
	private int myMaxHealth;
	private List<EntityNode> myUpgradeFroms;
	private List<EntityNode> myUpgradeTos;
	private Map<EntityNode, IMoney> myUpgradeCostMap;
	
	public EntityNode(EntityData aEntityData) {
		myEntityData = aEntityData;
		initData(aEntityData);
		
		myUpgradeFroms = new ArrayList<EntityNode>();
		myUpgradeTos = new ArrayList<EntityNode>();
		myUpgradeCostMap = new HashMap<EntityNode, IMoney>();
	}
	
	private void initData(EntityData aEntityData) {
		myName = aEntityData.getName();
		myBuyPrice = new Money(aEntityData.getBuyPrice());
		mySellPrice = new Money(aEntityData.getSellPrice());
	}
	
	public EntityData getEntityData()
	{
		return myEntityData;
	}

	public List<EntityNode> getUpgradeTos(){
		return myUpgradeTos;
	}
	
	public List<EntityNode> getUpgradeFroms(){
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
	
	public void addUpgradeTo(EntityNode aNode)
	{
		/*
		myUpgradeTos.add(aNode);
		int upgradeCost = myEntityData.getUpgrades().get(aNode.getName());
		myUpgradeCostMap.put(aNode, new Money(upgradeCost));
		aNode.addUpgradeFrom(this);
		*/
	}

	private void addUpgradeFrom(EntityNode aNode)
	{
		myUpgradeFroms.add(aNode);
	}
}
