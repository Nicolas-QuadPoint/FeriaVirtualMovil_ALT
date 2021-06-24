package com.feriantes4dawin.feriavirtualmovil.ui.auction;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaTransportista;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujasSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujasSubastaTransportista;
import com.feriantes4dawin.feriavirtualmovil.data.models.Subasta;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;

public class AuctionViewModel extends ViewModel {

    public FeriaVirtualApplication aplicacion;

    /* Fuentes de datos */
    public VentaRepository ventaRepository;
    public UsuarioRepository usuarioRepository;
    public SubastaRepository subastaRepository;

    /* Espejo de datos para transportistas */
    public LiveData<PujasSubastaTransportista> datosPujasTransportista;
    public MutableLiveData<PujasSubastaTransportista> datosMutablesPujasTransportista;

    /* Espejo de datos para la puja individual transportista */
    public LiveData<PujaSubastaTransportista> datosPujaTransportista;
    public MutableLiveData<PujaSubastaTransportista> datosMutablesPujaTransportista;

    /* Espejo de datos para la puja individual productor */
    public LiveData<PujaSubastaProductor> datosPujaProductor;
    public MutableLiveData<PujaSubastaProductor> datosMutablesPujaProductor;

    /* Espejo de datos para productores */
    public LiveData<PujasSubastaProductor> datosPujasProductor;
    public MutableLiveData<PujasSubastaProductor> datosMutablesPujasProductor;


    public AuctionViewModel(
            FeriaVirtualApplication aplicacion,
            VentaRepository ventaRepository,
            UsuarioRepository usuarioRepository,
            SubastaRepository subastaRepository){

        this.aplicacion = aplicacion;

        this.ventaRepository = ventaRepository;
        this.usuarioRepository = usuarioRepository;
        this.subastaRepository = subastaRepository;

        this.datosMutablesPujasProductor = new MutableLiveData<>();
        this.datosMutablesPujaProductor = new MutableLiveData<>();
        this.datosMutablesPujaTransportista = new MutableLiveData<>();
        this.datosMutablesPujasTransportista = new MutableLiveData<>();

        this.datosPujaProductor = datosMutablesPujaProductor;
        this.datosPujasProductor = datosMutablesPujasProductor;
        this.datosPujaTransportista = datosMutablesPujaTransportista;
        this.datosPujasTransportista = datosMutablesPujasTransportista;

    }


    public void pedirPujasSubastaTransportista(Subasta subasta){


    }

    public void pedirPujasSubastaProductor(Subasta subasta){

    }


    public void pujarSubastaProductor(PujaSubastaProductor pujaSubastaProductor){

    }

    public void pujarSubastaTransportista(PujaSubastaTransportista pujaSubastaTransportista){

    }

}
