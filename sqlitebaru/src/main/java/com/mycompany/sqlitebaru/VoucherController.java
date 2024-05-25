package com.mycompany.sqlitebaru;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class VoucherController implements Initializable {
    
    private Connection conn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getConnection();// TODO
    } 
    
    @FXML
    private TextField InsertCompany;
    @FXML
    private TextField insertTittle;
    @FXML
    private TextField insertValue;
    @FXML
    private DatePicker insertValidDate;
    @FXML
    private DatePicker insertExpiredDate;
    @FXML
    private TextArea InsertDescription;
    @FXML
    private ImageView idImage;
    @FXML
    private Button idSave;
    @FXML
    private Button idDelete;

    @FXML
    public void BtnSaveClick() {
        String title = insertTittle.getText();
        int value = Integer.parseInt(insertValue.getText());
        LocalDate validDate = insertValidDate.getValue();
        LocalDate expiredDate = insertExpiredDate.getValue();
        String description = InsertDescription.getText();
        String company = "MyCompany"; // Replace with actual company value
        String type = "Discount"; // Replace with actual type value
        String image = "path/to/image.jpg"; // Replace with actual image path or value

        // Convert LocalDate to java.sql.Date
        java.sql.Date sqlValidDate = java.sql.Date.valueOf(validDate);
        java.sql.Date sqlExpiredDate = java.sql.Date.valueOf(expiredDate);

        // Insert the voucher into the database
        boolean isInserted = insertVoucher(new Voucher(0, 0, title, company, value, "", sqlValidDate.getTime(), sqlExpiredDate.getTime(), description, image, type));
        
        if (isInserted) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Voucher inserted successfully");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failure", "Failed to insert voucher");
        }
    }

    @FXML
    public void BtnDeleteClick() {
        // Implement delete functionality if needed
    }
    
    @FXML
    public void btnbackclick() throws IOException {
        App.setRoot("halaman_MenuUtama_tabel");
    }

    private boolean insertVoucher(Voucher voucher) {
        conn = getConnection();
        if (conn == null) {
            System.out.println("Connection is null in insertVoucher method.");
            return false;
        }

        String sql = "INSERT INTO voucher(title_voucher, company, value, valid_date, expired_date, description, image, type) VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, voucher.getTitleVoucher());
            pstmt.setString(2, voucher.getCompany());
            pstmt.setInt(3, voucher.getValue());
            pstmt.setLong(4, voucher.getValidDate());
            pstmt.setLong(5, voucher.getExpiredDate());
            pstmt.setString(6, voucher.getDescription());
            pstmt.setString(7, voucher.getImage());
            pstmt.setString(8, voucher.gettype());
            pstmt.executeUpdate();
            System.out.println("Voucher inserted successfully");
            return true;
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            return false;
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
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:data.db");
                conn.setAutoCommit(true); // Set auto-commit to true to avoid locking
                System.out.println("Connection established in getConnection method.");
            } catch (SQLException e) {
                System.out.println("Failed to establish connection: " + e.getMessage());
            }
        }
        return conn;
    }
    
//    public Connection getConnection() {
//        if (conn == null) {
//            try {
//                conn = DriverManager.getConnection("jdbc:sqlite:data.db");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return conn;
//    }
}
