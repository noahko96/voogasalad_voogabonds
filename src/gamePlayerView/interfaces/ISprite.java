package gamePlayerView.interfaces;

import engine.model.playerinfo.IViewablePlayer;

public interface ISprite {
	
	void acceptSprite(IViewablePlayer aPlayer);
	void setPosition();
	void setOrientation();
	void setImage();
}
