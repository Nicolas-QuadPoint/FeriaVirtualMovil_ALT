package com.feriantes4dawin.feriavirtualmovil.ui.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.ui.util.SimpleAction;

/**
 * YesNoDialog
 * 
 * Es una implementación de SimpleDialog, cuya función es 
 * otorgar un diálogo de pregunta, con dos opciones: Sí y No. 
 * 
 */
public class YesNoDialog extends SimpleDialog{

    private boolean result;
    private String title;
    private String message;

    public YesNoDialog(AppCompatActivity act, String title, String message,
                       SimpleAction positiveResponseFunc,
                       SimpleAction negativeResponseFunc) {
        super(act,positiveResponseFunc,negativeResponseFunc);
        this.title = title;
        this.message = message;
        this.result = false;
    }

    /**
     * Permite consultar el estado de la respuesta 
     * que el usuario indica durante o después de responder.
     * 
     * @return true si el usuario responde 'Sí' o false si responde 
     * que 'No'. Por defecto, el valor es false.
     */
    public boolean getResult(){

        return this.result;
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

        dlg.setButton(AlertDialog.BUTTON_POSITIVE,act.getString(R.string.action_yes),
            (dialogo,idBoton) ->{

                result = true;

                if (positiveResponseFunc != null) {
                    positiveResponseFunc.doAction(v);
                }

                dlg.cancel();

            }
        );

        dlg.setButton(AlertDialog.BUTTON_NEGATIVE,act.getString(R.string.action_no),
            (dialogo,idBoton) ->{

                result = false;

                if (negativeResponseFunc != null) {
                    negativeResponseFunc.doAction(v);
                }

                dlg.cancel();

            }
        );

    }
}