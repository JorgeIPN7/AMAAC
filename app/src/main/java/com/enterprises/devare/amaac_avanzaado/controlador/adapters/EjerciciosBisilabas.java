package com.enterprises.devare.amaac_avanzaado.controlador.adapters;

import android.app.FragmentManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enterprises.devare.amaac_avanzaado.R;
import com.enterprises.devare.amaac_avanzaado.controlador.dialogos.SeccionTerminadaDialogo;
import com.enterprises.devare.amaac_avanzaado.controlador.dialogos.SeccionTerminadaDialogoBisi;
import com.enterprises.devare.amaac_avanzaado.modelo.Pictograma;
import com.enterprises.devare.amaac_avanzaado.modelo.db.DBHelper;

import java.util.List;

public class EjerciciosBisilabas extends AppCompatActivity {

    //<editor-fold desc="DECLARION DE VARIABLES">
    EjerciciosBisilabas.EjercicioBisilabasAdaptador adapter;
    private DBHelper db;
    private RecyclerView recycler_ejercicios;
    int nuevaPosicion;
    int categoria;
    String nombreNivel_Bisilabas;

    static MediaPlayer mediaPlayer;
    Thread hilo;
    int flagBotonPlay = 0;
    int seguroDeVida = 0;
    //</editor-fold>

    //<editor-fold desc="MÉTODO CALLBACK onCreate()">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejercicios);

        db = new DBHelper(this);

        recycler_ejercicios = (RecyclerView) findViewById(R.id.reciclador_ejercicio_niveles);
        Intent intent = getIntent();

        categoria = intent.getIntExtra(BisilabasEjercicios_main.BisilabasAdaptador.BisilabasViewHolder.BISILABA_SELECCIONADA, 1);
        nombreNivel_Bisilabas = intent.getStringExtra(BisilabasEjercicios_main.BisilabasAdaptador.BisilabasViewHolder.BISILABA_NIVEL);
        InitAdapter(recycler_ejercicios, db.getCategoria_Pictogramas(categoria));

    }
    //</editor-fold>


    //<editor-fold desc="MÉTODO InitAdapter()">
    public void InitAdapter(RecyclerView mRecyclerView, List<Pictograma> items) {

        assert mRecyclerView != null;
        adapter = new EjerciciosBisilabas.EjercicioBisilabasAdaptador(items);

        setupRecyclerView(mRecyclerView, adapter);

    }
    //</editor-fold>

    //<editor-fold desc="MÉTODO setupRecyclerView()">
    private void setupRecyclerView(@NonNull RecyclerView recyclerView, EjerciciosBisilabas.EjercicioBisilabasAdaptador items) {

        recyclerView.setAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //recyclerView.setLayoutManager( new GridLayoutManager(this, 3,GridLayoutManager.VERTICAL, false));
        //recyclerView.setLayoutManager( new GridLayoutManager(this, 3,GridLayoutManager.HORIZONTAL, false));
    }
    //</editor-fold>

    //<editor-fold desc="CLASE ADAPTADOR EjercicioAdaptador">
    public class EjercicioBisilabasAdaptador extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Pictograma> mValues;


        //<editor-fold desc="CONSTRUCTOR VocalesAdaptador()">
        public EjercicioBisilabasAdaptador(List<Pictograma> mValues) {
            this.mValues = mValues;
        }
        //</editor-fold>


        //<editor-fold desc="MÉTODO onCreateViewHolder()">
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ejercicio_imagen, parent, false);

            return new EjerciciosBisilabas.EjercicioBisilabasAdaptador.EjerciciosViewHolder(view);
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO getHRM(int milliseconds )">
        private String getHRM(int milliseconds) {
            int seconds = (int) (milliseconds / 1000) % 60;
            int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
            int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
            String aux = "";
            aux = ((hours < 10) ? "0" + hours : hours) + ":" + ((minutes < 10) ? "0" + minutes : minutes) + ":" + ((seconds < 10) ? "0" + seconds : seconds);
            return aux;
        }
        //</editor-fold>

        //<editor-fold desc="MÉTODO displayStateAndIsAlive(Thread thread)">
        public void displayStateAndIsAlive(Thread thread) {
            // java.lang.Thread.State can be NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED
            System.out.println("ESTADOHilo");
            System.out.println("State:" + thread.getState());
            System.out.println("Is alive?:" + thread.isAlive());
        }
        //</editor-fold>


        //<editor-fold desc="METÓDO onBindViewHolder()">
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final Pictograma object = mValues.get(position);
            final int nombre = object.getIdSonido();
            final int habilitado = object.getHabilitado();
            final boolean habilitadoEjercicio = estadoPictograma(habilitado);


            ((EjerciciosBisilabas.EjercicioBisilabasAdaptador.EjerciciosViewHolder) holder).tv_cv_ejercicio.setImageResource(Integer.parseInt( object.getNombre() ) );


            ((EjerciciosViewHolder) holder).tv_etiempo.setText("00:00:00");

            if (habilitadoEjercicio) {
                ((EjerciciosViewHolder) holder).iv_cv_bloqueado.setVisibility(View.INVISIBLE);

                //<editor-fold desc="SE HABILITAN LOS VIEWS DE LA INTERFAZ">
                ((EjerciciosViewHolder) holder).ll_ejercicio.setEnabled(habilitadoEjercicio);
                ((EjerciciosViewHolder) holder).ll_controles.setEnabled(habilitadoEjercicio);
                ((EjerciciosViewHolder) holder).tv_cv_ejercicio.setEnabled(habilitadoEjercicio);
                ((EjerciciosViewHolder) holder).tv_etiempo.setEnabled(habilitadoEjercicio);

                ((EjerciciosViewHolder) holder).card_view_controles.setEnabled(habilitadoEjercicio);
                ((EjerciciosViewHolder) holder).cv_ejercicio.setEnabled(habilitadoEjercicio);
                //</editor-fold>
                ((EjerciciosViewHolder) holder).fab.setVisibility(View.VISIBLE);

            } else {

                //<editor-fold desc="SE MUESTRAN LOS VIEWS DESABILITADOS">
                ((EjerciciosViewHolder) holder).iv_cv_bloqueado.setVisibility(View.VISIBLE);
                ((EjerciciosViewHolder) holder).ll_ejercicio.setEnabled(habilitadoEjercicio);
                ((EjerciciosViewHolder) holder).ll_controles.setEnabled(habilitadoEjercicio);
                ((EjerciciosViewHolder) holder).tv_cv_ejercicio.setEnabled(habilitadoEjercicio);
                ((EjerciciosViewHolder) holder).tv_etiempo.setEnabled(habilitadoEjercicio);
                ((EjerciciosViewHolder) holder).card_view_controles.setEnabled(habilitadoEjercicio);
                ((EjerciciosViewHolder) holder).cv_ejercicio.setEnabled(habilitadoEjercicio);
                //</editor-fold>
                ((EjerciciosViewHolder) holder).fab.setVisibility(View.INVISIBLE);
                ((EjerciciosViewHolder) holder).Ibtn_cv_ejercicio_siguiente.setVisibility(View.INVISIBLE);

            }

            ((EjerciciosViewHolder) holder).fab.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    flagBotonPlay++;
                    seguroDeVida++;

                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                    }

                    try {

                        mediaPlayer = MediaPlayer.create(getApplicationContext(), nombre);

                        hilo = new Thread(new Runnable() {
                            int i = 0;
                            int duracion = mediaPlayer.getDuration();
                            int posicionActual = -1;
                            boolean isMPPlaying = true;

                            public void run() {
                                while (isMPPlaying) {

                                    try {
                                        Thread.sleep(500);
                                        try {
                                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                public void onCompletion(MediaPlayer mp) {
                                                    ((EjerciciosBisilabas.EjercicioBisilabasAdaptador.EjerciciosViewHolder) holder).Ibtn_cv_ejercicio_siguiente.setVisibility(View.VISIBLE);
                                                    ((EjerciciosViewHolder) holder).tv_etiempo.setTextColor(getResources().getColor(R.color.color_respuestas));
                                                    ((EjerciciosViewHolder) holder).fab.setImageResource(R.drawable.ic_play);

                                                    isMPPlaying = false;
                                                    mediaPlayer.release();
                                                    mediaPlayer = null;
                                                    seguroDeVida = 0;
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        if (!isMPPlaying) break;
                                        else {
                                            posicionActual = mediaPlayer.getCurrentPosition();
                                        }

                                        if (seguroDeVida >= 10) {
                                            startActivity(new Intent(getApplicationContext(), IniciarNivel_main.class));
                                            displayStateAndIsAlive(hilo);
                                            hilo.interrupt();
                                            displayStateAndIsAlive(hilo);
                                            finish();

                                        }


                                        if (posicionActual == 0) i++;

                                        if (i == 2) {
                                            mediaPlayer.stop();
                                            break;
                                        }

                                        if (posicionActual >= 6500 && posicionActual <= 10500) {

                                            ((EjerciciosViewHolder) holder).tv_etiempo.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ((EjerciciosViewHolder) holder).tv_etiempo.setVisibility(View.VISIBLE);
                                                    ((EjerciciosViewHolder) holder).tv_etiempo.setText(getHRM(10500 - posicionActual));
                                                    ((EjerciciosViewHolder) holder).tv_etiempo.setTextColor(getResources().getColor(R.color.color_lugares));
                                                }
                                            });

                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    ((EjerciciosViewHolder) holder).iv_ejercicio_item_ejercicio.setImageResource(R.drawable.ic_niveles_respirar);
                                                    ((EjerciciosViewHolder) holder).iv_ejercicio_item_ejercicio.setVisibility(View.VISIBLE);
                                                }
                                            });
                                        }else if (posicionActual >= 10500 && posicionActual <= 14500) {

                                            ((EjerciciosViewHolder) holder).tv_etiempo.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ((EjerciciosViewHolder) holder).tv_etiempo.setVisibility(View.VISIBLE);
                                                    ((EjerciciosViewHolder) holder).tv_etiempo.setText(getHRM( 17500 - posicionActual ) );
                                                    ((EjerciciosViewHolder) holder).tv_etiempo.setTextColor(getResources().getColor(R.color.color_respuestas));
                                                }
                                            });

                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    ((EjerciciosViewHolder) holder).iv_ejercicio_item_ejercicio.setImageResource(R.drawable.ic_menu_guia_padre_technology);;
                                                }
                                            });
                                        } else if (posicionActual > 14500 && posicionActual <= 17500) {

                                            ((EjerciciosViewHolder) holder).tv_etiempo.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ((EjerciciosViewHolder) holder).tv_etiempo.setTextColor(getResources().getColor(R.color.color_bebidas));
                                                    ((EjerciciosViewHolder) holder).tv_etiempo.setText(getHRM( 17500 - posicionActual));
                                                }
                                            });

                                        } else {

                                            ((EjerciciosViewHolder) holder).tv_etiempo.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ((EjerciciosViewHolder) holder).tv_etiempo.setVisibility(View.INVISIBLE);
                                                }
                                            });

                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    ((EjerciciosViewHolder) holder).iv_ejercicio_item_ejercicio.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                        }

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }

                                displayStateAndIsAlive(hilo);
                                hilo.interrupt();
                                displayStateAndIsAlive(hilo);
                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    switch (flagBotonPlay) {
                        case 1:
                            hilo.start();
                            mediaPlayer.start();
                            ((EjerciciosViewHolder) holder).fab.setImageResource(R.drawable.ic_stop);
                            break;

                        case 2:
                            mediaPlayer.stop();
                            ((EjerciciosViewHolder) holder).fab.setImageResource(R.drawable.ic_play);
                            flagBotonPlay = 0;
                            break;
                    }

                }
            });

            //<editor-fold desc="IMPLEMETACIÓN DE BOTÓN SIGUIENTE">
            ((EjerciciosViewHolder) holder).Ibtn_cv_ejercicio_siguiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flagBotonPlay = 0;
                    seguroDeVida = 0;
                    ((EjerciciosBisilabas.EjercicioBisilabasAdaptador.EjerciciosViewHolder) holder).Ibtn_cv_ejercicio_siguiente.setVisibility(View.INVISIBLE);
                    nuevaPosicion = position + 1;
                    if (object.getCompletado() != 1) {

                        object.setCompletado(1);
                        int numEjercicios=db.count(categoria);
                        object.setProgreso(100/numEjercicios);
                        db.updatePictograma(object);

                        if (nuevaPosicion < mValues.size()) {
                            final Pictograma seteo = mValues.get(nuevaPosicion);
                            seteo.setHabilitado(1);
                            db.updatePictograma(seteo);
                            adapter.notifyDataSetChanged();
                        } else {
                            FragmentManager fragmentManager = getFragmentManager();
                            new SeccionTerminadaDialogoBisi().show(fragmentManager, "SeccionTerminadaDialog");
                            db.updateCampoPictograma(nombreNivel_Bisilabas, 1);
                        }

                    }

                    recycler_ejercicios.post(new Runnable() {
                        @Override
                        public void run() {

                            if (!((nuevaPosicion) == adapter.getItemCount()))
                                recycler_ejercicios.smoothScrollToPosition(nuevaPosicion);

                        }
                    });


                }
            });
            //</editor-fold>

        }
        //</editor-fold>

        //<editor-fold desc="MÉTÓDO estadoPictograma(int i)">
        public boolean estadoPictograma(int i) {
            if (i == 1) {
                return true;
            } else {
                return false;
            }
        }
        //</editor-fold>

        //<editor-fold desc="METÓDO getItemCount()">
        @Override
        public int getItemCount() {
            return mValues.size();
        }
        //</editor-fold>

        //<editor-fold desc="CLASE VocalesViewHolder">
        public class EjerciciosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView tv_etiempo;
            private ImageView tv_cv_ejercicio, iv_cv_bloqueado,fab,iv_ejercicio_item_ejercicio;
            private CardView card_view_controles, cv_ejercicio;
            private LinearLayout ll_ejercicio, ll_controles;
            private ImageButton Ibtn_cv_ejercicio_siguiente;

            public EjerciciosViewHolder(View itemView) {
                super(itemView);

                iv_cv_bloqueado = (ImageView) itemView.findViewById(R.id.iv_cv_bloqueado);
                tv_cv_ejercicio = (ImageView) itemView.findViewById(R.id.tv_cv_ejercicio);
                tv_etiempo = (TextView) itemView.findViewById(R.id.tv_etiempo);

                ll_ejercicio = (LinearLayout) itemView.findViewById(R.id.ll_ejercicio);
                ll_controles = (LinearLayout) itemView.findViewById(R.id.ll_controles);
                fab = (ImageView) itemView.findViewById(R.id.fab);
                iv_ejercicio_item_ejercicio = (ImageView) itemView.findViewById(R.id.iv_ejercicio_item_ejercicio);

                card_view_controles = (CardView) itemView.findViewById(R.id.card_view_controles);
                cv_ejercicio = (CardView) itemView.findViewById(R.id.cv_ejercicio);
                Ibtn_cv_ejercicio_siguiente = (ImageButton) itemView.findViewById(R.id.Ibtn_cv_ejercicio_siguiente);
                cv_ejercicio.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
            }
        }
        //</editor-fold>
    }
    //</editor-fold>


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_LONG).show();
        if(mediaPlayer != null && mediaPlayer.getCurrentPosition()>0)
            mediaPlayer.stop();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(getApplicationContext(), "onStop", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Toast.makeText(getApplicationContext(), "onPause", Toast.LENGTH_LONG).show();
        if(mediaPlayer != null && mediaPlayer.getCurrentPosition()>0)
            mediaPlayer.pause();

    }

    @Override
    protected void onResume(){
        super.onResume();
//        Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_LONG).show();
        if(mediaPlayer != null && mediaPlayer.getCurrentPosition()>0 )
            mediaPlayer.start();
    }

}
