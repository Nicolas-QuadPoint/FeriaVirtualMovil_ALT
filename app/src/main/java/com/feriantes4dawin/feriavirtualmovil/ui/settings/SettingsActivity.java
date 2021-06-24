package com.feriantes4dawin.feriavirtualmovil.ui.settings;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants;

/**
 * SettingsActivity 
 * 
 * Actividad que representa una simple sección de configuración 
 * para la aplicación.
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Objeto SettingsViewModel para poder enviar datos 
     * a la base de datos. 
     */
    private SettingsViewModel viewmodel;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings2, new SettingsFragment())
                .commit();
        }
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        this.viewmodel = new ViewModelProvider(this).get(SettingsViewModel.class);
    }

    @Override
    public void onBackPressed() {

        /**
         * Con esto se evita que, al acceder a esta actividad 
         * desde un fragmento determinado, se regrese al HomeFragment, 
         * y mejora la 'experiencia de usuario'.
         * TODO:Aprender el flujo de fragmentos de mejor forma!
         */
        finish();
    }

}