package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.db.SubastaDAO;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaTransportista;
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
    public Call<ResultadoID> pujarSubastaProductor(Integer id_subasta, DetallePujaSubastaProductor puja) {

        Call<ResultadoID> puc = subastaAPI.pujarSubastaProductor(id_subasta,puja);

        return puc;
    }

    @Override
    public Call<ResultadoID> pujarSubastaTransportista(Integer id_subasta, DetallePujaSubastaTransportista puja) {

        Call<ResultadoID> puc = subastaAPI.pujarSubastaTransportista(id_subasta,puja);

        return puc;
    }

    @Override
    public Call<ResultadoID> removerPujaSubastaProductor(Integer id_subasta, Integer id_productor) {
        return null;
    }

    @Override
    public Call<ResultadoID> removerPujaSubastaTransportista(Integer id_subasta, Integer id_transportista) {
        return null;
    }

    @Override
    public List<DetallePujaSubastaProductor> getAllPujasSubastaProductor(Integer id_subasta) {
        return null;
    }

    @Override
    public List<DetallePujaSubastaTransportista> getAllPujasSubastaTransportista(Integer id_subasta) {
        return null;
    }

    @Override
    public PujaSubastaProductor getInfoPujaSubastaProductor(Integer id_subasta, Integer id_productor) {
        return null;
    }

    @Override
    public PujaSubastaTransportista getInfoPujaSubastaTransportista(Integer id_subasta, Integer id_transportista) {
        return null;
    }
}
