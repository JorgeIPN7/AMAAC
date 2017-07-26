package com.enterprises.devare.amaac_avanzaado.controlador.adapters;

import android.app.FragmentManager;
import android.content.Context;
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
import com.enterprises.devare.amaac_avanzaado.modelo.db.DBHelper;
import com.enterprises.devare.amaac_avanzaado.modelo.db.DataManager;
import com.enterprises.devare.amaac_avanzaado.modelo.Pictograma;

import java.util.List;

import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POLISILABAS_BEBIDAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCALES;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCAL_A;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCAL_E;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCAL_I;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCAL_O;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCAL_U;

public class VocalesEjercicios_main extends AppCompatActivity {

    //<editor-fold desc="DECLARACIÓN DE VARIABLES">
    VocalesAdaptador adapter;
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
        InitAdapter(recycler_ejercicios, db.getCategoria_Pictogramas(CAT_VOCALES));

//Comprueba si ya se completo este nivel
        preferences = getSharedPreferences("NIVEL_OK", MODE_PRIVATE);
        NIVEL_OK = preferences.getBoolean("nivel_ok", false);


    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO iniciarDatos_Vocales_main() CARGA DE DATOS">
    public void iniciarDatos_Vocales_main(Context contexto) {
        DataManager datos = new DataManager();
        datos.Init_Contenido_niveles(contexto);
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO InitAdapter()">
    public void InitAdapter(RecyclerView mRecyclerView, List<Pictograma> items) {

        assert mRecyclerView != null;
        adapter = new VocalesAdaptador(items);

        setupRecyclerView(mRecyclerView, adapter);

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO setupRecyclerView()">
    private void setupRecyclerView(@NonNull RecyclerView recyclerView, VocalesAdaptador items) {

        recyclerView.setAdapter(items);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        }
        //recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //recyclerView.setLayoutManager( new GridLayoutManager(this, 3,GridLayoutManager.VERTICAL, false));
        //recyclerView.setLayoutManager( new GridLayoutManager(this, 3,GridLayoutManager.HORIZONTAL, false));
    }
    //</editor-fold>

    public class VocalesAdaptador extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Pictograma> mValues;

        //<editor-fold desc="CONSTRUCTOR VocalesAdaptador()">
        public VocalesAdaptador(List<Pictograma> mValues) {
            this.mValues = mValues;
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onCreateViewHolder()">
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_ejercicio_preestablecido, parent, false);
            return new VocalesViewHolder(view);

        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onBindViewHolder">
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            nuevaPosicion=position+1;
            final Pictograma object = mValues.get(position);
            final int habilitado=object.getHabilitado();
            final boolean habilitadoEjercicio =estadoPictograma(habilitado);
            final int completado=object.getCompletado();
            final boolean completadoEjercicio=estadoPictograma(completado);
            int resultado;


            //<editor-fold desc="SI EL NIVEL VOCAL FUE COMPLETADO SE HABILITA EL SIGUIENTE NIVEL">
            if (completadoEjercicio) {

                if (nuevaPosicion<mValues.size()){
                    final Pictograma seteo = mValues.get(nuevaPosicion);
                    seteo.setHabilitado(1);
                    db.updatePictograma(seteo);
                }else{

                    if(!NIVEL_OK){
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("nivel_ok", true);
                        editor.commit();

                        FragmentManager fragmentManager = getFragmentManager();
                        new SeccionTerminadaNivelDialogo().show(fragmentManager, "SeccionTerminadaNivelDialog");
                    }




                }
            }
            //</editor-fold>

            switch (object.getNombre()) {

                case "Aa":
                    resultado=db.obtenerProgreso(CAT_VOCAL_A);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((VocalesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_VOCAL_A)+"/"+db.count(CAT_VOCAL_A));
                    break;

                case "Ee":
                    resultado=db.obtenerProgreso(CAT_VOCAL_E);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((VocalesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_VOCAL_E)+"/"+db.count(CAT_VOCAL_E));
                    break;

                case "Ii":
                    resultado=db.obtenerProgreso(CAT_VOCAL_I);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((VocalesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_VOCAL_I)+"/"+db.count(CAT_VOCAL_I));
                    break;

                case "Oo":
                    resultado=db.obtenerProgreso(CAT_VOCAL_O);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((VocalesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_VOCAL_O)+"/"+db.count(CAT_VOCAL_O));
                    break;

                case "Uu":
                    resultado=db.obtenerProgreso(CAT_VOCAL_U);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((VocalesViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_VOCAL_U)+"/"+db.count(CAT_VOCAL_U));
                    break;

            }
            int progresoNivel=object.getProgreso();

            ((VocalesViewHolder) holder).tv_cv_ejercicio_vocal.setText(object.getNombre());
            ((VocalesViewHolder) holder).tv_cv_porcentaje_progreso.setText(progresoNivel+"%");
            ((VocalesViewHolder) holder).progressbar_nivel.setProgress(progresoNivel);


           if (habilitadoEjercicio){
                ((VocalesViewHolder) holder).iv_cv_ejercicio_nivel_bloqueado.setVisibility(View.INVISIBLE);
                ((VocalesViewHolder) holder).cv_ejercicios_nivel.setEnabled(habilitadoEjercicio);
                ((VocalesViewHolder) holder).tv_total_ejercicios_vocales.setEnabled(habilitadoEjercicio);
                ((VocalesViewHolder) holder).tv_cv_ejercicio_vocal.setEnabled(habilitadoEjercicio);
                ((VocalesViewHolder) holder).tv_cv_porcentaje_progreso.setEnabled(habilitadoEjercicio);
                ((VocalesViewHolder) holder).progressbar_nivel.setEnabled(habilitadoEjercicio);
                ((VocalesViewHolder) holder).tv_cv_porcentaje_progreso.setVisibility(View.VISIBLE);
                ((VocalesViewHolder) holder).progressbar_nivel.setVisibility(View.VISIBLE);

            }else{
                ((VocalesViewHolder) holder).iv_cv_ejercicio_nivel_bloqueado.setVisibility(View.VISIBLE);

                ((VocalesViewHolder) holder).cv_ejercicios_nivel.setEnabled(habilitadoEjercicio);
                ((VocalesViewHolder) holder).tv_total_ejercicios_vocales.setEnabled(habilitadoEjercicio);
                ((VocalesViewHolder) holder).tv_cv_ejercicio_vocal.setEnabled(habilitadoEjercicio);
                ((VocalesViewHolder) holder).tv_cv_porcentaje_progreso.setEnabled(habilitadoEjercicio);
                ((VocalesViewHolder) holder).progressbar_nivel.setEnabled(habilitadoEjercicio);
                ((VocalesViewHolder) holder).tv_cv_porcentaje_progreso.setVisibility(View.INVISIBLE);
                ((VocalesViewHolder) holder).progressbar_nivel.setVisibility(View.INVISIBLE);

            }
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO estadoPictograma(int i">
        public boolean  estadoPictograma(int i){
            if(i==1){
                return  true;
            }else{
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

        public class VocalesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            CardView cv_ejercicios_nivel;
            ImageView iv_cv_ejercicio_nivel_bloqueado;
            private TextView tv_total_ejercicios_vocales,
                    tv_cv_ejercicio_vocal,
                    tv_cv_porcentaje_progreso;
            ProgressBar progressbar_nivel;


            public static final String VOCAL_SELECCIONADA="com.enterprises.devare.amaac_avanzaado.controlador.adapters.vocalSeleccionada";
            public static final String VOCAL_NIVEL="com.enterprises.devare.amaac_avanzaado.controlador.adapters.vocalNivel";

            public VocalesViewHolder(View itemView) {
                super(itemView);
                cv_ejercicios_nivel= (CardView) itemView.findViewById(R.id.cv_ejercicios_nivel);
                iv_cv_ejercicio_nivel_bloqueado=(ImageView) itemView.findViewById(R.id.iv_cv_ejercicio_nivel_bloqueado) ;
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
                ejercicio = new Intent(getApplicationContext(), Ejercicios.class);

                switch (object.getNombre()) {

                    case "Aa":
                        ejercicio.putExtra(VOCAL_SELECCIONADA,CAT_VOCAL_A);
                        ejercicio.putExtra(VOCAL_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Ee":
                        ejercicio.putExtra(VOCAL_SELECCIONADA,CAT_VOCAL_E);
                        ejercicio.putExtra(VOCAL_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Ii":
                        ejercicio.putExtra(VOCAL_SELECCIONADA,CAT_VOCAL_I);
                        ejercicio.putExtra(VOCAL_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Oo":
                        ejercicio.putExtra(VOCAL_SELECCIONADA,CAT_VOCAL_O);
                        ejercicio.putExtra(VOCAL_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Uu":
                        ejercicio.putExtra(VOCAL_SELECCIONADA,CAT_VOCAL_U);
                        ejercicio.putExtra(VOCAL_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                }

            }
            //</editor-fold>

        }

    }

}
