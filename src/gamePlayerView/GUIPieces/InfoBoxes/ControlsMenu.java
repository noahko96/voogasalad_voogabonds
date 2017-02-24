package gamePlayerView.GUIPieces.InfoBoxes;
import java.util.ArrayList;
import gamePlayerView.GUIPieces.InfoBoxes.Controls;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControlsMenu{
    private Pane myRoot;
    private Stage myStage;
    private Scene myWindow;
    private VBox myButtons;
    private VBox myLabels;
    private Controls myControls;
    private String buttonCSS = "-fx-font: 22 arial; -fx-base: #6de894;";
    
    public ControlsMenu(){
        myRoot = new Pane();
        myStage = new Stage();
        myStage.setTitle("Select Controls");
        myWindow = new Scene(myRoot, 400,450);
        myWindow.setFill(Color.DODGERBLUE);
        myStage.setScene(myWindow);
        //can't make a new controls here... have to get from gameplayer
        myControls = new Controls();
        myButtons = new VBox();
        myLabels = new VBox();
        myRoot.setStyle("-fx-base: #000000; -fx-stroke: #6de894;");
    }
    
    public void init(){
        displayFunctions();
        displayControls();
        addApplyButton();
        myStage.show();
    }


    private void addApplyButton () {
        Button apply = new Button();
        apply.setLayoutX(150);
        apply.setLayoutY(385);
        apply.setText("APPLY");
        apply.setStyle(buttonCSS);
        apply.setOnMouseClicked(e -> saveControls());

        myRoot.getChildren().add(apply);
    }
    
    
    private void saveControls () {
        
        myControls.setControls(myControls.getControls());
        kill();
//        //System.out.println(myControls.getFunctions());
//        //System.out.println(myControls.getControls());
    }
    
    public void displayFunctions(){
        ArrayList<String> functions = myControls.getFunctions();
        
        for(String str : functions){
            ////System.out.println(resources.getString(e.nextElement()));
            Text functionText = new Text();
            functionText.setText(str + ": ");
            functionText.setStyle("-fx-fill: white; -fx-font: 22 arial;  ");
            myLabels.getChildren().add(functionText);
        }
        
        myLabels.setSpacing(35);
        myLabels.setLayoutX(50);
        myLabels.setLayoutY(30);
        myRoot.getChildren().add(myLabels);
    }
    
    public void kill(){
        myRoot.getChildren().clear();
        myLabels.getChildren().clear();
        myButtons.getChildren().clear();
        myStage.close();
    }
    
    public void displayControls(){
        ArrayList<String> controls = myControls.getControls();
        
        for(String str : controls){
            ////System.out.println(resources.getString(e.nextElement()));
            ToggleButton myButton = new ToggleButton();
            myButton.setText(str);
            myButton.setStyle(buttonCSS);
            myButton.setPrefWidth(200);
            myButton.setPrefHeight(50);
            myButton.setOnMouseClicked(e -> setControls(myButton));
            myButtons.getChildren().add(myButton);
        }
        
        myButtons.setSpacing(10);
        myButtons.setLayoutX(150);
        myButtons.setLayoutY(20);
        myRoot.getChildren().add(myButtons);
    }
    private void setControls(ToggleButton myButton) {
            myWindow.setOnKeyPressed(e -> 
                handleKeyInput(e.getCode(), myButton));
    }
    
    private void handleKeyInput(KeyCode code, ToggleButton myButton){
       if(myButton.isSelected()){
           myControls.setControlFor(myControls.getFunctions()
                                 .get(myControls.getControls().
                                      indexOf(myButton.getText())), code.getName());
//           //System.out.println(myControls.getControlFor(myControls.getFunctions()
//                              .get(myControls.getControls()
//                               .indexOf(myButton.getText()))));
           myButton.setText(code.getName());
           myButton.setSelected(false);
           
       }
    }

    public void getControlsToDisplay(Controls cont) {
        myControls = cont;
    }
}