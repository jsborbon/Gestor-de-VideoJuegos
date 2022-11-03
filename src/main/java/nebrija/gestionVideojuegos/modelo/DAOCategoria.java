package nebrija.gestionVideojuegos.modelo;

import java.io.EOFException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOCategoria {

    public void addCategoria(Categoria category, int tipo) {


        switch (tipo) {
            case 0: {
                ArrayList<Categoria> lista = new ArrayList<>(readCategoria(tipo)); //Lee la lista actual
                lista.add(category);
                String filename = "categoriesList.bin";
                //Crear fichero en binario

                // Serialization
                try {
                    //Saving of object in a file
                    Binario<Categoria> bin = new Binario<>();
                    asignarID(category, lista); //Asigna el ID a la plataforma
                    bin.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                ArrayList<Categoria> lista = new ArrayList<>(readCategoria(tipo)); //Lee la lista actual
                lista.add(category);
                String filename = "categoriesList.txt";
                //Guardando fichero de texto
                try {
                    //Saving of object in a file
                    FicheroTexto<Categoria> txt = new FicheroTexto<>();
                    asignarID(category, lista); //Asigna el ID al juego
                    txt.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                try {
                    BD<Categoria> bd = new BD<>();
                    bd.insertInDatabase(category);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                ArrayList<Categoria> lista = new ArrayList<>(readCategoria(tipo)); //Lee la lista actual
                String dataName = "categoria";
                try {
                    BDNoSQL<Categoria> bd = new BDNoSQL<>();
                    asignarID(category, lista); //Asigna el ID al juego
                    bd.insertInDatabase(category, dataName);
                } catch (UnknownHostException uhe) {
                    uhe.printStackTrace();
                }
                break;
            }
        }
    }


    private void asignarID(Categoria category, ArrayList<Categoria> lista) {
        int id = 0;
        if (lista.size() != 0) {
            id = lista.get(lista.size()-1).getId()+1; //Calcula el id de la nueva categoria
        }
        category.setId(id); //Asigna el id de la categoria
    }

    public ArrayList<Categoria> readCategoria(int tipo) {

        ArrayList<Categoria> lista = new ArrayList<>();

        switch (tipo) {
            case 0: {
                String filename = "categoriesList.bin";
                //Crear fichero en binario

                // Deserialization

                try {
                    Binario<Categoria> bin = new Binario<>();
                    // Reading the object from a file
                    lista = bin.readFile(filename);
                } catch (EOFException eofException) {
                    System.out.println("No hay datos en el archivo");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case 1: {
                String filename = "categoriesList.txt";
                //Leyendo fichero de texto
                try {
                    FicheroTexto<Categoria> txt = new FicheroTexto<>();
                    //Reading the object from a file
                    lista = txt.readFile(filename);

                } catch (EOFException eofException) {
                    System.out.println("No hay datos en el archivo");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case 2: {

                try {
                    String dataName = "categoria";
                    BD<Categoria> bd = new BD<>();
                    lista = bd.readDatabase(dataName);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 3: {

                String dataName = "categoria";
                try {
                    BDNoSQL<Categoria> bd = new BDNoSQL<>();
                    lista = bd.readDatabase(dataName);
                } catch (UnknownHostException uhe) {
                    uhe.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return lista;
    }

    public ArrayList<Categoria> buscarCategoria(int tipo, String nombre) {
        ArrayList<Categoria> lista = this.readCategoria(tipo);
        ArrayList<Categoria> listaFiltrada = new ArrayList<>();
        for (Categoria category : lista) {
            if (category.getNombre().toUpperCase().contains(nombre) || category.getNombre().toLowerCase().contains(nombre)) {
                listaFiltrada.add(category);
            }
        }
        return listaFiltrada;
    }

    public void eliminarCategoria(Categoria category, int tipo) {

        ArrayList<Categoria> lista = new ArrayList<>(readCategoria(tipo)); //Lee la lista actual
        int id = category.getId();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
            }
        }

        switch (tipo) {
            case 0: {
                String filename = "categoriesList.bin";
                //Crear fichero en binario

                // Serialization
                try {
                    //Saving of object in a file
                    Binario<Categoria> bin = new Binario<>();
                    bin.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                String filename = "categoriesList.txt";
                //Leyendo fichero de texto
                try {
                    FicheroTexto<Categoria> txt = new FicheroTexto<>();
                    //Reading the object from a file
                    txt.updateFile(filename, lista);
                } catch (EOFException eofException) {
                    System.out.println("No hay datos en el archivo");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case 2: {
                try {
                    String dataName = "categoria";
                    BD<Categoria> bd = new BD<>();
                    bd.deleteFromDatabase(dataName, id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {

                String dataName = "categoria";
                try {
                    BDNoSQL<Categoria> bd = new BDNoSQL<>();
                    bd.deleteFromDatabase(category, dataName);
                } catch (UnknownHostException uhe) {
                    uhe.printStackTrace();
                }
                break;
            }
        }
    }

}

