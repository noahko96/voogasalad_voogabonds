package authoring.model;

import java.util.Stack;

/**
 * This class wraps the stack of WaveData in a given level
 * @author owenchung
 *
 */

public class OneLevelData {
	
	private Stack<WaveData> waveDataStack;
	
	public OneLevelData() {
		waveDataStack = new Stack<WaveData>();
	}
	
	public void addWaveData(WaveData waveData) {
		this.waveDataStack.add(waveData);
	}
	public WaveData peekNextWaveData() {
		return waveDataStack.peek();
	}
	
	public WaveData popNextWaveData() {
		return waveDataStack.pop();
	}
	
	public void clearLevelData() {
		waveDataStack.clear();
	}
	

}

