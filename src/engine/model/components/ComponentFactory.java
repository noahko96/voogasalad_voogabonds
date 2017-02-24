package engine.model.components;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import authoring.model.ComponentData;
import authoring.model.Hide;
import engine.model.entities.IEntity;
import engine.model.systems.ISystem;
import gamePlayerView.gamePlayerView.Router;
import utility.Point;
/**
 * Creates all the components
 * @author owenchung
 *
 */
public class ComponentFactory {
        @Hide
	private static final String COMPONENT_PATH = "engine.model.components.concrete.";
	private List<ISystem<?>> mySystems;
	private Router myRouter;

	public ComponentFactory(List<ISystem<?>> systems, Router router) {
		mySystems = systems;
		myRouter = router;
	}
	
	/**
	 * Constructing a component using reflection
	 * @param entity
	 * @param compdata
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public IModifiableComponent constructComponent(IEntity aEntity, ComponentData compdata, Point location) throws UnsupportedOperationException {
		try {
//			//System.out.println(compdata.getComponentName());
			Class<?> tmpclass = Class.forName(COMPONENT_PATH+compdata.getComponentName());
			Constructor<?>[] constructors = tmpclass.getConstructors();
			// Note: Assuming only one constructor
			Class<?> [] arguments = constructors[0].getParameterTypes();
			List<Object> objectsToAttach = new ArrayList<Object>();
			for (Class<?> arg : arguments) {
				// attach appropriate system to argument
				ISystem<?> sysToAdd = getSystemToAttach(arg);
				if (sysToAdd != null) {
					objectsToAttach.add(getSystemToAttach(arg));
					continue;
				}
				// check if arg is Router, if so, attach myRouter
				Router routerToAdd = getRouterToAttach(arg);
				if (routerToAdd != null) {
					objectsToAttach.add(getRouterToAttach(arg));
					continue;
				}
				if (Point.class.isAssignableFrom(arg)) {
					objectsToAttach.add(getPointToAttach(arg, location));
					continue;
				}
				if (arg.isInstance(aEntity)) {
					objectsToAttach.add(aEntity);
					continue;
				}
				objectsToAttach.add(compdata);
				
			}
			try {
				return (IModifiableComponent) constructors[0].newInstance(objectsToAttach.toArray());
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new UnsupportedOperationException(COMPONENT_PATH+compdata.getComponentName());
			}
		} catch (ClassNotFoundException e) {
			throw new UnsupportedOperationException(COMPONENT_PATH+compdata.getComponentName());
		}
		
		
		
	}
	
	private Point getPointToAttach(Class<?> arg, Point location) {
		if (arg.isInstance(location)) {
			return location;
		} else {
			return null;
		}
	}


	private ISystem<?> getSystemToAttach(Class<?> arg) {
		for (ISystem<?> sys : mySystems) {
			if ( arg.isInstance(sys) ) {
				return sys;
			}
		}
		// SHOULD NOT EVER HAPPEN
		return null;
	}
	
	private Router getRouterToAttach(Class<?> arg) {
		if (arg.isInstance(myRouter)) {
			return myRouter;
		}
		return null;
	}
	
	public void addSystem(ISystem<?> systemToAdd) {
		mySystems.add(systemToAdd);
	}
}
