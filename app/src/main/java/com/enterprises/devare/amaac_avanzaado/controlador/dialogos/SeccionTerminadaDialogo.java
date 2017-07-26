package com.enterprises.devare.amaac_avanzaado.controlador.dialogos;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.enterprises.devare.amaac_avanzaado.R;
import com.enterprises.devare.amaac_avanzaado.controlador.adapters.IniciarNivel_main;
import com.enterprises.devare.amaac_avanzaado.controlador.adapters.VocalesEjercicios_main;

/**
 * Created by Angel on 14/04/2017.
 */

public class SeccionTerminadaDialogo extends DialogFragment{

    public SeccionTerminadaDialogo() {
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createSeccionTerminadaDialogo();
    }


    public AlertDialog createSeccionTerminadaDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialogo_seccion_terminada, null);

        builder.setView(v);

        Button btn_continuar_nivel = (Button) v.findViewById(R.id.btn_continuar_nivel);

        btn_continuar_nivel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Ir al siguiente nivel

                        Intent i= new Intent(getActivity(), VocalesEjercicios_main.class);
                        startActivity(i);
                        dismiss();
                    }
                }
        );

        return builder.create();
    }

}
