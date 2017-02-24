package gamePlayerView.builders;

import java.util.ArrayList;
import java.util.Collection;

import gamePlayerView.GUIPieces.InfoBoxes.BountyBox;
import gamePlayerView.GUIPieces.InfoBoxes.DamageBox;
import gamePlayerView.GUIPieces.InfoBoxes.HealthBox;
import gamePlayerView.GUIPieces.MachineInfoView.MachineInfo;
import gamePlayerView.GUIPieces.MachineInfoView.SellUI;
import gamePlayerView.GUIPieces.MachineInfoView.TargetingButtons;
import gamePlayerView.GUIPieces.MachineInfoView.UpgradeUI;
import gamePlayerView.gamePlayerView.GamePlayerScene;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class EntityInfoBox implements IGUIPiece {
	
	private final MachineInfo myMachineInfo;
	private final UpgradeUI myUpgradeUI;
	private final TargetingButtons myTargetingButtons;
	private final SellUI mySellUI;
	private final DamageBox myDamageBox;
	private final HealthBox myHealthBox;
	private final BountyBox myBountyBox;
	private HBox myHbox;
	
	public EntityInfoBox(EntityInfoBoxBuilder builder){
		myMachineInfo = builder.getMyMachineInfo();
		myUpgradeUI = builder.getMyUpgradeUI();
		myTargetingButtons = builder.getMyTargetingButtons();
		mySellUI = builder.getMySellUI();
		myDamageBox = builder.getMyDamageBox();
		myHealthBox = builder.getMyHealthBox();
		myBountyBox = builder.getMyBountyBox();
		
		myHbox=new HBox();
		myHbox.setSpacing(10);
		myHbox.getChildren().addAll(myMachineInfo.getNode(),myTargetingButtons.getNode(),myUpgradeUI.getNode(),mySellUI.getNode(),
				myDamageBox.getNode(),myHealthBox.getNode(),myBountyBox.getNode());
	}

	@Override
	public Node getNode() {
		return myHbox;
	}
}