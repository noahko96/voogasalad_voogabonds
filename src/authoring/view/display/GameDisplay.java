package authoring.view.display;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import authoring.controller.MapDataContainer;
import authoring.model.map.TerrainData;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import utility.Point;

/**
 * @author Christopher Lu
 * Creates the game display in the authoring environment, where the map is divided into clickable cells that allows the user
 * to change the property of the terrain upon click, including color and terrain type. Also responsible for repopulating map during deserialization.
 */

public class GameDisplay {

	private VBox terrainContainer;
	private ScrollPane terrainArea;
	private TilePane terrainGrid;
	private GridToolBar toolBar;
	public int DEFAULT_TILE_SIZE = 50;
	public int GAP = DEFAULT_TILE_SIZE/20;
	private int columns;
	private int rows;
	private int screenWidth;
	private int screenHeight;
	private ResourceBundle myResources;
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private MapDataContainer mapData;
	private Scene scene;
	private int tileSize;
	private HashMap<String, ArrayList<Point>> spawnPoints;
	private HashMap<String, ArrayList<Point>> sinkPoints;
	private HashSet<TerrainData> terrainList;
	private HashMap<String, String> validTerrain;
	private ArrayList<Point> usefulSinkPoints = new ArrayList<Point>();
	private ArrayList<Point> usefulSpawnPoints = new ArrayList<Point>();
	private ArrayList<Point> usefulTerrainPoints = new ArrayList<Point>();
	private ArrayList<String> usefulTerrainFills = new ArrayList<String>();
	private Set<String> spawnNames;
	
	public GameDisplay(BorderPane root, Scene scene, MapDataContainer controller) {
		setUpScreenResolution();
		this.scene = scene;
		this.myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "View");
		this.terrainContainer = new VBox();
		this.terrainArea = new ScrollPane();
		terrainArea.setId("background");
		this.terrainGrid = new TilePane();
		this.toolBar = new GridToolBar(terrainContainer, scene, controller);
		this.mapData = controller;
		importMapData();
		this.columns = mapData.getNumXCells();
		this.rows = mapData.getNumYCells();
		if (screenWidth/columns < (screenHeight*0.82)/rows) {
			this.tileSize = (int) (screenWidth/columns) - GAP;
		}
		else {
			this.tileSize = (int) ((screenHeight*0.82)/rows) - GAP;
		}
		controller.cellSize(tileSize);
		terrainArea.setContent(terrainGrid);
		terrainContainer.getChildren().add(terrainArea);
		root.setCenter(terrainContainer);
		makeUsefulSink();
		makeUsefulSpawn();
		makeUsefulTerrain();
		populateGrid();
		dragHandler();
	}
	
	/**
	 * Sets screenWidth and screenHeight to screen width and screen height.
	 */
	private void setUpScreenResolution() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
	}
	
	/**
	 * Puts map data in corresponding maps.
	 */
	private void importMapData() {
		spawnPoints = mapData.getSpawnPointMap();
		sinkPoints = mapData.getSinkPointMap();
		terrainList = mapData.getTerrainList();
		validTerrain = mapData.getValidTerrainMap();
	}
	
	/**
	 * Finds and adds all the points that contain a sink point to a point ArrayList.
	 */
	private void makeUsefulSink() {
		Set<String> sinkNames = sinkPoints.keySet();
		for (String s : sinkNames) {
			usefulSinkPoints.add(sinkPoints.get(s).get(0));
		}
	}
	
	/**
	 * Finds and adds all the points that contain a spawn point to a point ArrayList.
	 */
	private void makeUsefulSpawn() {
		spawnNames = spawnPoints.keySet();
		for (String s: spawnNames) {
			usefulSpawnPoints.add(spawnPoints.get(s).get(0));
		}
	}
	
	/**
	 * Finds and adds all the points that contain a terrain to a point ArrayList.
	 * Also adds corresponding fills to list of available fills.
	 */
	private void makeUsefulTerrain() {
		for (TerrainData terrain : terrainList) {
			usefulTerrainPoints.add(terrain.getLoc());
			usefulTerrainFills.add(terrain.getColor());
		}
	}
	
	private void makeSinkPoint(int row, int col, Point currentPoint, TerrainCell  cell) {
		cell.setStroke(Paint.valueOf(myResources.getString("DefaultSinkColor")));
		cell.setStrokeWidth(tileSize*0.1);
	}
	
	private void makeSpawnPoint(int row, int col, Point currentPoint, TerrainCell cell) {
		cell.setStroke(Paint.valueOf(myResources.getString("DefaultSpawnColor")));
		cell.setStrokeWidth(tileSize*0.1);
	}
	
	private void makeTerrainPoint(int row, int col, Point currentPoint, TerrainCell cell) {
		int index = usefulTerrainPoints.indexOf(currentPoint);
		try {
			cell.setFill(Paint.valueOf(usefulTerrainFills.get(index)));
		} catch (Exception e) {
			Image image = new Image(usefulTerrainFills.get(index));
			ImagePattern pattern = new ImagePattern(image);
			cell.setFill(pattern);
		}
	}
	
	/**
	 * Populates the grid using data in usefulTerrainPoints, usefulSinkPoints, and usefulSpawnPoints.
	 * These data structures get their data from the MapDataContainer.
	 */
	private void populateGrid() {
		terrainGrid.getChildren().clear();
		terrainGrid.setHgap(GAP);
		terrainGrid.setVgap(GAP);
		terrainGrid.setPrefColumns(columns);
		for (int r = 0; r < rows; r++) {
			for (int col = 0; col < columns; col++) {
				TerrainCell cell = new TerrainCell(mapData, toolBar, r, col, this);
				Point currentPoint = new Point((double)col, (double)r);
				if (usefulTerrainPoints.contains(currentPoint)) {
					if (usefulSinkPoints.contains(currentPoint)) {
						makeSinkPoint(r, col, currentPoint, cell);
						makeTerrainPoint(r, col, currentPoint, cell);
						cell.setWidth(tileSize*0.9);
						cell.setHeight(tileSize*0.9);
					}
					else if (usefulSpawnPoints.contains(currentPoint)) {
						makeSpawnPoint(r, col, currentPoint, cell);
						makeTerrainPoint(r, col, currentPoint, cell);
						cell.setWidth(tileSize*0.9);
						cell.setHeight(tileSize*0.9);
					}
					else {
						makeTerrainPoint(r, col, currentPoint, cell);
						cell.setWidth(tileSize);
						cell.setHeight(tileSize);
					}
				}
				else if (usefulSinkPoints.contains(currentPoint)) {
					makeSinkPoint(r, col, currentPoint, cell);
					cell.setWidth(tileSize*0.9);
					cell.setHeight(tileSize*0.9);
					cell.setFill(Paint.valueOf(myResources.getString("DefaultCellColor")));
				}
				else if (usefulSpawnPoints.contains(currentPoint)) {
					makeSpawnPoint(r, col, currentPoint, cell);
					cell.setWidth(tileSize*0.9);
					cell.setHeight(tileSize*0.9);
					cell.setFill(Paint.valueOf(myResources.getString("DefaultCellColor")));
				}
				else {
					cell.setWidth(tileSize);
					cell.setHeight(tileSize);
					cell.setFill(Paint.valueOf(myResources.getString("DefaultCellColor")));
				}
				terrainGrid.getChildren().add(cell);
			}
		}
	}

	/**
	 * Allows user to draw terrain quickly by draggin across the cells instead of individually selecting cells.
	 */
	private void dragHandler() {
		ObservableList <Node> entries = terrainGrid.getChildren();
		for (Node elem : entries){
			
		    elem.setOnDragDetected(new EventHandler<MouseEvent>() {
		        @Override public void handle(MouseEvent e) {
		            Dragboard db = elem.startDragAndDrop(TransferMode.ANY);
		            ClipboardContent cb = new ClipboardContent();
		            cb.put(DataFormat.PLAIN_TEXT, "");
		            db.setContent(cb);
		            dragHandlerData(elem);
		        }
		    });
		    elem.setOnDragOver(new EventHandler<DragEvent>() {
		        @Override public void handle(DragEvent e) {
		            e.acceptTransferModes(TransferMode.ANY);
		            dragHandlerData(elem);
		        }
		    });
		}
	}
	
	/**
	 * Adds and removes data in the MapDatacontainer as the user drags terrain across the map.
	 * @param elem
	 */
	private void dragHandlerData(Node elem) {
		TerrainCell convertedElem = (TerrainCell) elem;
		 if (toolBar.getImageStatus()) {
         	Image image = new Image(toolBar.getSelectedImagePath());
 			ImagePattern pattern = new ImagePattern(image);
         	((TerrainCell)elem).setFill(pattern);
         	
         	String[] splitPath = toolBar.getSelectedImagePath().toString().split("src/");
				String relPath = "";
				if (toolBar.getImageStatus() && splitPath.length > 1) {
					relPath += splitPath[1] + "/";
				}
			mapData.removeTerrainData(new TerrainData(convertedElem.getType(), convertedElem.getColumn(), convertedElem.getRow(), (int) convertedElem.getHeight(), convertedElem.getFill().toString()));
         	mapData.addTerrainData(new TerrainData(convertedElem.getType(), convertedElem.getColumn(), convertedElem.getRow(), (int) convertedElem.getHeight(), relPath));
				((TerrainCell)elem).setType(toolBar.getSelectedTerrain(), toolBar.getSelectedImagePath().toString());
         }
         else {
        	 	mapData.removeTerrainData(new TerrainData(convertedElem.getType(), convertedElem.getColumn(), convertedElem.getRow(), (int) convertedElem.getHeight(), convertedElem.getFill().toString()));
	            ((TerrainCell)elem).setFill(toolBar.getSelectedColor());
	            mapData.addTerrainData(new TerrainData(convertedElem.getType(), convertedElem.getColumn(), convertedElem.getRow(), (int) convertedElem.getHeight(), toolBar.getSelectedColor().toString()));
				((TerrainCell)elem).setType(toolBar.getSelectedTerrain(), toolBar.getSelectedColor().toString());
         }
	}
	
	public void setCols(int numCols) {
		columns = numCols;
		terrainGrid.setPrefColumns(columns);
		populateGrid();
	}
	
	public void setRows(int numRows) {
		rows = numRows;
		terrainGrid.setPrefRows(rows);
		populateGrid();
	}
	
	public VBox getTerrainBox() {
		return terrainContainer;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
}
