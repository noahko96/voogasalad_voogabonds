package engine.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import engine.IObserver;
import engine.model.components.IComponent;
import engine.model.components.IModifiableComponent;
/**
 * 
 * @author matthewfaw 
 *
 */
public class ConcreteEntity implements IEntity {
	private transient ArrayList<IObserver<IEntity>> myObservers;
	private transient ArrayList<IComponent> myComponents;
	private String myID;
	
	/**
	 * This object should only be constructed in this
	 * package, since components should be constructed 
	 * in the Factory
	 */
	ConcreteEntity()
	{
		myID = UUID.randomUUID().toString();
		myComponents = new ArrayList<IComponent>();
		myObservers = new ArrayList<IObserver<IEntity>>();
	}

	@Override
	public String getId() {
		return myID;
	}
	
	@Override
	public void setId(String uniqueID) {
		myID = uniqueID;
	}

	@Override
	public void addComponent(IModifiableComponent aComponent) {
		myComponents.add(aComponent);
		aComponent.setEntity(this);
	}

	@Override
	public void delete() {
		for (IComponent c: myComponents)
			c.delete();
		
		myComponents.removeAll(myComponents);
		
	}
	
	@Override
	public List<IComponent> getComponents() {
		return myComponents;
	}

	//********************IObservable interface***********//
	@Override
	public void attach(IObserver<IEntity> aObserver) {
		myObservers.add(aObserver);
	}

	@Override
	public void detach(IObserver<IEntity> aObserver) {
		myObservers.remove(aObserver);
	}

	@Override
	public void notifyObservers() {
		myObservers.stream().forEach(o -> o.update(this));
	}

}
