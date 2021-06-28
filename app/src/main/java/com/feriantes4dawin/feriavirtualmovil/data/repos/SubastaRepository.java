package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaTransportista;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallesPujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.Productos;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaTransportista;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;

import java.util.List;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Component;
import dagger.Module;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface SubastaRepository {

    Call<ResultadoID> pujarProductoSubastaProductor(

        Integer id_subasta,
        DetallePujaSubastaProductor puja

    );

    Call<ResultadoID> modificarPujaProductor(

        Integer id_subasta,
        DetallePujaSubastaProductor puja

    );

    Call<DetallesPujaSubastaProductor> removerPujaProductor(

        Integer id_subasta,
        Integer id_detalle

    );

    Call<DetallesPujaSubastaProductor> getProductosSubasta(

        Integer id_subasta,
        Integer id_productor

    );

    Call<DetallesPujaSubastaProductor> getProductosSubasta(

        Integer id_subasta

    );



}
