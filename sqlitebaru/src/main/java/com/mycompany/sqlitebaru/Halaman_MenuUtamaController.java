package com.mycompany.sqlitebaru;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author nicho
 */
public class Halaman_MenuUtamaController implements Initializable {
    

    

    Connection conn;

    public void connectdb() throws SQLException {
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
    private Label idTittle;
    @FXML
    private TextFlow idDesk;
    @FXML
    private Label idDate;
    @FXML
    private ImageView idImage;
    @FXML
    private Button idBtnUse;
    @FXML
    private Button idBttnEdit;
    @FXML
    private Hyperlink idHLAboutUs;
    
//    private int idbr2 = hlc.idbr;
    

    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        try {
            connectdb();
        } catch (SQLException ex) {
            Logger.getLogger(Halaman_MenuUtamaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onBtnUseClick() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void onBtnEditClick() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    private void onHLAboutUsClick() {
        Halaman_LoginController hlc = new Halaman_LoginController();
        // Implement your event handling logic here, e.g., display an about us dialog
        int a = hlc.idbr;
        System.out.println(a);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "ini id nya : "+a+"");
        alert.show();
    }

}
