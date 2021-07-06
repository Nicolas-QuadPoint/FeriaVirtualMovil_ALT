package com.feriantes4dawin.feriavirtualmovil.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * JsonConverterProvider
 * 
 * Módulo que entrega dependencias basadas en 
 * clases que manejen datos en JSON. 
 */
@Module
public class JsonConverterProvider {

    /**
     * Proporciona la dependencia de un objeto Gson
     * para poder convertir datos en json a clase, y 
     * visceversa. Crear objetos Gson es pesado, por 
     * ende es mejor crear uno solo y compartirlo. 
     * 
     * @see {@link com.google.gson.Gson}
     * @return Una instancia de Gson.
     */
    @Singleton
    @Provides
    public Gson getJSONParser(){

        return new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().create();

    }

}
