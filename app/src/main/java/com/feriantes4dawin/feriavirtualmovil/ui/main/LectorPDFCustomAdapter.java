package com.feriantes4dawin.feriavirtualmovil.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feriantes4dawin.feriavirtualmovil.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class LectorPDFCustomAdapter extends RecyclerView.Adapter<LectorPDFCustomAdapter.LectorPDFViewHolder> {

    private ArrayList<String> listaProductos;
    private PdfRenderer lectorPDF;
    private ParcelFileDescriptor archivoPDF;
    private Context c;
    private PdfRenderer.Page paginaActiva;

    public LectorPDFCustomAdapter(ParcelFileDescriptor archivoPDF) throws IOException {

        super();
        this.archivoPDF = archivoPDF;
        this.lectorPDF = new PdfRenderer(archivoPDF);
        this.paginaActiva = null;

    }

    @Override
    public LectorPDFCustomAdapter.LectorPDFViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creo el widget del elemento de lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pagina_lector,parent,false);
        this.c = parent.getContext();
        LectorPDFCustomAdapter.LectorPDFViewHolder vh = new LectorPDFCustomAdapter.LectorPDFViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(LectorPDFCustomAdapter.LectorPDFViewHolder holder, int position) {

        //Me aseguro que el index indicado no se pase de los límites del documento pdf
        if(lectorPDF.getPageCount() <= position){

            holder.ivPagina.setImageDrawable( c.getDrawable(R.drawable.logo_feriavirtual_compacto) );

        } else {

            //Cerramos hoja actual por si las moscas! (Si no hago esto, la app se muere!!)
            if(paginaActiva != null){
                paginaActiva.close();
            }

            paginaActiva = lectorPDF.openPage(position);
            Bitmap imagenFinal = Bitmap.createBitmap(paginaActiva.getWidth(),paginaActiva.getHeight(), Bitmap.Config.ARGB_8888);
            paginaActiva.render(imagenFinal,null,null,PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            holder.ivPagina.setImageBitmap(imagenFinal);


        }

    }

    @Override
    public int getItemCount(){
        return lectorPDF.getPageCount();
    }

    public class LectorPDFViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPagina;

        LectorPDFViewHolder(View v) {

            super(v);
            this.ivPagina = v.findViewById(R.id.ivPagina);

        }

    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        try{

            lectorPDF.close();
            archivoPDF.close();

        }catch(IOException ex){

            Log.e("LECTOR_PDF_ADAPTER","No se pudo cerrar el archivo o u paso algo raro!");

        }
    }
}