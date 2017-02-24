package engine.model.strategies;

import java.util.List;

import javafx.util.Pair;
import utility.Point;

/**
 * An interface for physical objects that can be placed on the map
 * @author Weston
 *
 */
public interface IPhysical extends IPosition {
	
	/**
	 * Returns the position of this object as a point
	 */
	@Override
	public Point getPosition();
	
	/**
	 * Returns the heading of this object as a double (in degrees)
	 */
	public double getHeading();
	
	/**
	 * 
	 * @return the list of valid terrains that this object may occupy
	 */
	public List<String> getValidTerrains();
	
	/**
	 * Sets this object's position and heading
	 * @param p
	 */
	public void setPosition(Pair<Double, Point> p);
	
	/**
	 * Sets this object's position
	 * @param position
	 */
	public void setPosition(Point position);
	
}
