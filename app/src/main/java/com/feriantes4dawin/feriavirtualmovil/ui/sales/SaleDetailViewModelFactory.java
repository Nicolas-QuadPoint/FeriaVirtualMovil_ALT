package com.feriantes4dawin.feriavirtualmovil.ui.sales;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;

import org.jetbrains.annotations.NotNull;

/**
 * SaleDetailViewModelFactory 
 * 
 * Un generador de objetos SaleDetailViewModel para poder instanciarla 
 * con objetos necesarios, como fuentes de datos, entre otras cosas.
 * 
 * @see https://stackoverflow.com/questions/54419236/why-a-viewmodel-factory-is-needed-in-android#54420034
 * 
 */
public class SaleDetailViewModelFactory implements ViewModelProvider.Factory {

    VentaRepository ventaRepository;
    FeriaVirtualApplication feriaVirtualApplication;

    public SaleDetailViewModelFactory(VentaRepository ventaRepository,FeriaVirtualApplication feriaVirtualApplication){

        this.ventaRepository = ventaRepository;
        this.feriaVirtualApplication = feriaVirtualApplication;

    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(SaleDetailViewModel.class)) {

            return (T)(new SaleDetailViewModel(ventaRepository,feriaVirtualApplication));

        }

        throw new IllegalArgumentException("No es un objeto CurrentSalesViewModel");

    }
}
