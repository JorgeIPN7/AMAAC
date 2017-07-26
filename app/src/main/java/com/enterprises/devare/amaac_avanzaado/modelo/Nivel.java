package com.enterprises.devare.amaac_avanzaado.modelo;

/**
 * Created by Angel on 11/11/2016.
 */

public class Nivel {

    //<editor-fold desc="ETIQUETAS">
    public static final int VISTA_NORMAL=0;
    //</editor-fold>

    private int id;
    private int tipo;
    private String nombre;
    private int progresso;
    private int idDrawable;

    //<editor-fold desc="CONSTRUCTOR CON PARAMETROS">
    public Nivel(int tipo, String nombre, int progresso, int idDrawable) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.progresso = progresso;
        this.idDrawable = idDrawable;
    }
    //</editor-fold>


    //<editor-fold desc="MÉTODOS GETTERS">
    public int getId() {
        return id;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public String getNombre() {
        return nombre;
    }

    public int getProgresso() {
        return progresso;
    }

    public int getTipo() {
        return tipo;
    }
    //</editor-fold>


    //<editor-fold desc="MÉTODOS SETTERS">
    public void setId(int id) {
        this.id = id;
    }

    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProgresso(int progresso) {
        this.progresso = progresso;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    //</editor-fold>

    public String toString() {
        return ("ID: " + id + "\n" +
                "Nombre: " + nombre + "\n" +
                "Tipo: " + tipo + "\n" +
                "IdDrawable: " + idDrawable + "\n" +
                "Progreso: " + progresso + "\n");
    }

}
