package com.feriantes4dawin.feriavirtualmovil.data.datasources.local;

import android.content.Context;

import androidx.core.view.ViewCompat;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.db.FeriaVirtualDatabase;
import com.feriantes4dawin.feriavirtualmovil.data.db.FeriaVirtualDatabase;
import com.feriantes4dawin.feriavirtualmovil.data.db.SubastaDAO;
import com.feriantes4dawin.feriavirtualmovil.data.db.UsuarioDAO;
import com.feriantes4dawin.feriavirtualmovil.data.db.VentaDAO;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FeriaVirtualDBProvider {

    private FeriaVirtualApplication fva;

    @Inject
    public FeriaVirtualDBProvider(FeriaVirtualApplication fva)
    {
        this.fva = fva;
    }

    @Provides
    @Singleton
    public UsuarioDAO provideUsuarioDAO(){
        return FeriaVirtualDatabase.getInstance(fva).getUsuarioDAO();
    }

    @Provides
    @Singleton
    public VentaDAO provideVentaDAO() {
        return FeriaVirtualDatabase.getInstance(fva).getVentaDAO();
    }

    @Provides
    @Singleton
    public SubastaDAO provideSubastaDAO(){
        return FeriaVirtualDatabase.getInstance(fva).getSubastaDAO();
    }



}