package com.feriantes4dawin.feriavirtualmovil.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.LoginObject;
import com.feriantes4dawin.feriavirtualmovil.data.models.ResultadoUsuario;
import com.feriantes4dawin.feriavirtualmovil.data.models.Rol;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepository;
import com.feriantes4dawin.feriavirtualmovil.util.FeriaVirtualConstants;
import com.google.gson.Gson;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LoginViewModel 
 * 
 * Clase que actúa como intermediario para ofrecer y enviar 
 * datos entre la actividad LoginActivity y la fuente de datos. 
 */
public class LoginViewModel extends ViewModel {

    /**
     * Objeto usado como fuente de datos para 
     * usuarios. 
     */
    private UsuarioRepository usuarioRepository;

    /**
     * Objeto convertidor de datos en JSON. 
     */
    private Gson convertidorJSON;

    /**
     * Objeto Application usado para algunas 
     * cosas. 
     */
    private FeriaVirtualApplication fva;

    /**
     * Puente de datos que vigilan las credenciales 
     * de acceso cuando éstos son modificados. 
     */
    public LiveData<LoginFormState> loginFormState;
    public MutableLiveData<LoginFormState> _loginForm;

    /**
     * Puente de datos que vigilan el resultado final 
     * de la validación de datos de las credenciales. 
     */
    public LiveData<LoginResult> loginResult;
    private MutableLiveData<LoginResult> _loginResult;

    @Inject
    public LoginViewModel(UsuarioRepository usuarioRepository, Gson convertidorJSON, FeriaVirtualApplication fva) {

        this.usuarioRepository = usuarioRepository;
        this.convertidorJSON = convertidorJSON;
        this.fva = fva;

        this._loginForm = new MutableLiveData<LoginFormState>();
        this._loginResult = new MutableLiveData<LoginResult>();
        this.loginResult = _loginResult;
        this.loginFormState = _loginForm;

    }

    /**
     * Método que realiza el proceso de autenticación, 
     * enviado las credenciales a la fuente de datos. Como es 
     * una rutina asíncrona, la respuesta es actualizada en el 
     * puente de datos loginResult, que debería ser vigilado para 
     * notar los cambios. 
     * 
     * @param username Identificador o email del usuario. 
     * @param password Contraseña del usuario. 
     */
    public void login(String username, String password) {

        Call<ResultadoUsuario> result = usuarioRepository.loginUsuario(new LoginObject(username,password));
        SharedPreferences sp = fva.getSharedPreferences(FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES, Context.MODE_PRIVATE);

        try {

            result.enqueue(new Callback<ResultadoUsuario>() {

                @Override
                public void onResponse(Call<ResultadoUsuario> call, Response<ResultadoUsuario> response) {

                    if(response.isSuccessful() && response.body() != null){

                        ResultadoUsuario rul = response.body();

                        /**
                         * Esta app solo permite usuarios transportistas, y productores
                         * así que si un rol de usuario es distinto a estos, entonces invalidamos todos
                         * los roles ajenos a estos
                         */
                        if(Rol.TRANSPORTISTA.equalsValues(rul.usuario.rol) ||
                           Rol.PRODUCTOR.equalsValues(rul.usuario.rol)) {

                            String usuarioString = convertidorJSON.toJson(rul.usuario);

                            sp.edit()
                                    .putString(FeriaVirtualConstants.SP_FERIAVIRTUAL_WEBAPI_AUTH_TOKEN,rul.token_usuario)
                                    .putString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR,usuarioString)
                                    .commit();

                            _loginResult.setValue(new LoginResult(new LoggedInUserView( rul.usuario.nombre ),null));

                            Log.i("LOGIN_VIEW_MODEL","Valor de token obtenido!: "+ rul.token_usuario);

                        } else {

                            _loginResult.setValue(new LoginResult(null,
                                R.string.invalid_user_role,
                                String.format(
                                    fva.getString(R.string.invalid_user_role),
                                    rul.usuario.rol.descripcion)
                                )
                            );

                        }

                    } else {
                        _loginResult.setValue(new LoginResult(null,R.string.login_failed));
                    }
                }

                @Override
                public void onFailure(Call<ResultadoUsuario> call, Throwable t) {
                    _loginResult.setValue(new LoginResult(null,R.string.login_failed));
                }
            });

        } catch( Exception ex ){

            Log.e("LOGIN_VIEW_MODEL","Error en login?: "+ex.toString());
            _loginResult.setValue(new LoginResult(null,R.string.login_failed));

        }

    }

    /**
     * Método llamado en el momento que los campos de texto del 
     * formulario de acceso son modificados, validándolos en 
     * el proceso. 
     * @param username Identificador o email del usuario. 
     * @param password Contraseña del usuario. 
     */
    public void loginDataChanged(String username,String password) {

        if (!isUserNameValid(username)) {

            _loginForm.setValue(new LoginFormState(R.string.invalid_username,null,false));

        } else if (!isPasswordValid(password)) {

            _loginForm.setValue(new LoginFormState(null,R.string.invalid_password,false));

        } else {

            _loginForm.setValue(new LoginFormState(null,null,true));

        }
    }

    /**
     * Método individual que valida el nombre del usuario. 
     * En este caso, valida solamente emails. 
     * 
     * @param username El Identificador o email del usuario. 
     * @return true si username cumple con las reglas de negocio, 
     * de lo contrario, false. 
     */
    private boolean isUserNameValid(String username) {

        return Patterns.EMAIL_ADDRESS.matcher(username).matches() && !username.isEmpty();

    }

    /**
     * Método individual que valida la contraseña en base a 
     * reglas de negocio. 
     * 
     * @param password La contraseña del usuario. 
     * @return true si password cumple con las reglas, o false. 
     */
    private boolean isPasswordValid(String password){

        return password.length() >= 5 && !password.isEmpty();

    }
}