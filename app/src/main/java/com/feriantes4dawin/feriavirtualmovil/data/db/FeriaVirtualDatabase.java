package com.feriantes4dawin.feriavirtualmovil.data.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetalleVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.EstadoContrato;
import com.feriantes4dawin.feriavirtualmovil.data.models.EstadoSubasta;
import com.feriantes4dawin.feriavirtualmovil.data.models.EstadoUsuario;
import com.feriantes4dawin.feriavirtualmovil.data.models.EstadoVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Producto;
import com.feriantes4dawin.feriavirtualmovil.data.models.Rol;
import com.feriantes4dawin.feriavirtualmovil.data.models.TipoVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants;

@Database(
    entities = {
        EstadoVenta.class,
        EstadoSubasta.class,
        EstadoUsuario.class,
        EstadoContrato.class,
        DetalleVenta.class,
        DetallePujaSubastaProductor.class,
        Producto.class,
        Rol.class,
        TipoVenta.class,
        Usuario.class,
        Venta.class
    },
    version = 1,
    exportSchema = false
)
@TypeConverters({
        ObjetoConverter.class
})
public abstract class FeriaVirtualDatabase extends RoomDatabase {

    private static FeriaVirtualDatabase feriaVirtualDatabase = null;
    private final static Object LOCK = new Object();

    public abstract UsuarioDAO getUsuarioDAO();
    public abstract SubastaDAO getSubastaDAO();
    public abstract VentaDAO getVentaDAO();

    /**
     * Crea la instancia de la base de datos.
     * Mantiene adjunto a él el contexto general de la aplicación
     * para que cualquiera que desee usar este objeto
     * pueda tener el contexto a su alcance.
     */
    public static FeriaVirtualDatabase getInstance(Context context) {

        synchronized (LOCK) {

            if (feriaVirtualDatabase == null) {

                feriaVirtualDatabase= Room.databaseBuilder(
                        context.getApplicationContext(),
                        FeriaVirtualDatabase.class, FeriaVirtualConstants.NOMBRE_BASE_DATOS)
                        .allowMainThreadQueries()
                    .build();

            }
        }
        return feriaVirtualDatabase;
    }
}