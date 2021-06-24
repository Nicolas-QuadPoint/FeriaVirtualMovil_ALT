package com.feriantes4dawin.feriavirtualmovil.data.network.interceptor;

import com.feriantes4dawin.feriavirtualmovil.misc.exceptions.NoConnectionException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Un Interceptor en contexto de peticiones http, es un mecanismo
 * que permite 'ponerse en medio' de una petición en curso.
 * Dicha intromisión es útil para agregar un campo especial,
 * o para revisar el contenido de ésta.
 *
 * Dichas interceptores se ubican en lo que se llaman 'cadenas'
 * o 'chains'. Pueden haber tantos interceptores como se desee,
 * siempre 'encadenadas' una detrás de otra.
 *
 * La razón de ser de esta interfaz es permitir una inyección de
 * dependencias en la aplicación, de esta forma se puede
 * alternar entre un interceptor de un tipo a otro.
 *
 * Como interfaz que es, puede ser implementada para cubrir
 * necesidades específicas, pero aquí se centrarán en dos casos:
 *
 * - Optar por obtener datos por internet, o
 * - Obtener los datos de forma local, almacenadas previamente.
 *
 * De forma gráfica, se puede decir que es mas o menos así:
 *
 *                               /-(SI)-----<fuente-de-datos-en-linea>
 *                              /
 * <petición>----<interceptor>--
 *               ¿Hay internet? \
 *                               \-(NO)-----<fuente-de-datos-en-cache>
 *
 */
public interface ConnectivityInterceptor extends Interceptor {

    @Override
    Response intercept(Interceptor.Chain chain) throws IOException;
}