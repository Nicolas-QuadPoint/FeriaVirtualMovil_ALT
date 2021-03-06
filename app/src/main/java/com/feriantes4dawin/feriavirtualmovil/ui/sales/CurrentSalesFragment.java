package com.feriantes4dawin.feriavirtualmovil.ui.sales;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import com.feriantes4dawin.feriavirtualmovil.data.models.Ventas;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepositoryImpl;
import com.feriantes4dawin.feriavirtualmovil.util.FeriaVirtualConstants;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * CurrentSalesFragment 
 * 
 * Fragmento que representa la sección de 'procesos de venta', 
 * que muestra, valga la redundancia, la lista de ventas a las 
 * cuales el usuario (Productor o Transportista) podrán ver 
 * información detallada de la misma, y la posiblidad de participar 
 * en alguna de ellas. 
 * 
 */
public class CurrentSalesFragment extends Fragment {

    /**
     * ViewModel para este fragmento, que actúa como interfaz para 
     * la capa de datos. 
     */
    private CurrentSalesViewModel currentSalesViewModel;

    /**
     * Creador de instancias de CurrentSalesViewModel. 
     */
    private CurrentSalesViewModelFactory currentSalesViewModelFactory;

    /**
     * Instancia usada para obtener las dependencias. 
     */
    private FeriaVirtualComponent feriaVirtualComponent;

    /**
     * Instancia del usuario autenticado
     */
    private Usuario usuario;

    /**
     * Dependencia utilizada para CurrentSalesViewModel. 
     */
    @Inject
    public VentaRepositoryImpl ventaRepository;

    @Inject
    public Gson convertidorJSON;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View root = inflater.inflate(R.layout.fragment_current_sales, container, false);
        SwipeRefreshLayout miSwiper = (SwipeRefreshLayout)root.findViewById(R.id.csf_swipeCurrentSales);

        miSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                //Reinicio la lista de ventas!
                currentSalesViewModel.getDatosVenta(usuario);

            }

        });

        //Activamos el refresco ahora, para que se sepa se que esta cargando algo!
        miSwiper.setRefreshing(true);

        return root;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {

        super.onAttach(context);

        FeriaVirtualApplication fva = ((FeriaVirtualApplication) getActivity().getApplicationContext());

        // Se obtiene el gestor de dependencias por medio de la aplicación!
        feriaVirtualComponent = ((FeriaVirtualApplication) getActivity().getApplicationContext())
                .getFeriaVirtualComponent();

        // Con esto las dependencias adquieren un valor.
        feriaVirtualComponent.injectIntoCurrentSalesFragment(this);

        //Instanciamos el factory!
        this.currentSalesViewModelFactory =
            new CurrentSalesViewModelFactory(
                ventaRepository,
                fva, convertidorJSON
            );

        //Creamos nuestro viewmodel
        this.currentSalesViewModel = new ViewModelProvider(this,currentSalesViewModelFactory).
                get(CurrentSalesViewModel.class);


        //Recuperamos el objeto de nuestro usuario
        SharedPreferences sp = fva.getSharedPreferences(FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);

        this.usuario = convertidorJSON.fromJson(sp.getString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR,""),Usuario.class);


        //Observamos el livedata del objeto y veamos que pasa!
        currentSalesViewModel.getDatosVenta(usuario).observe(this,
            new Observer<Ventas>() {
                @Override
                public void onChanged(Ventas ventasSimples) {

                    rellenarListaVentas(ventasSimples);

                }
            }
        );

    }

    /**
     * Método que se encarga de actualizar los componentes de vista con 
     * los datos entregados por parte del objeto CurrentSalesViewModel. 
     * 
     * @param ventasSimples Objeto Ventas obtenido del puente de
     * datos. Un valor null indica que no hay datos disponibles. 
     */
    private void rellenarListaVentas(Ventas ventasSimples){

        View vistaMaestra = requireView();
        RecyclerView rvListaVentasSimples = vistaMaestra.findViewById(R.id.asd_rvListaProductosSolicitados);
        SwipeRefreshLayout miSwiper = vistaMaestra.findViewById(R.id.csf_swipeCurrentSales);
        View llPlaceholderEmptyList = vistaMaestra.findViewById(R.id.csf_llPlaceholderEmptyList);
        boolean modoFinalLectura = false;

        try {

            SharedPreferences sp = requireContext().getSharedPreferences(
                    FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,Context.MODE_PRIVATE);
            Usuario u = convertidorJSON.fromJson(sp.getString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR,""),Usuario.class);

            if(ventasSimples != null){

                //Limpio la lista anterior
                rvListaVentasSimples.setAdapter(null);

                //Consulto de datos de usuario autenticado! (Si es transportista, solo es para lectura)
                modoFinalLectura = Rol.TRANSPORTISTA.equalsValues(u.rol);

                //Creo un nuevo objeto adapter, pasandole los datos!
                rvListaVentasSimples.setAdapter(
                        new SimpleSaleItemCustomAdapter(
                                (AppCompatActivity) requireActivity(),
                                ventasSimples,
                                convertidorJSON,
                                modoFinalLectura));

                rvListaVentasSimples.setLayoutManager( new LinearLayoutManager(requireContext()));

                llPlaceholderEmptyList.setVisibility(View.GONE);
                rvListaVentasSimples.setVisibility(View.VISIBLE);

            } else {

                //NO hay datos. Vamos a cambiar la vista de lista por el placeholder.
                llPlaceholderEmptyList.setVisibility(View.VISIBLE);
                rvListaVentasSimples.setVisibility(View.GONE);

            }



        }catch(Exception ex){

            Log.e("CURRENT_SALES_FRAG",String.format("No se pudo procesar la lista de ventas!!: %s",ex.toString()));

        } finally {

            //Desactivo el efecto de refresco si está activo
            if(miSwiper.isRefreshing()){
                miSwiper.setRefreshing(false);
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        //Observamos el livedata del objeto y veamos que pasa!
        Log.i("CURRENT_SALES_FRAG",String.format("Resumiendo aquí!: Usuario: %s",usuario));
        currentSalesViewModel.getDatosVenta(usuario);

    }
}