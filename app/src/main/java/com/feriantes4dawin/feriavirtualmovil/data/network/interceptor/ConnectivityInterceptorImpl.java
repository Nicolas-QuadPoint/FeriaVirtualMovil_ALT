package com.feriantes4dawin.feriavirtualmovil.data.network.interceptor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ConnectivityInterceptorImpl implements ConnectivityInterceptor{

    private Context c;

    public ConnectivityInterceptorImpl(Context c){
        this.c = c;
    }

    public ConnectivityInterceptorImpl getInstance(Context c){
        this.c = c;
        return this;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        if(!isOnline()){
            throw new IOException();
        }

        return chain.proceed(chain.request());
    }

    private boolean isOnline(){

        /* Esto ayudará a saber si la aplicacion está conectada a internet */
        ConnectivityManager connectivityManager = (ConnectivityManager)(c.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null? networkInfo.isConnected() : false;

    }

}