package nebrija.gestionVideojuegos.modelo;

import java.lang.reflect.Field;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.*;

public class BDNoSQL<E> {

    private static MongoClient mongoClient = null;
    private static DB database = null;

    public BDNoSQL() throws UnknownHostException {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            database = mongoClient.getDB("gestionVideojuegos");

    }


    // Insertar en una tabla
    public void insertInDatabase(E objeto, String collectionName) {
        DBObject objetoDB = toDBObject(objeto);
        DBCollection collection = database.getCollection(collectionName);
        collection.insert(objetoDB);
    }

    public DBObject toDBObject(E objeto) {
        BasicDBObject dbObject = new BasicDBObject();
        Field[] atributosField = objeto.getClass().getDeclaredFields();
        String[] atributos = objeto.toString().split("; ");
        for (int i = 0; i < atributosField.length; i++) {
            if (atributosField[i].getName().equals("plataforma")) {
                String[] plataformas = atributos[i].replaceAll("\\[|\\]", "").split(", ");
                dbObject.append("plataforma", plataformas);
            } else if (atributosField[i].getName().equals("requisitos")) {
                String[] requisitos = atributos[i].replaceAll("\\[|\\]", "").split(", ");
                dbObject.append("requisitos", requisitos);
            } else {
                dbObject.append(atributosField[i].getName(), atributos[i]);
            }
        }
        return dbObject;
    }


    // Obtener todos los objetos de una tabla
    public ArrayList<E> readDatabase(String databaseName) throws SQLException {
        ArrayList<E> result = new ArrayList<>();
        DBCollection collection = database.getCollection(databaseName);
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
           DBObject objeto = cursor.next();
            if (databaseName.equals("juego")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(objeto.get("fecha").toString(), formatter);
                String[] plataformas = (objeto.get("plataforma").toString()).replaceAll("\\[|\\]|\"", "").split(", ");
                String[] requisitos = (objeto.get("requisitos").toString()).replaceAll("\\[|\\]|\"", "").split(", ");
                result.add((E) new Juego(Integer.parseInt(objeto.get("id").toString()),objeto.get("nombre").toString(), objeto.get("categoria").toString(), objeto.get("compania").toString(), (new ArrayList(Arrays.asList(plataformas))), localDate, (new ArrayList(Arrays.asList(requisitos))), objeto.get("comentario").toString(),objeto.get("imagen").toString()));
            } else if (databaseName.equals("plataforma")) {
                result.add((E) new Plataforma(Integer.parseInt(objeto.get("id").toString()), objeto.get("nombre").toString(), objeto.get("descripcion").toString(), objeto.get("imagen").toString(), objeto.get("empresa").toString()));
            } else if (databaseName.equals("compania")) {
                result.add((E) new Compania(Integer.parseInt(objeto.get("id").toString()), objeto.get("nombre").toString(), objeto.get("descripcion").toString(), objeto.get("imagen").toString()));
             } else if (databaseName.equals("categoria")) {
                result.add((E) new Categoria(Integer.parseInt(objeto.get("id").toString()), objeto.get("nombre").toString(), objeto.get("imagen").toString(), objeto.get("descripcion").toString()));
            }

        }
        return result;
    }

    public void updateInDatabase(E objeto, String databaseName){
        DBCollection collection = database.getCollection(databaseName);
        DBObject query = new BasicDBObject("id",  objeto.toString().split("; ")[0]);
        DBObject objetoDB = toDBObject(objeto);
        collection.update(query, objetoDB);

    }


    public void deleteFromDatabase(E objeto, String databaseName){
        DBCollection collection = database.getCollection(databaseName);
        DBObject query = new BasicDBObject("id",  objeto.toString().split("; ")[0]);
        collection.remove(query);
    }
}
