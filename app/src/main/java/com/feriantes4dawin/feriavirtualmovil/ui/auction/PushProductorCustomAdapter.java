package com.feriantes4dawin.feriavirtualmovil.ui.auction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.Producto;
import com.feriantes4dawin.feriavirtualmovil.ui.widgets.PushDetailProductorDialog;

import java.util.ArrayList;

public class PushProductorCustomAdapter extends RecyclerView.Adapter<PushProductorCustomAdapter.PushProductorViewHolder> {

    private ArrayList<Producto> listaProductos;

    public PushProductorCustomAdapter(){

        super();

        this.listaProductos = new ArrayList<Producto>();

    }

    @Override
    public PushProductorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creo el widget del elemento de lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_push_item,parent,false);
        PushProductorViewHolder vh = new PushProductorViewHolder(view);

        //Agrego el evento al tocar el elemento
        view.setOnClickListener( v -> {

            PushDetailProductorDialog dlg = new PushDetailProductorDialog(
                    (AppCompatActivity) parent.getContext(),
                    vh.lblNombreProducto.getText().toString(), o -> {

                View vista = (View)o;

                EditText txtUnidades = (EditText)vista.findViewById(R.id.liiqp_txtUnidades);
                EditText txtPrecioPorUnidad = (EditText)vista.findViewById(R.id.liiqp_txtCostePorUnidad);

                vh.lblCantidadProducto.setText( txtUnidades.getText().toString() );
                vh.lblPrecioPorUnidadProducto.setText( txtPrecioPorUnidad.getText().toString() );

                //Habilito el check para deseleccionar en caso de
                vh.chkSeleccionado.setChecked(true);

            },null);

            dlg.generate().show();

        });


        return vh;
    }

    @Override
    public void onBindViewHolder(PushProductorViewHolder holder, int position) {
        //Aquí dejo los datos del producto!
        holder.lblNombreProducto.setText(listaProductos.get(position).nombre);

    }

    @Override
    public int getItemCount(){
        return listaProductos.size();
    }

    public class PushProductorViewHolder extends RecyclerView.ViewHolder {

        public Producto p;

        public String id_producto;
        public TextView lblNombreProducto;
        public TextView lblCantidadProducto;
        public TextView lblPrecioPorUnidadProducto;
        public CheckBox chkSeleccionado;

        PushProductorViewHolder(View v) {

            super(v);

            this.id_producto = "0";
            this.lblNombreProducto = (TextView)v.findViewById(R.id.libp_lblnombreProducto);
            this.lblCantidadProducto = (TextView)v.findViewById(R.id.libp_lblCantidadProducto);
            this.lblPrecioPorUnidadProducto = (TextView)v.findViewById(R.id.libp_lblPrecioPorUnidadProducto);
            this.chkSeleccionado = (CheckBox)v.findViewById(R.id.libp_chkSeleccionado);

        }

        public void limpiarCampos(){
            this.lblCantidadProducto.setText("0");
            this.lblPrecioPorUnidadProducto.setText("0");
            this.chkSeleccionado.setChecked(false);
        }

    }

}