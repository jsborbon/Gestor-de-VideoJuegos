package nebrija.gestionVideojuegos.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nebrija.gestionVideojuegos.App;
import nebrija.gestionVideojuegos.modelo.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class EliminarInterfaz {

    @FXML
    private TableView tblEliminar;
    @FXML
    private Button btnConfirmar;

    private static String elementoSeleccionado = "Juego";

    private TableColumn<Juego, String> columnas[]; //Array de columnas

    private int celdaImagen = -1;

    public void initialize() {
        ArrayList<?> listado = leerDatos();
        crearTabla(listado);
        cargarDatosEnTabla(listado);
    }

    private void crearTabla(ArrayList<?> listado) {
        tblEliminar.setEditable(true);
        try {
            if (listado.size() > 0) {
                Field[] nombresColumnas = listado.get(0).getClass().getDeclaredFields(); //Obtenemos los nombres de las columnas
                columnas = new TableColumn[nombresColumnas.length]; //Creamos el array de columnas

                //COLOCAR EN TABLA
                for (int i = 0; i < columnas.length; i++) { //Creamos las columnas
                    if (i != 0) {
                        columnas[i] = new TableColumn<>(darFormatoNombreEncabezados(nombresColumnas[i].getName()));
                        columnas[i].setCellValueFactory(new PropertyValueFactory<>(darFormatoNombreEncabezados(nombresColumnas[i].getName()))); //Asignamos el valor de la columna;
                        if (nombresColumnas[i].getName().equals("imagen")) {
                            celdaImagen = i;
                        }
                        tblEliminar.getColumns().add(columnas[i]);
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Lista vacia");;
        }
    }

    private void colocarImagenes() {

        if (celdaImagen > 0) {
            columnas[celdaImagen].setCellFactory(column -> new TableCell<>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                    } else {
                        try {
                            imageView.setImage(new Image(item));
                            imageView.setFitHeight(150);
                            imageView.setFitWidth(150);
                            setGraphic(imageView);
                        } catch (IllegalArgumentException iae) {
                            setGraphic(null);
                        }
                    }
                }
            });
        }
    }

    private String darFormatoNombreEncabezados(String texto) {
        String nombreColumna = texto.substring(0, 1).toUpperCase() + texto.substring(1);
        return nombreColumna;
    }

    private ArrayList leerDatos() {
        ArrayList<?> listado = null;
        switch (elementoSeleccionado) {
            case "Juego": {
                DAOVideojuegos dao = new DAOVideojuegos();
                listado = dao.readVideojuego(App.getTipoAlmacenamiento());
                break;
            }
            case "Desarrolladora": {
                DAOCompania dao = new DAOCompania();
                listado = dao.readCompania(App.getTipoAlmacenamiento());
                break;
            }
            case "Categoria": {
                DAOCategoria dao = new DAOCategoria();
                listado = dao.readCategoria(App.getTipoAlmacenamiento());
                break;
            }
            case "Plataforma": {
                DAOPlataforma dao = new DAOPlataforma();
                listado = dao.readPlataforma(App.getTipoAlmacenamiento());
                break;
            }
        }
        return listado;
    }

    private void cargarDatosEnTabla(ArrayList<?> listado) {
        try {
            if (listado != null && listado.size() > 0) {
                ObservableList<?> lista = FXCollections.observableArrayList(listado);

                colocarImagenes();

                tblEliminar.setItems(lista);
                btnConfirmar.setDisable(false);
            } else {
                btnConfirmar.setDisable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retirarElemento() throws IOException {
        Object elemento = tblEliminar.getSelectionModel().getSelectedItem();
        if (elemento != null) {
            switch (elementoSeleccionado) {
                case "Juego": {
                    eliminarJuegos(elemento);
                    break;
                }
                case "Desarrolladora": {
                    eliminarCompania(elemento);
                    break;
                }
                case "Categoria": {
                    eliminarCategoria(elemento);
                    break;
                }
                case "Plataforma": {
                    eliminarPlataforma(elemento);
                    break;
                }
            }
            listarJuegos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selecciona un elemento");
            alert.showAndWait();
        }
    }

    private void eliminarJuegos(Object elemento) throws IOException {
        DAOVideojuegos dao = new DAOVideojuegos();
        Juego game = (Juego) elemento;
        dao.eliminarVideojuego(game, App.getTipoAlmacenamiento());
    }

    private void eliminarCompania(Object elemento) throws IOException {
        DAOCompania dao = new DAOCompania();
        Compania company = (Compania) elemento;
        dao.eliminarCompania(company, App.getTipoAlmacenamiento());
    }

    private void eliminarPlataforma(Object elemento) throws IOException {
        DAOPlataforma dao = new DAOPlataforma();
        Plataforma platform = (Plataforma) elemento;
        dao.eliminarPlataforma(platform, App.getTipoAlmacenamiento());
    }

    private void eliminarCategoria(Object elemento) throws IOException {
        DAOCategoria dao = new DAOCategoria();
        Categoria category = (Categoria) elemento;
        dao.eliminarCategoria(category, App.getTipoAlmacenamiento());
    }


    @FXML
    private void eliminarElemento() throws IOException {
        retirarElemento();
    }

    @FXML
    private void listarJuegos() throws IOException {
        App.setRoot("listaJuegos");
    }

    public static String getElementoSeleccionado() {
        return elementoSeleccionado;
    }

    public static void setElementoSeleccionado(String elementoSeleccionado) {
        EliminarInterfaz.elementoSeleccionado = elementoSeleccionado;
    }
}
