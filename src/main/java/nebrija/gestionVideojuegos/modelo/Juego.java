package nebrija.gestionVideojuegos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Juego implements java.io.Serializable {

    private int id;
    private String nombre;
    private String categoria;
    private String compania;
    private ArrayList<String> plataforma;
    private LocalDate fecha;
    private ArrayList<String> requisitos;
    private String comentario;
    private String imagen;

    public Juego(String nombre, String categoria, String compania, ArrayList<String> plataforma, LocalDate fecha, ArrayList<String> requisitos, String comentario, String imagen) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.compania = compania;
        this.plataforma = plataforma;
        this.fecha = fecha;
        this.requisitos = requisitos;
        this.comentario = comentario;
        this.imagen = imagen;
    }

    public Juego(int id, String nombre, String categoria, String compania, ArrayList<String> plataforma, LocalDate fecha, ArrayList<String> requisitos, String comentario, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.compania = compania;
        this.plataforma = plataforma;
        this.fecha = fecha;
        this.requisitos = requisitos;
        this.comentario = comentario;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public ArrayList<String> getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(ArrayList<String> plataforma) {
        this.plataforma = plataforma;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ArrayList<String> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(ArrayList<String> requisitos) {
        this.requisitos = requisitos;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return id + "; " + nombre + "; " + categoria + "; " + compania + "; " + plataforma + "; " + fecha + "; " + requisitos + "; " + comentario + "; " + imagen;
    }
}
