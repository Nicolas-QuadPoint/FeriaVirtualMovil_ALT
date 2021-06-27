package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.models.DetalleVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.TipoVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Ventas;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;

import java.util.List;

import retrofit2.Call;


public interface VentaRepository {

    Call<Ventas> getVentasDisponibles(Usuario usuario);

    Call<DetalleVenta> getDetalleVenta(Integer venta_id);

    Call<TipoVenta> getTiposVenta();

    Call<Ventas> getHistorialVentas();

    List<TipoVenta> getTiposVentaLocal();

}
