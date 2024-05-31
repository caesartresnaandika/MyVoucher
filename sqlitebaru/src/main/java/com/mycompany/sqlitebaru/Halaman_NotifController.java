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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Halaman_NotifController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private MenuItem buttonHistory;

    @FXML
    private MenuItem buttonLogout;

    @FXML
    private MenuItem buttonProfile;

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
    private TableColumn<Voucher, Long> idColDayLeft;

    @FXML
    private TableColumn<Voucher, Integer> idColNo;

    @FXML
    private TableColumn<Voucher, String> idColTittle;

    @FXML
    private TableColumn<Voucher, Long> idColValidDate;
    
    @FXML
    private TableColumn<Voucher, Long> idColExpiredDate;

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
    private TextField searchBar;
    private ObservableList<Voucher> voucherObservableList;
    private Connection connection;

    Voucher voucher;

    @FXML
    void btngoToNotif(ActionEvent event) throws IOException{
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
    void handlerbuttonLogout() throws IOException{
        Halaman_LoginController.iduser=0;
        closeConnection();
        App.setRoot("halaman_Login");
    }

    @FXML
    void handlerbuttonProfile()throws IOException {
        App.setRoot("halaman_EditProfile");
    }

    @FXML
    void onBtnBackClick(ActionEvent event) throws IOException {
        if (Halaman_LoginController.iduser == 1) {
            App.setRoot("halaman_MenuUtama_tabel_ADMIN");
        } else {
            App.setRoot("halaman_MenuUtama_tabel");
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = getConnection();
        voucherObservableList = FXCollections.observableArrayList();
        idTable.setItems(voucherObservableList);

        idColNo.setCellValueFactory(new PropertyValueFactory<>("id_voucher"));
        idColTittle.setCellValueFactory(new PropertyValueFactory<>("title_voucher"));
        idColValidDate.setCellValueFactory(new PropertyValueFactory<>("valid_date"));
        idColExpiredDate.setCellValueFactory(new PropertyValueFactory<>("expired_date"));
        idColDayLeft.setCellValueFactory(new PropertyValueFactory<>("expired_date"));

        setColumnDateCellFactory();
        getAllData();

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filterData(newValue));
    }
    
    private void setColumnDateCellFactory() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        idColValidDate.setCellFactory(column -> new TableCell<Voucher, Long>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(item), ZoneId.systemDefault());
                    setText(dateTime.format(formatter));
                }
            }
        });

        idColExpiredDate.setCellFactory(column -> new TableCell<Voucher, Long>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(item), ZoneId.systemDefault());
                    setText(dateTime.format(formatter));
                }
            }
        });

        idColDayLeft.setCellFactory(column -> new TableCell<Voucher, Long>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    long currentTime = System.currentTimeMillis();
                    long daysLeft = (item - currentTime) / (1000 * 60 * 60 * 24); // Convert milliseconds to days
                    if (daysLeft <= 0) {
                        setText("Expired");
                    } else {
                        setText(daysLeft + " hari");
                    }
                }
            }
        });
    }
    
    private void getAllData() {
        String query = "SELECT voucher.id_voucher, voucher.title_voucher, voucher.company, voucher.type, voucher.detail_voucher, " +
                       "voucher.valid_date, voucher.expired_date, voucher.description, user.id_user, user.nama_depan " +
                       "FROM voucher " +
                       "JOIN user ON voucher.id_user = user.id_user";
        voucherObservableList.clear();
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
                voucherObservableList.add(voucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Connection Error", e.getMessage());
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
        ObservableList<Voucher> filteredList = voucherObservableList.stream()
                .filter(voucher ->
                        String.valueOf(voucher.getId_voucher()).contains(query) ||
                                voucher.getTitle_voucher().toLowerCase().contains(query.toLowerCase()) ||
                                voucher.getNama_depan().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        idTable.setItems(filteredList);
    }

    private String convertLongToDate(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTime.format(formatter);
    }
}
