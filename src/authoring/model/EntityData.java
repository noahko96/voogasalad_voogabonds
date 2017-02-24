package authoring.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import authoring.model.serialization.JSONDeserializer;
import engine.model.components.concrete.PurchasableComponentData;
import engine.model.components.concrete.SellableComponentData;
import engine.model.components.concrete.UpgradableComponentData;

/**
 * @author owenchung and alanguo
 */

public class EntityData implements IReadableData {
	private String myName;
	private Map<String, ComponentData> myComponents;
//	Optional<UpgradableComponentData> myUpgradeData;
//	Optional<SellableComponentData> mySellData;
//	Optional<PurchasableComponentData> myPurchaseData;
	
	public EntityData()
	{
		myComponents = new HashMap<String, ComponentData>();
	}
	
	public String getName(){
		return myName;
	}
	
	public void setName(String s){
		this.myName = s;
	}
	
	
	public void addComponent(String aName, ComponentData comp){
		myComponents.put(aName, comp);
	}
	
	public Map<String, ComponentData> getComponents(){
		return myComponents;
	}

	public int getBuyPrice() {
		return myComponents.get("PurchasableComponentData") == null ? Integer.MAX_VALUE : Integer.parseInt(myComponents.get("PurchasableComponentData").getFields().get(("myPurchaseValue")));
//		return myPurchaseData.isPresent() ? myPurchaseData.get().getBuyPrice() : Integer.MAX_VALUE;
	}
	public int getSellPrice() {
		return myComponents.get("SellableComponentData") == null ? Integer.MAX_VALUE : Integer.parseInt(myComponents.get("SellableComponentData").getFields().get("mySellValue"));
	}

	public Map<String, Integer> getUpgrades() {
		return myComponents.get("UpgradeComponentData") == null ? new HashMap<String, Integer>() : deserialize(myComponents.get("UpgradeComponentData").getFields().get("myUpgradeMap"));
	}

	private Map<String, Integer> deserialize(String s) {
		JSONDeserializer des = new JSONDeserializer();
		Map<String, Integer> result = new HashMap<String, Integer>();
		result = (HashMap<String, Integer>) des.deserialize(s, HashMap.class);
		return result;
	}
	
	

}
