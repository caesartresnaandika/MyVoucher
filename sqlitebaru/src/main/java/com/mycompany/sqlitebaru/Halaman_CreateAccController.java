/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.sqlitebaru;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Halaman_CreateAccController implements Initializable {


    @FXML
    private Hyperlink idHelpC;
    @FXML
    private Hyperlink idToS2;
    @FXML
    private Hyperlink idPP2;
    @FXML
    private Hyperlink idHLAboutUs;
    @FXML
    private TextField idFirstName;
    @FXML
    private TextField idLastName;
    @FXML
    private TextField idEmail;
    @FXML
    private PasswordField IdPass1;
    @FXML
    private PasswordField idPass2;
    @FXML
    private Button idBtnCreateAcc;
    @FXML
    private Hyperlink idToU1;
    @FXML
    private Hyperlink idPP1;
    @FXML
    private Hyperlink idHLLogin;
    private Connection conn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getConnection();// TODO
    }    
    
    @FXML
    private void onHLAboutUsClick() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("halaman_AboutUs.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("About Us");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onBtnCreateAccClick() throws IOException{
        if(idFirstName.getText().isEmpty() || idLastName.getText().isEmpty() || idEmail.getText().isEmpty() || IdPass1.getText().isEmpty() || idPass2.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Lengkapi semua informasi!");
            alert.show();
        }else if(!idPass2.getText().equals(IdPass1.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Passwords do not match!");
            alert.show();
        }else{
            String firstName=idFirstName.getText();
            String lastName=idLastName.getText();
            String email=idEmail.getText();
            String pass=IdPass1.getText();
            String query = "INSERT INTO user (nama_depan, nama_belakang, email, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, pass);
                preparedStatement.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Create Account Success");
                alert.show();
                App.setRoot("halaman_Login");
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database query error
//                return false;
            }
        }
    }

    @FXML
    private void onHLLoginClick() throws IOException {
        App.setRoot("halaman_Login");
    }
    
    @FXML
    private void onidHelpClick()throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("halaman_HelpCenter.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Help Center");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void onidToUClick()throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("halaman_ToU.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Term of Use");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void onPPClick() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("halaman_PrivacyPolicy.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Privacy Policy");
        stage.setScene(scene);
        stage.show();
    }
    
    
    
    public Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:data.db");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

}
