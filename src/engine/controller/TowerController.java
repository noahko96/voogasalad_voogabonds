//package engine.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import authoring.model.TowerData;
//import authoring.model.serialization.JSONDeserializer;
//import engine.model.game_environment.terrain.Terrain;
//import engine.model.machine.tower.Tower;
//import engine.model.machine.tower.TowerNode;
//
//public class TowerController {
//	
//	public void init() {
//		JSONDeserializer djson = new JSONDeserializer();
//		TowerData tdata = (TowerData) djson.deserializeFromFile("towers/ActualTower", TowerData.class);
//		List<String> terrainstrings = new ArrayList<String>();
//		terrainstrings.add("grass");
//		tdata.setTraversableTerrain(terrainstrings);
//		Tower tower = new Tower(tdata.getName(), tdata.getMaxHealth());
//		List<Terrain> terrains = new ArrayList<Terrain>();
//		//Don't care which location it is on
//		Terrain temp = new Terrain(-1, -1, tdata.getTraversableTerrain().get(0));
//		terrains.add(temp);
//		tower.setPossibleTerrains(terrains);
//		TowerNode towernode = new TowerNode(tower, null);
//		towernode.setSellPrice(tdata.getSellPrice());
//		towernode.setPrice(tdata.getBuyPrice());
//		
//		
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	public static void main(String [] args) {
//		
//	}
//}
