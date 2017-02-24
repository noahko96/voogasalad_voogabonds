package engine.model.game_environment.terrain;

import authoring.model.map.TerrainData;
import utility.Index;
import utility.Point;

public class Terrain {
	private TerrainData myTerrainData;
	
	public Terrain(TerrainData aTerrainData)
	{
		myTerrainData = aTerrainData;
	}
	
	/**
	 * Returns the location of the Terrain inside of the map
	 * @return
	 */
	public Index getIndexInMap()
	{
		return new Index((int)myTerrainData.getLoc().getX(), (int)myTerrainData.getLoc().getY());
	}
	
	/**
	 * Returns the *top* terrain type of this Terrain object
	 * @return
	 */
	public String getTerrainType()
	{
		return myTerrainData.getName();
	}
	
	public Point getCenter() {
		int size = myTerrainData.getSize();
		int x = (int) myTerrainData.getLoc().getX();
		int y = (int) myTerrainData.getLoc().getY();
		return new Point(size * (x + 0.5), size * (y + 0.5));
	}

	public boolean contains(Point goal) {
		Point center = getCenter();
		double halfSizeOfCell = ((double)myTerrainData.getSize()) / 2.0;
		double xMin = center.getX() - halfSizeOfCell;
		double xMax = center.getX() + halfSizeOfCell;
		double yMax = center.getY() + halfSizeOfCell;
		double yMin = center.getY() - halfSizeOfCell;
		
		double goalX = goal.getX() + halfSizeOfCell;
		double goalY = goal.getY() + halfSizeOfCell;
		
		return withinBound(goalX, xMin, xMax) && withinBound(goalY, yMin, yMax);

//		return getCenter().rectilinearDistance(goal) <= myTerrainData.getSize();
	}
	
	private boolean withinBound(double value, double minBound, double maxBound)
	{
		return value >= minBound && value <= maxBound;
	}
	

}