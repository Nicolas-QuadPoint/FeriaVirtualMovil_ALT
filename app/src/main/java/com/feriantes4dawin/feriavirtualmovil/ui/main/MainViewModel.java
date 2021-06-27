package com.feriantes4dawin.feriavirtualmovil.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoUsuario;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepository;
import com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MainViewModel 
 * 
 * Clase que actúa como intermediario para ofrecer y enviar 
 * datos entre la actividad MainActivity y la fuente de datos. 
 */
public class MainViewModel extends ViewModel {
    
    /**
     * Objeto de fuente de datos de usuarios. 
     */
    private UsuarioRepository usuarioRepository;

    /**
     * Objeto convertidor de datos JSON. 
     */
    private Gson convertidorJSON;

    /**
     * Referencia a objeto Application, usado para algunas 
     * cosas. 
     */
    private FeriaVirtualApplication feriaVirtualApplication;
    
    /**
     * Puente de datos entre esta clase y MainActivity. 
     */
    private LiveData<Usuario> datosUsuario;
    private MutableLiveData<Usuario> datosMutablesUsuario;
    
    
    public MainViewModel(UsuarioRepository usuarioRepository, Gson convertidorJSON, FeriaVirtualApplication feriaVirtualApplication){
        
        this.usuarioRepository = usuarioRepository;
        this.convertidorJSON = convertidorJSON;
        this.feriaVirtualApplication = feriaVirtualApplication;

        this.datosMutablesUsuario = new MutableLiveData<Usuario>();
        this.datosUsuario = datosMutablesUsuario;

    }

    /**
     * Realiza la consulta de datos de usuario a la fuente, 
     * de forma asíncrona. 
     * 
     * @return Un objeto LiveData para vigilar en caso de que hayan 
     * datos disponibles. 
     */
    public LiveData<Usuario> getDatosUsuario(){

        //Rutina asíncrona!
        cargarDatosUsuario();

        return datosUsuario;
    }

    /**
     * Rutina asíncrona que se encarga de recuperar los datos 
     * del usuario, actualizando el puente de datos para así 
     * llenar la vista de MainActivity con 
     * nueva información. 
     */
    private void cargarDatosUsuario(){

        try{

            SharedPreferences sp;
            String usuarioString;
            Usuario usuario;
            Call<ResultadoUsuario> resultadoUsuarioCall;

            sp = feriaVirtualApplication.getSharedPreferences(
                    FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,
                    Context.MODE_PRIVATE
            );

            usuarioString = sp.getString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR,"");
            usuario = convertidorJSON.fromJson(usuarioString,Usuario.class);

            resultadoUsuarioCall = usuarioRepository.getInfoUsuario(usuario);

            resultadoUsuarioCall.enqueue(new Callback<ResultadoUsuario>() {

                @Override
                public void onResponse(Call<ResultadoUsuario> call, Response<ResultadoUsuario> response) {

                    Log.i("MAIN_VIEW_MODEL",String.format("Código de respuesta http: %d",response.code()));

                    if(response.isSuccessful()){

                        datosMutablesUsuario.setValue(response.body().usuario);

                    } else {

                        datosMutablesUsuario.setValue(usuario);

                    }


                }

                @Override
                public void onFailure(Call<ResultadoUsuario> call, Throwable t) {
                    //Ignoramos el error de forma silenciosa... no sin antes decirlo en log
                    Log.e("MAIN_VIEW_MODEL",String.format("No se pudo obtener datos de usuario!: %s",t.toString()));
                    datosMutablesUsuario.setValue(usuario);
                }

            });

        } catch(Exception ex){

            Log.e("MAIN_VIEW_MODEL",String.format("No se pudo obtener datos de usuario!: %s",ex.toString()));
            datosMutablesUsuario.setValue(null);
        }

    }
}