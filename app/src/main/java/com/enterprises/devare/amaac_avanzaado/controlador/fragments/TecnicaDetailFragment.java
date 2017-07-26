package com.enterprises.devare.amaac_avanzaado.controlador.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprises.devare.amaac_avanzaado.R;
import com.enterprises.devare.amaac_avanzaado.modelo.dummy.TecnicaRelajacionContent;

public class TecnicaDetailFragment extends Fragment {

    public static final String ID_ARTICULO = "item_id";

    private TecnicaRelajacionContent.TecnicaRelajacion itemDetallado;

    //<editor-fold desc="CONSTRUCTOR">
    public TecnicaDetailFragment() {
    }
    //</editor-fold>

    //<editor-fold desc="M[ETODO CALLBACK onCreate()">
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ID_ARTICULO)) {

            itemDetallado = TecnicaRelajacionContent.ITEM_MAP.get(getArguments().getString(ID_ARTICULO));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(itemDetallado.titulo);//el contenido dentro ded  i barra
            }
        }
    }
    //</editor-fold>

    TextView titulo;

    //<editor-fold desc="MÉTODO onCreateView() ">
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tecnica_detail, container, false);

        if (itemDetallado != null) {
            ((TextView) v.findViewById(R.id.contenido)).setText(itemDetallado.descripcion);
            ((TextView) v.findViewById(R.id.titulo)).setText(itemDetallado.titulo);
            titulo= (TextView) v.findViewById(R.id.fecha);
            titulo.setText(itemDetallado.fecha);

            ImageView myImageView = (ImageView)v.findViewById(R.id.imagen);
            myImageView.setImageResource(itemDetallado.idImagen);

            ImageView go_to_video= (ImageView) v.findViewById(R.id.go_to_video);

            go_to_video.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Log.d("url", titulo.getText().toString());

                    String link = titulo.getText().toString();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                }

            });

        }

        return v;
    }
    //</editor-fold>
}
