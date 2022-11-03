package nebrija.gestionVideojuegos.modelo;

import java.io.EOFException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOCompania {

    public void addCompania(Compania company, int tipo) {


        switch (tipo) {
            case 0: {
                ArrayList<Compania> lista = new ArrayList<>(readCompania(tipo)); //Lee la lista actual
                lista.add(company); //Añade la compañia
                String filename = "companiesList.bin";
                //Crear fichero en binario

                // Serialization
                try {
                    //Saving of object in a file
                    Binario<Compania> bin = new Binario<>();
                    asignarID(company, lista); //Asigna el ID a la compañia
                    bin.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                ArrayList<Compania> lista = new ArrayList<>(readCompania(tipo)); //Lee la lista actual
                lista.add(company); //Añade la compañia
                String filename = "companiesList.txt";
                //Guardando fichero de texto
                try {
                    //Saving of object in a file
                    FicheroTexto<Compania> txt = new FicheroTexto<>();
                    asignarID(company, lista); //Asigna el ID al juego
                    txt.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                try {
                    BD<Compania> bd = new BD<>();
                    bd.insertInDatabase(company);
                }catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                ArrayList<Compania> lista = new ArrayList<>(readCompania(tipo)); //Lee la lista actual

                for (Compania c : lista) {
                    System.out.println(c);

                }


                String dataName = "compania";
                try {
                    BDNoSQL<Compania> bd = new BDNoSQL<>();
                    asignarID(company, lista); //Asigna el ID al juego
                    bd.insertInDatabase(company, dataName);
                } catch (UnknownHostException uhe) {
                    uhe.printStackTrace();
                }
                break;
            }
        }
    }

    private void asignarID(Compania company, ArrayList<Compania> lista) {
        int id = 0;
        if (lista.size() != 0) {
            id = lista.get(lista.size()-1).getId()+1; //Calcula el id de la nueva compañia
        }
        company.setId(id); //Asigna el id a la compañia
    }

    public ArrayList<Compania> readCompania(int tipo) {

        ArrayList<Compania> lista = new ArrayList<Compania>();

        switch (tipo) {
            case 0: {
                String filename = "companiesList.bin";
                //Crear fichero en binario

                // Deserialization

                try {
                    Binario<Compania> bin = new Binario<Compania>();
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
                String filename = "companiesList.txt";
                //Leyendo fichero de texto
                try {
                    FicheroTexto<Compania> txt = new FicheroTexto<>();
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
                    String dataName = "compania";
                    BD<Compania> bd = new BD<>();
                    lista = bd.readDatabase(dataName);
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 3: {

                String dataName = "compania";
                try {
                    BDNoSQL<Compania> bd = new BDNoSQL<>();
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

    public ArrayList<Compania> buscarCompania(int tipo, String nombre) {
        ArrayList<Compania> lista = this.readCompania(tipo);
        ArrayList<Compania> listaFiltrada = new ArrayList<Compania>();
        for (Compania company : lista) {
            if (company.getNombre().toUpperCase().contains(nombre) || company.getNombre().toLowerCase().contains(nombre)) {
                listaFiltrada.add(company);
            }
        }
        return listaFiltrada;
    }

    public void eliminarCompania(Compania company, int tipo) {

        ArrayList<Compania> lista = new ArrayList<>(readCompania(tipo)); //Lee la lista actual
        int id = company.getId();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
            }
        }

        switch (tipo) {
            case 0: {
                String filename = "companiesList.bin";
                //Crear fichero en binario

                // Serialization
                try {
                    //Saving of object in a file
                    Binario<Compania> bin = new Binario<>();
                    bin.writeFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                String filename = "companiesList.txt";
                //Crear fichero de texto
                try {
                    //Saving of object in a file
                    FicheroTexto<Compania> txt = new FicheroTexto<>();
                    txt.updateFile(filename, lista);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                try {
                    String dataName = "compania";
                    BD<Compania> bd = new BD<>();
                    bd.deleteFromDatabase(dataName, id);
                }catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                String dataName = "compania";
                try {
                    BDNoSQL<Compania> bd = new BDNoSQL<>();
                    bd.deleteFromDatabase(company, dataName);
                } catch (UnknownHostException uhe) {
                    uhe.printStackTrace();
                }
                break;
            }
        }
    }


}
