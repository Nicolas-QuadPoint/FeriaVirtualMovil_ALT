package com.feriantes4dawin.feriavirtualmovil.util;

/**
 * SimpleAction 
 * 
 * Interfaz que cumple una simple función: Ofrecer la posibilidad 
 * de ejecutar una orden en la clase que se implementa. 
 * 
 * Utilizado para permitir acciones desde las variantes de SimpleDialog. 
 */
public interface SimpleAction {

    /**
     * Método que deben implementar las clases que 
     * usen esta interfaz. 
     * 
     * @param o Una instancia de cualquier objeto. 
     */
    void doAction(Object o);

}
