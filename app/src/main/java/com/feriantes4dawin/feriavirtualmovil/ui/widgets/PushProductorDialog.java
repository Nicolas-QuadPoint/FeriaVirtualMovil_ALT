package com.feriantes4dawin.feriavirtualmovil.ui.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.ui.auction.PushProductorCustomAdapter;
import com.feriantes4dawin.feriavirtualmovil.util.SimpleAction;

import org.jetbrains.annotations.NotNull;

/**
 * PushProductorDialog
 * 
 * Implementación de SimpleDialog que representa 
 * un diálogo de puja de productor. 
 * Este diálogo muestra el detalle de la puja que el 
 * usuario va a mostrar, con una lista con los productos 
 * a seleccionar, descartables y editables. 
 */
public final class PushProductorDialog extends SimpleDialog {

    private boolean modoEdicion;
    private int idMensajeTitulo;

    public PushProductorDialog(AppCompatActivity act, SimpleAction positiveResponseFunc, SimpleAction negativeResponseFunc) {
        super(act,positiveResponseFunc,negativeResponseFunc);
        this.idMensajeTitulo = R.string.ppbd_push;
        this.modoEdicion = false;
    }

    public PushProductorDialog(AppCompatActivity act,boolean modoEdicion, SimpleAction positiveResponseFunc, SimpleAction negativeResponseFunc) {
        super(act,positiveResponseFunc,negativeResponseFunc);
        this.modoEdicion = modoEdicion;
        this.idMensajeTitulo = R.string.push_update;
    }

    @Override
    protected View prepareView() {

        ViewGroup vg = act.findViewById(android.R.id.content);
        this.v = LayoutInflater.from(act).inflate(R.layout.dialog_push_productor, vg, false);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.dppb_rvListaProductos);
        PushProductorCustomAdapter rvAdapter = new PushProductorCustomAdapter();
        dlg.setTitle(idMensajeTitulo);

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

                PushProductorCustomAdapter.PushProductorViewHolder vh = (PushProductorCustomAdapter.PushProductorViewHolder)viewHolder;
                vh.limpiarCampos();
                rvAdapter.notifyItemChanged(vh.getAbsoluteAdapterPosition());

            }
        };

        new ItemTouchHelper(limpiarEnSweep).attachToRecyclerView(rv);
        rv.setAdapter(rvAdapter);
        rv.setLayoutManager(new LinearLayoutManager(act));


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
                    }

            );

            //Para respuesta negativa
            dlg.setButton(AlertDialog.BUTTON_NEGATIVE,act.getString(R.string.action_cancel),

                    (dialogo,idBoton) -> {
                        if(negativeResponseFunc != null){
                            negativeResponseFunc.doAction(v);
                        }
                    }

            );



        }

    }

}
