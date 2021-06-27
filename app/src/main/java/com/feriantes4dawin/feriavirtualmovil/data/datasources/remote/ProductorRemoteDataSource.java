package com.feriantes4dawin.feriavirtualmovil.data.datasources.remote;

import com.feriantes4dawin.feriavirtualmovil.data.network.ProductoAPIService;
import com.feriantes4dawin.feriavirtualmovil.data.network.SubastaAPIService;

import javax.inject.Inject;

public class ProductorRemoteDataSource {

    public ProductoAPIService productoAPIService;

    @Inject
    public ProductorRemoteDataSource(ProductoAPIService productoAPIService){

        this.productoAPIService = productoAPIService;

    }

}
