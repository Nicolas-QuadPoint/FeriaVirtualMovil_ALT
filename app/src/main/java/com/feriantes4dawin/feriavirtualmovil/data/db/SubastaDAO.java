package com.feriantes4dawin.feriavirtualmovil.data.db;

import androidx.room.Dao;
import androidx.room.Query;

import com.feriantes4dawin.feriavirtualmovil.data.models.DetalleVenta;
import java.util.List;

@Dao
public interface SubastaDAO {

    @Query("select * from detalleventa")
    List<DetalleVenta> getSubastas();


}
