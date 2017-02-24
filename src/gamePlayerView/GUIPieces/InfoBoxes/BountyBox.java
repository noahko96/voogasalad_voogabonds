package gamePlayerView.GUIPieces.InfoBoxes;

import engine.IObservable;
import engine.IObserver;
import engine.model.components.viewable_interfaces.IViewableBounty;
import engine.model.components.viewable_interfaces.IViewableHealth;
import engine.model.playerinfo.IViewablePlayer;
import gamePlayerView.interfaces.IBountyAcceptor;
import gamePlayerView.interfaces.IEnemiesKilledAcceptor;
import gamePlayerView.interfaces.IHealthAcceptor;

/**
 * @author Guhan Muruganandam
 * 
 */

public class BountyBox extends InfoBox implements IObserver<IViewableBounty>, IBountyAcceptor {
	
	public BountyBox(){
		myDisplay = makeDisplay("Bounty: ");
	}
	
	
	public void acceptBounty(IObservable<IViewableBounty> aComponent) {
		aComponent.attach(this);
		//update(aPlayer);
	}

	@Override
	public void update(IViewableBounty aChangedObject) {
		myOutput.setText(Double.toString(aChangedObject.getBounty()));
	}

	@Override
	public void remove(IViewableBounty aRemovedObject) {
		myOutput.setText(Integer.toString(0));

	}
}
