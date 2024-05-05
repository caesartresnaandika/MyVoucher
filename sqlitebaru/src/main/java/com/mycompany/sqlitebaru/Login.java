/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sqlitebaru;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 *
 * @author Stefi
 */
public class Login {
    @FXML
    private TextField email;
    @FXML
    private TextField pass;
    Connection conn;
    Statement statement;
    ResultSet rs;
    @FXML
    protected void login() throws IOException{
      try{
          statement=conn.createStatement();
          rs=st.executeQuery("SELECT * FROM user WHERE email='"+email.getText()+"'AND password='"+pass.getText()+"'");
          if(rs.next()){
              if(email.getText().equals(rs.getString("email")) && pass.getText(rs.getString("pass"))){
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("halaman_MenuUtama.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Daftar Catatan");
                stage.setScene(scene);
                stage.show();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Information");
                alert.setContentText("Login success");
              }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Salah username atau password");
                alert.show();
              }
          }
      }catch(SQLException e){
          e.printStackTrace();
      }
    }
}
