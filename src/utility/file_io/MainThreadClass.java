package utility.file_io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

/**
 * 
 * The purpose of this class is to serve as an example for how one may use the FileChangeNotifier class
 * See the FileChangeNotifier for more information
 * 
 * This class simply launches the FileChangeNotifier on its own thread
 * 
 * Info about making a new thread obtained here:
 * http://stackoverflow.com/questions/3489543/how-to-call-a-method-with-a-separate-thread-in-java
 * @author matthewfaw
 *
 */
public class MainThreadClass {
	
	public MainThreadClass() throws IOException
	{
		FileRetriever fr = new FileRetriever("SerializedFiles/exampleGame");
		URL url = fr.getUrlRelativeToProject("SerializedFiles/exampleGame");
		Path folder = Paths.get(url.getPath());
		FileChangeNotifier fcn = new FileChangeNotifier(folder.toString(), ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
		fcn.onFileChangeDetected(file -> {
			Scanner s;
			try {
				s = new Scanner(file);
				while(s.hasNextLine()) {
					System.out.println(s.nextLine());
				}
			} catch (FileNotFoundException e) {
				System.out.println("File " + file + "was not found in file system");
			}});
		fcn.onErrorDetected(err -> System.out.println("Error: "+err));
		Thread t = new Thread(fcn);
		t.start();
		System.out.println("derp");
	}
	
	public static void main(String[] args) throws IOException
	{
		MainThreadClass m = new MainThreadClass();
	}
}
