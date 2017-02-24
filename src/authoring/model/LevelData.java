package authoring.model;

import java.util.List;
import java.util.*;

public class LevelData {
	
	private String name;
	private List<WaveData> waveDataList;
	
	public LevelData()
	{
		waveDataList = new ArrayList<WaveData>();
	}
	
	public void setLevelName(String name){
		this.name = name;
	}
	
	public String getLevelName(){
		return name;
	}
	
	public void addWaveDataListToList(WaveData waveData){
		this.waveDataList.add(waveData);
	}
	
	public WaveData popNextWaveData()
	{
		return waveDataList.remove(0);
	}

	public List<WaveData> getWaveDataList(){
		return waveDataList;
	}
	
	public boolean isEmpty()
	{
		return waveDataList.isEmpty();
	}
	
	public void removeWaveDataFromList(WaveData waveData){
		this.waveDataList.remove(waveData);
	}
	
	public void clearCurrentWaveData(){
		this.waveDataList.clear();
	}

}
