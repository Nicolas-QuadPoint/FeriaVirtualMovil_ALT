package com.feriantes4dawin.feriavirtualmovil.ui.proccesses;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.models.Ventas;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MyProcessesViewModel 
 * 
 * Clase que actúa como intermediario para ofrecer y enviar 
 * datos entre el fragmento MyProcessesFragment y la fuente de datos. 
 */
public class MyProcessesViewModel extends ViewModel {

    private FeriaVirtualApplication feriaVirtualApplication;
    private VentaRepository ventaRepository;

    private LiveData<Ventas> datosVenta;
    private MutableLiveData<Ventas> datosMutablesVenta;

    public MyProcessesViewModel(VentaRepository ventaRepository, FeriaVirtualApplication feriaVirtualApplication){

        this.feriaVirtualApplication = feriaVirtualApplication;
        this.ventaRepository = ventaRepository;

        this.datosMutablesVenta = new MutableLiveData<>();
        this.datosVenta = datosMutablesVenta;

    }

    /**
     * 
     * Pregunta por los procesos de ventas en los que el usuario 
     * (Productor o Transportista) haya participado en algún momento, 
     * independiente si fue viable o no, con el fin de consultar 
     * información. 
     * 
     * TODO: Agregar la lógica que falta para que esto funcione!
     * 
     * @return Un objeto LiveData para vigilar por cambios. 
     */
    LiveData<Ventas> getDatosVenta(){

        obtenerHistorialDeVentas();

        return datosVenta;
    }

    private void obtenerHistorialDeVentas(){

        try {

            Call<Ventas> ruc = ventaRepository.getHistorialVentas();

            ruc.enqueue(new Callback<Ventas>() {
                @Override
                public void onResponse(Call<Ventas> call, Response<Ventas> response) {

                    if(response.isSuccessful() && response.body() != null){

                        datosMutablesVenta.setValue(response.body());

                    } else {

                        Log.e("MY_PROCESSESS_VIEWMODEL","No hay ventas que mostrar" );
                        datosMutablesVenta.setValue(null);
                    }

                }

                @Override
                public void onFailure(Call<Ventas> call, Throwable t) {

                    Log.e(
                            "MY_PROCESSESS_VIEWMODEL",
                            String.format(
                                    "No se pudo recuperar el historial de procesos de venta!!!: %s",
                                    t.toString()
                            )
                    );

                    datosMutablesVenta.setValue(null);

                }
            });



        } catch(Exception ex){

            Log.e(
                "MY_PROCESSESS_VIEWMODEL",
                String.format(
                        "No se pudo recuperar el historial de procesos de venta!!!: %s",
                        ex.toString()
                )
            );

            datosMutablesVenta.setValue(null);

        }


    }


}