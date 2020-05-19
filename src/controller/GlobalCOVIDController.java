package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class GlobalCOVIDController {
    boolean flag = true;

    public void btnSidebar_OnAction(ActionEvent actionEvent) {
        BorderPane border_pane = (BorderPane) ((Node) actionEvent.getSource()).getScene().getRoot();
        if (flag == true) {
            Parent sidebar = null;
            try {
                sidebar = FXMLLoader.load(getClass().getResource("/view/main/SideBar.fxml"));
                border_pane.setLeft(sidebar);
                flag = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            border_pane.setLeft(null);
            flag = true;
        }
    }

    public void btnHome_OnAction(ActionEvent actionEvent) {
        BorderPane border_pane = (BorderPane) ((Node) actionEvent.getSource()).getScene().getRoot();
        if (flag == true) {
            Parent sidebar = null;
            try {
                sidebar = FXMLLoader.load(getClass().getResource("/view/main/ContentArea.fxml"));
                border_pane.setLeft(sidebar);
                flag = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            border_pane.setLeft(null);
            flag = true;
        }
    }

    public void btnUpdate_OnAction(ActionEvent actionEvent) {
    }
}
