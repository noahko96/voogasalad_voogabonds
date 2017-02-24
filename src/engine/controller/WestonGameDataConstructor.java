package engine.controller;

import java.util.ArrayList;
import authoring.controller.LevelDataContainer;
import authoring.controller.MapDataContainer;
import authoring.model.ComponentData;
import authoring.model.EntityData;
import authoring.model.LevelData;
import authoring.model.PlayerData;
import authoring.model.WaveData;
import authoring.model.map.TerrainData;
import authoring.model.serialization.JSONDeserializer;
import authoring.model.serialization.JSONSerializer;
import engine.exceptions.SerializationException;
import engine.model.game_environment.terrain.TerrainMap;
import utility.Point;
import utility.ResouceAccess;

/**
 * A class to construct some mock game data for testing
 * This class contains examples of how to serialize and deserialize objects using Gson
 * @author matthewfaw
 *
 */
public class WestonGameDataConstructor {
	public WestonGameDataConstructor()
	{
	}
	
	MapDataContainer getMockMapData()
	{
		MapDataContainer mapData = new MapDataContainer();
		int x = 14;
		int y = 12;
		int xmin = x/3;
		int xmax = 2*x/3;
		int ymax = y/2;
		mapData.setDimensions(x,y);
		for (int i=0; i<x; ++i) {
			for (int j=0; j<y; ++j) {
				TerrainData terrain;
				if (i > xmin && i < xmax && j < ymax) {
					terrain = new TerrainData("water", i, j, 50, "0x0000ff");
				} else {
					terrain = new TerrainData("grass", i, j, 50, "0x008000");
				}
				mapData.addTerrainData(terrain);
			}
		}
		ArrayList<Point> spawnPoints = new ArrayList<Point>();
		spawnPoints.add(new Point(1, 1));
		mapData.addSpawnPoints("spawnPoint", spawnPoints);
		
		ArrayList<Point> sinkPoints = new ArrayList<Point>();
		sinkPoints.add(new Point(4, 4));
		mapData.addSinkPoints("sinkPoint", sinkPoints);
		
		
		mapData.cellSize(50);
//			mapData.addValidTerrain(terrain1.getName(), "exampleColor 1");
//			mapData.addValidTerrain(terrain2.getName(), "exampleColor 2");

		return mapData;
	}
	
	public void constructMockData() throws SerializationException
	{
		JSONDeserializer derp = new JSONDeserializer(); 
		
//		TowerData data = (TowerData)derp.DeserializeFromFile("towers/ActualTower", TowerData.class);
//		//System.out.println(data.getBuyPrice());

		JSONSerializer ser = new JSONSerializer();
		
		try {
			
			//Map data
			MapDataContainer md = this.getMockMapData();
			
			//Sell/Buy data
			ComponentData pcd = new ComponentData();
			pcd.setComponentName("PurchasableComponentData");
			pcd.addField("myPurchaseValue", "50");
			
			ComponentData scd = new ComponentData();
			scd.setComponentName("SellableComponentData");
			scd.addField("mySellValue", "50");
			
			// Entity data
			EntityData ed  = new EntityData();
			
			ed.addComponent("PurchasableComponentData", pcd);
			ed.addComponent("SellableComponentData", scd);
			
			ed.setName("Awesome Tower1");
			ComponentData cd1 = new ComponentData();
			cd1.setComponentName("PhysicalComponent");
			cd1.addField("myHeading", "0");
			cd1.addField("myImagePath", "src/resources/cookie.png");
			cd1.addField("myImageSize", "50");
			cd1.addField("myValidTerrains", "grass, ice, steel, dark, fairy");
			
			ComponentData cd2 = new ComponentData();
			cd2.setComponentName("CollidableComponent");
			cd2.addField("myCollisionRadius", "50");

			ComponentData cd3 = new ComponentData();
			cd3.setComponentName("MoveableComponent");
			cd3.addField("myCollisionRadius", "50");
			cd3.addField("myTurnSpeed", "1");
			cd3.addField("myMoveSpeed", "1");
			cd3.addField("myMaxDistance", "1000");
			cd3.addField("myMovementCalc", "GreedyMovementStrategy");
			cd3.addField("removeOnGoal", "true");
			
	
			ComponentData cd4 = new ComponentData();
			cd4.setComponentName("DamageDealingComponent");
			cd4.addField("myDamage", "50");
			cd4.addField("myDamageRadius", "2");
			cd4.addField("myDamageCalc", "BinaryDamageStrategy");
			
			ComponentData cd5 = new ComponentData();
			cd5.setComponentName("CreatorComponent");
			cd5.addField("mySpawningStrategy", "BasicSpawnStrategy");
			cd5.addField("myTimeBetweenSpawns", "10");
			cd5.addField("mySpawnName", "Awesome Tower2");
			
			
			ed.addComponent("PhysicalComponent",cd1);
			ed.addComponent("CollidableComponent",cd2);
			ed.addComponent("MoveableComponent",cd3);
			ed.addComponent("CreatorComponent",cd5);
			
			// 2nd entity data
			EntityData ed2  = new EntityData();
			
			ed2.addComponent("PurchasableComponentData", pcd);
			ed2.addComponent("SellableComponentData", scd);
			
			ed2.setName("Awesome Tower2");
			ComponentData CD1 = new ComponentData();
			CD1.setComponentName("PhysicalComponent");
			CD1.addField("myHeading", "0");
			CD1.addField("myImagePath", "src/resources/cow.png");
			CD1.addField("myImageSize", "50");
			CD1.addField("myValidTerrains", "grass, ice, steel, dark");
			
			ComponentData CD2 = new ComponentData();
			CD2.setComponentName("CollidableComponent");
			CD2.addField("myCollisionRadius", "50");
			
			ComponentData CD3 = new ComponentData();
			CD3.setComponentName("HealthComponent");
			CD3.addField("myCurrHealth", "9000");
			CD3.addField("myMaxHealth", "9000");
			
			ed2.addComponent("PhysicalComponent",CD1);
			//ed2.addComponent("CollidableComponent",CD2);
			ed2.addComponent("HealthComponent",CD3);
			
			// Player Data
			PlayerData pdd = new PlayerData();
			pdd.setLoseStrategyName("lose strategy 1");
			pdd.setStartingCash(2000);
			pdd.setWinStrategyName("win strategy 1");
			pdd.setStartingLives(5);
			
			//Level data
			WaveData wad1 = new WaveData();
			wad1.setName("Cool wave");
			wad1.setNumEnemies(1);
			wad1.setSpawnPointName("spawnPoint");
			wad1.setSinkPointName("sinkPoint");
			wad1.setTimeBetweenEnemy(20);
			wad1.setTimeForWave(0);
			wad1.setWaveEntity("Awesome Tower1");
			WaveData wad2 = new WaveData();
			wad2.setName("Awesome wave");
			wad2.setNumEnemies(20);
			wad2.setSpawnPointName("spawnPoint");
			wad2.setSinkPointName("sinkPoint");
			wad2.setTimeBetweenEnemy(50);
			wad2.setTimeForWave(1);
			wad2.setWaveEntity("Awesome Tower1");
			WaveData wad3 = new WaveData();
			wad3.setName("Dumb wave");
			wad3.setNumEnemies(30);
			wad3.setSpawnPointName("spawnPoint");
			wad3.setSinkPointName("sinkPoint");
			wad3.setTimeBetweenEnemy(60);
			wad3.setTimeForWave(6);
			wad3.setWaveEntity("Awesome Tower2");
			LevelData ld = new LevelData();
			ld.addWaveDataListToList(wad1);
			//ld.addWaveDataListToList(wad2);
			//ld.addWaveDataListToList(wad3);
			ld.setLevelName("0");
			LevelDataContainer ldc = new LevelDataContainer();
			ldc.createNewLevelData(ld);

			ser.serializeToFile(md, "exampleGame/MapData/"+md.getClass().getSimpleName());
			ser.serializeToFile(pdd, "exampleGame/PlayerData/"+pdd.getClass().getSimpleName());
			ser.serializeToFile(ldc, "exampleGame/LevelData/"+ldc.getClass().getSimpleName());
			ser.serializeToFile(ed, "exampleGame/EntityData/"+ed.getClass().getSimpleName()+1);
			ser.serializeToFile(ed2, "exampleGame/EntityData/"+ed2.getClass().getSimpleName()+2);
			
			derp.deserializeFromFile("SerializedFiles/exampleGame/MapData/"+md.getClass().getSimpleName(), MapDataContainer.class);
			derp.deserializeFromFile("SerializedFiles/exampleGame/PlayerData/"+pdd.getClass().getSimpleName(), PlayerData.class);
			derp.deserializeFromFile("SerializedFiles/exampleGame/LevelData/"+ldc.getClass().getSimpleName(), LevelDataContainer.class);
			derp.deserializeFromFile("SerializedFiles/exampleGame/EntityData/"+ed.getClass().getSimpleName()+1, EntityData.class);
			derp.deserializeFromFile("SerializedFiles/exampleGame/EntityData/"+ed2.getClass().getSimpleName()+2, EntityData.class);
			
			//TerrainMap terrainMap = new TerrainMap(md);
//			terrainMap.getDestination();
		} catch (Exception e) {
			//TODO add more meaningful error message
			throw new SerializationException(ResouceAccess.getError("NoSerialize"));

		}
	}
	public static void main(String[] args) throws SerializationException
	{
		WestonGameDataConstructor m = new WestonGameDataConstructor();
		m.constructMockData();
	}
}
