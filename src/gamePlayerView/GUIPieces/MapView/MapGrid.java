package gamePlayerView.GUIPieces.MapView;

import java.util.ArrayList;
import java.util.ResourceBundle;
//import javax.media.j3d.Group;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import authoring.model.map.MapData;
import engine.IObservable;
import engine.controller.ApplicationController;
import engine.model.components.viewable_interfaces.IViewablePhysical;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utility.ErrorBox;
import utility.Point;

public class MapGrid extends Node {
    private ApplicationController myAppController;
    private int numColumns;
    private int numRows;
    private Rectangle[][] myGrid;
    private Pane myPane;
    private ArrayList<MoveableComponentView> mySprites;
    private int myCellSize;
    private Rectangle closest; 
    private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
    
    //XXX: maybe remove this--just a quick fix
    public MapGrid(int rows, int cols, int aCellSize, ApplicationController aAppController){
    	myAppController = aAppController;
    	this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Error");
    	myPane = new Pane();
    	closest = new Rectangle();
    	mySprites = new ArrayList<MoveableComponentView>();
    	numColumns = cols;
    	numRows = rows;
    	myCellSize = aCellSize;
    	myGrid = new Rectangle[numRows][numColumns];
    }
    
    public Rectangle fillCell(int row, int col, int aCellSize, String aHexValue,Pane pane) {

    	Rectangle temp = new Rectangle();
    	temp.setFill(Color.web(aHexValue));
    	temp.setStroke(Color.BLACK);
    	temp.setStrokeWidth(1);
//    	temp.widthProperty().bind(pane.widthProperty().divide(numRows));
//    	temp.heightProperty().bind(pane.heightProperty().divide(numColumns));
    	temp.setHeight(aCellSize);
    	temp.setWidth(aCellSize);
//    	temp.layoutXProperty().bind(pane.widthProperty().divide(numRows).multiply(row));
//    	temp.layoutYProperty().bind(pane.heightProperty().divide(numColumns).multiply(col));
    	temp.setX(row*aCellSize);
    	temp.setY(col*aCellSize);
//    	loadTerrainData(temp, row, col, aMapData);

    	temp.setOnDragDropped(new EventHandler<DragEvent>(){
    		public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
//				TowerData data = myTowerColumn.getTowerData(db.getString());
//				//System.out.println(data.getBuyPrice());
				Rectangle closestRectangle = findDropLocation(event.getX(), event.getY());
				myAppController.onTowerDropped(db.getString(), new Point(closestRectangle.getX() + myCellSize / 2, closestRectangle.getY() + myCellSize / 2));
				setClickAction();
				event.consume();
    		}
    	});

    	myGrid[row][col] = temp;
    	return myGrid[row][col];
    }
    
    public void setClickAction(){
        for(MoveableComponentView m : mySprites){
            m.setOnMouseClicked(e -> {
				try {
					setClickForComponent(m);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					ErrorBox.displayError(myResources.getString("CannotMoveObject"));
				}
			});
        }
    }
    
    private void setClickForComponent(MoveableComponentView m) throws Exception {
        //get info to come up on click
        //m.getInfo();
    	//myAppController.DisplayStats();
        myAppController.onEntityClicked(m.getEntityID());

    }
    
//    public void giveViewableComponent(IObservable<IViewablePhysical> aObservable)
//    {
//    	MoveableComponentView aComponent = new MoveableComponentView(aObservable, myPane);
//    	aObservable.attach(aComponent);
//    	sprites.add(aComponent);
//    	myPane.getChildren().add(aComponent);
//    }
    
    public void giveViewableComponent(IObservable<IViewablePhysical> aObservable)
    {
    	MoveableComponentView aComponent = new MoveableComponentView(aObservable, myAppController, myPane, myCellSize);
    	aObservable.attach(aComponent);
    	mySprites.add(aComponent);
    	myPane.getChildren().add(aComponent);
    	//System.out.println("Add a physical to the pane.");
    }
    
    
    private void loadTerrainData(Rectangle temp, int row, int col, MapData aMapData){
        //Set this up later
    }
    
    public void setRoot(Pane myRoot){
        myPane = myRoot;
    }
    
    @Deprecated
    private boolean isFull(Rectangle temp){
        return (temp.getFill() != Color.AQUA);    
    }
   
    
    public Rectangle findDropLocation(double x, double y){
        closest = new Rectangle();
                
        double minDist = Integer.MAX_VALUE;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                Rectangle temp = myGrid[i][j];
                if(calculateDistance(x, y, temp.getX(), temp.getY()) < minDist)
                        {                        
                            minDist = calculateDistance(x, y, temp.getX(), temp.getY());
                            closest = temp;
                        }
                else
                {      
                }
            }
        }
        return closest;
    }
    
    private double calculateDistance (double x, double y, double x2, double y2) {
       
        return Math.sqrt(Math.pow((x - (x2+myCellSize/2)), 2)
                         + Math.pow((y - (y2 + myCellSize/2)), 2));
    }
   

    public int getNumCols(){
        return numColumns;
    }
    
    public int getNumRows(){
        return numRows;
    }
    
    public int getHeight(){
        return myCellSize;
    }
    
    public int getWidth(){
        return myCellSize;
    }

    @Override
    protected NGNode impl_createPeer () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BaseBounds impl_computeGeomBounds (BaseBounds bounds, BaseTransform tx) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected boolean impl_computeContains (double localX, double localY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object impl_processMXNode (MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
        // TODO Auto-generated method stub
        return null;
    }

    


    
}