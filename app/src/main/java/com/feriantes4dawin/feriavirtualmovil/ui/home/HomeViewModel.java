package com.feriantes4dawin.feriavirtualmovil.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel{

    public MutableLiveData<String> _text;
    public LiveData<String> text;

    public HomeViewModel(){
        this._text = new MutableLiveData<String>();
        this._text.setValue("Home fragment!");
        this.text = _text;
    }
}