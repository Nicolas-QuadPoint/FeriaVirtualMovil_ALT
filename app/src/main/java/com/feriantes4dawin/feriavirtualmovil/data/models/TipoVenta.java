package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TipoVenta{
    @PrimaryKey
    public Integer id_tipo_venta;
    public String descripcion;
}