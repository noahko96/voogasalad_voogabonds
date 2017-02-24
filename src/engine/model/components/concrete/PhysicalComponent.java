package engine.model.components.concrete;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import authoring.model.ComponentData;
import authoring.model.Hide;
import engine.IObserver;
import engine.model.components.AbstractComponent;
import engine.model.components.viewable_interfaces.IViewablePhysical;
import engine.model.entities.IEntity;
import engine.model.strategies.IPhysical;
import engine.model.systems.PhysicalSystem;
import gamePlayerView.gamePlayerView.Router;
import javafx.util.Pair;
import utility.Point;

/**
 * Manages the information relevant to the physical information of an entity
 * Physical components contain information relevant to existing on a grid, and being displayed
 * 
 * @author matthewfaw
 * @author Weston
 * @author owenchung (edits)
 *
 */
public class PhysicalComponent extends AbstractComponent implements IPhysical, IViewablePhysical {
	private String myImagePath;
	private double myImageSize;
	private List<String> myValidTerrains;
	
	@Hide
	private transient List<IObserver<IViewablePhysical>> myObservers;
	@Hide
	private Point myPosition;
	@Hide
	private double myHeading;
	@Hide
	private transient PhysicalSystem mySystem;

		
	public PhysicalComponent(IEntity aEntity, PhysicalSystem physical, Router router, ComponentData data, Point position) {
		super(aEntity, router);
		mySystem = physical;
		
		myImagePath = data.getFields().get("myImagePath");
		myImageSize = Double.parseDouble(data.getFields().get("myImageSize"));
		myValidTerrains = Arrays.asList(data.getFields().get("myValidTerrains").trim().split("\\s*,\\s*"));
		
		myObservers = new ArrayList<IObserver<IViewablePhysical>>();

		myPosition = new Point(0, 0);
		myHeading = 0;
		
		physical.attachComponent(this);
		router.createNewViewableComponent(this);
		setPosition(position);
	}
	
	/******************IViewable interface********/
	@Override
	public Point getPosition() {
		return myPosition;
	}

	@Override
	public double getHeading() {
		return myHeading;
	}

	@Override
	public double getSize()
	{
		return myImageSize;
	}

	@Override
	public String getImagePath()
	{
		return myImagePath;
	}
	
	/***************IPhysical interface************/
	@Override
	public List<String> getValidTerrains() {
		return myValidTerrains;
	}
	
	@Override
	public void setPosition(Point position) {
		myPosition = position;
		notifyObservers();
	}
	
	@Override
	public void setPosition(Pair<Double, Point> p) {
		myHeading = p.getKey();
		while (Math.abs(myHeading) > 180) {
			myHeading -= 360 * (myHeading / Math.abs(myHeading));
		}
		if (myPosition != null)
			myPosition = p.getValue();
		notifyObservers();
	}

	/******************IObservable interface********/
	@Override
	public void attach(IObserver<IViewablePhysical> aObserver) {
		myObservers.add(aObserver);
	}

	@Override
	public void detach(IObserver<IViewablePhysical> aObserver) {
		myObservers.remove(aObserver);
	}

	@Override
	public void notifyObservers() {
		for (IObserver<IViewablePhysical> o: myObservers)
			if (this != null)
				o.update(this);
	}

	/***** IComponent interface ******/

	@Override
	public void distributeInfo() {
		getRouter().distributeViewableComponent(this);
	}

	@Override
	public void delete() {
		mySystem.detachComponent(this);
		myPosition = null;
		myObservers.forEach(observer -> observer.remove(this));
	}
}