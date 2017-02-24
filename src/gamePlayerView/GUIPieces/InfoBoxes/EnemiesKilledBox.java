package gamePlayerView.GUIPieces.InfoBoxes;

import engine.IObservable;
import engine.IObserver;
import engine.model.playerinfo.IViewablePlayer;
import gamePlayerView.interfaces.IEnemiesKilledAcceptor;

/**
 * @author Guhan Muruganandam
 * 
 */

public class EnemiesKilledBox extends InfoBox implements IObserver<IViewablePlayer>, IEnemiesKilledAcceptor {
	
	public EnemiesKilledBox(){
		myDisplay = makeDisplay("Enemies Killed: ");
	}

	@Override
	public void acceptEnemiesKilled(IObservable<IViewablePlayer> aPlayer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(IViewablePlayer aChangedObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(IViewablePlayer aRemovedObject) {
		// TODO Auto-generated method stub
		
	}

}
