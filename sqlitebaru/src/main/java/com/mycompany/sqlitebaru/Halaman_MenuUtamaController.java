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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    
//<<<<<<< Updated upstream
//    private int idbr2 = hlc.idbr;
    

    
    
//=======
//    private ObservableList<User> dataObservableList;
//    @FXML
//    private TableView<User> idTable;
//    @FXML
//    private TableColumn<User, String> idColNo;
//    @FXML
//    private TableColumn<User, String> idColTittle;
//    
//    private Connection connection;
//    
//    User selectedUser;
    
    private ObservableList<Voucher> dataObservableList;
    @FXML
    private TableView<Voucher> idTable;
    @FXML
    private TableColumn<Voucher, String> idColNo;
    @FXML
    private TableColumn<Voucher, String> idColTittle;
    
    private Connection connection;
    
    Voucher selectedvoucher;
   
//>>>>>>> Stashed changes

    @Override
public void initialize(URL location, ResourceBundle resources) {
    getConnection();
    dataObservableList = FXCollections.observableArrayList();
    idTable.setItems(dataObservableList);
    idColNo.setCellValueFactory(new PropertyValueFactory<>("idVoucher"));
    idColTittle.setCellValueFactory(new PropertyValueFactory<>("titleVoucher"));
    this.getAllData();
     idTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Voucher>() {
        @Override
        public void changed(ObservableValue<? extends Voucher> observableValue, Voucher course, Voucher t1) {
            if (observableValue.getValue() != null) {
                selectedvoucher = observableValue.getValue();
                idTittle.setText(observableValue.getValue().getTitleVoucher());
                idDate.setText(String.valueOf(observableValue.getValue().getValue()));            
            }
        }
     });
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
    void BtnNewVoucher() throws IOException {
        App.setRoot("halaman_CreateVoucher");
    }
    @FXML
    private void onHLAboutUsClick() {
        Halaman_LoginController hlc = new Halaman_LoginController();
        // Implement your event handling logic here, e.g., display an about us dialog
        int a = hlc.iduser;
        System.out.println(a);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "ini id nya : "+a+"");
        alert.show();
    }
    private void getAllData() {
        String query = "SELECT * FROM voucher";
        dataObservableList.clear();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_voucher = resultSet.getInt("id_voucher");
                String title_voucher = resultSet.getString("title_voucher");
                String description = resultSet.getString("description");
                Voucher voucher = new Voucher(id_voucher,title_voucher, description);
                dataObservableList.addAll(voucher);
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
