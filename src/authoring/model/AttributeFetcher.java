package authoring.model;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

import utility.file_io.FileRetriever;

public class AttributeFetcher {

	public Map<String, List<String>> componentAttributeMap = new HashMap<String, List<String>>();
	public Map<String, List<String>> componentAttributeTypeMap = new HashMap<String, List<String>>();
	public List<String> componentList;
	public List<String> attributeList;
	public List<String> attributeTypeList;
	private final String PACKAGE = "engine/model/components/concrete";
	private final String EXTENSION = ".class";
	private int index;

	public void fetch() throws ClassNotFoundException {
		componentList = new ArrayList<String>();
		//		File dir = new File(PATH);
		List<String> fileList = new ArrayList<String>();
		FileRetriever fr = new FileRetriever(PACKAGE);
		fileList = fr.getFileNames("/");


		for (String fileName : fileList){

			attributeList = new ArrayList<String>();
			attributeTypeList = new ArrayList<String>();

			String tempString = fileName;
			tempString = fileName;
			index = tempString.indexOf(EXTENSION);
			tempString = tempString.substring(0, index);
			tempString = tempString.replace('/','.');
			try{
				Class<?> cls = Class.forName(tempString);
				String newCompName = tempString.substring(PACKAGE.length(), tempString.length());
				componentList.add(newCompName);

				//if field does not have custom annotation, add to map
				for (Field f : cls.getDeclaredFields()){
					if (!f.isAnnotationPresent(Hide.class)){
						attributeList.add(fieldManipulator(f));
						attributeTypeList.add(f.getType().getSimpleName());
					}
				}
				componentAttributeMap.put(newCompName, attributeList);
				componentAttributeTypeMap.put(newCompName, attributeTypeList);
			}
			catch(ClassNotFoundException | SecurityException | IllegalArgumentException e){
				throw new ClassNotFoundException(String.format("No such class: %s", tempString));
			}
		}

	}

	private String fieldManipulator(Field f){	
		String fieldString = f.toString();
		fieldString = fieldString.substring(fieldString.lastIndexOf(".")+1, fieldString.length());
		return fieldString;
	}

	public List<String> getComponentList(){
		return componentList;
	}

	public List<String> getComponentAttributeList(String component){
		return componentAttributeMap.get(component);
	}
	public List<String> getComponentAttributeTypeList(String component){
		return componentAttributeTypeMap.get(component);
	}

}
