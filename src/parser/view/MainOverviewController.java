package parser.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import parser.MainApp;
import parser.model.All2GParser;
import parser.model.All3GParser;
import parser.model.WritterAlarms;

public class MainOverviewController {
	@SuppressWarnings("unused")
	private MainApp mainApp;
	private Stage stage;
	
	public MainOverviewController() {
	}
	
	@FXML
	private void initialize() {	
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void handleZAHO() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open file");
		File file = fileChooser.showOpenDialog(stage);
		
		All2GParser all2GParser = new All2GParser();
		all2GParser.parser(file);
		
		WritterAlarms wa = new WritterAlarms();
		wa.write(all2GParser.getAlarmZaho(), file, "ZAHO");
		wa.write(all2GParser.getAlarmZahp(), file, "ZAHP");
		wa.write(all2GParser.getAlarmZeol(), file, "ZEOL");
		wa.write(all2GParser.getAlarmZeoh(), file, "ZEOH");
		
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Parsed");
        alert.setHeaderText("Information was parsed");
        alert.setContentText("Logs were saved to /parsed subfolder.");
        alert.showAndWait();
	}
	
	@FXML
	private void handleRNCAlarms() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open file");
		File file = fileChooser.showOpenDialog(stage);
		
		All3GParser all3GParser = new All3GParser();
		all3GParser.parser(file);
		
		WritterAlarms wa = new WritterAlarms();
		wa.write(all3GParser.getAlarmsRNC(), file, "Alarms_RNC");
		wa.write(all3GParser.getAlarmsHistoryRNC(), file, "Alarms_History_RNC");
		wa.write(all3GParser.getAlarmsWBTS(), file, "Alarms_WBTS");
		wa.write(all3GParser.getAlarmsHistoryWBTS(), file, "Alarms_History_WBTS");
		
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Parsed");
        alert.setHeaderText("Information was parsed");
        alert.setContentText("Logs were saved to /parsed subfolder.");
        alert.showAndWait();
	}

}
