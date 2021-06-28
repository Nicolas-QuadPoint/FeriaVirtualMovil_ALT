package com.feriantes4dawin.feriavirtualmovil.ui.widgets;

import android.content.DialogInterface;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.ui.util.SimpleAction;
import com.feriantes4dawin.feriavirtualmovil.ui.util.SimpleTextWatcherAdapter;

/**
 * PushDetailProductorDialog 
 * 
 * Implementación de SimpleDialog cuya función es mostrar un 
 * pequeño formulario para realizar una puja sobre un producto en 
 * particular, en una subasta de productores. 
 * 
 */
public final class PushDetailProductorDialog extends SimpleDialog{

    /**
     * Nombre del producto a realizar la puja, que es usado más 
     * tarde para establecer el título del diálogo. 
     */
    private String productName;

    public PushDetailProductorDialog(AppCompatActivity act,String productName, SimpleAction positiveResponseFunc, SimpleAction negativeResponseFunc) {
        super(act,positiveResponseFunc,negativeResponseFunc);
        this.productName = productName;
    }

    @Override
    protected View prepareView() {

        ViewGroup vg = act.findViewById(android.R.id.content);
        this.v = LayoutInflater.from(act).inflate(R.layout.dialog_push_detail_productor, vg, false);

        EditText txtCantidad = v.findViewById(R.id.liiqp_txtUnidades);
        EditText txtCoste = v.findViewById(R.id.liiqp_txtCostePorUnidad);
        ComparadorTextWatcherAdapter comparador = new ComparadorTextWatcherAdapter(txtCantidad,txtCoste);

        txtCantidad.addTextChangedListener(comparador);
        txtCoste.addTextChangedListener(comparador);

        dlg.setTitle(
            String.format("%s: %s",
                act.getString(R.string.ppbd_title),
                productName != null?
                        productName : act.getString(R.string.err_mes_not_avalaible)
            )
        );



        return this.v;
    }

    @Override
    public AlertDialog generate() {

        AlertDialog ad = super.generate();
        
        /**
         * Como no se pueden establecer ciertas propiedades de los botones del 
         * diálogo, puesto que son creados al llamar a show(), agregamos un 
         * evento que nos permita cambiar el estado del botón en el momento que 
         * el diálogo se muestre. 
         * 
         * Con esto se consigue que el usuario no pueda enviar datos vacíos del 
         * formulario, y también consigue mejorar la lógica de negocio asociado 
         * al mismo.
         */
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                ad.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);

            }
        });

        return ad;
    }

    @Override
    protected void prepareResponses() {

        //Para respuesta positiva
        dlg.setButton(AlertDialog.BUTTON_POSITIVE,act.getString(R.string.ppbd_title),
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

                dlg.cancel();

            }
        );

    }

    /**
     * ComparadorTextWatcherAdapter
     * 
     * Clase interna, implementación de SimpleTextWatcherAdapter, que
     * comprueba que los campos de precio por cantidad y cantidad 
     * sean valores válidos. 
     * 
     * Con esto se pueden ahorrar algunas líneas de código que hacen 
     * mas o menos lo mismo.
     */
    public class ComparadorTextWatcherAdapter extends SimpleTextWatcherAdapter {

        /**
         * Objeto EditText cuyo contenido pueda compararse con el objeto 
         * txt propio de SimpleTextWatcherAdapter.
         */
        private EditText txt2;

        /**
         * Constructor que crea un objeto ComparadorTextWatcherAdapter. La idea
         * de tener dos objetos EditText, es la posibilidad de comparar 
         * fácilmente sus contenidos y realizar una lógica en base a esto. 
         * 
         * @param txt Objeto EditText que representa el campo objetivo. 
         * @param txt2 Objeto EditText que representa el campo a comparar. 
         */
        public ComparadorTextWatcherAdapter(EditText txt, EditText txt2){
            super(txt);
            this.txt2 = txt2;
        }

        @Override
        public void afterTextChanged(Editable e) {
            
            /**
             * Si ninguno de los dos está vacío, o cuyo valor no sea 0, entonces 
             * son válidos.
             */
            if ( (e.toString().isEmpty() || e.toString().equals("0")) ||
            ( (txt2.getText().toString().isEmpty() || txt2.getText().toString().equals("0")) )) {

                txt.setError("Completa los campos faltantes");
                dlg.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

            } else {

                txt.setError(null);
                dlg.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

            }
        }

    }

}
