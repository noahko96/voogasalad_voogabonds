package authoring.controller;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

import authoring.model.EntityData;
import authoring.model.EntityList;
import authoring.model.IReadableData;
import engine.IObserver;
import authoring.model.EntityData;
import engine.IObserver;
import engine.IObservable;


public class EntityDataContainer extends Container implements IObservable<Container>{

    private AbstractMap<String, EntityData> myEntityDataMap = new HashMap<String, EntityData>();
    ArrayList<IObserver<Container>> myObservers = new ArrayList<IObserver<Container>>();

    /**
     * @param EntityData
     */
    public void createEntityData(EntityData entityData) throws Exception{
        if (myEntityDataMap.containsKey(entityData.getName())){
            throw new Exception("An entity with this name already exists!");
        }
        myEntityDataMap.put(entityData.getName(), entityData);
        notifyObservers();
    }

    /**
     * @param EntityName
     * @return
     */
    public EntityData getEntityData(String EntityName){
        return myEntityDataMap.get(EntityName);
    }

    /**
     * @param originalName
     * @param updatedEntity
     */
    public void updateEntityData(String originalName, EntityData updatedEntity) throws Exception{
        myEntityDataMap.remove(originalName);
        createEntityData(updatedEntity);
        notifyObservers();
    }

    public void removeEntityData(String entityName) throws Exception {
        if (!myEntityDataMap.containsKey(entityName)) {
            throw new Exception("An entity with this name does not exist.");
        }
        myEntityDataMap.remove(entityName);
        notifyObservers();
    }

    /**
     * @return
     */
    public int getNumEntities() {
        return myEntityDataMap.size();
    }

    public AbstractMap<String, EntityData> getEntityDataMap(){
        return myEntityDataMap;
    }

    /**
     * IObservable functions
     */
    public void attach(IObserver<Container> observer){
        myObservers.add(observer);
    }

    public void detach(IObserver<Container> observer){
        myObservers.remove(observer);
    }

    public void notifyObservers(){
        for (IObserver<Container> observer: myObservers){
            observer.update(this);
        }
    }
}
