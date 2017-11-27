package com.example.felipearango.appscope.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;

import com.example.felipearango.appscope.R;

import java.util.ArrayList;

/**
 * Created by Sebastian Luna R on 11/27/2017.
 */

public class RecyclerShowInfo extends RecyclerView.Adapter<RecyclerShowInfo.ViewHolder>  {

    ArrayList<String> text;
    Context mContext;

    public RecyclerShowInfo(Context context, ArrayList<String> hint) {
        this.text = hint;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public EditText etData;


        public ViewHolder(View itemView) {
            super(itemView);
            etData = (EditText) itemView.findViewById(R.id.txtEtiquetasRU1);

        }
    }

    public RecyclerShowInfo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.rv_showinfo, parent,false);
        RecyclerShowInfo.ViewHolder vh = new RecyclerShowInfo.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerShowInfo.ViewHolder holder, final int position) {
        holder.etData.setText(text.get(position));
        holder.etData.setEnabled(false);

    }

    @Override
    public int getItemCount() {
        return text.size();
    }

}
