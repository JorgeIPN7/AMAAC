package com.enterprises.devare.amaac_avanzaado.controlador.activitys;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprises.devare.amaac_avanzaado.R;
import com.enterprises.devare.amaac_avanzaado.controlador.fragments.TecnicaDetailFragment;
import com.enterprises.devare.amaac_avanzaado.modelo.dummy.TecnicaRelajacionContent;

import java.util.List;

public class TecnicaListActivity extends AppCompatActivity {

    private boolean mTwoPane;

    //<editor-fold desc="MÉTODO CALLBACK onCreate(Bundle savedInstanceState)">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecnica_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        //<editor-fold desc="Muestra el botón Arriba en la barra de acción.">
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //</editor-fold>

        View recyclerView = findViewById(R.id.tecnica_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.tecnica_detail_container) != null) {

            mTwoPane = true;
        }
    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO setupRecyclerView(@NonNull RecyclerView recyclerView)">
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(TecnicaRelajacionContent.ITEMS));
    }
    //</editor-fold>

    //<editor-fold desc="CLASE SimpleItemRecyclerViewAdapter ">
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<TecnicaRelajacionContent.TecnicaRelajacion> mValues;

        //<editor-fold desc="CONSTRUCTOR CON PARAMETROS">
        public SimpleItemRecyclerViewAdapter(List<TecnicaRelajacionContent.TecnicaRelajacion> items) {
            mValues = items;
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onCreateViewHolder(ViewGroup parent, int viewType) ">
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tecnica_list_content, parent, false);
            return new ViewHolder(view);
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO onBindViewHolder(final ViewHolder holder, int position">
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            // holder.mIdView.setText(mValues.get(position).id);
            holder.mTituloView.setText(mValues.get(position).titulo);
            holder.mResumenView.setText(mValues.get(position).descripcion);
           // holder.mFechaView.setText(mValues.get(position).fecha);
            holder.mImageView.setImageResource(mValues.get(position).idImagen);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(TecnicaDetailFragment.ID_ARTICULO, holder.mItem.id);
                        TecnicaDetailFragment fragment = new TecnicaDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.tecnica_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, TecnicaDetailActivity.class);
                        intent.putExtra(TecnicaDetailFragment.ID_ARTICULO, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO getItemCount() ">
        @Override
        public int getItemCount() {
            return mValues.size();
        }
        //</editor-fold>

        //<editor-fold desc="CLASE ViewHolder">
        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            // public final TextView mIdView;
            public final TextView mTituloView;
            public final TextView mResumenView;
            //public final TextView mFechaView;
            public final ImageView mImageView;

            public TecnicaRelajacionContent.TecnicaRelajacion mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                // mIdView = (TextView) view.findViewById(R.id.txt_id);
                mTituloView = (TextView) view.findViewById(R.id.txt_titulo);
                mResumenView=(TextView) view.findViewById(R.id.txt_resumen);
                //mFechaView=(TextView) view.findViewById(R.id.txt_fecha);
                mImageView=(ImageView) view.findViewById(R.id.iv_miniatura);
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
