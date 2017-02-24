// This entire file is part of my masterpiece.
// CHRISTOPHER LU
/**
 * Purpose: ImageGallery uses the TerrainImage object to pair an image's URL to itself. This helps make populating the gallery much easier.
 * What I changed: In the populatePane() method in ImageGallery, during each iteration of the for loop, I originally just created a new image and set the image path of the image to
 * be the file path. I would then pass in the image into an imageView and display the imageview. However, I realized I had to consider how to handle mouseEvents like when the user
 * clicks to select the image. Thinking back on the TerrainCell class, I realized that it would be better if each ImageView could detect their own mouse events, so I decided
 * to create this TerrainImage class that displays the image at the imagePath and has its own click handler that sets image gallery's image path to its own image path.
 */

package authoring.view.display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Christopher Lu
 * Creates a TerrainImage object so that imageview's image url's can be passed into imageGallery and gridToolbar.
 */

public class TerrainImage extends ImageView {
	
	private String imagePath;
	private Image image;
	private ImageGallery imgGal;
	
	public TerrainImage(String imgPath, ImageGallery gal) {
		this.imagePath = imgPath;
		this.imgGal= gal;
		this.image = new Image(imgPath);
		this.setImage(image);
		clickHandler();
	}

	/**
	 * This is used in the ImageGallery. When an image in the imageGallery is selected, the image gallery's selected image path is set to this TerrainImage's image path.
	 */
	private void clickHandler() {
		this.setOnMouseClicked(click -> {
			imgGal.setSelectedImagePath(imagePath);
		});
	}
	
	public String getImagePath(){
		return imagePath;
	}
	
}
