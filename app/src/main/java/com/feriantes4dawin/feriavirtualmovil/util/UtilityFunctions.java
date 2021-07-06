package com.feriantes4dawin.feriavirtualmovil.util;

import android.content.Intent;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;

/**
 * UtilityFunctions
 * 
 * Clase que contiene métodos estáticos que cumplen distintas 
 * funciones. 
 */
public class UtilityFunctions {

    /**
     * Concepto y paso a paso extraido de:
     * https://www.tutorialspoint.com/how-to-pick-an-image-from-image-gallery-in-android
     *
     * Para que esto tenga efecto, la actividad que invoque este método debe sobrescribir
     * el método onActivityResult para obtener la información de la imágen.
     * La aplicación debe tener concedido el permiso para leer archivos. Vease esto en
     * el archivo de manifiesto
     * 
     * @throws NullPointerException Si act es nulo.
     * @param act Un objeto AppCompatActivity, no nulo.
     */
    public static void getImageFromGallery(AppCompatActivity act){

        if(act == null) throw new NullPointerException("No puedes usar esto con una actividad nula!");

        //Esto generara un dialogo para acceder a la galeria y escoger una imagen. La cual sera entregada
        //en forma de ubicación absoluta
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        //Vamos mostrando el dialogo!
        act.startActivityForResult(galleryIntent,FeriaVirtualConstants.ACCION_ESCOGER_IMAGEN);
    }

}