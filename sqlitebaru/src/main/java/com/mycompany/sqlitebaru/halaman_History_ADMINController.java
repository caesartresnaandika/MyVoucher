package com.mycompany.sqlitebaru;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class halaman_History_ADMINController implements Initializable{

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
    private Label dateUsedview;

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
    private TableColumn<History, Long> idColDateUsed;

    @FXML
    private TableColumn<History, Integer> idColNo;

    @FXML
    private TableColumn<History, String> idColTittle;

    @FXML
    private TableColumn<History, String> idColUser;

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
    private TableView<History> idTable;

    @FXML
    private Hyperlink idToS2;

    @FXML
    private TextField searchBar;
    
    private ObservableList<History> historyObservableList;
    private Connection connection;

    History history;

    @FXML
    void btngoToNotif(ActionEvent event) {

    }

    @FXML
    void onHLAboutUsClick(ActionEvent event) {

    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getConnection();
        historyObservableList = FXCollections.observableArrayList();
        idTable.setItems(historyObservableList);
        idColNo.setCellValueFactory(new PropertyValueFactory<>("id_voucher"));
        idColTittle.setCellValueFactory(new PropertyValueFactory<>("title_voucher"));
        idColDateUsed.setCellValueFactory(new PropertyValueFactory<>("use_date"));
        idColUser.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        setColumnDateCellFactory();  // Set custom cell factory for date column
        getAllHistoryData();

        idTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateVoucherDetails(newValue);
            }
        });

//        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filterData(newValue));
    }
    
    private void updateVoucherDetails(History history) {
        TittleView.setText(history.getTitle_voucher());
        DescriptionView.setText(history.getDescription());
        CompanyView.setText(history.getCompany());
        DetailView.setText(history.getDetail_voucher());
        ValidDateView.setText(convertLongToDate(history.getValid_date()));
        ExpiredView.setText(convertLongToDate(history.getExpired_date()));
        TypeView.setText(history.getType());
        ValueView.setText(String.valueOf(history.getDetail_voucher()));
        dateUsedview.setText(convertLongToDate(history.getUse_date()));
    }
    
    private void getAllHistoryData() {
        String query = "SELECT * FROM history";
        historyObservableList.clear();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setInt(1, Halaman_LoginController.iduser);  // Ensure iduser is set correctly
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
                long use_date = resultSet.getLong("use_date");
                convertLongToDate(use_date);
                History history = new History(id_user, id_voucher, title_voucher, company, type, detail_voucher, valid_date, expired_date, description, use_date);
                historyObservableList.add(history);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }
    
    private void setColumnDateCellFactory() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        idColDateUsed.setCellFactory(column -> new TableCell<History, Long>() {
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

//    private void filterData(String query) {
//        ObservableList<History> filteredList = historyObservableList.stream()
//                .filter(history ->
//                        String.valueOf(history.getId_voucher()).contains(query) ||
//                                history.getTitle_voucher().toLowerCase().contains(query.toLowerCase()))
//                .collect(Collectors.toCollection(FXCollections::observableArrayList));
//        TableHistory.setItems(filteredList);
//    }

    private String convertLongToDate(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTime.format(formatter);
    }

}
