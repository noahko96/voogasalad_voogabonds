package engine.model.systems;

/**
 * 
 * @author Weston
 *
 * @param <A> the type of component this system stores
 */
//public class AbstractSystem<A extends IComponent> implements ISystem {
//	private List<A> myComponents;
//	
//	public AbstractSystem() {
//		myComponents = new ArrayList<A>();
//	}
//	
//	protected List<A> getComponents() {
//		return myComponents;
//	}
//	
//	protected A getComponent(IComponent component) {
//		return (component != null) ? getComponent(component.getEntity()) : null;
//	}
//	
//	protected A getComponent(IEntity entity) {
//		for (A a: myComponents)
//			if (a.getEntity().equals(entity))
//				return a;
//		return null;
//	}
//	
//	/************ Attach and detach component methods ************/
//	public void attachComponent(A aComponent) {
//		myComponents.add(aComponent);
//	}
//	public void detachComponent(A aComponent) {
//		myComponents.remove(aComponent);
//	}
//	
//}
