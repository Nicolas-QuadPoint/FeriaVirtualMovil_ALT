package com.feriantes4dawin.feriavirtualmovil.ui.widgets;

import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.util.SimpleAction;

/**
 * SimpleDialog
 * 
 * Es una clase base que representa un simple cuadro de 
 * diálogo, ahorrando líneas de código que suponen crear un 
 * diálogo desde cero.
 * 
 * Como no es instanciable, su comportamiento no implica un
 * cuadro de diálogo completo, por lo que es deber de sus 
 * implementaciones finalizar su funcionamiento. 
 */
public abstract class SimpleDialog{

    /**
     * Referencia a una actividad. Esto permite 
     * a las implementaciones acceder de forma 
     * rápida a una vista o a la misma actividad 
     * y realizar ciertas acciones. 
     */
    protected AppCompatActivity act;

    /**
     * Objeto SimpleAction que es disparado cuando 
     * el botón del dialogo con valor 
     * DialogAlert.BUTTON_POSITIVE, es pulsado.
     * Puede ser null.
     */
    protected SimpleAction positiveResponseFunc;

    /**
     * Objeto SimpleAction que es disparado cuando 
     * el botón del dialogo con valor 
     * DialogAlert.BUTTON_NEGATIVE, es pulsado.
     * Puede ser null.
     */
    protected SimpleAction negativeResponseFunc;

    /**
     * Referencia al objeto AlertDialog generado.
     */
    protected AlertDialog dlg;

    /**
     * Referencia a la vista base sobre el cual el 
     * contenido del diálogo es desplegado.
     */
    protected View v;

    /**
     * Permite la creación de un objeto SimpleDialog, el cual 
     * es heredado por sus implementaciones.
     * 
     * @param act Objeto que represente un flujo de actividad.
     * @param positiveResponseFunc Objeto SimpleAction que es disparado 
     * si el botón positivo del diálogo es presionado. Puede ser null. 
     * @param negativeResponseFunc Objeto SimpleAction que es disparado 
     * si el botón negativo del diálogo es presionado. Puede ser null.
     */
    public SimpleDialog(AppCompatActivity act, SimpleAction positiveResponseFunc, SimpleAction negativeResponseFunc) {
        this.act = act;
        this.positiveResponseFunc = positiveResponseFunc;
        this.negativeResponseFunc = negativeResponseFunc;
    }

    /**
     * Método que permite a las implementaciones 'crear' 
     * la vista base donde los componentes importantes que 
     * representen el 'contenido' del diálogo se desplieguen. 
     * 
     * @return Un objeto View que representa la vista base. 
     */
    protected abstract View prepareView();

    /**
     * Método que permite a las implementaciones crear las 
     * opciones del diálogo. Aquí se crean los 'botones' y se les 
     * asocian los 'eventos' obtenidos por positiveResponseFunc y 
     * negativeResponseFunc. Este paso es opcional, y no afecta al 
     * funcionamiento del diálogo.
     */
    protected abstract void prepareResponses();

    /**
     * Método que crea al objeto AlertDialog, lo preconfigura, y 
     * le establece la vista generada por preapareView, además de 
     * establecer las posibles respuestas llamando a prepareResponse. 
     * 
     * @throws NullPointerException Si prepareView devuelve una referencia 
     * nula a un objeto View.
     * @return Un objeto AlertDialog generado y listo para mostrar.
     */
    public AlertDialog generate(){

        AlertDialog.Builder b = new AlertDialog.Builder(act);
        ViewGroup vg = act.findViewById(android.R.id.content);
        
        this.dlg = b.create();
        
        dlg.setTitle(R.string.other_stub);

        //Con esto evitamos que cuando se presione el botón de retroceso,
        //el dialogo no desaparece!
        dlg.setCancelable(false);
        
        //Establecemos la vista
        dlg.setView(prepareView());

        if(this.v != null){

            prepareResponses();
            return this.dlg;

        } else {

            throw new NullPointerException("Can't generate the required View object for AlertDialog");

        }

    }


}
