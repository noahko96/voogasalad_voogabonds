package authoring.view.tabs;

import java.util.ArrayList;
import java.util.List;

import authoring.controller.Container;
import authoring.controller.LevelDataContainer;
import authoring.controller.WaveDataContainer;
import authoring.model.LevelData;
import authoring.model.WaveData;
import authoring.view.tabs.entities.EntityListView;
import authoring.view.tabs.entities.EntityTab;
import engine.IObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LevelTab extends ListTab<String> implements IObserver<Container>, ISubmittable{

    private static final int COLS = 2;
    
	private ArrayList<WaveData> myWaves = new ArrayList<WaveData>();
	private LevelDataContainer myContainer;
	private ArrayList<CheckBox> myWaveChecks;
	private TextField myNameField;
	private VBox myV;
	private LevelData originalLevel;
	
	public LevelTab(String text, LevelDataContainer container) {
		super(text, COLS);
		myContainer = container;
		setKeyboardAction(handleKeyPress());
	}

	@Override
	protected EventHandler<ActionEvent> handleAddNewObject() {
		return new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				originalLevel = null;
				VBox menu = setUpMenu(getResources().getString("DefaultLevelName"), null);
				getTilePane().getChildren().add(menu);
			}
		};
	}
	
	private VBox setUpMenu(String name, List<WaveData> selectedWaves) {
		myV = new VBox();
		myV.setId("vbox");
		myNameField = setUpTextInputWithLabel(getResources().getString("EnterName"), name, myV);
		Label wavesLabel = setUpLabel(getResources().getString("SelectWaves"));
		myWaveChecks = new ArrayList<CheckBox>();
		VBox checkV = new VBox();
		checkV.setId("check-vbox");
		for (WaveData wave: myWaves){
			CheckBox check = new CheckBox(wave.getName());
			check.setId("checkbox");
			if (selectedWaves != null)
				for (WaveData selectedWave: selectedWaves)
					if (selectedWave.getName().equals(wave.getName())){
						check.setSelected(true);
						break;
					}
			myWaveChecks.add(check);
			checkV.getChildren().add(check);
		}
		ScrollPane scroll = new ScrollPane();
		scroll.setId("scrollpane");
		scroll.setContent(checkV);
		wavesLabel.setLabelFor(scroll);
		Button finish = setUpSubmitButton();
		Button cancel = setUpCancelButton(myV);
		HBox h = new HBox();
		h.getChildren().addAll(finish, cancel);
		myV.getChildren().addAll(wavesLabel, scroll, h);
		return myV;
	}

	@Override
	public void update(Container c) {
		if (c instanceof WaveDataContainer){
			myWaves.clear();
			for (WaveData wave: ((WaveDataContainer) c).getWaveMap().values()){
				myWaves.add(wave);
			}
		}else if (c instanceof LevelDataContainer){
			getList().clear();
			for (LevelData level: ((LevelDataContainer) c).finalizeLevelDataMap()){
				String name = level.getLevelName();
				getList().add(name);
			}
		}
	}

	@Override
	protected void edit(String name) {
		LevelData level = myContainer.getLevelData(name);
		originalLevel = level;
		VBox menu = setUpMenu(level.getLevelName(), level.getWaveDataList());
		getTilePane().getChildren().add(menu);
	}

	public Button setUpSubmitButton() {
		Button finish = new Button(getResources().getString("Finish"));
		finish.setId("button");
		finish.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				LevelData level = new LevelData();
				try {
					level.setLevelName(myNameField.getText());
					for (CheckBox check: myWaveChecks){
						if (check.isSelected())
							for (WaveData wave: myWaves)
								if (wave.getName().equals(check.getText())){
									level.addWaveDataListToList(wave);
									break;
								}
					}
				} catch(Exception e){
					showError(e.getMessage());
					return;
				}
				if (originalLevel == null)
					myContainer.createNewLevelData(level);
				else
					myContainer.updateLevel(level, originalLevel);
				getTilePane().getChildren().remove(myV);
			}
		});
		return finish;
	}
	
	private EventHandler<KeyEvent> handleKeyPress(){
		return new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				String selected = getListView().getSelectionModel().getSelectedItem();
                if (selected != null && (event.getCode() == KeyCode.D || event.getCode() == KeyCode.BACK_SPACE)) {
                    try {
                        myContainer.removeLevelData(selected);
                        getList().remove(selected);
                    } catch (Exception e) {
                        showError(e.getMessage());
                    }
                }
			}
		};
	}

	@Override
	public void remove(Container aRemovedObject) {
		// Do nothing
	}
}
