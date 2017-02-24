package utility.file_io;

import java.io.File;
import java.util.function.Consumer;

/**
 * This interface defines means of attaching event handlers to significant 
 * events that occur in the FileChangeNotifier -- change detection and errors
 * 
 * For example usage, please see documentation for FileChangeNotifier.java
 * 
 * @author matthewfaw
 *
 */
public interface IFileChangeNotifier extends Runnable {

	/**
	 * This method sets up the function to be performed when a file change is detected
	 * This function takes a File as an input, and returns void.
	 * 
	 * This is where one would attach whatever system they might be using to handle file changes
	 * 
	 * An example usage:
	 * fileChangeNotifier.onFileChangeDetected(file -> System.out.println(file));
	 * @param aFunction the function to be called when a File change is detected
	 */
	public void onFileChangeDetected(Consumer<File> aFunction);

	/**
	 * This method sets up the function to be performed when an error occurs in the FileChangeNotifier
	 * The thrown errors are I/O Exceptions and thread Interrupt exceptions
	 * 
	 * The purpose of this method is to allow the user of the FileChangeNotifier to attach a custom error-handling
	 * method to this notifier that is called whenever an error occurs.  Thus, an error handling service can
	 * take over when an error occurs
	 * 
	 * An example usage:
	 * fileChangeNotifier.onFileChangeDetected(errMessage -> System.out.println(errMessage));
	 * 
	 * @param aFunction
	 */
	public void onErrorDetected(Consumer<String> aFunction);
}
