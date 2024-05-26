package com.mycompany.sqlitebaru;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Halaman_MenuUtamaController implements Initializable {

    @FXML
    private MenuButton dropFilter;

    @FXML
    private MenuButton dropLanguage;

    @FXML
    private MenuButton dropNotif;

    @FXML
    private MenuButton dropProfil;

    @FXML
    private Text idAppName;

    @FXML
    private ImageView idBell;

    @FXML
    private Button idBtnAddNewVouc;

    @FXML
    private Button idBtnUse;

    @FXML
    private Button idBttnEdit;
    @FXML
    private Label idDate;

    @FXML
    private TextFlow idDesk;

    @FXML
    private ImageView idFlag;

    @FXML
    private Hyperlink idHLAboutUs;

    @FXML
    private Hyperlink idHelpC;

    @FXML
    private ImageView idLogo;

    @FXML
    private Hyperlink idPP2;

    @FXML
    private ImageView idProfil;

    @FXML
    private Label idTitle;

    @FXML
    private Hyperlink idToS2;

    @FXML
    private ImageView idlogoFilter;

    @FXML
    private ImageView idlogoPlus;

    @FXML
    private ImageView imgvFoto;

    @FXML
    private TextField searchBar;

    private ObservableList<Voucher> dataObservableList;
    @FXML
    private TableView<Voucher> idTable;
    @FXML
    private TableColumn<Voucher, String> idColNo;
    @FXML
    private TableColumn<Voucher, String> idColTittle;

    private Connection connection;
    static Voucher selectedVoucher;

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
            public void changed(ObservableValue<? extends Voucher> observableValue, Voucher oldVoucher, Voucher newVoucher) {
                if (observableValue.getValue() != null) {
                    selectedVoucher = observableValue.getValue();
//                    idTittle.setText(selectedVoucher.getTitleVoucher());
                    idDate.setText(String.valueOf(selectedVoucher.getValue()));            
                }
            }
        });
    }

    @FXML
    private void onBtnUseClick() throws IOException {
        getConnection();
        if (selectedVoucher != null) {
            String query = "DELETE FROM voucher WHERE id_voucher = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, selectedVoucher.getIdVoucher());
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    dataObservableList.remove(selectedVoucher);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Voucher Used successfully");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to Used voucher");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the voucher");
            } finally {
                closeConnection();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "No voucher selected");
        }
    }

    @FXML
    void onBtnEditClick() throws IOException {
        App.setRoot("halaman_EditVoucher");
    }

    @FXML
    void BtnNewVoucher() throws IOException {
        App.setRoot("halaman_CreateVoucher");
    }

    @FXML
    private void onHLAboutUsClick() {
        Halaman_LoginController hlc = new Halaman_LoginController();
        int a = hlc.iduser;
        System.out.println(a);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "ini id nya : " + a);
        alert.show();
    }

    private void getAllData() {
    String query = "SELECT * FROM voucher";
    dataObservableList.clear();
    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
            int id_voucher = resultSet.getInt("id_voucher");
            int id_user = resultSet.getInt("id_user");
            String title_voucher = resultSet.getString("title_voucher");
            String company = resultSet.getString("company");
            String type = resultSet.getString("type");
            int value = resultSet.getInt("value");
            String detail_voucher = resultSet.getString("detail_voucher");
            long valid_date = resultSet.getLong("valid_date");
            long expired_date = resultSet.getLong("expired_date");
            String description = resultSet.getString("description");
            String image = resultSet.getString("image");

            Voucher voucher = new Voucher(id_user, id_voucher, title_voucher, company, type, value, detail_voucher, valid_date, expired_date, description, image);
            dataObservableList.add(voucher);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while retrieving the vouchers");
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
