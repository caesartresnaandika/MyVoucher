package com.mycompany.sqlitebaru;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date; // Import class Date
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditVoucherController implements Initializable {
    private Connection connection;

    @FXML 
    private TextField inputTitle;
    @FXML
    private TextArea inputDescription;
    @FXML
    private DatePicker inputValidDate;
    @FXML
    private DatePicker inputExpiredDate;
    @FXML
    private MenuButton inputType;
    @FXML 
    private TextField inputDiscount;

    private Voucher voucher;

    @FXML 
    public void btnback() throws IOException {
        App.setRoot("halaman_MenuUtama_tabel");
    }
    @FXML 
public void btnDelete() {
    if (voucher != null) {
        int id = voucher.getId_voucher();
        String query = "DELETE FROM voucher WHERE id_voucher = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Voucher deleted successfully");
                App.setRoot("halaman_MenuUtama_tabel");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete voucher");
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the voucher");
        }
    } else {
        showAlert(Alert.AlertType.WARNING, "Warning", "No voucher selected");
    }
}

    @FXML 
    public void btnSave() {
        String title = inputTitle.getText();
        String description = inputDescription.getText();
        LocalDate validDate = inputValidDate.getValue();
        LocalDate expiredDate = inputExpiredDate.getValue();
        String type = inputType.getText();
        String discount = inputDiscount.getText();

        if (title.isEmpty() || description.isEmpty() || validDate == null || expiredDate == null || type.isEmpty() || discount.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter all fields");
            return;
        }

        updateVoucherInDatabase(voucher.getId_voucher(), title, description, validDate, expiredDate, type, discount);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        voucher = Halaman_MenuUtamaController.selectedVoucher;
        if (voucher != null) {
            inputTitle.setText(voucher.getTitle_voucher());
            inputDescription.setText(voucher.getDescription());
            inputDiscount.setText(String.valueOf(voucher.getDetail_voucher()));
            inputType.setText(voucher.getType());
            inputValidDate.setValue(LocalDate.ofEpochDay(voucher.getValid_date()/ (24 * 60 * 60 * 1000)));
            inputExpiredDate.setValue(LocalDate.ofEpochDay(voucher.getExpired_date()/ (24 * 60 * 60 * 1000)));
        }
    }

    private void updateVoucherInDatabase(int id, String title, String description, LocalDate validDate, LocalDate expiredDate, String type, String discount) {
        String query = "UPDATE voucher SET title_voucher = ?, description = ?, valid_date = ?, expired_date = ?, type = ?, value = ? WHERE id_voucher = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setDate(3, Date.valueOf(validDate)); // Menggunakan Date.valueOf() untuk LocalDate
            preparedStatement.setDate(4, Date.valueOf(expiredDate)); // Menggunakan Date.valueOf() untuk LocalDate
            preparedStatement.setString(5, type);
            preparedStatement.setString(6, discount);
            preparedStatement.setInt(7, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Voucher updated successfully");
                App.setRoot("halaman_MenuUtama_tabel");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update voucher");
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating the voucher");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:data.db");
                System.out.println("Connection established in getConnection method.");
            } catch (SQLException e) {
                System.out.println("Failed to establish connection: " + e.getMessage());
            }
        }
        return connection;
    }
}
