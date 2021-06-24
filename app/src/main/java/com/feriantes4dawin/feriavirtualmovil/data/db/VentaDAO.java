package com.feriantes4dawin.feriavirtualmovil.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;

@Dao
public interface VentaDAO {

    @Insert
    void insert(Venta venta);

    @Delete
    void borrarVenta(Venta venta);

    @Update
    void modificarVenta(Venta venta);

    @Query("select * from venta where id_venta=:id_venta")
    Venta findVenta(Integer id_venta);

}