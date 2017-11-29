package com.example.felipearango.appscope.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.Empresa;
import com.example.felipearango.appscope.models.RecyclerAddRemoveAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.felipearango.appscope.Util.Util.cuenta_espera_certif;
import static com.example.felipearango.appscope.Util.Util.usuario_empresa;
import static com.example.felipearango.appscope.activities.Activity_ScreenRegisterUC.ESTADO_ACTIVA;

public class Activity_ScreenRegisterE extends AppCompatActivity implements View.OnClickListener {

    private EditText txtnameE,txtRazonSoc, txtNIT, txtUrl, txtSocial1;
    private Button btnRegisterE, btnAgregarNIT, btnAgregarRedSocial1;
    private EditText txtEtiquetaRU;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ArrayList<String> dataEtiquetas = new ArrayList<>();

    private RecyclerView rvEtiquetas;
    private RecyclerAddRemoveAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private int id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_register_e);
        instanceXml();
        firebaseAuth = FirebaseAuth.getInstance();
        initializedDR();
    }

    ////////////////////////////////
    //Metodos
    ////////////////////////////////

    private void initializedDR() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void instanceXml(){
        txtnameE = (EditText) findViewById(R.id.txtNameE);
        txtRazonSoc = (EditText) findViewById(R.id.txtRazonSoc);
        txtNIT = (EditText) findViewById(R.id.txtNIT);
        txtUrl = (EditText) findViewById(R.id.txtUrl);
        //linearLayout = (LinearLayout) findViewById(R.id.llLayout);

        btnRegisterE = (Button) findViewById(R.id.btnRegisterE);
        btnRegisterE.setOnClickListener(this);

        btnAgregarRedSocial1 = (Button) findViewById(R.id.btnAddLabelR);
        btnAgregarRedSocial1.setOnClickListener(this);


        btnAgregarNIT = (Button) findViewById(R.id.btnAgregarNit);
        btnAgregarNIT.setOnClickListener(this);


        txtEtiquetaRU = (EditText) findViewById(R.id.txtEtiquetasRU);
        txtEtiquetaRU.setHint("Redes sociales");

        mLinearLayoutManager = new LinearLayoutManager(this);
        rvEtiquetas = (RecyclerView)findViewById(R.id.rv_add);
        rvEtiquetas.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerAddRemoveAdapter(this, dataEtiquetas);
        rvEtiquetas.setAdapter(mAdapter);
    }



    private void takeDates(){
        String id = "";
        String nombre = getTxtEdit(txtnameE);
        String razonSocial = getTxtEdit(txtRazonSoc);
        String urlEmpresa = getTxtEdit(txtUrl);
        String nitEmpresa = getTxtEdit(txtNIT);
        String mail="";
        String foto = "";
        String redesSociales = "";
        float rating = 0;
        FirebaseUser user = firebaseAuth.getCurrentUser();
        id = user.getUid();
        mail = user.getEmail();
        Empresa uE = new Empresa(id, nombre, razonSocial, urlEmpresa,nitEmpresa,
                mail,ESTADO_ACTIVA,foto,rating,usuario_empresa, cuenta_espera_certif,redesSociales);
        if(uE != null){
            insertarUsEFireBase(uE,user);
        }
    }

    private boolean comprobarcampos(){
        return true;
    }

    private void insertarUsEFireBase(Empresa uE,FirebaseUser user){
        databaseReference.child("CorrientsUsers").child(user.getUid()).setValue(uE);
        finish();
        startActivity(new Intent(getApplicationContext(),Activity_Perfil.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAgregarNit:{
                break;
            }
            case R.id.btnRegisterE: {
                takeDates();
                break;
            }
            case R.id.btnAddLabelR: {

                if(!txtEtiquetaRU.getText().toString().equals("")){
                    addToEtiquetas(getTxtEdit(txtEtiquetaRU));
                    txtEtiquetaRU.setText("");
                } else{
                    txtEtiquetaRU.setError("Campo vacío");
                }
                break;
            }

        }
    }

    private void addToEtiquetas(String lbl){
        int position = 0;

        dataEtiquetas.add(position,lbl);
        mAdapter.notifyItemInserted(position);
        mAdapter.notifyDataSetChanged();
        rvEtiquetas.scrollToPosition(position);
        Toast.makeText(this, "Etiqueta Agregada", Toast.LENGTH_SHORT).show();

    }

    private String arrayEditTxtToStr(ArrayList<String> etiquetas){
        String etiq = "";
        for (int i = 0; i <etiquetas.size() ; i++) {
            if(i+1 >= etiquetas.size()){
                etiq += etiquetas.get(i);
            }else{
                etiq += etiquetas.get(i)+",";
            }
        }
        Toast.makeText(this,etiq,Toast.LENGTH_LONG).show();
        return etiq;
    }

    /**
     *
     * @param txt
     * @return
     */
    private String getTxtEdit(EditText txt){
        return txt.getText().toString();
    }
}
