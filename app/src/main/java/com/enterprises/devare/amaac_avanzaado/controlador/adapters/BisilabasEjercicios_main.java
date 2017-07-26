package com.enterprises.devare.amaac_avanzaado.controlador.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
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

import com.enterprises.devare.amaac_avanzaado.R;
import com.enterprises.devare.amaac_avanzaado.controlador.dialogos.SeccionTerminadaNivelDialogo;
import com.enterprises.devare.amaac_avanzaado.modelo.Pictograma;
import com.enterprises.devare.amaac_avanzaado.modelo.db.DBHelper;

import java.util.List;

import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_BISILABAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_BISILABAS_ANIMALES;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_BISILABAS_BEBIDAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_BISILABAS_COMIDA;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_BISILABAS_ESTADOS_ANIMO;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_BISILABAS_FAMILIA;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_BISILABAS_OBJETOS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_BISILABAS_VERBOS;

public class BisilabasEjercicios_main extends AppCompatActivity {

    //<editor-fold desc="DECLARACIÓN DE VARIABLES">
    BisilabasAdaptador adapter;
    private DBHelper db;
    private RecyclerView recycler_ejercicios;
    int nuevaPosicion;

    //</editor-fold>

    //<editor-fold desc="MÉTODO CALLBACK onCreate()">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ejercicio_niveles);
        db = new DBHelper(this);

        recycler_ejercicios = (RecyclerView) findViewById(R.id.reciclador_ejercicio_niveles);
        InitAdapter(recycler_ejercicios, db.getCategoria_Pictogramas(CAT_BISILABAS));

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO InitAdapter()">
    public void InitAdapter(RecyclerView mRecyclerView, List<Pictograma> items) {

        assert mRecyclerView != null;
        adapter = new BisilabasAdaptador(items);

        setupRecyclerView(mRecyclerView, adapter);

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO setupRecyclerView()">
    private void setupRecyclerView(@NonNull RecyclerView recyclerView, BisilabasAdaptador items) {

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

    public class BisilabasAdaptador extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Pictograma> mValues;

        //<editor-fold desc="CONSTRUCTOR VocalesAdaptador()">
        public BisilabasAdaptador(List<Pictograma> mValues) {
            this.mValues = mValues;
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onCreateViewHolder()">
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_ejercicio_preestablecido_imagen, parent, false);
            return new BisilabasViewHolder(view);

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
                    FragmentManager fragmentManager = getFragmentManager();
                    new SeccionTerminadaNivelDialogo().show(fragmentManager, "SeccionTerminadaNivelDialog");
                }
            }
            //</editor-fold>

            switch (object.getNombre()) {

                case "Animales II":
                    resultado=db.obtenerProgreso(CAT_BISILABAS_ANIMALES);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((BisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_BISILABAS_ANIMALES)+"/"+db.count(CAT_BISILABAS_ANIMALES));
                    ((BisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_animales );
                    break;

                case "Bebidas II":
                    resultado=db.obtenerProgreso(CAT_BISILABAS_BEBIDAS);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((BisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_BISILABAS_BEBIDAS)+"/"+db.count(CAT_BISILABAS_BEBIDAS));
                    ((BisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_comida );
                    break;

                case "Comida II":
                    resultado=db.obtenerProgreso(CAT_BISILABAS_COMIDA);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((BisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_BISILABAS_COMIDA)+"/"+db.count(CAT_BISILABAS_COMIDA));
                    ((BisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_comida );
                    break;


                case "Familia II":
                    resultado=db.obtenerProgreso(CAT_BISILABAS_FAMILIA);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((BisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_BISILABAS_FAMILIA)+"/"+db.count(CAT_BISILABAS_FAMILIA));
                    ((BisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_familia );
                    break;

                case "Objetos II":
                    resultado=db.obtenerProgreso(CAT_BISILABAS_OBJETOS);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((BisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_BISILABAS_OBJETOS)+"/"+db.count(CAT_BISILABAS_OBJETOS));
                    ((BisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_objetos );
                    break;

                case "Animo II":
                    resultado=db.obtenerProgreso(CAT_BISILABAS_ESTADOS_ANIMO);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((BisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_BISILABAS_ESTADOS_ANIMO)+"/"+db.count(CAT_BISILABAS_ESTADOS_ANIMO));
                    ((BisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_animo );
                    break;

                case "Verbos II":
                    resultado=db.obtenerProgreso(CAT_BISILABAS_VERBOS);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((BisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_BISILABAS_VERBOS)+"/"+db.count(CAT_BISILABAS_VERBOS));
                    ((BisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_verbos );
                    break;









            }
            int progresoNivel=object.getProgreso();

            ((BisilabasViewHolder) holder).tv_cv_ejercicio_vocal.setText(object.getNombre());
            ((BisilabasViewHolder) holder).tv_cv_ejercicio_vocal.setTextSize(25);
            ((BisilabasViewHolder) holder).tv_cv_porcentaje_progreso.setText(progresoNivel+"%");
            ((BisilabasViewHolder) holder).progressbar_nivel.setProgress(progresoNivel);


           if (habilitadoEjercicio){
                ((BisilabasViewHolder) holder).iv_cv_ejercicio_nivel_bloqueado.setVisibility(View.INVISIBLE);
                ((BisilabasViewHolder) holder).cv_ejercicios_nivel.setEnabled(habilitadoEjercicio);
                ((BisilabasViewHolder) holder).tv_total_ejercicios_vocales.setEnabled(habilitadoEjercicio);
                ((BisilabasViewHolder) holder).tv_cv_ejercicio_vocal.setEnabled(habilitadoEjercicio);
                ((BisilabasViewHolder) holder).tv_cv_porcentaje_progreso.setEnabled(habilitadoEjercicio);
                ((BisilabasViewHolder) holder).progressbar_nivel.setEnabled(habilitadoEjercicio);
                ((BisilabasViewHolder) holder).tv_cv_porcentaje_progreso.setVisibility(View.VISIBLE);
                ((BisilabasViewHolder) holder).progressbar_nivel.setVisibility(View.VISIBLE);

            }else{
                ((BisilabasViewHolder) holder).iv_cv_ejercicio_nivel_bloqueado.setVisibility(View.VISIBLE);

                ((BisilabasViewHolder) holder).cv_ejercicios_nivel.setEnabled(habilitadoEjercicio);
                ((BisilabasViewHolder) holder).tv_total_ejercicios_vocales.setEnabled(habilitadoEjercicio);
                ((BisilabasViewHolder) holder).tv_cv_ejercicio_vocal.setEnabled(habilitadoEjercicio);
                ((BisilabasViewHolder) holder).tv_cv_porcentaje_progreso.setEnabled(habilitadoEjercicio);
                ((BisilabasViewHolder) holder).progressbar_nivel.setEnabled(habilitadoEjercicio);
                ((BisilabasViewHolder) holder).tv_cv_porcentaje_progreso.setVisibility(View.INVISIBLE);
                ((BisilabasViewHolder) holder).progressbar_nivel.setVisibility(View.INVISIBLE);

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

        public class BisilabasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            CardView cv_ejercicios_nivel;
            ImageView iv_cv_ejercicio_nivel_bloqueado, iv_cv_ejercicio;
            private TextView tv_total_ejercicios_vocales,
                    tv_cv_ejercicio_vocal,
                    tv_cv_porcentaje_progreso;
            ProgressBar progressbar_nivel;


            public static final String BISILABA_SELECCIONADA="com.enterprises.devare.amaac_avanzaado.controlador.adapters.bisilabaSeleccionada";
            public static final String BISILABA_NIVEL="com.enterprises.devare.amaac_avanzaado.controlador.adapters.bisilabaNivel";

            public BisilabasViewHolder(View itemView) {
                super(itemView);
                cv_ejercicios_nivel= (CardView) itemView.findViewById(R.id.cv_ejercicios_nivel);
                iv_cv_ejercicio_nivel_bloqueado=(ImageView) itemView.findViewById(R.id.iv_cv_ejercicio_nivel_bloqueado) ;
                iv_cv_ejercicio=(ImageView) itemView.findViewById(R.id.iv_cv_ejercicio) ;


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
                ejercicio = new Intent(getApplicationContext(), EjerciciosBisilabas.class);

                switch (object.getNombre()) {

                    case "Animales II":
                        ejercicio.putExtra(BISILABA_SELECCIONADA,CAT_BISILABAS_ANIMALES);
                        ejercicio.putExtra(BISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Comida II":
                        ejercicio.putExtra(BISILABA_SELECCIONADA,CAT_BISILABAS_COMIDA);
                        ejercicio.putExtra(BISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Familia II":
                        ejercicio.putExtra(BISILABA_SELECCIONADA,CAT_BISILABAS_FAMILIA);
                        ejercicio.putExtra(BISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Objetos II":
                        ejercicio.putExtra(BISILABA_SELECCIONADA,CAT_BISILABAS_OBJETOS);
                        ejercicio.putExtra(BISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Animo II":
                        ejercicio.putExtra(BISILABA_SELECCIONADA,CAT_BISILABAS_ESTADOS_ANIMO);
                        ejercicio.putExtra(BISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Verbos II":
                        ejercicio.putExtra(BISILABA_SELECCIONADA,CAT_BISILABAS_VERBOS);
                        ejercicio.putExtra(BISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;











                }

            }
            //</editor-fold>

        }

    }

}
