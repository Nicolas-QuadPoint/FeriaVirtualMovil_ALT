package com.feriantes4dawin.feriavirtualmovil.data.models;

import java.util.ArrayList;
import java.util.List;

public class DetallesPujaSubastaProductor {
    public List<DetallePujaSubastaProductor> pujas;

    public DetallesPujaSubastaProductor(List<DetallePujaSubastaProductor> pujas) {
        this.pujas = new ArrayList<>(pujas);
    }
}
