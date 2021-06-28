package com.feriantes4dawin.feriavirtualmovil.ui.auction;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallesPujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.Productos;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaTransportista;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujasSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujasSubastaTransportista;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;
import com.feriantes4dawin.feriavirtualmovil.data.models.Subasta;
import com.feriantes4dawin.feriavirtualmovil.data.repos.ProductoRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;
import com.feriantes4dawin.feriavirtualmovil.ui.util.SimpleAction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionViewModel extends ViewModel {

    public FeriaVirtualApplication aplicacion;

    /* Fuentes de datos */
    public VentaRepository ventaRepository;
    public UsuarioRepository usuarioRepository;
    public SubastaRepository subastaRepository;
    public ProductoRepository productoRepository;

    /* Espejo de datos para transportistas */
    public LiveData<PujasSubastaTransportista> datosPujasTransportista;
    public MutableLiveData<PujasSubastaTransportista> datosMutablesPujasTransportista;

    /* Espejo de datos para la puja individual transportista */
    public LiveData<PujaSubastaTransportista> datosPujaTransportista;
    public MutableLiveData<PujaSubastaTransportista> datosMutablesPujaTransportista;

    /* Espejo de datos para la puja individual productor */
    public LiveData<PujaSubastaProductor> datosPujaProductor;
    public MutableLiveData<PujaSubastaProductor> datosMutablesPujaProductor;

    /* Espejo de datos para productores */
    public LiveData<PujasSubastaProductor> datosPujasProductor;
    public MutableLiveData<PujasSubastaProductor> datosMutablesPujasProductor;

    /* Espejo de datos para productos */
    public LiveData<Productos> datosProductos;
    public MutableLiveData<Productos> datosMutablesProductos;


    public AuctionViewModel(
            FeriaVirtualApplication aplicacion,
            VentaRepository ventaRepository,
            UsuarioRepository usuarioRepository,
            SubastaRepository subastaRepository,
            ProductoRepository productoRepository){

        this.aplicacion = aplicacion;

        this.ventaRepository = ventaRepository;
        this.usuarioRepository = usuarioRepository;
        this.subastaRepository = subastaRepository;
        this.productoRepository = productoRepository;

        this.datosMutablesPujasProductor = new MutableLiveData<>();
        this.datosMutablesPujaProductor = new MutableLiveData<>();
        this.datosMutablesPujaTransportista = new MutableLiveData<>();
        this.datosMutablesPujasTransportista = new MutableLiveData<>();
        this.datosMutablesProductos = new MutableLiveData<>();

        this.datosPujaProductor = datosMutablesPujaProductor;
        this.datosPujasProductor = datosMutablesPujasProductor;
        this.datosPujaTransportista = datosMutablesPujaTransportista;
        this.datosPujasTransportista = datosMutablesPujasTransportista;
        this.datosProductos = datosMutablesProductos;

    }

    public LiveData<Productos> obtenerProductos(){

        cargarProductos();

        return datosProductos;

    }

    private void cargarProductos(){

        try{

            Call<Productos> reqProductos = productoRepository.getProductos();

            reqProductos.enqueue(new Callback<Productos>() {
                @Override
                public void onResponse(Call<Productos> call, Response<Productos> response) {

                    if(response.isSuccessful()){

                        datosMutablesProductos.setValue(response.body());

                    } else {

                        datosMutablesProductos.setValue(null);

                    }


                }

                @Override
                public void onFailure(Call<Productos> call, Throwable t) {
                    Log.e("AUCTION_VIEW_MODEL",String.format("Ocurrió un error en pedirProductos!: %s",t.toString()));
                    datosMutablesProductos.setValue(null);
                }
            });


        }catch(Exception ex){

            Log.e("AUCTION_VIEW_MODEL",String.format("Ocurrió un error en pedirProductos!: %s",ex.toString()));
            datosMutablesProductos.setValue(null);

        }

    }


    public void pedirPujasSubastaTransportista(Subasta subasta){


    }

    public void pedirPujasSubastaProductor(Subasta subasta){

    }


    public void pujarSubastaProductor(DetallePujaSubastaProductor pujaSubastaProductor, SimpleAction accion){

        try{
            Call<ResultadoID> resultado = subastaRepository.pujarProductoSubastaProductor(pujaSubastaProductor.id_venta,pujaSubastaProductor);
            resultado.enqueue(new Callback<ResultadoID>() {
                @Override
                public void onResponse(Call<ResultadoID> call, Response<ResultadoID> response) {

                    if(response.isSuccessful() && response.body() != null){

                        if(accion != null){
                            accion.doAction(response.body().id_resultado);
                        }

                    } else {

                        if(accion != null){
                            accion.doAction(Integer.valueOf(0));
                        }

                    }

                }

                @Override
                public void onFailure(Call<ResultadoID> call, Throwable t) {

                    if(accion != null){
                        accion.doAction(Integer.valueOf(0));
                    }

                    Log.e("AUCTION_VIEW_MODEL",String.format("Fallo la puja de productor!!!: %s",t.toString()));
                }

            });

        } catch(Exception ex){

            Log.e("AUCTION_VIEW_MODEL",String.format("Fallo la puja de productor!!!: %s",ex.toString()));

            if(accion != null){
                accion.doAction(Integer.valueOf(0));
            }
        }
    }

    public void modificarPujaProductor(DetallePujaSubastaProductor detalle,SimpleAction accion){

        try{

            Call<ResultadoID> resultado = subastaRepository.modificarPujaProductor(
                detalle.id_venta,
                detalle);

            resultado.enqueue(new Callback<ResultadoID>() {
                @Override
                public void onResponse(Call<ResultadoID> call, Response<ResultadoID> response) {

                    if(response.isSuccessful() && response.body() != null){

                        if(accion != null){
                            accion.doAction(response.body().id_resultado);
                        }

                    } else {

                        if(accion != null){
                            accion.doAction(Integer.valueOf(0));
                        }

                    }

                }

                @Override
                public void onFailure(Call<ResultadoID> call, Throwable t) {

                    if(accion != null){
                        accion.doAction(Integer.valueOf(0));
                    }

                    Log.e("AUCTION_VIEW_MODEL",String.format("Fallo la puja de productor!!!: %s",t.toString()));
                }

            });

        } catch(Exception ex){

            Log.e("AUCTION_VIEW_MODEL",String.format("Fallo la puja de productor!!!: %s",ex.toString()));

            if(accion != null){
                accion.doAction(Integer.valueOf(0));
            }
        }

    }

    public void removerPujaProductor(DetallePujaSubastaProductor detalle, SimpleAction accion){

        try{

            Call<DetallesPujaSubastaProductor> resultado = subastaRepository.removerPujaProductor(
                    detalle.id_venta,
                    detalle.id_detalle);

            resultado.enqueue(new Callback<DetallesPujaSubastaProductor>() {
                @Override
                public void onResponse(Call<DetallesPujaSubastaProductor> call, Response<DetallesPujaSubastaProductor> response) {

                    if(response.isSuccessful() && response.body() != null){

                        if(accion != null){
                            accion.doAction(response.body().pujas != null? 1 : 0);
                        }

                    } else {

                        if(accion != null){
                            accion.doAction(Integer.valueOf(0));
                        }

                    }

                }

                @Override
                public void onFailure(Call<DetallesPujaSubastaProductor> call, Throwable t) {

                    if(accion != null){
                        accion.doAction(Integer.valueOf(0));
                    }

                    Log.e("AUCTION_VIEW_MODEL",String.format("Fallo la puja de productor!!!: %s",t.toString()));
                }

            });

        } catch(Exception ex){

            Log.e("AUCTION_VIEW_MODEL",String.format("Fallo la puja de productor!!!: %s",ex.toString()));

            if(accion != null){
                accion.doAction(Integer.valueOf(0));
            }
        }

    }

    public void pujarSubastaTransportista(PujaSubastaTransportista pujaSubastaTransportista){

    }

}
