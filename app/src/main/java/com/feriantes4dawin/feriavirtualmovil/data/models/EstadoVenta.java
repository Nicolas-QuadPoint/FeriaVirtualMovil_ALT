package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity
public class EstadoVenta {
    @PrimaryKey
    @Expose
    public Integer id_estado_venta;

    @Expose
    public String descripcion;


    public EstadoVenta(Integer id_estado_venta, String descripcion) {
        this.id_estado_venta = id_estado_venta;
        this.descripcion = descripcion;
    }

    public EstadoVenta() {
        this.id_estado_venta = -1;
        this.descripcion = "Desconocido";
    }

    public static final EstadoVenta CREADO = new EstadoVenta(0,"CREADO");
    public static final EstadoVenta INICIADO = new EstadoVenta(1,"INICIADO");
    public static final EstadoVenta LLENO = new EstadoVenta(2,"LLENO");
    public static final EstadoVenta PENDIENTE_TRANPORTISTA  = new EstadoVenta(3,"PENDIENTE TRANSPORTISTA");
    public static final EstadoVenta EN_TRANSPORTE  = new EstadoVenta(4,"EN TRANSPORTE");
    public static final EstadoVenta TRANSPORTE_COMPLETADO = new EstadoVenta(5,"TRANSPORTE COMPLETADO");
    public static final EstadoVenta FINALIZADO = new EstadoVenta(6,"FINALIZADO");
    public static final EstadoVenta CANCELADO = new EstadoVenta(7,"CANCELADO");


    public boolean equalsValues(EstadoVenta ev){

        if(ev == null){

            return false;

        } else {

            return (this.id_estado_venta == ev.id_estado_venta && (this.descripcion.compareTo(ev.descripcion) == 0) );

        }

    }

}
