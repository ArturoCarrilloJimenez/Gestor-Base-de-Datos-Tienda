package jv.conectBD.ClassObject;

public class Cliente {
    private int id;
    private String nombre,apellido1,apellido2,telefono;

    public Cliente(int id,String nombre, String apellido1, String apellido2, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getId() {
        return id;
    }
}
