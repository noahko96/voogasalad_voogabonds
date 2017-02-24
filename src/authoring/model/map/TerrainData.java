package authoring.model.map;
import utility.Point;

import authoring.model.IReadableData;

/**
 * @author philipfoo
 *
 */
public class TerrainData implements IReadableData {
	/**
	 * Will correspond to a type of "valid terrain" as defined by the author.
	 */
	private String type;
	private Point loc;
	private String myColor;
	private int mySize;
	
	public TerrainData(String type) {
		this.type = type;
	}
	
	//TODO: add error checking for what the hex string looks like
	public TerrainData(String type, int x, int y, int aSize, String aHexValue){
		this.type = type;
		this.loc = new Point(x, y);
		mySize = aSize;
		myColor = aHexValue;
	}
	
	/**
	 * Get the hex value associated with the terrain data
	 * @return
	 */
	public String getColor()
	{
		return myColor;
	}
	
	public int getSize()
	{
		return mySize;
	}
	
	@Override
	public String getName()
	{
		return type;
	}
	
	
	public Point getLoc(){
		return loc;
	}
	
	
	@Override
	public boolean equals(Object o){
		if (o == this){
			return true;
		}
		if (!(o instanceof TerrainData)){
			return false;
		}
		TerrainData td = (TerrainData) o;
		return td.type.equals(this.type) && (td.getLoc().getX() == loc.getX()) && (td.getLoc().getY() == loc.getY());
	}
}
