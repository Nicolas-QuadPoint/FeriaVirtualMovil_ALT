package com.feriantes4dawin.feriavirtualmovil;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * FeriaVirtualApplicationModule
 * 
 * El módulo que realiza el único trabajo de 
 * proporcionar un objeto Context a las 
 * clases que lo necesiten. 
 */
@Module
public class FeriaVirtualApplicationModule {

    /**
     * Instacia de la aplicación (la principal) para 
     * guardarla y proporcionarla más tarde 
     */
    private Application application;

    /**
     * Constructor para generar una instancia de 
     * FeriaVirtualApplicationModule. Esta clase está hecha 
     * de forma que <b>no deba ser creada por el usuario 
     * explícitamente. </b> 
     * 
     * @param app Una instancia de Application, proporcionada 
     * por Android al momento de iniciar la aplicación. 
     */
    @Inject
    public FeriaVirtualApplicationModule(Application app){

        this.application = app;
    }

    /**
     * Proporciona un objeto Application que puede 
     * convertirse a Context para usarse en determinadas 
     * ocasiones. 
     * 
     * @return Una instancia Application, no null.
     */
    @Provides
    @Singleton
    public Application getContext(){
        return application;
    }

}
