package gamePlayerView.GUIPieces.MachineInfoView;

import gamePlayerView.GUIPieces.InfoBoxes.EnemiesKilledBox;
import gamePlayerView.interfaces.IGUIPiece;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * @author Guhan Muruganandam
 * 
 */

public class TowerStatistics implements IGUIPiece {
	private HBox myTowerStatistics;
	public TowerStatistics(){
		myTowerStatistics=makeTowerStats();
	}
	
	private HBox makeTowerStats() {
		HBox towerStats=new HBox();
		EnemiesKilledBox enemieskilled=new EnemiesKilledBox();
		//OTHER STATS
		towerStats.getChildren().addAll(enemieskilled.getNode()/*,valueofTower*/);
		return towerStats;
	}

	@Override
	public Node getNode() {
		return myTowerStatistics;
	}
}
