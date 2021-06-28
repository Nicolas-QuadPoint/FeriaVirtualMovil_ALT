package com.feriantes4dawin.feriavirtualmovil.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualComponent;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.ui.login.LoginActivity;
import com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants;
import com.feriantes4dawin.feriavirtualmovil.ui.util.SimpleAction;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.YesNoDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * MainActivity 
 * 
 * La actividad principal de la aplicación, donde los fragmentos 
 * o secciones podrán acoplarse, permitiendo también la navegación 
 * entre ellas a través de la barra de menú lateral. 
 * 
 * También posee una barra superior donde están las opciones para 
 * cerrar sesión o configurar la aplicación. 
 */
public class MainActivity extends AppCompatActivity{

    /* Objeto que permite establecer datos en la barra de navegación */
    private AppBarConfiguration appBarConfiguration;

    /**
     * Intermediario de datos entre esta actividad y la 
     * fuente de datos. 
     */
    private MainViewModel mainViewmodel;

    /**
     * Creador de instancias de MainViewModel. 
     */
    private MainViewModelFactory mainViewModelFactory;

    /**
     * Referencia al objeto sostenedor de dependencias. 
     */
    public FeriaVirtualComponent feriaVirtualComponent;

    /**
     * Dependencia que representa el origen de datos 
     * para usuarios. 
     */
    @Inject
    public UsuarioRepositoryImpl usuarioRepository;

    /**
     * Dependencia que representa el objeto para convertir 
     * datos JSON y visceversa. 
     */
    @Inject
    public Gson convertidorJSON;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_sales, R.id.nav_processes, R.id.nav_profile)
                .setOpenableLayout(drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController,appBarConfiguration);
        NavigationUI.setupWithNavController(navView,navController);

        // Se obtiene la referencia al objeto sostenedor de dependencias. 
        feriaVirtualComponent = ((FeriaVirtualApplication) this.getApplicationContext())
                .getFeriaVirtualComponent();

        // Con eso inyectamos nuestras dependencias para usarlas.
        feriaVirtualComponent.injectIntoMainActivity(this);

        //Creamos nuestro factory!
        this.mainViewModelFactory = new MainViewModelFactory(
                usuarioRepository,
                convertidorJSON,
                (FeriaVirtualApplication)getApplicationContext()
        );

        //Creamos nuestro viewmodel
        this.mainViewmodel = new ViewModelProvider(this, mainViewModelFactory).
                get(MainViewModel.class);


        //Observamos cambios del viewmodel, para actualizar campos!
        mainViewmodel.getDatosUsuario().observe(this, new Observer<Usuario>(){

            @Override
            public void onChanged(Usuario usuario) {

                actualizarDatosMenuLateral(usuario);

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Este metodo resuelve la accion seleccionada del menu superior de la actividad
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.action_logout:{
                
                /**
                 * Generamos el diálogo para preguntar si se quiere cerrar la sesión o no.
                 */
                YesNoDialog yesno = new YesNoDialog(this, getString(R.string.err_mes_question), getString(R.string.err_msg_logout_prompt),
                        new SimpleAction() {
                            @Override
                            public void doAction(Object o) {

                                cerrarSesion();

                            }
                        },
                    null
                );

                yesno.generate().show();

                return true;

            }
            default:{
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
            }
        }


    }

    @Override
    public void onBackPressed() {
        
        /**
         * Esto actualmente no se ejecuta, por razones que no tengo entendidas.
         * TODO: Hacer que esto funcione, y el diálogo aparezca al presionar el 
         * botón de retroceso!
         */
        YesNoDialog yesno = new YesNoDialog(this, getString(R.string.err_mes_question), getString(R.string.err_msg_logout_prompt),
                new SimpleAction() {
                    @Override
                    public void doAction(Object o) {
                        cerrarSesion();
                    }
                },
                null
        );
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri ubicacionImagenSeleccionada;

        if (resultCode == RESULT_OK && requestCode == FeriaVirtualConstants.ESCOGER_IMAGEN_REQUEST){

            ubicacionImagenSeleccionada = data.getData();

            Log.i("MAIN_ACTIVITY",String.format("Imágen recuperada: %s",
                    (ubicacionImagenSeleccionada == null)? "null" : ubicacionImagenSeleccionada.getPath()));

        } else if (data.hasExtra("fragment")){
            Log.i("MAIN_ACTIVITY","Esto vino de algun fragmento!!!");
        }

    }

    public void navLogoutClick(View v){

        Log.i("[MAIN_ACTIVITY]","Logout request!");

    }

    public void actualizarDatosMenuLateral(Usuario usuario){

        TextView lblNombreUsuario;
        TextView lblEmailUsuario;
        TextView lblRolUsuario;

        if(usuario != null){

            lblNombreUsuario = findViewById(R.id.nhm_lblNombreUsuario);
            lblEmailUsuario = findViewById(R.id.nhm_lblEmailUsuario);
            lblRolUsuario = findViewById(R.id.nhm_lblRolUsuario);

            lblNombreUsuario.setText( usuario.getNombreCompleto() );
            lblEmailUsuario.setText( usuario.email );
            lblRolUsuario.setText( usuario.rol.descripcion );

        }
    }

    /**
     * Método que hace los arreglos para cerrar la sesión y hacer que el usuario 
     * nuevamente tenga que ingresar sus credenciales para acceder. 
     */
    public void cerrarSesion(){


        SharedPreferences sp = getSharedPreferences(
                FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        Intent loginActivityIntent = new Intent(getApplicationContext(), LoginActivity.class);
        
        /**
         * Con esto nos evitamos la molestia de de que el usuario regrese al menu principal 
         * presionando la tecla de retroceso. 
         */
        loginActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        //Limpiando los datos de las shared preferences!
        sp.edit()
                .putString(FeriaVirtualConstants.SP_FERIAVIRTUAL_WEBAPI_AUTH_TOKEN,"")
                .putString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR,"")
                .putInt(FeriaVirtualConstants.SP_USUARIO_ID,0)
                .putInt(FeriaVirtualConstants.SP_VENTA_ID,0)
                .commit();

        startActivity(loginActivityIntent);


    }
}