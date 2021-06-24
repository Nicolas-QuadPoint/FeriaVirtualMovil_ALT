package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity
public class DetallePujaSubastaTransportista {
    @PrimaryKey
    public Integer p_secret_pk;

    @Expose
    public Subasta subasta;
    @Expose
    public Transportista transportista;
    @Expose
    public String descripcion_propuesta;
    @Expose
    public Integer coste_transporte;
}
