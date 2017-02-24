package utility;

/**
 * A class to encapulate indices
 * assumes that all indices have two values (x and y)
 * @author matthewfaw
 *
 */
public class Index {
	private int myX;
	private int myY;

	public Index(int aX, int aY)
	{
		myX = aX;
		myY = aY;
	}
	
	/**
	 * returns the first entry of the index
	 * @return
	 */
	public int getX()
	{
		return myX;
	}
	/**
	 * returns the second entry of the index
	 * @return
	 */
	public int getY()
	{
		return myY;
	}
}
