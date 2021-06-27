package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaTransportista;
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

    Call<ResultadoID> pujarSubastaProductor(

        Integer id_subasta,
        DetallePujaSubastaProductor puja

    );

    Call<ResultadoID> pujarSubastaTransportista(


        Integer id_subasta,
        DetallePujaSubastaTransportista puja

    );

    Call<ResultadoID> removerPujaSubastaProductor(

        Integer id_subasta,
        Integer id_productor

    );

    Call<ResultadoID> removerPujaSubastaTransportista(

        Integer id_subasta,
        Integer id_transportista

    );


    List<DetallePujaSubastaProductor> getAllPujasSubastaProductor(

        Integer id_subasta

    );

    List<DetallePujaSubastaTransportista> getAllPujasSubastaTransportista(

        Integer id_subasta

    );

    PujaSubastaProductor getInfoPujaSubastaProductor(

        Integer id_subasta,
        Integer id_productor

    );

    PujaSubastaTransportista getInfoPujaSubastaTransportista(

        Integer id_subasta,
        Integer id_transportista

    );


}
