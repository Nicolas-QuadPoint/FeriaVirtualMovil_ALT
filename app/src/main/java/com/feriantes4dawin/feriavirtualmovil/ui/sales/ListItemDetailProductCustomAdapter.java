package com.feriantes4dawin.feriavirtualmovil.ui.sales;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.ProductoVenta;

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
    private List<ProductoVenta> productosVenta;

    /**
     * Crea un objeto ListItemDetailProductCustomAdapter 
     * para un RecyclerView. 
     * 
     * @param productosVenta Objeto que sirve como origen 
     * de datos para este adapter. 
     */
    public ListItemDetailProductCustomAdapter(List<ProductoVenta> productosVenta){

        super();
        this.productosVenta = productosVenta;

    }

    @Override
    public ListItemDetailProductCustomAdapter.ListItemDetailProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_detail_product,parent,false);
        ListItemDetailProductCustomAdapter.ListItemDetailProductViewHolder vh =
                new ListItemDetailProductCustomAdapter.ListItemDetailProductViewHolder(view);
        
        return vh;
    }

    @Override
    public void onBindViewHolder(ListItemDetailProductCustomAdapter.ListItemDetailProductViewHolder holder, int position) {

        ProductoVenta pv = productosVenta.get(position);
        try {

            holder.id_producto = pv.producto.id_producto;
            holder.lblNombreProducto.setText(pv.producto.nombre);
            holder.lblTipoProducto.setText(pv.producto.tipo_producto.descripcion);
            holder.lblUnidades.setText(pv.cantidad.toString());

        } catch(Exception ex) {

            Log.e("LI_ITEM_DETAIL_PROD_CA",ex.toString());

            holder.id_producto = 0;
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

        public Integer id_producto;
        public TextView lblNombreProducto;
        public TextView lblTipoProducto;
        public TextView lblUnidades;

        public ListItemDetailProductViewHolder(View v) {

            super(v);

            this.lblNombreProducto = v.findViewById(R.id.lidp_lblNombreProducto);
            this.lblTipoProducto = v.findViewById(R.id.lidp_lblTipoProducto);
            this.lblUnidades = v.findViewById(R.id.lidp_lblUnidades);

        }

    }

}