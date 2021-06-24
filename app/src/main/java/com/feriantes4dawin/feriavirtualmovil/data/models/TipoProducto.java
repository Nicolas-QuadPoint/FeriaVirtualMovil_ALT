package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TipoProducto{
    @PrimaryKey
    public Integer id_tipo_producto;
    public String descripcion;
}