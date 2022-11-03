package nebrija.gestionVideojuegos.modelo;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FicheroTexto<E> {

    //    escribir en un fichero
    public void writeFile(String filename, ArrayList<E> list) throws IOException {
        //Saving of object in a file
        FileWriter file = new FileWriter(filename, true);
        PrintWriter out = new PrintWriter(file);
        String linea = "";
        for (Object listadoItems : list) {
            linea = listadoItems.toString()+"\r\n";
            out.write(linea);
        }

        // Method for serialization of object

        out.close();
        file.close();

        System.out.println("Object has been written to file");

    }

    private void createFile(String filename) throws IOException {
        //Saving of object in a file
        File file = new File(filename);
        file.createNewFile();


        System.out.println("File created");

    }

    public ArrayList<E> readFile(String filename) throws IOException, ClassNotFoundException {

        File file;
        Scanner in;

        try {
            file = new File(filename);
            in = new Scanner(file);
        } catch (FileNotFoundException fnfe) {
            createFile(filename);
            file = new File(filename);
            in = new Scanner(file);
        }
        // Method for deserialization of object
        System.out.println("Leyendo");

        ArrayList<E> list = new ArrayList<E>();

        while (in.hasNextLine()) {
            String[] data = in.nextLine().split("; ");

            if (data.length > 0) {
                if (filename.equals("gamesList.txt")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(data[5], formatter);
                    String[] plataformas = data[4].replaceAll("\\[|\\]", "").split(", ");
                    String[] requisitos = data[6].replaceAll("\\[|\\]", "").split(", ");
                    list.add((E) new Juego(Integer.parseInt(data[0]), data[1], data[2], data[3], (new ArrayList(Arrays.asList(plataformas))), localDate, (new ArrayList(Arrays.asList(requisitos))), data[7], data[8]));

                } else if (filename.equals("platformsList.txt")) {
                    list.add((E) new Plataforma(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]));
                } else if (filename.equals("companiesList.txt")) {
                    list.add((E) new Compania(Integer.parseInt(data[0]), data[1], data[2], data[3]));
                } else if (filename.equals("categoriesList.txt")) {
                    list.add((E) new Categoria(Integer.parseInt(data[0]), data[1], data[2], data[3]));
                }
            }
        }
        in.close();

        System.out.println("Object has been read ");

        return list;
    }

    public void updateFile(String filename, ArrayList<E> list) throws IOException {

        //Cleaning the file
        cleaningFile(filename);
        writeFile(filename, list);
        System.out.println("Object has been rewritten to file");

    }

    public void cleaningFile(String filename) throws IOException {

        //Cleaning the file

        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        bw.write("");
        bw.close();
        System.out.println("File has been cleaned");

    }
}
