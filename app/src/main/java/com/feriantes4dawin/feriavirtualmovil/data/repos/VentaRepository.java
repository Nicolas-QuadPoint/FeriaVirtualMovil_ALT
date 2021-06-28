package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.models.DetalleVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.TipoVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Ventas;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;

import java.util.List;

import retrofit2.Call;


public interface VentaRepository {

    Call<Ventas> getVentasDisponibles(Usuario usuario);

    Call<Venta> getInfoVenta(Integer id_venta);

    Call<DetalleVenta> getDetalleVenta(Integer id_venta);

    Call<TipoVenta> getTiposVenta();

    Call<Ventas> getHistorialVentas();

    List<TipoVenta> getTiposVentaLocal();

}
