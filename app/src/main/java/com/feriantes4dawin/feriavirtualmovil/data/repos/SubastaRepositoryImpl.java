package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.db.SubastaDAO;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaTransportista;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallesPujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.Producto;
import com.feriantes4dawin.feriavirtualmovil.data.models.Productos;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.PujaSubastaTransportista;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;
import com.feriantes4dawin.feriavirtualmovil.data.network.SubastaAPIService;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
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
    public Call<ResultadoID> pujarProductoSubastaProductor(
        Integer id_subasta,
        DetallePujaSubastaProductor puja
    ){
        Call<ResultadoID> puc = subastaAPI.pujarSubastaProductor(id_subasta,puja);
        return puc;
    }

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

        Call<DetallesPujaSubastaProductor> puc = subastaAPI.getProductosSubasta(id_subasta);
        return puc;

    }

    @Override
    public Call<DetallesPujaSubastaProductor> getProductosSubasta(Integer id_subasta){

        Call<DetallesPujaSubastaProductor> puc = null;
        return puc;

    }


}
