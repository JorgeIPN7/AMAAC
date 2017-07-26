package com.enterprises.devare.amaac_avanzaado.controlador.activitys;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.enterprises.devare.amaac_avanzaado.R;
import com.enterprises.devare.amaac_avanzaado.controlador.fragments.TecnicaDetailFragment;

public class TecnicaDetailActivity extends AppCompatActivity {

    //<editor-fold desc="MÉTODO CALLBACK onCreate(Bundle savedInstanceState)">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecnica_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        //<editor-fold desc="Muestra el botón Arriba en la barra de acción.">
         ActionBar actionBar = getSupportActionBar();
         if (actionBar != null) {
             actionBar.setDisplayHomeAsUpEnabled(true);
         }
        //</editor-fold>

        // For more information, see the Fragments API guide at:
        // http://developer.android.com/guide/components/fragments.html

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(TecnicaDetailFragment.ID_ARTICULO ,
                    getIntent().getStringExtra(TecnicaDetailFragment.ID_ARTICULO ));
            TecnicaDetailFragment fragment = new TecnicaDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.tecnica_detail_container, fragment)
                    .commit();
        }
    }
    //</editor-fold>

    //<editor-fold desc="MMÉTODO onCreateOptionsMenu(Menu menu)">
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle_articulo, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO onOptionsItemSelected(MenuItem item)">
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            NavUtils.navigateUpTo(this,new Intent(this, TecnicaListActivity.class));
            return true;
        }

        else if(id == R.id.acccion_favoritos) {

            TextView titulo = (TextView) findViewById(R.id.fecha);
            String link = titulo.getText().toString();

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
        }

        return super.onOptionsItemSelected(item);
    }
    //</editor-fold>
}
