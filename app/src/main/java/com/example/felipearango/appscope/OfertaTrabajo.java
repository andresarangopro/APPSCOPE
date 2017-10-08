package com.example.felipearango.appscope;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.felipearango.appscope.Login.TIPO_USUARIO;

public class OfertaTrabajo extends MainActivity implements View.OnClickListener {

    private EditText titulo, detalles, etiquetas;
    private Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_oferta_trabajo, null, false);
        mDrawer.addView(contentView, 0);
        iniciar();
        getUser();
    }

    private void getUser(){

        FirebaseUser user = firebaseAuth.getCurrentUser();

        titulo = (EditText)findViewById(R.id.tvTitulo);
        detalles = (EditText)findViewById(R.id.tvDetalles);
        etiquetas = (EditText)findViewById(R.id.tvEtiqueta);
        btnIngresar = (Button)findViewById(R.id.btnAgregar);
        btnIngresar.setOnClickListener(this);
    }

    private void iniciar(){
        initializedDR();
    }

    private void addJob(String nombre, String desc, String etiquetas, Object usr){

        ArrayList<String> ets = new ArrayList<>(Arrays.asList(etiquetas.split(",")));

        if(usr instanceof Empresa){
            Empresa emp = (Empresa) usr;
            addJobToET(ets, nombre, desc);
        }else{

        }
    }

    private void addJobToET(ArrayList<String> ets, String nombre, String desc){

        for (String et: ets) {
            nodeExist(et, nombre, desc);
        }
    }

    private void initializedDR() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void nodeExist(final String et, final String nombre, final String desc){
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Etiqueta ets = new Etiqueta("", nombre, desc);
                //Empresa empresa = new Empresa("AA","BB","","","","","","","");

                databaseReference.child("Etiquetas").child(ets.getNombreEtiqueta()).setValue(ets);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        eventPDUA("EmpresaUsers");

    }

    public void eventPDUA(String usChildString){
        databaseReference.child("EmpresaUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                Empresa uE =  dataSnapshot.child(user.getUid()).getValue(Empresa.class);

                addJob(titulo.getText().toString(), detalles.getText().toString(),etiquetas.getText().toString(), uE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
