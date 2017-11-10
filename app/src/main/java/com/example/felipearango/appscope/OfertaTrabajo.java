package com.example.felipearango.appscope;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.felipearango.appscope.Login.TIPO_USUARIO;

public class OfertaTrabajo extends MainActivity implements View.OnClickListener {

    ////////////////////////////
    //Variables
    ///////////////////////////

    private EditText titulo, detalles, txtEtiquetaRU;
    private Button btnIngresar;
    private ImageButton btnAddLabelR;
    private  ArrayList<String> listEtiquetas;
    private ArrayList<EditText> dataEtiquetas = new ArrayList<>();
    private ArrayList<Button> dataButtons = new ArrayList<>();
    private ArrayList<EditText> listEdit = new ArrayList<>();
    private LinearLayout lLayoutEtiquetas;


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
        btnAddLabelR = (ImageButton) findViewById(R.id.btnAddLabelR);
        btnAddLabelR.setOnClickListener(this);
        listEdit.add(titulo);
        listEdit.add(detalles);
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
        LinearLayout llrow = new LinearLayout(this);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0, 1);
        llrow.setWeightSum(1f);
        llrow.setOrientation(LinearLayout.HORIZONTAL);
        llrow.setLayoutParams(llParams);
        // LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //  p.weight = 0.5f;
        EditText nET = new EditText(this);
        nET.setEnabled(false);
        nET.setText(parametrizacionEtiqueta(lbl));
        nET.setLayoutParams(new TableLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT, 1f));
        //nET.setLayoutParams(p);
        llrow.addView(nET);
        dataEtiquetas.add(nET);
        Button btn = new Button(this);
        btn.setOnClickListener(this);
        //  p.weight = 0.8f;
        //  btn.setLayoutParams(p);
        //btn.setText("----");
        // btn.setTextSize(12);
        // btn.setBackgroundResource(R.drawable.ic_menos);
        llrow.addView(btn);
        dataButtons.add(btn);
        lLayoutEtiquetas.setWeightSum(lLayoutEtiquetas.getWeightSum()+1);
        lLayoutEtiquetas.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,
                lLayoutEtiquetas.getWeightSum()+1));
        lLayoutEtiquetas.addView(llrow);
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