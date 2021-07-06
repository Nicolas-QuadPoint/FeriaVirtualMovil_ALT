package com.feriantes4dawin.feriavirtualmovil.ui.auction;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallesPujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.Productos;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;
import com.feriantes4dawin.feriavirtualmovil.util.FeriaVirtualConstants;
import com.feriantes4dawin.feriavirtualmovil.util.SimpleAction;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionViewModel extends ViewModel {

    public FeriaVirtualApplication aplicacion;

    /* Fuentes de datos */
    public VentaRepository ventaRepository;
    public UsuarioRepository usuarioRepository;
    public SubastaRepository subastaRepository;
    public Gson convertidorJSON;

    /* Espejo de datos para productos */
    public LiveData<Productos> datosProductos;
    public MutableLiveData<Productos> datosMutablesProductos;


    public AuctionViewModel(
            FeriaVirtualApplication aplicacion,
            VentaRepository ventaRepository,
            UsuarioRepository usuarioRepository,
            SubastaRepository subastaRepository,
            Gson convertidorJSON){

        this.aplicacion = aplicacion;

        this.ventaRepository = ventaRepository;
        this.usuarioRepository = usuarioRepository;
        this.subastaRepository = subastaRepository;
        this.convertidorJSON = convertidorJSON;

        this.datosMutablesProductos = new MutableLiveData<>();
        this.datosProductos = datosMutablesProductos;

    }

    public LiveData<Productos> obtenerProductos(Integer id_usuario){

        cargarProductos(id_usuario);

        return datosProductos;

    }

    private void cargarProductos(Integer id_usuario){

        try{

            Call<Productos> reqProductos = subastaRepository.getProductosProductor(id_usuario);

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

    public void pujarSubastaProductor(DetallePujaSubastaProductor pujaSubastaProductor, SimpleAction accion){

        try {

            SharedPreferences sp = aplicacion.getSharedPreferences(FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES, Context.MODE_PRIVATE);
            Usuario usuario = convertidorJSON.fromJson(sp.getString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR,""),Usuario.class);

            Call<ResultadoID> resultado = subastaRepository.pujarProductoSubastaProductor(
                pujaSubastaProductor.id_venta,
                pujaSubastaProductor);
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

}
