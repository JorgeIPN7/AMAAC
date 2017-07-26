package com.enterprises.devare.amaac_avanzaado.controlador.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import com.enterprises.devare.amaac_avanzaado.R;
import com.enterprises.devare.amaac_avanzaado.modelo.db.DBHelper;
import com.enterprises.devare.amaac_avanzaado.modelo.db.DataManager;
import com.enterprises.devare.amaac_avanzaado.modelo.metodos.MethodsManager;
import com.enterprises.devare.amaac_avanzaado.modelo.Nivel;

import java.util.List;
import java.util.Locale;

import static com.enterprises.devare.amaac_avanzaado.modelo.Nivel.VISTA_NORMAL;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_MONOSILABAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POLISILABAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VOCALES;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_CONSONANTES;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_BISILABAS;


public class IniciarNivel_main extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //<editor-fold desc="DECLARACIÓN DE VARIABLES">
    private RecyclerView recycler_nivel;
    private NivelAdaptador adapter;
    private TextToSpeech tts;
    private DBHelper db;
    //</editor-fold>

    //<editor-fold desc="VARIABLES DE REFERENCIA">
    private MethodsManager methodsManager = new MethodsManager();

    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_iniciar_nivel);

        tts = new TextToSpeech(this, this);
        db = new DBHelper(this);
        recycler_nivel = (RecyclerView) findViewById(R.id.reciclador_iniciar_nivel);
        InitAdapter(recycler_nivel, db.getAllNiveles());

    }

    //<editor-fold desc="MÉTODO iniciarDatos_IniciarNivel_main() CARGA DE DATOS">
    public void iniciarDatos_IniciarNivel_main(Context contexto){
        DataManager datos = new DataManager();
        datos.Init_Niveles(contexto);
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO InitAdapter()">
    public void InitAdapter(RecyclerView mRecyclerView, List<Nivel> items) {

        assert mRecyclerView != null;
        adapter = new NivelAdaptador(items);
        setupRecyclerView(mRecyclerView, adapter, VISTA_NORMAL);

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO setupRecyclerView()">
    private void setupRecyclerView(@NonNull RecyclerView recyclerView, NivelAdaptador items, int tipo) {
        recyclerView.setAdapter(items);
        if (tipo == 0) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
            }else{
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL, false));
            }

        } else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }

        //recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //recyclerView.setLayoutManager( new GridLayoutManager(this, 3,GridLayoutManager.VERTICAL, false));
        //recyclerView.setLayoutManager( new GridLayoutManager(this, 3,GridLayoutManager.HORIZONTAL, false));
    }
    //</editor-fold>

    //<editor-fold desc="METODOS DE TEXT TO SPEECH">
    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.LANG_MISSING_DATA | status == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast toast = Toast.makeText(IniciarNivel_main.this, "ola", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void speak(String str) {

        if (str == "") {
            speak("Selecciona un pictograma para formar una oración");
        } else {
            tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
            tts.setSpeechRate(0.0f);
            tts.setPitch(0.0f);
        }

    }

    protected void onDestroy() {

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    //</editor-fold>

    /*********************************************************************************************************
     * Inicia mi Adaptador CardViewadaptador
     *********************************************************************************************************/
    public class NivelAdaptador extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Nivel> mValues;

        //<editor-fold desc="CONSTRUCTOR NivelAdaptador()">
        public NivelAdaptador(List<Nivel> items) {
            this.mValues = items;
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onCreateViewHolder">
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;

            switch (viewType) {

                case VISTA_NORMAL:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tipo_nivel, parent, false);
                    return new NivelViewHolder(view);

                /* case TIPO_PIC_SELECCIONADO:
                     view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_frase, parent, false);
                     return new FraseViewHolder(view); */

            }
            return null;

        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onBindViewHolder()">
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            Nivel object = mValues.get(position);
            int sumaProgresoNiveles;

/*
            Log.d("position", "-> " + position);
            Log.d("count", "1 -> " + db.count(CAT_VOCALES));
            Log.d("count", "2 -> " + db.count(CAT_CONSONANTES));
            Log.d("count", "3 -> " + db.count(CAT_MONOSILABAS));
            Log.d("count", "4 -> " + db.count(CAT_BISILABAS));
            Log.d("count", "5 -> " + db.count(CAT_POLISILABAS));
*/




            if (object != null){


                switch (object.getNombre()) {

                    case "Vocales":
                        sumaProgresoNiveles = db.obtenerProgreso(CAT_VOCALES);
                        object.setProgresso(sumaProgresoNiveles / db.count(CAT_VOCALES));
                        db.updatePictogramaNivel(object);

                        ((NivelViewHolder) holder).mVTextViewNivel.setText(object.getNombre() + " " + object.getProgresso() + "%");
                        ((NivelViewHolder) holder).mVTextProgreso.setText(db.ejerciciosCompletos(CAT_VOCALES) + "/" + db.count(CAT_VOCALES));


                        break;

                    case "Consonantes":
                        sumaProgresoNiveles = db.obtenerProgreso(CAT_CONSONANTES);
                        object.setProgresso(sumaProgresoNiveles / db.count(CAT_CONSONANTES));
                        db.updatePictogramaNivel(object);

                        ((NivelViewHolder) holder).mVTextViewNivel.setText(object.getNombre() + " " + object.getProgresso() + "%");
                        ((NivelViewHolder) holder).mVTextProgreso.setText(db.ejerciciosCompletos(CAT_CONSONANTES) + "/" + db.count(CAT_CONSONANTES));
                        break;

                    case "Monosilabas":
                        sumaProgresoNiveles = db.obtenerProgreso(CAT_MONOSILABAS);
                        object.setProgresso(sumaProgresoNiveles / db.count(CAT_MONOSILABAS));
                        db.updatePictogramaNivel(object);

                        ((NivelViewHolder) holder).mVTextViewNivel.setText(object.getNombre() + " " + object.getProgresso() + "%");
                        ((NivelViewHolder) holder).mVTextProgreso.setText(db.ejerciciosCompletos(CAT_MONOSILABAS) + "/" + db.count(CAT_MONOSILABAS));
                        break;

                    case "Bisilabas":
                        sumaProgresoNiveles = db.obtenerProgreso(CAT_BISILABAS);
                        object.setProgresso(sumaProgresoNiveles / db.count(CAT_BISILABAS));
                        db.updatePictogramaNivel(object);

                        ((NivelViewHolder) holder).mVTextViewNivel.setText(object.getNombre() + " " + object.getProgresso() + "%");
                        ((NivelViewHolder) holder).mVTextProgreso.setText(db.ejerciciosCompletos(CAT_BISILABAS) + "/" + db.count(CAT_BISILABAS));
                        break;

                    case "Polisilabas":
                        sumaProgresoNiveles = db.obtenerProgreso(CAT_POLISILABAS);
                        object.setProgresso(sumaProgresoNiveles / db.count(CAT_POLISILABAS));
                        db.updatePictogramaNivel(object);

                        ((NivelViewHolder) holder).mVTextViewNivel.setText(object.getNombre() + " " + object.getProgresso() + "%");
                        ((NivelViewHolder) holder).mVTextProgreso.setText(db.ejerciciosCompletos(CAT_POLISILABAS) + "/" + db.count(CAT_POLISILABAS));
                        break;

                }

            ((NivelViewHolder) holder).mVImageNivel.setImageResource(object.getIdDrawable());
            ((NivelViewHolder) holder).mvProgressBarNivel.setProgress(object.getProgresso());



        }




















/*
            if (object != null) switch (object.getTipo()) {

                case VISTA_NORMAL:

                    //Log.d("xyz", "-> " + object.getNombre() + " " + object.getProgresso() );



                    switch (object.getNombre()) {

                        case "Vocales":
                            Log.d("nivelVocales", "0 -> " + object.toString()  );
                            sumaProgresoNiveles=db.obtenerProgreso(CAT_VOCALES);
                            Log.d("nivelVocales", "1 -> " + sumaProgresoNiveles);
                            object.setProgresso(sumaProgresoNiveles/db.count(CAT_VOCALES));
                            Log.d("nivelVocales", "2 -> " + db.count(CAT_VOCALES));
                            db.updatePictogramaNivel(object);
                            Log.d("nivelVocales", "3 -> " + object.toString()  );

                            ((NivelViewHolder) holder).mVTextViewNivel.setText(object.getNombre() + " " + object.getProgresso() + "%");
                            ((NivelViewHolder) holder).mVTextProgreso.setText(db.ejerciciosCompletos(CAT_VOCALES)+ "/" + db.count(CAT_VOCALES));




                            break;

                        case "Consonantes":
                            Log.d("nivelConsonantes", "0 -> " + object.toString()  );
                            sumaProgresoNiveles=db.obtenerProgreso(CAT_CONSONANTES);
                            Log.d("nivelConsonantes", "1 -> " + sumaProgresoNiveles  );
                            object.setProgresso(sumaProgresoNiveles/db.count(CAT_CONSONANTES));
                            Log.d("nivelConsonantes", "2 -> " + db.count(CAT_CONSONANTES)  );
                            db.updatePictogramaNivel(object);
                            Log.d("nivelConsonantes", "3 -> " + object.toString()  );

                            ((NivelViewHolder) holder).mVTextViewNivel.setText(object.getNombre() + " " + object.getProgresso() + "%");
                            ((NivelViewHolder) holder).mVTextProgreso.setText(db.ejerciciosCompletos(CAT_CONSONANTES)+ "/" + db.count(CAT_CONSONANTES));
                            break;

                        case "Monosilabas":
                            Log.d("nivelMonosilabas", "0 -> " + object.toString()  );
                            sumaProgresoNiveles=db.obtenerProgreso(CAT_MONOSILABAS);
                            Log.d("nivelMonosilabas", "1 -> " + sumaProgresoNiveles  );
                            object.setProgresso(sumaProgresoNiveles/db.count(CAT_MONOSILABAS));
                            Log.d("nivelMonosilabas", "2 -> " + db.count(CAT_MONOSILABAS)  );
                            db.updatePictogramaNivel(object);
                            Log.d("nivelMonosilabas", "3 -> " + object.toString()  );

                            ((NivelViewHolder) holder).mVTextViewNivel.setText(object.getNombre() + " " + object.getProgresso() + "%");
                            ((NivelViewHolder) holder).mVTextProgreso.setText(db.ejerciciosCompletos(CAT_MONOSILABAS) + "/" + db.count(CAT_MONOSILABAS));
                            break;

                        case "Bisilabas":
                            Log.d("nivelBisilabas", "0 -> " + object.toString()  );
                            sumaProgresoNiveles=db.obtenerProgreso(CAT_BISILABAS);
                            Log.d("nivelBisilabas", "1 -> " + sumaProgresoNiveles  );
                            object.setProgresso(sumaProgresoNiveles/db.count(CAT_BISILABAS));
                            Log.d("nivelBisilabas", "2 -> " + db.count(CAT_BISILABAS)  );
                            db.updatePictogramaNivel(object);
                            Log.d("nivelBisilabas", "3 -> " + object.toString()  );

                            ((NivelViewHolder) holder).mVTextViewNivel.setText(object.getNombre() + " " + object.getProgresso() + "%");
                            ((NivelViewHolder) holder).mVTextProgreso.setText(db.ejerciciosCompletos(CAT_BISILABAS) + "/" + db.count(CAT_BISILABAS));
                            break;

                        case "Polisilabas":
                            Log.d("nivelPolisilabas", "0 -> " + object.toString()  );
                            sumaProgresoNiveles=db.obtenerProgreso(CAT_POLISILABAS);
                            Log.d("nivelPolisilabas", "1 -> " + sumaProgresoNiveles  );
                            object.setProgresso(sumaProgresoNiveles/db.count(CAT_POLISILABAS));
                            Log.d("nivelPolisilabas", "2 -> " + db.count(CAT_POLISILABAS)  );
                            db.updatePictogramaNivel(object);
                            Log.d("nivelPolisilabas", "3 -> " + object.toString()  );

                            ((NivelViewHolder) holder).mVTextViewNivel.setText(object.getNombre() + " " + object.getProgresso() + "%");
                            ((NivelViewHolder) holder).mVTextProgreso.setText(db.ejerciciosCompletos(CAT_POLISILABAS) + "/" + db.count(CAT_POLISILABAS));
                            break;

                    }

                    ((NivelViewHolder) holder).mVImageNivel.setImageResource(object.getIdDrawable());
                    ((NivelViewHolder) holder).mvProgressBarNivel.setProgress(object.getProgresso());
                    break;

                    // case TIPO_PIC_SELECCIONADO:
                         //((FraseViewHolder) holder).mNombreViewFrase.setText(object.nombre);
                         //((FraseViewHolder) holder).mImageViewFrase.setImageResource(object.getIdDrawable());
                         //((FraseViewHolder) holder).cv.setCardBackgroundColor(getBackground_CardView(object.getCategoria()));
                         //break;
            }

            */



            // holder.mVTextView.setText(object.nombre);
            //holder.mVImage.setBackgroundResource(getBackground2(items.get(position).categoria));
            //holder.mVTextView.setTextColor(getBackground(items.get(position).categoria));

        }
        //</editor-fold>

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        //<editor-fold desc="MÉTODO getItemViewType()">
        @Override
        public int getItemViewType(int position) {

            if (mValues != null) {
                final Nivel object = mValues.get(position);
                if (object != null) {
                    return object.getTipo();
                }
            }
            return 0;
        }
        //</editor-fold>






        //<editor-fold desc="CLASE NivelViewHolder">
        public class NivelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private ImageView mVImageNivel;
            private TextView mVTextViewNivel,mVTextProgreso;
            private ProgressBar mvProgressBarNivel;

            public NivelViewHolder(View itemView) {
                super(itemView);

                mvProgressBarNivel = (ProgressBar) itemView.findViewById(R.id.progressbar_nivel);
                mVImageNivel = (ImageView) itemView.findViewById(R.id.iv_card_nivel);
                mVTextViewNivel = (TextView) itemView.findViewById(R.id.tv_nombre_nivel);
                mVTextProgreso = (TextView) itemView.findViewById(R.id.tv_tipo_nivel_progreso);
                mVImageNivel.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {

                int posicision = getAdapterPosition();
                Nivel mNivel = mValues.get(posicision);
                Intent Vocales, Consonantes, Monosilabas, Bisilabas, Polisilabas;

                methodsManager.Init_Toast(v, null, mNivel);

                Locale locSpanish = new Locale("spa", "MEX");
                tts.setLanguage(locSpanish);
                speak(mNivel.getNombre());

                switch (mNivel.getNombre()) {

                    case "Vocales":
                        Vocales = new Intent(IniciarNivel_main.this, VocalesEjercicios_main.class);
                        startActivity(Vocales);
                        break;

                    case "Consonantes":
                        Consonantes = new Intent(IniciarNivel_main.this, ConsonantesEjercicios_main.class);
                        startActivity(Consonantes);
                        break;

                    case "Monosilabas":
                        Monosilabas = new Intent(IniciarNivel_main.this, MonosilabasEjercicios_main.class);
                        startActivity(Monosilabas);
                        break;

                    case "Bisilabas":
                        Bisilabas = new Intent(IniciarNivel_main.this,  BisilabasEjercicios_main.class);
                        startActivity(Bisilabas);
                        break;

                    case "Polisilabas":
                        Polisilabas = new Intent(IniciarNivel_main.this,  PolisilabasEjercicios_main.class);
                        startActivity(Polisilabas);
                        break;
                }
            }
        }
        //</editor-fold>


        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

    }

}

