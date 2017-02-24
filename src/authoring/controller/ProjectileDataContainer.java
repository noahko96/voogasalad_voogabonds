package authoring.controller;

import java.util.AbstractMap;
import java.util.HashMap;

import authoring.model.ProjectileData;

@Deprecated
public class ProjectileDataContainer {

	private AbstractMap<String, ProjectileData> myProjectileDataMap = new HashMap<String, ProjectileData>();

	public AbstractMap getProjectileDataMap(){
		return myProjectileDataMap;
	}
	
	public void createProjectileData(ProjectileData projectileData){
		myProjectileDataMap.put(projectileData.getName(), projectileData);
	}
	
	public ProjectileData getProjectileData(String projectileName){
		return myProjectileDataMap.get(projectileName);
	}
	
	public void updateTowerData(String originalName, ProjectileData updatedProjectile){
		myProjectileDataMap.remove(originalName);
		createProjectileData(updatedProjectile);
	}
}