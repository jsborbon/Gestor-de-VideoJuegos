package nebrija.gestionVideojuegos.modelo;

public class Plataforma implements java.io.Serializable {

    private int id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private String empresa;

    public Plataforma(String nombre, String descripcion, String imagen, String empresa) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.empresa = empresa;
    }

    public Plataforma(int id, String nombre, String descripcion, String imagen, String empresa) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.empresa = empresa;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return id + "; " + nombre + "; " + descripcion + "; " + imagen + "; " + empresa;
    }
}
