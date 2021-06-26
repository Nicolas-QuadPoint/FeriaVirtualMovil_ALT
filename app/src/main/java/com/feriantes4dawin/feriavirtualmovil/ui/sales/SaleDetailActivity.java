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
import com.feriantes4dawin.feriavirtualmovil.data.models.Rol;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.models.VentaSimple;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.ui.auction.AuctionSaleActivity;
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
    private VentaSimple ventaSimple;

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
        Button btnCancelar = findViewById(R.id.asd_btnCancelar);
        Button btnConfirmar = findViewById(R.id.asd_btnConfirmar);
        SwipeRefreshLayout miSwiper = (SwipeRefreshLayout)findViewById(R.id.asd_swipeSaleDetail);
        View pantallaCarga = findViewById(R.id.asd_llloading);
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
        this.ventaSimple = convertidorJSON.fromJson(sp.getString(FeriaVirtualConstants.SP_VENTA_OBJ_STR,""),VentaSimple.class);
        this.usuario = convertidorJSON.fromJson( sp.getString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR,""), Usuario.class );


        //Observamos el livedata del objeto y veamos que pasa!
        saleDetailViewModel.getDatosVenta(id_venta).observe(this,
                new Observer<Venta>() {
                    @Override
                    public void onChanged(Venta venta) {
                        rellenarDatosVenta(venta);

                    }
                }
        );

        miSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pantallaCarga.setVisibility(View.VISIBLE);
                saleDetailViewModel.getDatosVenta(id_venta);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YesNoDialog ynd = new YesNoDialog(SaleDetailActivity.this,
                        getString(R.string.action_confirm),
                        getString(R.string.confirm_push_products),
                        null,
                        null);

                ynd.generate().show();


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
                startActivity(i);


            }catch(Exception ex){

                Log.e("SALE_DETAIL_ACT",String.format("Un error al intentar pujar!: %s",ex.toString()));
                Snackbar.make(this,findViewById(R.id.asd_contenedor),getString(R.string.err_msg_generic),Snackbar.LENGTH_LONG).show();

            }

        });

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
     * @param venta Objeto Venta del cual extraer los datos. Un 
     * objeto null indica que no hay datos disponibles. 
     */
    private void rellenarDatosVenta(Venta venta){

        SwipeRefreshLayout miSwiper = (SwipeRefreshLayout)findViewById(R.id.asd_swipeSaleDetail);
        View pantallaCarga = findViewById(R.id.asd_llloading);

        TextView lblNombreEmpresa = findViewById(R.id.csi_lblCodigoVenta);
        TextView lblFechaInicioVenta = findViewById(R.id.csi_lblFechaInicioVenta);
        TextView lblFechaFinVenta = findViewById(R.id.csi_lblFechaFinVenta);
        TextView lblEstadoVenta = findViewById(R.id.csi_lblEstadoVenta);
        TextView lblComentariosVenta = findViewById(R.id.csi_lblComentariosVenta);

        RecyclerView rvProductosSeleccionados = findViewById(R.id.asd_rvListaProductosSolicitados);

        if(venta != null){

            lblNombreEmpresa.setText( venta.usuario_autor.nombre );
            lblFechaInicioVenta.setText( venta.fecha_inicio_venta );
            lblComentariosVenta.setText( venta.comentarios_venta );
            lblEstadoVenta.setText( venta.estado_venta.descripcion );

            //Creo un nuevo objeto adapter, pasandole los datos!
            rvProductosSeleccionados.setAdapter(new ListItemDetailProductCustomAdapter(venta.productos_venta));
            rvProductosSeleccionados.setLayoutManager( new LinearLayoutManager(this));

        } else {

            lblNombreEmpresa.setText( String.format("%s N° %d",getString(R.string.title_sale_process),ventaSimple.id_venta) );
            lblFechaInicioVenta.setText( ventaSimple.fecha_inicio_venta );
            lblFechaFinVenta.setText( ventaSimple.fecha_fin_venta );
            lblComentariosVenta.setText( ventaSimple.comentarios_venta );
            lblEstadoVenta.setText( ventaSimple.estado_venta.descripcion );

            Snackbar.make
                    (this,
                            findViewById(R.id.asd_contenedor),
                            getString(R.string.err_msg_generic),
                            Snackbar.LENGTH_LONG).show();

        }

        if(miSwiper.isRefreshing()){
            pantallaCarga.setVisibility(View.GONE);
            miSwiper.setRefreshing(false);
        }

    }

}