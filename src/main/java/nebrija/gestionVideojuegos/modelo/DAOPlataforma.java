package nebrija.gestionVideojuegos.modelo;

import java.io.EOFException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOPlataforma {

    public void addPlataforma(Plataforma platform, int tipo) {


        switch (tipo) {
            case 0: {
                ArrayList<Plataforma> lista = new ArrayList<>(readPlataforma(tipo)); //Lee la lista actual
                lista.add(platform);
                String filename = "platformsList.bin";
                //Crear fichero en binario

                // Serialization
                try {
                    //Saving of object in a file
                    Binario<Plataforma> bin = new Binario<>();
                    asignarID(platform, lista); //Asigna el ID a la plataforma
                    bin.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                ArrayList<Plataforma> lista = new ArrayList<>(readPlataforma(tipo)); //Lee la lista actual
                lista.add(platform);
                String filename = "platformsList.txt";
                //Guardando fichero de texto
                try {
                    //Saving of object in a file
                    FicheroTexto<Plataforma> txt = new FicheroTexto<>();
                    asignarID(platform, lista); //Asigna el ID al juego
                    txt.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                try {
                    BD<Plataforma> bd = new BD<>();
                    bd.insertInDatabase(platform);
                }catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                ArrayList<Plataforma> lista = new ArrayList<>(readPlataforma(tipo)); //Lee la lista actual
                String dataName = "plataforma";
                try {
                    BDNoSQL<Plataforma> bd = new BDNoSQL<>();
                    asignarID(platform, lista); //Asigna el ID a la plataforma
                    bd.insertInDatabase(platform, dataName);
                } catch (UnknownHostException uhe) {
                    uhe.printStackTrace();
                }
                break;
            }
        }
    }


    private void asignarID(Plataforma platform, ArrayList<Plataforma> lista) {
        int id = 0;
        if (lista.size() != 0) {
            id = lista.get(lista.size()-1).getId()+1; //Calcula el id de la nueva plataforma
        }
        platform.setId(id); //Asigna el id a la plataforma
    }

    public ArrayList<Plataforma> readPlataforma(int tipo) {

        ArrayList<Plataforma> lista = new ArrayList<Plataforma>();

        switch (tipo) {
            case 0: {
                String filename = "platformsList.bin";
                //Crear fichero en binario

                // Deserialization

                try {
                    Binario<Plataforma> bin = new Binario<Plataforma>();
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
                String filename = "plataformsList.txt";
                //Leyendo fichero de texto
                try {
                    FicheroTexto<Plataforma> txt = new FicheroTexto<>();
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
                    String dataName = "plataforma";
                    BD<Plataforma> bd = new BD<>();
                    lista = bd.readDatabase(dataName);
                }catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                String dataName = "plataforma";
                try {
                    BDNoSQL<Plataforma> bd = new BDNoSQL<>();
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

    public ArrayList<Plataforma> buscarPlataforma(int tipo, String nombre) {
        ArrayList<Plataforma> lista = this.readPlataforma(tipo);
        ArrayList<Plataforma> listaFiltrada = new ArrayList<Plataforma>();
        for (Plataforma platform : lista) {
            if (platform.getNombre().toUpperCase().contains(nombre) || platform.getNombre().toLowerCase().contains(nombre)) {
                listaFiltrada.add(platform);
            }
        }
        return listaFiltrada;
    }

    public void eliminarPlataforma(Plataforma platform, int tipo) {

        ArrayList<Plataforma> lista = new ArrayList<>(readPlataforma(tipo)); //Lee la lista actual
        int id = platform.getId();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
            }
        }

        switch (tipo) {
            case 0: {
                String filename = "platformsList.bin";
                //Crear fichero en binario

                // Serialization
                try {
                    //Saving of object in a file
                    Binario<Plataforma> bin = new Binario<>();
                    bin.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                String filename = "platformsList.txt";
                //Crear fichero de texto
                try {
                    //Saving of object in a file
                    FicheroTexto<Plataforma> txt = new FicheroTexto<>();
                    txt.updateFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                try {
                    String dataName = "plataforma";
                    BD<Plataforma> bd = new BD<>();
                    bd.deleteFromDatabase(dataName, id);
                }catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {

                String dataName = "plataforma";
                try {
                    BDNoSQL<Plataforma> bd = new BDNoSQL<>();
                    bd.deleteFromDatabase(platform, dataName);
                } catch (UnknownHostException uhe) {
                    uhe.printStackTrace();
                }
                break;
            }
        }
    }

}

