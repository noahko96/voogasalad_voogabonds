package authoring.model.serialization;


import java.io.PrintWriter;

import com.google.gson.*;

public class JSONSerializer {
	private static final String DEFAULT_FILE_LOCATION = "src/SerializedFiles/";
	
	private Gson gson;
	
	public String serialize(Object obj){

		//overly verbose for now, but easily changeable later, for whether we want a JsonElement or String
		gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gson.toJson(obj);
		JsonElement json = gson.toJsonTree(obj);
		//System.out.println(json);
		return jsonString;
		
			
	}
	
	public void serializeToFile(Object obj, String fileName) throws Exception{
		
		String str = serialize(obj).toString();
		
		try{
			PrintWriter out = new PrintWriter(DEFAULT_FILE_LOCATION+fileName);
			out.println(str);
			out.close();
		}
		catch(Exception e){
			throw new Exception("File was unable to be serialized correctly.");
		}
		
	}


}