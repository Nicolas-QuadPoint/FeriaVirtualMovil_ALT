package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

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


    public Producto(){
        this.id_producto = 0;
        this.nombre = "producto";
        this.cantidad = 0;
        this.peso = 0;
        this.volumen = 0;
        this.estado = 0;
        this.refrigeracion = 0;
        this.fecha_llegada = "01/01/2000";
        this.codigo_productor = 0;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return this.nombre;
    }
}