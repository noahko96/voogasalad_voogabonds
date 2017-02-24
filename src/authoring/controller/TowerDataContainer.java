package authoring.controller;

import authoring.model.IReadableData;
import authoring.model.TowerData;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import java.util.HashMap;
import java.util.AbstractMap;

@Deprecated
public class TowerDataContainer implements IDataContainer {

    private AbstractMap<String, TowerData> myTowerDataMap = new HashMap<String, TowerData>();

    /**
     * @return
     */
    public AbstractMap<String, TowerData> finalizeTowerDataMap(){
        return myTowerDataMap;
    }

    /**
     * @param towerData
     */
    public void createTowerData(TowerData towerData){
        myTowerDataMap.put(towerData.getName(), towerData);
    }

    /**
     * @return
     */
    public int getNumTowers() {
        return myTowerDataMap.size();
    }

    /**
     * @param towerName
     * @return
     */
    public TowerData getTowerData(String towerName){
        return myTowerDataMap.get(towerName);
    }

    /**
     * @param originalName
     * @param updatedTower
     */
    public void updateTowerData(String originalName, TowerData updatedTower){
        myTowerDataMap.remove(originalName);
        createTowerData(updatedTower);
    }


    @Override
    public IReadableData getObjectData (String name) {
        return getTowerData(name);
    }

    @Override
    public void createObjectData (IReadableData data) {
        createTowerData((TowerData)data);
    }

    @Override
    public void updateObjectData (String oldName, IReadableData data) throws Exception {
        if (!myTowerDataMap.containsKey(oldName)) {
            throw new Exception("This tower has not been created.");
        }
        updateTowerData(oldName, (TowerData)data);
    }

    @Override
    public boolean deleteObjectData (String name) {
        if (!myTowerDataMap.containsKey(name)) {
            return false;
        }
        myTowerDataMap.remove(name);
        return true;
    }
}
