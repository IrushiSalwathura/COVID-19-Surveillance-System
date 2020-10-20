package controller;

import DBConnect.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class GlobalCOVIDController {
    public Label lblLaastUpdated;
    public Label lblConfirmed;
    public Label lblRecovered;
    public Label lblDeaths;
    public JFXTextField txtConfirmed;
    public JFXTextField txtRecovered;
    public JFXTextField txtDeaths;
    public JFXButton btnUpdate;
    public JFXTextField txtDate;
    public AnchorPane root;
    boolean flag = true;

    public void initialize(){
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(root);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        txtDate.setText(dateFormat.format(date));

        currentStatus();
    }
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

        String currentDate = txtDate.getText();
        String confirmed = txtConfirmed.getText();
        String recovered = txtRecovered.getText();
        String deaths = txtDeaths.getText();
        int affected = 0;
        try {
            PreparedStatement pstm = DBConnection.getInstance().geConnection().prepareStatement
                    ("INSERT INTO GlobalCovidData VALUES (?,?,?,?)");
            pstm.setObject(1,currentDate);
            pstm.setObject(2,confirmed);
            pstm.setObject(3,recovered);
            pstm.setObject(4,deaths);
            affected = pstm.executeUpdate();

            if(affected == 0){
                new Alert(Alert.AlertType.ERROR,"Couldn't update global status",ButtonType.OK).show();
            }else{
                new Alert(Alert.AlertType.INFORMATION,"Updated Successfully",ButtonType.OK).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void currentStatus(){
        try {
            PreparedStatement pstm = DBConnection.getInstance().geConnection().prepareStatement
                    ("SELECT * FROM GlobalCovidData ORDER BY `date` DESC LIMIT 1");
            ResultSet rst = pstm.executeQuery();
            while(rst.next()) {
                String date = rst.getString("date");
                String confirmed = rst.getString("confirmedCases");
                String recovered = rst.getString("recoveredCases");
                String deaths = rst.getString("deaths");

                lblLaastUpdated.setText(date);
                lblConfirmed.setText(confirmed);
                lblRecovered.setText(recovered);
                lblDeaths.setText(deaths);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
