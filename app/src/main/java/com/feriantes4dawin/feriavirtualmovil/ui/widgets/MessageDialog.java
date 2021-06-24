package com.feriantes4dawin.feriavirtualmovil.ui.widgets;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.ui.util.EnumMessageType;
import com.feriantes4dawin.feriavirtualmovil.ui.util.SimpleAction;

/**
 * MessageDialog 
 * 
 * Implementación de SimpleDialog, que representa un simple cuadro 
 * de texto con una sola opción: Ok. 
 * 
 * Utilizado con motivo de entregar información relevante que tenga 
 * mayor impacto que un Toast o Snackbar. 
 */
public class MessageDialog extends SimpleDialog {

    /**
     * Título del diálogo 
     */
    private String title;

    /**
     * Mensaje del diálogo 
     */
    private String message;

    /**
     * Tipo del mensaje para el diálogo. 
     * Con esto se puede personalizar el estado de un mensaje 
     * si se trata de una información, advertencia o error, con 
     * alguna imágen lateral o cambio de estilo. 
     */
    private EnumMessageType messageType;

    /**
     * Permite crear un objeto MessageDialog. 
     * 
     * @param act Instancia válida de un objeto AppCompatActivity. 
     * @param messageType Tipo del mensaje, también obligatorio.
     * @param title Título del diálogo.
     * @param message Mensaje del diálogo.
     * @param positiveResponseFunc Evento para gestionar una acción en 
     * caso de que el botón Ok es presionado. Puede ser null.
     */
    public MessageDialog(AppCompatActivity act,
                         EnumMessageType messageType,
                         String title, String message,
                         SimpleAction positiveResponseFunc){

        super(act,positiveResponseFunc,null);
        this.title = title;
        this.message = message;
        this.messageType = messageType;

    }

    @Override
    protected View prepareView() {

        ViewGroup vg = act.findViewById(android.R.id.content);
        this.v = LayoutInflater.from(act).inflate(R.layout.dialog_message, vg, false);
        TextView txtMessage = (TextView) v.findViewById(R.id.dyn_lblMensaje);

        dlg.setTitle(title);
        txtMessage.setText(message);

        return this.v;
    }

    @Override
    protected void prepareResponses() {

        dlg.setButton(DialogInterface.BUTTON_POSITIVE, act.getString(R.string.action_accept),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(positiveResponseFunc != null){
                    positiveResponseFunc.doAction(MessageDialog.this);
                }
                dlg.cancel();
            }
        });

    }

}
