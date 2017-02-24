package mainmenu.screens;

import java.util.Date;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Christopher Lu
 * Creates the MenuTableItem class that are the objects that will show up in the table
 * allowing users to select which project to start.
 */

public class MenuTableItem {

	private SimpleStringProperty projectName;
	private Date modifiedDate;
	
	public MenuTableItem(String pName, Date pDate) {
		this.projectName = new SimpleStringProperty(pName);
		this.modifiedDate = pDate;
	}
	
	public String getProjectName() {
		return projectName.get();
	}
	
	public void setProjectName(String newProjectName) {
		projectName.set(newProjectName);
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(Date newDate) {
		modifiedDate = newDate;
	}
	
}
