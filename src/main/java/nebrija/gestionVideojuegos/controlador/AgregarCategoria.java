package nebrija.gestionVideojuegos.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nebrija.gestionVideojuegos.App;
import nebrija.gestionVideojuegos.modelo.Categoria;
import nebrija.gestionVideojuegos.modelo.DAOCategoria;

import java.io.IOException;

public class AgregarCategoria {
    @FXML
    private TextField nombreCategoria;
    @FXML
    private TextField imagenCategoria;
    @FXML
    private TextArea descripcionCategoria;

    private boolean comprobarCampos(String dato) {
        boolean relleno = true;
        if (dato.equals("") || dato.isEmpty() || dato.isBlank() || dato == null) {
            relleno = false;
        }
        return relleno;
    }

    @FXML
    private void agregarNuevaCategoria() throws IOException {
        try {
            if (comprobarCampos(nombreCategoria.getText()) && comprobarCampos(imagenCategoria.getText()) && comprobarCampos(descripcionCategoria.getText())) {
                DAOCategoria dao = new DAOCategoria();
                Categoria categoria = new Categoria(nombreCategoria.getText(), imagenCategoria.getText(), descripcionCategoria.getText());
                dao.addCategoria(categoria, App.getTipoAlmacenamiento());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al guardar la categoría");
                alert.setContentText("No se ha podido guardar la nueva categoría, comprueba que todos los campos estén rellenos");
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
