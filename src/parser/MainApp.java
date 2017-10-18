package parser;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import parser.view.MainOverviewController;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Parser");
		
		this.primaryStage.getIcons().add(new Image("file:resources/images/logo.png"));
		
		initRootLayout();
		
		showMainOverview();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
     * Initializes the root layout.
     */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Error in the MainApp (initRootLayout)");
		}
	}
	
    /**
     * Shows the person overview inside the root layout.
     */
	public void showMainOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MainOverview.fxml"));
			AnchorPane mainOverview = (AnchorPane) loader.load();
			
			rootLayout.setCenter(mainOverview);
			
			MainOverviewController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (IOException e) {
			System.out.println("Error in the MainApp (showMainOverview)");
		}
	}
	
	 /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
