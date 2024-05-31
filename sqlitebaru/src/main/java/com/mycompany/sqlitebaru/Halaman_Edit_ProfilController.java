package com.mycompany.sqlitebaru;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Halaman_Edit_ProfilController implements Initializable{

    @FXML
    private MenuItem goToNotif;

    @FXML
    private Button idBack;

    @FXML
    private TextField idEmail;

    @FXML
    private TextField idFirstName;

    @FXML
    private TextField idLastName;

    @FXML
    private PasswordField idPassword;

    @FXML
    private Button idSave;

    @FXML
    private MenuItem idprofile;
    
    private Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getConnection();
        loadData();
    }
    
    private void loadData(){
        String query = "SELECT nama_depan, nama_belakang, email, password FROM user WHERE id_user=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Halaman_LoginController.iduser);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                idFirstName.setText(rs.getString("nama_depan"));
                idLastName.setText(rs.getString("nama_belakang"));
                idEmail.setText(rs.getString("email"));
                idPassword.setText(rs.getString("password"));
            }
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Load data tidak berhasil!");
            alert.show();
        }
    }
    
    @FXML
    private void BtnSaveClick(){
        String firstName=idFirstName.getText();
        String lastName=idLastName.getText();
        String email=idEmail.getText();
        String pass=idPassword.getText();
        String query = "UPDATE user SET nama_depan=?, nama_belakang=?, email=?, password=? WHERE id_user=?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, pass);
            preparedStatement.setInt(5, Halaman_LoginController.iduser);
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Update profil berhasil");
            alert.show();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Update profil tidak berhasil!");
            alert.show();
        }
    }

    @FXML
    private void btnbackclick(ActionEvent event) throws IOException {
        if (Halaman_LoginController.iduser == 1) {
                App.setRoot("halaman_MenuUtama_tabel_ADMIN");
            }else{
        App.setRoot("halaman_MenuUtama_tabel");}
    }
    
    private Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:data.db");
                System.out.println("Connection established in getConnection method.");
            } catch (SQLException e) {
                System.out.println("Failed to establish connection: " + e.getMessage());
            }
        }
        return connection;
    }
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void btngoToNotif(ActionEvent event) throws IOException{
        if (Halaman_LoginController.iduser == 1) {
                App.setRoot("halaman_notif_ADMIN");
            } else {App.setRoot("halaman_Notif");}
    }


    @FXML
    private void onHLAboutUsClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("halaman_AboutUs.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("About Us");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handlerbuttonHistory(ActionEvent event) throws IOException {
        if (Halaman_LoginController.iduser == 1) {
                App.setRoot("halaman_History_ADMIN");
            } else {
            App.setRoot("halaman_History");
        }
    }
    
    @FXML
    private void handlerbuttonLogout() throws IOException{
        Halaman_LoginController.iduser=0;
        closeConnection();
        App.setRoot("halaman_Login");
    }

    @FXML
    private void handlerbuttonProfile()throws IOException {
        App.setRoot("halaman_EditProfile");
    }
}
