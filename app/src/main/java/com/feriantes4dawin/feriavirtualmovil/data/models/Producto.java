package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Producto{
    @PrimaryKey
    public Integer id_producto;
    public String nombre;
    public Integer cantidad;
    public Integer peso;
    public Integer volumen;
    public Integer estado;
    public Integer refrigeracion;
    public String fecha_llegada;
    public Integer codigo_productor;
}