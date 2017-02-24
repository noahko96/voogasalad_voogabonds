package gamePlayerView.GUIPieces.InfoBoxes;

import engine.IObservable;
import engine.IObserver;
import engine.model.playerinfo.IViewablePlayer;
import gamePlayerView.interfaces.IPlayerAcceptor;

/**
 * UI feature for Waves text box and Label
 * @author Guhan Muruganandam
 *
 */
public class WavesBox extends InfoBox implements IObserver<IViewablePlayer>,IPlayerAcceptor {
	
	public WavesBox(){
		myDisplay = makeDisplay("Waves: ");
	}

	public void acceptPlayer(IObservable<IViewablePlayer> aObservable) {
		aObservable.attach(this);
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
