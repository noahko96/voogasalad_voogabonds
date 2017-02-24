package engine.model.strategies.winlose;

import engine.model.playerinfo.IModifiablePlayer;
import engine.model.strategies.AbstractLoseStrategy;

public class NeverLoseStrategy extends AbstractLoseStrategy<Object> {
	
	public NeverLoseStrategy(IModifiablePlayer player) {
		super(player);
	}

	@Override
	protected boolean checkCondition(Object o) {
		//Should never get called because this strat isn't observing anything.
		//Therefore, nothing should call its update method.
		return false;
	}
}
