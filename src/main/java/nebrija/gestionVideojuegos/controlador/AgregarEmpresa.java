package nebrija.gestionVideojuegos.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nebrija.gestionVideojuegos.App;
import nebrija.gestionVideojuegos.modelo.Compania;
import nebrija.gestionVideojuegos.modelo.DAOCompania;

import java.io.IOException;

public class AgregarEmpresa {
    @FXML
    private TextField nombreCompania;
    @FXML
    private TextField imagenCompania;
    @FXML
    private TextArea descripcionCompania;


    private boolean comprobarCampos(String dato) {
        boolean relleno = true;
        if (dato.equals("") || dato.isEmpty() || dato.isBlank() || dato == null) {
            relleno = false;
        }
        return relleno;
    }

    @FXML
    private void agregarNuevaCompania() throws IOException {
        try {
            if (comprobarCampos(nombreCompania.getText()) && comprobarCampos(imagenCompania.getText()) && comprobarCampos(descripcionCompania.getText())) {
                DAOCompania dao = new DAOCompania();
                Compania company = new Compania(nombreCompania.getText(), descripcionCompania.getText(), imagenCompania.getText());
                dao.addCompania(company, App.getTipoAlmacenamiento());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al guardar la compañía");
                alert.setContentText("No se ha podido guardar la nueva empresa, comprueba que todos los campos estén rellenos");
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
