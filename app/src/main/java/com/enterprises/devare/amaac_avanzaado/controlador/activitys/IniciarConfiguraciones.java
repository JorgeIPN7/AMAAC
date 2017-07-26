package com.enterprises.devare.amaac_avanzaado.controlador.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.enterprises.devare.amaac_avanzaado.R;
import com.enterprises.devare.amaac_avanzaado.controlador.adapters.ConsonantesEjercicios_main;
import com.enterprises.devare.amaac_avanzaado.controlador.adapters.IniciarNivel_main;
import com.enterprises.devare.amaac_avanzaado.controlador.adapters.VocalesEjercicios_main;
import com.enterprises.devare.amaac_avanzaado.modelo.db.DataManager;

public class IniciarConfiguraciones extends AppCompatActivity {

    //<editor-fold desc="DECLARAR VARIABLES">
    TextView loadText;
    private ProgressBar progressBar;
    //</editor-fold>

    //<editor-fold desc="VARIABLES DE REFERENCIA">
    IniciarNivel_main init_IniciarNivel_main= new IniciarNivel_main();
    VocalesEjercicios_main init_Vocales_main=new VocalesEjercicios_main();
    GuiapadreListActivity init_tips_uso_app=new GuiapadreListActivity();
    //</editor-fold>


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_configuraciones);

        inicializarcomponentesUI();
        progressBar.setMax(100);
        progressBar.setBackgroundColor(Color.TRANSPARENT);
        //progressBar.getProgressDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgress(0);

        AsyncTaskCargaDatos ATCargaDatos = new AsyncTaskCargaDatos(this);
        inicializarDBAMAAC();

        ATCargaDatos.execute();
        DataManager datos = new DataManager();
        datos.inicializar_pictogramas(this);

    }

    //<editor-fold desc="SE CARGAN TODOS LOS DATOS DE LA APLICACION AMAAC">
    private void inicializarDBAMAAC() {
        init_IniciarNivel_main.iniciarDatos_IniciarNivel_main(this);
        init_Vocales_main.iniciarDatos_Vocales_main(this);
}
    //</editor-fold>

    //<editor-fold desc="MÉTODO inicializarcomponentesUI()">
    private void inicializarcomponentesUI() {
        loadText = (TextView) findViewById(R.id.loadText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
    }
    //</editor-fold>

    //<editor-fold desc="CLASE AsyncTaskCargaDatos ">
    public class AsyncTaskCargaDatos extends AsyncTask<Void, Integer, Void> {

        Context mContext;

        AsyncTaskCargaDatos(Context context) {
            mContext = context;
        }

        //<editor-fold desc="MÉTODO doInBackground()">
        @Override
        protected Void doInBackground(Void... params) {

            publishProgress(0);

            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(50);
                    publishProgress(i + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onProgressUpdate()">
        @Override
        protected void onProgressUpdate(Integer... value) {
            loadText.setText(value[0] + " %");
            progressBar.setProgress(value[0]);

        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onPostExecute()">
        @Override
        protected void onPostExecute(Void result) {

            mContext.startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }
        //</editor-fold>

    }
    //</editor-fold>

}
