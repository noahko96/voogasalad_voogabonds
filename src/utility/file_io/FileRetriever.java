package utility.file_io;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The purpose of this class is to retrieve all files in a given file path
 * This class can be used by the GameEngine to get all the files that it needs to construct
 * @author matthewfaw
 *
 */
public class FileRetriever {
	private static final char DEFAULT_PATH_SEPARATOR = '/';
	private String myDefaultPath;

	public FileRetriever(String aDefaultPath)
	{
//		aDefaultPath = aDefaultPath.replace(DEFAULT_PATH_SEPARATOR,File.separatorChar);
		myDefaultPath = aDefaultPath;
	}

	/**
	 * Given a directory relative to the default path constructed, 
	 * list all of the files contained in that directory
	 * 
	 * TODO: This method currently assumes that the requested file path is valid: 
	 * need to throw error if invalid
	 * @param aDirectory
	 * @return
	 */
	public List<String> getFileNames(String aDirectory)
	{
//		aDirectory = aDirectory.replace(DEFAULT_PATH_SEPARATOR, File.separatorChar);
		URL url = getUrlRelativeToProject(myDefaultPath + aDirectory);
		File folder = new File(url.getPath());
		File[] files = folder.listFiles();
		
		ArrayList<String> filesInDirectory = new ArrayList<String>();
		for (File aFile: files){
			if (aFile.isFile()) {
				String relativeFilePath = getRelativeFilePath(aFile);
				filesInDirectory.add(relativeFilePath);
			}
		}
		return filesInDirectory;
	}
	public URL getUrlRelativeToProject(String aRelativePath)
	{
		return getClass().getClassLoader().getResource(aRelativePath);
	}
	/**
	 * A method to retrieve the file path relative to myDefaultPath 
	 * (the path with which the object was constructed)
	 * @param aFile
	 * @return
	 */
	private String getRelativeFilePath(File aFile)
	{
		String absoluteFilePath = aFile.getAbsolutePath();
		String defaultPath = myDefaultPath.replace(DEFAULT_PATH_SEPARATOR, File.separatorChar);
		String relativeFilePath = absoluteFilePath.substring(absoluteFilePath.indexOf(defaultPath)/*+ myDefaultPath.length() + 1*/);
		return relativeFilePath.replace(File.separatorChar, DEFAULT_PATH_SEPARATOR);
	}

	/*
	public static void main(String[] args)
	{
		FileRetriever fr = new FileRetriever("engine/model/components");
		List<String> l = fr.getFileNames("/");
		//System.out.println(l);
	}
	*/
	
}
