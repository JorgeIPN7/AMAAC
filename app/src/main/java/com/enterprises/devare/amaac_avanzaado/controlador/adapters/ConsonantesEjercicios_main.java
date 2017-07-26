package com.enterprises.devare.amaac_avanzaado.controlador.adapters;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.enterprises.devare.amaac_avanzaado.R;
import com.enterprises.devare.amaac_avanzaado.controlador.dialogos.SeccionTerminadaNivelDialogo;
import com.enterprises.devare.amaac_avanzaado.modelo.Pictograma;
import com.enterprises.devare.amaac_avanzaado.modelo.db.DBHelper;

import java.util.List;


import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_B;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_C;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_D;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_F;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_G;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_H;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_J;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_K;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_L;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_M;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_N;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_P;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_Q;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_R;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_S;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_T;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_V;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_W;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_X;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_Y;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES_Z;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POLISILABAS_BEBIDAS;


public class ConsonantesEjercicios_main extends AppCompatActivity {

    //<editor-fold desc="DECLARACIÓN DE VARIABLES">
    ConsonantesAdaptador adapter;
    private DBHelper db;
    private RecyclerView recycler_ejercicios;
    int nuevaPosicion;


    SharedPreferences preferences;
    boolean NIVEL_OK;


    //</editor-fold>

    //<editor-fold desc="MÉTODO CALLBACK onCreate()">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ejercicio_niveles);
        db = new DBHelper(this);

        recycler_ejercicios = (RecyclerView) findViewById(R.id.reciclador_ejercicio_niveles);
        InitAdapter(recycler_ejercicios, db.getCategoria_Pictogramas(CAT_CONSONANTES));
        //Comprueba si ya se completo este nivel
        preferences = getSharedPreferences("NIVEL_OK", MODE_PRIVATE);
        NIVEL_OK = preferences.getBoolean("nivel_consonantes_ok", false);

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO InitAdapter()">
    public void InitAdapter(RecyclerView mRecyclerView, List<Pictograma> items) {

         assert mRecyclerView != null;
         adapter = new ConsonantesAdaptador(items);

        setupRecyclerView(mRecyclerView, adapter);

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO setupRecyclerView()">
    private void setupRecyclerView(@NonNull RecyclerView recyclerView, ConsonantesAdaptador items) {

        recyclerView.setAdapter(items);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        }
        //recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //recyclerView.setLayoutManager( new GridLayoutManager(this, 3,GridLayoutManager.VERTICAL, false));
        //recyclerView.setLayoutManager( new GridLayoutManager(this, 3,GridLayoutManager.HORIZONTAL, false));
    }
    //</editor-fold>

    public class ConsonantesAdaptador extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Pictograma> mValues;

        //<editor-fold desc="CONSTRUCTOR VocalesAdaptador()">
        public ConsonantesAdaptador(List<Pictograma> mValues) {
            this.mValues = mValues;
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onCreateViewHolder()">
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_ejercicio_preestablecido, parent, false);
            return new ConsonantesViewHolder(view);

        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onBindViewHolder">
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            nuevaPosicion = position +1;
            final Pictograma object = mValues.get(position);
            final int habilitado = object.getHabilitado();
            final boolean habilitadoEjercicio = estadoPictograma(habilitado);
            final int completado = object.getCompletado();
            final boolean completadoEjercicio = estadoPictograma(completado);
            int resultado;
           // Toast.makeText(ConsonantesEjercicios_main.this, object.getProgreso()+"% "+object.getNombre(), Toast.LENGTH_SHORT).show();

            if (completadoEjercicio) {

                if (nuevaPosicion < mValues.size()) {
                    final Pictograma seteo = mValues.get(nuevaPosicion);
                    seteo.setHabilitado(1);
                    db.updatePictograma(seteo);
                } else {

                    if(!NIVEL_OK){
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("nivel_consonantes_ok", true);
                        editor.commit();

                        FragmentManager fragmentManager = getFragmentManager();
                        new SeccionTerminadaNivelDialogo().show(fragmentManager, "SeccionTerminadaNivelDialog");
                    }
                }
            }

            switch (object.getNombre()) {

                case "Bb":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_B);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_B) + "/"+db.count(CAT_CONSONANTES_B));
                    break;

                case "Cc":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_C);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_C) + "/"+db.count(CAT_CONSONANTES_C));
                    break;

                case "Dd":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_D);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_D) + "/"+db.count(CAT_CONSONANTES_D));
                    break;

                case "Ff":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_F);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_F) + "/"+db.count(CAT_CONSONANTES_F));
                    break;

                case "Gg":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_G);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_G) + "/"+db.count(CAT_CONSONANTES_G));
                    break;

                case "Hh":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_H);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_H) + "/"+db.count(CAT_CONSONANTES_H));
                    break;

                case "Jj":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_J);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_J) + "/"+db.count(CAT_CONSONANTES_J));
                    break;

                case "Kk":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_K);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_K) + "/"+db.count(CAT_CONSONANTES_K));
                    break;

                case "Ll":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_L);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_L) + "/"+db.count(CAT_CONSONANTES_L));
                    break;

                case "Mm":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_M);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_M) + "/"+db.count(CAT_CONSONANTES_M));
                    break;

                case "Nn":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_N);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_N) + "/"+db.count(CAT_CONSONANTES_N));
                    break;

                case "Pp":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_P);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_P) + "/"+db.count(CAT_CONSONANTES_P));
                    break;

                case "Qq":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_Q);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_Q) + "/"+db.count(CAT_CONSONANTES_Q));
                    break;

                case "Rr":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_R);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_R) + "/"+db.count(CAT_CONSONANTES_R));
                    break;

                case "Ss":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_S);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_S) + "/"+db.count(CAT_CONSONANTES_S));
                    break;

                case "Tt":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_T);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_T) + "/"+db.count(CAT_CONSONANTES_T));
                    break;

                case "Vv":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_V);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_V) + "/"+db.count(CAT_CONSONANTES_V));
                    break;

                case "Ww":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_W);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_W) + "/"+db.count(CAT_CONSONANTES_W));
                    break;

                case "Xx":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_X);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_X) + "/"+db.count(CAT_CONSONANTES_X));
                    break;

                case "Yy":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_Y);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_Y) + "/"+db.count(CAT_CONSONANTES_Y));
                    break;

                case "Zz":
                    resultado = db.obtenerProgreso(CAT_CONSONANTES_Z);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_CONSONANTES_Z) + "/"+db.count(CAT_CONSONANTES_Z));
                    break;

            }

            int progresoNivel = object.getProgreso();

            ((ConsonantesViewHolder) holder).tv_cv_ejercicio_vocal.setText(object.getNombre());
            ((ConsonantesViewHolder) holder).tv_cv_porcentaje_progreso.setText(progresoNivel + "%");
            ((ConsonantesViewHolder) holder).progressbar_nivel.setProgress(progresoNivel);

            if (habilitadoEjercicio) {
                ((ConsonantesViewHolder) holder).iv_cv_ejercicio_nivel_bloqueado.setVisibility(View.INVISIBLE);
                ((ConsonantesViewHolder) holder).cv_ejercicios_nivel.setEnabled(habilitadoEjercicio);
                ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setEnabled(habilitadoEjercicio);
                ((ConsonantesViewHolder) holder).tv_cv_ejercicio_vocal.setEnabled(habilitadoEjercicio);
                ((ConsonantesViewHolder) holder).tv_cv_porcentaje_progreso.setEnabled(habilitadoEjercicio);
                ((ConsonantesViewHolder) holder).progressbar_nivel.setEnabled(habilitadoEjercicio);
                ((ConsonantesViewHolder) holder).tv_cv_porcentaje_progreso.setVisibility(View.VISIBLE);
                ((ConsonantesViewHolder) holder).progressbar_nivel.setVisibility(View.VISIBLE);

            } else {
                ((ConsonantesViewHolder) holder).iv_cv_ejercicio_nivel_bloqueado.setVisibility(View.VISIBLE);

                ((ConsonantesViewHolder) holder).cv_ejercicios_nivel.setEnabled(habilitadoEjercicio);
                ((ConsonantesViewHolder) holder).tv_total_ejercicios_vocales.setEnabled(habilitadoEjercicio);
                ((ConsonantesViewHolder) holder).tv_cv_ejercicio_vocal.setEnabled(habilitadoEjercicio);
                ((ConsonantesViewHolder) holder).tv_cv_porcentaje_progreso.setEnabled(habilitadoEjercicio);
                ((ConsonantesViewHolder) holder).progressbar_nivel.setEnabled(habilitadoEjercicio);
                ((ConsonantesViewHolder) holder).tv_cv_porcentaje_progreso.setVisibility(View.INVISIBLE);
                ((ConsonantesViewHolder) holder).progressbar_nivel.setVisibility(View.INVISIBLE);

            }
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO estadoPictograma(int i">
        public boolean estadoPictograma(int i) {
            if (i == 1) {
                return true;
            } else {
                return false;
            }
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO getItemCount() ">
        @Override
        public int getItemCount() {
            return mValues.size();
        }
        //</editor-fold>

        public class ConsonantesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            CardView cv_ejercicios_nivel;
            ImageView iv_cv_ejercicio_nivel_bloqueado;
            private TextView tv_total_ejercicios_vocales,
                    tv_cv_ejercicio_vocal,
                    tv_cv_porcentaje_progreso;
            ProgressBar progressbar_nivel;


            public static final String CONSONANTE_SELECCIONADA = "com.enterprises.devare.amaac_avanzaado.controlador.adapters.consonanteSeleccionada";
            public static final String CONSONANTE_NIVEL = "com.enterprises.devare.amaac_avanzaado.controlador.adapters.consonanteNivel";

            public ConsonantesViewHolder(View itemView) {
                super(itemView);
                cv_ejercicios_nivel = (CardView) itemView.findViewById(R.id.cv_ejercicios_nivel);
                iv_cv_ejercicio_nivel_bloqueado = (ImageView) itemView.findViewById(R.id.iv_cv_ejercicio_nivel_bloqueado);
                tv_total_ejercicios_vocales = (TextView) itemView.findViewById(R.id.tv_total_ejercicios_vocales);
                tv_cv_ejercicio_vocal = (TextView) itemView.findViewById(R.id.tv_cv_ejercicio_vocal);
                tv_cv_porcentaje_progreso = (TextView) itemView.findViewById(R.id.tv_cv_porcentaje_progreso);
                progressbar_nivel = (ProgressBar) itemView.findViewById(R.id.progressbar_nivel);
                cv_ejercicios_nivel.setOnClickListener(this);
            }
            //</editor-fold>

            //<editor-fold desc="MÉTODO CONTROLADOR DE EVENTOS onClick(View v)">
            @Override
            public void onClick(View v) {

                int posicision = getAdapterPosition();
                final Pictograma object = mValues.get(posicision);
                Intent ejercicio;
                ejercicio = new Intent(getApplicationContext(), EjerciciosConsonantes.class);

                switch (object.getNombre()) {

                    case "Bb":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_B);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Cc":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_C);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Dd":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_D);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Ff":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_F);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Gg":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_G);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Hh":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_H);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Jj":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_J);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Kk":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_K);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Ll":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_L);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Mm":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_M);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Nn":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_N);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Pp":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_P);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Qq":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_Q);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Rr":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_R);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Ss":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_S);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Tt":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_T);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Vv":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_V);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Ww":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_W);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Xx":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_X);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Yy":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_Y);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Zz":
                        ejercicio.putExtra(CONSONANTE_SELECCIONADA, CAT_CONSONANTES_Z);
                        ejercicio.putExtra(CONSONANTE_NIVEL, object.getNombre());
                        startActivity(ejercicio);
                        break;

                }

            }
            //</editor-fold>

        }

    }

}
