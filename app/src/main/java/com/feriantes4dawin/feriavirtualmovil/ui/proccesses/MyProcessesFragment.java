package com.feriantes4dawin.feriavirtualmovil.ui.proccesses;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualComponent;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.VentasSimples;
import com.feriantes4dawin.feriavirtualmovil.data.repos.VentaRepositoryImpl;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * MyProccessesFragment 
 * 
 * Fragmento que representa la sección 'Mis procesos de venta', 
 * que lista, valga la redundancia, los procesos de venta que 
 * el usuario autenticado haya participado, con el fin de obtener 
 * información.
 * 
 */
public class MyProcessesFragment extends Fragment{

    /**
     * Creador de instancias MyProcessesViewModel
     */
    private MyProcessesViewModelFactory myProcessesViewModelFactory;
    
    /**
     * Instancia de MyProcessesViewModel para obtener y 
     * enviar datos hacia la fuente. 
     */
    private MyProcessesViewModel myProcessesViewModel;

    /**
     * Objeto necesario para obtener las dependencias de 
     * esta clase. 
     */
    private FeriaVirtualComponent feriaVirtualComponent;

    /**
     * Dependencia correspondiente a la fuente de datos para 
     * ventas. 
     */
    @Inject
    public VentaRepositoryImpl ventaRepository;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

        View root = inflater.inflate(R.layout.fragment_my_processes, container, false);
        SwipeRefreshLayout miSwiper = root.findViewById(R.id.fmproc_swipeMyProcesses);

        miSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                myProcessesViewModel.getDatosVenta();

            }
        });

        return root;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {

        super.onAttach(context);

        // Se obtiene la referencia al contenedor de dependencias. 
        feriaVirtualComponent = ((FeriaVirtualApplication) getActivity().getApplicationContext())
                .getFeriaVirtualComponent();

        // Con esto inyectamos nuestras dependencias de clase. 
        feriaVirtualComponent.injectIntoMyProcessesFragment(this);

        //Creamos nuestro viewmodel
        this.myProcessesViewModelFactory = new MyProcessesViewModelFactory(
                ventaRepository,
                (FeriaVirtualApplication) requireActivity().getApplicationContext()
        );

        this.myProcessesViewModel = new ViewModelProvider(this,myProcessesViewModelFactory).
                get(MyProcessesViewModel.class);

        //Observamos el livedata del objeto y veamos que pasa!
        myProcessesViewModel.getDatosVenta().observe(this,
                new Observer<VentasSimples>() {
                    @Override
                    public void onChanged(VentasSimples ventasSimples) {

                        actualizarDatosVista(ventasSimples);

                    }
                }
        );


    }

    /**
     * Actualiza los datos de la lista de procesos de venta, 
     * tomando como fuente el objeto entregado por el puente de 
     * datos. 
     * @param ventasSimples Objeto VentasSimples con los datos de 
     * ventas pasadas, o null si no hay datos disponibles. 
     */
    public void actualizarDatosVista(VentasSimples ventasSimples){

        SwipeRefreshLayout miSwiper = requireView().findViewById(R.id.fmproc_swipeMyProcesses);
        View llPlaceholderEmptyList = requireView().findViewById(R.id.myprocf_llPlaceholderEmptyList);
        View listaElementos = requireView().findViewById(R.id.myprocf_listaElementos);
        RecyclerView rvMyProcesses = requireView().findViewById(R.id.rvMyProcesses);

        if(ventasSimples != null){

            listaElementos.setVisibility(View.VISIBLE);
            llPlaceholderEmptyList.setVisibility(View.GONE);

        } else {

            //NO hay datos disponibles. Mejor mostramos el placeholder y escondemos 
            //la lista.
            listaElementos.setVisibility(View.GONE);
            llPlaceholderEmptyList.setVisibility(View.VISIBLE);

        }

        //Quitamos el refresco si sigue activo 
        if(miSwiper.isRefreshing()){
            miSwiper.setRefreshing(false);
        }
    }
}