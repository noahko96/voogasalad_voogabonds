package utility;

import java.util.List;

import engine.model.strategies.IPosition;

/**
 * A class that represents 2 and 3D points, as well as supporting some math between them. (Distance calculations, vector math, etc.)
 * @author Weston
 *
 */
public class Point implements IShape, IPosition {
	private static final double ERROR_TOLERANCE = Math.exp(-6);
	
	private double myX;
	private double myY;
	private double myZ;
	
	/**
	 * Constructs a 3D point with the given coordinates
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point(double x, double y, double z) {
		myX = x;
		myY = y;
		myZ = z;
	}
	
	/**
	 * Constructs a 2D point with the given coordinates
	 * @param x
	 * @param y
	 */
	public Point(double x, double y) {
		this(x, y, 0);
	}
	
	/**
	 * Constructs a 3D point from the given coordinates
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point(Number x, Number y, Number z) {
		this(x.doubleValue(), y.doubleValue(), z.doubleValue());
	}
	
	/**
	 * Constructs a 2D point with the given coordinates
	 * @param x
	 * @param y
	 */
	public Point(Number x, Number y) {
		this(x.doubleValue(), y.doubleValue(), 0);
	}
	
	/**
	 * Constructs a point at the origin
	 * @param point 
	 */
	public Point(Point p){
		this(p.myX, p.myY, p.myZ);
	}
	
	/**
	 * Adds the two given points together
	 * @param p
	 * @return a new point, whose coordinates equal the two given points added together coordinate-wise
	 */
	public Point add(Point p) {
		return new Point(
				myX + p.myX,
				myY + p.myY,
				myZ + p.myZ
				);
	}
	
	/**
	 * Subtracts Point p from this
	 * @param p
	 * @return a new point, whose coordinates equal the difference between the two given points coordinate-wise
	 */
	public Point subtract(Point p) {
		return new Point(
				myX - p.myX,
				myY - p.myY,
				myZ - p.myZ
				);
	}
	
	/**
	 * Computes the dot product of p and this
	 * @param p
	 * @return the dot product
	 */
	public double dot(Point p) {
		return 	myX * p.myX +
				myY * p.myY +
				myZ * p.myZ;
	}
	
	/**
	 * Computes the cross product of p and this
	 * @param p
	 * @return a new Point perpendicular to this and p, with magnitude |this|*|p| 
	 */
	public Point cross(Point p) {
		return new Point(
				myY * p.myZ - myZ * p.myY,
				myZ * p.myX - myX * p.myZ,
				myX * p.myY - myY * p.myX
				);
				
	}
	
	/**
	 * Computes the euclidean distance between this and p
	 * @param p
	 * @return the distance
	 */
	public double euclideanDistance(Point p) {
		return Math.sqrt(
				Math.pow(myX - p.myX, 2) +
				Math.pow(myY - p.myY, 2) +
				Math.pow(myZ - p.myZ, 2)
				);
	}
	
	/**
	 * Computes the rectilinear distance between this and p
	 * @param p
	 * @return the distance
	 */
	public double rectilinearDistance(Point p){
		return
				Math.abs(myX - p.myX) +
				Math.abs(myY - p.myY) +
				Math.abs(myZ - p.myZ);
	}
	
	/**
	 * Computes the magnitude of this
	 * @return the magnitude
	 */
	public double magnitude(){
		return Math.sqrt(this.dot(this));
	}
	
	/**
	 * Treats all points as 2D points.
	 * TODO: Test this algorithm.
	 * @param pts
	 * @return
	 */
	public int windingNumber(List<Point> pts) {
		double result = 0;
		Point difference = subtract(pts.get(0));
		double last = Math.atan(difference.myY/difference.myX);
		
		for (Point p: pts) {
			difference = subtract(p);
			result += Math.atan(difference.myY/difference.myX) - last;
			last = Math.atan(difference.myY/difference.myX);
		}
		
		difference = subtract(pts.get(0));
		result += Math.atan(difference.myY/difference.myX) - last;
		
		return (int) result;
	}
	
	public double towards(Point p) {
		Point vector = p.subtract(this);
		
		return Math.toDegrees(Math.atan2(vector.myY, vector.myX));
	}
	
	public Point moveAlongHeading(double distance, double heading) {
		return new Point(myX + distance * Math.cos(Math.toRadians(heading)), myY + distance * Math.sin(Math.toRadians(heading)), myZ);
	}
	
	public double getX(){
		return myX;
	}
	public double getY(){
		return myY;
	}
	public double getZ(){
		return myZ;
	}
	
	public void setX(Number n){
		myX = n.doubleValue();
	}
	public void setY(Number n){
		myY= n.doubleValue();
	}
	public void setZ(Number n){
		myZ= n.doubleValue();
	}
	

	@Override
	public double getDistanceTo(Point p) {
		return euclideanDistance(p);
	}

	@Override
	public double getDistanceTo(IShape s) {
		return s.getDistanceTo(this);
	}

	@Override
	public boolean contains(Point p) {
		return equals(p);
	}

	@Override
	public boolean contains(IShape s) {
		if (s instanceof Point) {
			return contains((Point) s);
		}
		return false;
	}

	@Override
	public boolean overlaps(IShape s) {
		return false;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			return equals((Point) o);
		}
		return false;
	}
	
	private boolean equals(Point p) {
		return
				Math.abs(myX - p.myX) <= ERROR_TOLERANCE &&
				Math.abs(myY - p.myY) <= ERROR_TOLERANCE &&
				Math.abs(myZ - p.myZ) <= ERROR_TOLERANCE;
	}

	@Override
	public Point closestTo(Point p) {
		return new Point(this);
	}

	@Override
	public Point closestTo(IShape s) {
		return new Point(this);
	}

	public boolean onLine(Point a, Point b) {
		if (equals(a))
			return true;
		
		Point ab =  b.subtract(a);
		Point ap = subtract(a);
		
		return (ab.dot(ab) > ap.dot(ap) && ap.collinear(ab));
	}

	public boolean collinear(Point p) {
		return normalize().equals(p.normalize());
	}
	
	public Point normalize() {
		return scale(1 / Math.sqrt(dot(this)));
	}
	
	public Point scale(double scale) {
		return new Point(myX * scale, myY * scale, myZ * scale);
	}

	@Override
	public Point getPosition() {
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("(%f, %f, %f", myX, myY, myZ);
	}
	
	/*
	public static void main(String[] args) {
		Point a = new Point(4, 3);
		Point b = new Point(6, 4.5);
		Point c = new Point(22, 5);
		Point d = new Point(1, 3);
		Point e = new Point(5, 2);
		Point f = new Point(6, 3);
		
		//System.out.println(b.onLine(a, f));
	}
	*/
}
