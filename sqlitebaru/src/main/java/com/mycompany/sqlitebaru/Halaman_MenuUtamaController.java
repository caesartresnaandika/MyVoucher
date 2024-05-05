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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;

/**
 * FXML Controller class
 *
 * @author nicho
 */
public class Halaman_MenuUtamaController implements Initializable {
    

    

//    Connection conn;
//
//    public void connectdb() throws SQLException {
//        conn = DriverManager.getConnection("jdbc:sqlite:data.db");
//    }

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
    
<<<<<<< Updated upstream
//    private int idbr2 = hlc.idbr;
    

    
    
=======
    private ObservableList<User> dataObservableList;
    @FXML
    private TableView<User> idTable;
    @FXML
    private TableColumn<User, String> idColNo;
    @FXML
    private TableColumn<User, String> idColTittle;
    
    private Connection connection;
    
    User selectedUser;
   
>>>>>>> Stashed changes

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getConnection();
        dataObservableList = FXCollections.observableArrayList();
        idTable.setItems(dataObservableList);
        idColNo.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        idColTittle.setCellValueFactory(new PropertyValueFactory<>("nama_depan"));
        this.getAllData();
        
        
        
         idTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observableValue, User course, User t1) {
                if (observableValue.getValue() != null) {
                    selectedUser = observableValue.getValue();
                    idColNo.setText(observableValue.getValue().getemail());
                    idColTittle.setText(observableValue.getValue().getnama_depan());
                    
                }
            }
         });
        
        // TODO
//        try {
//            connectdb();
////            System.out.println("hhhhh");
//        } catch (SQLException ex) {
////            System.out.println("uuuu");
//            Logger.getLogger(Halaman_MenuUtamaController.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
    private void getAllData() {
        String query = "SELECT * FROM User";
        dataObservableList.clear();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_user = resultSet.getInt("id_user");
                String nama_depan = resultSet.getString("nama_depan");
                String nama_belakang = resultSet.getString("nama_belakang");
                String email = resultSet.getString("email");
                User user = new User(id_user, nama_depan, nama_belakang, email);
                dataObservableList.addAll(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database query error
        }
    }
    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
