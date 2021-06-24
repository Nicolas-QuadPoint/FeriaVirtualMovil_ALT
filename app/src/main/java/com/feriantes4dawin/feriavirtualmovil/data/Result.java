package com.feriantes4dawin.feriavirtualmovil.data;

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
public class Result<T> {

    public class Success<T> extends Result{
        public T data;
        public Success(){ }
        public Success(T d){
            this.data = d;
        }
    }

    public class Error<T> extends Result{
        public Exception exception;
        public Error(){}
        public Error(Exception ex){
            this.exception = ex;
        }
    }

    @Override
    public String toString(){
        if(this instanceof  Success){
            return String.format("Success[data=%s]",((Success<?>)this).data.toString());
        }
        else if(this instanceof Error){
            return String.format("Error[exception=%s]",((Error<?>) this).exception);
        }
        else{
            return "Unkwown type";
        }
    }
}