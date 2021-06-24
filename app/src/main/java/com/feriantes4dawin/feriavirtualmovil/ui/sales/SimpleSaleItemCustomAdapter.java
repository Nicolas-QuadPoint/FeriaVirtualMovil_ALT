package com.feriantes4dawin.feriavirtualmovil.ui.sales;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.VentaSimple;
import com.feriantes4dawin.feriavirtualmovil.data.models.VentasSimples;
import com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants;

/**
 * SimpleSaleItemCustomAdapter 
 * 
 * Adaptador para RecyclerView que gestiona la estructura, valores y 
 * eventos relacionados a la Lista de Ventas proporcionado por 
 * CurrentSalesFragment. 
 * 
 * Al tocar un elemento de la lista, será rederigido a SaleDetailActivity 
 * con los datos en detalle de la venta. 
 * 
 * Este es el flujo determinado para poder participar en subastas de 
 * Productor o de Transportista según sea el caso.
 */
public class SimpleSaleItemCustomAdapter extends RecyclerView.Adapter<SimpleSaleItemCustomAdapter.SimpleSaleItemViewHolder> {

    /**
     * Objeto que contiene una lista de objetos Venta, 
     * utilizados como fuente de datos para este adaptador.
     * 
     * @see {@link com.feriantes4dawin.feriavirtualmovil.data.models.VentasSimples}
     */
    private VentasSimples ventasSimples;

    /**
     * Constructor que crea un objeto SimpleSaleItemCustomAdapter. 
     * 
     * @param ventasSimples Objeto VentasSimples usado como origen de 
     * datos.
     */
    public SimpleSaleItemCustomAdapter(VentasSimples ventasSimples){

        super();
        this.ventasSimples = ventasSimples;

    }

    @Override
    public SimpleSaleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sale_item,parent,false);
        SimpleSaleItemViewHolder vh = new SimpleSaleItemViewHolder(view);

        /**
         * Aquí se gestiona el evento de selección para un elemento de lista. 
         * El contexto aquí es simplemente redirigir el flujo del fragmento y 
         * trasladar el trabajo a SaleDetailActivity para continuar con el proceso 
         * de participación a subasta. 
         */
        view.setOnClickListener(v -> {

            SharedPreferences sp = parent.getContext().getSharedPreferences(
                    FeriaVirtualConstants.FERIAVIRTUAL_MOVIL_SHARED_PREFERENCES,
                    Context.MODE_PRIVATE);

            Intent i = new Intent(parent.getContext(),SaleDetailActivity.class);

            sp.edit().putInt(FeriaVirtualConstants.SP_VENTA_ID,vh.id_venta).commit();

            parent.getContext().startActivity(i);
        });



        return vh;
    }

    @Override
    public void onBindViewHolder(SimpleSaleItemViewHolder holder, int position) {

        try {

            VentaSimple vs = ventasSimples.ventas.get(position);

            holder.id_venta = vs.id_venta;
            holder.lblNombreEmpresa.setText(vs.usuario_autor.nombre);
            holder.lblFechaInicioVenta.setText(vs.fecha_inicio_venta);
            holder.lblComentariosVenta.setText(vs.comentarios_venta);

        } catch(Exception ex) {

            holder.id_venta = 0;
            holder.lblNombreEmpresa.setText(R.string.err_mes_not_avalaible);
            holder.lblFechaInicioVenta.setText(R.string.err_mes_not_avalaible);
            holder.lblComentariosVenta.setText(R.string.err_mes_not_avalaible);

        }

    }

    @Override
    public int getItemCount(){
        return ventasSimples.ventas.size();
    }

    /**
     * SimpleSaleItemViewHolder 
     * 
     * Vista que contiene los elementos a los cuales darles valores 
     * recuperados de la fuente de datos. 
     * 
     */
    public class SimpleSaleItemViewHolder extends RecyclerView.ViewHolder {

        public Integer id_venta;
        public TextView lblNombreEmpresa;
        public TextView lblFechaInicioVenta;
        public TextView lblComentariosVenta;

        public SimpleSaleItemViewHolder(View v) {

            super(v);

            this.lblNombreEmpresa = v.findViewById(R.id.csi_lblNombreNegocio);
            this.lblFechaInicioVenta = v.findViewById(R.id.csi_lblFechaVenta);
            this.lblComentariosVenta = v.findViewById(R.id.csi_lblComentariosVenta);

        }

    }

}