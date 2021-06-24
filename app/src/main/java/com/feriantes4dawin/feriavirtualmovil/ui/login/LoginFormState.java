package com.feriantes4dawin.feriavirtualmovil.ui.login;

/**
 * LoginFormState 
 * 
 * Clase que encapsula datos con detalles del 
 * estado actual de la autenticación, permitiendo 
 * conocer en el momento si los campos son válidos o no. 
 * Utilizado en LoginViewModel para enviar datos de 
 * estado a LoginActivity. 
 * 
 * @see {@link LoginViewModel}
 */
public class LoginFormState {

    public Integer usernameError;
    public Integer passwordError;
    public Boolean isDataValid;

    public LoginFormState(){}

    public LoginFormState(Integer usernameError, Integer passwordError, Boolean isDataValid) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = isDataValid;
    }
}