package com.example.felipearango.appscope.models;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.Util;

import java.util.ArrayList;

/**
 * Created by Felipe Arango on 2/12/2017.
 */

public class RecyclerAdapterInfo extends RecyclerView.Adapter<RecyclerAdapterInfo.RecyclerAdapterInfoHolder>{
    private ArrayList<String> listEtiquetas;
    private Context mContext;
    private ArrayList<String> dataEtiquetas;
    private RecyclerAddRemoveAdapter mAdapter;
    private RecyclerView rvEtiquetas;
    private EditText et_doc;




    public static class RecyclerAdapterInfoHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        // public ImageView imagen;
        public TextView nombre;
        private View thisView;
        //public TextView visitas;

        public RecyclerAdapterInfoHolder(View v) {
            super(v);
            nombre = (TextView) itemView.findViewById(R.id.tvEtiqueta);
            thisView = itemView;
        }
    }

    public RecyclerAdapterInfo(Context context, ArrayList<String> etiquetas,ArrayList<String> dataE,
                               RecyclerAddRemoveAdapter map, RecyclerView rvE,EditText et_d){

        this.listEtiquetas = etiquetas;
        mContext = context;
        dataEtiquetas = dataE;
        mAdapter = map;
        rvEtiquetas = rvE;
        et_doc = et_d;

    }

    @Override
    public RecyclerAdapterInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_info, parent, false);
        return new RecyclerAdapterInfoHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterInfoHolder holder, final int position) {
        holder.nombre.setText(listEtiquetas.get(position));

        holder.thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.agregarEtiquetaS(listEtiquetas.get(position),dataEtiquetas,mAdapter,rvEtiquetas,et_doc);
                Toast.makeText(mContext,"Etiqueta agregada",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listEtiquetas.size();
    }



}
