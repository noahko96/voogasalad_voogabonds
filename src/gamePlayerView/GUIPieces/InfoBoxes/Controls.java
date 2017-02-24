package gamePlayerView.GUIPieces.InfoBoxes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;

public class Controls {
    private ResourceBundle myDefaultControls;
    private Enumeration<String> myControlFunctions;
    private ArrayList<String> myControls;
    private ArrayList<String> myFunctions;
    private Map<String, String> myMappings;
    
    public Controls(){
        myDefaultControls =  ResourceBundle.getBundle("resources/defaultcontrols");
        myControlFunctions = myDefaultControls.getKeys();
        myControls = new ArrayList<String>();
        myFunctions = new ArrayList<String>();
        
        while(myControlFunctions.hasMoreElements()){
            String str = myControlFunctions.nextElement();
            myFunctions.add(str);
            myControls.add(myDefaultControls.getString(str));
        }
        
        myMappings = new HashMap<String, String>();
        makeMap();
    }
    
    public void setDefaults(){
        while(myControlFunctions.hasMoreElements()){
            String str = myControlFunctions.nextElement();
            myControls.add(myDefaultControls.getString(str));
        }
        
    }
    
    public void setControlFor(String function, String newControl){
//        //System.out.println(function + ": ");
//        //System.out.println(mappings.get(function));
        for(int i = 0; i < myControls.size(); i++){
            if(myMappings.get(function) == myControls.get(i)){
                myControls.remove(i);
                myControls.add(i, newControl);
//                //System.out.println("Replaced");
//                //System.out.println("controls size: " + controls.size());
            }
        }
        myMappings.replace(function, myMappings.get(function), newControl);
        
    }
    
    private void makeMap(){
        for(int i = 0; i < myFunctions.size(); i++){
        myMappings.put(myFunctions.get(i), myControls.get(i));
        }
    }
    
    public String getControlFor(String function){
        return myMappings.get(function);
    }
    
    public ArrayList<String> getControls(){
        return myControls;
    }
    
    public ArrayList<String> getFunctions(){
        return myFunctions;
    }
        
    public void setControls(ArrayList<String> newControls){
        myControls = newControls;
    }
}