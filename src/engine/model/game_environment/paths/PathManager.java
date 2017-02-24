package engine.model.game_environment.paths;

import java.util.List;
import java.util.stream.Collectors;

import engine.model.game_environment.terrain.Terrain;
import utility.Point;

public class PathManager {
	
	private List<Point> myPath;
	
	public PathManager(List<Terrain> aPathToFollow, Point goal)
	{
		myPath = aPathToFollow.stream().map(t -> t.getCenter()).collect(Collectors.toList());
		myPath.add(goal);
	}
	
	public Point getNextVertex(Point p) {
		if (myPath.size() <= 0)
			return null;
		if (p.equals(myPath.get(0)))
				myPath.remove(myPath.get(0));
		return myPath.size() <= 0 ? null : myPath.get(0);
	}
	
}
