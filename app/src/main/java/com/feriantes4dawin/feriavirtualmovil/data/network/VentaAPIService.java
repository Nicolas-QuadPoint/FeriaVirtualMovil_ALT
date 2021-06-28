package com.feriantes4dawin.feriavirtualmovil.data.network;

import com.feriantes4dawin.feriavirtualmovil.data.models.DetalleVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Ventas;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

//https://www.youtube.com/watch?v=RSYTn-O3r34

public interface VentaAPIService {

    @GET("ventas?tipo=productores")
    Call<Ventas> getVentasDisponiblesProductores();

    @GET("ventas?tipo=transportistas")
    Call<Ventas> getVentasDisponiblesTransportistas();

    @GET("ventas?formato=detallado")
    Call<Ventas> getVentasDisponibles();

    @GET("ventas/historial")
    Call<Ventas> getHistorialVentas();

    @GET("ventas/{id_venta}?formato=detallado")
    Call<DetalleVenta> getDetalleVenta(@Path(value="id_venta") Integer id_venta);

    @GET("ventas/{id_venta}?formato=simple")
    Call<Venta> getInfoVenta(@Path(value="id_venta") Integer id_venta);

}