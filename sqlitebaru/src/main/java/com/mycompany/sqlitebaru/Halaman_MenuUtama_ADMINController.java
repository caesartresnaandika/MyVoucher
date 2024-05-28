package com.mycompany.sqlitebaru;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Halaman_MenuUtama_ADMINController implements Initializable {
    @FXML
    private Label CompanyView;

    @FXML
    private Label DescriptionView;

    @FXML
    private Label DetailView;

    @FXML
    private Label ExpiredView;

    @FXML
    private Label TittleView;

    @FXML
    private Label TypeView;

    @FXML
    private Label TypeView1;

    @FXML
    private Label UserView;

    @FXML
    private Label ValidDateView;

    @FXML
    private Label ValueView;

    @FXML
    private MenuButton dropFilter;

    @FXML
    private MenuButton dropLanguage;

    @FXML
    private MenuButton dropNotif;

    @FXML
    private MenuButton dropProfil;

    @FXML
    private MenuItem goToNotif;

    @FXML
    private Text idAppName;

    @FXML
    private ImageView idBell;

    @FXML
    private Button idBtnDellete;

    @FXML
    private Button idBttnEdit;

    @FXML
    private TableColumn<Voucher, String> idColNo;

    @FXML
    private TableColumn<Voucher, String> idColTittle;

    @FXML
    private TableColumn<Voucher, String> idColUser;

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
    private TableView<Voucher> idTable;

    @FXML
    private Hyperlink idToS2;

    @FXML
    private ImageView idlogoFilter;

    @FXML
    private TextField searchBar;
    
    private ObservableList<Voucher> dataObservableList;
   

    private Connection connection;
    static Voucher selectedVoucher;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getConnection();
        dataObservableList = FXCollections.observableArrayList();
        idTable.setItems(dataObservableList);
        idColNo.setCellValueFactory(new PropertyValueFactory<>("id_voucher"));
        idColTittle.setCellValueFactory(new PropertyValueFactory<>("title_voucher"));
        idColUser.setCellValueFactory(new PropertyValueFactory<>("nama_depan"));
        this.getAllData();
        
        idTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Voucher>() {
            @Override
            public void changed(ObservableValue<? extends Voucher> observableValue, Voucher oldVoucher, Voucher newVoucher) {
                if (observableValue.getValue() != null) {
                    selectedVoucher = observableValue.getValue();
                    updateVoucherDetails(selectedVoucher);
                }
            }
        });
        
        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterData(newValue);
            }
        });
    }

    @FXML
    public void onBtnUseClick() throws IOException {
        getConnection();
        if (selectedVoucher != null) {
            String query = "DELETE FROM voucher WHERE id_voucher = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, selectedVoucher.getId_voucher());
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    dataObservableList.remove(selectedVoucher);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Voucher Used successfully");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to Use voucher");
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

    private void getAllData() {
        String query = "SELECT voucher.id_voucher, voucher.title_voucher, voucher.company, voucher.type, voucher.detail_voucher, " +
                       "voucher.valid_date, voucher.expired_date, voucher.description, user.id_user, user.nama_depan " +
                       "FROM voucher " +
                       "JOIN user ON voucher.id_user = user.id_user";
        dataObservableList.clear();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_user = resultSet.getInt("id_user");
                int id_voucher = resultSet.getInt("id_voucher");
                String title_voucher = resultSet.getString("title_voucher");
                String company = resultSet.getString("company");
                String type = resultSet.getString("type");
                String detail_voucher = resultSet.getString("detail_voucher");
                long valid_date = resultSet.getLong("valid_date");
                long expired_date = resultSet.getLong("expired_date");
                String description = resultSet.getString("description");
                String nama_depan = resultSet.getString("nama_depan");

                // Create Voucher object using the constructor
                Voucher voucher = new Voucher(id_voucher, id_user, title_voucher, company, type, detail_voucher, valid_date, expired_date, description, nama_depan);

                // Add Voucher object to dataObservableList
                dataObservableList.add(voucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    private void filterData(String query) {
        ObservableList<Voucher> filteredList = dataObservableList.stream()
            .filter(voucher -> 
                String.valueOf(voucher.getId_voucher()).contains(query) || 
                voucher.getTitle_voucher().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toCollection(FXCollections::observableArrayList));
        idTable.setItems(filteredList);
    }
    
    
    private String convertLongToDate(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTime.format(formatter);
    }

    private void updateVoucherDetails(Voucher voucher) {
        TittleView.setText(voucher.getTitle_voucher());
        DescriptionView.setText(voucher.getDescription());
        CompanyView.setText(voucher.getCompany());
        UserView.setText(voucher.getNama_depan());
        ValidDateView.setText(convertLongToDate(voucher.getValid_date()));
        ExpiredView.setText(convertLongToDate(voucher.getExpired_date()));
        TypeView.setText(voucher.getType());
        ValueView.setText(String.valueOf(voucher.getDetail_voucher()));
    }
    
    @FXML
    void btngoToNotif(ActionEvent event) {

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
        App.setRoot("halaman_History_ADMIN");
    }

    @FXML
    void handlerbuttonLogout() throws IOException{
        Halaman_LoginController.iduser=0;
        closeConnection();
        App.setRoot("halaman_Login");
    }

    @FXML
    void handlerbuttonProfile()throws IOException {
    }
}
