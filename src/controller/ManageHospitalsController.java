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
import util.HospitalTM;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageHospitalsController {
    public JFXComboBox cmbDistrict;
    public JFXTextField txtSearch;
    public JFXListView<HospitalTM> listHospitals;
    public JFXTextField txtID;
    public JFXTextField txtHospitalName;
    public JFXTextField txtCity;
    public JFXTextField txtCapacity;
    public JFXTextField txtDirector;
    public JFXTextField txtDirectorContact;
    public JFXTextField txtHospitalContact1;
    public JFXTextField txtHospitalContact2;
    public JFXTextField txtHospitalFax;
    public JFXTextField txtHospitalEmail;
    public JFXButton btnSave;
    public AnchorPane root;
    boolean flag = true;
    ArrayList<HospitalTM> hospitalsList = new ArrayList<>();

    public void initialize(){
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(root);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        loadHospitalList();
        generateHospitalID();
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
                ObservableList<HospitalTM> searchHospitals = listHospitals.getItems();
                searchHospitals.clear();

                for (HospitalTM hospital : hospitalsList ) {
                    if (hospital.getHospitalName().contains(newValue) || hospital.getHospitalName().toLowerCase().contains(newValue)) {
                        searchHospitals.add(hospital);
                    }

                }
            }
        });
        listHospitals.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HospitalTM>() {
            @Override
            public void changed(ObservableValue<? extends HospitalTM> observable, HospitalTM oldValue, HospitalTM newValue) {
                if(newValue == null){
                    Clear();
                    btnSave.setDisable(true);
                    return;
                }
                txtID.setText(newValue.getHospitalId());
                txtHospitalName.setText(newValue.getHospitalName());
                txtCity.setText(newValue.getCity());
                cmbDistrict.getSelectionModel().select(newValue.getDistrict());
                txtCapacity.setText(Integer.toString(newValue.getCapacity()));
                txtDirector.setText(newValue.getDirector());
                txtDirectorContact.setText(newValue.getDirectorContactNo());
                txtHospitalContact1.setText(newValue.getHospitalContactNo1());
                txtHospitalContact2.setText(newValue.getHospitalContactNo2());
                txtHospitalFax.setText(newValue.getHospitalFax());
                txtHospitalEmail.setText(newValue.getHospitalEmail());
                btnSave.setText("UPDATE");
            }

        });
    }
    @SuppressWarnings("Duplicates")
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
    @SuppressWarnings("Duplicates")
    public void btnSave_OnAction(ActionEvent actionEvent) {
        if(btnSave.getText().equals("SAVE")) {
            try {
                PreparedStatement pstm = DBConnection.getInstance().geConnection().prepareStatement("INSERT INTO Hospital VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                pstm.setObject(1, txtID.getText());
                pstm.setObject(2, txtHospitalName.getText());
                pstm.setObject(3, txtCity.getText());
                pstm.setObject(4, cmbDistrict.getSelectionModel().getSelectedItem());
                pstm.setObject(5, txtCapacity.getText());
                pstm.setObject(6, txtDirector.getText());
                pstm.setObject(7, txtDirectorContact.getText());
                pstm.setObject(8, txtHospitalContact1.getText());
                pstm.setObject(9, txtHospitalContact2.getText());
                pstm.setObject(10, txtHospitalFax.getText());
                pstm.setObject(11, txtHospitalEmail.getText());
                int affected = pstm.executeUpdate();

                if(affected == 0){
                    new Alert(Alert.AlertType.ERROR,"Couldn't save hospital information",ButtonType.OK).show();
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
                        ("UPDATE Hospital SET hospitalId=?,hospitalName=?,city=?,district=?,capacity=?,director=?,directorContactNo=?,hospitalContactNo1=?,hospitalContactNo2=?,hospitalFax=?,hospitalEmail=? WHERE hospitalId ='"+txtID.getText()+"'");
                pstm.setObject(1, txtID.getText());
                pstm.setObject(2, txtHospitalName.getText());
                pstm.setObject(3, txtCity.getText());
                pstm.setObject(4, cmbDistrict.getSelectionModel().getSelectedItem());
                pstm.setObject(5, txtCapacity.getText());
                pstm.setObject(6, txtDirector.getText());
                pstm.setObject(7, txtDirectorContact.getText());
                pstm.setObject(8, txtHospitalContact1.getText());
                pstm.setObject(9, txtHospitalContact2.getText());
                pstm.setObject(10, txtHospitalFax.getText());
                pstm.setObject(11, txtHospitalEmail.getText());
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
        String id = txtID.getText();
        try {
            PreparedStatement pstm = DBConnection.getInstance().geConnection().prepareStatement("DELETE  FROM Hospital WHERE hospitalId=?");
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
    public void generateHospitalID(){
        int maxId = 0;
        try {
            PreparedStatement pstm = DBConnection.getInstance().geConnection().prepareStatement
                    ("SELECT * FROM Hospital ORDER BY hospitalId DESC LIMIT 1");
            ResultSet rst = pstm.executeQuery();
            while(rst.next()) {
                String hospitalID = rst.getString("hospitalId");
                int id = Integer.parseInt(hospitalID.replace("H",""));
                if(maxId<id){
                    maxId=id;
                }
                maxId=maxId+1;
                String newId = "";
                if(maxId<10){
                    newId="H00"+maxId;
                }else if(maxId<100){
                    newId="H0"+maxId;
                }else{
                    newId="H"+maxId;
                }
                txtID.setText(newId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void loadHospitalList(){
        try {
            PreparedStatement pstm = DBConnection.getInstance().geConnection().prepareStatement
                    ("SELECT * FROM Hospital");
            ResultSet rst = pstm.executeQuery();
            ObservableList<HospitalTM> hospitals = listHospitals.getItems();
            while(rst.next()) {
                String hospitalId = rst.getString("hospitalId");
                String hospitalName = rst.getString("hospitalName");
                String city = rst.getString("city");
                String district = rst.getString("district");
                int capacity = rst.getInt("capacity");
                String director = rst.getString("director");
                String directorContact = rst.getString("directorContactNo");
                String hospitalContactNo1 = rst.getString("hospitalContactNo1");
                String hospitalContactNo2 = rst.getString("hospitalContactNo2");
                String hospitalFax = rst.getString("hospitalFax");
                String hospitalEmail = rst.getString("hospitalEmail");
                hospitals.add(new HospitalTM(hospitalId,hospitalName,city,district,capacity,director,directorContact,hospitalContactNo1,hospitalContactNo2,hospitalFax,hospitalEmail));
                hospitalsList.add(new HospitalTM(hospitalId,hospitalName,city,district,capacity,director,directorContact,hospitalContactNo1,hospitalContactNo2,hospitalFax,hospitalEmail));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @SuppressWarnings("Duplicates")
    public void Clear(){
        txtID.clear();
        txtHospitalName.clear();
        cmbDistrict.getSelectionModel().clearSelection();
        txtCity.clear();
        txtCapacity.clear();
        txtDirector.clear();
        txtDirectorContact.clear();
        txtHospitalContact1.clear();
        txtHospitalContact2.clear();
        txtHospitalFax.clear();
        txtHospitalEmail.clear();
    }
}
