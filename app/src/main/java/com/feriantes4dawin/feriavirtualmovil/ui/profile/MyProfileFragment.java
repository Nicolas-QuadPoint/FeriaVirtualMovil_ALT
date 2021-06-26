package com.feriantes4dawin.feriavirtualmovil.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualComponent;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.repos.UsuarioRepositoryImpl;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * MyProfileFragment 
 * 
 * Fragmento que corresponde a la sección 'perfil' del 
 * usuario, donde sus datos personales son visibles, y 
 * permite la posibilidad de cambiar algunas de ellas, 
 * como dirección, número de teléfono y contraseña. 
 * 
 */
public class MyProfileFragment extends Fragment {


    /**
     * Intermediario de datos entre la fuente y esta clase. 
     */
    private MyProfileViewModel myProfileViewModel;

    /**
     * Creador de instancias MyProfileViewModel. 
     */
    private MyProfileViewModelFactory myProfileViewModelFactory;


    /**
     * Instancia que mantiene las dependencias que 
     * esta clase necesita. 
     */
    private FeriaVirtualComponent feriaVirtualComponent;

    /**
     * Dependencia correspondiente al origen de datos 
     * para usuarios. 
     */
    @Inject
    public UsuarioRepositoryImpl usuarioRepository;

    /**
     * Dependencia correspondiente al objeto convertidor 
     * JSON. 
     */
    @Inject
    public Gson convertidorJSON;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Obtenermos la vista con todos los controles
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);

        //Retornamos la vista para que sea dibujada
        return root;
    }

    @Override
    public void onViewCreated(View view ,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnImagen = (ImageButton) view.findViewById(R.id.fmp_btnFotoPerfilUsuario);
        SwipeRefreshLayout miSwiper = (SwipeRefreshLayout)view.findViewById(R.id.fmp_swipeFMP);

        miSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                View pantallaCarga = getActivity().findViewById(R.id.asd_llloading);
                pantallaCarga.setVisibility(View.VISIBLE);

                myProfileViewModel.getDatosUsuario();

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        
        super.onAttach(context);

        // Se obtiene la instancia de mantenedor de dependencias 
        feriaVirtualComponent = ((FeriaVirtualApplication) getActivity().getApplicationContext())
                .getFeriaVirtualComponent();

        // Inyectamos nuestras dependencias en esta clase 
        feriaVirtualComponent.injectIntoMyProfileFragment(this);

        //Creamos nuestro viewmodel
        this.myProfileViewModelFactory = new MyProfileViewModelFactory(
                usuarioRepository,
                convertidorJSON,
                (FeriaVirtualApplication) requireActivity().getApplicationContext()
        );
        
        this.myProfileViewModel = new ViewModelProvider(this,myProfileViewModelFactory).
                get(MyProfileViewModel.class);

        //Observamos el livedata del objeto y veamos que pasa!
        myProfileViewModel.getDatosUsuario().observe(this,
            new Observer<Usuario>() {
                @Override
                public void onChanged(Usuario usuario) {

                    actualizarDatosVista(usuario);

                }
            }
        );
    }

    /**
     * Método que es llamado cuando hay información de usuario en el 
     * puente de datos. Dependiendo de su valor, se actualizan los 
     * datos en los controles de vista, o se lanza un error. 
     * 
     * @param usuario Objeto del cual hay datos nuevos, o null, 
     * indicando que hubo un error al intentar obtener dichos 
     * datos. 
     */
    private void actualizarDatosVista(Usuario usuario){

        FragmentActivity fa = getActivity();

        View pantallaCarga = fa.findViewById(R.id.asd_llloading);
        SwipeRefreshLayout miSwiper = fa.findViewById(R.id.fmp_swipeFMP);

        if(usuario != null) {

            TextView lblNombreUsuario = fa.findViewById(R.id.mpf_lblNombreUsuario);
            TextView lblEmail = fa.findViewById(R.id.mpf_lblEmail);
            TextView lblRolUsuario = fa.findViewById(R.id.mpf_lblRolUsuario);

            lblNombreUsuario.setText(
                usuario.getNombreCompleto()
            );

            lblEmail.setText( usuario.email );

            lblRolUsuario.setText( usuario.rol.descripcion );

        } else {

            Snackbar.make(miSwiper,R.string.err_msg_error_getting_user_info,Snackbar.LENGTH_LONG).show();

        }

        //Quitamos la pantalla de carga ya que todo terminó
        pantallaCarga.setVisibility(View.GONE);

        //Y lo mismo para el refresco. 
        if(miSwiper.isRefreshing()){
            miSwiper.setRefreshing(false);
        }
    }

    public void btnFotoPerfilUsuarioClick(View v){

        //Toast.makeText(this.getActivity(),"Deberia cambiar imagen ¿No?",Toast.LENGTH_SHORT).show();
        //UtilityFunctions.getImageFromGallery( ( (AppCompatActivity) this.getActivity() ));

    }

}