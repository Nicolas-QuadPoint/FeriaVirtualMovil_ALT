package com.feriantes4dawin.feriavirtualmovil.data.datasources.remote;

import com.feriantes4dawin.feriavirtualmovil.data.network.UsuarioAPIService;

import javax.inject.Inject;

public class UsuarioRemoteDataSource {

    public UsuarioAPIService usuarioAPI;

    @Inject
    public UsuarioRemoteDataSource(UsuarioAPIService usuarioAPI){

        this.usuarioAPI = usuarioAPI;

    }

}
