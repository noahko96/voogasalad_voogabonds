package engine.model.strategies;

import java.util.ArrayList;
import java.util.List;

import engine.IObservable;
import engine.IObserver;
import engine.model.playerinfo.IModifiablePlayer;

/**
 * A class to take the player list out of the concrete IWinLoseStrategy objects
 * @author Weston
 *
 * @param <A> the type of the observed objects that the condition will check
 */
abstract public class AbstractWinLoseStrategy<A> implements IObserver<A>, IWinLoseStrategy {
	List<IModifiablePlayer> myPlayers;
	
	public AbstractWinLoseStrategy(IModifiablePlayer p) {
		myPlayers = new ArrayList<IModifiablePlayer>();
		subscribe(p);
	}
	
	public AbstractWinLoseStrategy(IModifiablePlayer p, IObservable<A> toObserve){
		this(p);
		toObserve.attach(this);
	}

	@Override
	public void subscribe(IModifiablePlayer p) {
		myPlayers.add(p);
	}
	

	@Override
	public void unsubscribe(IModifiablePlayer p) {
		myPlayers.remove(p);
		
	}
	
	@Override
	public void remove(A observed) {
		update(observed);
	}

}
