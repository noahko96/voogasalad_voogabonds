package engine.model.game_environment.paths;

import engine.model.game_environment.terrain.TerrainMap;

@Deprecated
public class PathFactory {
	
	
	TerrainMap myMap;
	
	public PathFactory(TerrainMap aTerrainMap)
	{
		myMap = aTerrainMap;
	}
	
	/**
	 * This method runs the BFS shortest path algorithm to construct paths from the Terrain map
	 * @param aTerrainMap
	 * @return
	 */
	/*
	public PathManager constructPaths(PhysicalComponent physical)
	{
		Terrain source = myMap.getTerrain(physical.getPosition());
		
		Queue<Terrain> terrainQueue = new LinkedList<Terrain>();
		
		terrainQueue.add(source);

		HashMap<Terrain,Terrain> paths = constructPathsInGraph(terrainQueue, physical.getValidTerrains());
		
		List<Terrain> shortestPath = constructShortestPath(paths, myMap.getDestination());
		
		PathManager pathManager = new PathManager(shortestPath);
		return pathManager;
	}
	*/
	/**
	 * Helper method 
	 * @param aQueue
	 * @param aTerrainMap
	 * @return a map from terrain nodes to their previous node on the path
	 */
	//TODO: Refactor this pls
	/*
	private HashMap<Terrain, Terrain> constructPathsInGraph(Queue<Terrain> aQueue, List<String> aValidTerrains)

	{
		HashMap<Terrain, Terrain> pathToFollow = new HashMap<Terrain, Terrain>();
		while (!aQueue.isEmpty()) {
			Terrain currentTerrain = aQueue.poll();
			for (Terrain neighbor: myMap.getNeighbors(currentTerrain)) {
				if (!pathToFollow.containsKey(neighbor)) {//node is unmarked
					if (hasValidTerrainType(aValidTerrains, neighbor)) {
						pathToFollow.put(neighbor, currentTerrain);
						aQueue.add(neighbor);
						if (neighbor == myMap.getDestination()) { 
							return pathToFollow;
						}
					}
				}
			}
		}
		//TODO: Throw error-->No path from source to destination!
		return null;
	}
	*/
	/*
	private boolean hasValidTerrainType(List<String> aValidTerrains, Terrain neighbor) {	
		return aValidTerrains.stream().anyMatch(s -> s.equals(neighbor));
	}

	private boolean hasSameTerrainType(Terrain t1, Terrain t2)
	{
		return t1.getTerrainType().equals(t2.getTerrainType());
	}
	
	
	private List<Terrain> constructShortestPath(Map<Terrain,Terrain> aPreviousPathMap, Terrain aEndingTerrain)
	{
		List<Terrain> shortestPath = new ArrayList<Terrain>();
		
		Terrain currentTerrain = aEndingTerrain;
		while(aPreviousPathMap.containsKey(currentTerrain)) {
			shortestPath.add(0, currentTerrain);
			currentTerrain = aPreviousPathMap.get(currentTerrain);
		}
		shortestPath.add(0,currentTerrain); //adds the source
		return shortestPath;
	}
	

	public static void main(String[] args)
	{
		
		TerrainMap t = new TerrainMap(null);
		PathFactory p = new PathFactory(t);
		
		//p.constructPaths(t);
	}
	*/
}
