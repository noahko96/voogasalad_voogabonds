package authoring.model.serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import authoring.controller.LevelDataContainer;
import authoring.controller.MapDataContainer;
import authoring.model.EntityData;
import authoring.model.LevelData;
import authoring.model.PlayerData;

public class GameStateDeserializer {

	private MapDataContainer mdc;
	private List<EntityData> edc  = new ArrayList<EntityData>();
	private LevelDataContainer ldc;
	private PlayerData pdd;
	
	public void loadGameState(String filepath) throws Exception{

		List<Object> objects = new ArrayList<Object>();
		File file = new File(filepath);
		File[] subFiles = file.listFiles();
		List<Class> classList = new ArrayList<Class>(Arrays.asList(EntityData.class, MapDataContainer.class, LevelDataContainer.class, PlayerData.class));

		JSONDeserializer jdes = new JSONDeserializer();
		for (File f : subFiles){
			File[] fFiles = f.listFiles();
			for(File fsub : fFiles){
				for (Class c:classList){
					try{
						if (fsub.toString().contains(c.getSimpleName())){
							//System.out.println(fsub.toString());
							//System.out.println(c.getSimpleName());
							// removes the "src/" portion
							String fp = fsub.getPath().substring(4, fsub.getPath().length());
							//System.out.println(fp);
							Object obj = jdes.deserializeFromFile("src/"+fp, c);
							if ( c == PlayerData.class){
								pdd = (PlayerData) obj;
							}if ( c == LevelDataContainer.class){
								ldc = (LevelDataContainer) obj;
							}if ( c == EntityData.class){
								edc.add((EntityData) obj);
							}if ( c == MapDataContainer.class){
								mdc = (MapDataContainer) obj;
							}
						
						}
					}
					catch(Exception e){
//						//System.out.println("fuck");
//						throw new Exception("Error in loading authoring environment.");
					}
				}

			}
		}

		//System.out.println(objects.size());

	}
	
	public MapDataContainer getMapDataContainer(){
		return mdc;
	}
	
	public List<EntityData> getEntityData(){
		return edc;
	}
	
	public LevelDataContainer getLevelDataContainer(){
		return ldc;
	}
	
	public PlayerData getPlayerData(){
		return pdd;
	}

}
