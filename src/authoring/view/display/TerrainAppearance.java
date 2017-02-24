package authoring.view.display;

import javafx.scene.paint.Color;

/**
 * @author Christopher Lu
 * Assigns each terrain type to a color so that colors and terrains are linked.
 */

public class TerrainAppearance {
	
	private Color terrainColor;
	private String terrainType;

	public TerrainAppearance(Color color, String type) {
		this.terrainColor = color;
		this.terrainType = type;
	}
	
	public Color getColor() {
		return terrainColor;
	}
	
	public String getType() {
		return terrainType;
	}
	
	public void setColor(Color newColor) {
		terrainColor = newColor;
	}
	
	public void setTerrain(String newTerrain) {
		terrainType = newTerrain;
	}
	
}
