package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity
public class DetallePujaSubastaProductor {
    @PrimaryKey
    public Integer p_secret_pk;

    @Expose
    public Integer id_detalle;
    @Expose
    public Integer id_venta;
    @Expose
    public Producto producto;
    @Expose
    public Integer cantidad;
    @Expose
    public TipoVenta tipo_venta;

}
