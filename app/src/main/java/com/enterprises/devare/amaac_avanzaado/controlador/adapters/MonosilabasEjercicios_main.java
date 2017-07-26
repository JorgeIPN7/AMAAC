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
import com.enterprises.devare.amaac_avanzaado.modelo.db.DataManager;

import java.util.List;

import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_MONOSILABAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_MONOSILABAS_ANIMALES;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_MONOSILABAS_BEBIDAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_MONOSILABAS_COMIDA;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_MONOSILABAS_FAMILIA;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_MONOSILABAS_OBJETOS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_MONOSILABAS_RESPUESTAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_MONOSILABAS_VERBOS;

public class MonosilabasEjercicios_main extends AppCompatActivity {

    //<editor-fold desc="DECLARACIÓN DE VARIABLES">
    MonosilabasAdaptador adapter;
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
        InitAdapter(recycler_ejercicios, db.getCategoria_Pictogramas(CAT_MONOSILABAS));

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
        adapter = new MonosilabasAdaptador(items);

        setupRecyclerView(mRecyclerView, adapter);

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO setupRecyclerView()">
    private void setupRecyclerView(@NonNull RecyclerView recyclerView, MonosilabasAdaptador items) {

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

    public class MonosilabasAdaptador extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Pictograma> mValues;

        //<editor-fold desc="CONSTRUCTOR VocalesAdaptador()">
        public MonosilabasAdaptador(List<Pictograma> mValues) {
            this.mValues = mValues;
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onCreateViewHolder()">
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_ejercicio_preestablecido_imagen, parent, false);
            return new MonosilabasViewHolder(view);

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

                case "Animales I":
                    resultado=db.obtenerProgreso(CAT_MONOSILABAS_ANIMALES);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((MonosilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_MONOSILABAS_ANIMALES)+"/"+db.count(CAT_MONOSILABAS_ANIMALES));
                    ((MonosilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_animales );
                    break;

                case "Bebidas I":
                    resultado=db.obtenerProgreso(CAT_MONOSILABAS_BEBIDAS);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((MonosilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_MONOSILABAS_BEBIDAS)+"/"+db.count(CAT_MONOSILABAS_BEBIDAS));
                    ((MonosilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_comida );
                    break;

                case "Comida I":
                    resultado=db.obtenerProgreso(CAT_MONOSILABAS_COMIDA);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((MonosilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_MONOSILABAS_COMIDA)+"/"+db.count(CAT_MONOSILABAS_COMIDA));
                    ((MonosilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_comida );
                    break;

                case "Familia I":
                    resultado=db.obtenerProgreso(CAT_MONOSILABAS_FAMILIA);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((MonosilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_MONOSILABAS_FAMILIA)+"/"+db.count(CAT_MONOSILABAS_FAMILIA));
                    ((MonosilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_familia );
                    break;

                case "Objetos I":
                    resultado=db.obtenerProgreso(CAT_MONOSILABAS_OBJETOS);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((MonosilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_MONOSILABAS_OBJETOS)+"/"+db.count(CAT_MONOSILABAS_OBJETOS));
                    ((MonosilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_objetos );
                    break;

                case "Respuestas I":
                    resultado=db.obtenerProgreso(CAT_MONOSILABAS_RESPUESTAS);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((MonosilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_MONOSILABAS_RESPUESTAS)+"/"+db.count(CAT_MONOSILABAS_RESPUESTAS));
                    ((MonosilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_respuesta );
                    break;

                case "Verbos I":
                    resultado=db.obtenerProgreso(CAT_MONOSILABAS_VERBOS);
                    object.setProgreso(resultado);
                    db.updatePictograma(object);
                    ((MonosilabasViewHolder) holder).tv_total_ejercicios_vocales.setText(db.ejerciciosCompletos(CAT_MONOSILABAS_VERBOS)+"/"+db.count(CAT_MONOSILABAS_VERBOS));
                    ((MonosilabasViewHolder) holder).iv_cv_ejercicio.setImageResource( R.drawable.ic_seccion_verbos );
                    break;

            }
            int progresoNivel=object.getProgreso();

            ((MonosilabasViewHolder) holder).tv_cv_ejercicio_vocal.setText(object.getNombre());
            ((MonosilabasViewHolder) holder).tv_cv_ejercicio_vocal.setTextSize(25);
            ((MonosilabasViewHolder) holder).tv_cv_porcentaje_progreso.setText(progresoNivel+"%");
            ((MonosilabasViewHolder) holder).progressbar_nivel.setProgress(progresoNivel);


           if (habilitadoEjercicio){
                ((MonosilabasViewHolder) holder).iv_cv_ejercicio_nivel_bloqueado.setVisibility(View.INVISIBLE);
                ((MonosilabasViewHolder) holder).cv_ejercicios_nivel.setEnabled(habilitadoEjercicio);
                ((MonosilabasViewHolder) holder).tv_total_ejercicios_vocales.setEnabled(habilitadoEjercicio);
                ((MonosilabasViewHolder) holder).tv_cv_ejercicio_vocal.setEnabled(habilitadoEjercicio);
                ((MonosilabasViewHolder) holder).tv_cv_porcentaje_progreso.setEnabled(habilitadoEjercicio);
                ((MonosilabasViewHolder) holder).progressbar_nivel.setEnabled(habilitadoEjercicio);
                ((MonosilabasViewHolder) holder).tv_cv_porcentaje_progreso.setVisibility(View.VISIBLE);
                ((MonosilabasViewHolder) holder).progressbar_nivel.setVisibility(View.VISIBLE);

            }else{
                ((MonosilabasViewHolder) holder).iv_cv_ejercicio_nivel_bloqueado.setVisibility(View.VISIBLE);

                ((MonosilabasViewHolder) holder).cv_ejercicios_nivel.setEnabled(habilitadoEjercicio);
                ((MonosilabasViewHolder) holder).tv_total_ejercicios_vocales.setEnabled(habilitadoEjercicio);
                ((MonosilabasViewHolder) holder).tv_cv_ejercicio_vocal.setEnabled(habilitadoEjercicio);
                ((MonosilabasViewHolder) holder).tv_cv_porcentaje_progreso.setEnabled(habilitadoEjercicio);
                ((MonosilabasViewHolder) holder).progressbar_nivel.setEnabled(habilitadoEjercicio);
                ((MonosilabasViewHolder) holder).tv_cv_porcentaje_progreso.setVisibility(View.INVISIBLE);
                ((MonosilabasViewHolder) holder).progressbar_nivel.setVisibility(View.INVISIBLE);

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

        public class MonosilabasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            CardView cv_ejercicios_nivel;
            ImageView iv_cv_ejercicio_nivel_bloqueado, iv_cv_ejercicio;
            private TextView tv_total_ejercicios_vocales,
                    tv_cv_ejercicio_vocal,
                    tv_cv_porcentaje_progreso;
            ProgressBar progressbar_nivel;


            public static final String MONOSILABA_SELECCIONADA="com.enterprises.devare.amaac_avanzaado.controlador.adapters.monosilabaSeleccionada";
            public static final String MONOSILABA_NIVEL="com.enterprises.devare.amaac_avanzaado.controlador.adapters.monosilabaNivel";

            public MonosilabasViewHolder(View itemView) {
                super(itemView);
                cv_ejercicios_nivel= (CardView) itemView.findViewById(R.id.cv_ejercicios_nivel);
                iv_cv_ejercicio_nivel_bloqueado=(ImageView) itemView.findViewById(R.id.iv_cv_ejercicio_nivel_bloqueado) ;
                iv_cv_ejercicio= (ImageView)itemView.findViewById(R.id.iv_cv_ejercicio);
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
                ejercicio = new Intent(getApplicationContext(), EjerciciosMonosilabas.class);

                switch (object.getNombre()) {

                    case "Animales I":
                        ejercicio.putExtra(MONOSILABA_SELECCIONADA,CAT_MONOSILABAS_ANIMALES);
                        ejercicio.putExtra(MONOSILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Bebidas I":
                        ejercicio.putExtra(MONOSILABA_SELECCIONADA,CAT_MONOSILABAS_BEBIDAS);
                        ejercicio.putExtra(MONOSILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Comida I":
                        ejercicio.putExtra(MONOSILABA_SELECCIONADA,CAT_MONOSILABAS_COMIDA);
                        ejercicio.putExtra(MONOSILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Familia I":
                        ejercicio.putExtra(MONOSILABA_SELECCIONADA,CAT_MONOSILABAS_FAMILIA);
                        ejercicio.putExtra(MONOSILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Objetos I":
                        ejercicio.putExtra(MONOSILABA_SELECCIONADA,CAT_MONOSILABAS_OBJETOS);
                        ejercicio.putExtra(MONOSILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Respuestas I":
                        ejercicio.putExtra(MONOSILABA_SELECCIONADA,CAT_MONOSILABAS_RESPUESTAS);
                        ejercicio.putExtra(MONOSILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                    case "Verbos I":
                        ejercicio.putExtra(MONOSILABA_SELECCIONADA,CAT_MONOSILABAS_VERBOS);
                        ejercicio.putExtra(MONOSILABA_NIVEL,object.getNombre());
                        startActivity(ejercicio);
                        break;

                }

            }
            //</editor-fold>

        }

    }

}
