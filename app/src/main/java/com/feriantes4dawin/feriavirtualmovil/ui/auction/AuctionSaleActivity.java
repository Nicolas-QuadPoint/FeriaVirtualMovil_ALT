package com.feriantes4dawin.feriavirtualmovil.ui.auction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualComponent;
import com.feriantes4dawin.feriavirtualmovil.data.models.Rol;
import com.feriantes4dawin.feriavirtualmovil.data.models.Subasta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.repos.SubastaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepository;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.ui.login.LoginViewModel;
import com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants;
import com.feriantes4dawin.feriavirtualmovil.ui.util.SimpleAction;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.PushProductorDialog;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.PushTransportistaDialog;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.SimpleDialog;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.YesNoDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import javax.inject.Inject;

public class AuctionSaleActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    private FeriaVirtualComponent feriaVirtualComponent;

    private AuctionViewModelFactory auctionViewModelFactory;
    private AuctionViewModel auctionViewModel;

    @Inject
    public Gson convertidorJSON;

    @Inject
    public VentaRepositoryImpl ventaRepository;

    @Inject
    public UsuarioRepositoryImpl usuarioRepository;

    @Inject
    public SubastaRepositoryImpl subastaRepository;


    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auction_sale);

        // Creation of the graph using the application graph
        feriaVirtualComponent = ((FeriaVirtualApplication) getApplicationContext())
                .getFeriaVirtualComponent();

        // Make Dagger instantiate @Inject fields in AuctionSaleActivity
        feriaVirtualComponent.injectIntoAuctionSaleActivity(this);

        //Creando mi super duper factory y viewmodel!
        this.auctionViewModelFactory = new AuctionViewModelFactory(
            (FeriaVirtualApplication) getApplication(),
            ventaRepository,
            usuarioRepository,
            subastaRepository
        );
        this.auctionViewModel = new ViewModelProvider(this,auctionViewModelFactory)
                .get(AuctionViewModel.class);


        ExtendedFloatingActionButton aas_btnPujar = (ExtendedFloatingActionButton) findViewById(R.id.aas_btnPujar);
        RecyclerView rv = (RecyclerView) findViewById(R.id.aas_rvListaPujas);
        SwipeRefreshLayout miSwiper = findViewById(R.id.aas_swipeSaleDetail);
        View vistaMiPuja = findViewById(R.id.inc_pujaUsuario);
        PushListCustomAdapter rvAdapter = new PushListCustomAdapter();
        rv.setAdapter(rvAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sp = getSharedPreferences(
                FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,
                Context.MODE_PRIVATE
        );

        try {

            String stringUsuario = sp.getString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR, "");
            usuario = convertidorJSON.fromJson(stringUsuario, Usuario.class);

        } catch (Exception ex) {

            Snackbar.make(this, findViewById(android.R.id.content), getString(R.string.err_msg_generic), Snackbar.LENGTH_SHORT).show();
            Log.e("AUCTION_SALE_ACTIVITY", ex.toString());
            usuario = null;
        }

        if (usuario != null && usuario.rol != null) {

            aas_btnPujar.setOnClickListener(v -> {

                irAPujar(usuario, false);

            });

        } else {

            aas_btnPujar.setEnabled(false);

        }

        miSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                PushListCustomAdapter nrvAdapter = new PushListCustomAdapter();

                rv.setAdapter(nrvAdapter);
                rv.setLayoutManager(new LinearLayoutManager(AuctionSaleActivity.this));

                miSwiper.setRefreshing(false);
            }

        });

        vistaMiPuja.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               irAPujar(usuario,true);

            }

        });

    }

    public void irAPujar(Usuario usuario,boolean modoEdicion){

        Class<? extends AppCompatActivity> actividadDestino = null;
        Intent irHaciaActividad = null;

        if(Rol.PRODUCTOR.equals(usuario.rol)){

            //La actividad será para roles!
            actividadDestino = PushProductorActivity.class;

        } else if (Rol.TRANSPORTISTA.equals(usuario.rol)){

            //La actividad será para transportistas
            actividadDestino = PushTransportistaActivity.class;

        } else {

            Snackbar.make(this,findViewById(android.R.id.content),getString(R.string.err_msg_generic),Snackbar.LENGTH_SHORT).show();

        }

        if(actividadDestino != null){

            irHaciaActividad = new Intent(AuctionSaleActivity.this,actividadDestino);
            irHaciaActividad.putExtra("modo_edicion",modoEdicion);
            irHaciaActividad.putExtra("usuario",convertidorJSON.toJson(usuario));

            startActivity(irHaciaActividad);

        }

    }

    public void mostrarDialogoPuja(Usuario u,boolean modoEdicion,Object objInfoPuja){

    }

}