package com.feriantes4dawin.feriavirtualmovil;

import android.app.Application;
import android.content.Context;

import com.feriantes4dawin.feriavirtualmovil.data.datasources.local.FeriaVirtualDBProvider;
import com.feriantes4dawin.feriavirtualmovil.data.datasources.remote.FeriaVirtualAPIProvider;

import dagger.Module;
import dagger.Provides;

/**
 * FeriaVirtualApplication
 * 
 * Es la Clase base que representa la aplicación misma,  
 * y por ende, es la raiz de todas las referencias a las 
 * dependencias a inyectar en las demás instancias de clase. 
 * 
 * Es aquí donde algunas dependencias (usualmente de instancia 
 * única) son creadas y se 'cuelgan' al ciclo de vida de esta 
 * clase.
 * 
 * Digamos que es algo así como el 'método main' de la 
 * aplicación android.
 */
public class FeriaVirtualApplication extends Application {

    /**
     * Objeto FeriaVirtualComponent que entrega los métodos 
     * necesarios para inyectar dependencias a las clases que 
     * se necesiten. 
     */
    private FeriaVirtualComponent feriaVirtualComponent;

    @Override
    public void onCreate() {

        //Llamamos la versión del padre que hace todo el trabajo
        //genérico
        super.onCreate();

        //Y este crea todas las instancias necesarias de las dependencias 
        //indicadas en FeriaVirtualComponent. Con esto estarán disponibles 
        //para todas las clases que se necesiten.
        this.feriaVirtualComponent = DaggerFeriaVirtualComponent.builder().
                feriaVirtualDBProvider(new FeriaVirtualDBProvider(this)).
                feriaVirtualAPIProvider(new FeriaVirtualAPIProvider(this)).
                build();
    }

    /**
     * Proporciona un objeto FeriaVirtualComponent, requerido para ciertas 
     * cosas de la aplicación. 
     * @see {@link FeriaVirtualComponent}
     * @return Una instancia de FeriaVirtualComponent.
     */
    public FeriaVirtualComponent getFeriaVirtualComponent(){
        return feriaVirtualComponent;
    }

    //FeriaVirtualApplicationGraphComponent c = DaggerFeriaVirtualApplicationGraph.create();

}
