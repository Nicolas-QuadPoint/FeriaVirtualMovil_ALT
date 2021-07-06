package com.feriantes4dawin.feriavirtualmovil.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.models.ObjetoModificacionContrasena;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoID;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoUsuario;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepository;
import com.feriantes4dawin.feriavirtualmovil.util.FeriaVirtualConstants;
import com.feriantes4dawin.feriavirtualmovil.util.SimpleAction;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MyProfileViewModel 
 * 
 * Clase que actúa como intermediario para ofrecer y enviar 
 * datos entre el fragmento MyProfileFragment y la fuente de datos. 
 */
public class MyProfileViewModel extends ViewModel {

    /**
     * Origen de datos para Usuarios. 
     */
    private UsuarioRepository usuarioRepository;

    /**
     * Convertidor JSON, para lidiar con estos tipos de datos. 
     */
    private Gson convertidorJSON;

    /**
     * Instancia de Application, usado para algunas cosas. 
     */
    private FeriaVirtualApplication fva;

    /**
     * Puente de datos para MyProfileFragment. 
     */
    private MutableLiveData<Usuario> datosMutablesUsuario;
    private LiveData<Usuario> datosUsuario;

    public MyProfileViewModel(UsuarioRepository ur, Gson convertidorJSON, FeriaVirtualApplication fva) {

        this.usuarioRepository = ur;
        this.convertidorJSON = convertidorJSON;
        this.fva = fva;
        this.datosMutablesUsuario = new MutableLiveData<Usuario>();
        this.datosUsuario = datosMutablesUsuario;

    }

    /**
     * Consulta al origen de datos para obtener información del usuario 
     * actualmente autenticado. La información de dicho usuario (Como el ID), 
     * requerido para esta acción, es obtenido de las preferencias compartidas. 
     * 
     * @return Un objeto LiveData para vigilar. 
     */
    public LiveData<Usuario> getDatosUsuario() {

        /**
         * Ejecutara una rutina asíncrona, donde dependiendo del
         * dato a obtener, se podrá actualizar los datos de usuario
         * en la vista del fragmento o no.
         */
        cargarDatosUsuario();
        
        return datosUsuario;
    }

    /**
     * Rutina asíncrona para recuperar datos de usuario. 
     * Actualiza el puente de datos para que la capa de vista, 
     * MyProfileFragment, pueda cargar dicha información a sus 
     * componentes. 
     */
    private void cargarDatosUsuario() {

        try {

            Usuario u;
            String usuarioString;
            Call<ResultadoUsuario> ruc;
            SharedPreferences sp = fva.getSharedPreferences(
                    FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,
                    Context.MODE_PRIVATE
            );

            usuarioString = sp.getString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR, "");
            u = convertidorJSON.fromJson(usuarioString,Usuario.class);
            if(u != null){
                Log.i("MY_PROFILE_FRAG",u.toString());
            }

            ruc = this.usuarioRepository.getInfoUsuario(u);

            if(ruc != null){
                ruc.enqueue(new Callback<ResultadoUsuario>() {
                    @Override
                    public void onResponse(Call<ResultadoUsuario> call, Response<ResultadoUsuario> response) {

                        //Establecemos los datos recuperados de usuario aquí. Si en
                        //MyProfileFragment están obesrvando el objeto 'LiveData' asociado a
                        //datosMutablesUsuario, entonces el evento onChange se activará, y podremos
                        //mostrar datos al usuario
                        if(response.body() != null){
                            datosMutablesUsuario.setValue(response.body().usuario);
                        } else {
                            datosMutablesUsuario.setValue(null);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResultadoUsuario> call, Throwable t) {
                        Log.e("MY_PROFILE_VIEW_MODEL", "Error! no se pudo recuperar datos de usuario!: " + t.toString());
                        datosMutablesUsuario.setValue(null);
                    }
                });

            } else {

                Log.e("MY_PROFILE_VIEW_MODEL", "Error! no se pudo recuperar datos de usuario!: Objeto respuesta no pudo obtenerse ");
                datosMutablesUsuario.setValue(null);

            }


        } catch(Exception ex) {
            Log.e("MY_PROFILE_VIEW_MODEL", String.format("Ocurrio un error!!!: %s",ex.toString()) );
            datosMutablesUsuario.setValue(null);
        }

    }

    /**
     * Rutina asíncrona que envía la nueva información de 
     * usuario en el puente de datos a la fuente de datos 
     * determinada. 
     * 
     * TODO: Agregar la lógica que permite lo anterior!
     * 
     * @param u El Objeto usuario a actualizar en la fuente. 
     */
    public void guardarCambiosUsuario(Usuario u){

    }

    /**
     * Rutina asíncrona cuya tarea es cambiar la contraseña 
     * del usuario actualmente autenticado, además de proporcionar 
     * un método callback para que el se pueda actuar en dependencia 
     * del resultado de la petición. 
     * 
     * @param omc Objeto ObjetoModificacionContrasena con la información 
     * de la contraseña de usuario. 
     * @param simpleAction Objeto SimpleAction con el procedimiento a 
     * ejecutar tras finalizada la petición. Si es null, este método 
     * finaliza y ninguna llamada es realizada. 
     */
    public void cambiarContrasena(ObjetoModificacionContrasena omc, SimpleAction simpleAction) {

        Call<ResultadoID> ruc = usuarioRepository.changePasswordUsuario(omc);

        if(simpleAction == null) {
            return;
        }

        if(ruc != null){

            ruc.enqueue(new Callback<ResultadoID>() {
                @Override
                public void onResponse(Call<ResultadoID> call, Response<ResultadoID> response) {

                    Log.i("MY_PROFILE_VIEW_MODEL",String.format("Código de respuesta: %d",response.code()));
                    Boolean resultadofinal;

                    if(response.isSuccessful()){
                        resultadofinal = new Boolean(response.body().id_resultado == 1);
                    } else {
                        resultadofinal = new Boolean(false);
                    }

                    simpleAction.doAction(resultadofinal);

                }

                @Override
                public void onFailure(Call<ResultadoID> call, Throwable t) {
                    simpleAction.doAction(Boolean.valueOf(false));
                }
            });

        } else {

            simpleAction.doAction(Boolean.valueOf(false));
        }

    }
}

