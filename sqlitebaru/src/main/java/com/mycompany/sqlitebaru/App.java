package com.mycompany.sqlitebaru;

import static com.mycompany.sqlitebaru.Halaman_LoginController.iduser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 * JavaFX App
 */
public class App extends Application {
    static int iduser;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        if (SessionManager.getInstance().isLoggedIn()) {
            if(iduser==1){
        scene = new Scene(loadFXML("halaman_MenuUtama_tabel_ADMIN"));
        stage.setScene(scene);
        stage.show();}
            else{
                scene = new Scene(loadFXML("halaman_MenuUtama_tabel"));
        stage.setScene(scene);
        stage.show();
            }
        }
        else{
        scene = new Scene(loadFXML("halaman_Login"));
        stage.setScene(scene);
        stage.show();
            
        }
        }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}