package com.feriantes4dawin.feriavirtualmovil.ui.login;

/**
 * User details post authentication that is exposed to the UI
 */
public class LoggedInUserView{
    
    public String displayName;
    //... other data fields that may be accessible to the UI

    public LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    public LoggedInUserView() {

    }
}