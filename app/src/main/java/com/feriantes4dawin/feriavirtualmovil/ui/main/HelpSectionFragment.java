package com.feriantes4dawin.feriavirtualmovil.ui.main;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * HelpSectionFragment 
 * 
 * Fragmento cuya única función es mostrar el manual de usuario, 
 * en formato pdf con la ayuda de un webview. 
 */
public class HelpSectionFragment extends Fragment {

    private static final String nombreArchivo = "manual_usuario_2.pdf";
    private float factorDeEscalado = 1.0f;
    private ScaleGestureDetector detectorDeEscalado;
    private GestureDetector.SimpleOnGestureListener detectorGestos;
    private int dedoActivo = MotionEvent.INVALID_POINTER_ID;

    private float actualX = 0.0f;
    private float actualY = 0.0f;

    private boolean modoZoom;
    private boolean modoMultitactil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_help, container, false);

        SwipeRefreshLayout miSwipe = v.findViewById(R.id.fmp_swipeFMP);

        miSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //No recargaremos nada por ahora!
                miSwipe.setRefreshing(false);
            }
        });


        /**
         * Código adaptado de:
         * https://medium.com/@chahat.jain0/rendering-a-pdf-document-in-android-activity-fragment-using-pdfrenderer-442462cb8f9a
         *
         */

        try{

            ParcelFileDescriptor archivoFinal;
            InputStream flujoEntradaArchivo;
            FileOutputStream flujoSalidaArchivo;
            final byte buffer[] = new byte[1024];
            int tamanoArchivoBytes;
            File archivoEntrada = new File(getContext().getCacheDir(),nombreArchivo);
            RecyclerView rv;

            //Si el archivo no existe en cache, entoneces vamos a dejarlo en cache.
            if(!archivoEntrada.exists()){

                //PdfRenderer no aguanta archivos sin procesar, por lo que hay que
                //entregarle un flujo de bytes descomprimidos y cargados en cache.
                flujoEntradaArchivo = requireContext().getResources().openRawResource(R.raw.manual_usuario_2);
                flujoSalidaArchivo = new FileOutputStream(archivoEntrada);

                //Procesamos el archivo, en pedazos de 1kb, escribiéndolo en
                //flujoSalidaArchivo. El resultado irá finalmente al objeto
                //
                while((tamanoArchivoBytes = flujoEntradaArchivo.read(buffer)) != -1){
                    flujoSalidaArchivo.write(buffer,0,tamanoArchivoBytes);
                }
                //Cerramos los flujos para evitar fugas de memoria.
                flujoEntradaArchivo.close();
                flujoSalidaArchivo.close();

            }

            //Vamos a crear el archivo que sera enviado al lector pdf.
            archivoFinal = ParcelFileDescriptor.open(archivoEntrada, ParcelFileDescriptor.MODE_READ_ONLY);

            //Si todo lo anterior fue bien, entonces no hay que temer y
            //Crear nuestro pequeño experimento.
            if(archivoFinal != null){

                LectorPDFCustomAdapter miAdapter = new LectorPDFCustomAdapter(archivoFinal);
                rv = v.findViewById(R.id.rvLectorPDF);

                rv.setAdapter(miAdapter);
                rv.setLayoutManager(new LinearLayoutManager(requireContext()));
              //  Snackbar.make(requireView(),"Archivo cargado!",Snackbar.LENGTH_SHORT).show();
                Log.i("HELP_SECTION_FRAGMENT","El archivo debería verse...");
            } else {

                //Snackbar.make(requireView(),"El archivo no se pudo abrir...",Snackbar.LENGTH_SHORT).show();
                Log.e("HELP_SECTION_FRAGMENT","El archivo no se pudo abrir!!!");
            }


        }catch(Exception ex){

            //Snackbar.make(v,"El archivo no se pudo abrir..."+ex.toString(),Snackbar.LENGTH_SHORT).show();
            Log.e("HELP_SECTION_FRAGMENT",ex.toString());
        }

        /**
         * Agregamos manejo de ciertos eventos de desplazamiento para
         * la vista!
         */
        this.detectorGestos = new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onDoubleTap(MotionEvent e) {

                factorDeEscalado = modoZoom? 1.0f : 5.0f;
                modoZoom = factorDeEscalado > 1.0f;
                detectorDeEscalado.onTouchEvent(e);

                return true;
            }

        };


        /**
         * Agregamos efectos de escalado en la vista!
         * https://www.tutorialspoint.com/android-imageview-zoom-in-and-zoom-out
         */
        View rv = v.findViewById(R.id.rvLectorPDF);

        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //gestiona el pellizco
                detectorDeEscalado.onTouchEvent(event);


                int accionActual = event.getActionMasked();

                switch(accionActual){

                    /* Un dedo toca la pantalla */
                    case MotionEvent.ACTION_DOWN: {
                        int nuevoDedo = event.getActionIndex();
                        actualX = event.getX(nuevoDedo);
                        actualY = event.getY(nuevoDedo);
                        dedoActivo = event.getPointerId(0);

                    } break;
                    case MotionEvent.ACTION_POINTER_DOWN:{
                        modoMultitactil = true;
                    } break;
                    /* Si el gesto termina, o si el último dedo deja la pantalla */
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {

                        modoMultitactil = false;
                        dedoActivo = MotionEvent.INVALID_POINTER_ID;

                    } break;
                    /* UN dedo se mueve por la pantalla */
                    case MotionEvent.ACTION_MOVE: {

                        if(modoMultitactil == false){

                            Log.i("HELP_SECTION_FRAG","NO es multitactil esta cosa!");

                            int dedo = event.findPointerIndex(dedoActivo);

                            float nuevaX = event.getX(dedo);
                            float nuevaY = event.getY(dedo);


                            if(modoZoom){

                                /**
                                 * Deberiamos mover el viewport (traslado) de la vista
                                 * segun las coordenadas?
                                 */
                                rv.setTranslationX( rv.getTranslationX() + ((nuevaX - actualX)) );
                                //Log.i("HELP_SECTION_FRAG","Valor de X en dedo: "+new Float(actualX));

                            }

                            actualX = nuevaX;
                            actualY = nuevaY;

                        }

                    } break;
                    /* Un dedo deja la pantalla */
                    case MotionEvent.ACTION_POINTER_UP: {

                        int accionDedo = event.getActionIndex();
                        int dedo = event.getPointerId(accionDedo);

                        //El dedo levantado es el activo?
                        if(dedo == dedoActivo){

                            int nuevoDedo = dedo == 0? 1 : 0;

                            //Actualizamos las coordenadas de ubicacion
                            actualX = event.getX(nuevoDedo);
                            actualY = event.getY(nuevoDedo);

                            //Y dejamos el dedo activo como el último
                            dedoActivo = event.getPointerId(nuevoDedo);

                            //Solo hay un dedo en pantalla?
                            modoMultitactil = nuevoDedo == 0;

                        } else {

                            modoMultitactil = true;

                        }

                    } break;
                }

                //Usamos false para delegar eventos de gestos a las vistas
                //hijas.
                return false;
            }
        });



        this.detectorDeEscalado = new ScaleGestureDetector(requireContext(),
                new ScaleGestureDetector.SimpleOnScaleGestureListener(){

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return super.onScaleBegin(detector);

            }

            @Override
            public boolean onScale (ScaleGestureDetector detector) {

                /**
                 * Aquí nace la magia!!
                 */
                factorDeEscalado *= detectorDeEscalado.getScaleFactor();
                /**
                 * Evitamos que la vista tenga un tamaño menor al normal. Solo se
                 * permite acercarse a un tamaño del 10 veces el original.
                 */
                factorDeEscalado = Math.max(1.0f, Math.min(factorDeEscalado, 10.0f));

                /**
                 * Escalamos las dimensiones de la vista
                 */
                rv.setScaleX(factorDeEscalado);
                rv.setScaleY(factorDeEscalado);

                /**
                 * Si el factor de escalado es mayor al orignal
                 * entonces se la vista está aumentada. Con esto es posible
                 * saber si es necesario calcular la nueva posición horizontal
                 * del viewport (punto de vista) para recorrer la vista aumentada.
                 */
                modoZoom = factorDeEscalado > 1.0f;

                if(modoZoom == false) {
                    rv.setTranslationX(0.0f);
                }

                //Le decimios que la vista está 'sucia' para que vuelva a dibujarse
                rv.invalidate();

                return true;

            }
        });



        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {

        super.onAttach(context);

    }


    //public class ManejadorDeGestos extends
}