package com.feriantes4dawin.feriavirtualmovil.data.repos;

import com.feriantes4dawin.feriavirtualmovil.data.models.LoginObject;
import com.feriantes4dawin.feriavirtualmovil.data.models.ObjetoModificacionContrasena;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoUsuario;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;

import retrofit2.Call;

public interface UsuarioRepository {

    //UsuarioRepository getInstance();

    Call<ResultadoUsuario> loginUsuario(LoginObject loginObject);

    Call<ResultadoUsuario> getInfoUsuario(Usuario usuario);

    Call<ResultadoID> updateUsuario(Usuario usuario);

    Call<ResultadoID> changePasswordUsuario(ObjetoModificacionContrasena omc);
}