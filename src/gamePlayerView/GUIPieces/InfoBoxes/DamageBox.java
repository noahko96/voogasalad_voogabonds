package gamePlayerView.GUIPieces.InfoBoxes;

import engine.IObservable;
import engine.IObserver;
import engine.model.components.viewable_interfaces.IViewableBounty;
import engine.model.components.viewable_interfaces.IViewableDamageDealer;
import engine.model.components.viewable_interfaces.IViewableHealth;
import engine.model.playerinfo.IViewablePlayer;
import gamePlayerView.interfaces.IBountyAcceptor;
import gamePlayerView.interfaces.IDamageAcceptor;
import gamePlayerView.interfaces.IEnemiesKilledAcceptor;
import gamePlayerView.interfaces.IHealthAcceptor;

/**
 * @author Guhan Muruganandam
 * 
 */

public class DamageBox extends InfoBox implements IObserver<IViewableDamageDealer>, IDamageAcceptor {
	
	public DamageBox(){
		myDisplay = makeDisplay("Damage: ");
	}
	
	
	public void acceptDamage(IObservable<IViewableDamageDealer> aComponent) {
		aComponent.attach(this);
		//update(aPlayer);
	}

	@Override
	public void update(IViewableDamageDealer aChangedObject) {
		myOutput.setText(Integer.toString(aChangedObject.getDamage()));
	}

	@Override
	public void remove(IViewableDamageDealer aRemovedObject) {
		myOutput.setText(Integer.toString(0));

	}
}
