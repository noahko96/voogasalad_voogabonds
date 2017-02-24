package engine.model.strategies;

import engine.IObservable;
import engine.model.playerinfo.IModifiablePlayer;

abstract public class AbstractWinStrategy<A> extends AbstractWinLoseStrategy<A> {
	
	public AbstractWinStrategy(IModifiablePlayer player) {
		super(player);
	}
	
	public AbstractWinStrategy(IModifiablePlayer player, IObservable<A> toObserve) {
		super(player, toObserve);
	}

	@Override
	public void update(A observed) {
		if (checkCondition(observed))
			for (IModifiablePlayer p: myPlayers)
				p.win();
	}

	/**
	 * Check the if the win condition is satisfied
	 * @param observed
	 * @return true iff condition is satisfied
	 */
	abstract protected boolean checkCondition(A observed);
}
