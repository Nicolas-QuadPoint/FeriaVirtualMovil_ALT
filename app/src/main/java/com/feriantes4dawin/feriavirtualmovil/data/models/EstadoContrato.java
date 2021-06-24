package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EstadoContrato{
    @PrimaryKey
    public Integer id_estado_contrato;
    public String descripcion;
}