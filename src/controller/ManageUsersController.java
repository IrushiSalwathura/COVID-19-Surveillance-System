package controller;

import DBConnect.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import util.CenterTM;
import util.HospitalTM;
import util.UsersTM;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class ManageUsersController {
    public JFXComboBox cmbUserRole;
    public AnchorPane root;
    public JFXTextField txtName;
    public JFXTextField txtContactNo;
    public JFXTextField txtEmail;
    public JFXTextField txtUsername;
    public JFXTextField txtPassword;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnAdd;
    public JFXComboBox<HospitalTM> cmbHospitals;
    public JFXComboBox<CenterTM> cmbQuarantineCenters;
    public Label lblLocation;
    public TableView<UsersTM> tblUser;
    public JFXTextField txtSearch;
    boolean flag = true;

    private ArrayList<UsersTM> usersTemp = new ArrayList<>();

    public void initialize(){
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(root);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        disableFields();
        cmbHospitals.setVisible(false);
        cmbQuarantineCenters.setVisible(false);
        lblLocation.setVisible(false);

        cmbUserRole.getItems().removeAll(cmbUserRole.getItems());
        cmbUserRole.getItems().addAll("Admin", "P.S.T.F. Member", "Hospital IT","Quarantine Center IT");


        tblUser.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("username"));
        tblUser.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblUser.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Role"));
        tblUser.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("Button"));
        //hidden columns
        tblUser.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("userContactNo"));
        tblUser.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        tblUser.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("password"));

        loadUsers();


        tblUser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UsersTM>() {
            @Override
            public void changed(ObservableValue<? extends UsersTM> observable, UsersTM oldValue, UsersTM selectedUser) {
                if(selectedUser==null){
                    return;
                }
                btnSave.setText("UPDATE");
                enableFields();
                txtName.setText(selectedUser.getName());
                txtUsername.setText(selectedUser.getUsername());
                txtContactNo.setText(selectedUser.getUserContactNo());
                txtEmail.setText(selectedUser.getUserEmail());
                txtPassword.setText(selectedUser.getPassword());
                cmbUserRole.getSelectionModel().select(selectedUser.getRole());

                if(cmbUserRole.getSelectionModel().getSelectedItem().equals("Hospital IT")){
                    try {
                        cmbQuarantineCenters.setVisible(false);
                        cmbHospitals.setVisible(true);
                        lblLocation.setVisible(true);
                        PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("SELECT hospitalId FROM user_hospital WHERE username=?");
                        preparedStatement.setObject(1,txtUsername.getText());
                        ResultSet resultSet = preparedStatement.executeQuery();

                        ObservableList<HospitalTM> hospitals = cmbHospitals.getItems();
                        loadHospitals();

                        while(resultSet.next()){
                            String hospitalId = resultSet.getString(1);

                            for (HospitalTM hospital : hospitals){
                                if(hospital.getHospitalId().equals(hospitalId)){
                                    cmbHospitals.getSelectionModel().select(hospital);
                                }
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                else if(cmbUserRole.getSelectionModel().getSelectedItem().equals("Quarantine Center IT")){
                    try {
                        cmbHospitals.setVisible(false);
                        cmbQuarantineCenters.setVisible(true);
                        lblLocation.setVisible(true);
                        PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("SELECT centerId FROM user_center WHERE username=?");
                        preparedStatement.setObject(1,txtUsername.getText());
                        ResultSet resultSet = preparedStatement.executeQuery();

                        ObservableList<CenterTM> centers = cmbQuarantineCenters.getItems();

                        while(resultSet.next()){
                            String centerId = resultSet.getString(1);

                            for (CenterTM center : centers){
                                if(center.getCenterId().equals(centerId)){
                                    cmbQuarantineCenters.getSelectionModel().select(center);
                                }
                            }

                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    cmbQuarantineCenters.setVisible(false);
                    cmbHospitals.setVisible(false);
                    lblLocation.setVisible(false);
                }
            }

        });

        cmbUserRole.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object selectedRole) {
                if(selectedRole==null){
                    return;
                }

                if (selectedRole.equals("Hospital IT")) {
                    loadHospitals();
                    cmbHospitals.setVisible(true);
                    lblLocation.setVisible(true);

                    try {
                        cmbQuarantineCenters.setVisible(false);
                        PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("SELECT hospitalId FROM user_hospital WHERE username=?");
                        preparedStatement.setObject(1,txtUsername.getText());
                        ResultSet resultSet = preparedStatement.executeQuery();

                        ObservableList<HospitalTM> hospitals = cmbHospitals.getItems();
                        loadHospitals();

                        while(resultSet.next()){
                            String hospitalId = resultSet.getString(1);

                            for (HospitalTM hospital : hospitals){
                                if(hospital.getHospitalId().equals(hospitalId)){
                                    cmbHospitals.getSelectionModel().select(hospital);
                                }
                            }

                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if (selectedRole.equals("Quarantine Center IT")) {
                    loadQuarantineCenters();
                    cmbQuarantineCenters.setVisible(true);
                    lblLocation.setVisible(true);

                    try {
                        cmbHospitals.setVisible(false);
                        PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("SELECT centerId FROM user_center WHERE username=?");
                        preparedStatement.setObject(1,txtUsername.getText());
                        ResultSet resultSet = preparedStatement.executeQuery();

                        ObservableList<CenterTM> centers = cmbQuarantineCenters.getItems();

                        while(resultSet.next()){
                            String centerId = resultSet.getString(1);

                            for (CenterTM center : centers){
                                if(center.getCenterId().equals(centerId)){
                                    cmbQuarantineCenters.getSelectionModel().select(center);
                                }
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                else{
                    cmbQuarantineCenters.setVisible(false);
                    cmbHospitals.setVisible(false);
                    lblLocation.setVisible(false);
                }
            }


        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<UsersTM> searchUsers = tblUser.getItems();
                searchUsers.clear();

                for (UsersTM user : usersTemp) {
                    if (user.getUsername().contains(newValue) || user.getUsername().toLowerCase().contains(newValue) || user.getName().contains(newValue) || user.getName().toLowerCase().contains(newValue)) {
                        searchUsers.add(user);
                    }

                }
            }
        });



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

    public void btnSave_OnAction(ActionEvent actionEvent) {
        if(txtUsername.getText().trim().length()==0||
                txtName.getText().trim().length()==0||
                txtPassword.getText().trim().length()==0||
                txtEmail.getText().trim().length()==0||
                txtContactNo.getText().trim().length()==0
        ){
            new Alert(Alert.AlertType.ERROR,"All fields are required", ButtonType.OK).show();
            return;
        }

        if(!txtContactNo.getText().matches("^[0-9]{1,3}[-][0-9]{1,7}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid contact number. The valid format is XXX-XXXXXXX",ButtonType.OK).show();
            return;
        }

        if(!txtEmail.getText().matches("^[a-z0-9_.]{1,}[@][a-z.]{1,}[a-z]{1,}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid email address",ButtonType.OK).show();
        }



        if(btnSave.getText().equals("SAVE")){
            try {
                PreparedStatement pst = DBConnection.getInstance().geConnection().prepareStatement("INSERT INTO User VALUES(?,?,?,?,?,?)");
                pst.setObject(1,txtName.getText());
                pst.setObject(2,txtContactNo.getText());
                pst.setObject(3,txtEmail.getText());
                pst.setObject(4,txtUsername.getText());
                pst.setObject(5,txtPassword.getText());
                pst.setObject(6,cmbUserRole.getSelectionModel().getSelectedItem());

                int affectedRows = pst.executeUpdate();

                if(affectedRows==0){
                    new Alert(Alert.AlertType.ERROR,"Couldn't save user information", ButtonType.OK).show();
                }
                else{
                    new Alert(Alert.AlertType.INFORMATION,"Added Successfully",ButtonType.OK).show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(cmbUserRole.getSelectionModel().getSelectedItem().equals("Hospital IT")){
                try {
                    PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("INSERT INTO user_hospital VALUES(?,?)");
                    preparedStatement.setObject(1,txtUsername.getText());
                    preparedStatement.setObject(2,cmbHospitals.getSelectionModel().getSelectedItem().getHospitalId());
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (cmbUserRole.getSelectionModel().getSelectedItem().equals("Quarantine Center IT")) {
                try {
                    PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("INSERT INTO user_center VALUES (?,?)");
                    preparedStatement.setObject(1,txtUsername.getText());
                    preparedStatement.setObject(2,cmbQuarantineCenters.getSelectionModel().getSelectedItem().getCenterId());
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        else{
            try {
                PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("UPDATE User SET name=?,userContactNo=?,userEmail=?,username=?,password=?,userRole=? WHERE username='" + txtUsername.getText() + "'");
                preparedStatement.setObject(1,txtName.getText());
                preparedStatement.setObject(2,txtContactNo.getText());
                preparedStatement.setObject(3,txtEmail.getText());
                preparedStatement.setObject(4,txtUsername.getText());
                preparedStatement.setObject(5,txtPassword.getText());
                preparedStatement.setObject(6,cmbUserRole.getSelectionModel().getSelectedItem());

                int affectedRows = preparedStatement.executeUpdate();

                if(affectedRows==0){
                    new Alert(Alert.AlertType.ERROR,"Couldn't update user information", ButtonType.OK).show();
                }
                else{
                    new Alert(Alert.AlertType.INFORMATION,"Updated User information Successfully",ButtonType.OK).show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(cmbUserRole.getSelectionModel().getSelectedItem().equals("Hospital IT")){

                try {
                    PreparedStatement pst = DBConnection.getInstance().geConnection().prepareStatement("SELECT hospitalId FROM user_hospital WHERE username=?");
                    pst.setObject(1,txtUsername.getText());
                    ResultSet resultSet = pst.executeQuery();

                    if(!resultSet.next()){
                        PreparedStatement preparedStatement;
                        preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("DELETE FROM user_center WHERE username=?");
                        preparedStatement.setObject(1,txtUsername.getText());
                        preparedStatement.executeUpdate();


                        preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("INSERT INTO user_hospital VALUES (?,?)");
                        preparedStatement.setObject(1,txtUsername.getText());
                        preparedStatement.setObject(2,cmbHospitals.getSelectionModel().getSelectedItem().getHospitalId());
                        preparedStatement.executeUpdate();


                    }
                    else{
                        PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("UPDATE user_hospital SET username=?,hospitalId=? WHERE username='"+txtUsername.getText()+"'");
                        preparedStatement.setObject(1,txtUsername.getText());
                        preparedStatement.setObject(2,cmbHospitals.getSelectionModel().getSelectedItem().getHospitalId());
                        preparedStatement.executeUpdate();

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (cmbUserRole.getSelectionModel().getSelectedItem().equals("Quarantine Center IT")) {
                try {
                    PreparedStatement pst = DBConnection.getInstance().geConnection().prepareStatement("SELECT centerId FROM user_center WHERE username=?");
                    pst.setObject(1,txtUsername.getText());
                    ResultSet resultSet = pst.executeQuery();

                    if(!resultSet.next()){

                        PreparedStatement preparedStatement;
                        preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("DELETE FROM user_hospital WHERE username=?");
                        preparedStatement.setObject(1,txtUsername.getText());
                        preparedStatement.executeUpdate();


                        preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("INSERT INTO user_center VALUES (?,?)");
                        preparedStatement.setObject(1,txtUsername.getText());
                        preparedStatement.setObject(2,cmbQuarantineCenters.getSelectionModel().getSelectedItem().getCenterId());
                        preparedStatement.executeUpdate();


                    }
                    else{
                        PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("UPDATE user_center SET username=?,centerId=? WHERE username='"+txtUsername.getText()+"'");
                        preparedStatement.setObject(1,txtUsername.getText());
                        preparedStatement.setObject(2,cmbQuarantineCenters.getSelectionModel().getSelectedItem().getCenterId());
                        preparedStatement.executeUpdate();


                    }



                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        loadUsers();
        clearFields();
        btnSave.setText("SAVE");
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        if(cmbUserRole.getSelectionModel().getSelectedItem().equals("Hospital IT")){
            try {
                PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("DELETE FROM user_hospital WHERE username=?");
                preparedStatement.setObject(1,txtUsername.getText());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (cmbUserRole.getSelectionModel().getSelectedItem().equals("Quarantine Center IT")) {
            try {
                PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("DELETE FROM user_center WHERE username=?");
                preparedStatement.setObject(1,txtUsername.getText());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("DELETE from User WHERE username=?");
            preparedStatement.setObject(1,txtUsername.getText());

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows==0){
                new Alert(Alert.AlertType.ERROR,"Couldn't delete user information", ButtonType.OK).show();
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully",ButtonType.OK).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadUsers();
        clearFields();
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        enableFields();
        clearFields();
        String pwd = generateRandomString();
        txtPassword.setText(pwd);
        btnSave.setText("SAVE");
    }

    private void loadUsers(){
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("SELECT * FROM User");
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<UsersTM> users = tblUser.getItems();
            users.clear();
            usersTemp.clear();

            while(resultSet.next()){

                Button btnRemove = new Button("Remove");
                String name = resultSet.getString("name");
                String userContactNo = resultSet.getString("userContactNo");
                String userEmail = resultSet.getString("userEmail");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String userRole = resultSet.getString("userRole");

                UsersTM user1 = new UsersTM(username,name,userRole,btnRemove,userContactNo,userEmail,password);
                users.add(user1);
                usersTemp.add(user1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadHospitals(){
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("SELECT * FROM Hospital ORDER BY hospitalId");
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<HospitalTM> hospitals = cmbHospitals.getItems();
            hospitals.clear();


            while(resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String city = resultSet.getString(3);
                String district = resultSet.getString(4);
                int capacity = Integer.parseInt(resultSet.getString(5));
                String director = resultSet.getString(6);
                String directorContact = resultSet.getString(7);
                String tel1 = resultSet.getString(8);
                String tel2= resultSet.getString(9);
                String fax = resultSet.getString(10);
                String email = resultSet.getString(11);

                hospitals.add(new HospitalTM(id,name,city,district,capacity,director,directorContact,tel1,tel2,fax,email));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadQuarantineCenters(){
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().geConnection().prepareStatement("SELECT * FROM QuarantineCenter ORDER BY centerId");
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<CenterTM> centers = cmbQuarantineCenters.getItems();
            centers.clear();

            while(resultSet.next()){
                String id = resultSet.getString(1);
                String centerName = resultSet.getString("centerName");
                String city = resultSet.getString("city");
                String district = resultSet.getString("district");
                String head = resultSet.getString("head");
                String headContactNo = resultSet.getString("headContactNo");
                String centerContactNo1 = resultSet.getString("centerContactNo1");
                String centerContactNo2 = resultSet.getString("centerContactNo2");
                int capacity = Integer.parseInt(resultSet.getString("capacity"));

                centers.add(new CenterTM(id,centerName,city,district,head,headContactNo,centerContactNo1,centerContactNo2,capacity));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void enableFields(){
        txtUsername.setDisable(false);
        txtContactNo.setDisable(false);
        txtEmail.setDisable(false);
        txtName.setDisable(false);
        txtPassword.setDisable(false);
        btnSave.setDisable(false);
        btnDelete.setDisable(false);
        cmbUserRole.setDisable(false);
    }

    private void disableFields(){
        txtUsername.setDisable(true);
        txtContactNo.setDisable(true);
        txtEmail.setDisable(true);
        txtName.setDisable(true);
        txtPassword.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        cmbUserRole.setDisable(true);
    }

    private void clearFields(){
        txtUsername.clear();
        txtContactNo.clear();
        txtEmail.clear();
        txtName.clear();
        txtPassword.clear();
        cmbHospitals.setVisible(false);
        cmbQuarantineCenters.setVisible(false);
        lblLocation.setVisible(false);
        cmbUserRole.getSelectionModel().clearSelection();
        tblUser.getSelectionModel().clearSelection();
    }

    private String generateRandomString() {
        byte[] randomBytes = new byte[10];
        Random rnd = new Random();
        for (int i = 0; i < randomBytes.length; i++) {
            randomBytes[i] = (byte) (rnd.nextInt(122 - 65) + 65);
        }
        return new String(randomBytes, StandardCharsets.US_ASCII);
    }
}
