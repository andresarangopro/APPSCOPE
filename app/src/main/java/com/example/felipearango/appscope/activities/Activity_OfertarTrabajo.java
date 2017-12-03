package com.example.felipearango.appscope.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.Util;
import com.example.felipearango.appscope.models.Empresa;
import com.example.felipearango.appscope.models.Etiqueta;
import com.example.felipearango.appscope.models.RecyclerAdapterInfo;
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
    private Button btnIngresar, btnInfo;
    private  ArrayList<String> listEtiquetas;
   // private ArrayList<String> dataEtiquetas = new ArrayList<>();
    private ArrayList<Button> dataButtons = new ArrayList<>();
    private ArrayList<EditText> listEdit = new ArrayList<>();
    private ArrayList<String> dataEtiquetas = new ArrayList<>();
    private LinearLayout lLayoutEtiquetas;
    private RecyclerView rvEtiquetas;
    private RecyclerAddRemoveAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    RecyclerView recycler;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager lManager;
    private ArrayList<String> listEtiquetasFirebase = new ArrayList<>();
    private LinearLayout ll;
    ////////////////////////////
    //onCreate
    ///////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_oferta_trabajo, null, false);
        mDrawer.addView(contentView, 0);

        initiComponents();
        initializedDR();

        getEtiquetas();
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
        btnInfo = (Button) findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(this);
        btnIngresar.setOnClickListener(this);
        lLayoutEtiquetas = (LinearLayout) findViewById(R.id.lLayoutEtiquetas);
        txtEtiquetaRU = (EditText) findViewById(R.id.txtEtiquetasRU);
        txtEtiquetaRU.setHint("Etiquetas");
        btnAddLabelR = (Button) findViewById(R.id.btnAddLabelR);
        ll = (LinearLayout)findViewById(R.id.llOfertar);
        btnAddLabelR.setOnClickListener(this);

        listEdit.add(titulo);
        listEdit.add(detalles);

        mLinearLayoutManager = new LinearLayoutManager(this);
        rvEtiquetas = (RecyclerView)findViewById(R.id.rv_add);
        rvEtiquetas.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerAddRemoveAdapter(this, dataEtiquetas);
        rvEtiquetas.setAdapter(mAdapter);
    }

    private ArrayList<String> listEtToST(ArrayList<EditText> etiqu){
        ArrayList<String> listEqtiquetas = new ArrayList<>();
        for (int i = 0; i < etiqu.size() ; i++) {
            listEqtiquetas.add(etiqu.get(i).getText().toString());
        }
        return  listEqtiquetas;
    }

    public void getEtiquetas(){

        databaseReference.child("Etiqueta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //listUsers.clear();
                if(listEtiquetasFirebase.size() >= 1){
                    listEtiquetasFirebase.clear();
                }
                for (DataSnapshot etiqueta:dataSnapshot.getChildren()) {
                    String eti = etiqueta.getKey();

                    listEtiquetasFirebase.add(eti);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addJob(String titulo, String desc, ArrayList<String> etiquetas, Object usr){

        String idJob =  databaseReference.push().getKey();
        Etiqueta et ;
        Trabajo t;

        listEtiquetas = etiquetas;
        if(usr instanceof Empresa){
            t = new Trabajo(idJob,0,titulo,desc,"","", ((Empresa) usr).getId(),((Empresa) usr).getNombre() );
        }else{
            t = new Trabajo(idJob,0,titulo,desc,"","",
                    ((UsuarioCorriente) usr).getId(),((UsuarioCorriente) usr).getNombre()
                    +" "+((UsuarioCorriente) usr).getApellido() );
        }
        insertarJobFB(t);
        if(usr instanceof Empresa){
            for (int i = 0; i < listEtiquetas.size() ; i++) {
                Log.e("Etiquetas",listEtiquetas.get(i)+""+listEtiquetas.size());
                String etiqueta = listEtiquetas.get(i);
                et = new Etiqueta(etiqueta,
                        ((Empresa) usr).getId(), t.getId());
                insertarEtiqFB(et);
            }
        }else{
            for (int i = 0; i < listEtiquetas.size() ; i++) {
                Log.e("Etiquetas",listEtiquetas.get(i)+""+listEtiquetas.size());
                String etiqueta = Util.parametrizacionEtiqueta(listEtiquetas.get(i));
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
                      if(dataEtiquetas.size() >= 1){
                          eventPD("CorrientsUsers");
                      }else{
                          txtEtiquetaRU.setError("Ingrese almenos una etiquetas");
                      }
                }
                break;
            }
            case R.id.btnAddLabelR:{
                    if(!txtEtiquetaRU.getText().toString().equals("")){
                        String etPara = Util.parametrizacionEtiqueta(getTxtEdit(txtEtiquetaRU));
                        addToEtiquetas(etPara);
                        txtEtiquetaRU.setText("");
                    } else{
                        txtEtiquetaRU.setError("Campo vacío");
                    }
                break;
            }
            case R.id.btnInfo:{
                    showPopUp();
                break;
            }

            default:{}

        }
    }

    private void addToEtiquetas(String lbl){
        int position = 0;
        dataEtiquetas.add(lbl);
        mAdapter.notifyItemInserted(position);
        mAdapter.notifyDataSetChanged();
        rvEtiquetas.scrollToPosition(position);
        Toast.makeText(this, "Etiqueta Agregada" +dataEtiquetas, Toast.LENGTH_SHORT).show();
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

    private void eventPD(String usChildString){
        databaseReference.child(usChildString).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(TIPO_USUARIO == 0){
                    Log.e("TR", dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class).getNombre() + user.getUid());
                    UsuarioCorriente uC =  dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class);
                    addJob(titulo.getText().toString(), detalles.getText().toString(), dataEtiquetas, uC);
                        vaciasCampos();
                }else {
                    if(!titulo.getText().toString().equals("")){

                        Empresa uE = dataSnapshot.child(user.getUid()).getValue(Empresa.class);
                        Log.e("TR", dataEtiquetas.size()+"");
                        addJob(titulo.getText().toString(), detalles.getText().toString(),dataEtiquetas, uE);
                        vaciasCampos();
                    }else{
                       // Toast.makeText(Activity_OfertarTrabajo.this,"No entro",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void vaciasCampos(){
        for (int i = 0; i < listEdit.size() ; i++) {
            listEdit.get(i).setText("");
            dataEtiquetas = new ArrayList<>();
            mAdapter = new RecyclerAddRemoveAdapter(this, dataEtiquetas);
            rvEtiquetas.setAdapter(mAdapter);
        }
    }

    public void showPopUp() {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_info, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // Obtener el Recycler
        recycler = (RecyclerView) popupView.findViewById(R.id.rv_Info);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new RecyclerAdapterInfo(this,listEtiquetasFirebase,dataEtiquetas,mAdapter,rvEtiquetas,txtEtiquetaRU);
        recycler.setAdapter(adapter);


        //////////////////////////////////////////////////////////////
        ////Esto muestra el pop Up window
        ////////////////////////////////////////////////////////////

        popupWindow.showAtLocation(ll, Gravity.CENTER, 0, 0);
        //////////////////////////////////////////////////
        ////////Listener que oculta el pop Up
        ////////////////////////////////////////////////
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

}