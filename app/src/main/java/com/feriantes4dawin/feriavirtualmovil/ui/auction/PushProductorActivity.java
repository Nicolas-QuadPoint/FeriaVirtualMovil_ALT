package com.feriantes4dawin.feriavirtualmovil.ui.auction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualComponent;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.ui.util.SimpleAction;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.YesNoDialog;
import com.google.gson.Gson;

import javax.inject.Inject;


public class PushProductorActivity extends AppCompatActivity {

    private FeriaVirtualComponent feriaVirtualComponent;

    @Inject
    public Gson convertidorJSON;

    @Inject
    public SubastaRepositoryImpl subastaRepository;

    @Inject
    public VentaRepositoryImpl ventaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_productor);

        // Creation of the login graph using the application graph
        feriaVirtualComponent = ((FeriaVirtualApplication) getApplicationContext())
                .getFeriaVirtualComponent();

        // Make Dagger instantiate @Inject fields in LoginActivity
        feriaVirtualComponent.injectIntoPushProductorActivity(this);

        Button btnPujar = findViewById(R.id.tbpm_btnPujar);
        Button btnEliminar = findViewById(R.id.tbpm_btnEliminar);
        Button btnCancelar = findViewById(R.id.tbpm_btnCancelar);
        RecyclerView rvListaProductosPuja = findViewById(R.id.dppb_rvListaProductos);


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YesNoDialog ynd = new YesNoDialog(
                        PushProductorActivity.this,
                        getString(R.string.action_confirm),
                        getString(R.string.confirm_delete_push),
                        new SimpleAction() {
                            @Override
                            public void doAction(Object o) {

                                Toast.makeText(PushProductorActivity.this,"Debería hacer algo aquí!",Toast.LENGTH_SHORT).show();

                            }
                        },null);

            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}