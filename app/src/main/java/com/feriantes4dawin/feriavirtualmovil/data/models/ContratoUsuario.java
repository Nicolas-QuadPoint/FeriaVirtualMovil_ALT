package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.PrimaryKey;

public class ContratoUsuario {

    @PrimaryKey
    public Integer id_contrato_usuario;

    public String fecha_inicio_contrato;
    public String fecha_termino_contrato;
    public EstadoContrato estado_contrato;
}
