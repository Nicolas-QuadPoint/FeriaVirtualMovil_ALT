package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.Result;
import com.feriantes4dawin.feriavirtualmovil.data.db.SubastaDAO;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallesPujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.Productos;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;
import com.feriantes4dawin.feriavirtualmovil.data.network.SubastaAPIService;

import javax.inject.Inject;

import dagger.Module;
import retrofit2.Call;

@Module
public class SubastaRepositoryImpl implements SubastaRepository {

    public SubastaDAO subastaDAO;
    public SubastaAPIService subastaAPI;


    @Inject
    public SubastaRepositoryImpl(SubastaDAO subastaDAO,SubastaAPIService subastaAPI){

        this.subastaDAO = subastaDAO;
        this.subastaAPI = subastaAPI;

    }

    @Override
    public Call<Productos> getProductosProductor(
        Integer id_productor
    ){

        Call<Productos> puc = subastaAPI.getProductosProductor(id_productor);
        return puc;
    }

    @Override
    public Call<ResultadoID> pujarProductoSubastaProductor(
        Integer id_subasta,
        DetallePujaSubastaProductor puja
    ){
        Call<ResultadoID> puc = subastaAPI.pujarSubastaProductor(id_subasta,puja);
        return puc;
    }

    @Override
    public Call<ResultadoID> modificarPujaProductor(

            Integer id_subasta,
            DetallePujaSubastaProductor puja

    ){
        Call<ResultadoID> puc = subastaAPI.modificarPujaProductor(id_subasta,puja);
        return puc;
    };

    @Override
    public Call<DetallesPujaSubastaProductor> removerPujaProductor(
        Integer id_subasta,
        Integer id_detalle
    ){

        Call<DetallesPujaSubastaProductor> puc = subastaAPI.removerPujaSubastaProductor(id_subasta,id_detalle);
        return puc;

    }

    @Override
    public Call<DetallesPujaSubastaProductor> getProductosSubasta(Integer id_subasta, Integer id_productor){

        Call<DetallesPujaSubastaProductor> puc = subastaAPI.getProductosSubasta(id_subasta,id_productor);
        return puc;

    }

    @Override
    public Call<DetallesPujaSubastaProductor> getTodosLosProductosSubasta(
            Integer id_subasta
    ){
        Call<DetallesPujaSubastaProductor> puc = subastaAPI.getTodosLosProductosSubasta(id_subasta);
        return puc;
    }

    @Override
    public Call<ResultadoID> transportarEncargoProductos(
            Integer id_subasta
    ){
        Call<ResultadoID> puc = subastaAPI.transportarEncargoProductos(id_subasta);
        return puc;
    }

    @Override
    public Call<ResultadoID> finalizarEncargoProductos(
            Integer id_subasta
    ){
        Call<ResultadoID> puc = subastaAPI.finalizarEncargoProductos(id_subasta);
        return puc;
    }

}
