///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
// */
//package com.mycompany.sqlitebaru;
//
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.Hyperlink;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class Halaman_LoginController implements Initializable {
//
//    @FXML
//    private Hyperlink idHelpC;
//    @FXML
//    private Hyperlink idToU2;
//    @FXML
//    private Hyperlink idPP2;
//    @FXML
//    private Text idPageTittle;
//    @FXML
//    private TextField idEmail;
//    @FXML
//    private Text labelEmail;
//    @FXML
//    private Text labelPassword;
//    @FXML
//    private PasswordField idPassword;
//    @FXML
//    private Button idBtnLogin;
//    @FXML
//    private Hyperlink idForgetPass;
//    @FXML
//    private Hyperlink idPP1;
//    @FXML
//    private Hyperlink idToU1;
//    @FXML
//    private Hyperlink idHidePass;
//    @FXML
//    private Button idBtnCreateAcc;
//    @FXML
//    private Hyperlink idHLAboutUs;
//    private Connection conn;
//    Statement statement;
//    ResultSet rs;
//    public static int iduser;
//
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        getConnection(); // TODO
//    }    
//
//    @FXML
//    private void onBtnLoginClick() throws IOException{
//        try{
//          statement=conn.createStatement();
//          rs=statement.executeQuery("SELECT * FROM user WHERE email='"+idEmail.getText()+"'AND password='"+idPassword.getText()+"'");
//          if(rs.next()){
////              if(idEmail.getText().equals(rs.getString("email")) && idPassword.getText().equals(rs.getString("password"))){
////                Stage stage = new Stage();
//                iduser=rs.getInt("id_user");
//                App.setRoot("halaman_MenuUtama_tabel");
////                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
////                Scene scene = new Scene(fxmlLoader.load());
////                stage.setTitle("Menu Utama");
////                stage.setScene(scene);
////                stage.show();
//                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Login success");
//                alert.show();
////              }
//          }else{
//                Alert alert = new Alert(Alert.AlertType.ERROR, "Salah username atau password");
//                alert.show();
//              }
//        }catch(SQLException e){
//          e.printStackTrace();
//      }
//    }
//
//    @FXML
//    private void onBtnCreateAccClick() throws IOException {
//        App.setRoot("halaman_CreateAcc");
//    }
//    
//    @FXML
//    private void onHLAboutUsClick() throws IOException {
//        Stage stage = new Stage();
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("halaman_AboutUs.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("About Us");
//        stage.setScene(scene);
//        stage.show();
//    }
//    
//    public Connection getConnection() {
//        if (conn == null) {
//            try {
//                conn = DriverManager.getConnection("jdbc:sqlite:data.db");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return conn;
//    }
//    
//}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.sqlitebaru;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Halaman_LoginController implements Initializable {

    @FXML
    private Hyperlink idHelpC;
    @FXML
    private Hyperlink idToU2;
    @FXML
    private Hyperlink idPP2;
    @FXML
    private Text idPageTittle;
    @FXML
    private TextField idEmail;
    @FXML
    private Text labelEmail;
    @FXML
    private Text labelPassword;
    @FXML
    private PasswordField idPassword;
    @FXML
    private Button idBtnLogin;
    @FXML
    private Hyperlink idForgetPass;
    @FXML
    private Hyperlink idPP1;
    @FXML
    private Hyperlink idToU1;
//    @FXML
//    private Hyperlink idHidePass;
    @FXML
    private Button idBtnCreateAcc;
    @FXML
    private Hyperlink idHLAboutUs;
    private Connection conn;
    Statement statement;
    ResultSet rs;
    public static int iduser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getConnection(); // TODO
    }    

    @FXML
    private void onBtnLoginClick() throws IOException{
        try{
          statement=conn.createStatement();
          rs=statement.executeQuery("SELECT * FROM user WHERE email='"+idEmail.getText()+"'AND password='"+idPassword.getText()+"'");
          if(rs.next()){
//              if(idEmail.getText().equals(rs.getString("email")) && idPassword.getText().equals(rs.getString("password"))){
//                Stage stage = new Stage();
                iduser=rs.getInt("id_user");
                App.setRoot("halaman_MenuUtama_tabel");
//                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
//                Scene scene = new Scene(fxmlLoader.load());
//                stage.setTitle("Menu Utama");
//                stage.setScene(scene);
//                stage.show();
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Login success");
                alert.show();
//              }
          }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Salah username atau password");
                alert.show();
              }
        }catch(SQLException e){
          e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @FXML
    private void onBtnCreateAccClick() throws IOException {
        App.setRoot("halaman_CreateAcc");
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
    
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
