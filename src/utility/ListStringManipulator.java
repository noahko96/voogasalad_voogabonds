package utility;

import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * @author Niklas Sjoquist
 * 
 * This class converts a list of strings to a comma-separated string
 *
 */
public class ListStringManipulator {
    
    public static String listToString(List<String> list) {
        if (list != null && list.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String str : list) {
                sb.append(str+", ");
            }
            sb.deleteCharAt(sb.length()-2);
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        } else {
            return null;
        }
    }
    
    /**
     * Converts lists in String form to a List of Strings.
     * 
     * Format (must be consistent): 
     * Strings must be separated by a comma followed by one space.
     * 
     * @param str - comma- or space-separated list
     * @return
     * @throws Exception 
     */
    public static List<String> stringToList(String str) throws PatternSyntaxException {
        String[] list;
        try {
            list = str.split(", ");
        } catch (PatternSyntaxException pse) {
            throw pse;
        }
        return Arrays.asList(list);
    }

}
