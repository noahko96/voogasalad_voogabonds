package authoring.model;

import java.io.File;
import java.util.List;

@Deprecated
public class ProjectileData implements IReadableData {

	private String name;
	private int maxRange;
	private int areaOfEffectRadius;
	private String imagePath;
	private String damage;
	private String movement;
	private int speed;
	private int damageMultiplier;
	private int turnSpeed;
	private double collisionRadius;
	private List<String> myValidTerrains;
	
	public void setName(String name) throws Exception {
		if (name == null || name.length() == 0){
			throw new Exception("Name must be a valid string.");
		}
		this.name = name;
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	public void setMaxRange(int maxRange) throws Exception{
		if (maxRange < 0){
			throw new Exception("Range cannot be a negative number.");
		}
		this.maxRange = maxRange;
	}
	
	public int getMaxRange(){
		return maxRange;
	}
	
	public void setAreaOfEffectRadius(int AOERadius) throws Exception{
		if (AOERadius < 0){
			throw new Exception("Area of Effect Radius cannot be a negative number.");
		}
		this.areaOfEffectRadius = AOERadius;
	}
	
	public int getAreaOfEffectRadius(){
		return areaOfEffectRadius;
	}
	
	public void setImagePath(String imagePath) throws Exception{
		if (!imagePath.endsWith(".png") || !(new File(imagePath).exists())){
			throw new Exception("Image file is invalid.");
		}
		this.imagePath = imagePath;
	}
	
	public String getImagePath(){
		return imagePath;
	}
	
	public void setDamageStrategy(String damage){
		this.damage = damage;
	}
	
	public String getDamageStrategy(){
		return damage;
	}
	
	public void setMovementStrategy(String movement){
		this.movement = movement;
	}
	
	public String getMovementStrategy(){
		return movement;
	}

	public double getSpeed() {
		return speed;
	}
	
	public double getDamage() {
		return damageMultiplier;
	}

	public double getTurnSpeed() {
		return turnSpeed;
	}

	public double getCollisionRadius() {
		return collisionRadius;
	}

	public List<String> getValidTerrains() {
		return myValidTerrains;
	}
}
