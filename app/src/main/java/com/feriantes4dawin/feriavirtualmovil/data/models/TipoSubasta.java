package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TipoSubasta{
    @PrimaryKey
    public Integer id_tipo_subasta;
    public String descripcion;
}