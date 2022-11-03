package nebrija.gestionVideojuegos.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import nebrija.gestionVideojuegos.App;
import nebrija.gestionVideojuegos.modelo.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AgregarJuegos {

    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox cbCategoria;
    @FXML
    private ComboBox cbCompania;
    @FXML
    private GridPane gridPlataforma;
    @FXML
    private DatePicker dateFecha;
    @FXML
    private TextArea txtComentario;
    @FXML
    private TextArea txtRequisitos;
    @FXML
    private TextField txtImagen;

    public void initialize() {
        cbCompania.setPromptText("Selecciona una desarrolladora");
        cbCategoria.setPromptText("Selecciona una categoría");
        cbCompania.setItems(leerCompania());
        cbCategoria.setItems(leerCategoria());
        colocarPlataformas();
    }

    private void colocarPlataformas() {
        int columnIndex = 0;
        int rowIndex = 0;

        gridPlataforma.getChildren().clear();
        DAOPlataforma dao = new DAOPlataforma();
        ArrayList<Plataforma> listado = dao.readPlataforma(App.getTipoAlmacenamiento());
        ArrayList<String> nombrePlataforma = new ArrayList<>();
        try {
            if (listado.size() == 0) {
                PlataformaEnum[] plataforma = PlataformaEnum.values();
                for (PlataformaEnum platform : plataforma) {
                    nombrePlataforma.add(platform.toString());
                }
            } else {
                for (Plataforma plataforma : listado) {
                    nombrePlataforma.add(plataforma.getNombre());
                }
            }
        } catch (NullPointerException npe) {
            PlataformaEnum[] plataforma = PlataformaEnum.values();
            for (PlataformaEnum platform : plataforma) {
                nombrePlataforma.add(platform.toString());
            }
        }

        for (int i = 0; i < nombrePlataforma.size(); i++) {
            CheckBox checkBox = new CheckBox(nombrePlataforma.get(i));
            checkBox.setId(nombrePlataforma.get(i));
            gridPlataforma.add(checkBox, columnIndex, rowIndex);
            if (columnIndex == 2) {
                columnIndex = 0;
                rowIndex++;
            } else {
                columnIndex++;
            }
        }
    }

    private ObservableList<String> leerCategoria() {
        DAOCategoria dao = new DAOCategoria();
        ArrayList<Categoria> listado = dao.readCategoria(App.getTipoAlmacenamiento());
        ArrayList<String> nombreCategoria = new ArrayList<>();

        try {

            if (listado.size() == 0) {
                CategoriaEnum[] categorias = CategoriaEnum.values();
                for (CategoriaEnum category : categorias) {
                    nombreCategoria.add(category.toString());
                }
            } else {
                for (Categoria category : listado) {
                    nombreCategoria.add(category.getNombre());
                }
            }
        } catch (NullPointerException npe) {
            CategoriaEnum[] categorias = CategoriaEnum.values();
            for (CategoriaEnum category : categorias) {
                nombreCategoria.add(category.toString());
            }
        }
        ObservableList<String> listaCategoria = FXCollections.observableArrayList(nombreCategoria);

        return listaCategoria;
    }

    private ObservableList<String> leerCompania() {
        DAOCompania dao = new DAOCompania();
        ArrayList<Compania> listado = dao.readCompania(App.getTipoAlmacenamiento());
        ArrayList<String> nombreCompania = new ArrayList<>();
        try {

            if (listado.size() == 0) {
                CompaniaEnum[] companias = CompaniaEnum.values();
                for (CompaniaEnum companies : companias) {
                    nombreCompania.add(companies.toString());
                }
            } else {
                for (Compania company : listado) {
                    nombreCompania.add(company.getNombre());
                }
            }
        } catch (NullPointerException npe) {
            CompaniaEnum[] companias = CompaniaEnum.values();
            for (CompaniaEnum companies : companias) {
                nombreCompania.add(companies.toString());
            }
        }
        ObservableList<String> listaCompania = FXCollections.observableArrayList(nombreCompania);

        return listaCompania;
    }

    private boolean comprobarCampos(String dato) {
        boolean relleno = true;
        if (dato.equals("") || dato.isEmpty() || dato.isBlank() || dato == null) {
            relleno = false;
        }
        return relleno;
    }

    @FXML
    private void agregarNuevoJuego() throws IOException {
        try {
            ArrayList<String> plataformas = new ArrayList<>();
            for (int i = 0; i < gridPlataforma.getChildren().size(); i++) {
                CheckBox checkBox = (CheckBox) gridPlataforma.getChildren().get(i);
                if (checkBox.isSelected()) {
                    plataformas.add(checkBox.getId());
                }
            }
            String[] requisitos = txtRequisitos.getText().replaceAll("[ \\t\\n\\x0B\\f\\r]", " ").split(",");
            if (comprobarCampos(txtNombre.getText()) && comprobarCampos(cbCategoria.getValue().toString()) && comprobarCampos(cbCompania.getValue().toString()) && comprobarCampos(plataformas.toString()) && comprobarCampos(dateFecha.getValue().toString()) && comprobarCampos((new ArrayList(Arrays.asList(requisitos))).toString()) && comprobarCampos(txtComentario.getText()) && comprobarCampos(txtImagen.getText())) {
                DAOVideojuegos dao = new DAOVideojuegos();
                Juego juego = new Juego(txtNombre.getText(), cbCategoria.getValue().toString(), cbCompania.getValue().toString(), plataformas, dateFecha.getValue(), (new ArrayList(Arrays.asList(requisitos))), txtComentario.getText(), txtImagen.getText());
                dao.addVideojuego(juego, App.getTipoAlmacenamiento());
            } else {
                mostrarError();
            }
        } catch (NullPointerException npe) {
            mostrarError();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            listarJuegos();
        }
    }

    private void mostrarError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al guardar el juego");
        alert.setContentText("No se ha podido guardar el juego, comprueba que todos los campos estén rellenos");
        alert.showAndWait();
    }

    @FXML
    private void listarJuegos() throws IOException {
        App.setRoot("listaJuegos");
    }

}