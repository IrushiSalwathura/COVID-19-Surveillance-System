package controller;

import DBConnect.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import util.CenterTM;
import util.HospitalTM;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageQuarantineCentersController {
    public JFXComboBox cmbDistrict;
    public JFXListView<CenterTM> listCenters;
    public JFXTextField txtSearch;
    public JFXTextField txtCenterId;
    public JFXTextField txtCenterName;
    public JFXTextField txtCity;
    public JFXTextField txtHead;
    public JFXTextField txtHeadContactNo;
    public JFXTextField txtCenterContactNo1;
    public JFXTextField txtCenterContactNo2;
    public JFXTextField txtCapacity;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnAdd;
    public AnchorPane root;
    boolean flag = true;
    ArrayList<CenterTM> centersList = new ArrayList<>();

    public void initialize(){
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(root);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        loadCentersList();
        generateCenterID();
        cmbDistrict.getItems().removeAll(cmbDistrict.getItems());
        cmbDistrict.getItems().addAll("Northern-Jaffna","Northern-Kilinochchi","Northern-Mannar","Northern-Mullaitivu","Northern-Vavuniya",
                "NorthWestern-Kurunegala","NorthWestern-Puttalam",
                "NorthCentral-Anuradhapura","NorthCentral-Polonnaruwa",
                "Central-Kandy","Central-Matale","Central-Nuwara Eliya",
                "Western-Colombo","Western-Gampaha","Western-Kalutara",
                "Southern-Galle","Southern-Matara","Southern-Hambantota",
                "Sabaragamuwa-Kegalle","Sabaragamuwa-Ratnapura",
                "Eastern-Trincomalee","Eastern-Batticaloa","Eastern-Ampara",
                "Uva-Badulla","Uva-Monaragala");

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<CenterTM> searchCenters = listCenters.getItems();
                searchCenters.clear();

                for (CenterTM center : centersList) {
                    if (center.getCenterName().contains(newValue) || center.getCenterName().toLowerCase().contains(newValue)) {
                        searchCenters.add(center);
                    }

                }
            }
        });

        listCenters.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CenterTM>() {
            @Override
            public void changed(ObservableValue<? extends CenterTM> observable, CenterTM oldValue, CenterTM newValue) {
                if(newValue == null){
                    Clear();
                    btnSave.setDisable(true);
                    return;
                }
                txtCenterId.setText(newValue.getCenterId());
                txtCenterName.setText(newValue.getCenterName());
                txtCity.setText(newValue.getCity());
                cmbDistrict.getSelectionModel().select(newValue.getDistrict());
                txtHead.setText(newValue.getHead());
                txtHeadContactNo.setText(newValue.getHeadContactNo());
                txtCenterContactNo1.setText(newValue.getCenterContactNo1());
                txtCenterContactNo2.setText(newValue.getCenterContactNo2());
                txtCapacity.setText(Integer.toString(newValue.getCapacity()));
                btnSave.setText("UPDATE");
            }

        });
    }
    @SuppressWarnings("Duplicates")
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
    @SuppressWarnings("Duplicates")
    public void btnSave_OnAction(ActionEvent actionEvent) {
        if(btnSave.getText().equals("SAVE")) {
            try {
                PreparedStatement pstm = DBConnection.getInstance().geConnection().prepareStatement("INSERT INTO QuarantineCenter VALUES (?,?,?,?,?,?,?,?,?)");
                pstm.setObject(1, txtCenterId.getText());
                pstm.setObject(2, txtCenterName.getText());
                pstm.setObject(3, txtCity.getText());
                pstm.setObject(4, cmbDistrict.getSelectionModel().getSelectedItem());
                pstm.setObject(5, txtHead.getText());
                pstm.setObject(6, txtHeadContactNo.getText());
                pstm.setObject(7, txtCenterContactNo1.getText());
                pstm.setObject(8, txtCenterContactNo2.getText());
                pstm.setObject(9, txtCapacity.getText());
                int affected = pstm.executeUpdate();

                if(affected == 0){
                    new Alert(Alert.AlertType.ERROR,"Couldn't save center information", ButtonType.OK).show();
                }else{
                    new Alert(Alert.AlertType.INFORMATION,"Added Successfully",ButtonType.OK).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                PreparedStatement pstm = DBConnection.getInstance().geConnection().prepareStatement
                        ("UPDATE QuarantineCenter SET centerId=?,centerName=?,city=?,district=?,head=?,headContactNo=?,centerContactNo1=?,centerContactNo2=?,capacity=? WHERE centerId ='"+txtCenterId.getText()+"'");
                pstm.setObject(1, txtCenterId.getText());
                pstm.setObject(2, txtCenterName.getText());
                pstm.setObject(3, txtCity.getText());
                pstm.setObject(4, cmbDistrict.getSelectionModel().getSelectedItem());
                pstm.setObject(5, txtHead.getText());
                pstm.setObject(6, txtHeadContactNo.getText());
                pstm.setObject(7, txtCenterContactNo1.getText());
                pstm.setObject(8, txtCenterContactNo2.getText());
                pstm.setObject(9, txtCapacity.getText());
                int affected = pstm.executeUpdate();
                if(affected>0){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        String id = txtCenterId.getText();
        try {
            PreparedStatement pstm = DBConnection.getInstance().geConnection().prepareStatement("DELETE  FROM QuarantineCenter WHERE centerId=?");
            pstm.setObject(1,id);
            int affected = pstm.executeUpdate();
            if(affected>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully", ButtonType.OK).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btnSave.setText("SAVE");
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        Clear();
        btnSave.setText("SAVE");
    }

    public void loadCentersList(){
        try {
            PreparedStatement pstm = DBConnection.getInstance().geConnection().prepareStatement
                    ("SELECT * FROM QuarantineCenter");
            ResultSet rst = pstm.executeQuery();
            ObservableList<CenterTM> centers = listCenters.getItems();
            while(rst.next()) {
                String centerId = rst.getString("centerId");
                String centerName = rst.getString("centerName");
                String city = rst.getString("city");
                String district = rst.getString("district");
                String head = rst.getString("head");
                String headContactNo = rst.getString("headContactNo");
                String centerContactNo1 = rst.getString("centerContactNo1");
                String centerContactNo2 = rst.getString("centerContactNo2");
                int capacity = rst.getInt("capacity");
                centers.add(new CenterTM(centerId,centerName,city,district,head,headContactNo,centerContactNo1,centerContactNo2,capacity));
                centersList.add(new CenterTM(centerId,centerName,city,district,head,headContactNo,centerContactNo1,centerContactNo2,capacity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void generateCenterID(){
        int maxId = 0;
        try {
            PreparedStatement pstm = DBConnection.getInstance().geConnection().prepareStatement
                    ("SELECT * FROM QuarantineCenter ORDER BY centerId DESC LIMIT 1");
            ResultSet rst = pstm.executeQuery();
            while(rst.next()) {
                String hospitalID = rst.getString("centerId");
                int id = Integer.parseInt(hospitalID.replace("C",""));
                if(maxId<id){
                    maxId=id;
                }
                maxId=maxId+1;
                String newId = "";
                if(maxId<10){
                    newId="C00"+maxId;
                }else if(maxId<100){
                    newId="C0"+maxId;
                }else{
                    newId="C"+maxId;
                }
                txtCenterId.setText(newId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("Duplicates")
    public void Clear(){
        txtCenterId.clear();
        txtCenterName.clear();
        cmbDistrict.getSelectionModel().clearSelection();
        txtCity.clear();
        txtHead.clear();
        txtHeadContactNo.clear();
        txtCenterContactNo1.clear();
        txtCenterContactNo2.clear();
        txtCapacity.clear();
    }
}
