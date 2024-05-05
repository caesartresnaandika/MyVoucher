/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.sqlitebaru;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author nicho
 */
public class Halaman_MenuUtamaController implements Initializable {
    Connection conn;
    public void connectdb() throws SQLException{
        conn = DriverManager.getConnection("jdbc:sqlite:data.db");
    }


    @FXML
    private Text idAppName;
    @FXML
    private TextField searchBar;
    @FXML
    private ImageView idLogo;
    @FXML
    private ImageView idFlag;
    @FXML
    private MenuButton dropLanguage;
    @FXML
    private MenuButton dropProfil;
    @FXML
    private ImageView idBell;
    @FXML
    private MenuButton dropNotif;
    @FXML
    private ImageView idProfil;
    @FXML
    private Button idBtnAddNewVouc;
    @FXML
    private ImageView idlogoPlus;
    @FXML
    private ImageView idlogoFilter;
    @FXML
    private MenuButton dropFilter;
    @FXML
    private Text idTittle;
    @FXML
    private Text idDesk;
    @FXML
    private Text idDate;
    @FXML
    private ImageView idImage;
    @FXML
    private Button idBtnUse;
    @FXML
    private Button idBttnEdit;
    
    private int IdUser; 
    

    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO

        try {
            connectdb();
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    } 
    @FXML
    private void onBtnUseClick() throws IOException{
        App.setRoot("halaman_Login");
    }
    @FXML
    private void onBtnEditClick()throws IOException{
        App.setRoot("secondary");
    }
    private void onHLAboutUsClick()throws IOException{
        App.setRoot("secondary");
    }
    
    
}
