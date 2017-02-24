package utility;

public class Polygon implements IShape {

	@Override
	public double getDistanceTo(Point p) {
		return closestTo(p).euclideanDistance(p);
	}

	@Override
	public double getDistanceTo(IShape s) {
		return closestTo(s).euclideanDistance(closestTo(this));
	}

	@Override
	public Point closestTo(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point closestTo(IShape s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(IShape s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean overlaps(IShape s) {
		return contains(s.closestTo(this));
	}

}
