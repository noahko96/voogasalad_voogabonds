package authoring.controller;
import authoring.model.map.*;
import authoring.model.IReadableData;
import engine.IObservable;
import engine.IObserver;
import engine.model.game_environment.terrain.Terrain;
import javafx.collections.SetChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import utility.Point;

import java.util.Stack;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;

import com.sun.prism.paint.Color;

/**
 * MapDataController doesn't expect a FrontEndMap like the rest of the controller classes.
 * Instead set the x and y dimensions, and then add spawn/sink points through the below functions.
 * @author philipfoo
 *
 */
public class MapDataContainer extends Container implements IReadableData, IObservable<Container>{
	private String myName;
	private int numXCells;
	private int numYCells;
	private int cellSize;
	private String myTune;
	
	private transient ArrayList<IObserver<Container>> myListeners = new ArrayList<IObserver<Container>>();
	
	/**
	 * Each group of spawn points will have a name. A map is necessary because
	 * a wave must specify a spawn point which its enemies will start from.
	 */
	private HashMap<String, ArrayList<Point>> spawnPoints;
	
	/**
	 * Set of points that enemies will try to reach.
	 */
	private HashMap<String, ArrayList<Point>> sinkPoints;
	
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
	private HashMap<String, String> validTerrain;
	
	public MapDataContainer(){
		this.spawnPoints = new HashMap<String, ArrayList<Point>>();
		this.sinkPoints = new HashMap<String, ArrayList<Point>>();
		this.terrainList = new HashSet<TerrainData>();
		this.validTerrain = new HashMap<String, String>();
	}
	
	@Override
	public String getName()
	{
		return myName;
	}
	
	/**
	 * @return name of sound file
	 */
	public String getTune() {
	    return myTune;
	}
	
	/**
	 * @param soundFile - .mp3 or .wav file
	 */
	public void setTune(String soundFile) throws Exception {
	    if (soundFile.endsWith(".mp3") || soundFile.endsWith(".wav")) {
	        myTune = soundFile;
	    } else {
	        throw new Exception("Invalid sound file. Please enter an MP3 or WAV file.");
	    }
	}
	
	/**
	 * MAP DIMENSION FUNCTIONS
	 */
	public void setDimensions(int x, int y){
		try{
			setNumXCells(x);
			setNumYCells(y);
		}catch(Exception e){
			//Show error to front-end here
		}
	}
	
	private void setNumXCells(int x) throws Exception{
		if (x <= 0){
			throw new IllegalArgumentException("The map must be wider than 0 cells.");
		}
		this.numXCells = x;
		//System.out.println("Total XCells: " + this.numXCells);
	}
	public int getNumXCells(){
		return numXCells;
	}
	
	//matthewfaw: making this public--hope it's ok
	public void cellSize(int cellSize) {
		if (cellSize<=0){
			throw new IllegalArgumentException("The size of cells must be greater than 0 pixels.");
		}
		this.cellSize = cellSize;
	}
	
	public int getCellSize(){
		return cellSize;
	}
	
	private void setNumYCells(int y) throws Exception{
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
	public void addSpawnPoints(String name, ArrayList<Point> newSpawnPoints){
		try{
			for (Point p: newSpawnPoints){
				validatePoint(p, "spawn");
			}
		}catch(Exception e){
			//Show error to front-end here
		}
		spawnPoints.put(name, newSpawnPoints);
		notifyObservers();
		//System.out.println("Added Spawn Point " + name);
	}
	
	
	public void removeSpawnPoints(String name){
			spawnPoints.remove(name);
			notifyObservers();
			//System.out.println("Removed Spawn Point " + name);
	}
	
	public List<Point> getSpawnPoints(String name){
		return spawnPoints.get(name).stream().map(e -> midpoint(e)).collect(Collectors.toList());
	}
	
	private Point midpoint(Point e) {
		return e.scale(cellSize);
	}

	public HashMap<String, ArrayList<Point>> getSpawnPointMap(){
		return spawnPoints;
	}
	
	/**
	 * SINK POINT FUNCTIONS
	 */
	public void addSinkPoints(String name, ArrayList<Point> newSinkPoints){
		try{
			for (Point p: newSinkPoints) {
				validatePoint(p, "sink");
			}
		}catch(Exception e){
			//Show error to front-end here
		}
		sinkPoints.put(name, newSinkPoints);
		notifyObservers();
		//System.out.println("Added Sink Point " + name);
	}
	
	public void removeSinkPoint(String name){
			sinkPoints.remove(name);
			notifyObservers();
			//System.out.println("Removed Sink Point " + name);
	}
	
	public List<Point> getSinkPoints(String name){
		return sinkPoints.get(name).stream().map(e -> midpoint(e)).collect(Collectors.toList());
	}
	
	public HashMap<String, ArrayList<Point>> getSinkPointMap(){
		return sinkPoints;
	}
	
	/**
	 * TERRAIN DATA FUNCTIONS
	 */
	public void addTerrainData(TerrainData terrain) {
		validatePoint(terrain.getLoc(), "terrain");
		terrainList.add(terrain);
	}

	public void removeTerrainData(TerrainData terrain){
		terrainList.remove(terrain.getLoc());
	}
	
	public HashSet<TerrainData> getTerrainList(){
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
		notifyObservers();
		//System.out.println("Added Terrain: " + name);
	}
	public void removeValidTerrain(String name) {
		if (validTerrain.containsKey(name)){
			validTerrain.remove(name);
			notifyObservers();
		}
	}
	
	public HashMap<String, String> getValidTerrainMap(){
		return validTerrain;
	}
	

	/**
	 * IObservable functions
	 *
	 */
	public void attach(IObserver<Container> listener){
		myListeners.add(listener);
	}
	
	public void detach(IObserver<Container> listener){
		myListeners.remove(listener);
	}
	
	public void notifyObservers(){
		for (IObserver<Container> listener: myListeners){
			listener.update(this);
		}
	}
	
//	// TODO: make the methods we need more available in the MapData object itself so we don't need this here.
//	public MapData getMapData() throws Exception{
//		myMapData.setNumXCells(numXCells);
//		myMapData.setNumYCells(numYCells);
//		for (String spawnName : spawnPoints.keySet()){
//			myMapData.addSpawnPoints(spawnName, spawnPoints.get(spawnName));
//		}
//		for (Point p : sinkPoints){
//			myMapData.addSinkPoint(p);
//		}
//		for (TerrainData td : terrainList){
//			myMapData.addTerrainData(td);
//		}
//		//System.out.println("before terrain");
//		for (String vt: validTerrain.keySet()){
//			myMapData.addValidTerrain(vt, "color");
//		}
//		return myMapData;
//	}
	
	
	
	private void validatePoint(Point p, String type){
		if (p.getX() >= numXCells || p.getX() < 0){
			throw new IllegalArgumentException("X location of " + type + " point not valid.");
		}
		if (p.getY() >= numYCells || p.getY() < 0){
			throw new IllegalArgumentException("Y location of " + type + " point not valid.");
		}
	}
}
