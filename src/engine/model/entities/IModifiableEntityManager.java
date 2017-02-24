package engine.model.entities;

public interface IModifiableEntityManager {
	public void addEntity(String id, ConcreteEntity entity);
	public void removeEntity(String id);
}
