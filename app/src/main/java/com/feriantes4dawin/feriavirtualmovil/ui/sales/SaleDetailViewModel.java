package com.feriantes4dawin.feriavirtualmovil.ui.sales;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetalleVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallesPujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.Producto;
import com.feriantes4dawin.feriavirtualmovil.data.models.Productos;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;
import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;
import com.feriantes4dawin.feriavirtualmovil.ui.util.SimpleAction;

import java.util.ArrayList;

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
    private VentaRepository ventaRepository;

    /**
     * Fuente de datos para subastas/procesos
     */
    private SubastaRepository subastaRepository;

    /**
     * Referencia a la aplicación. 
     */
    private FeriaVirtualApplication feriaVirtualApplication;

    /**
     * Puente de datos para SaleDetailActivity.
     * @link https://developer.android.com/topic/libraries/architecture/livedata
     */
    public LiveData<Venta> datosVenta;
    private MutableLiveData<Venta> datosMutablesVenta;

    public LiveData<DetallesPujaSubastaProductor> datosProductosVenta;
    private MutableLiveData<DetallesPujaSubastaProductor> datosMutablesProductosVenta;

    /**
     * Crea un objeto SaleDetailViewModel
     * 
     * @param ventaRepository Objeto VentaRepository para conectarse a 
     * la fuente de datos. 
     * @param feriaVirtualApplication Instancia de Application. 
     */
    public SaleDetailViewModel(VentaRepository ventaRepository,
                               SubastaRepository subastaRepository,
                               FeriaVirtualApplication feriaVirtualApplication){

        this.ventaRepository = ventaRepository;
        this.subastaRepository = subastaRepository;
        this.feriaVirtualApplication = feriaVirtualApplication;

        this.datosMutablesVenta = new MutableLiveData<>();
        this.datosVenta = datosMutablesVenta;

        this.datosMutablesProductosVenta = new MutableLiveData<>();
        this.datosProductosVenta = datosMutablesProductosVenta;

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
        internalGetDatosVenta(venta_id);

        return datosVenta;

    }

    public LiveData<DetallesPujaSubastaProductor> borrarPuja(Integer id_venta, Integer id_productor, DetallePujaSubastaProductor p){

        internalBorrarPuja(id_venta,id_productor,p);

        return datosProductosVenta;
    }

    public LiveData<DetallesPujaSubastaProductor> getProductosVenta(Integer id_venta){

        internalGetProductosVenta(id_venta,-1);
        return datosProductosVenta;

    }

    public LiveData<DetallesPujaSubastaProductor> getProductosVenta(Integer id_venta,Integer id_productor){

        internalGetProductosVenta(id_venta,id_productor);

        return datosProductosVenta;
    }

    public void finalizarEncargoProductos(Integer id_venta,SimpleAction accion){
        try {

            Call<ResultadoID> puc = subastaRepository.finalizarEncargoProductos(id_venta);

            puc.enqueue(new Callback<ResultadoID>() {
                @Override
                public void onResponse(Call<ResultadoID> call, Response<ResultadoID> response) {

                    if(response.isSuccessful() && response.body() != null){

                        if(accion != null){
                            accion.doAction(  (response.body().id_resultado  == 1)? 1 : 0  );
                        }

                    } else {

                        if(accion != null){
                            accion.doAction(0  );
                        }

                    }

                }

                @Override
                public void onFailure(Call<ResultadoID> call, Throwable t) {
                    Log.e("SALE_DETAIL_ACT",String.format("No se pudo finalizar el transporte!: %s",t.toString()));
                    if(accion != null){
                        accion.doAction(Integer.valueOf(0));
                    }
                }
            });

        } catch(Exception ex){
            Log.e("SALE_DETAIL_ACT",String.format("No se pudo concretar el transporte!: %s",ex.toString()));
        }
    }

    public void transportarEncargoProductos(Integer id_venta, SimpleAction accion){

        try {

            Call<ResultadoID> puc = subastaRepository.transportarEncargoProductos(id_venta);

            puc.enqueue(new Callback<ResultadoID>() {
                @Override
                public void onResponse(Call<ResultadoID> call, Response<ResultadoID> response) {

                    if(response.isSuccessful() && response.body() != null){

                        if(accion != null){
                            accion.doAction(  (response.body().id_resultado  == 1)? 1 : 0  );
                        }

                    } else {

                        if(accion != null){
                            accion.doAction(0  );
                        }

                    }

                }

                @Override
                public void onFailure(Call<ResultadoID> call, Throwable t) {
                    Log.e("SALE_DETAIL_ACT",String.format("No se pudo concretar el transporte!: %s",t.toString()));
                    if(accion != null){
                        accion.doAction(Integer.valueOf(0));
                    }
                }
            });

        } catch(Exception ex){
            Log.e("SALE_DETAIL_ACT",String.format("No se pudo concretar el transporte!: %s",ex.toString()));
        }

    }

    private void internalGetProductosVenta(Integer id_venta,Integer id_productor){

        try {
            Call<DetallesPujaSubastaProductor> puc;
            if(id_productor >= 0){
                puc = subastaRepository.getProductosSubasta(id_venta,id_productor);
            } else {
                puc = subastaRepository.getTodosLosProductosSubasta(id_venta);
            }

            puc.enqueue(new Callback<DetallesPujaSubastaProductor>() {

                @Override
                public void onResponse(Call<DetallesPujaSubastaProductor> call, Response<DetallesPujaSubastaProductor> response) {

                    datosMutablesProductosVenta.setValue(response.body());

                }

                @Override
                public void onFailure(Call<DetallesPujaSubastaProductor> call, Throwable t) {
                    Log.e("SALE_DETAIL_VIEWMODEL",String.format("No se pudo obtener productos venta!: %s",t.toString()));
                    datosMutablesProductosVenta.setValue(null);
                }
            });


        } catch(Exception ex) {

            Log.e("SALE_DETAIL_VIEWMODEL",String.format("No se pudo obtener productos venta!: %s",ex.toString()));
            datosMutablesProductosVenta.setValue(null);
        }

    }

    private void internalBorrarPuja(Integer id_venta,Integer id_productor, DetallePujaSubastaProductor p){

        try {

            Call<DetallesPujaSubastaProductor> puc = subastaRepository.removerPujaProductor(
                    id_venta,
                    p.id_detalle);

            puc.enqueue(new Callback<DetallesPujaSubastaProductor>() {
                @Override
                public void onResponse(Call<DetallesPujaSubastaProductor> call, Response<DetallesPujaSubastaProductor> response) {
                    if(response.isSuccessful() && response.body() != null){

                        datosMutablesProductosVenta.setValue(response.body());

                    } else {

                        //Si falla, entonces no modificamos la lista, y retornamos solamente una
                        //copia
                        datosMutablesProductosVenta.setValue(
                            new DetallesPujaSubastaProductor( datosMutablesProductosVenta.getValue().pujas )
                        );

                    }
                }

                @Override
                public void onFailure(Call<DetallesPujaSubastaProductor> call, Throwable t) {
                    Log.e("SALE_DETAIL_VIEWMODEL",String.format("No se pudo eliminar puja!: %s",t.toString()));
                    //Si falla, entonces no modificamos la lista, y retornamos solamente una
                    //copia
                    datosMutablesProductosVenta.setValue(
                            new DetallesPujaSubastaProductor( datosMutablesProductosVenta.getValue().pujas )
                    );
                }
            });


        } catch(Exception ex) {

            Log.e("SALE_DETAIL_VIEWMODEL",String.format("No se pudo eliminar puja!: %s",ex.toString()));
            //Si falla, entonces no modificamos la lista, y retornamos solamente una
            //copia
            datosMutablesProductosVenta.setValue(
                    new DetallesPujaSubastaProductor( datosMutablesProductosVenta.getValue().pujas )
            );
        }

    }

    /**
     * Método que recupera el detalle de la venta. Solo usable 
     * detro de esta clase. Cabe mencionar que este método es 
     * asíncrono, por lo que no detiene el hilo de ejecución.
     * 
     * @param venta_id ID de la venta a buscar, no null.
     */
    private void internalGetDatosVenta(Integer venta_id){

        if(venta_id != null){

            Call<Venta> puc = ventaRepository.getInfoVenta(venta_id);
            
            puc.enqueue(new Callback<Venta>() {

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

        } else {

            datosMutablesVenta.setValue(null);

        }

    }

}