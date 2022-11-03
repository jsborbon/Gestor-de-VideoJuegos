package nebrija.gestionVideojuegos.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nebrija.gestionVideojuegos.App;
import nebrija.gestionVideojuegos.modelo.DAOPlataforma;
import nebrija.gestionVideojuegos.modelo.Plataforma;

import java.io.IOException;

public class AgregarPlataforma {
    @FXML
    private TextField nombrePlataforma;
    @FXML
    private TextField imagenPlataforma;
    @FXML
    private TextArea descripcionPlataforma;
    @FXML
    private TextField desarrolladoraPlataforma;


    private boolean comprobarCampos(String dato) {
        boolean relleno = true;
        if (dato.equals("") || dato.isEmpty() || dato.isBlank() || dato == null) {
            relleno = false;
        }
        return relleno;
    }

    @FXML
    private void agregarNuevaPlataforma() throws IOException {
        try {
            if (comprobarCampos(nombrePlataforma.getText()) && comprobarCampos(imagenPlataforma.getText()) && comprobarCampos(descripcionPlataforma.getText()) && comprobarCampos(desarrolladoraPlataforma.getText())) {
                DAOPlataforma dao = new DAOPlataforma();
                Plataforma platform = new Plataforma(nombrePlataforma.getText(), descripcionPlataforma.getText(), imagenPlataforma.getText(), desarrolladoraPlataforma.getText());
                dao.addPlataforma(platform, App.getTipoAlmacenamiento());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al guardar la plataforma");
                alert.setContentText("No se ha podido guardar la nueva plataforma, comprueba que todos los campos estén rellenos");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            listarJuegos();
        }
    }


    @FXML
    private void listarJuegos() throws IOException {
        App.setRoot("listaJuegos");
    }

}
