package com.enterprises.devare.amaac_avanzaado.controlador.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.enterprises.devare.amaac_avanzaado.R;
import com.enterprises.devare.amaac_avanzaado.modelo.db.DataManager;

public class TerminosYCondiciones extends AppCompatActivity implements View.OnClickListener {

    //<editor-fold desc="DECLARAR VARIABLES">
    TextView tv_tyc_contenido;
    Button btn_tyc_aceptar, btn_tyc_rechazar;
    SharedPreferences preferences;
    CharSequence textoInterpretado;


    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos_ycondiciones);

        inicializarComponentesUI();
        String contenido = getString(R.string.contenido_terminos_y_condiciones);

        preferences = getSharedPreferences("TerminosYCondiciones", MODE_PRIVATE);
        boolean TERMINOS_Y_CONDICIONES = preferences.getBoolean("opcion", false);

        if (Build.VERSION.SDK_INT >= 24) {
            textoInterpretado = Html.fromHtml(contenido, Html.FROM_HTML_MODE_LEGACY);
            tv_tyc_contenido.setText(textoInterpretado);

        } else {
            textoInterpretado = Html.fromHtml(contenido);
            tv_tyc_contenido.setText(textoInterpretado);
        }

        controlarEventosSetOnClickListener();

    }

    //<editor-fold desc="MÉTODO inicializarComponentesUI()">
    private void inicializarComponentesUI() {

        tv_tyc_contenido = (TextView) findViewById(R.id.tv_tyc_contenido);
        btn_tyc_aceptar = (Button) findViewById(R.id.btn_tyc_aceptar);
        btn_tyc_rechazar = (Button) findViewById(R.id.btn_tyc_rechazar);
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO controlarEventosSetOnClickListener()">
    private void controlarEventosSetOnClickListener() {
        btn_tyc_aceptar.setOnClickListener(this);
        btn_tyc_rechazar.setOnClickListener(this);
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO GESTOR DE EVENTOS onClick()">
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_tyc_aceptar:
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("opcion", true);
                editor.commit();



                Intent intent = new Intent(this, IniciarConfiguraciones.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_tyc_rechazar:
                finish();
                break;
        }
    }
    //</editor-fold>
}
