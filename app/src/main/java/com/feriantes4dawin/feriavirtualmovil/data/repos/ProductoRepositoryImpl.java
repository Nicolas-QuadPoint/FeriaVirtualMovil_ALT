package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.datasources.remote.ProductorRemoteDataSource;
import com.feriantes4dawin.feriavirtualmovil.data.models.Productos;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;
import com.feriantes4dawin.feriavirtualmovil.data.network.ProductoAPIService;

import javax.inject.Inject;

import dagger.Module;
import retrofit2.Call;

@Module
public class ProductoRepositoryImpl implements ProductoRepository {

    ProductoAPIService productoAPIService;

    @Inject
    public ProductoRepositoryImpl(ProductoAPIService productoAPIService){

        this.productoAPIService = productoAPIService;

    }

    @Override
    public Call<Productos> getProductos() {

        Call<Productos> ruc = productoAPIService.getProductos();
        return ruc;

    }

    @Override
    public Call<ResultadoID> enviarProductosProceso(PujaSubastaProductor puja) {
        return null;
    }

}
