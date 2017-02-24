package authoring.model.serialization;
import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import authoring.controller.MapDataContainer;
import authoring.controller.PlayerDataContainer;
import authoring.controller.EntityDataContainer;
import authoring.controller.LevelDataContainer;
import authoring.controller.Router;
import authoring.controller.WaveDataContainer;
import authoring.model.EntityData;
import authoring.model.LevelData;
import authoring.model.PlayerData;
import authoring.model.WaveData;
import authoring.model.map.TerrainData;
import utility.ErrorBox;

public class GameStateLoader {
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
	private JSONDeserializer deserializer = new JSONDeserializer();
	
	public void loadMapData(Router r, String gameTitle){
		String mapFilePath = myResources.getString("DefaultSerialPath") + gameTitle + myResources.getString("MapDataFilePath");
		try{
			MapDataContainer newMapData = (MapDataContainer) deserializer.deserializeFromFile(mapFilePath, MapDataContainer.class);
			MapDataContainer routerMapData = r.getMapDataContainer();
			
			routerMapData.setDimensions(newMapData.getNumXCells(), newMapData.getNumYCells()); //Set dimensions                               
			
			//Load spawn points
			for (String spawnName: newMapData.getSpawnPointMap().keySet()){
				routerMapData.addSpawnPoints(spawnName, newMapData.getSpawnPointMap().get(spawnName));
			}
			
			//Load sink points
			for (String sinkName: newMapData.getSinkPointMap().keySet()){
				routerMapData.addSinkPoints(sinkName, newMapData.getSinkPointMap().get(sinkName));
			}
			
			//Load terrainList
			for (TerrainData td: newMapData.getTerrainList()){
				routerMapData.addTerrainData(td);
			}
			
			//Load valid terrain
			for (String terrainName: newMapData.getValidTerrainMap().keySet()){
				routerMapData.addValidTerrain(terrainName, newMapData.getValidTerrainMap().get(terrainName));
			}
		}catch(Exception e){
			e.printStackTrace();
			ErrorBox.displayError(myResources.getString("LoadAuthoringError"));
			
		}
	}
	
	public void loadEntityData(Router r, String gameTitle){
		String entityFilePath = myResources.getString("SourceFilePath") + myResources.getString("DefaultSerialPath") + gameTitle + myResources.getString("EntityDataFilePath");
		File dir = new File(entityFilePath);
		
		File[] entityFiles = dir.listFiles();
		try{
			EntityDataContainer routerEntityData = r.getEntityDataContainer();
			for (File f: entityFiles){
				String entityDataPath = f.toString().substring(myResources.getString("SourceFilePath").length());
				entityDataPath = entityDataPath.replace("\\", "/");
				EntityData oldEntityData = (EntityData) deserializer.deserializeFromFile(entityDataPath, EntityData.class);
//				//System.out.println("\nLOADING...");
//				for (String component : oldEntityData.getComponents().keySet()) {
//				    //System.out.println(component);
//				}
				routerEntityData.createEntityData(oldEntityData);
			}
		}catch(Exception e){
			e.printStackTrace();
			ErrorBox.displayError(myResources.getString("LoadAuthoringError"));
			
		}
	}
	
	public void loadLevelData(Router r, String gameTitle){
		String levelFilePath = myResources.getString("DefaultSerialPath") + gameTitle + myResources.getString("LevelDataFilePath");
		LevelDataContainer routerLevelData = r.getLevelDataContainer();
		try{
			LevelDataContainer newLevelData = (LevelDataContainer) deserializer.deserializeFromFile(levelFilePath, LevelDataContainer.class);
//			ArrayList<LevelData> listLevelData = (ArrayList<LevelData>) deserializer.deserializeFromFile(levelFilePath, ArrayList.class);
			for (LevelData ld: newLevelData.finalizeLevelDataMap()){
				routerLevelData.createNewLevelData(ld);
			}
		}catch(Exception e){
			e.printStackTrace();
			ErrorBox.displayError(myResources.getString("LoadAuthoringError"));
			
		}
	}
	
	public void loadWaveData(Router r, String gameTitle){
		String waveFilePath = myResources.getString("SourceFilePath") + myResources.getString("DefaultSerialPath") + gameTitle + myResources.getString("WaveDataFilePath");
		WaveDataContainer routerWaveData = r.getWaveDataContainer();
		File dir = new File(waveFilePath);
		File[] waveFiles = dir.listFiles();
		
		try{
			for (File f: waveFiles){
				String waveDataPath = f.toString().substring(myResources.getString("SourceFilePath").length());
				waveDataPath = waveDataPath.replace("\\", "/");
				WaveData oldWaveData = (WaveData) deserializer.deserializeFromFile(waveDataPath, WaveData.class);
				routerWaveData.createNewWave(oldWaveData.getName(), oldWaveData);
			}
		}catch(Exception e){
			e.printStackTrace();
			ErrorBox.displayError(myResources.getString("LoadAuthoringError"));
			
		}
	}
	
	public void loadPlayerData(Router r, String gameTitle){
		String playerFilePath = myResources.getString("DefaultSerialPath") + gameTitle + myResources.getString("PlayerDataFilePath");
		PlayerData routerPlayerData = r.getPlayerDataContainer().getPlayerData();
		
		try{
			PlayerData newPlayerData = (PlayerData) deserializer.deserializeFromFile(playerFilePath, PlayerData.class);
			routerPlayerData.setLoseStrategyName(newPlayerData.getLoseStrategyName());
			routerPlayerData.setStartingCash(newPlayerData.getStartingCash());
			routerPlayerData.setStartingLives(newPlayerData.getStartingLives());
			routerPlayerData.setWinStrategyName(newPlayerData.getWinStrategyName());
		}catch(Exception e){
			e.printStackTrace();
			ErrorBox.displayError(myResources.getString("LoadAuthoringError"));
			
		}
	}
}
