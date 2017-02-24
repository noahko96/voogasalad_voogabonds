package engine.model.strategies.winlose;

import engine.model.playerinfo.IModifiablePlayer;
import engine.model.strategies.AbstractLoseStrategy;

public class PlayerNoMoneyLoseStrategy extends AbstractLoseStrategy<IModifiablePlayer> {
	public PlayerNoMoneyLoseStrategy(IModifiablePlayer player) {
		super(player);
	}

	@Override
	protected boolean checkCondition(IModifiablePlayer observed) {
		return observed.getAvailableMoney() <= 0;
	}

}
