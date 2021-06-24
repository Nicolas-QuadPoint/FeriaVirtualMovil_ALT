package com.feriantes4dawin.feriavirtualmovil.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepository;
import com.feriantes4dawin.feriavirtualmovil.ui.sales.CurrentSalesViewModel;
import com.feriantes4dawin.feriavirtualmovil.ui.util.ViewmodelFactory;
import com.google.gson.Gson;

/**
 * MainViewModelFactory 
 * 
 * Clase creadora de instancias para objetos MainViewModel. 
 */
public class MainViewModelFactory implements ViewModelProvider.Factory {

    /**
     * Fuente de datos de usuarios. 
     */
    private UsuarioRepository usuarioRepository;

    /**
     * Objeto convertidor de datos en JSON. 
     */
    private Gson convertidorJSON;

    /**
     * Instancia de Application, necesaria para algunas cosas. 
     */
    private FeriaVirtualApplication feriaVirtualApplication;


    public MainViewModelFactory(UsuarioRepository usuarioRepository, Gson convertidorJSON, FeriaVirtualApplication feriaVirtualApplication){
        this.usuarioRepository = usuarioRepository;
        this.convertidorJSON = convertidorJSON;
        this.feriaVirtualApplication = feriaVirtualApplication;
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {

        if(modelClass.isAssignableFrom(MainViewModel.class)) {

            return (T)(new MainViewModel(usuarioRepository,convertidorJSON,feriaVirtualApplication));

        }

        throw new IllegalArgumentException("No es un objeto MainViewModel");
    }

}
