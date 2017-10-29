package com.example.felipearango.appscope;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.felipearango.appscope.Login.TIPO_USUARIO;

public class OfertaTrabajo extends MainActivity implements View.OnClickListener {

    private EditText titulo, detalles, etiquetas;
    private Button btnIngresar;
    private  ArrayList<String> listEtiquetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_oferta_trabajo, null, false);
        mDrawer.addView(contentView, 0);
        iniciar();
        getUser();
       // inicializatedFireBase();
    }

    private void getUser(){
        titulo = (EditText)findViewById(R.id.tvTitulo);
        detalles = (EditText)findViewById(R.id.tvDetalles);
        etiquetas = (EditText)findViewById(R.id.tvEtiqueta);
        btnIngresar = (Button)findViewById(R.id.btnAgregar);
        btnIngresar.setOnClickListener(this);
    }

    private void iniciar(){
        initializedDR();
    }

    private void addJob(String titulo, String desc, String etiquetas, Object usr){

        String idJob =  databaseReference.push().getKey();
        Etiqueta et ;
        Trabajo t;
        listEtiquetas = new ArrayList<String>(Arrays.asList(etiquetas.split(",")));

        if(usr instanceof Empresa){
            t = new Trabajo(idJob,0,titulo,desc,"","", ((Empresa) usr).getId());
        }else{
            t = new Trabajo(idJob,0,titulo,desc,"","", ((UsuarioCorriente) usr).getId());
        }
        insertarJobFB(t);
        if(usr instanceof Empresa){
            for (int i = 0; i < listEtiquetas.size() ; i++) {
                Log.e("Etiquetas",listEtiquetas.get(i)+""+listEtiquetas.size());
                et = new Etiqueta(parametrizacionEtiqueta(listEtiquetas.get(i)),
                        ((Empresa) usr).getId(), t.getId());
                insertarEtiqFB(et);
            }
        }else{
            for (int i = 0; i < listEtiquetas.size() ; i++) {
                Log.e("Etiquetas",listEtiquetas.get(i)+""+listEtiquetas.size());
                et = new Etiqueta(parametrizacionEtiqueta(listEtiquetas.get(i)),
                        ((UsuarioCorriente) usr).getId(), t.getId());
                insertarEtiqFB(et);
            }
        }



    }

    private void initializedDR() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View view) {
        if(btnIngresar == view){
           eventPDUA("EmpresaUsers");
        }

    }

    private void insertarJobFB(Trabajo job){
        databaseReference.child("Jobs").child(job.getId()).setValue(job);
    }

    private void insertarEtiqFB(Etiqueta etiqueta){
        databaseReference.child("Etiqueta").child(etiqueta.getNombreEtiqueta())
                .child(etiqueta.getIdEmpresa()).child(etiqueta.getIdTrabajo()).setValue(etiqueta);
    }

    public void eventPDUA(String usChildString){
        databaseReference.child(usChildString).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(TIPO_USUARIO == 0){
                    UsuarioCorriente uC =  dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class);
                    addJob(titulo.getText().toString(), detalles.getText().toString(), etiquetas.getText().toString(), uC);
                }else {

                    Empresa uE = dataSnapshot.child(user.getUid()).getValue(Empresa.class);
                    addJob(titulo.getText().toString(), detalles.getText().toString(), etiquetas.getText().toString(), uE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String parametrizacionEtiqueta(String etiqueta){
        String sSubCadena = etiqueta;
        String fisrtLetter = etiqueta;

        fisrtLetter = fisrtLetter.substring(0,1);
        fisrtLetter = fisrtLetter.toUpperCase();
        sSubCadena = sSubCadena.substring(1,sSubCadena.length());
        sSubCadena= sSubCadena.toLowerCase();

        return fisrtLetter+sSubCadena;
    }

}