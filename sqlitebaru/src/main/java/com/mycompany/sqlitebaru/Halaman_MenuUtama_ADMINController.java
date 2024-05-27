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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Halaman_MenuUtama_ADMINController implements Initializable {

    @FXML
    private Label CompanyView, DescriptionView, DetailView, ExpiredView, TittleView, TypeView, UserView, ValidDateView, ValueView;

    @FXML
    private MenuButton dropFilter, dropLanguage, dropNotif, dropProfil;

    @FXML
    private MenuItem goToNotif;

    @FXML
    private Text idAppName;

    @FXML
    private ImageView idBell, idFlag, idLogo, idProfil, idlogoFilter;

    @FXML
    private Button idBtnDellete, idBttnEdit;

    @FXML
    private TableColumn<Voucher, String> idColNo, idColTittle, idColUser;

    @FXML
    private TableView<Voucher> idTable;

    @FXML
    private TextField searchBar;

    @FXML
    private Hyperlink idHLAboutUs, idHelpC, idPP2, idToS2;

    private ObservableList<Voucher> dataObservableList;
    private Connection connection;
    private static Voucher selectedVoucher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getConnection();
        dataObservableList = FXCollections.observableArrayList();
        idTable.setItems(dataObservableList);
        idColNo.setCellValueFactory(new PropertyValueFactory<>("id_voucher"));
        idColTittle.setCellValueFactory(new PropertyValueFactory<>("title_voucher"));
        idColUser.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        getAllData();

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
    void btngoToNotif(ActionEvent event) {
        // Implement notification logic
    }

    @FXML
    void onBtnEditClick(ActionEvent event) throws IOException {
        App.setRoot("halaman_EditVoucher");
    }

    @FXML
    void onBtnUseClick(ActionEvent event) {
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

    @FXML
    void onHLAboutUsClick(ActionEvent event) throws IOException {
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

                Voucher voucher = new Voucher(id_voucher, id_user, title_voucher, company, type, detail_voucher, valid_date, expired_date, description);
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
            .filter(voucher -> String.valueOf(voucher.getId_voucher()).contains(query) || 
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
        DetailView.setText(voucher.getDetail_voucher());
        ValidDateView.setText(convertLongToDate(voucher.getValid_date()));
        ExpiredView.setText(convertLongToDate(voucher.getExpired_date()));
        TypeView.setText(voucher.getType());
        ValueView.setText(String.valueOf(voucher.getDetail_voucher()));
    }
}
