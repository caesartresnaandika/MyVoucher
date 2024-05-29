package com.mycompany.sqlitebaru;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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

    @FXML
    void BtnSaveClick(ActionEvent event) {

    }

    @FXML
    void btnbackclick(ActionEvent event) throws IOException {
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
    void btngoToNotif(ActionEvent event) throws IOException{
        if (Halaman_LoginController.iduser == 1) {
                App.setRoot("halaman_notif_ADMIN");
            } else {App.setRoot("halaman_Notif");}
    }


    @FXML
    void onHLAboutUsClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("halaman_AboutUs.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("About Us");
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void handlerbuttonHistory(ActionEvent event) throws IOException {
        if (Halaman_LoginController.iduser == 1) {
                App.setRoot("halaman_History_ADMIN");
            } else {
            App.setRoot("halaman_History");
        }
    }
    
    @FXML
    void handlerbuttonLogout() throws IOException{
        Halaman_LoginController.iduser=0;
        closeConnection();
        App.setRoot("halaman_Login");
    }

    @FXML
    void handlerbuttonProfile()throws IOException {
        App.setRoot("halaman_EditProfile");
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getConnection();
    }

}
