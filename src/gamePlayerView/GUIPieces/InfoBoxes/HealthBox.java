package gamePlayerView.GUIPieces.InfoBoxes;

import engine.IObservable;
import engine.IObserver;
import engine.model.components.viewable_interfaces.IViewableHealth;
import engine.model.playerinfo.IViewablePlayer;
import gamePlayerView.interfaces.IEnemiesKilledAcceptor;
import gamePlayerView.interfaces.IHealthAcceptor;

/**
 * @author Guhan Muruganandam
 * 
 */

public class HealthBox extends InfoBox implements IObserver<IViewableHealth>, IHealthAcceptor {
	
	public HealthBox(){
		myDisplay = makeDisplay("Health: ");
	}
	
	
	public void acceptHealth(IObservable<IViewableHealth> aComponent) {
		aComponent.attach(this);
		//update(aPlayer);
	}

	@Override
	public void update(IViewableHealth aChangedObject) {
		myOutput.setText(Double.toString(aChangedObject.getCurrHealth()));
	}

	@Override
	public void remove(IViewableHealth aRemovedObject) {
		myOutput.setText(Integer.toString(0));

	}
}
