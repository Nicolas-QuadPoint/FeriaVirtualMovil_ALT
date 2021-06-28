package com.feriantes4dawin.feriavirtualmovil.ui.auction;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;

import org.jetbrains.annotations.NotNull;

public class AuctionViewModelFactory implements ViewModelProvider.Factory {

    public FeriaVirtualApplication aplicacion;

    /* Fuentes de datos */
    public VentaRepository ventaRepository;
    public UsuarioRepository usuarioRepository;
    public SubastaRepository subastaRepository;

    public AuctionViewModelFactory(
            FeriaVirtualApplication aplicacion,
            VentaRepository ventaRepository,
            UsuarioRepository usuarioRepository,
            SubastaRepository subastaRepository) {

        this.aplicacion = aplicacion;

        this.ventaRepository = ventaRepository;
        this.usuarioRepository = usuarioRepository;
        this.subastaRepository = subastaRepository;
    }


    @NonNull
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(AuctionViewModel.class)){
            return (T)( new AuctionViewModel(aplicacion,ventaRepository,usuarioRepository,subastaRepository) );
        }
        throw new IllegalArgumentException("El objeto no es un AuctionViewModel");
    }

}
