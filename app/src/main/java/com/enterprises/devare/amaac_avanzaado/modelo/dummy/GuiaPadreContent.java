package com.enterprises.devare.amaac_avanzaado.modelo.dummy;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.enterprises.devare.amaac_avanzaado.R;

public class GuiaPadreContent{

    public static final List<GuiaPadre> ITEMS = new ArrayList<GuiaPadre>();

    public static final Map<String, GuiaPadre> ITEM_MAP = new HashMap<String,GuiaPadre>();


    public static void agregarItem(GuiaPadre item) {

        if(ITEMS.size()<3) {
           ITEMS.add(item);
           ITEM_MAP.put(item.id, item);
        }
    }

    public static class GuiaPadre {
        public final String id;
        public final String titulo;
        public final String descripcion;
        public final String fecha;
        public final int idImagen;

        public GuiaPadre(String id, String titulo, String descripcion, String fecha, int idImagen) {
            this.id = id;
            this.titulo = titulo;
            this.descripcion = descripcion;
            this.fecha=fecha;
            this.idImagen=idImagen;
        }

        @Override
        public String toString() {
            return titulo;
        }
    }
}
