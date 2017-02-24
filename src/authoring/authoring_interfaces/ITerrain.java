package authoring.authoring_interfaces;

import javafx.geometry.Point2D;

/**
 * This interface is responsible for setting up the battlefield terrain, assuming that we will be using a grid.
 * I believe that Terrain should be the individual cells in the ovverall grid so the user can set the overall environment to greater precision.
 * @author Christopher LU
 * @author Niklas
 *
 */

public interface ITerrain {
	
	/**
	 * Determines the position of the terrain.
	 */
	public Point2D getPosition();
	
	/**
	 * Type of terrain determines how fast an enemy can move across it.
	 */
	public String getType();
	
	/**
	 * Determines the terrain's color.
	 */
	public String getColor();
	

}
