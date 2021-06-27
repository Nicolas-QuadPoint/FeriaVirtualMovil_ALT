package com.feriantes4dawin.feriavirtualmovil.data.network;

import com.feriantes4dawin.feriavirtualmovil.data.models.LoginObject;
import com.feriantes4dawin.feriavirtualmovil.data.models.Productos;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoUsuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProductoAPIService {

    @GET("productos")
    Call<Productos> getProductos();

    @GET("productos/enviar-a-proceso")
    Call<ResultadoID> enviarProductosProceso(
        @Body
        PujaSubastaProductor puja
    );

}
