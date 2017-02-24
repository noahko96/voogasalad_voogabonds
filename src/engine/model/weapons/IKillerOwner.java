package engine.model.weapons;

import engine.model.playerinfo.IModifiablePlayer;
import utility.Point;

/**
 * An interface to represent the creator/owner of something that does damage and gets kills, so that
 * information about said kills can be relayed to the owner.
 * @author Weston
 *
 */
@Deprecated
public interface IKillerOwner {
	
	abstract public DamageInfo notifyDestroy(DamageInfo result);
	abstract public double getHeading();
	abstract public Point getPosition();
	abstract public IModifiablePlayer getOwner();
}
