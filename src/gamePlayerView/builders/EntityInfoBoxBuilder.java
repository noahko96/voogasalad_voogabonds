package gamePlayerView.builders;
import engine.controller.ApplicationController;
import engine.model.components.viewable_interfaces.IViewableBounty;
import engine.model.components.viewable_interfaces.IViewableDamageDealer;
import engine.model.components.viewable_interfaces.IViewableHealth;
import engine.model.components.viewable_interfaces.IViewablePhysical;
import engine.model.components.viewable_interfaces.IViewableSell;
import engine.model.components.viewable_interfaces.IViewableTargeting;
import engine.model.components.viewable_interfaces.IViewableUpgrade;
import gamePlayerView.GUIPieces.InfoBoxes.BountyBox;
import gamePlayerView.GUIPieces.InfoBoxes.DamageBox;
import gamePlayerView.GUIPieces.InfoBoxes.HealthBox;
import gamePlayerView.GUIPieces.MachineInfoView.MachineInfo;
import gamePlayerView.GUIPieces.MachineInfoView.SellUI;
import gamePlayerView.GUIPieces.MachineInfoView.TargetingButtons;
import gamePlayerView.GUIPieces.MachineInfoView.UpgradeUI;
import gamePlayerView.gamePlayerView.GamePlayerScene;
import javafx.scene.layout.HBox;

public class EntityInfoBoxBuilder {
		//private EntityInfoBox myEntityInfoBox;
		private MachineInfo myMachineInfo;
		private TargetingButtons myTargetingButtons;
		private HBox myEntityInfoBox;
		private GamePlayerScene  myGamePlayerScene;
		private HealthBox myHealthBox;
		private DamageBox myDamageBox;
		private BountyBox myBountyBox;
		private UpgradeUI myUpgradeUI;
		private SellUI mySellUI;
		private ApplicationController myAppController;

		
		public EntityInfoBoxBuilder(GamePlayerScene gamePlayerScene,ApplicationController appController)
		{
			//myEntityInfoBox = new EntityInfoBox();
			myEntityInfoBox	= new HBox();
			myGamePlayerScene = gamePlayerScene;
			myAppController	= appController;
		}
		
		public EntityInfoBoxBuilder withMachineInfo(IViewablePhysical aComponent)
		{
			//null check before constructing
			//add upgrade button to  the info box
			myMachineInfo = new MachineInfo(aComponent);
			myEntityInfoBox.getChildren().add(getMyMachineInfo().getNode());
			return this;
		}
		
		public EntityInfoBoxBuilder withTargetingMechanism(IViewableTargeting aComponent)
		{
			//System.out.println("Building a targeting box");
			//null check before constructing
			//add upgrade button to  the info box
			myTargetingButtons = new TargetingButtons(myAppController);
			myEntityInfoBox.getChildren().add(myTargetingButtons.getNode());
			return this;
		}
		
		public EntityInfoBoxBuilder withHealthBox(IViewableHealth aComponent) {
			this.myHealthBox = new HealthBox();
			myEntityInfoBox.getChildren().add(myHealthBox.getNode());

			return this;
		}
		
		public EntityInfoBoxBuilder withBountyBox(IViewableBounty aComponent) {
			myBountyBox = new BountyBox();
			myEntityInfoBox.getChildren().add(myBountyBox.getNode());
			return this;
		}
		
		public EntityInfoBoxBuilder withDamageBox(IViewableDamageDealer aComponent) {
			myDamageBox = new DamageBox();
			myEntityInfoBox.getChildren().add(myDamageBox.getNode());
			return this;
		}
		
		public EntityInfoBoxBuilder withUpgrade(IViewableUpgrade aComponent) {
			myUpgradeUI = new UpgradeUI(aComponent);
			myEntityInfoBox.getChildren().add(myUpgradeUI.getNode());
			return this;
		}
		
		public EntityInfoBoxBuilder withSell(IViewableSell aComponent) {
			mySellUI = new SellUI(myAppController);
			myEntityInfoBox.getChildren().add(mySellUI.getNode());
			return this;
		}
		
		public EntityInfoBox build()
		{
			return new EntityInfoBox(this);
			//myGamePlayerScene.addEntityInfoBox(myEntityInfoBox);
		}

		public MachineInfo getMyMachineInfo() {
			return myMachineInfo;
		}
		
		public TargetingButtons getMyTargetingButtons() {
			return myTargetingButtons;
		}
		
		public UpgradeUI getMyUpgradeUI() {
			return myUpgradeUI;
		}
		
		public SellUI getMySellUI(){
			return mySellUI;
		}
		
		public DamageBox getMyDamageBox(){
			return myDamageBox;
		}
		
		public BountyBox getMyBountyBox(){
			return myBountyBox;
		}
		
		public HealthBox getMyHealthBox(){
			return myHealthBox;
		}
	}
