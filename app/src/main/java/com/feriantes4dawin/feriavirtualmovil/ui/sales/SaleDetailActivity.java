package com.feriantes4dawin.feriavirtualmovil.ui.sales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualComponent;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallesPujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.Rol;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.ui.auction.PushProductorActivity;
import com.feriantes4dawin.feriavirtualmovil.ui.auction.PushTransportistaActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import static com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants.*;

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

    /**
     * Flag para determinar el comportamiento de ciertos controles,
     * y evitar que el usuario modifique datos que no debería
     */
    private boolean modoSoloLectura;

    /**
     * Observador que se limita a observar cuando una puja es eliminada.
     */
    private ObservadorAlBorrarProducto observadorAlBorrarProducto;

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
        View pantallaCargaListaProd = findViewById(R.id.dppb_llloading);
        Intent datosEntradaActividad = getIntent();
        SharedPreferences sp = getSharedPreferences(
                FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);

        //Se obtiene la instacia de FeriaVirtualComponent de la aplicación.
        feriaVirtualComponent = ((FeriaVirtualApplication)getApplicationContext())
                .getFeriaVirtualComponent();

        //Con esto se inyectan las dependencias de esta clase!
        feriaVirtualComponent.injectIntoSaleDetailActivity(this);

        //Instanciamos el factory!
        this.saleDetailViewModelFactory = new SaleDetailViewModelFactory(
                ventaRepository,
                subastaRepository,
                (FeriaVirtualApplication) getApplicationContext()
        );

        //Creamos nuestro viewmodel, utilizando nuestra factory
        this.saleDetailViewModel = new ViewModelProvider(this,saleDetailViewModelFactory).
                get(SaleDetailViewModel.class);

        //Obtenemos el id de venta seleccionada en el fragmento CurrentSalesFragment o MyProcessesFragment
        this.id_venta = sp.getInt(SP_VENTA_ID,0);
        this.venta = convertidorJSON.fromJson(sp.getString(SP_VENTA_OBJ_STR,""), Venta.class);
        this.usuario = convertidorJSON.fromJson( sp.getString(SP_USUARIO_OBJ_STR,""), Usuario.class );
        this.modoSoloLectura = datosEntradaActividad.getBooleanExtra(MODO_SOLO_LECTURA,false);
        this.observadorAlBorrarProducto = new ObservadorAlBorrarProducto();


        /* Revisando si el modo de vista es de solo lectura */
        if(modoSoloLectura){

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
                saleDetailViewModel.getProductosVenta(id_venta,usuario.id_usuario.intValue());
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
                i.putExtra(CODIGO_ACCION,ACCION_AGREGAR_PUJA);
                startActivityForResult(i,1,null);


            }catch(Exception ex){

                Log.e("SALE_DETAIL_ACT",String.format("Un error al intentar pujar!: %s",ex.toString()));
                Snackbar.make(this,findViewById(R.id.asd_contenedor),getString(R.string.err_msg_generic),Snackbar.LENGTH_LONG).show();

            }

        });

        pantallaCarga.setVisibility(View.VISIBLE);
        pantallaCargaListaProd.setVisibility(View.VISIBLE);

    }

    public void actualizarDatosVenta(){

        View pantallaCargaListaProd = findViewById(R.id.dppb_llloading);
        View pantallaCargaGeneral = findViewById(R.id.asd_llloading);

        //Observamos el livedata del objeto y veamos que pasa!
        saleDetailViewModel.getDatosVenta(this.id_venta).observe(this,
                new Observer<Venta>() {
                    @Override
                    public void onChanged(Venta ventaRecuperada) {
                        rellenarDatosVenta(ventaRecuperada);
                    }
                }
        );

        saleDetailViewModel.getProductosVenta(this.id_venta,usuario.id_usuario.intValue()).observe(this,
            new Observer<DetallesPujaSubastaProductor>() {
                @Override
                public void onChanged(DetallesPujaSubastaProductor productosRecuperados) {
                    rellenarListaProductos(productosRecuperados);
                }
        });

        pantallaCargaGeneral.setVisibility(View.VISIBLE);
        pantallaCargaListaProd.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            Log.e("SALE_DETAIL_ACT","paso por onActivityResult!");

            String mensajeError = getString(data.getIntExtra(ID_CODIGO_ERROR,R.string.err_code_str_generic));
            boolean exito = data.getBooleanExtra(RESULTADO_ACCION,false);
            String mensajeExito = null;
            int codigoAccion = data.getIntExtra(CODIGO_ACCION,-1);

            switch(codigoAccion) {

                case ACCION_AGREGAR_PUJA: {
                    mensajeExito = getString(R.string.err_mes_add_push_ok);
                }
                break;
                case ACCION_MODIFICAR_PUJA: {
                    mensajeExito = getString(R.string.err_mes_update_push_ok);
                }
                break;
                case ACCION_REMOVER_PUJA: {
                    mensajeExito = getString(R.string.err_mes_remove_push_ok);
                }
                break;

            }

            if(codigoAccion == ACCION_VISUALIZAR_PUJA && exito == false){

                Snackbar.make(
                    findViewById(android.R.id.content),
                    String.format(getString(R.string.err_mes_operation_failed_due),mensajeError),
                    Snackbar.LENGTH_LONG
                ).show();


            } else {

                Snackbar.make(
                        findViewById(android.R.id.content),
                        exito? mensajeExito : String.format(
                                getString(R.string.err_mes_operation_failed_due),mensajeError),
                        Snackbar.LENGTH_LONG
                ).show();

            }

            if(exito){
                actualizarDatosVenta();
            }

        }

    }

    @Override
    public void onBackPressed() {
        //No domino la navegación entre actividades aún, mejor finalicemos este.
        finish();
    }

    @Override
    public void supportNavigateUpTo(@NonNull @NotNull Intent upIntent) {
        upIntent.putExtra("fragment",FRAGMENTO_LISTA_PETICIONES_VENTA);
        super.supportNavigateUpTo(upIntent);
        Log.i("SALE_DETAIL_ACTIVITY","Deberia ir a " + FRAGMENTO_LISTA_PETICIONES_VENTA);
    }

    /**
     * Método llamado desde el puente de datos para actualizar los 
     * datos obtenidos desde la fuente, exitoso o no. 
     * 
     * @param ventaRecuperada Objeto Venta del cual extraer los datos. Un
     * objeto null indica que no hay datos disponibles. 
     */
    private void rellenarDatosVenta(Venta ventaRecuperada){

        SwipeRefreshLayout miSwiper = (SwipeRefreshLayout)findViewById(R.id.asd_swipeSaleDetail);
        View pantallaCarga = findViewById(R.id.asd_llloading);

        TextView lblNombreEmpresa = findViewById(R.id.csi_lblCodigoVenta);
        TextView lblFechaInicioVenta = findViewById(R.id.csi_lblFechaInicioVenta);
        TextView lblFechaFinVenta = findViewById(R.id.csi_lblFechaFinVenta);
        TextView lblEstadoVenta = findViewById(R.id.csi_lblEstadoVenta);
        TextView lblComentariosVenta = findViewById(R.id.csi_lblComentariosVenta);

        if(ventaRecuperada != null){

            lblNombreEmpresa.setText( String.format("%s N° %d",getString(R.string.title_sale_process), ventaRecuperada.id_venta) );
            lblFechaInicioVenta.setText( ventaRecuperada.fecha_inicio_venta );
            lblFechaFinVenta.setText( ventaRecuperada.fecha_fin_venta );
            lblComentariosVenta.setText( ventaRecuperada.comentarios_venta );
            lblEstadoVenta.setText( ventaRecuperada.estado_venta.descripcion );

        } else {

            lblNombreEmpresa.setText( String.format("%s N° %d",getString(R.string.title_sale_process), this.venta.id_venta) );
            lblFechaInicioVenta.setText( this.venta.fecha_inicio_venta );
            lblFechaFinVenta.setText( this.venta.fecha_fin_venta );
            lblComentariosVenta.setText( this.venta.comentarios_venta );
            lblEstadoVenta.setText( this.venta.estado_venta.descripcion );

        }

        if(miSwiper.isRefreshing()){
            miSwiper.setRefreshing(false);
        }

        pantallaCarga.setVisibility(View.GONE);

    }

    private void rellenarListaProductos(DetallesPujaSubastaProductor productosRecuperados){

        View pantallaCargaListaProd = findViewById(R.id.dppb_llloading);
        View phListaVaciaProd = findViewById(R.id.dppb_phListaVaciaProd);
        RecyclerView rvListaProductos = findViewById(R.id.dppb_rvListaProductos);

        if(productosRecuperados != null && productosRecuperados.pujas != null &&
        productosRecuperados.pujas.size() > 0){

            rvListaProductos.setAdapter(
                new ListItemDetailProductCustomAdapter(
                    this,
                    productosRecuperados.pujas,
                    this.modoSoloLectura,
                    convertidorJSON
                )
            );
            rvListaProductos.setLayoutManager(new LinearLayoutManager(this));
            phListaVaciaProd.setVisibility(View.GONE);

        } else {

            phListaVaciaProd.setVisibility(View.VISIBLE);

        }

        pantallaCargaListaProd.setVisibility(View.GONE);


    }

    private void prepararListaProductos(RecyclerView rv){

        /**
         * Sacado de https://www.youtube.com/watch?v=M1XEqqo6Ktg
         */
        ItemTouchHelper.SimpleCallback limpiarEnSweep =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(@NonNull @NotNull RecyclerView recyclerView,
                                          @NonNull @NotNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull @NotNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {

                        ListItemDetailProductCustomAdapter.ListItemDetailProductViewHolder vh = (ListItemDetailProductCustomAdapter.ListItemDetailProductViewHolder)viewHolder;
                        //rv.getAdapter().notifyItemChanged(vh.getAbsoluteAdapterPosition());
                        saleDetailViewModel.borrarPuja(SaleDetailActivity.this.id_venta,usuario.id_usuario.intValue(),
                        vh.detalle).observe(SaleDetailActivity.this,observadorAlBorrarProducto);

                    }
                };

        new ItemTouchHelper(limpiarEnSweep).attachToRecyclerView(rv);

    }

    public class ObservadorAlBorrarProducto implements Observer<DetallesPujaSubastaProductor>{

        @Override
        public void onChanged(DetallesPujaSubastaProductor productos) {
            if(productos != null){

                //rellenarListaProductos(productos);
                Snackbar.make(
                    SaleDetailActivity.this.findViewById(android.R.id.content),
                    R.string.err_mes_remove_push_ok,
                    Snackbar.LENGTH_LONG)
                .show();

            } else {
                Snackbar.make(
                    SaleDetailActivity.this.findViewById(android.R.id.content),
                    R.string.err_mes_remove_push_failed,
                    Snackbar.LENGTH_LONG)
                .show();
            }

            /* Quito el observador, pues ya no es necesario */
            SaleDetailActivity.this
                    .saleDetailViewModel
                    .datosProductosVenta
                    .removeObserver(SaleDetailActivity.this.observadorAlBorrarProducto);
        }
    }

}