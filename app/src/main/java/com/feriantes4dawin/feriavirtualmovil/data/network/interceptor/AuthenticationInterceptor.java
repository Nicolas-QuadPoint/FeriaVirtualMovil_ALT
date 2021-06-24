package com.feriantes4dawin.feriavirtualmovil.data.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public interface AuthenticationInterceptor extends Interceptor {

    @Override
    Response intercept(Interceptor.Chain chain) throws IOException;

}
