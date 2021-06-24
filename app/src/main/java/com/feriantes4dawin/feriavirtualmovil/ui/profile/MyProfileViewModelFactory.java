package com.feriantes4dawin.feriavirtualmovil.ui.profile;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepository;
import com.google.gson.Gson;

/**
 * MyProfileViewModelFactory 
 * 
 * Un generador de objetos MyProfileViewModel para poder instanciarla 
 * con objetos necesarios, como fuentes de datos, entre otras cosas.
 * 
 * @see https://stackoverflow.com/questions/54419236/why-a-viewmodel-factory-is-needed-in-android#54420034
 * 
 */
public class MyProfileViewModelFactory implements ViewModelProvider.Factory {

        private UsuarioRepository usuarioRepo;
        private Gson convertidorJSON;
        private FeriaVirtualApplication fva;

        public MyProfileViewModelFactory(UsuarioRepository ur, Gson convertidorJSON, FeriaVirtualApplication fva){
            this.usuarioRepo = ur;
            this.convertidorJSON = convertidorJSON;
            this.fva = fva;
        }

    public <T extends ViewModel> T create(Class<T> modelClass) {

        if(modelClass.isAssignableFrom(MyProfileViewModel.class)) {

            return (T)(new MyProfileViewModel(usuarioRepo,convertidorJSON,fva));

        }

        throw new IllegalArgumentException("No es un objeto MyProfileViewModel");
    }

}