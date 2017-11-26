package com.example.felipearango.appscope.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.felipearango.appscope.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian Luna R on 11/25/2017.
 */

public class RecyclerAddRemoveAdapter extends RecyclerView.Adapter<RecyclerAddRemoveAdapter.ViewHolder> {

    ArrayList<String> text;
    Context mContext;

    public RecyclerAddRemoveAdapter(Context context, ArrayList<String> hint) {
        this.text = hint;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public EditText etData;
        public Button btnAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            etData = (EditText) itemView.findViewById(R.id.txtEtiquetasRU1);
            btnAdd = (Button) itemView.findViewById(R.id.btnAddLabelR1);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_add_remove, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.etData.setText(text.get(position));
        holder.etData.setEnabled(false);
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,text.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return text.size();
    }
}
