package nebrija.gestionVideojuegos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static int tipoAlmacenamiento = 0; //Binario

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Gestión Videojuegos");

        // Set the application icon.
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/icon.png")));

        scene = new Scene(loadFXML("listaJuegos"), 800, 650);
        stage.setScene(scene);
        stage.show();

    }

    public static void setRoot(String fxml) throws IOException {
        Window window = scene.getWindow();

        if(fxml.equals("listaJuegos")){
            window.setHeight(650);
            window.setWidth(800);
        }else{
            window.setHeight(600);
            window.setWidth(520);
        }

        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static int getTipoAlmacenamiento() {
        return tipoAlmacenamiento;
    }

    public static void setTipoAlmacenamiento(int tipoAlmacenamiento) {
        App.tipoAlmacenamiento = tipoAlmacenamiento;
    }


}