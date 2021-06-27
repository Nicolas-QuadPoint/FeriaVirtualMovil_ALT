package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TipoVenta{
    @PrimaryKey
    @Expose
    public Integer id_tipo_venta;

    @Expose
    public String descripcion;

    public TipoVenta(Integer id_tipo_venta, String descripcion) {
        this.id_tipo_venta = id_tipo_venta;
        this.descripcion = descripcion;
    }

    public TipoVenta() {
    }

    public static final TipoVenta VENTA_INTERNA = new TipoVenta(1,"Venta Interna (Nacional)");
    public static final TipoVenta VENTA_EXTERNA = new TipoVenta(2,"Venta Externa (Exportación)");

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return this.descripcion;
    }

}