package com.enterprises.devare.amaac_avanzaado.controlador.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.enterprises.devare.amaac_avanzaado.R;
import com.enterprises.devare.amaac_avanzaado.controlador.adapters.Comunicador_main;
import com.enterprises.devare.amaac_avanzaado.controlador.adapters.IniciarNivel_main;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ImageView iv_Iniciar_nivel, iv_Iniciar_comunicador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        inicializarComponentesUI();
        controlarEventosSetOnClickListener();


    }

    //<editor-fold desc="MÉTODO controlarEventosSetOnClickListener()">
    private void controlarEventosSetOnClickListener() {
        iv_Iniciar_nivel.setOnClickListener(this);
        iv_Iniciar_comunicador.setOnClickListener(this);
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO inicializarComponentesUI(">
    private void inicializarComponentesUI() {
        iv_Iniciar_nivel = (ImageView) findViewById(R.id.iv_main_iniciar_nivel);
        iv_Iniciar_comunicador = (ImageView) findViewById(R.id.iv_main_iniciar_comunicador);
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO GESTOR DE EVENTOS onClick()">
    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.iv_main_iniciar_nivel:
                i = new Intent(MainActivity.this, IniciarNivel_main.class);
                startActivity(i);
                break;

            case R.id.iv_main_iniciar_comunicador:
                i = new Intent(MainActivity.this, Comunicador_main.class);
                startActivity(i);
                break;
        }

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO onBackPressed()">
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO onOptionsItemSelected()">
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO onNavigationItemSelected">
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_guia_padre) {
            Intent intent = new Intent(this, GuiapadreListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_consejos_relajacion) {
            Intent intent = new Intent(this, TecnicaListActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //</editor-fold>
}
