package authoring.model.serialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Point;
import javafx.geometry.Point2D;
import authoring.model.ProjectileData;
import authoring.model.TowerData;
import authoring.model.WeaponData;

public class SerializationTester {
	
	private String fileName;
	private String fileLoc = "src/SerializedFiles/";
	
	@SuppressWarnings("unchecked")
	public void Tester(Object obj) throws Exception{
//		fileName = "test.txt";
		List<Map<Integer, Integer>> list = new ArrayList<Map<Integer, Integer>>();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(100, 10);
		map.put(20, 50);
		list.add(map);
		TowerData tow = new TowerData();
		tow.setBuyPrice(40);
		tow.setCollisionRadius(2);
		tow.setImagePath("src/resources/boss.png");
//		tow.setInitialLocations(new Point(2.0, 4.0));
		tow.setMaxHealth(1000);
		tow.setMoveStrategyName("fast as fuck");
		tow.setName("tower 1");
		tow.setSellPrice(100);
		
		obj = tow;
		JSONSerializer ser = new JSONSerializer();
		JSONDeserializer des = new JSONDeserializer();
		// We should make a user prompt for when they want to save a game file, which will be set as fileName
		List<List<Object>> mySerializables = new ArrayList<List<Object>>();
		List<TowerData> towerList = new ArrayList<TowerData>();
		towerList.add(tow);
		towerList.add(new TowerData());
		List<WeaponData> weaponList = new ArrayList<WeaponData>();
		weaponList.add(new WeaponData());
		List<ProjectileData> projectileList = new ArrayList<ProjectileData>();
		projectileList.add(new ProjectileData());
		
		mySerializables.addAll((Collection<? extends List<Object>>) towerList);
		
		mySerializables.addAll((Collection<? extends List<Object>>) weaponList);
		
		mySerializables.addAll((Collection<? extends List<Object>>) projectileList);
		
		List<String> li = new ArrayList<String>();
		li.add("ActualTower");
		li.add("EmptyTower");
		li.add("EmptyWeapon");
		li.add("EmptyProjectile");
		for (int i = 0; i<mySerializables.size(); i++){
			ser.serializeToFile(mySerializables.get(i), li.get(i));// mySerializables.get(i)+"");
			fileName = mySerializables.get(i)+"";
//			des.DeserializeFromFile(fileName);
		}

//		SerializeJSON ser = new SerializeJSON();
//		DeserializeJSON des = new DeserializeJSON();
//		ser.SerializeToFile(new TowerData(), "Test.txt");
//		Object obj = des.DeserializeFromFile("Test.txt", TowerData.class);
//		//System.out.println("TESTING");
//		//System.out.println(obj.toString());
//		TowerData d =  (TowerData) obj;
//		//System.out.println(d.getBuyPrice());
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}

}
