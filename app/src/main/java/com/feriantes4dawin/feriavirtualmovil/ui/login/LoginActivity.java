package com.feriantes4dawin.feriavirtualmovil.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualComponent;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.ui.main.MainActivity;
import com.feriantes4dawin.feriavirtualmovil.util.EnumMessageType;
import com.feriantes4dawin.feriavirtualmovil.util.FeriaVirtualConstants;
import com.feriantes4dawin.feriavirtualmovil.util.SimpleTextWatcherAdapter;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.MessageDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * LoginActivity 
 * 
 * Actividad que representa el formulario de autenticación, 
 * siendo esta la primera en aparecer al iniciar la aplicación. 
 * 
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Referencia al objeto contenedor de dependencias. 
     */
    private FeriaVirtualComponent feriaVirtualComponent;

    /**
     * Dependencia que representa a la fuente de datos 
     * de usuarios. 
     */
    @Inject
    public UsuarioRepositoryImpl usuarioRepository;

    /**
     * Dependencia que representa el objeto que convierte 
     * datos en JSON o visceversa. 
     */
    @Inject
    public Gson convertidorJSON;

    /**
     * Objeto intermediario que nos proporciona los datos. 
     */
    private LoginViewModel loginViewModel;

    /**
     * Objeto creador de instancias LoginViewModel. 
     */
    private LoginViewModelFactory loginViewModelFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        //Injectamos nuestras dependencias
        feriaVirtualComponent =  ((FeriaVirtualApplication) getApplicationContext())
                .getFeriaVirtualComponent();

        feriaVirtualComponent.injectIntoLoginActivity(this);

        //Creamos nuestro factory!
        loginViewModelFactory = new LoginViewModelFactory(
                usuarioRepository,
                convertidorJSON,
                (FeriaVirtualApplication) getApplicationContext());

        //Creamos el viewmodel con nuestro factory!
        loginViewModel = new ViewModelProvider(this,loginViewModelFactory)
            .get(LoginViewModel.class);

        //Obtengo las preferencias compartidas (por si acaso!)
        SharedPreferences sp = getSharedPreferences(
            FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,
            Context.MODE_PRIVATE);

        /**
         * Se hace un chequeo previo para ver si hay datos de sesión guardadas 
         * previamente, y de esta forma, evitar la autenticación. 
         * TODO: ¿Debería obligar al usuario a iniciar sesión cada vez?
         */
        if(revisarSiHaySesion(sp)){

            manejarSesion();

        } else {

            EditText username = findViewById(R.id.username);
            EditText password = findViewById(R.id.password);
            Button login = findViewById(R.id.login);
            LinearLayout loading = findViewById(R.id.dcp_llloading);
            Button  quit = findViewById(R.id.btnQuit);
            TextView linkRegistro = findViewById(R.id.tvLinkRegistro);
            Spanned textolink;

            textolink = HtmlCompat.fromHtml(
                    String.format("%s%s%s",
                            "<a href='https://www.google.com/?hl=es'>",
                            getResources().getString(R.string.launch_user_registry_web),
                            "</a>"),
                    HtmlCompat.FROM_HTML_MODE_COMPACT);

            //Le da el aspecto y acciones de un enlace
            linkRegistro.setText(textolink);
            linkRegistro.setMovementMethod(LinkMovementMethod.getInstance());

            loginViewModel.loginFormState.observe(this, new Observer<LoginFormState>() {

                @Override
                public void onChanged(LoginFormState loginFormState) {

                    if(loginFormState == null){
                        return;
                    }

                    // disable login button unless both username / password is valid
                    login.setEnabled(loginFormState.isDataValid);

                    if (loginFormState.usernameError != null) {
                        username.setError(getString(R.string.invalid_username));
                    } else {
                        username.setError(null);
                    }
                    if (loginFormState.passwordError != null) {
                        password.setError(getString(R.string.invalid_password));
                    } else {
                        password.setError(null);
                    }
                }

            });

            loginViewModel.loginResult.observe(this, new Observer<LoginResult>() {

                @Override
                public void onChanged(LoginResult loginResult) {

                    loading.setVisibility(View.GONE);

                    if(loginResult == null){
                        return;
                    }
                    else if (loginResult.error != null) {
                        showLoginFailed(loginResult);
                    }
                    else if (loginResult.success != null) {
                        updateUiWithUser(loginResult.success);
                        manejarSesion();

                    }

                }

            });

            username.addTextChangedListener(

                new SimpleTextWatcherAdapter(null) {
                    @Override
                    public void afterTextChanged(Editable s) {
                        loginViewModel.loginDataChanged(
                                username.getText().toString(),
                                password.getText().toString()
                        );
                    }
                }

            );

            password.addTextChangedListener(

                new SimpleTextWatcherAdapter(null) {
                    @Override
                    public void afterTextChanged(Editable s) {
                        loginViewModel.loginDataChanged(
                                username.getText().toString(),
                                password.getText().toString()
                        );
                    }
                }

            );

            password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId == EditorInfo.IME_ACTION_DONE){
                        loginViewModel.login(
                                username.getText().toString(),
                                password.getText().toString()
                        );
                    }
                    return false;
                }

            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loading.setVisibility(View.VISIBLE);
                    loginViewModel.login(username.getText().toString(), password.getText().toString());
                }
            });

            quit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }

    }

    /**
     * Método llamado cuando la autenticación es positiva, y 
     * se deriva al MainActivity. 
     * 
     * @param model Objeto con los detalles del usuario recién 
     * autenticado. 
     */
    private void updateUiWithUser(LoggedInUserView model) {
        
        String welcome = getString(R.string.welcome);
        String displayName = model.displayName;
        
        // TODO : initiate successful logged in experience
        Toast.makeText(
            getApplicationContext(),
            String.format("%s, %s",welcome,model.displayName),
            Toast.LENGTH_LONG
        ).show();
    }

    /**
     * Método que es llamado cuando el chequeo de validación de 
     * creadenciales falla por algún motivo.
     * 
     * @param loginResult Objeto LoginResult con los detalles del 
     * error. 
     */
    private void showLoginFailed(LoginResult loginResult) {

        View v = findViewById(R.id.actlog_svPrincipal);
        if(loginResult.error == R.string.invalid_user_role) {

            MessageDialog md = new MessageDialog(
                 this,
                EnumMessageType.ERROR_MESSAGE,
                getString(R.string.err_mes_not_allowed),
                loginResult.errorMessage,
                null
            );

            md.generate().show();

        } else {
            Log.e("LOGIN_ACTIVITY",String.format("codigo de error: %d - %s",loginResult.error,getString(loginResult.error)));
            Snackbar.make( v, getString(loginResult.error), Snackbar.LENGTH_SHORT).show();

        }

    }


    /**
     * Chequeo rápido en los datos en cache para verificar la 
     * existencia (no integridad) de las credenciales de acceso 
     * o datos del usuario en si.
     * 
     * TODO: Realizar un chequeo de seguridad de los mismos.
     * 
     * @param sp Objeto SharedPreferences entregado por la 
     * aplicación. 
     * @return true si los datos existen, o false si no.
     */
    public boolean revisarSiHaySesion(SharedPreferences sp){

        return (!sp.getString(FeriaVirtualConstants.SP_FERIAVIRTUAL_WEBAPI_AUTH_TOKEN,"").equals(""))
                && (!sp.getString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR,"").equals(""));

    }

    /**
     * Método que solamente inicia la actividad MainActivity. 
     * Cabe destacar que esto solo debe llamarse cuando la 
     * autenticación es válida.
     */
    public void manejarSesion(){

        //Cambiamos a la actividad principal
        Intent menuprincipalintent = new Intent(getApplicationContext(), MainActivity.class);
        SharedPreferences sp = getSharedPreferences(FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,Context.MODE_PRIVATE);
        menuprincipalintent.putExtra("usuario-autenticado",sp.getString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR,""));

        startActivity(menuprincipalintent);

        setResult(Activity.RESULT_OK);

        //Complete and destroy login activity once successful
        finish();

    }
}