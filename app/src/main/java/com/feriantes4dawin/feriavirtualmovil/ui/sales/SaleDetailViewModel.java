package com.feriantes4dawin.feriavirtualmovil.ui.sales;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * SaleDetailViewModel 
 * 
 * Intermediario que facilita a la actividad SaleDetailActivity 
 * los datos necesarios para que los muestre. 
 * 
 * Esta clase no está hecha para que sea instanciada explícitamente 
 * por el usuario, sino que debe usarse junto a un objeto 
 * SaleDetailViewModelFactory para estar en regla. 
 */
public class SaleDetailViewModel extends ViewModel {

    /**
     * Fuente de datos para Ventas 
     */
    VentaRepository ventaRepository; 

    /**
     * Referencia a la aplicación. 
     */
    FeriaVirtualApplication feriaVirtualApplication;

    /**
     * Puente de datos para SaleDetailActivity. 
     * @see https://developer.android.com/topic/libraries/architecture/livedata
     */
    LiveData<Venta> datosVenta;
    MutableLiveData<Venta> datosMutablesVenta;

    /**
     * Crea un objeto SaleDetailViewModel
     * 
     * @param ventaRepository Objeto VentaRepository para conectarse a 
     * la fuente de datos. 
     * @param feriaVirtualApplication Instancia de Application. 
     */
    public SaleDetailViewModel(VentaRepository ventaRepository, FeriaVirtualApplication feriaVirtualApplication){

        this.ventaRepository = ventaRepository;
        this.feriaVirtualApplication = feriaVirtualApplication;

        this.datosMutablesVenta = new MutableLiveData<Venta>();
        this.datosVenta = datosMutablesVenta;

    }

    /**
     * Interactúa con la fuente de datos y recupera el detalle 
     * de la venta cuyo ID corresponda con el entregado. 
     * Se debe vigilar el objeto retornado para actualizar la 
     * vista oportunamente. 
     * 
     * @param venta_id ID de la venta a buscar, no null.
     * @return Un objeto LiveData, el cual se debe vigilar en 
     * caso de encontrar resultados, o no.
     */
    public LiveData<Venta> getDatosVenta(Integer venta_id){

        /* Llamo a la rutina asíncrona */
        cargarDatosVenta(venta_id);

        return datosVenta;

    }

    /**
     * Método que recupera el detalle de la venta. Solo usable 
     * detro de esta clase. Cabe mencionar que este método es 
     * asíncrono, por lo que no detiene el hilo de ejecución.
     * 
     * @param venta_id ID de la venta a buscar, no null.
     */
    private void cargarDatosVenta(Integer venta_id){

        if(venta_id != null){

            Call<Venta> ventaCall = ventaRepository.getInfoVenta(venta_id);
            
            ventaCall.enqueue(new Callback<Venta>() {

                @Override
                public void onResponse(Call<Venta> call, Response<Venta> response) {

                    if(response.isSuccessful() && response.body() != null) {

                        datosMutablesVenta.setValue(response.body());

                    } else {

                        datosMutablesVenta.setValue(null);

                    }

                }

                @Override
                public void onFailure(Call<Venta> call, Throwable t) {
                    Log.e("SALE_DETAIL_VIEW_MODEL", "Error! no se pudo recuperar datos de venta!: " + t.toString());
                    datosMutablesVenta.setValue(null);
                }
            });

        } else{

            datosMutablesVenta.setValue(null);

        }

    }

}