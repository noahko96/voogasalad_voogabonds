package engine.model.game_environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import authoring.controller.MapDataContainer;
import authoring.model.map.TerrainData;
import engine.model.components.concrete.PhysicalComponent;
import engine.model.game_environment.paths.PathManager;
import engine.model.game_environment.terrain.Terrain;
import engine.model.game_environment.terrain.TerrainMap;
import engine.model.strategies.IMovable;
import engine.model.strategies.IPhysical;
import utility.Point;

public class MapMediator {
//	private PathFactory myPathFactory;
	
	private TerrainMap myTerrainMap;
	private MapDataContainer myMapData;
	private double myPixelWidth;
	
	public MapMediator(MapDataContainer mapData, int pixelWidth) {
		myMapData = mapData;
		myTerrainMap = new TerrainMap(mapData, pixelWidth);
		myPixelWidth = pixelWidth;

	}
	/**
	 * Determines if a point is a valid terrain.
	 * @param aLocation
	 * @param validTerrains
	 * @return
	 */
	public boolean isAValidTerrain(Point aLocation, List<String> validTerrains) {
		
		return myTerrainMap.hasTerrain(validTerrains, aLocation);
		
//		for (TerrainData terrainData: myMapData.getTerrainList()) {
//			if (terrainData.getLoc().equals(aLocation)) {
//				//System.out.println("The terrain at this point is: "+terrainData.getName());
//				for (String validTerrain: validTerrains) {
//					if (validTerrain.equals(terrainData.getName())) {
//						return true;
//					}
//				}
//			}
//		}
//		return false;
	}
	
	/**
	 * Determines if an object can be placed on the map at the requested location
	 * Places the object if it can
	 * @param aLocation to place the object
	 * @param aValidTerrainList: a list of terrains the object can be placed on
	 * @return true if the entity was placed, false otherwise
	 */
	public boolean attemptToPlaceEntity(Point aLocation, PhysicalComponent aPhysicalComponent)
	{		
		/*
		 * Find terrain data at input point, then see if that data is a valid terrain for the 
		 * physical component. If so, place physical component.
		 */
		
		List<String> physicalComponentTerrains = aPhysicalComponent.getValidTerrains();
		Collection<TerrainData> terrainList = myMapData.getTerrainList();
		for (TerrainData terrainData: terrainList) {
			if (terrainData.getLoc().equals(aLocation)) {
				// found the terrain data at input location
				for (String physicalTerrain: physicalComponentTerrains) {
					if (physicalTerrain.equals(terrainData.getName())) {
						// terrain data at input location matches valid terrain type
						
						// Set heading to 0 and position as input point
						aPhysicalComponent.setPosition(aLocation);
//						accept(aPhysicalComponent, aLocation);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public PathManager constructPaths(IPhysical physical, IMovable movement)
	{
		//Point goal = movement.getGoalPoint().scale();
		Terrain source = myTerrainMap.getTerrain(physical.getPosition());
		Terrain sink = myTerrainMap.getTerrain(movement.getGoalPoint());
		
		Queue<Terrain> terrainQueue = new LinkedList<Terrain>();
		
		terrainQueue.add(source);

		HashMap<Terrain,Terrain> paths = constructPathsInGraph(terrainQueue, physical.getValidTerrains(), sink);
		
		//TODO: Fix the algorithm so this doesn't occur
		if (paths.containsKey(source)) {
			paths.remove(source);
		}
		List<Terrain> shortestPath = constructShortestPath(paths, sink);
		
		PathManager pathManager = new PathManager(shortestPath, movement.getGoalPoint());
		return pathManager;
	}
	
	/**
	 * Helper method 
	 * @param aQueue
	 * @param aTerrainMap
	 * @return a map from terrain nodes to their previous node on the path
	 */
	//TODO: Refactor this pls

	private HashMap<Terrain, Terrain> constructPathsInGraph(Queue<Terrain> aQueue, List<String> aValidTerrains, Terrain sink)
	{
		HashMap<Terrain, Terrain> pathToFollow = new HashMap<Terrain, Terrain>();
		while (!aQueue.isEmpty()) {
			Terrain currentTerrain = aQueue.poll();
			for (Terrain neighbor: myTerrainMap.getNeighbors(currentTerrain)) {
				if (!pathToFollow.containsKey(neighbor)) {//node is unmarked
					if (hasValidTerrainType(aValidTerrains, neighbor)) {
						pathToFollow.put(neighbor, currentTerrain);
						aQueue.add(neighbor);
						if (neighbor.equals(sink)) { 
							return pathToFollow;
						}
					}
				}
			}
		}
		//TODO: Throw error-->No path from source to destination!
		return pathToFollow;
	}
	
	private boolean hasValidTerrainType(List<String> aValidTerrains, Terrain neighbor) {
		if (neighbor == null || neighbor.getTerrainType() == null) {
			return false;
		}
		for (Iterator<String> iterator = aValidTerrains.iterator(); iterator.hasNext();) {
			String terrain = iterator.next();
			if (terrain.equals(neighbor.getTerrainType())) {
				return true;
			}
		}
		return false;
//		return aValidTerrains.stream().anyMatch(s -> s.equals(neighbor.getTerrainType()));
	}
	
	private List<Terrain> constructShortestPath(Map<Terrain,Terrain> aPreviousPathMap, Terrain sink)
	{
		List<Terrain> shortestPath = new ArrayList<Terrain>();
		
		Terrain currentTerrain = sink;
		while(aPreviousPathMap.containsKey(currentTerrain)) {
//		while(true) {
			shortestPath.add(0, currentTerrain);
			currentTerrain = aPreviousPathMap.get(currentTerrain);
		}
		shortestPath.add(0,currentTerrain); //adds the source
		return shortestPath;
	}

}
