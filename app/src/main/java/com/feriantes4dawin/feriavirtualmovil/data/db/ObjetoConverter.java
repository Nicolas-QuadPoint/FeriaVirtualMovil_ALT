package com.feriantes4dawin.feriavirtualmovil.data.db;

import androidx.room.TypeConverter;

import com.feriantes4dawin.feriavirtualmovil.data.models.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ObjetoConverter {

    //Conversores genéricos (entrada)

    @TypeConverter
    public String ObjetoAJSON(Object obj){

        return new Gson().toJson(obj);

    }

    @TypeConverter
    public String ListaAJSON(List<?> lista){
        return new Gson().toJson(lista);
    }

    //Conversores para listas de objetos (salida)

    @TypeConverter
    public List<Producto> JSONListaProductos(String json){
        return new Gson().fromJson(json,new ArrayList<Producto>().getClass());
    }

    @TypeConverter
    public List<DetallePujaSubastaProductor> JSONAListaObjetosPujaSubastaProductor(String json){
        return new Gson().fromJson(json, new ArrayList<DetallePujaSubastaProductor>().getClass());
    }

    //Conversores para cada tipo (salida)

    @TypeConverter
    public ContratoUsuario JSONAContratoUsuario(String json){
        return new Gson().fromJson(json, ContratoUsuario.class);
    }

    @TypeConverter
    public EstadoUsuario JSONAEstadoUsuario(String json){
        return new Gson().fromJson(json, EstadoUsuario.class);
    }

    @TypeConverter
    public EstadoContrato JSONAEstadoContrato(String json){
        return new Gson().fromJson(json, EstadoContrato.class);
    }

    @TypeConverter
    public EstadoSubasta JSONAEstadoSubasta(String json){
        return new Gson().fromJson(json, EstadoSubasta.class);
    }

    @TypeConverter
    public EstadoVenta JSONAEstadoVenta(String json){
        return new Gson().fromJson(json, EstadoVenta.class);
    }

    @TypeConverter
    public Producto JSONAProducto(String json){
        return new Gson().fromJson(json, Producto.class);
    }


    @TypeConverter
    public Rol JSONARol(String json){
        return new Gson().fromJson(json, Rol.class);
    }

    @TypeConverter
    public TipoVenta JSONATipoVenta(String json){
        return new Gson().fromJson(json, TipoVenta.class);
    }

    @TypeConverter
    public Usuario JSONAUsuario(String json){
        return new Gson().fromJson(json, Usuario.class);
    }

    @TypeConverter
    public Venta JSONAVenta(String json){
        return new Gson().fromJson(json, Venta.class);
    }
}
