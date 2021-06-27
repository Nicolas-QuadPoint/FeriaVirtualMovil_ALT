package com.feriantes4dawin.feriavirtualmovil.ui.sales;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.models.Ventas;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * CurrentSalesViewModel 
 * 
 * Clase que maneja los datos para el fragmento 
 * CurrentSalesFragment. 
 */
public class CurrentSalesViewModel extends ViewModel {

    /**
     * Origen de datos para ventas. 
     */
    private VentaRepository ventaRepository;

    /**
     * Instancia de Application para algunas funciones. 
     */
    private FeriaVirtualApplication fva;

    /**
     * Ideal para traspasar JSON en algunos casos
     */
    private Gson convertidorJSON;

    /**
     * Puente de datos para usarse en 
     * CurrentSalesFragment. 
     */
    private LiveData<Ventas> datosVenta;
    private MutableLiveData<Ventas> datosMutablesVenta;


    public CurrentSalesViewModel(VentaRepository ventaRepository, FeriaVirtualApplication fva, Gson convertidorJSON){

        this.ventaRepository = ventaRepository;
        this.fva = fva;
        this.convertidorJSON = convertidorJSON;

        this.datosMutablesVenta = new MutableLiveData<Ventas>();
        this.datosVenta = datosMutablesVenta;
    }

    /**
     * Realiza la petición para obtener datos de venta, y 
     * mientras la operación esté en curso, se puede vigilar 
     * el estado del objeto LiveData retornado en espera 
     * de cambios. 
     * 
     * @return Un objeto LiveData para vigilarlo. 
     */
    public LiveData<Ventas> getDatosVenta(Usuario u){

        //Operación asíncrona. No esperar a que termine.
        cargarListaVentas(u);

        return datosVenta;

    }

    /**
     * Rutina asíncrona que recupera los datos de venta de 
     * la fuente, y dependiendo de su éxito, el puente de 
     * datos recibirá un valor nulo, o una lista de objetos 
     * Venta para que el fragmento CurrentSalesFragment pueda 
     * procesar. 
     */
    private void cargarListaVentas(Usuario u){

        Call<Ventas> ruc;
        try{

            ruc = ventaRepository.getVentasDisponibles(u);

            if(ruc == null){

                datosMutablesVenta.setValue(null);

            } else {

                ruc.enqueue(new Callback<Ventas>() {
                    @Override
                    public void onResponse(Call<Ventas> call, Response<Ventas> response) {

                        Log.i("CUR_SALES_VIEW_MODEL",String.format("Código de respuesta http: %d",response.code()));

                        if(response.isSuccessful() && response.body().ventas != null && response.body().ventas.size() > 0){

                            datosMutablesVenta.setValue(response.body());

                        } else {

                            datosMutablesVenta.setValue(null);

                        }

                    }

                    @Override
                    public void onFailure(Call<Ventas> call, Throwable t) {

                        Log.e("CUR_SALES_VIEW_MODEL",String.format("No se pudo recuperar datos de venta!: %s",t.toString()));
                        datosMutablesVenta.setValue(null);

                    }

                });

            }


        } catch(Exception ex){

            Log.e("CUR_SALES_VIEW_MODEL",String.format("No se pudo recuperar datos de venta!: %s",ex.toString()));
            datosMutablesVenta.setValue(null);

        }

    }


}