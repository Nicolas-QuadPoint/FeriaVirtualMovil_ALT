package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.models.VentasSimples;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;

import retrofit2.Call;
import retrofit2.http.Path;


public interface VentaRepository {

    Call<VentasSimples> getVentasSimplesDisponibles(Usuario usuario);

    Call<VentasSimples> getVentasDisponibles(Usuario usuario);

    Call<Venta> getInfoVenta(Integer venta_id);

}
