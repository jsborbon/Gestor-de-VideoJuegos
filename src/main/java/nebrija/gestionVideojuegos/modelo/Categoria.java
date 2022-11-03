package nebrija.gestionVideojuegos.modelo;

public class Categoria implements java.io.Serializable {
    private int id;
    private String nombre;
    private String imagen;
    private String descripcion;

    public Categoria(String nombre, String imagen, String descripcion) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public Categoria(int id, String nombre, String imagen, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return id + "; " + nombre + "; " + imagen + "; " + descripcion;
    }
}
