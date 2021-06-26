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
    public String nombre;
    @Expose
    public String apellido;
    @Expose
    public String email;
    @Expose
    public String contrasena;
    @Expose
    public Rol rol;
    @Expose
    public Integer id_productor;

    @Override
    public String toString() {
        return "Usuario{" +
                "secret_id_usuario=" + secret_id_usuario +
                ", id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", rol=" + rol +
                '}';
    }

    public String getNombreCompleto(){

        return String.format("%s %s",

                nombre != null? nombre : "",
                apellido != null? apellido : ""

        );

    }
}
