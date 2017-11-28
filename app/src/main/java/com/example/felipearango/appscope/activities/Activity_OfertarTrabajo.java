package com.example.felipearango.appscope.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.Empresa;
import com.example.felipearango.appscope.models.Etiqueta;
import com.example.felipearango.appscope.models.RecyclerAddRemoveAdapter;
import com.example.felipearango.appscope.models.Trabajo;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;

public class Activity_OfertarTrabajo extends MainActivity implements View.OnClickListener {

    ////////////////////////////
    //Variables
    ///////////////////////////

    private EditText titulo, detalles, txtEtiquetaRU;
    private Button btnAddLabelR;
    private Button btnIngresar;
    private  ArrayList<String> listEtiquetas;
    private ArrayList<EditText> dataEtiquetas = new ArrayList<>();
    private ArrayList<Button> dataButtons = new ArrayList<>();
    private ArrayList<EditText> listEdit = new ArrayList<>();
    private ArrayList<String> dataEtiquetasS = new ArrayList<>();
    private LinearLayout lLayoutEtiquetas;
    private RecyclerView rvEtiquetas;
    private RecyclerAddRemoveAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;


    ////////////////////////////
    //onCreate
    ///////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_oferta_trabajo, null, false);
        mDrawer.addView(contentView, 0);
        iniciar();
        initiComponents();
       // inicializatedFireBase();
    }


    ////////////////////////////
    //Metodos
    ///////////////////////////

    private void initiComponents(){
        titulo = (EditText)findViewById(R.id.tvTitulo);
        detalles = (EditText)findViewById(R.id.tvDetalles);
        //etiquetas = (EditText)findViewById(R.id.tvEtiqueta);
        btnIngresar = (Button)findViewById(R.id.btnAgregar);
        btnIngresar.setOnClickListener(this);
        lLayoutEtiquetas = (LinearLayout) findViewById(R.id.lLayoutEtiquetas);
        txtEtiquetaRU = (EditText) findViewById(R.id.txtEtiquetasRU);
        txtEtiquetaRU.setHint("Etiquetas");
        btnAddLabelR = (Button) findViewById(R.id.btnAddLabelR);

        btnAddLabelR.setOnClickListener(this);

        listEdit.add(titulo);
        listEdit.add(detalles);

        mLinearLayoutManager = new LinearLayoutManager(this);
        rvEtiquetas = (RecyclerView)findViewById(R.id.rv_add);
        rvEtiquetas.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerAddRemoveAdapter(this, dataEtiquetasS);
        rvEtiquetas.setAdapter(mAdapter);
    }


    private void iniciar(){
        initializedDR();
    }

    private ArrayList<String> listEtToST(ArrayList<EditText> etiqu){
        ArrayList<String> listEqtiquetas = new ArrayList<>();
        for (int i = 0; i < etiqu.size() ; i++) {
            listEqtiquetas.add(etiqu.get(i).getText().toString());
        }
        return  listEqtiquetas;
    }
    private void addJob(String titulo, String desc, ArrayList<EditText> etiquetas, Object usr){

        String idJob =  databaseReference.push().getKey();
        Etiqueta et ;
        Trabajo t;

        listEtiquetas = listEtToST(etiquetas);

        if(usr instanceof Empresa){
            t = new Trabajo(idJob,0,titulo,desc,"","", ((Empresa) usr).getId());
        }else{
            t = new Trabajo(idJob,0,titulo,desc,"","", ((UsuarioCorriente) usr).getId());
        }
        insertarJobFB(t);
        if(usr instanceof Empresa){
            for (int i = 0; i < listEtiquetas.size() ; i++) {
                Log.e("Etiquetas",listEtiquetas.get(i)+""+listEtiquetas.size());
                String etiqueta = parametrizacionEtiqueta(listEtiquetas.get(i));
                et = new Etiqueta(etiqueta,
                        ((Empresa) usr).getId(), t.getId());
                insertarEtiqFB(et);
            }
        }else{


            for (int i = 0; i < listEtiquetas.size() ; i++) {
                Log.e("Etiquetas",listEtiquetas.get(i)+""+listEtiquetas.size());
                String etiqueta = parametrizacionEtiqueta(listEtiquetas.get(i));
                et = new Etiqueta(etiqueta,
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
        int vista = view.getId();

        switch (vista){
            case R.id.btnAgregar:{
                if(camposVacios()){
                    if(TIPO_USUARIO == 0){
                        eventPDUA("CorrientsUsers");
                    }else{
                        eventPDUA("EmpresaUsers");
                    }
                }
                break;
            }
            case R.id.btnAddLabelR:{
                    if(!txtEtiquetaRU.getText().toString().equals("")){
                        addToEtiquetas(getTxtEdit(txtEtiquetaRU));
                        txtEtiquetaRU.setText("");
                    } else{
                        txtEtiquetaRU.setError("Campo vacío");
                    }
                break;
            }

            default:{}

        }
    }

    private void addToEtiquetas(String lbl){
        int position = 0;
        dataEtiquetasS.add(position,lbl);
        mAdapter.notifyItemInserted(position);
        mAdapter.notifyDataSetChanged();
        rvEtiquetas.scrollToPosition(position);
        Toast.makeText(this, "Etiqueta Agregada", Toast.LENGTH_SHORT).show();
    }


    private boolean camposVacios(){
        boolean valido = true;
        for (int i = 0; i < listEdit.size() ; i++) {
            if(listEdit.get(i).getText().toString().equals("")){
                listEdit.get(i).setError("Ingrese este campo por favor");
                valido = false;
            }
        }
        return valido;
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
                    Log.e("TR", dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class).getNombre() + user.getUid());
                    UsuarioCorriente uC =  dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class);
                    addJob(titulo.getText().toString(), detalles.getText().toString(), dataEtiquetas, uC);
                }else {

                    Empresa uE = dataSnapshot.child(user.getUid()).getValue(Empresa.class);
                    addJob(titulo.getText().toString(), detalles.getText().toString(),dataEtiquetas, uE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String parametrizacionEtiqueta(String etiqueta){
        etiqueta = etiqueta.trim();
        String sSubCadena = etiqueta;
        String fisrtLetter = etiqueta;

        fisrtLetter = fisrtLetter.substring(0,1);
        fisrtLetter = fisrtLetter.toUpperCase();
        sSubCadena = sSubCadena.substring(1,sSubCadena.length());
        sSubCadena= sSubCadena.toLowerCase();

        Toast.makeText(this, fisrtLetter+sSubCadena , Toast.LENGTH_LONG).show();
        return fisrtLetter+sSubCadena;
    }

}