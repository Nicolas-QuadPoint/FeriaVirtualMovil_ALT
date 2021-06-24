package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Subasta {
    @PrimaryKey
    public Integer id_subasta;
    public String fecha_inicio_subasta;
    public String fecha_fin_subasta;
    public TipoSubasta tipo_subasta;
    public EstadoSubasta estado_subasta;
    public Venta venta;
    public List<DetallePujaSubastaProductor> ofertas_productores;
    public List<DetallePujaSubastaTransportista> ofertas_transportistas;
}
