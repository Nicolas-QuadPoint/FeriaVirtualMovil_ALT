package com.feriantes4dawin.feriavirtualmovil.ui.sales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualComponent;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetalleVenta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Rol;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.ui.auction.PushProductorActivity;
import com.feriantes4dawin.feriavirtualmovil.ui.auction.PushTransportistaActivity;
import com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.YesNoDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * SaleDetailActivity 
 * 
 * Flujo de actividad que contiene el detalle de una venta, 
 * permitiendo (si está disponible) participar de dicha venta a 
 * través de subastas. 
 */
public class SaleDetailActivity extends AppCompatActivity {

    /**
     * Objeto que nos entrega el puente de datos. 
     */
    private SaleDetailViewModel saleDetailViewModel;
    private SaleDetailViewModelFactory saleDetailViewModelFactory;

    /**
     * Objeto que nos entrega las dependencias con la anotación 
     * Inject sobre ellas. 
     */
    private FeriaVirtualComponent feriaVirtualComponent;

    /**
     * ID de la venta entregada a esta actividad al momento de 
     * su creación. 
     */
    private Integer id_venta;

    /**
     * Referencia al usuario actualmente autenticado
     */
    private Usuario usuario;

    /**
     * Referencia a la venta seleccionada en la lista de ventas.
     */
    private Venta venta;

    private boolean estaCargando;

    /**
     * Dependencia que nos ofrece la fuente de datos que 
     * usará saleDetailViewModel. 
     */
    @Inject
    public VentaRepositoryImpl ventaRepository;


    @Inject
    public SubastaRepositoryImpl subastaRepository;

    @Inject
    public Gson convertidorJSON;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sale_detail);

        ExtendedFloatingActionButton btnPujar = (ExtendedFloatingActionButton)findViewById(R.id.asd_btnPujar);
        SwipeRefreshLayout miSwiper = (SwipeRefreshLayout)findViewById(R.id.asd_swipeSaleDetail);
        View pantallaCarga = findViewById(R.id.asd_llloading);
        Intent datosEntradaActividad = getIntent();
        SharedPreferences sp = getSharedPreferences(
                FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);

        //Se obtiene la instacia de FeriaVirtualComponent de la aplicación.
        feriaVirtualComponent = ((FeriaVirtualApplication)getApplicationContext())
                .getFeriaVirtualComponent();

        //Con esto se inyectan las dependencias de esta clase!
        feriaVirtualComponent.injectIntoSaleDetailActivity(this);

        //Instanciamos el factory!
        this.saleDetailViewModelFactory = new SaleDetailViewModelFactory(
                ventaRepository,
                (FeriaVirtualApplication) getApplicationContext()
        );

        //Creamos nuestro viewmodel, utilizando nuestra factory
        this.saleDetailViewModel = new ViewModelProvider(this,saleDetailViewModelFactory).
                get(SaleDetailViewModel.class);

        //Obtenemos el id de venta seleccionada en el fragmento CurrentSalesFragment o MyProcessesFragment
        this.id_venta = sp.getInt(FeriaVirtualConstants.SP_VENTA_ID,0);
        this.venta = convertidorJSON.fromJson(sp.getString(FeriaVirtualConstants.SP_VENTA_OBJ_STR,""), Venta.class);
        this.usuario = convertidorJSON.fromJson( sp.getString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR,""), Usuario.class );

        if(datosEntradaActividad.getBooleanExtra(FeriaVirtualConstants.MODO_SOLO_LECTURA,false)){

            View vDescripcionListaProd = findViewById(R.id.dppb_vDescripcionListaProd);
            vDescripcionListaProd.setVisibility(View.INVISIBLE);
            btnPujar.setVisibility(View.GONE);

        }


        actualizarDatosVenta();

        miSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pantallaCarga.setVisibility(View.VISIBLE);
                saleDetailViewModel.getDatosVenta(id_venta);
            }
        });

        /** 
         * Con esto se redirige el flujo a la actividad AuctionSaleActivity, que sería
         * la sección de subastas.
         */
        btnPujar.setOnClickListener( v -> {

            try{

                Class<? extends AppCompatActivity> actividadObjetivo = null;

                if(Rol.PRODUCTOR.equalsValues(usuario.rol)) {

                    actividadObjetivo = PushProductorActivity.class;

                } else if(Rol.TRANSPORTISTA.equalsValues(usuario.rol)){

                    actividadObjetivo = PushTransportistaActivity.class;

                } else {

                    Snackbar.make(this,findViewById(R.id.asd_contenedor),getString(R.string.err_msg_generic),Snackbar.LENGTH_LONG).show();
                    return;

                }

                Intent i = new Intent(SaleDetailActivity.this, actividadObjetivo);
                i.putExtra("id_venta", this.id_venta);
                startActivityForResult(i,1,null);


            }catch(Exception ex){

                Log.e("SALE_DETAIL_ACT",String.format("Un error al intentar pujar!: %s",ex.toString()));
                Snackbar.make(this,findViewById(R.id.asd_contenedor),getString(R.string.err_msg_generic),Snackbar.LENGTH_LONG).show();

            }

        });



    }

    public void actualizarDatosVenta(){

        //Observamos el livedata del objeto y veamos que pasa!
        saleDetailViewModel.getDatosVenta(id_venta).observe(this,
                new Observer<DetalleVenta>() {
                    @Override
                    public void onChanged(DetalleVenta detalleVenta) {
                        rellenarDatosVenta(detalleVenta);
                    }
                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            Log.e("SALE_DETAIL_ACT","paso por onActivityResult!");

            String mensaje;

            if(data.hasExtra("producto_agregado")){

                if(data.getBooleanExtra("producto_agregado",false)){
                    mensaje = getString(R.string.product_added);
                    saleDetailViewModel.getDatosVenta(id_venta);
                } else {
                    mensaje = String.format(
                            getString(R.string.err_mes_operation_failed_due),
                            getString( data.getIntExtra("codigo_error", R.string.err_code_str_generic) )
                    );
                }

                Snackbar.make(findViewById(android.R.id.content),mensaje,Snackbar.LENGTH_LONG).show();

            } else {

            }



        }

    }

    @Override
    public void onBackPressed() {
        //No domino la navegación entre actividades aún, mejor finalicemos este.
        if(!estaCargando){
            finish();
        }
    }

    @Override
    public void supportNavigateUpTo(@NonNull @NotNull Intent upIntent) {
        upIntent.putExtra("fragment",FeriaVirtualConstants.FRAGMENTO_LISTA_PETICIONES_VENTA);
        super.supportNavigateUpTo(upIntent);
        Log.i("SALE_DETAIL_ACTIVITY","Deberia ir a " + FeriaVirtualConstants.FRAGMENTO_LISTA_PETICIONES_VENTA);
    }

    /**
     * Método llamado desde el puente de datos para actualizar los 
     * datos obtenidos desde la fuente, exitoso o no. 
     * 
     * @param detalleVenta Objeto Venta del cual extraer los datos. Un
     * objeto null indica que no hay datos disponibles. 
     */
    private void rellenarDatosVenta(DetalleVenta detalleVenta){

        SwipeRefreshLayout miSwiper = (SwipeRefreshLayout)findViewById(R.id.asd_swipeSaleDetail);
        View pantallaCarga = findViewById(R.id.asd_llloading);

        TextView lblNombreEmpresa = findViewById(R.id.csi_lblCodigoVenta);
        TextView lblFechaInicioVenta = findViewById(R.id.csi_lblFechaInicioVenta);
        TextView lblFechaFinVenta = findViewById(R.id.csi_lblFechaFinVenta);
        TextView lblEstadoVenta = findViewById(R.id.csi_lblEstadoVenta);
        TextView lblComentariosVenta = findViewById(R.id.csi_lblComentariosVenta);

        RecyclerView rvProductosSeleccionados = findViewById(R.id.dppb_rvListaProductos);

        lblNombreEmpresa.setText( String.format("%s N° %d",getString(R.string.title_sale_process), this.venta.id_venta) );
        lblFechaInicioVenta.setText( this.venta.fecha_inicio_venta );
        lblFechaFinVenta.setText( this.venta.fecha_fin_venta );
        lblComentariosVenta.setText( this.venta.comentarios_venta );
        lblEstadoVenta.setText( this.venta.estado_venta.descripcion );


        if(detalleVenta != null && detalleVenta.productos != null){

            //Creo un nuevo objeto adapter, pasandole los datos!
            rvProductosSeleccionados.setAdapter(new ListItemDetailProductCustomAdapter(detalleVenta.productos));
            rvProductosSeleccionados.setLayoutManager( new LinearLayoutManager(this));

        }

        if(miSwiper.isRefreshing()){
            pantallaCarga.setVisibility(View.GONE);
            miSwiper.setRefreshing(false);
        }

    }

}