package com.mycompany.sqlitebaru;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class VoucherController implements Initializable {

    private static final int MAX_RETRIES = 5;
    private static final int RETRY_DELAY_MS = 1000;
    private Connection connection;

    private int userId; // Add a variable to store the user ID
    private String selectedType; // Add a variable to store the selected type

    @FXML
    private TextField InsertCompany;

    @FXML
    private TextArea InsertDescription;

    @FXML
    private MenuButton InsertType;

    @FXML
    private MenuItem handleTypeSelectionCashBack;

    @FXML
    private MenuItem handleTypeSelectionDiscount;

    @FXML
    private MenuItem handleTypeSelectionPromo;

    @FXML
    private Button idDelete;

    @FXML
    private ImageView idImage;

    @FXML
    private Button idSave;

    @FXML
    private Button idback;

    @FXML
    private MenuItem goToNotif;

    @FXML
    private DatePicker insertExpiredDate;

    @FXML
    private TextField insertTittle;

    @FXML
    private DatePicker insertValidDate;

    @FXML
    private TextField insertValue;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getConnection(); // Initialize the database connection
        createTable();   // Create the database table if it doesn't exist

        // Set up MenuItems' event handlers
        handleTypeSelectionCashBack.setOnAction(event -> handleTypeSelection(event, "Cashback"));
        handleTypeSelectionDiscount.setOnAction(event -> handleTypeSelection(event, "Discount"));
        handleTypeSelectionPromo.setOnAction(event -> handleTypeSelection(event, "Promo"));
    }

    // Method to set the user ID
    public void setUserId(int userId) {
        this.userId = userId;
    }

    private void handleTypeSelection(ActionEvent event, String type) {
        selectedType = type;
        InsertType.setText(type); // Update the MenuButton's text with the selected type
    }

    @FXML
    public void BtnSaveClick() {
        String title = insertTittle.getText();
        String company = InsertCompany.getText();
        String detail = insertValue.getText();
        LocalDate validDate = insertValidDate.getValue();
        LocalDate expiredDate = insertExpiredDate.getValue();
        String description = InsertDescription.getText();

        if (validDate == null || expiredDate == null) {
            showAlert(Alert.AlertType.ERROR, "Invalid Date", "Please select valid and expired dates.");
            return;
        }

        // Convert LocalDate to java.sql.Date
        java.sql.Date sqlValidDate = java.sql.Date.valueOf(validDate);
        java.sql.Date sqlExpiredDate = java.sql.Date.valueOf(expiredDate);
        
        

        // Insert the voucher into the database

        boolean isInserted = insertVoucher(new Voucher(0, Halaman_LoginController.iduser, title, company, selectedType, detail, sqlValidDate.getTime(), sqlExpiredDate.getTime(), description));


        if (isInserted) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Voucher inserted successfully");
            clearFields(); // Clear the input fields after successful insertion
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
        if (connection == null) {
            System.out.println("Connection is null in insertVoucher method.");
            return false;
        }


        String query = "INSERT INTO voucher (id_user, title_voucher, company, type, detail_voucher, valid_date, expired_date, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int attempt = 0;

        while (attempt < MAX_RETRIES) {
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, voucher.getId_user());
                pstmt.setString(2, voucher.getTitle_voucher());
                pstmt.setString(3, voucher.getCompany());
                pstmt.setString(4, voucher.getType());
                pstmt.setString(5, voucher.getDetail_voucher());
                pstmt.setLong(6, voucher.getValid_date());
                pstmt.setLong(7, voucher.getExpired_date());
                pstmt.setString(8, voucher.getDescription());
                pstmt.executeUpdate();
                System.out.println("Voucher inserted successfully");
                return true;
            } catch (SQLException e) {
                if (e.getMessage().contains("database is locked")) {
                    attempt++;
                    System.out.println("Database is locked, retrying... (" + attempt + ")");
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Retry interrupted", ie);
                    }
                } else {
                    System.out.println("SQL Exception: " + e.getMessage());
                    return false;
                }
            }
        }
        System.out.println("Failed to insert voucher after several retries.");
        return false;
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

    private void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS voucher ("
                + "id_voucher INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "id_user INTEGER, "
                + "title_voucher TEXT NOT NULL, "
                + "company TEXT NOT NULL, "
                + "type TEXT NOT NULL, "
                + "detail_voucher TEXT, "
                + "valid_date INTEGER NOT NULL, "
                + "expired_date INTEGER NOT NULL, "
                + "description TEXT NOT NULL)";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Failed to create table: " + e.getMessage());
        }
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
    
    private void clearFields() {
        insertTittle.clear();
        InsertCompany.clear();
        insertValidDate.setValue(null);
        insertExpiredDate.setValue(null);
        InsertDescription.clear();
        insertValue.clear();
        InsertType.setText("Select Type");
        selectedType = null; // Reset the selected type
    }
    
    @FXML
    void btngoToNotif(ActionEvent event) throws IOException{
        if (Halaman_LoginController.iduser == 1) {
                App.setRoot("halaman_notif_ADMIN");
            } else {
                App.setRoot("halaman_Notif");
            }
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
        if (Halaman_LoginController.iduser == 1) {
                App.setRoot("halaman_notif_ADMIN");
            } else {
            App.setRoot("halaman_History");}
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
}
