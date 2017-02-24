package engine.model.collision_detection;

import java.util.ArrayList;
import java.util.List;


import engine.IObservable;
import engine.IObserver;
import engine.model.components.IComponent;
import engine.model.entities.IEntity;
import engine.model.systems.ISystem;

/**
 * All objects must be subscribed as ICollidable
 * NOT IObservables.
 *
 */
@Deprecated
public class CollisionDetectionTemp implements IObserver<ICollidable>, IObservable<ISystem>, ISystem {
	
	private ICollisionHandler collisionHandler;
	private List<ICollidable> myCollidables;
	private List<IObserver<ISystem>> myObservers;
	//private List<IRegisterable> myRegisterables;
	
	public CollisionDetectionTemp() {
		//TODO: initialize all collidables
		myCollidables = new ArrayList<ICollidable>();
		//TODO: initialize with all observers
		myObservers = new ArrayList<IObserver<ISystem>>();
		//TODO: initialize collision handler
		collisionHandler = new CollisionHandler();
		// Init registerables
		//myRegisterables = new ArrayList<IRegisterable>();
	}

	/**
	 * Determine if observable a intersects with observable b
	 * @param a
	 * @param b
	 * @return
	 */
	private boolean intersects(ICollidable a, ICollidable b) {
		/*
		double a_x = a.getPosition().getX();
		double a_y = a.getPosition().getY();
		double a_r = a.getCollisionRadius();
		
		double b_x = b.getPosition().getX();
		double b_y = b.getPosition().getY();
		double b_r = b.getCollisionRadius();
		
		return Math.pow(a_r - b_r, 2) <= 
				Math.pow(a_x - b_x, 2) + 
				Math.pow(a_y - b_y, 2);
		*/
		return false;
	}
	
	
	//************************************Observer interface****************************//

	/**
	 * On collision, this system will be notified and
	 * this method will be called.
	 */
	@Override
	public void update(ICollidable movedObservable) {
		for(ICollidable observable: myCollidables) {
			if (intersects(movedObservable, observable)) {
				// notify collision detection strategy
				collisionHandler.handleCollision(movedObservable, observable);
			}
		}
	}
	
	//************************************Observable interface****************************//
	@Override
	public void attach(IObserver<ISystem> aObserver) {
		myObservers.add(aObserver);
	}

	@Override
	public void detach(IObserver<ISystem> aObserver) {
		myObservers.remove(aObserver);
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		myObservers.forEach(observer -> observer.update(this));
	}

	@Override
	public void remove(ICollidable aRemovedObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List getComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getComponent(IComponent component) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getComponent(IEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void attachComponent(Object aComponet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detachComponent(Object aComponent) {
		// TODO Auto-generated method stub
		
	}
	
	//************************************ISystem interface****************************//
//	@Override
//	public void register(IRegisterable registerable) {
//		myRegisterables.add(registerable);
//	}
//
//	@Override
//	public void unregister(IRegisterable registerable) {
//		myRegisterables.remove(registerable);
//	}

}
