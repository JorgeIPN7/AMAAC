package com.enterprises.devare.amaac_avanzaado.modelo.utilidades;

import com.enterprises.devare.amaac_avanzaado.R;

public class Utilidades {


    //<editor-fold desc="MÉTODO getBackground_CardView">
    public static int getBackground_CardView(int categoria){
        int d;

        switch (categoria){
            case 1:
                d = 0xffFE2E2E;
                break;
            case 2:
                d = 0xffFF8000;
                break;
            case 3:
                d = 0xffC1C110;
                break;
            case 4:
                d = 0xff5BB000;
                break;
            case 5:
                d = 0xff00D000;
                break;
            case 6:
                d = 0xff11C070;
                break;
            case 7:
                d = 0xff26D0D0;
                break;
            case 8:
                d = 0xff0080FF;
                break;
            case 9:
                d = 0xff6B6BFF;
                break;
            case 10:
                d = 0xffB870FF;
                break;
            case 11:
                d = 0xffFF00FF;
                break;
            case 12:
                d = 0xffFF0080;
                break;
            case 13:
                d = 0xffFF0040;
                break;
            case 14:
                d = 0xff848484;
                break;
            case 15:
                d = 0xffCCCCCC;
                break;
            default:
                d = 0xffFFFFFF;
                break;
        }

        return d;
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO getBackground">
    public static int getBackground(int categoria){
        int d;

        switch (categoria){
            case 1:
                d = R.drawable.pic_border_verbos_aux;
                break;
            case 2:
                d = R.drawable.pic_border_respuestas;
                break;
            case 3:
                d = R.drawable.pic_border_verbos;
                break;
            case 4:
                d = R.drawable.pic_border_bebidas;
                break;
            case 5:
                d = R.drawable.pic_border_frutas;
                break;
            case 6:
                d = R.drawable.pic_border_verduras;
                break;
            case 7:
                d = R.drawable.pic_border_comida;
                break;
            case 8:
                d = R.drawable.pic_border_postres;
                break;
            case 9:
                d = R.drawable.pic_border_alimentos;
                break;
            case 10:
                d = R.drawable.pic_border_animales;
                break;
            case 11:
                d = R.drawable.pic_border_objetos;
                break;
            case 12:
                d = R.drawable.pic_border_animo;
                break;
            case 13:
                d = R.drawable.pic_border_lugares;
                break;
            case 14:
                d = R.drawable.pic_border_familia;
                break;
            case 15:
                d = R.drawable.pic_border_profesiones;
                break;


            default:
                d = R.drawable.pic_border;
                break;
        }



        return d;
    }
    //</editor-fold>

}
