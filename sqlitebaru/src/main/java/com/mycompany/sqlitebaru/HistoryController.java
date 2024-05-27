// Controller for History
package com.mycompany.sqlitebaru;

import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HistoryController implements Initializable {

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
    private Label ValidDateView;
    @FXML
    private Label ValueView;
    @FXML
    private Label dateUsedView;
    @FXML
    private TableColumn<History, Integer> NoHistory;
    @FXML
    private TableColumn<History, String> TitleHistory;
    @FXML
    private TableColumn<History, Long> ColumnDate;
    @FXML
    private TableView<History> TableHistory;
    @FXML
    private TextField searchBar;

    private ObservableList<History> historyObservableList;
    private Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getConnection();
        historyObservableList = FXCollections.observableArrayList();
        TableHistory.setItems(historyObservableList);
        NoHistory.setCellValueFactory(new PropertyValueFactory<>("id_voucher"));
        TitleHistory.setCellValueFactory(new PropertyValueFactory<>("title_voucher"));
        ColumnDate.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getUse_date()).asObject());
        getAllHistoryData();

        TableHistory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateVoucherDetails(newValue);
            }
        });

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filterData(newValue));
    }

    private void getAllHistoryData() {
        String query = "SELECT * FROM history WHERE id_user=?";
        historyObservableList.clear();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Halaman_LoginController.iduser);  // Ensure iduser is set correctly
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                History history = new History(
                        resultSet.getInt("id_voucher"),
                        resultSet.getInt("id_user"),
                        resultSet.getString("title_voucher"),
                        resultSet.getString("company"),
                        resultSet.getString("type"),
                        resultSet.getString("detail_voucher"),
                        resultSet.getLong("valid_date"),
                        resultSet.getLong("expired_date"),
                        resultSet.getString("description"),
                        resultSet.getLong("use_date")
                );
                historyObservableList.add(history);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void filterData(String query) {
        ObservableList<History> filteredList = historyObservableList.stream()
                .filter(history ->
                        String.valueOf(history.getId_voucher()).contains(query) ||
                                history.getTitle_voucher().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        TableHistory.setItems(filteredList);
    }

    private String convertLongToDate(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTime.format(formatter);
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
        dateUsedView.setText(convertLongToDate(history.getUse_date()));
    }
}
