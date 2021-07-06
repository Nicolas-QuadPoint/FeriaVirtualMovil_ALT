package com.feriantes4dawin.feriavirtualmovil.data.network.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.feriantes4dawin.feriavirtualmovil.util.FeriaVirtualConstants;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Con ayuda de:
 * https://futurestud.io/tutorials/android-basic-authentication-with-retrofit
 */
public class AuthenticationInterceptorImpl implements AuthenticationInterceptor{

    private Context c;

    public AuthenticationInterceptorImpl(Context c){
        this.c = c;
    }

    public AuthenticationInterceptorImpl getInstance(Context c){
        this.c = c;
        return this;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request req = chain.request();
        Request nuevaPeticion;
        SharedPreferences sp = this.c.getSharedPreferences(
                FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        String token = sp.getString(FeriaVirtualConstants.SP_FERIAVIRTUAL_WEBAPI_AUTH_TOKEN,"none");
        Log.i("AUTH_INTERCEPTOR","Token sesion: "+token);
        nuevaPeticion = req.newBuilder()
                .addHeader(FeriaVirtualConstants.SP_FERIAVIRTUAL_WEBAPI_AUTH_TOKEN,token)
                .build();

        return chain.proceed(nuevaPeticion);
    }
}
