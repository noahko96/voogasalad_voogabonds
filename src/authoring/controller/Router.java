package authoring.controller;
import authoring.view.tabs.WaveTab;
import authoring.view.tabs.entities.EntityTab;
import authoring.view.tabs.LevelTab;
import authoring.view.tabs.RulesTab;

public class Router {
	private MapDataContainer mdc = new MapDataContainer();
	private EntityDataContainer edc = new EntityDataContainer();
	private PlayerDataContainer pldc = new PlayerDataContainer();
	private LevelDataContainer ldc = new LevelDataContainer();
	private WaveDataContainer wadc = new WaveDataContainer();
	private String gameTitle;
	
	public void link(EntityTab e, LevelTab l, WaveTab w){
		//Listeners for EntityTab
		mdc.attach(e); //Entity listener
		edc.attach(e);
		//Listeners for WaveTab
		mdc.attach(w); //Terrain listener
		edc.attach(w); //Entity listener
		wadc.attach(w);
		//Listeners for levelTab
		wadc.attach(l);
		ldc.attach(l);
		
		
		//Refresh upon loading of a new game
		mdc.notifyObservers();
		edc.notifyObservers();
		wadc.notifyObservers();
		ldc.notifyObservers();
	}
	
	public void clearContainers(){
		mdc = new MapDataContainer();
		edc = new EntityDataContainer();
		pldc = new PlayerDataContainer();
		ldc = new LevelDataContainer();
		wadc = new WaveDataContainer();
		gameTitle = null;
	}
	
	
	public MapDataContainer getMapDataContainer(){
		return mdc;
	}
	
	public PlayerDataContainer getPlayerDataContainer(){
		return pldc;
	}
	
	public WaveDataContainer getWaveDataContainer(){
		return wadc;
	}
	
	public LevelDataContainer getLevelDataContainer(){
		return ldc;
	}
	
	public EntityDataContainer getEntityDataContainer(){
		return edc;
	}
	
	public void setGameTitle(String title){
		gameTitle = title;
	}
	
	public String getGameTitle(){
		return gameTitle;
	}
}
