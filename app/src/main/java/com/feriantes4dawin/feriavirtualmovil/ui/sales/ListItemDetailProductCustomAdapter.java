package com.feriantes4dawin.feriavirtualmovil.ui.sales;


import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.DetallePujaSubastaProductor;
import com.feriantes4dawin.feriavirtualmovil.data.models.TipoVenta;
import com.feriantes4dawin.feriavirtualmovil.ui.auction.PushProductorActivity;
import com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants;
import com.google.gson.Gson;

import java.util.List;

/**
 * ListItemDetailProductCustomAdapter 
 * 
 * Es un adapter que representa los datos de la lista de productos 
 * para pujar en una subasta. 
 */
public class ListItemDetailProductCustomAdapter extends RecyclerView.Adapter<ListItemDetailProductCustomAdapter.ListItemDetailProductViewHolder> {

    /**
     * Fuente de datos para rellenar este adapter.
     */
    private List<DetallePujaSubastaProductor> productosVenta;

    /**
     * Referencia a contexto para lanzar actividades.
     */
    private AppCompatActivity activity;

    /**
     * Flag para evitar acciones de modificacion por parte
     * del usuario
     */
    private boolean modoSoloLectura;

    /**
     * Objeto para usar datos en json
     */
    private Gson convertidorJSON;

    /**
     * Crea un objeto ListItemDetailProductCustomAdapter 
     * para un RecyclerView. 
     * 
     * @param productosVenta Objeto que sirve como origen 
     * de datos para este adapter. 
     */
    public ListItemDetailProductCustomAdapter(AppCompatActivity act,List<DetallePujaSubastaProductor> productosVenta){

        super();
        this.activity = act;
        this.productosVenta = productosVenta;
        this.modoSoloLectura = false;
        this.convertidorJSON = new Gson();

    }

    public ListItemDetailProductCustomAdapter(AppCompatActivity act, List<DetallePujaSubastaProductor> productosVenta, boolean modoSoloLectura, Gson convertidorJSON){

        super();
        this.activity = act;
        this.productosVenta = productosVenta;
        this.modoSoloLectura = modoSoloLectura;
        this.convertidorJSON = convertidorJSON == null? new Gson() : convertidorJSON;
    }

    @Override
    public ListItemDetailProductCustomAdapter.ListItemDetailProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_detail_product,parent,false);
        ListItemDetailProductCustomAdapter.ListItemDetailProductViewHolder vh =
                new ListItemDetailProductCustomAdapter.ListItemDetailProductViewHolder(view);

        if(!modoSoloLectura){

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String detalleString = convertidorJSON.toJson(vh.detalle);
                    Intent i = new Intent(activity, PushProductorActivity.class);
                    i.putExtra(FeriaVirtualConstants.MODO_SOLO_LECTURA,modoSoloLectura);
                    i.putExtra(FeriaVirtualConstants.SP_DETALLE_PUJA_PROD_STR,detalleString);

                    if(modoSoloLectura){

                        i.putExtra(FeriaVirtualConstants.CODIGO_ACCION,FeriaVirtualConstants.ACCION_VISUALIZAR_PUJA);

                    } else {

                        i.putExtra(FeriaVirtualConstants.CODIGO_ACCION,FeriaVirtualConstants.ACCION_MODIFICAR_PUJA);

                    }

                    activity.startActivityForResult(i,0);

                }
            });

        }

        return vh;
    }

    @Override
    public void onBindViewHolder(ListItemDetailProductCustomAdapter.ListItemDetailProductViewHolder holder, int position) {

        holder.detalle = productosVenta.get(position);

        try {

            holder.lblNombreProducto.setText(holder.detalle.producto.nombre);
            holder.lblUnidades.setText(holder.detalle.cantidad.toString());
            holder.lblTipoProducto.setText(
                TipoVenta.getTipoVentaByID(holder.detalle.tipo_venta.id_tipo_venta).descripcion
            );

        } catch(Exception ex) {

            Log.e("LI_ITEM_DETAIL_PROD_CA",ex.toString());

            holder.lblNombreProducto.setText(R.string.err_mes_not_avalaible);
            holder.lblTipoProducto.setText(R.string.err_mes_not_avalaible);
            holder.lblUnidades.setText("0");

        }

    }

    @Override
    public int getItemCount(){
        return productosVenta.size();
    }

    /**
     * ListItemDetailProductViewHolder 
     * 
     * Vista que contiene los controles donde mostrar los 
     * datos obtenidos de la fuente, y que representan un 
     * elemento individual de la lista. 
     */
    public class ListItemDetailProductViewHolder extends RecyclerView.ViewHolder {

        public DetallePujaSubastaProductor detalle;

        public View contenedor;
        public TextView lblNombreProducto;
        public TextView lblTipoProducto;
        public TextView lblUnidades;

        public ListItemDetailProductViewHolder(View v) {

            super(v);

            this.contenedor = v.findViewById(R.id.lidp_contenedor);
            this.lblNombreProducto = v.findViewById(R.id.lidp_lblNombreProducto);
            this.lblTipoProducto = v.findViewById(R.id.lidp_lblTipoProducto);
            this.lblUnidades = v.findViewById(R.id.lidp_lblUnidades);

        }

    }

}