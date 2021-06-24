package com.feriantes4dawin.feriavirtualmovil.data.network;

import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.models.VentaSimple;
import com.feriantes4dawin.feriavirtualmovil.data.models.Ventas;
import com.feriantes4dawin.feriavirtualmovil.data.models.VentasSimples;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

//https://www.youtube.com/watch?v=RSYTn-O3r34

public interface VentaAPIService {

    @GET("ventas?formato=simple")
    Call<VentasSimples> getVentasSimplesDisponibles();

    @GET("ventas?formato=detallado")
    Call<Ventas> getVentasDisponibles();

    @GET("ventas/{venta_id}")
    Call<Venta> getInfoVenta(@Path(value="venta_id") Integer venta_id);
}