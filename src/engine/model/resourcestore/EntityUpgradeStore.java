package engine.model.resourcestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import authoring.model.EntityData;
import engine.model.entities.EntityNode;

/**
 * Entity Upgrade Store that stores the hierarchies among entities
 * @author owenchung 
 * @author matthewfaw
 */


public class EntityUpgradeStore implements IEntityUpgradeStore {
	private ArrayList<EntityNode> myBaseEntities;
	private Map<String, EntityNode> myConstructedEntityNodes;
	
	public EntityUpgradeStore(List<EntityData> aEntityInfoList) {
		myBaseEntities = new ArrayList<EntityNode>();
		myConstructedEntityNodes = new HashMap<String, EntityNode>();
		Stack<EntityNode> entityNodes = constructNodes(aEntityInfoList);
		connectNodes(entityNodes);
	}
	
	
	/**
	 * A helper method to construct all of the node objects from the entity data objects
	 * This method only construct the nodes, it does not connect them
	 * 
	 * Note that this method also populate the myConstructedEntityNodes map, 
	 * and the myBaseEntities map
	 */
	private Stack<EntityNode> constructNodes(List<EntityData> aEntityInfoList)
	{
		Stack<EntityNode> entityNodes = new Stack<EntityNode>();
		for (EntityData entityData: aEntityInfoList) {
			EntityNode entityNode = new EntityNode(entityData);
			entityNodes.add(entityNode);
			myConstructedEntityNodes.put(entityNode.getName(), entityNode);
			if (entityData.getBuyPrice() >= 0) {
				myBaseEntities.add(entityNode);
			}
		}
		return entityNodes;
	}

	/**
	 * This method connects all of the Entity Nodes in the input stack
	 * Note that this method depends on myConstructedEntityNodes map being fully
	 * populated in order to easily connect the graph
	 * @param aEntityNodes
	 */
	private void connectNodes(Stack<EntityNode> aEntityNodes)
	{
		while (!aEntityNodes.isEmpty()) {
			EntityNode entityNode = aEntityNodes.pop();
			EntityData entityData = entityNode.getEntityData();
			if (!entityData.getUpgrades().isEmpty()) {
				for (String nodeName: entityData.getUpgrades().keySet()) {
					entityNode.addUpgradeTo(myConstructedEntityNodes.get(nodeName));
				}
			}
		}
	}

	/**
	 * get all of the base entities data in the game
	 */
	@Override
	public List<EntityData> getBaseEntitesData() {
		return convertEntityNodesToEntityData(myBaseEntities);
	}

	/**
	 * return the price of a entity if it's a base entity, otherwise return -1
	 * @param entity
	 * @return
	 */
	public int getPrice(String aEntityName) {
		for (EntityNode en : myBaseEntities) {
			if (en.getName() == aEntityName) {
				return myConstructedEntityNodes.get(aEntityName).getEntityData().getBuyPrice();
			}
		}
		return -1;	
	}
	
	public List<EntityData> getPossibleUpgrades(EntityData entity) 
	{
		List<EntityNode> upgradeNodes = myConstructedEntityNodes.get(entity.getName()).getUpgradeTos();
		return convertEntityNodesToEntityData(upgradeNodes);
	}
	
	/**
	 * A helper method to convert a list of EntityNodes to a list of EntityData
	 * @param aEntityNodes
	 * @return corresponding list of entity data
	 */
	private List<EntityData> convertEntityNodesToEntityData(List<EntityNode> aEntityNodes)
	{
		return aEntityNodes.stream().map(entityNode -> entityNode.getEntityData()).collect(Collectors.toList());
	}

}
