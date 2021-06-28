package com.feriantes4dawin.feriavirtualmovil.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepository;
import com.google.gson.Gson;

/**
 * LoginViewModelFactory 
 * 
 * Un generador de objetos LoginViewModel para poder instanciarla 
 * con objetos necesarios, como fuentes de datos, entre otras cosas.
 * 
 * @link https://stackoverflow.com/questions/54419236/why-a-viewmodel-factory-is-needed-in-android#54420034
 * 
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    /**
     * Fuente de datos para usuarios. 
     */
    private UsuarioRepository usuarioRepository;

    /**
     * Objeto para convertir datos en JSON. 
     */
    private Gson convertidorJSON;

    /**
     * Referencia a un objeto Application que se usa 
     * en algunos casos. 
     */
    private FeriaVirtualApplication fva;

    public LoginViewModelFactory(UsuarioRepository usuarioRepository, Gson convertidorJSON, FeriaVirtualApplication fva){
        super();
        this.usuarioRepository = usuarioRepository;
        this.convertidorJSON = convertidorJSON;
        this.fva = fva;
    }

    @Override
    public  <T extends ViewModel> T create(Class<T> modelClass){
        if(modelClass.isAssignableFrom(LoginViewModel.class)){
            return (T)(new LoginViewModel(usuarioRepository,convertidorJSON,fva) );
        }
        throw new IllegalArgumentException("El objeto no es un LoginViewModel");
    }

}