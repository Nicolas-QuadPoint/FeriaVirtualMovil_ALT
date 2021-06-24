package com.feriantes4dawin.feriavirtualmovil.ui.proccesses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.models.VentasSimples;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepository;

/**
 * MyProcessesViewModel 
 * 
 * Clase que actúa como intermediario para ofrecer y enviar 
 * datos entre el fragmento MyProcessesFragment y la fuente de datos. 
 */
public class MyProcessesViewModel extends ViewModel {

    private FeriaVirtualApplication feriaVirtualApplication;
    private VentaRepository ventaRepository;

    private LiveData<VentasSimples> datosVenta;
    private MutableLiveData<VentasSimples> datosMutablesVenta;

    public MyProcessesViewModel(VentaRepository ventaRepository, FeriaVirtualApplication feriaVirtualApplication){

        this.feriaVirtualApplication = feriaVirtualApplication;
        this.ventaRepository = ventaRepository;

        this.datosMutablesVenta = new MutableLiveData<>();
        this.datosVenta = datosMutablesVenta;

    }

    /**
     * 
     * Pregunta por los procesos de ventas en los que el usuario 
     * (Productor o Transportista) haya participado en algún momento, 
     * independiente si fue viable o no, con el fin de consultar 
     * información. 
     * 
     * TODO: Agregar la lógica que falta para que esto funcione!
     * 
     * @return Un objeto LiveData para vigilar por cambios. 
     */
    LiveData<VentasSimples> getDatosVenta(){

        //No hago nada, ayuda!
        return datosVenta;
    }


}