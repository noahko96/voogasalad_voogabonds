package usecases;

import java.util.Map;
import authoring.authoring_interfaces.IWave;

public class MockWave implements IWave {
    private int myWaveNumber;
    private Map<String,Integer> myWaveEnemyTypes;
    
    public MockWave(int waveNumber) {
        myWaveNumber = waveNumber;
    }
    
    public void setWaveEnemyTypes(Map<String,Integer> waveEnemyTypes) {
        myWaveEnemyTypes = waveEnemyTypes;
    }

    @Override
    public Map<String, Integer> getWaveEnemyNames () {
        return myWaveEnemyTypes;
    }
    
    @Override
    public int getWaveNumber () {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getWavePattern () {
        // TODO Auto-generated method stub
        return null;
    }

    
}
