package com.feriantes4dawin.feriavirtualmovil.data.datasources.remote;

import com.feriantes4dawin.feriavirtualmovil.data.network.SubastaAPIService;

import javax.inject.Inject;

public class SubastaRemoteDataSource {

    public SubastaAPIService subastaAPI;

    @Inject
    public SubastaRemoteDataSource(SubastaAPIService subastaAPI){

        this.subastaAPI = subastaAPI;

    }

}
