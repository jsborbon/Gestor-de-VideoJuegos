module nebrija.gestionVideojuegos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mongo.java.driver;

    opens nebrija.gestionVideojuegos to javafx.fxml;
    exports nebrija.gestionVideojuegos;
    exports nebrija.gestionVideojuegos.controlador;
    opens nebrija.gestionVideojuegos.controlador to javafx.fxml;
    exports nebrija.gestionVideojuegos.modelo;
    opens nebrija.gestionVideojuegos.modelo to javafx.fxml;
}
