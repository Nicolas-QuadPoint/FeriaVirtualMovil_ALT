package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.db.VentaDAO;
import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.models.VentasSimples;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.network.VentaAPIService;

import javax.inject.Inject;

import dagger.Module;
import retrofit2.Call;
import retrofit2.http.Path;

@Module
public class VentaRepositoryImpl implements VentaRepository {

    public VentaDAO ventaDAO;
    public VentaAPIService ventaAPI;

    @Inject
    public VentaRepositoryImpl(VentaDAO ventaDAO,VentaAPIService ventaAPI){
        this.ventaDAO = ventaDAO;
        this.ventaAPI = ventaAPI;
    }

    /*
    @Override
    @Provides
    public VentaRepository getInstance(){
        return this;
    }
     */

    @Override
    public Call<VentasSimples> getVentasDisponibles(Usuario usuario) {

        Call<VentasSimples> ruc = ventaAPI.getVentasSimplesDisponibles();
        return ruc;
    }

    @Override
    public Call<VentasSimples> getVentasSimplesDisponibles(Usuario usuario) {

        Call<VentasSimples> ruc = ventaAPI.getVentasSimplesDisponibles();
        return ruc;

    }

    @Override
    public Call<Venta> getInfoVenta(Integer venta_id){

        Call<Venta> ruc = ventaAPI.getInfoVenta(venta_id);
        return ruc;
    }
}
