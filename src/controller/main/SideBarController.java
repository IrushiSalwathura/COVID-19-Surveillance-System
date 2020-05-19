package controller.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SideBarController {
    boolean flag = true;

    public void btnHome_OnMouseClicked(MouseEvent mouseEvent) {
        BorderPane border_pane = (BorderPane) ((Node) mouseEvent.getSource()).getScene().getRoot();
        if (flag == true) {
            Parent contentArea = null;
            try {
                contentArea = FXMLLoader.load(getClass().getResource("/view/main/ContentArea.fxml"));
                border_pane.setCenter(contentArea);
                flag = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            border_pane.setCenter(null);
            flag = true;
        }
    }

    public void btnGlobal_OnMouseClicked(MouseEvent mouseEvent) {
        BorderPane border_pane = (BorderPane) ((Node) mouseEvent.getSource()).getScene().getRoot();
        if (flag == true) {
            Parent global = null;
            try {
                global = FXMLLoader.load(getClass().getResource("/view/GlobalCOVID.fxml"));
                border_pane.setCenter(global);
                flag = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            border_pane.setCenter(null);
            flag = true;
        }
    }

    public void btnManageHospitals_OnMouseClicked(MouseEvent mouseEvent) {
        BorderPane border_pane = (BorderPane) ((Node) mouseEvent.getSource()).getScene().getRoot();
        if (flag == true) {
            Parent hospitals = null;
            try {
                hospitals = FXMLLoader.load(getClass().getResource("/view/ManageHospitals.fxml"));
                border_pane.setCenter(hospitals);
                flag = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            border_pane.setCenter(null);
            flag = true;
        }
    }

    public void btnManageQC_OnMouseClicked(MouseEvent mouseEvent) {
        BorderPane border_pane = (BorderPane) ((Node) mouseEvent.getSource()).getScene().getRoot();
        if (flag == true) {
            Parent qCenters = null;
            try {
                qCenters = FXMLLoader.load(getClass().getResource("/view/ManageQuarantineCenters.fxml"));
                border_pane.setCenter(qCenters);
                flag = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            border_pane.setCenter(null);
            flag = true;
        }
    }

    public void btnManageUsers_OnMouseClicked(MouseEvent mouseEvent) {
        BorderPane border_pane = (BorderPane) ((Node) mouseEvent.getSource()).getScene().getRoot();
        if (flag == true) {
            Parent users = null;
            try {
                users = FXMLLoader.load(getClass().getResource("/view/ManageUsers.fxml"));
                border_pane.setCenter(users);
                flag = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            border_pane.setCenter(null);
            flag = true;
        }
    }
}
