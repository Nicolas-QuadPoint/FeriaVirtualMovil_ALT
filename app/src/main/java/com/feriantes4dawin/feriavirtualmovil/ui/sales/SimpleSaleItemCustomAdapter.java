package com.feriantes4dawin.feriavirtualmovil.ui.sales;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.feriantes4dawin.feriavirtualmovil.R;
import com.feriantes4dawin.feriavirtualmovil.data.models.Usuario;
import com.feriantes4dawin.feriavirtualmovil.data.models.Venta;
import com.feriantes4dawin.feriavirtualmovil.data.models.Ventas;
import com.feriantes4dawin.feriavirtualmovil.util.FeriaVirtualConstants;
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
     * @see {@link Ventas}
     */
    private Ventas ventasSimples;

    private Gson convertidorJSON;

    private AppCompatActivity activity;

    private boolean modoSoloLectura;

    /**
     * Constructor que crea un objeto SimpleSaleItemCustomAdapter. 
     * 
     * @param ventasSimples Objeto Ventas usado como origen de
     * datos.
     * @param convertidorJSON Objeto Gson para procesar ciertos
     *                        datos.
     */
    public SimpleSaleItemCustomAdapter(AppCompatActivity activity,Ventas ventasSimples, Gson convertidorJSON){

        super();
        this.ventasSimples = ventasSimples;
        this.convertidorJSON = convertidorJSON;
        this.modoSoloLectura = false;
        this.activity = activity;
    }

    /**
     * Constructor que crea un objeto SimpleSaleItemCustomAdapter, con la
     * posibilidad de establecer el modo de lectura de esta lista.
     *
     * @param ventasSimples Objeto Ventas usado como origen de
     * datos.
     * @param convertidorJSON Objeto Gson para procesar ciertos datos
     * @param modoSoloLectura Permite la no modificacion de los datos de
     *                        esta lista.
     */
    public SimpleSaleItemCustomAdapter(AppCompatActivity activity, Ventas ventasSimples, Gson convertidorJSON, boolean modoSoloLectura){

        super();
        this.ventasSimples = ventasSimples;
        this.convertidorJSON = convertidorJSON;
        this.modoSoloLectura = modoSoloLectura;
        this.activity = activity;
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

            String ventaString = convertidorJSON.toJson(vh.venta);
            Usuario usuario = convertidorJSON.fromJson(sp.getString(FeriaVirtualConstants.SP_USUARIO_OBJ_STR,""), Usuario.class);

            Intent i = new Intent(parent.getContext(),SaleDetailActivity.class);
            sp.edit()
                    .putInt(FeriaVirtualConstants.SP_VENTA_ID,vh.venta.id_venta)
                    .putString(FeriaVirtualConstants.SP_VENTA_OBJ_STR,ventaString)
                    .commit();

            //Se obliga acceder al modo edicion (solo si es explicitado!)
            i.putExtra(FeriaVirtualConstants.MODO_SOLO_LECTURA,modoSoloLectura);

            activity.startActivityForResult(i,0,null);
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(SimpleSaleItemViewHolder holder, int position) {

        try {

            Venta vs = ventasSimples.ventas.get(position);
            holder.venta = vs;
            holder.lblCodigoVenta.setText(String.format("%s N° %d",activity.getString(R.string.title_sale_process),vs.id_venta));
            holder.lblFechaInicioVenta.setText(vs.fecha_inicio_venta);
            holder.lblFechaFinVenta.setText(vs.fecha_fin_venta);
            holder.lblEstadoVenta.setText(vs.estado_venta.descripcion);
            holder.lblComentariosVenta.setText(vs.comentarios_venta);

        } catch(Exception ex) {

            Log.e("SALE_ITEM_CUSTOMADAPTER",String.format("Paso algo raro!: %s",ex.toString()));

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

        public Venta venta;
        public TextView lblCodigoVenta;
        public TextView lblFechaInicioVenta;
        public TextView lblFechaFinVenta;
        public TextView lblEstadoVenta;
        public TextView lblComentariosVenta;

        public SimpleSaleItemViewHolder(View v) {

            super(v);
            this.lblCodigoVenta = v.findViewById(R.id.csi_lblCodigoVenta);
            this.lblFechaInicioVenta = v.findViewById(R.id.csi_lblFechaInicioVenta);
            this.lblFechaFinVenta = v.findViewById(R.id.csi_lblFechaFinVenta);
            this.lblEstadoVenta = v.findViewById(R.id.csi_lblEstadoVenta);
            this.lblComentariosVenta = v.findViewById(R.id.csi_lblComentariosVenta);

        }

    }

}