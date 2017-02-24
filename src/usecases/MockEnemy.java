package usecases;

import authoring.authoring_interfaces.IEnemy;
import javafx.geometry.Point2D;

public class MockEnemy implements IEnemy {
	private String myName;
	private double mySpeed;
	
	public MockEnemy(String name){
		myName = name;
	}
	
	public void setSpeed(double speed){
		mySpeed = speed;
	}
	
	public String getName(){
		return myName;
	}

	@Override
	public double getSpeed() {
		return mySpeed;
	}

	@Override
	public Point2D getSpawnPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point2D getEndPoint() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean atEnd(){
            return false; 
	}

}
