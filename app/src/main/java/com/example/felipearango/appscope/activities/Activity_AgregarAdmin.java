package com.example.felipearango.appscope.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.felipearango.appscope.R;

public class Activity_AgregarAdmin extends MainActivity implements View.OnClickListener{

    private EditText etNombre, etEmailAgregar, etContraseña, etConfirmarContraseña, etEmailEliminar;
    private Button btnAddadmin, btneliminar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_agregar_admin, null, false);
        mDrawer.addView(contentView, 0);
        initComponents();
    }

    private void initComponents(){
        etNombre = (EditText) findViewById(R.id.etNombre);
        etEmailAgregar  = (EditText) findViewById(R.id.etEmailAgregar);
        etContraseña  = (EditText) findViewById(R.id.etContraseña);
        etConfirmarContraseña  = (EditText) findViewById(R.id.etContraseña);
        etEmailEliminar  = (EditText) findViewById(R.id.etEmailEliminar);

        btnAddadmin = (Button) findViewById(R.id.btnAddadmin);
        btneliminar = (Button) findViewById(R.id.btneliminar);

        btnAddadmin.setOnClickListener(this);
        btneliminar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vista = v.getId();
        switch (vista){
            case R.id.btnAddadmin:{
                break;
            }
            case R.id.btneliminar:{
                break;
            }
        }
    }


}
