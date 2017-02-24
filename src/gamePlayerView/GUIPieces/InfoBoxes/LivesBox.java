package gamePlayerView.GUIPieces.InfoBoxes;

import engine.IObservable;
import engine.IObserver;
import engine.model.playerinfo.IViewablePlayer;
import gamePlayerView.interfaces.IPlayerAcceptor;

/**
 * @author Guhan Muruganandam
 */

/**
 * UI feature for Lives text box and Label
 */

public class LivesBox extends InfoBox implements IObserver<IViewablePlayer>, IPlayerAcceptor {
	
	public LivesBox(){
		myDisplay = makeDisplay("Lives: ");
	}

	@Override
	public void acceptPlayer(IObservable<IViewablePlayer> aPlayer) {
		aPlayer.attach(this);
		//update(aPlayer);
	}

	@Override
	public void update(IViewablePlayer aChangedObject) {
		myOutput.setText(Integer.toString(aChangedObject.getLivesRemaining()));
	}

	@Override
	public void remove(IViewablePlayer aRemovedObject) {
		myOutput.setText("-");
	}

}
