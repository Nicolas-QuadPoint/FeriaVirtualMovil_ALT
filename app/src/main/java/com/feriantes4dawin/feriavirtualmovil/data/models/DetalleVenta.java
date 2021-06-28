package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.util.List;

@Entity
public class DetalleVenta {
    @PrimaryKey
    public Integer secret_id_venta;

    @Expose
    public Venta venta;

    @Expose
    public List<Producto> productos;
}
