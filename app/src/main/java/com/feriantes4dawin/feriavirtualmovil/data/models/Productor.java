package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;

@Entity
public class Productor extends Usuario{
    ParProductoCantidad productos_asociados;
}
