package proyectohotel;

public class Usuario {
    private String nombre;
    private String tipo; // Ej: "cliente" o "admin"

    public Usuario(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }
}

