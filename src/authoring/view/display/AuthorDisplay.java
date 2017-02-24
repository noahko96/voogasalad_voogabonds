package authoring.view.display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import authoring.controller.MapDataContainer;
import authoring.controller.Router;
import authoring.model.EntityList;
import authoring.view.menus.TopMenuBar;
import authoring.view.tabs.LevelTab;
import authoring.view.tabs.MapTab;
import authoring.view.tabs.RulesTab;
import authoring.view.tabs.WaveTab;
import authoring.view.tabs.entities.EntityTab;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import main.MainInitializer;
import utility.ResouceAccess;

public class AuthorDisplay {
    public static final String AUTHORING_TITLE = "Authoring Environment";
    
    private BorderPane root;
    private TabPane tabPane;
    private TopMenuBar topMenuBar;
    private EntityList el;
    private Scene scene;
    private Router router;
    
    public AuthorDisplay(BorderPane pane, Scene scn, Router r) {

        this.scene = scn;
        this.router = r;
        root = pane;
        topMenuBar = new TopMenuBar(pane, router);
        
        tabPane = new TabPane();
        tabPane.setId("background");
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        try {
        	List<Tab> tabs = getTabs();
        	
            for (int i = 0; i < tabs.size(); i++) {
                tabPane.getTabs().add(tabs.get(i));
                tabs.get(i).setId("tab");
            }
        } catch (ClassNotFoundException e) {
        	throw new UnsupportedOperationException(ResouceAccess.getError("NoTabs"), e);
        }

        root.setCenter(tabPane);
    }
    
    public BorderPane getContent() {
        return root;
    }
    
    private List<Tab> getTabs() throws ClassNotFoundException {
        List<Tab> tabs = new ArrayList<>();
        
        MapDataContainer mapData = router.getMapDataContainer();
        MapTab mapTab = new MapTab(tabPane, scene, mapData);
        EntityTab entityTab = new EntityTab(router.getEntityDataContainer());
        RulesTab rulesTab = new RulesTab("Rules", router.getPlayerDataContainer());
        WaveTab waveTab = new WaveTab("Waves", router.getWaveDataContainer());
        LevelTab levelTab = new LevelTab("Levels", router.getLevelDataContainer());
        router.link(entityTab, levelTab, waveTab);

        tabs.add(mapTab);
        tabs.add(entityTab);
        tabs.add(rulesTab);
        tabs.add(waveTab);
        tabs.add(levelTab);

        return tabs;
    }
    
    public Router getRouter(){
    	return router;
    }

}