package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Producto{
    @PrimaryKey
    public Integer id_producto;
    public String nombre;
    public Integer volumen;
    public TipoProducto tipo_producto;
    public Integer costo_mantencion;
}