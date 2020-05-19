package controller.main;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class DashboardMainController {
    public BorderPane borderPane;

    public void initialize(){
        try {
            Parent contentArea = FXMLLoader.load(this.getClass().getResource("/view/main/ContentArea.fxml"));
            borderPane.setCenter(contentArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
