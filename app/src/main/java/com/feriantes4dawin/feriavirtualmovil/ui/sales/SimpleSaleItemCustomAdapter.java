package com.feriantes4dawin.feriavirtualmovil.ui.sales;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.VentaSimple;
import com.feriantes4dawin.feriavirtualmovil.data.models.VentasSimples;
import com.feriantes4dawin.feriavirtualmovil.ui.util.FeriaVirtualConstants;
import com.google.gson.Gson;

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

    private Gson convertidorJSON;

    private Context context;

    /**
     * Constructor que crea un objeto SimpleSaleItemCustomAdapter. 
     * 
     * @param ventasSimples Objeto VentasSimples usado como origen de 
     * datos.
     */
    public SimpleSaleItemCustomAdapter(VentasSimples ventasSimples, Gson convertidorJSON){

        super();
        this.ventasSimples = ventasSimples;
        this.convertidorJSON = convertidorJSON;

    }

    @Override
    public SimpleSaleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sale_item,parent,false);
        SimpleSaleItemViewHolder vh = new SimpleSaleItemViewHolder(view);

        this.context = parent.getContext();

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

            String ventaString = convertidorJSON.toJson(vh.ventaSimple);

            Intent i = new Intent(parent.getContext(),SaleDetailActivity.class);
            sp.edit()
                    .putInt(FeriaVirtualConstants.SP_VENTA_ID,vh.id_venta)
                    .putString(FeriaVirtualConstants.SP_VENTA_OBJ_STR,ventaString)
                    .commit();

            parent.getContext().startActivity(i);
        });



        return vh;
    }

    @Override
    public void onBindViewHolder(SimpleSaleItemViewHolder holder, int position) {

        try {

            VentaSimple vs = ventasSimples.ventas.get(position);
            holder.ventaSimple = vs;
            holder.id_venta = vs.id_venta;
            holder.lblCodigoVenta.setText(String.format("%s N° %d",context.getString(R.string.title_sale_process),vs.id_venta));
            holder.lblFechaInicioVenta.setText(vs.fecha_inicio_venta);
            holder.lblFechaFinVenta.setText(vs.fecha_fin_venta);
            holder.lblEstadoVenta.setText(vs.estado_venta.descripcion);
            holder.lblComentariosVenta.setText(vs.comentarios_venta);

        } catch(Exception ex) {

            Log.e("SALE_ITEM_CUSTOMADAPTER",String.format("Paso algo raro!: %s",ex.toString()));

            holder.id_venta = 0;

            holder.lblCodigoVenta.setText(R.string.err_mes_not_avalaible);
            holder.lblFechaInicioVenta.setText(R.string.err_mes_not_avalaible);
            holder.lblFechaFinVenta.setText(R.string.err_mes_not_avalaible);
            holder.lblEstadoVenta.setText(R.string.err_mes_not_avalaible);
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

        public VentaSimple ventaSimple;
        public Integer id_venta;
        public TextView lblCodigoVenta;
        public TextView lblFechaInicioVenta;
        public TextView lblFechaFinVenta;
        public TextView lblEstadoVenta;
        public TextView lblComentariosVenta;

        public SimpleSaleItemViewHolder(View v) {

            super(v);
            this.ventaSimple = null;
            this.lblCodigoVenta = v.findViewById(R.id.csi_lblCodigoVenta);
            this.lblFechaInicioVenta = v.findViewById(R.id.csi_lblFechaInicioVenta);
            this.lblFechaFinVenta = v.findViewById(R.id.csi_lblFechaFinVenta);
            this.lblEstadoVenta = v.findViewById(R.id.csi_lblEstadoVenta);
            this.lblComentariosVenta = v.findViewById(R.id.csi_lblComentariosVenta);

        }

    }

}