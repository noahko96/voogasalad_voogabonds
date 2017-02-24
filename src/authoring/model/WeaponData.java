package authoring.model;

@Deprecated
public class WeaponData implements IReadableData {

	private String name;
	private String projectileName;
	private double range;
	private int fireRate;
	
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
	
	public void setProjectileName(String projectileName) throws Exception{
		if (projectileName == null || projectileName.length() == 0){
			throw new Exception("Name must be a valid string.");
		}
		this.projectileName = projectileName;
	}
	
	public String getProjectileName(){
		return projectileName;
	}
	
	public void setRange(double range) throws Exception{
		if (range < 0){
			throw new Exception("Range cannot be a negative number.");
		}
		this.range = range;
	}
	
	public double getRange(){
		return range;
	}
	
	public void setFireRate(int fireRate) throws Exception{
		if (fireRate < 0){
			throw new Exception("Fire Rate cannot be a negative number.");
		}
		this.fireRate = fireRate;
	}
	
	public int getFireRate(){
		return fireRate;
	}

}

