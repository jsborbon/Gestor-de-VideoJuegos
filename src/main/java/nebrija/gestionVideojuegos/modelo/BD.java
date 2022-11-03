package nebrija.gestionVideojuegos.modelo;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class BD<E> {

    private Connection con = null;
    private static final String databaseName = "gestionVideojuegos";

    public BD() throws SQLException {
        if(con == null) {
            con = DBconexion.getConnection(); //Conecta al servidor
            crearDataBase(databaseName); //Crea la base de datos
            DBconexion.accesoBD(databaseName); //Establece la base de datos
            con = DBconexion.getConnection(); //Conecta a la base de datos
        }
        crearTablas(); //Crea las tablas
    }


    // Insertar en una tabla
    public void insertInDatabase(E objeto) throws SQLException {

        String[] atributos = objeto.toString().split("; ");
        Field[] atributosField = objeto.getClass().getDeclaredFields();
        String sql = "INSERT INTO " + objeto.getClass().getSimpleName().toLowerCase() + " (";
        for (int i = 1; i < atributosField.length; i++) {
            sql += atributosField[i].getName();
            if (i != atributosField.length - 1) {
                sql += ",";
            }
        }
        sql += ") VALUES (";
        for (int i = 1; i < atributos.length; i++) {
            sql += "?";
            if (i != atributos.length - 1) {
                sql += ",";
            }
        }
        sql += ")";

        PreparedStatement ps = con.prepareStatement(sql);

        for (int i = 1; i < atributos.length; i++) {
            ps.setString(i, atributos[i]);
        }

        ps.executeUpdate();

        ps.close();

    }

    public ArrayList<E> readDatabase(String databaseName) throws SQLException {

        PreparedStatement ps = con.prepareStatement("SELECT * from " + databaseName);

        ResultSet rs = ps.executeQuery();
        ArrayList<E> result = null;
        while (rs.next()) {
            if (result == null)
                result = new ArrayList<>();
            if (databaseName.equals("juego")) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(rs.getString("fecha"), formatter);
                String[] plataformas = rs.getString("plataforma").replaceAll("\\[|\\]", "").split(", ");
                String[] requisitos = rs.getString("requisitos").replaceAll("\\[|\\]", "").split(", ");

                result.add((E) new Juego(rs.getInt("id"), rs.getString("nombre"), rs.getString("categoria"), rs.getString("compania"), (new ArrayList(Arrays.asList(plataformas))), localDate, (new ArrayList(Arrays.asList(requisitos))), rs.getString("comentario"), rs.getString("imagen")));
            } else if (databaseName.equals("plataforma")) {
                result.add((E) new Plataforma(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("imagen"), rs.getString("empresa")));
            } else if (databaseName.equals("compania")) {
                result.add((E) new Compania(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("imagen")));

            } else if (databaseName.equals("categoria")) {
                result.add((E) new Categoria(rs.getInt("id"), rs.getString("nombre"), rs.getString("imagen"), rs.getString("descripcion")));
            }

        }
        rs.close();
        ps.close();
        return result;
    }

    public void deleteFromDatabase(String databaseName, int id) throws SQLException {

        PreparedStatement ps = con.prepareStatement("DELETE FROM " + databaseName + " WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public void updateDatabase(String databaseName, E objeto) throws SQLException {
        String[] atributos = objeto.toString().split("; ");
        Field[] atributosField = objeto.getClass().getDeclaredFields();
        String sql = "UPDATE " + databaseName + " SET ";
        for (int i = 1; i < atributosField.length; i++) {
            sql += atributosField[i].getName() + " = '" + atributos[i]+"'";
            if (i != atributosField.length - 1) {
                sql += ",";
            }
        }
        sql += "WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(atributos[0]));
        ps.executeUpdate();
        ps.close();

    }

    private void crearDataBase(String databaseName) {
            try {
                PreparedStatement ps = con.prepareStatement("CREATE DATABASE IF NOT EXISTS "+databaseName);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    private void crearTablas(){
        if(con != null){
            try {
                PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS juego (id INTEGER PRIMARY KEY AUTO_INCREMENT, nombre varchar(45), categoria varchar(45), compania varchar(45), plataforma TEXT, fecha date, requisitos TEXT, comentario TEXT, imagen varchar(45))");
                ps.executeUpdate();
                ps.close();
                ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS plataforma (id INTEGER PRIMARY KEY AUTO_INCREMENT, nombre varchar(45), descripcion TEXT, imagen varchar(255), empresa varchar(45))");
                ps.executeUpdate();
                ps.close();
                ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS compania (id INTEGER PRIMARY KEY AUTO_INCREMENT, nombre varchar(45), descripcion TEXT, imagen varchar(255))");
                ps.executeUpdate();
                ps.close();
                ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS categoria (id INTEGER PRIMARY KEY AUTO_INCREMENT, nombre varchar(45), imagen varchar(255), descripcion TEXT)");
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
