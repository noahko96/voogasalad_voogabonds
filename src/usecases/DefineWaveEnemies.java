package usecases;

import java.util.HashMap;
import java.util.Map;

public class DefineWaveEnemies {
    private MockWave myWave;
    
    public DefineWaveEnemies() {
        myWave = new MockWave(1);
        Map<String,Integer> waveEnemies = defineWaveEnemies();
        myWave.setWaveEnemyTypes(waveEnemies);
    }
    
    private static Map<String,Integer> defineWaveEnemies() {
        Map<String,Integer> waveEnemyMap = new HashMap<String,Integer>();
        waveEnemyMap.put("Enemy1", 20);
        waveEnemyMap.put("Enemy2", 50);
        waveEnemyMap.put("Enemy3", 10);
        
        return waveEnemyMap;
    }
    
}
