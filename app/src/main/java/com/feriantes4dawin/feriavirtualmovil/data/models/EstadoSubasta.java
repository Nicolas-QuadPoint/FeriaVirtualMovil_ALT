package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EstadoSubasta{
    @PrimaryKey
    public Integer id_estado_venta;
    public String descripcion;
}