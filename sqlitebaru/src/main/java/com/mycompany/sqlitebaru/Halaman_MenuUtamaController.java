package com.mycompany.sqlitebaru;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class Halaman_MenuUtamaController implements Initializable {
    @FXML
    private Hyperlink idHLAboutUs;
    @FXML
    private Label CompanyView;

    @FXML
    private Label DescriptionView;

    @FXML
    private Label ExpiredView;

    @FXML
    private Label TittleView;

    @FXML
    private Label TypeView;

    @FXML
    private Label ValidDateView;

    @FXML
    private Label ValueView;

    @FXML
    private MenuItem buttonHistory;

    @FXML
    private MenuItem buttonLogout;

    @FXML
    private MenuItem buttonProfile;

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
    private Button idBtnAddNewVouc;

    @FXML
    private Button idBtnUse;

    @FXML
    private Button idBttnEdit;

    @FXML
    private ImageView idFlag;
    @FXML
    private Hyperlink idHelpC;

    @FXML
    private ImageView idLogo;

    @FXML
    private Hyperlink idPP2;

    @FXML
    private ImageView idProfil;

    @FXML
    private Hyperlink idToS2;

    @FXML
    private ImageView idlogoFilter;

    @FXML
    private ImageView idlogoPlus;

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
        idColNo.setCellValueFactory(new PropertyValueFactory<>("id_voucher"));
        idColTittle.setCellValueFactory(new PropertyValueFactory<>("title_voucher"));
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
            long currentTime = System.currentTimeMillis();
            if (selectedVoucher.getExpired_date() < currentTime) {
                showAlert(Alert.AlertType.WARNING, "Voucher Expired", "The selected voucher is expired and cannot be used.");
                return;
            }
            try {
                // Insert into history
                insertIntoHistory(selectedVoucher);
                // Remove from vouchers
                removeVoucher(selectedVoucher);
                // Update table view
                dataObservableList.remove(selectedVoucher);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Voucher successfully moved to history.");
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to move voucher to history.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "No voucher selected.");
        }
    }

    @FXML
    private void onBtnEditClick() throws IOException {
        App.setRoot("halaman_EditVoucher");
    }

    @FXML
    void BtnNewVoucher() throws IOException {
        App.setRoot("halaman_CreateVoucher");
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

    private void getAllData() {
        String query = "SELECT * FROM voucher WHERE id_user=?";
        dataObservableList.clear();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Halaman_LoginController.iduser);
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

                // Create Voucher object using the constructor
                Voucher voucher = new Voucher(id_voucher, id_user, title_voucher, company, type, detail_voucher, valid_date, expired_date, description);

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
        ValidDateView.setText(convertLongToDate(voucher.getValid_date()));
        ExpiredView.setText(convertLongToDate(voucher.getExpired_date()));
        TypeView.setText(voucher.getType());
        ValueView.setText(String.valueOf(voucher.getDetail_voucher()));
    }

    private void insertIntoHistory(Voucher voucher) throws SQLException {
        String query = "INSERT INTO history (id_voucher, id_user, title_voucher, company, type, detail_voucher, valid_date, expired_date, description, use_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, voucher.getId_voucher());
            preparedStatement.setInt(2, voucher.getId_user());
            preparedStatement.setString(3, voucher.getTitle_voucher());
            preparedStatement.setString(4, voucher.getCompany());
            preparedStatement.setString(5, voucher.getType());
            preparedStatement.setString(6, voucher.getDetail_voucher());
            preparedStatement.setLong(7, voucher.getValid_date());
            preparedStatement.setLong(8, voucher.getExpired_date());
            preparedStatement.setString(9, voucher.getDescription());
            preparedStatement.setLong(10, System.currentTimeMillis());
            preparedStatement.executeUpdate();
        }
    }

    private void removeVoucher(Voucher voucher) throws SQLException {
        String query = "DELETE FROM voucher WHERE id_voucher = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, voucher.getId_voucher());
            preparedStatement.executeUpdate();
        }
    }

    @FXML
    void btngoToNotif(ActionEvent event) throws IOException {
        App.setRoot("halaman_Notif");
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
        App.setRoot("halaman_History");
    }

    @FXML
    void handlerbuttonLogout() throws IOException {
        Halaman_LoginController.iduser = 0;
        closeConnection();
        App.setRoot("halaman_Login");
    }

    @FXML
    void handlerbuttonProfile() throws IOException {
        App.setRoot("halaman_EditProfile");
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
}
