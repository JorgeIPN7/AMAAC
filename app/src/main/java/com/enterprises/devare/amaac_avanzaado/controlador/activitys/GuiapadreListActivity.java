package com.enterprises.devare.amaac_avanzaado.controlador.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprises.devare.amaac_avanzaado.R;

import java.util.List;

import com.enterprises.devare.amaac_avanzaado.controlador.fragments.GuiapadreDetailFragment;
import com.enterprises.devare.amaac_avanzaado.modelo.dummy.GuiaPadreContent;


public class GuiapadreListActivity extends AppCompatActivity {

    private boolean mTwoPane;

    //<editor-fold desc="MÉTODO CALLBACK onCreate(Bundle savedInstanceState)">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guiapadre_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        //<editor-fold desc="Muestra el botón Arriba en la barra de acción.">
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //</editor-fold>

        View recyclerView = findViewById(R.id.guiapadre_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.guiapadre_detail_container) != null) {
            mTwoPane = true;
        }

        cargarDatos();

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO cargarDatos()">
    public void cargarDatos() {

        GuiaPadreContent datos = new GuiaPadreContent();
        datos.agregarItem(new GuiaPadreContent.GuiaPadre("1", getString(R.string.como_usar_app_titulo1).toString(), getString(R.string.como_usar_app_descripcion1).toString(), getString(R.string.como_usar_app_fecha1).toString(), R.drawable.ic_launcher_play_200dp));
        datos.agregarItem(new GuiaPadreContent.GuiaPadre("2", getString(R.string.como_usar_app_titulo2).toString(), getString(R.string.como_usar_app_descripcion2), getString(R.string.como_usar_app_fecha2), R.drawable.ic_launcher_creador_frases_200dp));
        datos.agregarItem(new GuiaPadreContent.GuiaPadre("3", getString(R.string.como_usar_app_titulo3), getString(R.string.como_usar_app_descripcion3), getString(R.string.como_usar_app_fecha3), R.drawable.ic_launcher_ejercicios_especificos_200dp));

//        datos.agregarItem(new GuiaPadreContent.GuiaPadre("4", getString(R.string.como_usar_app_titulo4), getString(R.string.como_usar_app_descripcion4), getString(R.string.como_usar_app_fecha4), R.drawable.ic_menu_guia_padre_technology));
//        datos.agregarItem(new GuiaPadreContent.GuiaPadre("5", getString(R.string.como_usar_app_titulo5), getString(R.string.como_usar_app_descripcion5), getString(R.string.como_usar_app_fecha5), R.drawable.ic_launcher_ejercicios_especificos_200dp));
//        datos.agregarItem(new GuiaPadreContent.GuiaPadre("6", getString(R.string.como_usar_app_titulo6), getString(R.string.como_usar_app_descripcion6), getString(R.string.como_usar_app_fecha6), R.drawable.ic_launcher_play_200dp));

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO onOptionsItemSelected(MenuItem item)">
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO setupRecyclerView(@NonNull RecyclerView recyclerView)">
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(GuiaPadreContent.ITEMS));
    }
    //</editor-fold>

    //<editor-fold desc="CLASE SimpleItemRecyclerViewAdapter">
    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<GuiaPadreContent.GuiaPadre> mValues;

        //<editor-fold desc="CONSTRUCTOR">
        public SimpleItemRecyclerViewAdapter(List<GuiaPadreContent.GuiaPadre> items) {
            mValues = items;
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onCreateViewHolder(ViewGroup parent, int viewType)">
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.guiapadre_list_content, parent, false);
            return new ViewHolder(view);
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onBindViewHolder(final ViewHolder holder, int position)">
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            // holder.mIdView.setText(mValues.get(position).id);
            holder.mTituloView.setText(mValues.get(position).titulo);
            holder.mResumenView.setText(mValues.get(position).descripcion);
            holder.mFechaView.setText(mValues.get(position).fecha);
            holder.mImageView.setImageResource(mValues.get(position).idImagen);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(GuiapadreDetailFragment.ID_ARTICULO, holder.mItem.id);
                        GuiapadreDetailFragment fragment = new GuiapadreDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.guiapadre_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, GuiapadreDetailActivity.class);
                        intent.putExtra(GuiapadreDetailFragment.ID_ARTICULO, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO  getItemCount()">
        @Override
        public int getItemCount() {
            return mValues.size();
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO ViewHolder">
        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            // public final TextView mIdView;
            public final TextView mTituloView;
            public final TextView mResumenView;
            public final TextView mFechaView;
            public final ImageView mImageView;
            public GuiaPadreContent.GuiaPadre mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                // mIdView = (TextView) view.findViewById(R.id.txt_id);
                mTituloView = (TextView) view.findViewById(R.id.txt_titulo);
                mResumenView = (TextView) view.findViewById(R.id.txt_resumen);
                mFechaView = (TextView) view.findViewById(R.id.txt_fecha);
                mImageView = (ImageView) view.findViewById(R.id.iv_miniatura);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTituloView.getText() + "'";
            }
        }
        //</editor-fold>
    }
    //</editor-fold>
}
 //clase checada