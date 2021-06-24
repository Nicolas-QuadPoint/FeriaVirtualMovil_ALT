package com.feriantes4dawin.feriavirtualmovil.ui.proccesses;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.models.VentasSimples;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;
import com.feriantes4dawin.feriavirtualmovil.ui.profile.MyProfileViewModel;

import org.jetbrains.annotations.NotNull;


/**
 * MyProcessesViewModelFactory 
 * 
 * Un generador de objetos MyProcessesViewModel para poder instanciarla 
 * con objetos necesarios, como fuentes de datos, entre otras cosas.
 * 
 * @see https://stackoverflow.com/questions/54419236/why-a-viewmodel-factory-is-needed-in-android#54420034
 * 
 */
public class MyProcessesViewModelFactory implements ViewModelProvider.Factory {

    private FeriaVirtualApplication feriaVirtualApplication;
    private VentaRepository ventaRepository;

    public MyProcessesViewModelFactory(VentaRepository ventaRepository, FeriaVirtualApplication feriaVirtualApplication){

        this.feriaVirtualApplication = feriaVirtualApplication;
        this.ventaRepository = ventaRepository;

    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(MyProcessesViewModel.class)) {

            return (T)(new MyProcessesViewModel(ventaRepository,feriaVirtualApplication));

        }

        throw new IllegalArgumentException("No es un objeto MyProcessesViewModel");
    }
}
