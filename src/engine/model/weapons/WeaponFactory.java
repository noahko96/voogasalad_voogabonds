package engine.model.weapons;

import authoring.model.ProjectileData;
import authoring.model.WeaponData;
import engine.controller.timeline.TimelineController;
import engine.model.data_stores.DataStore;
import engine.model.game_environment.MapMediator;
import engine.model.projectiles.ProjectileFactory;

/**
 * A class to crate new weapons without having to pass the same arguments many times.
 * @author Weston
 *
 */
@Deprecated
public class WeaponFactory {
	private ProjectileFactory myProjectileFactory;
	private DataStore<WeaponData> myWeapons;
	private MapMediator myMap;
	
	public WeaponFactory(DataStore<WeaponData> weapons, DataStore<ProjectileData> projMap, TimelineController time, MapMediator map) {
		myMap = map;
		myWeapons = weapons;
		
		myProjectileFactory = new ProjectileFactory(projMap, time, myMap);
	}

	public Weapon newWeapon(String name, IKillerOwner owner) {
		return new Weapon(myWeapons.getData(name), owner, myProjectileFactory, myMap);
	}
}
