module com.mycompany.sqlitebaru {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires java.sql; //ini perlu d tambahin
    opens com.mycompany.sqlitebaru to javafx.fxml;
    exports com.mycompany.sqlitebaru;
}
