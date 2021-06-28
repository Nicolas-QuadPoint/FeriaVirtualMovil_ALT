package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.db.VentaDAO;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetalleVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Rol;
import com.feriantes4dawin.feriavirtualmovil.data.models.TipoVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Ventas;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.network.VentaAPIService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import retrofit2.Call;

@Module
public class VentaRepositoryImpl implements VentaRepository {

    public VentaDAO ventaDAO;
    public VentaAPIService ventaAPI;

    @Inject
    public VentaRepositoryImpl(VentaDAO ventaDAO,VentaAPIService ventaAPI){
        this.ventaDAO = ventaDAO;
        this.ventaAPI = ventaAPI;
    }

    public Call<TipoVenta> getTiposVenta(){

        return null;

    }

    public List<TipoVenta> getTiposVentaLocal(){

        ArrayList<TipoVenta> lista = new ArrayList<TipoVenta>();
        lista.add(TipoVenta.VENTA_INTERNA);
        lista.add(TipoVenta.VENTA_EXTERNA);

        return lista;

    }

    @Override
    public Call<Ventas> getHistorialVentas(){

        Call<Ventas> ruc = ventaAPI.getHistorialVentas();
        return ruc;
    }

    @Override
    public Call<Ventas> getVentasDisponibles(Usuario usuario) {

        Call<Ventas> ruc;

        if(Rol.PRODUCTOR.equalsValues(usuario.rol))
            ruc = ventaAPI.getVentasDisponiblesProductores();
        else if(Rol.TRANSPORTISTA.equalsValues(usuario.rol)){
            ruc = ventaAPI.getVentasDisponiblesTransportistas();
        }
        else{
            ruc = null;
        }

        return ruc;
    }

    @Override
    public Call<Venta> getInfoVenta(Integer id_venta){

        Call<Venta> ruc = ventaAPI.getInfoVenta(id_venta);
        return ruc;

    }

    @Override
    public Call<DetalleVenta> getDetalleVenta(Integer id_venta){

        Call<DetalleVenta> ruc = ventaAPI.getDetalleVenta(id_venta);
        return ruc;
    }
}
