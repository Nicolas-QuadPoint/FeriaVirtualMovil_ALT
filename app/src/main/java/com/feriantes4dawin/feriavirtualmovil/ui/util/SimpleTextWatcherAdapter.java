package com.feriantes4dawin.feriavirtualmovil.ui.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * SimpleTextWatcherAdapter
 * 
 * Es una implementación 'torpe' de TextWatcher, que actúa como 
 * adaptador. Cualquier clase que extienda de SimpleTextWatcherAdapter
 * puede sobrescribir los métodos que va a usar, cosa que no se 
 * puede hacer de forma directa con TextWatcher.
 * 
 * Además de esto, se proporciona un objeto EditText adicional,
 * que referencie al objeto que use esta clase como listener. 
 * 
 */
public class SimpleTextWatcherAdapter implements TextWatcher {

    protected EditText txt;

    public SimpleTextWatcherAdapter(EditText txt){
        this.txt = txt;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after){
        //No hago nada. Impleméntame y sobrescríbeme!
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count){
        //No hago nada. Impleméntame y sobrescríbeme!
    }

    @Override
    public void afterTextChanged(Editable e){
        //No hago nada. Impleméntame y sobrescríbeme!
    }

}
