package com.feriantes4dawin.feriavirtualmovil.ui.login;

/**
 * LoginResult 
 * 
 * Clase que permite transportar datos de error o éxito 
 * referentes a la autenticación del usuario. 
 */
public class LoginResult{

    /**
     * Objeto con detalles del usuario autenticado, si lo hay. 
     */
    public LoggedInUserView success;

    /**
     * Objeto que obtendrá un valor si un error surge durante 
     * la autenticación. 
     */
    public Integer error;

    /**
     * Objeto que obtendrá un valor si un error surge durante 
     * la autenticación, y éste requiere de un mensaje con 
     * detalles. 
     */
    public String errorMessage;

    public LoginResult() {

    }

    public LoginResult(LoggedInUserView success, Integer error) {
        this.success = success;
        this.error = error;
    }

    public LoginResult(LoggedInUserView success, Integer error, String errorMessage){
        this.success = success;
        this.error = error;
        this.errorMessage = errorMessage;
    }
}