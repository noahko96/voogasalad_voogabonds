package authoring.controller;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import authoring.model.WaveData;
import engine.IObservable;
import engine.IObserver;

public class WaveDataContainer extends Container implements IObservable<Container>{
	private transient ArrayList<IObserver<Container>> myObservers = new ArrayList<IObserver<Container>>();
	
	/**
	 * HashMap which maintains insertion order. Insertion order is important because
	 * waves all have an attribute 'timeBeforeWave', which means that they are
	 * relative to what comes before them. As a result, we need to make sure that
	 * waves are in the map in the order in which they were created.
	 */
	private AbstractMap<String, WaveData> myWaveMap = new LinkedHashMap<String, WaveData>();
	
	public void createNewWave(String name, WaveData wave){
		myWaveMap.put(name, wave);
		notifyObservers();
	}
	
	public WaveData getWaveData(String name){
		return myWaveMap.get(name);
	}
	
	public AbstractMap<String, WaveData> getWaveMap(){
		return myWaveMap;
	}
	
	public AbstractMap<String, WaveData> finalizeWaveDataMap(){
		return myWaveMap;
	}
	
	/**
	 * The function is this complex because it's impossible to update the key of a
	 * LinkedHashMap (which is necessary if the name gets updated). As a result, 
	 * we need to go through the map again and copy over all old values, making sure to put 
	 * it in the same order.
	 * @param wave
	 * @param originalName
	 */
	public void updateWave(WaveData wave, String originalName){
		if (originalName.equals(wave.getName())){
			//Find original WaveData object, simply update it
			myWaveMap.put(wave.getName(), wave);
		}else{
			LinkedHashMap<String, WaveData> newMap = new LinkedHashMap<String, WaveData>();
			for (String name: myWaveMap.keySet()){
				if (name.equals(originalName)){
					//TODO: Put new object with potentially new name into newMap
					newMap.put(wave.getName(), wave);
				}else{
					//Put same object into newMap
					newMap.put(name, myWaveMap.get(name));
				}
			}
			myWaveMap = newMap;
		}
	}

	/*
	 * IObservable functions
	 */
	@Override
	public void attach(IObserver<Container> observer) {
		myObservers.add(observer);
	}

	@Override
	public void detach(IObserver<Container> observer) {
		myObservers.remove(observer);	
	}

	@Override
	public void notifyObservers() {
		for (IObserver<Container> observer: myObservers){
			observer.update(this);
		}
	}

	public void removeWaveData(String selected) throws Exception {
		WaveData wave = myWaveMap.remove(selected);
		if (wave == null){
			throw new Exception("Wave does not exist.");
		}
	}
}
