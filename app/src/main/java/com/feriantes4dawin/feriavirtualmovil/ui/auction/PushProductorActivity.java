package com.feriantes4dawin.feriavirtualmovil.ui.auction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualComponent;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.Producto;
import com.feriantes4dawin.feriavirtualmovil.data.models.Productos;
import com.feriantes4dawin.feriavirtualmovil.data.models.TipoVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.util.FeriaVirtualConstants;
import com.feriantes4dawin.feriavirtualmovil.util.SimpleAction;
import com.feriantes4dawin.feriavirtualmovil.util.SimpleTextWatcherAdapter;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.YesNoDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.feriantes4dawin.feriavirtualmovil.util.FeriaVirtualConstants.*;


public class PushProductorActivity extends AppCompatActivity {

    private FeriaVirtualComponent feriaVirtualComponent;

    @Inject
    public Gson convertidorJSON;

    @Inject
    public SubastaRepositoryImpl subastaRepository;

    @Inject
    public VentaRepositoryImpl ventaRepository;

    private AuctionViewModel auctionViewModel;
    private AuctionViewModelFactory auctionViewModelFactory;

    private Integer id_venta;

    private DetallePujaSubastaProductor detalle;

    private List<Producto> listaProductos;

    private Usuario usuario;


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
                convertidorJSON
        );

        this.auctionViewModel = new ViewModelProvider(this,auctionViewModelFactory)
                .get(AuctionViewModel.class);

        SharedPreferences sp = getSharedPreferences(
            FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        );

        this.id_venta = getIntent().getIntExtra("id_venta",0);
        this.usuario = convertidorJSON.fromJson(sp.getString(SP_USUARIO_OBJ_STR,""),Usuario.class);

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

        btnPujar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DetallePujaSubastaProductor detallePujaProductor = new DetallePujaSubastaProductor();

                detallePujaProductor.tipo_venta = (TipoVenta) spTipoVenta.getSelectedItem();
                detallePujaProductor.cantidad = Integer.valueOf(txtCantidadProductos.getText().toString());
                detallePujaProductor.producto = ((Producto) spProductos.getSelectedItem());
                detallePujaProductor.id_venta = PushProductorActivity.this.id_venta;

                auctionViewModel.pujarSubastaProductor(detallePujaProductor, new SimpleAction() {
                    @Override
                    public void doAction(Object o) {

                        Integer resultado = (Integer)o;
                        finalizarActividad(
                            FeriaVirtualConstants.ACCION_AGREGAR_PUJA,
                            Boolean.valueOf( resultado != 0 ),
                            R.string.err_mes_add_push_failed
                        );

                    }
                });

                pantallaCarga.setVisibility(View.VISIBLE);

            }
        });

        txtCantidadProductos.addTextChangedListener( new SimpleTextWatcherAdapter(txtCantidadProductos){

            @Override
            public void afterTextChanged(Editable e) {
                try{

                    if(e.toString().trim().isEmpty() || Long.valueOf(e.toString()) == 0l){
                        txtCantidadProductos.setError(getString(R.string.err_mes_product_quantity_invalid));
                        btnPujar.setEnabled(false);
                    } else {

                        btnPujar.setEnabled(true);

                    }

                } catch(Exception ex) {

                    txtCantidadProductos.setError(getString(R.string.err_mes_product_quantity_invalid));
                    btnPujar.setEnabled(false);

                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        auctionViewModel.obtenerProductos(usuario.id_usuario.intValue()).observe(this,new Observer<Productos>(){
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

                    PushProductorActivity.this.listaProductos = new ArrayList<>(productos.productos);

                    configurarDatosEntrada();

                    contenedorPrincipal.setClickable(true);
                    pantallaCarga.setVisibility(View.GONE);

                } else {

                    finalizarActividad(
                            FeriaVirtualConstants.ACCION_AGREGAR_PUJA,
                            false,
                            R.string.err_code_str_cant_recover_data);
                }

            }
        });

        contenedorPrincipal.setClickable(false);
        pantallaCarga.setVisibility(View.VISIBLE);

        //Con esto evitamos que envie o modifique la cantidad a valores invalidos
        btnPujar.setEnabled(false);
    }


    public void configurarDatosEntrada(){

        Button btnPujar = findViewById(R.id.tbpm_btnPujar);
        Button btnEliminar = findViewById(R.id.tbpm_btnEliminar);
        Button btnCancelar = findViewById(R.id.tbpm_btnCancelar);
        EditText txtCantidadProductos = findViewById(R.id.app_txtCantidad);
        Spinner spTipoVenta = findViewById(R.id.app_spTipoVenta);
        Spinner spProductos = findViewById(R.id.app_spProducto);
        View pantallaCarga = findViewById(R.id.app_llloading);
        Intent datosPeticion = getIntent();

        /* Aqui chequeo el modo de accion */
        if(datosPeticion.getIntExtra(CODIGO_ACCION,-1) != ACCION_AGREGAR_PUJA){

            try {

                //Aqui doy los datos del detalle!
                this.detalle = convertidorJSON.fromJson(
                        datosPeticion.getStringExtra(
                                FeriaVirtualConstants.SP_DETALLE_PUJA_PROD_STR
                        ),
                        DetallePujaSubastaProductor.class
                );

                if(detalle != null){

                    txtCantidadProductos.setText( detalle.cantidad.toString() );

                    if(listaProductos != null){

                        Producto productoEncontrado = null;

                        for(Producto p : listaProductos){

                            if(detalle.producto.id_producto.compareTo(p.id_producto) == 0){
                                productoEncontrado = p;
                                break;
                            }

                        }

                        Log.e("PUSH_PRODUCTOR_ACT",String.format(
                                "Producto del detalle: %s |"+
                                "Producto de la lista: %s ",productoEncontrado,
                                ((ArrayAdapter)spProductos.getAdapter()).getPosition(
                                        productoEncontrado
                                )
                                ));

                        spProductos.setSelection(((ArrayAdapter)spProductos.getAdapter()).getPosition(
                            productoEncontrado
                        ));

                    }

                    spTipoVenta.setSelection(((ArrayAdapter)spTipoVenta.getAdapter()).getPosition(
                            TipoVenta.getTipoVentaByID(detalle.tipo_venta.id_tipo_venta)
                    ));

                    //seleccionarElementoDeSpinner(spTipoVenta,TipoVenta.getTipoVentaByID(detalle.tipo_venta.id_tipo_venta));

                } else {

                    finalizarActividad(
                            FeriaVirtualConstants.ACCION_VISUALIZAR_PUJA,
                            false,
                            R.string.err_code_str_cant_recover_data);
                }


                if(datosPeticion.getBooleanExtra(FeriaVirtualConstants.MODO_SOLO_LECTURA,false)){

                    //Aqui quito control al usuario para que actue
                    txtCantidadProductos.setEnabled(false);
                    spProductos.setEnabled(false);
                    spTipoVenta.setEnabled(false);
                    btnPujar.setEnabled(false);
                    btnCancelar.setText(R.string.action_accept);


                } else if(datosPeticion.getIntExtra(CODIGO_ACCION, -1) == ACCION_MODIFICAR_PUJA) {

                    /* Aqui es modo modificacion! */
                    btnPujar.setText(R.string.action_update);
                    btnEliminar.setEnabled(true);

                    btnPujar.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            detalle.tipo_venta = (TipoVenta) spTipoVenta.getSelectedItem();
                            detalle.cantidad = Integer.valueOf(txtCantidadProductos.getText().toString());
                            detalle.producto = ((Producto) spProductos.getSelectedItem());
                            //detalle.id_venta = PushProductorActivity.this.id_venta;

                            auctionViewModel.modificarPujaProductor(detalle, new SimpleAction() {
                                @Override
                                public void doAction(Object o) {

                                    Integer resultado = (Integer) o;
                                    finalizarActividad(
                                            ACCION_MODIFICAR_PUJA,
                                            Boolean.valueOf(resultado != 0),
                                            R.string.err_mes_update_push_failed
                                    );

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

                                            detalle.tipo_venta = (TipoVenta) spTipoVenta.getSelectedItem();
                                            detalle.cantidad = Integer.valueOf(txtCantidadProductos.getText().toString());
                                            detalle.producto = ((Producto) spProductos.getSelectedItem());

                                            auctionViewModel.removerPujaProductor(
                                                    detalle,
                                                    new SimpleAction() {
                                                        @Override
                                                        public void doAction(Object o) {

                                                            Integer resultado = (Integer) o;
                                                            finalizarActividad(
                                                                    ACCION_REMOVER_PUJA,
                                                                    resultado != 0,
                                                                    R.string.err_mes_remove_push_failed
                                                            );

                                                        }
                                                    }
                                            );

                                        }
                                    }, null);

                                ynd.generate().show();

                        }
                    });


                }

            } catch(Exception ex){
                Log.e("PUSH_PRODUCT_ACT",String.format("No se pudo visualizar puja!: %s",ex.toString()));
                finalizarActividad(ACCION_VISUALIZAR_PUJA,false,R.string.err_code_str_cant_recover_data);
            }



        }


    }

    public void finalizarActividad(int accion, boolean exito, int idCodigoError){

        Intent i = new Intent();
        i.putExtra(FeriaVirtualConstants.CODIGO_ACCION,accion);
        i.putExtra(RESULTADO_ACCION,exito);
        i.putExtra(FeriaVirtualConstants.ID_CODIGO_ERROR,idCodigoError);
        setResult(RESULT_OK,i);
        finish();
    }

}