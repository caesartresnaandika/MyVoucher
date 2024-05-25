package com.mycompany.sqlitebaru;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JavaFX App
 */
public class App extends Application {
   

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("halaman_Login"));
        stage.setScene(scene);
        stage.show();
        }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        
        launch();
//       try {
            // Koneksi ke database SQLite
//            Connection conn = DriverManager.getConnection("jdbc:sqlite:data.db");

            // Buat statement SQL untuk mengambil semua data
//            Statement stmt = conn.createStatement();
//            String sql = "SELECT * FROM user";
//            ResultSet rs = stmt.executeQuery(sql);
//
//            // Tampilkan data
//            while (rs.next()) {
//                String id = rs.getString("id_user");
//                String namadpn = rs.getString("nama_depan");
//                String namablkg = rs.getString("nama_belakang");
//                String email = rs.getString("email");
//                String password = rs.getString("password");
//                String foto = rs.getString("foto");
//
//                System.out.println("ID User: " + id + ", Nama: " + namadpn+" "+namablkg + ", Email: " + email + ", password: " + password + ", foto: " + foto);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    }

