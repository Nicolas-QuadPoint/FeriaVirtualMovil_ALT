package com.feriantes4dawin.feriavirtualmovil.ui.widgets;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.feriantes4dawin.feriavirtualmovil.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimpleSpinnerArrayAdapter<T> extends BaseAdapter {

    private List<T> elementos;
    private Context context;

    public SimpleSpinnerArrayAdapter(@NonNull Context context, @NonNull List<T> objects) {

        super();
        this.elementos = objects;
        this.context = context;

    }

    @Override
    public int getCount() {
        return elementos.size();
    }

    @Override
    public T getItem(int position) {
        return elementos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View contenedor = convertView;
        TextView lblContenido = null;

        if(contenedor == null){

            contenedor = LayoutInflater.from(context).inflate(R.layout.list_item_spinner_simple,parent,false);

        }

        lblContenido= contenedor.findViewById(R.id.liss_lblTexto);

        lblContenido.setText( elementos.get(position).toString() );

        return contenedor;

    }
}
