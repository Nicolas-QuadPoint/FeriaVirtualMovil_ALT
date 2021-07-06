package com.feriantes4dawin.feriavirtualmovil.ui.widgets;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.util.SimpleAction;
import com.feriantes4dawin.feriavirtualmovil.util.SimpleTextWatcherAdapter;

import android.content.DialogInterface;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ChangePasswordDialog 
 * 
 * Implementación de SimpleDialog que representa un formulario 
 * para el cambio de contraseña del usuario. 
 * 
 * Con tres campos, se permite tanto la validación de la nueva 
 * contraseña, como la comprobación de la constraseña actual. 
 */
public class ChangePasswordDialog extends SimpleDialog{

    /**
     * Contraseña cambiada del usuario. actualizada con cada 
     * cambio al formulario. 
     */
    private String passwdString;
    //private MutableLiveData<ObjetoModificacionContrasena> datosMutablesContrasena;

    public ChangePasswordDialog(AppCompatActivity act,
      //                          MutableLiveData<ObjetoModificacionContrasena> datosMutablesContrasena,
                                SimpleAction positiveResponseFunc,
                                SimpleAction negativeResponseFunc) {
        super(act,positiveResponseFunc,negativeResponseFunc);

       // this.datosMutablesContrasena = datosMutablesContrasena;
    }

    /**
     * Contiene la cadena de contraseña que es modificada 
     * cuando el usuario realiza una modificación en el 
     * formulario.
     * 
     * @return La cadena con la contraseña.
     */
    public String getPasswdString(){
        return this.passwdString;
    }

    @Override
    protected View prepareView() {

        ViewGroup vg = act.findViewById(android.R.id.content);
        this.v = LayoutInflater.from(act).inflate(R.layout.dialog_change_password, vg, false);
        dlg.setTitle(act.getString(R.string.mpf_setting_change_passwd));

        EditText txtPass1 = (EditText) v.findViewById(R.id.dcp_txtPasswd2);
        EditText txtPass2 = (EditText) v.findViewById(R.id.dcp_txtPasswd3);

        txtPass1.addTextChangedListener(new ChangePasswordTextWatcherAdapter(txtPass1,txtPass2));
        txtPass2.addTextChangedListener(new ChangePasswordTextWatcherAdapter(txtPass2,txtPass1));

        return this.v;
    }

    @Override
    public AlertDialog generate() {
        AlertDialog ad = super.generate();

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

        dlg.setButton(AlertDialog.BUTTON_POSITIVE,act.getString(R.string.action_change),
            (dialogo,idBoton) ->{

                if (positiveResponseFunc != null) {
                    positiveResponseFunc.doAction(v);
                }

                dlg.cancel();

            }
        );

        dlg.setButton(AlertDialog.BUTTON_NEGATIVE,act.getString(R.string.action_cancel),
            (dialogo,idBoton) ->{

                this.passwdString = null;

                if (negativeResponseFunc != null) {
                    negativeResponseFunc.doAction(v);
                }

                dlg.cancel();

            }
        );

    }


    /**
     * ChangePasswordTextWatcherAdapter
     * Objeto que extiende de SimpleTextWatcherAdapter para vigilar
     * el valor de ambos campos de contraseña típicos de los 
     * formularios de este tipo.
     * 
     * Se asegura que ambas contraseñas sean iguales, y no vacías. 
     */
    public class ChangePasswordTextWatcherAdapter extends SimpleTextWatcherAdapter {

        /**
         * Objeto EditText a comparar. 
         */
        private EditText txtPasswd2;

        /**
         * Crea un objeto ChangePasswordTextWatcherAdapter
         * 
         * @param txtPasswd1 El EditText principal. 
         * @param txtPasswd2 El EditText a comparar. 
         */
        public ChangePasswordTextWatcherAdapter(EditText txtPasswd1, EditText txtPasswd2){
            super(txtPasswd1);
            this.txtPasswd2 = txtPasswd2;
        }

        @Override
        public void afterTextChanged(Editable e) {

            if(isPasswdValid(e) && isPasswdValid(txtPasswd2.getText()) &&
            e.toString().equals(txtPasswd2.getText().toString())){

                txtPasswd2.setError(null);
                txt.setError(null);
                dlg.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

            } else {

                txtPasswd2.setError(act.getString(R.string.err_mes_passwd_does_not_match));
                dlg.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                passwdString = txtPasswd2.getText().toString();

            }
        }

        /**
         * Método de validación para ofrecer la lógica de negocio 
         * de una contraseña.
         * TODO: ¿Debería delegar esto a otra clase?
         * 
         * @param e Un objeto Editable con el contenido de texto para 
         * validar. 
         * @return true si la cadena de contraseña supera la prueba de 
         * validación, o false.
         */
        private boolean isPasswdValid(Editable e){

            return (e.length() > 5 || !e.toString().isEmpty());

        }
    }

}