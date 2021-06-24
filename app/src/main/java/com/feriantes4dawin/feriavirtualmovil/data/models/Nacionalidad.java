package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Nacionalidad{
    @PrimaryKey
    public Integer id_nacionalidad;
    public String iso;
    public String nombre;
    public String codigo_telefonico;
}