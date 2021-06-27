package com.feriantes4dawin.feriavirtualmovil.ui.auction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualComponent;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.Producto;
import com.feriantes4dawin.feriavirtualmovil.data.models.Productos;
import com.feriantes4dawin.feriavirtualmovil.data.models.TipoVenta;
import com.feriantes4dawin.feriavirtualmovil.data.repos.ProductoRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.ProductoRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.ui.util.SimpleAction;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.SimpleSpinnerArrayAdapter;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.YesNoDialog;
import com.google.android.material.snackbar.Snackbar;
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

    @Inject
    public ProductoRepositoryImpl productoRepository;

    private AuctionViewModel auctionViewModel;
    private AuctionViewModelFactory auctionViewModelFactory;

    private Integer id_venta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_productor);

        // Creation of the login graph using the application graph
        feriaVirtualComponent = ((FeriaVirtualApplication) getApplicationContext())
                .getFeriaVirtualComponent();

        // Make Dagger instantiate @Inject fields in LoginActivity
        feriaVirtualComponent.injectIntoPushProductorActivity(this);

        //Creando mi super duper factory y viewmodel!
        this.auctionViewModelFactory = new AuctionViewModelFactory(
                (FeriaVirtualApplication) getApplication(),
                null,
                null,
                subastaRepository,
                productoRepository
        );

        this.auctionViewModel = new ViewModelProvider(this,auctionViewModelFactory)
                .get(AuctionViewModel.class);

        this.id_venta = getIntent().getIntExtra("id_venta",0);

        Button btnPujar = findViewById(R.id.tbpm_btnPujar);
        Button btnEliminar = findViewById(R.id.tbpm_btnEliminar);
        Button btnCancelar = findViewById(R.id.tbpm_btnCancelar);
        EditText txtCantidadProductos = findViewById(R.id.app_txtCantidad);
        Spinner spTipoVenta = findViewById(R.id.app_spTipoVenta);
        Spinner spProductos = findViewById(R.id.app_spProducto);
        View pantallaCarga = findViewById(R.id.app_llloading);
        View contenedorPrincipal = findViewById(R.id.app_contenedorPrincipal);

        ArrayAdapter<TipoVenta> adaptadorSpTipoVenta = new ArrayAdapter<TipoVenta>(
            this,
                android.R.layout.simple_spinner_dropdown_item,
                ventaRepository.getTiposVentaLocal()
        );

        adaptadorSpTipoVenta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spTipoVenta.setAdapter(
            adaptadorSpTipoVenta
        );

        spTipoVenta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnPujar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DetallePujaSubastaProductor detallePujaProductor = new DetallePujaSubastaProductor();

                detallePujaProductor.tipo_venta = (TipoVenta) spTipoVenta.getSelectedItem();
                detallePujaProductor.cantidad = Integer.valueOf(txtCantidadProductos.getText().toString());
                detallePujaProductor.id_producto = ((Producto) spProductos.getSelectedItem());
                detallePujaProductor.id_venta = PushProductorActivity.this.id_venta;

                auctionViewModel.pujarSubastaProductor(detallePujaProductor, new SimpleAction() {
                    @Override
                    public void doAction(Object o) {

                        Integer resultado = (Integer)o;
                        finalizarActividad( Boolean.valueOf( resultado != 0 ) );

                    }
                });

                pantallaCarga.setVisibility(View.VISIBLE);

            }
        });

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

        auctionViewModel.obtenerProductos().observe(this,new Observer<Productos>(){
            @Override
            public void onChanged(Productos productos) {

                if(productos != null){

                    ArrayAdapter<Producto> adaptadorSpTipoVenta = new ArrayAdapter<Producto>(
                            PushProductorActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            productos.productos
                    );

                    adaptadorSpTipoVenta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spProductos.setAdapter(
                        adaptadorSpTipoVenta
                    );

                } else {

                    finalizarActividad(false);

                }

                contenedorPrincipal.setClickable(true);
                pantallaCarga.setVisibility(View.GONE);
            }
        });

        contenedorPrincipal.setClickable(false);
        pantallaCarga.setVisibility(View.VISIBLE);

    }

    public void finalizarActividad(boolean exito){

        Intent i = new Intent();
        i.putExtra("producto_agregado",exito);
        i.putExtra("codigo_error",exito? R.string.err_code_str_ok : R.string.err_mes_product_add_failed);
        setResult(RESULT_OK,i);
        finish();
    }
}