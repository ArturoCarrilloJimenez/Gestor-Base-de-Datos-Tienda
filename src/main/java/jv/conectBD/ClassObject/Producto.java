package jv.conectBD.ClassObject;

public class Producto {

    private int id;

    private String nombre,descripcion;
    private double pvp;

    public Producto(int id,String nombre, String descripcion, double pvp) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pvp = pvp;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPvp() {
        return pvp;
    }

    public int getId() {
        return id;
    }
}
