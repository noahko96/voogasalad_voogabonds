package authoring.view.tabs;

import java.util.ArrayList;

import authoring.controller.PlayerDataContainer;
import authoring.model.PlayerData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RulesTab extends AuthoringTab implements ISubmittable {
	
	private ObservableList<String> myWins = FXCollections.observableList(new ArrayList<String>());
	private ObservableList<String> myLosses = FXCollections.observableList(new ArrayList<String>());
	private PlayerDataContainer myContainer;
	private TextField myLivesField;
	private TextField myCashField;
	private ComboBox<String> myWinBox;
	private ComboBox<String> myLoseBox;
	
	public RulesTab(String text, PlayerDataContainer container){
		super(text);
		myWins.addAll(getResources().getString("NeverWin"));
		myLosses.addAll(getResources().getString("NeverLose"), getResources().getString("HpLose"),
				getResources().getString("NoMoneyLose"));
		myContainer = container;
		VBox menu;
		if (myContainer.getPlayerData() == null){
			menu = setUpMenu(getResources().getString("DefaultLives"), 
					getResources().getString("DefaultCash"), null, null);
		}
		else{
			menu = setUpMenu(String.valueOf(myContainer.getPlayerData().getStartingLives()), 
					String.valueOf(myContainer.getPlayerData().getStartingCash()), 
					myContainer.getPlayerData().getWinStrategyName(), 
					myContainer.getPlayerData().getLoseStrategyName());
		}
		menu.setId("vbox");
		setContent(menu);
	}

	private VBox setUpMenu(String lives, String cash, String win, String lose) {
		VBox v = new VBox();
		myLivesField = setUpTextInputWithLabel(getResources().getString("EnterLives"), 
				lives, v);
		myCashField = setUpTextInputWithLabel(getResources().getString("EnterCash"),
				cash, v);
		myWinBox = setUpStringComboBoxWithLabel(getResources().getString("EnterWin"), win, 
				myWins, v);
		myLoseBox = setUpStringComboBoxWithLabel(getResources().getString("EnterLose"), lose,
				myLosses, v);
		Button applyChanges = setUpSubmitButton();
		v.getChildren().add(applyChanges);
		return v;
	}

	public Button setUpSubmitButton() {
		Button applyChanges = new Button(getResources().getString("ApplyChanges"));
		applyChanges.setId("button");
		applyChanges.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				PlayerData player = myContainer.getPlayerData();
				try {
					player.setStartingLives(myLivesField.getText());
					player.setStartingCash(myCashField.getText());
					player.setWinStrategyName(myWinBox.getValue());
					player.setLoseStrategyName(myLoseBox.getValue());
				} catch(Exception e){
					showError(e.getMessage());
				}
			}
		});
		return applyChanges;
	}
}
