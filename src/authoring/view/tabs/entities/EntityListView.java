package authoring.view.tabs.entities;

import java.io.File;

import authoring.model.EntityData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EntityListView {
    private static final String PHYSICAL_COMPONENT = "PhysicalComponent";
    private static final String IMAGE_ATTRIBUTE = "myImagePath";
    private static final int IMAGE_HEIGHT = 50;
    
    private String myName;
    private String myImagePath;
    
    public EntityListView(EntityData entityData) {
        if (entityData == null) {
            //System.out.println("EntityData is null");
        }
        myName = entityData.getName();
        myImagePath = getImagePath(entityData);
    }
    
    public String getEntityName() {
        return myName;
    }
    
    public ImageView getEntityImageView() {
        if (myImagePath == null || myImagePath.equals("")) {
            return null;
        }
//        String relativePath = myImagePath.substring(4); // remove leading 'src/'
        Image img = new Image(getClass().getClassLoader().getResourceAsStream(myImagePath));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitHeight(IMAGE_HEIGHT);
        return view;
    }
    
    private String getImagePath(EntityData entityData) {
        String imagePath = null;
        for (String component : entityData.getComponents().keySet()) {
            if (component.equals(PHYSICAL_COMPONENT)) {
                for (String attribute : entityData.getComponents().get(component).getFields().keySet()) {
                    if (attribute.equals(IMAGE_ATTRIBUTE)) {
                        imagePath = entityData.getComponents().get(component).getFields().get(attribute);
                    }
                }
            }
        }
        if (imagePath != null)
        	imagePath = imagePath.replace('\\', File.separatorChar);
        return imagePath;
    }

}
