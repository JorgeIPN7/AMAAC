package com.enterprises.devare.amaac_avanzaado.controlador.adapters;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enterprises.devare.amaac_avanzaado.R;
import com.enterprises.devare.amaac_avanzaado.controlador.activitys.MainActivity;
import com.enterprises.devare.amaac_avanzaado.modelo.db.DBHelper;
import com.enterprises.devare.amaac_avanzaado.modelo.db.DataManager;
import com.enterprises.devare.amaac_avanzaado.modelo.metodos.MethodsManager;
import com.enterprises.devare.amaac_avanzaado.modelo.Pictograma;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//<editor-fold desc="IMPORTAR ETIQUETAS">
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VERBOS_AUX;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_RESPUESTAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VERBOS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_BEBIDAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_FRUTAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_VERDURAS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_COMIDA;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_POSTRES;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_ALIMENTOS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_ANIMALES;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_OBJETOS;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_ANIMO;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_LUGARES;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_FAMILIA;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.CAT_PROFESIONES;

import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.TIPO_PIC_NORMAL;
import static com.enterprises.devare.amaac_avanzaado.modelo.Pictograma.TIPO_PIC_SELECCIONADO;
//</editor-fold>

import static com.enterprises.devare.amaac_avanzaado.modelo.utilidades.Utilidades.getBackground_CardView;


public class Comunicador_main extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //<editor-fold desc="DECLARACIÓN DE VARIABLES">
    private RecyclerView
            recycler_verbos_aux,
            recycler_respuestas,
            recycler_verbos,
            recycler_bebidas,
            recycler_frutas,
            recycler_verduras,
            recycler_comida,
            recycler_postres,
            recycler_alimentos,
            recycler_animales,
            recycler_objetos,
            recycler_animo,
            recycler_lugares,
            recycler_familia,
            recycler_profesiones,
            recyclerView_frase;
    private CardViewAdaptador adapter;
    private TextToSpeech tts;

    //</editor-fold>

    //<editor-fold desc="DECLARACIÓN DE VARIABLES DE REFERENCIA">
    MethodsManager methodsManager = new MethodsManager();
    private List<Pictograma> PIC_GUARDADO = new ArrayList<Pictograma>();
    DataManager datos = new DataManager();
    private DBHelper db = new DBHelper(this);
    //</editor-fold>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_comunicador);




        tts = new TextToSpeech(this, this);
        Locale locSpanish = new Locale("spa", "MEX");
        tts.setLanguage(locSpanish);

        inicializarComponentesUI();
        inicializarAdaptador();
    }

    //<editor-fold desc="MÉTODO inicializarComponentesUI()">
    private void inicializarComponentesUI() {

        recycler_verbos_aux = (RecyclerView) findViewById(R.id.reciclador_card_tablero_verbos_aux);
        recycler_respuestas = (RecyclerView) findViewById(R.id.reciclador_card_tablero_respuestas);
        recycler_verbos = (RecyclerView) findViewById(R.id.reciclador_card_tablero_verbos);
        recycler_bebidas = (RecyclerView) findViewById(R.id.reciclador_card_tablero_bebidas);
        recycler_frutas = (RecyclerView) findViewById(R.id.reciclador_card_tablero_frutas);
        recycler_verduras = (RecyclerView) findViewById(R.id.reciclador_card_tablero_verduras);
        recycler_comida = (RecyclerView) findViewById(R.id.reciclador_card_tablero_comida);
        recycler_postres = (RecyclerView) findViewById(R.id.reciclador_card_tablero_postres);
        recycler_alimentos = (RecyclerView) findViewById(R.id.reciclador_card_tablero_alimentos);
        recycler_animales = (RecyclerView) findViewById(R.id.reciclador_card_tablero_animales);
        recycler_objetos = (RecyclerView) findViewById(R.id.reciclador_card_tablero_objetos);
        recycler_animo = (RecyclerView) findViewById(R.id.reciclador_card_tablero_animo);
        recycler_lugares = (RecyclerView) findViewById(R.id.reciclador_card_tablero_lugares);
        recycler_familia = (RecyclerView) findViewById(R.id.reciclador_card_tablero_familia);
        recycler_profesiones = (RecyclerView) findViewById(R.id.reciclador_card_tablero_profesiones);


        recyclerView_frase = (RecyclerView) findViewById(R.id.reciclador_card_item_frase);


    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO inicializarAdaptador()">
    private void inicializarAdaptador() {

        InitAdapter(recycler_verbos_aux ,db.getCategoria_Pictogramas(CAT_VERBOS_AUX));
        InitAdapter(recycler_respuestas ,db.getCategoria_Pictogramas(CAT_RESPUESTAS));
        InitAdapter(recycler_verbos ,db.getCategoria_Pictogramas(CAT_VERBOS));
        InitAdapter(recycler_bebidas ,db.getCategoria_Pictogramas(CAT_BEBIDAS));
        InitAdapter(recycler_frutas ,db.getCategoria_Pictogramas(CAT_FRUTAS));
        InitAdapter(recycler_verduras ,db.getCategoria_Pictogramas(CAT_VERDURAS));
        InitAdapter(recycler_comida ,db.getCategoria_Pictogramas(CAT_COMIDA));
        InitAdapter(recycler_postres ,db.getCategoria_Pictogramas(CAT_POSTRES));
        InitAdapter(recycler_alimentos ,db.getCategoria_Pictogramas(CAT_ALIMENTOS));
        InitAdapter(recycler_animales ,db.getCategoria_Pictogramas(CAT_ANIMALES));
        InitAdapter(recycler_objetos ,db.getCategoria_Pictogramas(CAT_OBJETOS));
        InitAdapter(recycler_animo ,db.getCategoria_Pictogramas(CAT_ANIMO));
        InitAdapter(recycler_lugares ,db.getCategoria_Pictogramas(CAT_LUGARES));
        InitAdapter(recycler_familia ,db.getCategoria_Pictogramas(CAT_FAMILIA));
        InitAdapter(recycler_profesiones ,db.getCategoria_Pictogramas(CAT_PROFESIONES));

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO InitAdapter()">
    public void InitAdapter(RecyclerView mRecyclerView, List<Pictograma> items) {

        assert mRecyclerView != null;
        adapter = new CardViewAdaptador(items);
        setupRecyclerView(mRecyclerView, adapter, TIPO_PIC_NORMAL);

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO Init_Adapter_Seleccionados">
    public void Init_Adapter_Seleccionados(RecyclerView mRecyclerView, List<Pictograma> picto_seleccionados) {

        assert mRecyclerView != null;
        adapter = new CardViewAdaptador(picto_seleccionados);
        setupRecyclerView(mRecyclerView, adapter, TIPO_PIC_SELECCIONADO);

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO setupRecyclerView()">
    private void setupRecyclerView(@NonNull RecyclerView recyclerView, CardViewAdaptador items, int tipo) {

        recyclerView.setAdapter(items);
        if (tipo == 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        } else
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //recyclerView.setLayoutManager( new GridLayoutManager(this, 3,GridLayoutManager.VERTICAL, false));
        //recyclerView.setLayoutManager( new GridLayoutManager(this, 3,GridLayoutManager.HORIZONTAL, false));

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO onCreateOptionsMenu()">
/*    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_comunicador_frases, menu);
        return true;

    }*/
    //</editor-fold>
  /*   //<editor-fold desc="MÉTODO onOptionsItemSelected()">
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       switch (item.getItemId()) {

            case R.id.item_borrar_frase:
                PIC_GUARDADO = methodsManager.DeleteAll(PIC_GUARDADO, this);
                Init_Adapter_Seleccionados(recyclerView_frase, PIC_GUARDADO);
                return true;

            case R.id.item_reproducir_frase:
                String x = methodsManager.Reproducir_Frase(PIC_GUARDADO);
                speak(x);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    //</editor-fold>*/

    //<editor-fold desc="MÉTODOS DE LA CLASE TEXT TO SPEECH">
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.LANG_MISSING_DATA | status == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast toast = Toast.makeText(Comunicador_main.this, "ola", Toast.LENGTH_SHORT);
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

    public void btn_ayuda(View view){
        String x= "¡ayuda!";
        speak(x);
    }

    public void btn_estoy_bien(View view){
        String x= "¡Estoy bién!";
        speak(x);
    }

    public void btn_quiero_hablar(View view){
        String x= "¡Disculpe, Quiero hablar!";
        speak(x);
    }

    public void btn_no_quiero_hablar(View view){
        String x= "¡Perdón, No quiero hablar!";
        speak(x);
    }

    public void reproducirFrase(View view){
        String x= methodsManager.Reproducir_Frase(PIC_GUARDADO);
        speak(x);
    }

    public void borrarFrase(View view){
        PIC_GUARDADO=methodsManager.DeleteAll(PIC_GUARDADO,this);
        Init_Adapter_Seleccionados(recyclerView_frase,PIC_GUARDADO);
    }

    public void go_to_pp(View view){
        startActivity(new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }
    //</editor-fold>


    //<editor-fold desc="CLASE ADAPTADOR CardViewAdaptador">
    public class CardViewAdaptador extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Pictograma> mValues;

        public CardViewAdaptador(List<Pictograma> items) {
            this.mValues = items;
        }

        //<editor-fold desc="MÉTODO onCreateViewHolder">
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;

            switch (viewType) {

                case TIPO_PIC_NORMAL:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_categoria, parent, false);
                    return new CardViewHolder(view);

                case TIPO_PIC_SELECCIONADO:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_frase, parent, false);
                    return new FraseViewHolder(view);

            }
            return null;

        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onBindViewHolder()">
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            Pictograma object = mValues.get(position);

            if (object != null) {
                switch (object.getTipo()) {

                    case TIPO_PIC_NORMAL:
                        ((CardViewHolder) holder).mVTextView.setText(object.nombre);
                        ((CardViewHolder) holder).mVImage.setImageResource(object.idDrawable);
                        ((CardViewHolder) holder).cv.setCardBackgroundColor(getBackground_CardView(object.getCategoria()));
                        break;

                    case TIPO_PIC_SELECCIONADO:
                        ((FraseViewHolder) holder).mNombreViewFrase.setText(object.nombre);
                        ((FraseViewHolder) holder).mImageViewFrase.setImageResource(object.getIdDrawable());
                        ((FraseViewHolder) holder).cv.setCardBackgroundColor(getBackground_CardView(object.getCategoria()));
                        break;

                }
            }
            //holder.mVTextView.setText(object.nombre);
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
                final Pictograma object = mValues.get(position);
                if (object != null) {
                    return object.getTipo();
                }
            }
            return 0;
        }
        //</editor-fold>

        //<editor-fold desc="CLASE CardViewHolder">
        public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private ImageView mVImage;
            private TextView mVTextView;
            private CardView cv;


            public CardViewHolder(View itemView) {
                super(itemView);

                cv = (CardView) itemView.findViewById(R.id.cv_pictograma);
                mVImage = (ImageView) itemView.findViewById(R.id.iv_card);
                mVTextView = (TextView) itemView.findViewById(R.id.tv_nombre);
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {

                int posicision = getAdapterPosition();
                Pictograma mPictograma = mValues.get(posicision);
                methodsManager.Init_Toast(v, mPictograma, null);

                Locale locSpanish = new Locale("spa", "MEX");
                tts.setLanguage(locSpanish);
                speak(mPictograma.getNombre());

                PIC_GUARDADO = methodsManager.Guardar(TIPO_PIC_SELECCIONADO, mPictograma.getCategoria(), mPictograma.nombre, mPictograma.getIdDrawable(), PIC_GUARDADO);
                Init_Adapter_Seleccionados(recyclerView_frase, PIC_GUARDADO);
            }

        }
        //</editor-fold>

        //<editor-fold desc="CLASE FraseViewHolder">
        public class FraseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public CardView cv;
            public final View mView;
            public final ImageView mImageViewFrase;
            public final TextView mNombreViewFrase;

            public FraseViewHolder(View view) {
                super(view);

                mView = view;

                mImageViewFrase = (ImageView) view.findViewById(R.id.iv_card_frase);
                mNombreViewFrase = (TextView) view.findViewById(R.id.tv_nombre_frase);
                cv = (CardView) view.findViewById(R.id.cv_pictograma_frase);

                view.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {

                int position = getAdapterPosition();
                PIC_GUARDADO = methodsManager.Delete_Picto_Seleccionado(position, PIC_GUARDADO);
                Init_Adapter_Seleccionados(recyclerView_frase, PIC_GUARDADO);

            }
        }
        //</editor-fold>

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

    }
    //</editor-fold>


}

