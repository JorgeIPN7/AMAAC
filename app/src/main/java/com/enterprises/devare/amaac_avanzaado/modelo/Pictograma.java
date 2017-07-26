package com.enterprises.devare.amaac_avanzaado.modelo;

import android.content.res.Resources;

public class Pictograma {

    //<editor-fold desc="ETIQUETAS">
    public static final int TIPO_PIC_NORMAL = 0;
    public static final int TIPO_PIC_SELECCIONADO = 1;

    public static final int CAT_VERBOS_AUX = 1;
    public static final int CAT_RESPUESTAS = 2;
    public static final int CAT_VERBOS = 3;
    public static final int CAT_BEBIDAS = 4;
    public static final int CAT_FRUTAS = 5;
    public static final int CAT_VERDURAS = 6;
    public static final int CAT_COMIDA = 7;
    public static final int CAT_POSTRES = 8;
    public static final int CAT_ALIMENTOS = 9;
    public static final int CAT_ANIMALES = 10;
    public static final int CAT_OBJETOS = 11;
    public static final int CAT_ANIMO = 12;
    public static final int CAT_LUGARES = 13;
    public static final int CAT_FAMILIA = 14;
    public static final int CAT_PROFESIONES = 15;

    public static final int CAT_VOCALES = 16;
    public static final int CAT_VOCAL_A = 161;
    public static final int CAT_VOCAL_E = 162;
    public static final int CAT_VOCAL_I = 163;
    public static final int CAT_VOCAL_O = 164;
    public static final int CAT_VOCAL_U = 165;

    public static final int CAT_CONSONANTES = 17;
    public static final int CAT_CONSONANTES_B = 171;
    public static final int CAT_CONSONANTES_C = 172;
    public static final int CAT_CONSONANTES_D = 173;
    public static final int CAT_CONSONANTES_F = 174;
    public static final int CAT_CONSONANTES_G = 175;
    public static final int CAT_CONSONANTES_H = 176;
    public static final int CAT_CONSONANTES_J = 177;
    public static final int CAT_CONSONANTES_K = 178;
    public static final int CAT_CONSONANTES_L = 179;
    public static final int CAT_CONSONANTES_M = 1710;
    public static final int CAT_CONSONANTES_N = 1711;
    public static final int CAT_CONSONANTES_P = 1712;
    public static final int CAT_CONSONANTES_Q = 1713;
    public static final int CAT_CONSONANTES_R = 1714;
    public static final int CAT_CONSONANTES_S = 1715;
    public static final int CAT_CONSONANTES_T = 1716;
    public static final int CAT_CONSONANTES_V = 1717;
    public static final int CAT_CONSONANTES_W = 1718;
    public static final int CAT_CONSONANTES_X = 1719;
    public static final int CAT_CONSONANTES_Y = 1720;
    public static final int CAT_CONSONANTES_Z = 1721;

    public static final int CAT_MONOSILABAS = 18;
    public static final int CAT_MONOSILABAS_COMIDA = 181;
    public static final int CAT_MONOSILABAS_BEBIDAS = 182;
    public static final int CAT_MONOSILABAS_RESPUESTAS = 183;
    public static final int CAT_MONOSILABAS_ANIMALES = 184;
    public static final int CAT_MONOSILABAS_VERBOS = 185;
    public static final int CAT_MONOSILABAS_FAMILIA = 186;
    public static final int CAT_MONOSILABAS_OBJETOS = 187;

    public static final int CAT_BISILABAS = 19;
    public static final int CAT_BISILABAS_BEBIDAS = 191;
    public static final int CAT_BISILABAS_FRUTAS = 192;
    public static final int CAT_BISILABAS_VERDURAS = 193;
    public static final int CAT_BISILABAS_COMIDA = 194;
    public static final int CAT_BISILABAS_COMIDA_GENERAL = 195;
    public static final int CAT_BISILABAS_VERBOS_AUX = 196;
    public static final int CAT_BISILABAS_ANIMALES = 197;
    public static final int CAT_BISILABAS_VERBOS = 198;
    public static final int CAT_BISILABAS_FAMILIA = 199;
    public static final int CAT_BISILABAS_ESTADOS_ANIMO = 1910;
    public static final int CAT_BISILABAS_LUGARES = 1911;
    public static final int CAT_BISILABAS_OBJETOS = 1912;

    public static final int CAT_POLISILABAS = 20;
    public static final int CAT_POLISILABAS_BEBIDAS = 201;
    public static final int CAT_POLISILABAS_FRUTAS = 202;
    public static final int CAT_POLISILABAS_VERDURAS = 203;
    public static final int CAT_POLISILABAS_COMIDA = 204;
    public static final int CAT_POLISILABAS_COMIDA_GENERAL = 205;
    public static final int CAT_POLISILABAS_VERBOS_AUX = 206;
    public static final int CAT_POLISILABAS_ANIMALES = 207;
    public static final int CAT_POLISILABAS_VERBOS = 208;
    public static final int CAT_POLISILABAS_FAMILIA = 209;
    public static final int CAT_POLISILABAS_ESTADOS_ANIMO = 2010;
    public static final int CAT_POLISILABAS_LUGARES = 2011;
    public static final int CAT_POLISILABAS_OBJETOS = 2012;
    //</editor-fold>

    private int id;
    private int tipo;
    public int categoria;
    public String nombre;
    public int idDrawable;
    private int idSonido;
    private int habilitado;
    private int completado;
    private int progreso;

    //<editor-fold desc="CONSTRUCTOR CON PARAMETROS">

    public Pictograma(int tipo, int categoria, String nombre, int idDrawable, int idSonido, int habilitado, int completado, int progreso) {
        this.categoria = categoria;
        this.completado = completado;
        this.habilitado = habilitado;
        this.idDrawable = idDrawable;
        this.idSonido = idSonido;
        this.nombre = nombre;
        this.tipo = tipo;
        this.progreso = progreso;
    }

    //</editor-fold>

    //<editor-fold desc="MÉTODOS GETTERS">

    public int getCategoria() {
        return categoria;
    }

    public int getCompletado() {
        return completado;
    }

    public int getHabilitado() {
        return habilitado;
    }

    public int getId() {
        return id;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public int getIdSonido() {
        return idSonido;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public int getProgreso() {
        return progreso;
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODOS SETTERS">

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public void setCompletado(int completado) {
        this.completado = completado;
    }

    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    public void setIdSonido(int idSonido) {
        this.idSonido = idSonido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setProgreso(int progreso) {

        this.progreso = progreso;
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO toString()">
    public String toString() {
        return ("Nombre: " + nombre + "\n" +
                "Categoria: " + categoria + "\n" +
                "Habilitado: " + habilitado + "\n" +
                "Completado: " + completado + "\n" +
                "IdDrawable: " + idDrawable + "\n" +
                "Progreso: " + progreso + "\n");
    }
    //</editor-fold>
}

