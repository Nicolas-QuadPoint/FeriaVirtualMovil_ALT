package com.feriantes4dawin.feriavirtualmovil.data.datasources.remote;

import com.feriantes4dawin.feriavirtualmovil.data.network.VentaAPIService;

import javax.inject.Inject;

public class VentaRemoteDataSource {

    public VentaAPIService ventaAPI;

    @Inject
    public VentaRemoteDataSource(VentaAPIService ventaAPI){

        this.ventaAPI = ventaAPI;

    }

}
