package com.example.felipearango.appscope.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.RecyclerAdapterValidarCuenta;
import com.example.felipearango.appscope.models.ValidarCuenta;

import java.util.ArrayList;

public class Activity_Admin extends MainActivity {

    private RecyclerAdapterValidarCuenta mAdapter;
    private RecyclerView mRecyclerAccounts;
    private ArrayList<ValidarCuenta> mValidarCuenta = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_admin, null, false);
        mDrawer.addView(contentView, 0);

        ValidarCuenta va1 = new ValidarCuenta("Empresa 1", "22/02/02");
        ValidarCuenta va2 = new ValidarCuenta("Empresa 2", "22/01/04");
        ValidarCuenta va3 = new ValidarCuenta("Empresa 3", "22/07/29");

        mValidarCuenta.add(va1);
        mValidarCuenta.add(va2);
        mValidarCuenta.add(va3);

        iniciar();
    }

    private void iniciar(){
        mRecyclerAccounts = (RecyclerView) findViewById(R.id.rv_Empresas);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerAccounts.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerAdapterValidarCuenta(mValidarCuenta);
        mRecyclerAccounts.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerAccounts.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerAccounts.addItemDecoration(dividerItemDecoration);
    }

}
