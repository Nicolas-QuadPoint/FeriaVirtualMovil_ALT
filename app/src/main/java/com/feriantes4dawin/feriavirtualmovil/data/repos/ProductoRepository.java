package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.models.Productos;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;
import com.feriantes4dawin.feriavirtualmovil.data.models.TipoVenta;

import retrofit2.Call;

public interface ProductoRepository {

    Call<Productos> getProductos();

    Call<ResultadoID> enviarProductosProceso(PujaSubastaProductor puja);

}
