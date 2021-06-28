package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallesPujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.Productos;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;

import retrofit2.Call;


public interface SubastaRepository {

    Call<ResultadoID> pujarProductoSubastaProductor(

        Integer id_subasta,
        DetallePujaSubastaProductor puja

    );

    Call<ResultadoID> modificarPujaProductor(

        Integer id_subasta,
        DetallePujaSubastaProductor puja

    );

    Call<Productos> getProductosProductor(

        Integer id_subasta
    );

    Call<DetallesPujaSubastaProductor> removerPujaProductor(

        Integer id_subasta,
        Integer id_detalle

    );

    Call<DetallesPujaSubastaProductor> getProductosSubasta(

        Integer id_subasta,
        Integer id_productor

    );



}
