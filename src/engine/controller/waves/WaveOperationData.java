package engine.controller.waves;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import authoring.model.IReadableData;
import authoring.model.WaveData;
/**
 * This class stores the sequence of the waves
 * @author owenchung
 *
 */
public class WaveOperationData implements IReadableData {
	Map<Integer, WaveData> myWaveDataMap;
	
	Iterator<Entry<Integer, WaveData>> myIterator;
	
	public WaveOperationData(Map<Integer, WaveData> wavedatamap){
		myWaveDataMap = wavedatamap;
		myIterator = myWaveDataMap.entrySet().iterator();
	}
	public WaveData getNextWave(){
		if (myIterator.hasNext()){
			WaveData ret = myIterator.next().getValue();
			myIterator.remove();
			return ret;
		}else{
			return null;
		}
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
