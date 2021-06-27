package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Venta {

    @PrimaryKey
    public Integer id_venta;
    public String fecha_inicio_venta;
    public String fecha_fin_venta;
    public String comentarios_venta;
    public EstadoVenta estado_venta;

}
