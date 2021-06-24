package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EstadoUsuario{
    @PrimaryKey
    public Integer id_estado_usuario;
    public String descripcion;
}