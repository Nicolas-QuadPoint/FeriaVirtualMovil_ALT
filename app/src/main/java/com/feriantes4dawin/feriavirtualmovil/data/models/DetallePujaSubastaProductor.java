package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity
public class DetallePujaSubastaProductor {
    @PrimaryKey
    public Integer p_secret_pk;

    @Expose
    public Subasta subasta;
    @Expose
    public Producto producto;
    @Expose
    public Integer kilos;
    @Expose
    public Integer precio_kilo;
}
