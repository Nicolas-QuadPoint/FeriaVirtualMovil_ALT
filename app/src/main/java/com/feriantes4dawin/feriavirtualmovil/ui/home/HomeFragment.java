package com.feriantes4dawin.feriavirtualmovil.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualComponent;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private FeriaVirtualComponent feriaVirtualComponent;

    @Inject
    public Gson convertidorJSON;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        try{

            SharedPreferences sp = requireContext().getSharedPreferences(
                    FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,
                    Context.MODE_PRIVATE
            );

            String usuarioString = sp.getString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR,"");
            Usuario u = convertidorJSON.fromJson(usuarioString,Usuario.class);
            TextView lblNombreUsuario = root.findViewById(R.id.hf_lblNombreUsuario);
            TextView lblSubastasSinConfirmar = root.findViewById(R.id.hf_lblSubastasSinConfirmar);
            TextView lblGananciasMes = root.findViewById(R.id.hf_lblGananciasMes);

            lblNombreUsuario.setText(u.getNombreCompleto());
            lblGananciasMes.setText(R.string.err_mes_not_avalaible);
            lblSubastasSinConfirmar.setText(R.string.err_mes_not_avalaible);

        } catch(Exception ex) {

            Snackbar.make(
                    getActivity().findViewById(android.R.id.content),
                    getString(R.string.err_msg_generic),
                    Snackbar.LENGTH_SHORT
            ).show();
            Log.e("HOME_FRAGMENT",ex.toString());

        }

        return root;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {

        super.onAttach(context);

        // Creation of the login graph using the application graph
        feriaVirtualComponent = ((FeriaVirtualApplication) getActivity().getApplicationContext())
                .getFeriaVirtualComponent();

        // Make Dagger instantiate @Inject fields in LoginActivity
        feriaVirtualComponent.injectIntoHomeFragment(this);

    }
}