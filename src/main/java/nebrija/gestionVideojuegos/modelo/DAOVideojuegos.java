package nebrija.gestionVideojuegos.modelo;

import java.io.EOFException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOVideojuegos {

    public void addVideojuego(Juego game, int tipo) {


        switch (tipo) {
            case 0: {

                String filename = "gamesList.bin";
                //Crear fichero en binario
                ArrayList<Juego> lista = new ArrayList<>(readVideojuego(tipo)); //Lee la lista actual
                lista.add(game); //Añade el juego

                // Serialization
                try {
                    //Saving of object in a file
                    Binario<Juego> bin = new Binario<>();
                    asignarID(game, lista); //Asigna el ID al juego
                    bin.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                String filename = "gamesList.txt";
                //Guardando fichero de texto

                ArrayList<Juego> lista = new ArrayList<>(readVideojuego(tipo)); //Lee la lista actual
                lista.add(game); //Añade el juego
                try {
                    //Saving of object in a file
                    FicheroTexto<Juego> txt = new FicheroTexto<>();
                    asignarID(game, lista); //Asigna el ID al juego
                    txt.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                try {
                    BD<Juego> bd = new BD<>();
                    bd.insertInDatabase(game);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                ArrayList<Juego> lista = new ArrayList<>(readVideojuego(tipo)); //Lee la lista actual
                String dataName = "juego";
                try {
                    BDNoSQL<Juego> bd = new BDNoSQL<>();
                    asignarID(game, lista); //Asigna el ID al juego
                    bd.insertInDatabase(game, dataName);
                } catch (UnknownHostException uhe) {
                    uhe.printStackTrace();
                }
                break;
            }
        }
    }

    private void asignarID(Juego game, ArrayList<Juego> lista) {
        int id = 0;
        if (lista.size() != 0) {
            id = lista.get(lista.size()-1).getId()+1; //Calcula el id del juego
        }
        game.setId(id); //Asigna el id al videojuego
    }

    public ArrayList<Juego> readVideojuego(int tipo) {

        ArrayList<Juego> lista = new ArrayList<Juego>();

        switch (tipo) {
            case 0: {
                String filename = "gamesList.bin";
                //Crear fichero en binario

                // Deserialization

                try {
                    Binario<Juego> bin = new Binario<Juego>();
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
                String filename = "gamesList.txt";
                //Leyendo fichero de texto
                try {
                    FicheroTexto<Juego> txt = new FicheroTexto<>();
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
                    String dataName = "juego";
                    BD<Juego> bd = new BD<>();
                    lista = bd.readDatabase(dataName);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                String dataName = "juego";
                try {
                    BDNoSQL<Juego> bd = new BDNoSQL<>();
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

    public ArrayList<Juego> buscarVideojuego(int tipo, String nombre) { //REVISAR
        ArrayList<Juego> lista = this.readVideojuego(tipo);
        ArrayList<Juego> listaFiltrada = new ArrayList<Juego>();
        for (Juego juego : lista) {
            if (juego.getNombre().toUpperCase().contains(nombre) || juego.getNombre().toLowerCase().contains(nombre)) {
                listaFiltrada.add(juego);
            }
        }
        return listaFiltrada;
    }


    public void updateVideojuego(Juego game, int tipo) {

        ArrayList<Juego> lista = new ArrayList<>(readVideojuego(tipo)); //Lee la lista actual
        int id = game.getId();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.set(i, game);
            }
        }

        switch (tipo) {
            case 0: {
                String filename = "gamesList.bin";
                //Crear fichero en binario

                // Serialization
                try {
                    //Saving of object in a file
                    Binario<Juego> bin = new Binario<>();
                    bin.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                String filename = "gamesList.txt";
                //Crear fichero de texto
                try {
                    //Saving of object in a file
                    FicheroTexto<Juego> txt = new FicheroTexto<>();
                    txt.updateFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                try {
                    String dataName = "juego";
                    BD<Juego> bd = new BD<>();
                    bd.updateDatabase(dataName, game);
                }catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                String dataName = "juego";
                try {
                    BDNoSQL<Juego> bd = new BDNoSQL<>();
                    bd.updateInDatabase(game, dataName);
                } catch (UnknownHostException uhe) {
                    uhe.printStackTrace();
                }
                break;
            }
        }

    }

    public void eliminarVideojuego(Juego game, int tipo) {

        ArrayList<Juego> lista = new ArrayList<>(readVideojuego(tipo)); //Lee la lista actual
        int id = game.getId();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
            }
        }

        switch (tipo) {
            case 0: {
                String filename = "gamesList.bin";
                //Leer fichero en binario

                // Serialization
                try {
                    //Saving of object in a file
                    Binario<Juego> bin = new Binario<>();
                    bin.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                String filename = "gamesList.txt";
                //Leer fichero en binario

                try {
                    //Saving of object in a file
                    FicheroTexto<Juego> txt = new FicheroTexto<>();
                    txt.updateFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                try {
                    String dataName = "juego";
                    BD<Juego> bd = new BD<>();
                    bd.deleteFromDatabase(dataName, id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                String dataName = "juego";
                try {
                    BDNoSQL<Juego> bd = new BDNoSQL<>();
                    bd.deleteFromDatabase(game, dataName);
                } catch (UnknownHostException uhe) {
                    uhe.printStackTrace();
                }
                break;
            }
        }
    }

}
