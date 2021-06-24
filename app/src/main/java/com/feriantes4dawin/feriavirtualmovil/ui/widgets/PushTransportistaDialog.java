package com.feriantes4dawin.feriavirtualmovil.ui.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaTransportista;
import com.feriantes4dawin.feriavirtualmovil.ui.util.SimpleAction;

/**
 * PushTransportistaDialog 
 * 
 * Una implementación de SimpleDialog que contiene la lógica 
 * necesaria para mostrar un diálogo de puja de subasta, para 
 * usuarios transportistas, que dista de la puja para usuarios 
 * productores, siendo este primero más simple. 
 */
public class PushTransportistaDialog extends SimpleDialog {

    private boolean modoEdicion;
    private int idMensajeTitulo;
    private DetallePujaSubastaTransportista objInfoPuja;

    public PushTransportistaDialog(AppCompatActivity act, SimpleAction positiveResponseFunc, SimpleAction negativeResponseFunc) {
        super(act, positiveResponseFunc, negativeResponseFunc);
        this.modoEdicion = false;
        this.objInfoPuja = null;
        this.idMensajeTitulo = R.string.ppbd_push;
    }

    public PushTransportistaDialog(AppCompatActivity act,
                                   boolean modoEdicion,
                                   DetallePujaSubastaTransportista objInfoPuja,
                                   SimpleAction positiveResponseFunc, SimpleAction negativeResponseFunc) {
        super(act, positiveResponseFunc, negativeResponseFunc);
        this.modoEdicion = modoEdicion;
        this.objInfoPuja = objInfoPuja;
        this.idMensajeTitulo = R.string.push_update;
    }

    @Override
    protected View prepareView() {

        ViewGroup vg = act.findViewById(android.R.id.content);
        this.v = LayoutInflater.from(act).inflate(R.layout.dialog_push_transportista, vg, false);
        dlg.setTitle(idMensajeTitulo);

        return this.v;

    }

    @Override
    protected void prepareResponses() {

        if(modoEdicion){

            //Para respuesta afirmativa (actualizar)
            dlg.setButton(AlertDialog.BUTTON_POSITIVE,act.getString(R.string.action_accept),

                    (dialogo,idBoton) -> {
                        if(positiveResponseFunc != null){
                            positiveResponseFunc.doAction(v);
                        }
                    }

            );

            //Para respuesta negativa
            dlg.setButton(AlertDialog.BUTTON_NEGATIVE,act.getString(R.string.action_delete),

                    (dialogo,idBoton) -> {
                        if(negativeResponseFunc != null){
                            negativeResponseFunc.doAction(v);
                        }
                    }

            );

            //Para cancelar
            dlg.setButton(AlertDialog.BUTTON_NEUTRAL,act.getString(R.string.action_cancel),

                    (dialogo,idBoton) -> {

                        dialogo.cancel();

                    }

            );


        } else {


            //Para respuesta afirmativa (actualizar)
            dlg.setButton(AlertDialog.BUTTON_POSITIVE,act.getString(R.string.action_accept),

                    (dialogo,idBoton) -> {
                        if(positiveResponseFunc != null){
                            positiveResponseFunc.doAction(v);
                        }
                        dialogo.cancel();
                    }

            );

            //Para respuesta negativa
            dlg.setButton(AlertDialog.BUTTON_NEGATIVE,act.getString(R.string.action_cancel),

                    (dialogo,idBoton) -> {
                        if(negativeResponseFunc != null){
                            negativeResponseFunc.doAction(v);
                        }
                        dialogo.cancel();
                    }

            );



        }

    }
}
