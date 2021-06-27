package com.feriantes4dawin.feriavirtualmovil.data.datasources.remote;

import android.content.Context;

import com.feriantes4dawin.feriavirtualmovil.FeriaVirtualApplication;
import com.feriantes4dawin.feriavirtualmovil.data.network.ProductoAPIService;
import com.feriantes4dawin.feriavirtualmovil.data.network.SubastaAPIService;
import com.feriantes4dawin.feriavirtualmovil.data.network.UsuarioAPIService;
import com.feriantes4dawin.feriavirtualmovil.data.network.VentaAPIService;
import com.feriantes4dawin.feriavirtualmovil.data.network.interceptor.AuthenticationInterceptor;
import com.feriantes4dawin.feriavirtualmovil.data.network.interceptor.AuthenticationInterceptorImpl;
import com.feriantes4dawin.feriavirtualmovil.data.network.interceptor.ConnectivityInterceptor;
import com.feriantes4dawin.feriavirtualmovil.data.network.interceptor.ConnectivityInterceptorImpl;
import com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class FeriaVirtualAPIProvider {

    private FeriaVirtualApplication fva;
    private Retrofit.Builder apiServiceCreator;

    @Inject
    public FeriaVirtualAPIProvider(FeriaVirtualApplication fva){
        this.fva = fva;
    }

    @Provides
    @Singleton
    public UsuarioAPIService provideUsuarioAPI() {
        return commonAPIBuilder().build().
        create(UsuarioAPIService.class);
    }

    @Provides
    @Singleton
    public VentaAPIService provideVentaAPI(){
        return commonAPIBuilder().build().
        create(VentaAPIService.class);
    }

    @Provides
    @Singleton
    public SubastaAPIService provideSubastaAPI(){
        return commonAPIBuilder().build().
                create(SubastaAPIService.class);
    }

    @Provides
    @Singleton
    public ProductoAPIService provideProductoAPI(){
        return commonAPIBuilder().build().
                create(ProductoAPIService.class);
    }

    private Retrofit.Builder commonAPIBuilder(){

        if(this.apiServiceCreator == null){

            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            OkHttpClient httpClient;
            AuthenticationInterceptor ai = new AuthenticationInterceptorImpl(this.fva);
            ConnectivityInterceptor ci = new ConnectivityInterceptorImpl(this.fva);

            if(!httpClientBuilder.interceptors().contains(ai)){
                httpClientBuilder.interceptors().add(ai);
            }

            if(!httpClientBuilder.interceptors().contains(ci)){
                httpClientBuilder.interceptors().add(ci);
            }

            httpClient = httpClientBuilder.build();

            //Crea un builder para transformar los datos a enviar a la api
            this.apiServiceCreator = new Retrofit.Builder().

                    //Se establece el objeto que gestionara las peticiones http para retrofit
                            client(httpClient).

                    //Esto indica la url base que la api tiene.
                            baseUrl(FeriaVirtualConstants.URL_BASE_API_WEB_FERIAVIRTUAL).

                    //Esto activa el mecanismo de coroutines de Kotlin, usando un adapter que
                    //gestiona dichos procesos
                    //addCallAdapterFactory(CoroutineCallAdapterFactory()).

                    //Y e aquí lo más importante: El objeto que se encargará de convertir
                    //cada objeto de dato en JSON para nuestra api
                            addConverterFactory(GsonConverterFactory.create());
        }

        return apiServiceCreator;
    }

}