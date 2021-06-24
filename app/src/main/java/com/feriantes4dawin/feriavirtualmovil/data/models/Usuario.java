package com.feriantes4dawin.feriavirtualmovil.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants;
import com.google.gson.annotations.Expose;

import org.jetbrains.annotations.NotNull;

@Entity
public class Usuario {

    @PrimaryKey(autoGenerate = false)
    public Integer secret_id_usuario = FeriaVirtualConstants.CURRENT_LOGGED_USUARIO;

    @Expose
    public Long id_usuario;
    @Expose
    public String personal_id;
    @Expose
    public String nombre;
    @Expose
    public String nombre_segundo;
    @Expose
    public String apellido_paterno;
    @Expose
    public String apellido_materno;
    @Expose
    public String fecha_nacimiento;
    @Expose
    public Long telefono;
    @Expose
    public String direccion;
    @Expose
    public String email;
    @Expose
    public String contrasena;
    @Expose
    public String salt_contrasena;
    @Expose
    public Nacionalidad nacionalidad;
    @Expose
    public Rol rol;
    @Expose
    public EstadoUsuario estado_usuario;
    @Expose
    public ContratoUsuario contrato_usuario;

    @Override
    public String toString() {
        return "Usuario{" +
                "secret_id_usuario=" + secret_id_usuario +
                ", id_usuario=" + id_usuario +
                ", personal_id='" + personal_id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", nombre_segundo='" + nombre_segundo + '\'' +
                ", apellido_paterno='" + apellido_paterno + '\'' +
                ", apellido_materno='" + apellido_materno + '\'' +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", telefono=" + telefono +
                ", direccion='" + direccion + '\'' +
                ", email='" + email + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", salt_contrasena='" + salt_contrasena + '\'' +
                ", nacionalidad=" + nacionalidad +
                ", rol=" + rol +
                ", estado_usuario=" + estado_usuario +
                ", contrato_usuario=" + contrato_usuario +
                '}';
    }

    public String getNombreCompleto(){

        return String.format("%s %s %s %s",

                nombre != null? nombre : "",
                nombre_segundo != null? nombre_segundo : "",
                apellido_paterno != null? apellido_paterno : "",
                apellido_materno != null? apellido_materno : ""

        );

    }
}
