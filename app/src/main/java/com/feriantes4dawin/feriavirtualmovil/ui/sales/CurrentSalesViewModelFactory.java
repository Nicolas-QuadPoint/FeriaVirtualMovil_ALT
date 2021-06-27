package com.feriantes4dawin.feriavirtualmovil.ui.sales;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.google.gson.Gson;

/**
 * CurrentSalesViewModelFactory 
 * 
 * Un generador de objetos CurrentSalesViewModel para poder instanciarla 
 * con objetos necesarios, como fuentes de datos, entre otras cosas.
 * 
 * @see https://stackoverflow.com/questions/54419236/why-a-viewmodel-factory-is-needed-in-android#54420034
 * 
 */
public class CurrentSalesViewModelFactory implements ViewModelProvider.Factory {

    /**
     * Objeto que sirve como fuente de datos para 
     * ventas. 
     */
    private VentaRepository ventaRepository;

    /**
     * Instancia de la aplicación. 
     */
    private FeriaVirtualApplication fva;

    /**
     * Convertidor de JSON
     */
    private Gson convertidorJSON;

    public CurrentSalesViewModelFactory(VentaRepository ventaRepository,FeriaVirtualApplication fva,Gson convertidorJSON){
        this.ventaRepository = ventaRepository;
        this.fva = fva;
        this.convertidorJSON = convertidorJSON;
    }

    public <T extends ViewModel> T create(Class<T> modelClass) {

        if(modelClass.isAssignableFrom(CurrentSalesViewModel.class)) {

            return (T)(new CurrentSalesViewModel(ventaRepository,fva,convertidorJSON));

        }

        throw new IllegalArgumentException("No es un objeto CurrentSalesViewModel");
    }

}