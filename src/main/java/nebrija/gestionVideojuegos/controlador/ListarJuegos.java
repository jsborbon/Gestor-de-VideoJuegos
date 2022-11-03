package nebrija.gestionVideojuegos.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nebrija.gestionVideojuegos.App;
import nebrija.gestionVideojuegos.modelo.DAOVideojuegos;
import nebrija.gestionVideojuegos.modelo.Juego;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ListarJuegos {

    @FXML
    private TextField txtNombreEnTabla;
    @FXML
    private TableView<Juego> tblListaJuegos;
    @FXML
    private Menu almacenamiento;


    private TableColumn<Juego, String> imagenCol;
    private TableColumn<Juego, String> nombreCol;
    private TableColumn<Juego, String> categoriaCol;
    private TableColumn<Juego, String> companiaCol;
    private TableColumn<Juego, String> plataformaCol;
    private TableColumn<Juego, LocalDate> fechaCol;
    private TableColumn<Juego, String> requisitosCol;
    private TableColumn<Juego, String> comentarioCol;

    private DAOVideojuegos dao;
    private String[] opciones = {"Binario", "Fichero", "BD SQL", "BD NoSQL"};

    public void initialize() {
        crearTabla();
        leerDatos();
        almacenamiento.getItems().get(App.getTipoAlmacenamiento()).setText(opciones[App.getTipoAlmacenamiento()] + " ✓");
    }

    private void leerDatos() {
        dao = new DAOVideojuegos();
        ArrayList<Juego> listado = dao.readVideojuego(App.getTipoAlmacenamiento());
        cargarDatosEnTabla(listado);
    }

    private void crearTabla() {
        tblListaJuegos.setEditable(true);
        tblListaJuegos.setFixedCellSize(150);

        //COLOCAR EN TABLA
        imagenCol = new TableColumn<>("Imagen");
        nombreCol = new TableColumn<>("Nombre");
        categoriaCol = new TableColumn<>("Categoria");
        companiaCol = new TableColumn<>("Compañia");
        plataformaCol = new TableColumn<>("Plataforma");
        fechaCol = new TableColumn<>("Fecha");
        requisitosCol = new TableColumn<>("Requisitos");
        comentarioCol = new TableColumn<>("Comentarios");

        //ASIGNAR VALORES
        imagenCol.setCellValueFactory(new PropertyValueFactory<>("imagen"));
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        companiaCol.setCellValueFactory(new PropertyValueFactory<>("compania"));
        plataformaCol.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        fechaCol.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        requisitosCol.setCellValueFactory(new PropertyValueFactory<>("requisitos"));
        comentarioCol.setCellValueFactory(new PropertyValueFactory<>("comentario"));
        // ANCHO firstColumn.setPrefWidth(60);


        //Editar
        nombreCol.setCellFactory(TextFieldTableCell.forTableColumn());
        categoriaCol.setCellFactory(TextFieldTableCell.forTableColumn());
        companiaCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //plataformaCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //fechaCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //requisitosCol.setCellFactory(colocarRequisitos(TextFieldListCell.forListView()));
        comentarioCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nombreCol.setOnEditCommit(modificarInfo());
        categoriaCol.setOnEditCommit(modificarInfo());
        companiaCol.setOnEditCommit(modificarInfo());
        //plataformaCol.setOnEditCommit(modificarInfo());
        //fechaCol.setOnEditCommit(modificarInfo());
        //requisitosCol.setOnEditCommit(modificarInfo());
        comentarioCol.setOnEditCommit(modificarInfo());

        tblListaJuegos.getColumns().addAll(imagenCol, nombreCol, categoriaCol, companiaCol, plataformaCol, fechaCol, requisitosCol, comentarioCol);


    }

    private EventHandler<TableColumn.CellEditEvent<Juego, String>> modificarInfo() {
        return new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Juego, String> event) {

                Juego game = event.getRowValue();
                if (event.getTableColumn().getText().equals("Nombre")) {
                    game.setNombre(event.getNewValue());
                } else if (event.getTableColumn().getText().equals("Categoria")) {
                    game.setCategoria(event.getNewValue());
                } else if (event.getTableColumn().getText().equals("Compañia")) {
                    game.setCompania(event.getNewValue());
                } else if (event.getTableColumn().getText().equals("Comentarios")) {
                    game.setComentario(event.getNewValue());
                }
                dao.updateVideojuego(game, App.getTipoAlmacenamiento());
                leerDatos();
            }
        };
    }

    private void cargarDatosEnTabla(ArrayList<Juego> listado) {
        try {
            if (listado != null) {
                tblListaJuegos.getItems().clear();
                ObservableList<Juego> listaVideoJuegos = FXCollections.observableArrayList(listado);
                colocarImagen();
                colocarLista(listado);
                tblListaJuegos.setItems(listaVideoJuegos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buscar() {
        dao = new DAOVideojuegos();
        String nombre = txtNombreEnTabla.getText();

        if (nombre.equals("")) {
            leerDatos();
        } else {
            cargarDatosEnTabla(dao.buscarVideojuego(App.getTipoAlmacenamiento(), nombre));
        }
    }

    private void colocarImagen() {
        imagenCol.setCellFactory(columna -> new TableCell<>() {
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

    private void colocarLista(ArrayList<Juego> lista) {
        colocarRequisitos(lista);
        colocarPlataformas(lista);
    }

    private void colocarRequisitos(ArrayList<Juego> lista) {


        requisitosCol.setCellFactory(requisito -> new TableCell() {
            private final ListView<String> listView = new ListView<>();

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                try {

                    if (empty) {
                        setGraphic(null);
                    } else {
                        if (lista.size() > 0) {
                            listView.setItems(FXCollections.observableArrayList(lista.get(getIndex()).getRequisitos()));
                            listView.setCellFactory(TextFieldListCell.forListView());
                            listView.setEditable(true);
                            listView.setOnEditCommit(function -> {
                                lista.get(getIndex()).getRequisitos().set(function.getIndex(), function.getNewValue());
                                dao.updateVideojuego(lista.get(getIndex()), App.getTipoAlmacenamiento());
                                leerDatos();
                            });
                            setGraphic(listView);
                        } else {
                            setGraphic(null);
                        }
                    }
                } catch (IndexOutOfBoundsException ioobe) {
                    setGraphic(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void colocarPlataformas(ArrayList<Juego> lista) {
        plataformaCol.setCellFactory(plataformas -> new TableCell() {
            private final ListView<String> listView = new ListView<>();

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                try {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        if (lista.size() > 0) {
                            listView.setItems(FXCollections.observableArrayList(lista.get(getIndex()).getPlataforma()));
                            listView.setPrefWidth(70);
                            listView.setCellFactory(TextFieldListCell.forListView());
                            listView.setEditable(true);
                            listView.setOnEditCommit(function -> {
                                lista.get(getIndex()).getPlataforma().set(function.getIndex(), function.getNewValue());
                                dao.updateVideojuego(lista.get(getIndex()), App.getTipoAlmacenamiento());
                                leerDatos();
                            });
                            setGraphic(listView);
                        } else {
                            setGraphic(null);
                        }
                    }
                } catch (IndexOutOfBoundsException ioobe) {
                    setGraphic(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @FXML
    private void cambiarAlmacenamiento(ActionEvent actionEvent) {
        String option = ((MenuItem) actionEvent.getSource()).getText();

        if (option.split(" ").length == 3) {
            option = option.split(" ")[0] + " " + option.split(" ")[1];
        }
        for (int i = 0; i < 4; i++) {
            if (opciones[i].equalsIgnoreCase(option) || opciones[i].equalsIgnoreCase(option.split(" ")[0])) {
                almacenamiento.getItems().get(i).setText(opciones[i] + " ✓");
                App.setTipoAlmacenamiento(i);
                txtNombreEnTabla.setText("");
                leerDatos();
            } else {
                almacenamiento.getItems().get(i).setText(opciones[i]);
            }
        }
    }

    @FXML
    private void agregarJuegos() throws IOException {
        for (int i = 0; i < tblListaJuegos.getItems().size(); i++) {
            tblListaJuegos.getItems().clear();
        }
        App.setRoot("agregarJuegos");
    }


    @FXML
    private void eliminarInterfaz(ActionEvent actionEvent) throws IOException {
        for (int i = 0; i < tblListaJuegos.getItems().size(); i++) {
            tblListaJuegos.getItems().clear();
        }

        String option = ((MenuItem) actionEvent.getSource()).getText();
        EliminarInterfaz.setElementoSeleccionado(option);
        App.setRoot("eliminarInterfaz");
    }

    @FXML
    private void agregarEmpresa() throws IOException {
        for (int i = 0; i < tblListaJuegos.getItems().size(); i++) {
            tblListaJuegos.getItems().clear();
        }
        App.setRoot("agregarEmpresa");
    }

    @FXML
    private void agregarPlataforma() throws IOException {
        for (int i = 0; i < tblListaJuegos.getItems().size(); i++) {
            tblListaJuegos.getItems().clear();
        }
        App.setRoot("agregarPlataforma");
    }

    @FXML
    private void agregarCategoria() throws IOException {
        for (int i = 0; i < tblListaJuegos.getItems().size(); i++) {
            tblListaJuegos.getItems().clear();
        }
        App.setRoot("agregarCategoria");
    }

}

