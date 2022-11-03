package nebrija.gestionVideojuegos.modelo;

import java.io.*;
import java.util.ArrayList;

public class Binario<E> {

    //    escribir en un fichero
    public void writeFile(String filename, ArrayList<E> list) throws IOException {

        //Saving of object in a file
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);

        // Method for serialization of object

        out.writeObject(list);

        out.close();
        file.close();

    }

    private void createFile(String filename) throws IOException {
        //Saving of object in a file
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);

        // Method for serialization of object

        out.close();
        file.close();


    }

    public ArrayList<E> readFile(String filename) throws IOException, ClassNotFoundException {

        FileInputStream file;
        ObjectInputStream in;

        try {
            file = new FileInputStream(filename);
            in = new ObjectInputStream(file);
        } catch (FileNotFoundException fnfe) {
            createFile(filename);
            file = new FileInputStream(filename);
            in = new ObjectInputStream(file);
        }
        // Method for deserialization of object

        Object obj = in.readObject();
        ArrayList<E> list = new ArrayList<E>();

        if (obj != null) {
            list = (ArrayList<E>) obj;
        }
        in.close();
        file.close();



        return list;
    }

}
