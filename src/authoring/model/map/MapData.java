package authoring.model.map;
import utility.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.ObservableMap;
import javafx.collections.SetChangeListener;
import javafx.collections.MapChangeListener;


import authoring.model.IReadableData;

public class MapData implements IReadableData {
	private String myName;
	private int numXCells;
	private int numYCells;
	private int cellSize;
	
	/**
	 * Each group of spawn points will have a name. A map is necessary because
	 * a wave must specify a spawn point which its enemies will start from.
	 */
	private ObservableMap<String, ArrayList<Point>> spawnPoints;
	
	/**
	 * Set of points that enemies will try to reach.
	 */
	private ObservableMap<String, ArrayList<Point>> sinkPoints;
	
	/**
	 * Set defines the geography of the entire map. This set will contain a list of
	 * TerrainDatas, which can be used to populate the entire map. Should contain
	 * a minimum of numXCells x numYCells TerrainData elements.
	 */
	private HashSet<TerrainData> terrainList;
	
	/**
	 * Map of possible terrains that might exist, as created by the user.
	 * The key will be the name of the terrain, and the value will be how its displayed (currently color in HEX).
	 */
	private ObservableMap<String, String> validTerrain;
	
	public MapData()
	{
		this.spawnPoints = FXCollections.observableHashMap();
		this.sinkPoints = FXCollections.observableHashMap();
		this.terrainList = new HashSet<TerrainData>();
		this.validTerrain = FXCollections.observableHashMap();
	}
	
	@Override
	public String getName()
	{
		return myName;
	}
	
	/**
	 * MAP DIMENSION FUNCTIONS
	 */
	public void setNumXCells(int x) throws Exception{
		if (x <= 0){
			throw new Exception("The map must be wider than 0 cells.");
		}
		this.numXCells = x;
		//System.out.println("Total XCells: " + this.numXCells);
	}
	public int getNumXCells(){
		return numXCells;
	}
	
	public void cellSize(int cellSize) throws Exception{
		if (cellSize<=0){
			throw new Exception("The size of cells must be greater than 0 pixels.");
		}
		this.cellSize = cellSize;
	}
	
	public int getCellSize(){
		return cellSize;
	}
	
	public void setNumYCells(int y) throws Exception{
		if (y <= 0){
			throw new Exception("The map must be taller than 0 cells.");
		}
		this.numYCells = y;
		//System.out.println("Total YCells: " + this.numYCells);
	}
	public int getNumYCells(){
		return numYCells;
	}
	
	/**
	 * SPAWN POINT FUNCTIONS
	 */
	public void addSpawnPoints(String name, ArrayList<Point> newSpawnPoints) throws Exception{
		for (Point p: newSpawnPoints){
			validatePoint(p, "spawn");
		}
		spawnPoints.put(name, newSpawnPoints);
		//System.out.println("Added Spawn Point " + name);
	}
	
	public void addSpawnPointListener(MapChangeListener<String, ArrayList<Point>> listener){
		spawnPoints.addListener(listener);
	}
	
	public void removeSpawnPoints(String name){
		if (spawnPoints.containsKey(name)){
			spawnPoints.remove(name);
			//System.out.println("Removed Spawn Point " + name);
		}
	}
	
	public ArrayList<Point> getSpawnPoints(String name){
		return spawnPoints.get(name);
	}
	
	/**
	 * SINK POINT FUNCTIONS
	 */
	public void addSinkPoints(String name, ArrayList<Point> newSinkPoints) throws Exception{
		for (Point p: newSinkPoints) {
			validatePoint(p, "sink");
		}
		sinkPoints.put(name, newSinkPoints);
		//System.out.println("Added Sink Point " + name);
	}
	
	public void removeSinkPoint(String name){
		if (sinkPoints.containsKey(name)){
			sinkPoints.remove(name);
			//System.out.println("Removed Sink Point " + name);
		}
	}
	
	public ArrayList<Point> getSinkPoints(String name){
		return sinkPoints.get(name);
	}
	
	/**
	 * TERRAIN DATA FUNCTIONS
	 */
	public void addTerrainData(TerrainData terrain) throws Exception{
		validatePoint(terrain.getLoc(), "terrain");
		terrainList.add(terrain);
	}

	public void removeTerrainData(TerrainData terrain){
		if (terrainList.contains(terrain)){
			terrainList.remove(terrain);
		}
	}
	
	public Set<TerrainData> getTerrainList(){
		return terrainList;
	}
	
	/**
	 * VALID TERRAIN FUNCTIONS
	 */
	public void addValidTerrain(String name, String color) throws Exception{
		if (name == null || name.length() == 0){
			throw new Exception("No terrain specified.");
		}
		validTerrain.put(name, color);
		//System.out.println("Added Terrain: " + name);
	}
	
	public void removeValidTerrain(String name) throws Exception{
		if (validTerrain.containsKey(name)){
			validTerrain.remove(name);
		}
	}
	
	public ObservableMap<String, String> getValidTerrain(){
		return validTerrain;
	}
	
	public void addValidTerrainListener(MapChangeListener<String, String> listener){
		validTerrain.addListener(listener);
	}
	
	
	private void validatePoint(Point p, String type) throws Exception{
		if (p.getX() >= numXCells || p.getX() < 0){
			throw new Exception("X location of " + type + " point not valid.");
		}
		if (p.getY() >= numYCells || p.getY() < 0){
			throw new Exception("Y location of " + type + " point not valid.");
		}
	}
}
