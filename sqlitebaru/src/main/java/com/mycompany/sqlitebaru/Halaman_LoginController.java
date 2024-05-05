/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.sqlitebaru;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author nicho
 */
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
    @FXML
    private Hyperlink idHidePass;
    @FXML
    private Button idBtnCreateAcc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onBtnLoginClick(ActionEvent event) {
    }

    @FXML
    private void onBtnCreateAccClick(ActionEvent event) {
    }
    
}
