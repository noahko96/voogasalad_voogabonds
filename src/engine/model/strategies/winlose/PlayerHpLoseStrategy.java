package engine.model.strategies.winlose;

import engine.model.playerinfo.IModifiablePlayer;
import engine.model.playerinfo.IViewablePlayer;
import engine.model.strategies.AbstractLoseStrategy;

public class PlayerHpLoseStrategy extends AbstractLoseStrategy<IViewablePlayer> {
	
	public PlayerHpLoseStrategy(IModifiablePlayer player) {
		super(player);
	}

	@Override
	protected boolean checkCondition(IViewablePlayer observed) {
		return observed.getLivesRemaining() <= 0;
	}

}
