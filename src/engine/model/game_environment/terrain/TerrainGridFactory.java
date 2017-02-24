package engine.model.game_environment.terrain;

import authoring.controller.MapDataContainer;
import authoring.model.map.TerrainData;

public class TerrainGridFactory {
	public TerrainGridFactory()
	{
	}
	
	public Terrain[][] constructTerrainMap(MapDataContainer aTerrainMapData, int cellSize)
	{
		Terrain[][] terrain = new Terrain[aTerrainMapData.getNumXCells()][aTerrainMapData.getNumXCells()];
		
		for (TerrainData terrainCell: aTerrainMapData.getTerrainList()) {
			terrain[(int) terrainCell.getLoc().getX()][(int) terrainCell.getLoc().getY()] = new Terrain(terrainCell);
		}
		return terrain;
	}
}
