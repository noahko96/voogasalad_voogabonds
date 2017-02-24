package gamePlayerView.GUIPieces.MapView;

import java.util.ArrayList;

import authoring.controller.MapDataContainer;
import authoring.model.map.MapData;
import authoring.model.map.TerrainData;
import engine.model.components.viewable_interfaces.IViewable;
import engine.model.components.viewable_interfaces.IViewablePhysical;
import engine.model.machine.IViewableMachine;
import gamePlayerView.GUIPieces.InfoBoxes.Controls;
import gamePlayerView.interfaces.IGUIPiece;
import engine.IObservable;
import engine.IObserver;
import engine.controller.ApplicationController;
import engine.controller.timeline.TimelineController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * 
 * @author graysonwise
 *
 */

public class MapDisplay implements IObserver<TimelineController>, IGUIPiece {
    
    private ApplicationController myAppController;
    private Pane myRoot;
    private Pane myPane;
    private MapGrid myBackground;
    private ArrayList<MoveableComponentView> mySprites;
    private Controls myControls;
    private static boolean isPlaying;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    
	//TODO: matthewfaw
    //Set up map from backend
    //register as observer of timeline
    public MapDisplay(ApplicationController aAppController) throws Exception{
    	myAppController = aAppController;
        mySprites = new ArrayList<MoveableComponentView>();
        myRoot = new Pane();
        myPane = new Pane();
        myControls = new Controls();
        init();
        isPlaying = false;
    }
    
//    public void giveViewableComponent(IObservable<IViewablePhysical> aObservable)
//    {
//    	background.giveViewableComponent(aObservable);
//    }
    
    public void giveViewableComponent(IObservable<IViewablePhysical> aObservable) {
    	myBackground.giveViewableComponent(aObservable);
    }
    
    public void setMap(MapDataContainer aMapData){
       
        int cellSizeToUse = (int) Math.min((myRoot.getHeight()/aMapData.getNumYCells()), (myRoot.getHeight()/aMapData.getNumXCells()));

        myBackground = new MapGrid(aMapData.getNumXCells(), aMapData.getNumYCells(), cellSizeToUse, myAppController);
        
        for (TerrainData terrainData: aMapData.getTerrainList()) {
        	//XXX: I don't like that we have to cast here
            aMapData.cellSize(cellSizeToUse);
        	myPane.getChildren().add (myBackground.fillCell((int)terrainData.getLoc().getX(), 
        	                                             (int)terrainData.getLoc().getY(), 
        						aMapData.getCellSize(), 
        						terrainData.getColor(),
        						myRoot));
        }
        
        
        myRoot.getChildren().add(myPane);
        myBackground.setRoot(myRoot);

    }
    
    //TODO: Use timeline controller instead
    @Deprecated
    public void init(){
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Animation.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
    
    public void setupDragging(Scene myScene){
        
        myScene.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
               
               event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
               event.consume();
            }
        });
        
        myScene.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                 event.consume();
            }
        });
        
        myScene.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event){
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                   success = true;
                }
                event.setDropCompleted(success);
                
                event.consume();
             }
        });
    }


    //Use the update(TimelineController) method instead
    @Deprecated 
    public void step (double elapsedTime) {
        //game-specific definition of a step
        //bad guys move, etc...
        //call every other classes step function        
        
    }
    
    public static void setPlaying(boolean val){
        isPlaying = val;
    }
    
    public static boolean isPlaying(){
        return isPlaying;
    }
    public Node getView() {
        return myRoot;
    }

    @Override
    public void update(TimelineController aChangedObject) {
        //TODO:
    }
    
    public void handleKeyInput(KeyCode code){
        //System.out.println("hey");
        //System.out.println(myControls.getControlFor("Left"));
        //System.out.println(code);
        switch (code)
        {
            case LEFT:
                //System.out.println("WOO!");
            break;
            default:
                break;
                
        }
    }

    public void getControls(Controls cont) {
        myControls = cont;
    }


	@Override
	public void remove(TimelineController aRemovedObject) {
		// Do nothing.
	}

	@Override
	public Node getNode() {
		return myRoot;
	}

    public Pane getPane () {
        // TODO Auto-generated method stub
        return myRoot;
    }
}