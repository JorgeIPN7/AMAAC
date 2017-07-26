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
import android.util.Log;
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
import com.enterprises.devare.amaac_avanzaado.modelo.db.DataManager;

import java.util.List;

import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_BISILABAS_ESTADOS_ANIMO;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_MONOSILABAS_ANIMALES;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POLISILABAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POLISILABAS_ANIMALES;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POLISILABAS_BEBIDAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POLISILABAS_COMIDA;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POLISILABAS_ESTADOS_ANIMO;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POLISILABAS_FAMILIA;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POLISILABAS_FRUTAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POLISILABAS_OBJETOS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POLISILABAS_VERBOS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCALES;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCAL_A;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCAL_E;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCAL_I;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCAL_O;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCAL_U;

public class PolisilabasEjercicios_main extends AppCompatActivity {

    //<editor-fold desc="DECLARACIÓN DE VARIABLES">
    PolisilabasAdaptador adapter;
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
        InitAdapter(recycler_ejercicios, db.getCategoria_Pictogramas(CAT_POLISILABAS));

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
        adapter = new PolisilabasAdaptador(items);

        setupRecyclerView(mRecyclerView, adapter);

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO setupRecyclerView()">
    private void setupRecyclerView(@NonNull RecyclerView recyclerView, PolisilabasAdaptador items) {

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

    public class PolisilabasAdaptador extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Pictograma> mValues;

        //<editor-fold desc="CONSTRUCTOR VocalesAdaptador()">
        public PolisilabasAdaptador(List<Pictograma> mValues) {
            this.mValues = mValues;
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onCreateViewHolder()">
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_ejercicio_preestablecido_imagen, parent, false);
            return new PolisilabasViewHolder(view);

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
                case "Animales III":
                    resultado=db.obtenerProgreso(CAT_POLISILABAS_ANIMALES);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((PolisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_POLISILABAS_ANIMALES)+"/"+db.count(CAT_POLISILABAS_ANIMALES));
                    ((PolisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_animales );
                    break;


                case "Comida III":
                    resultado=db.obtenerProgreso(CAT_POLISILABAS_COMIDA);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((PolisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_POLISILABAS_COMIDA)+"/"+db.count(CAT_POLISILABAS_COMIDA));
                    ((PolisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_comida );
                    break;


                case "Familia III":
                    resultado=db.obtenerProgreso(CAT_POLISILABAS_FAMILIA);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((PolisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_POLISILABAS_FAMILIA)+"/"+db.count(CAT_POLISILABAS_FAMILIA));
                    ((PolisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_familia );
                    break;

                case "Objetos III":
                    resultado=db.obtenerProgreso(CAT_POLISILABAS_OBJETOS);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((PolisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_POLISILABAS_OBJETOS)+"/"+db.count(CAT_POLISILABAS_OBJETOS));
                    ((PolisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_objetos );
                    break;

                case "Animo III":
                    resultado=db.obtenerProgreso(CAT_POLISILABAS_ESTADOS_ANIMO);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((PolisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_POLISILABAS_ESTADOS_ANIMO)+"/"+db.count(CAT_POLISILABAS_ESTADOS_ANIMO));
                    ((PolisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_animo );
                    break;

                case "Verbos III":
                    resultado=db.obtenerProgreso(CAT_POLISILABAS_VERBOS);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((PolisilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_POLISILABAS_VERBOS)+"/"+db.count(CAT_POLISILABAS_VERBOS));
                    ((PolisilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_verbos );
                    break;








            }
            int progresoNivel=object.getProgreso();

            ((PolisilabasViewHolder) holder).tv_cv_ejercicio_vocal.setText(object.getNombre());
            ((PolisilabasViewHolder) holder).tv_cv_ejercicio_vocal.setTextSize(25);
            ((PolisilabasViewHolder) holder).tv_cv_porcentaje_progreso.setText(progresoNivel+"%");
            ((PolisilabasViewHolder) holder).progressbar_nivel.setProgress(progresoNivel);


           if (habilitadoEjercicio){
                ((PolisilabasViewHolder) holder).iv_cv_ejercicio_nivel_bloqueado.setVisibility(View.INVISIBLE);
                ((PolisilabasViewHolder) holder).cv_ejercicios_nivel.setEnabled(habilitadoEjercicio);
                ((PolisilabasViewHolder) holder).tv_total_ejercicios_vocales.setEnabled(habilitadoEjercicio);
                ((PolisilabasViewHolder) holder).tv_cv_ejercicio_vocal.setEnabled(habilitadoEjercicio);
                ((PolisilabasViewHolder) holder).tv_cv_porcentaje_progreso.setEnabled(habilitadoEjercicio);
                ((PolisilabasViewHolder) holder).progressbar_nivel.setEnabled(habilitadoEjercicio);
                ((PolisilabasViewHolder) holder).tv_cv_porcentaje_progreso.setVisibility(View.VISIBLE);
                ((PolisilabasViewHolder) holder).progressbar_nivel.setVisibility(View.VISIBLE);

            }else{
                ((PolisilabasViewHolder) holder).iv_cv_ejercicio_nivel_bloqueado.setVisibility(View.VISIBLE);

                ((PolisilabasViewHolder) holder).cv_ejercicios_nivel.setEnabled(habilitadoEjercicio);
                ((PolisilabasViewHolder) holder).tv_total_ejercicios_vocales.setEnabled(habilitadoEjercicio);
                ((PolisilabasViewHolder) holder).tv_cv_ejercicio_vocal.setEnabled(habilitadoEjercicio);
                ((PolisilabasViewHolder) holder).tv_cv_porcentaje_progreso.setEnabled(habilitadoEjercicio);
                ((PolisilabasViewHolder) holder).progressbar_nivel.setEnabled(habilitadoEjercicio);
                ((PolisilabasViewHolder) holder).tv_cv_porcentaje_progreso.setVisibility(View.INVISIBLE);
                ((PolisilabasViewHolder) holder).progressbar_nivel.setVisibility(View.INVISIBLE);

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

        public class PolisilabasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            CardView cv_ejercicios_nivel;
            ImageView iv_cv_ejercicio_nivel_bloqueado, iv_cv_ejercicio;
            private TextView tv_total_ejercicios_vocales,
                    tv_cv_ejercicio_vocal,
                    tv_cv_porcentaje_progreso;
            ProgressBar progressbar_nivel;

            public static final String POLISILABA_SELECCIONADA="com.enterprises.devare.amaac_avanzaado.controlador.adapters.polisilabaSeleccionada";
            public static final String POLISILABA_NIVEL="com.enterprises.devare.amaac_avanzaado.controlador.adapters.polisilabaNivel";

            public PolisilabasViewHolder(View itemView) {
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
                ejercicio = new Intent(getApplicationContext(), EjerciciosPolisilabas.class);

                switch (object.getNombre()) {


                    case "Animales III":
                        ejercicio.putExtra(POLISILABA_SELECCIONADA,CAT_POLISILABAS_ANIMALES);
                        ejercicio.putExtra(POLISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Bebidas III":
                        ejercicio.putExtra(POLISILABA_SELECCIONADA,CAT_POLISILABAS_BEBIDAS);
                        ejercicio.putExtra(POLISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Comida III":
                        ejercicio.putExtra(POLISILABA_SELECCIONADA,CAT_POLISILABAS_COMIDA);
                        ejercicio.putExtra(POLISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Familia III":
                        ejercicio.putExtra(POLISILABA_SELECCIONADA,CAT_POLISILABAS_FAMILIA);
                        ejercicio.putExtra(POLISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Objetos III":
                        ejercicio.putExtra(POLISILABA_SELECCIONADA,CAT_POLISILABAS_OBJETOS);
                        ejercicio.putExtra(POLISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Animo III":
                        ejercicio.putExtra(POLISILABA_SELECCIONADA,CAT_POLISILABAS_ESTADOS_ANIMO);
                        ejercicio.putExtra(POLISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Verbos III":
                        ejercicio.putExtra(POLISILABA_SELECCIONADA,CAT_POLISILABAS_VERBOS);
                        ejercicio.putExtra(POLISILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                }









            }
            //</editor-fold>

        }

    }

}
