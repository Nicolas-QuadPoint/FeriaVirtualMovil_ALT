package com.feriantes4dawin.feriavirtualmovil.data.network;

import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaTransportista;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaTransportista;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;
import com.feriantes4dawin.feriavirtualmovil.data.models.Subastas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SubastaAPIService {

    @POST("subastas/{idsubasta}/productor/puja")
    Call<ResultadoID> pujarSubastaProductor(

        @Path("idsubasta")
        Integer id_subasta,
        @Body
        DetallePujaSubastaProductor puja

    );

    @POST("subastas/{idsubasta}/transportista/puja")
    Call<ResultadoID> pujarSubastaTransportista(

        @Path("idsubasta")
        Integer id_subasta,
        @Body
        DetallePujaSubastaTransportista puja

    );

    @DELETE("subastas/{idsubasta}/productor/puja")
    Call<ResultadoID> removerPujaSubastaProductor(

        @Path("idsubasta")
        Integer id_subasta,
        @Body
        Integer id_productor

    );

    @DELETE("subastas/{idsubasta}/transportista/puja")
    Call<ResultadoID> removerPujaSubastaTransportista(

        @Path("idsubasta")
        Integer id_subasta,
        @Body
        Integer id_transportista

    );


    @GET("subastas/{idsubasta}/productor")
    List<DetallePujaSubastaProductor> getAllPujasSubastaProductor(

        @Path("idsubasta")
        Integer id_subasta

    );

    @GET("subastas/{idsubasta}/transportista")
    List<DetallePujaSubastaTransportista> getAllPujasSubastaTransportista(

        @Path("idsubasta")
        Integer id_subasta

    );

    @GET("subastas/{idsubasta}/productor/{idproductor}")
    PujaSubastaProductor getInfoPujaSubastaProductor(
        @Path("idsubasta")
        Integer id_subasta,
        @Path("idproductor")
        Integer id_productor
    );

    @GET("subastas/{idsubasta}/transportista/{idproductor}")
    PujaSubastaTransportista getInfoPujaSubastaTransportista(
        @Path("idsubasta")
        Integer id_subasta,
        @Path("idtransportista")
        Integer id_transportista
    );


}
