package engine.model.components.viewable_interfaces;

import engine.IObservable;
import engine.model.entities.IEntity;
import utility.Point;

public interface IViewablePhysical extends IViewable, IObservable<IViewablePhysical> {
	
	/**
	 * A method to get the size of the image to display
	 * assumes that the object to display is a square
	 * @return the width/height of the image
	 */
	public double getSize();
	/**
	 * A method to display to orientation of the viewable in degrees relative to the positive y axis
	 * @return the heading, in degrees
	 */
	public double getHeading();
	/**
	 * gets the current location of the viewable
	 * @return the current position
	 */
	public Point getPosition();
	/**
	 * The image path of the viewable
	 * @return the path to the image to display
	 */
	public String getImagePath();
	
//	/**
//	 * Returns the entity associated with the viewable
//	 * @return the corresponding entity
//	 */
//	public IEntity getEntity();

}
