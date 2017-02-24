package engine.model.game_environment.terrain;

import java.util.List;

import authoring.controller.MapDataContainer;
import utility.Index;
import utility.Point;

public class TerrainMap {
	private TerrainGridFactory  myMapFactory; 
	private Terrain[][] myMap;

	//TODO: set these up
	private double myTerrainWidth;
	private double myTerrainHeight;
	//private double myMapWidth;
	//private double myMapHeight;
	
	private INeighborStrategy<Terrain> myNeighborStrategy;
	
	public TerrainMap(MapDataContainer aTerrainMapData, int pixelWidth)
	{
		myMapFactory = new TerrainGridFactory();
		myMap = myMapFactory.constructTerrainMap(aTerrainMapData, aTerrainMapData.getCellSize());

		myTerrainWidth = pixelWidth / Math.max(aTerrainMapData.getNumXCells(),  aTerrainMapData.getNumYCells());
		myTerrainHeight = myTerrainWidth;
		
		//TODO: Change this to be constructed in a factory, perhaps
		myNeighborStrategy = new AdjacentNeighborStrategy(this);
	}
	
	/**
	 * Returns the neighbors of a given Terrain node
	 * @param aTerrain
	 * @return
	 */
	public List<Terrain> getNeighbors(Terrain aTerrain)
	{
		return myNeighborStrategy.getNeighbors(aTerrain);
	}
	
	/**
	 * A method to determine if the location on the map has one of the requested Terrains
	 * @param aValidTerrainList: valid terrains
	 * @param aLocation: location on the map
	 * @return true if the location has one of the valid terrains
	 */
	public boolean hasTerrain(List<String> list, Point aLocation) {
		Terrain terrain = getTerrain(aLocation);
		
		for (String terrainName: list) {
			if (terrain.getTerrainType().equals(terrainName)) {
				return true;
			}
		}
		return false;

	}
	/**
	 * A method to get the terrain object corresponding to the requested location on the map
	 * This method assumes it is passed a valid point--***TODO:should add error checking here***
	 * 
	 * This method also assumes that the map is oriented using the right-hand rule, meaning
	 * that x increases going down the rows of the matrix, and y increases going along the columns
	 * @param aMapLocation
	 * @return the terrain at the map location
	 */
	public Terrain getTerrain(Point aMapLocation)
	{
		double x = aMapLocation.getX();
		double y = aMapLocation.getY();
		
		int xIndex = (int)Math.floor(x/myTerrainHeight);
		int yIndex = (int)Math.floor(y/myTerrainWidth);
		
		//TODO: what if out of range
		return myMap[xIndex][yIndex];
	}
	/**
	 * A method to get the terrain at a certain index
	 * TODO: Add error checking--method currently assumes that the indices are valid
	 * This method is package friendly only, since on the the neighbor strategies should access Terrain object
	 * this way
	 * @param aIndex
	 * @return
	 */
	Terrain getTerrain(Index aIndex)
	{
		return myMap[aIndex.getX()][aIndex.getY()];
	}
	
	/**
	 * Gets the height of the map (the number of rows in the map)
	 * @return
	 */
	int getHeight()
	{
		return myMap.length;
	}
	/**
	 * Gets the width of the map (the number of columns in the map)
	 * @return
	 */
	int getWidth()
	{
		return myMap[0].length;
	}
	
}
