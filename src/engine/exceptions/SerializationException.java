package engine.exceptions;

/**
 * An error class to handle constructing error messages 
 * specific to Serialization errors
 * @author matthewfaw
 *
 */
public class SerializationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SerializationException(String aMessage)
	{
		super(aMessage);
	}
	
}
