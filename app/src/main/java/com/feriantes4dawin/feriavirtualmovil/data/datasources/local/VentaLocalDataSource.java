package com.feriantes4dawin.feriavirtualmovil.data.datasources.local;

import android.content.Context;

import com.feriantes4dawin.feriavirtualmovil.data.db.VentaDAO;

import javax.inject.Inject;

public class VentaLocalDataSource {

    public VentaDAO ventaDAO;

    @Inject
    public VentaLocalDataSource(VentaDAO ventaDAO){
        //this.ventaDAO = new FeriaVirtualDBProvider(c).provideDB().getVentaDAO();
        this.ventaDAO = ventaDAO;
    }

}
