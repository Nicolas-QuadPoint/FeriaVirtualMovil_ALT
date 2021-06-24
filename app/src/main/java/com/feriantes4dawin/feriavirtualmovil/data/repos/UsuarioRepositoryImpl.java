package com.feriantes4dawin.feriavirtualmovil.data.repos;

import android.util.Log;

import com.feriantes4dawin.feriavirtualmovil.data.db.UsuarioDAO;
import com.feriantes4dawin.feriavirtualmovil.data.models.LoginObject;
import com.feriantes4dawin.feriavirtualmovil.data.models.ObjetoModificacionContrasena;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoUsuario;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.network.UsuarioAPIService;

import javax.inject.Inject;

import dagger.Module;
import retrofit2.Call;

@Module
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private UsuarioRepository instancia;

    /* Para conexiones locales */
    private UsuarioDAO usuarioDAO;

    /* Para conectarse a la web api */
    private UsuarioAPIService usuarioAPI;

    /* Guarda al usuario activo */
    private Usuario usuarioActivo;

    @Inject
    public UsuarioRepositoryImpl(UsuarioDAO usuarioDAO,UsuarioAPIService usuarioAPI) {

        this.usuarioDAO = usuarioDAO;
        this.usuarioAPI = usuarioAPI;

    }

    /*
    @Provides
    @Override
    public UsuarioRepository getInstance(){
        return this;
    }*/

    @Override
    public Call<ResultadoUsuario> loginUsuario(LoginObject loginObject) {

        ResultadoUsuario ru = null;
        Call<ResultadoUsuario> ruc;

        try {

            //Revisando desde el webapi (EN PROGRESO)
            ruc = usuarioAPI.login(loginObject);

            return ruc;

        } catch(Exception ex){

            Log.e("USUARIO_REPOSITORY","Error en loginUsuario!: " + ex.toString());
            return null;

        }

    }

    @Override
    public Call<ResultadoUsuario> getInfoUsuario(Usuario usuario) {

        Call<ResultadoUsuario> ruc;

        try {

            ruc = usuarioAPI.getInfoUsuario(usuario.id_usuario);

            return ruc;

        } catch(Exception ex){

            Log.e("USUARIO_REPOSITORY","Error en getInfoUsuario!: " + ex.toString());
            return null;

        }
    }

    @Override
    public Call<ResultadoID> updateUsuario(Usuario usuario){

        Call<ResultadoID> ruc;

        try {

            ruc = usuarioAPI.updateUsuario(usuario.id_usuario,usuario);

            return ruc;

        } catch(Exception ex){

            Log.e("USUARIO_REPOSITORY","Error en loginUsuario!: " + ex.toString());
            return null;

        }

    }

    @Override
    public Call<ResultadoID> changePasswordUsuario(ObjetoModificacionContrasena omc){

        try {

            Call<ResultadoID> ri = usuarioAPI.changePasswordUsuario(omc.id_usuario,omc);

            return ri;

        } catch(Exception ex){

            Log.e("USUARIO_REPOSITORY","Error en loginUsuario!: " + ex.toString());
            return null;

        }

    }

    private void saveUsuarioToDatabase(Usuario usuario){

        //Insertamos el usuario recuperado de login o getInfoUsuario
        //a la base de datos!
        usuarioDAO.upsert(usuario);
        setLoggedInUser(usuario);

    }

    private void setLoggedInUser(Usuario usuario) {
        this.usuarioActivo = usuario;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}