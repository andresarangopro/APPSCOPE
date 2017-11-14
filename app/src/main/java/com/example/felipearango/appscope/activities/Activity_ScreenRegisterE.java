package com.example.felipearango.appscope.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.Empresa;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.felipearango.appscope.activities.Activity_ScreenRegisterUC.ESTADO_ACTIVA;

public class Activity_ScreenRegisterE extends AppCompatActivity implements View.OnClickListener {

    private EditText txtnameE,txtRazonSoc, txtNIT, txtUrl, txtSocial1;
    private Button btnRegisterE, btnAgregarNIT, btnAgregarRedSocial1;
    private EditText titulo, detalles, txtEtiquetaRU,txtEtiquetaRU1,txtEtiquetaRU2,txtEtiquetaRU3;
    private Button btnAddLabelR, btnAddLabelR1, btnAddLabelR2, btnAddLabelR3;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ArrayList<EditText> dataEtiquetas = new ArrayList<>();
    private ArrayList<Button> dataButtons = new ArrayList<>();
    private ArrayList<LinearLayout> dataLinear = new ArrayList<>();
    private LinearLayout llRedesSociales;
    private LinearLayout linearLayout;

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
        txtSocial1 = (EditText) findViewById(R.id.txtEtiquetasRU);
        llRedesSociales = (LinearLayout) findViewById(R.id.llRedesSociales);
        linearLayout = (LinearLayout) findViewById(R.id.llLayout);

        btnRegisterE = (Button) findViewById(R.id.btnRegisterE);
        btnRegisterE.setOnClickListener(this);

        btnAgregarRedSocial1 = (Button) findViewById(R.id.btnAddLabelR);
        btnAgregarRedSocial1.setOnClickListener(this);


        btnAgregarNIT = (Button) findViewById(R.id.btnAgregarNit);
        btnAgregarNIT.setOnClickListener(this);


        txtEtiquetaRU1 = (EditText) findViewById(R.id.txtEtiquetasRU1);
        btnAddLabelR1 = (Button) findViewById(R.id.btnAddLabelR1);
        txtEtiquetaRU2 = (EditText) findViewById(R.id.txtEtiquetasRU2);
        btnAddLabelR2 = (Button) findViewById(R.id.btnAddLabelR2);
        txtEtiquetaRU3 = (EditText) findViewById(R.id.txtEtiquetasRU3);
        btnAddLabelR3 = (Button) findViewById(R.id.btnAddLabelR3);
        btnAddLabelR.setOnClickListener(this);
        btnAddLabelR1.setOnClickListener(this);
        btnAddLabelR2.setOnClickListener(this);
        btnAddLabelR3.setOnClickListener(this);
        dataEtiquetas.add(txtEtiquetaRU1);
        dataEtiquetas.add(txtEtiquetaRU2);
        dataEtiquetas.add(txtEtiquetaRU3);
        setInvisible();

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
                mail,ESTADO_ACTIVA,foto,rating,redesSociales);
        if(uE != null){
            insertarUsEFireBase(uE,user);
        }
    }

    private boolean comprobarcampos(){
        return true;
    }

    private void insertarUsEFireBase(Empresa uE,FirebaseUser user){
        databaseReference.child("EmpresaUsers").child(user.getUid()).setValue(uE);
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
            case R.id.btnAddLabelR1:{
                txtEtiquetaRU1.setText("");
                txtEtiquetaRU1.setVisibility(View.GONE);
                break;
            }
            case R.id.btnAddLabelR2:{
                txtEtiquetaRU2.setText("");
                txtEtiquetaRU2.setVisibility(View.GONE);
                break;
            }
            case R.id.btnAddLabelR3:{
                txtEtiquetaRU3.setText("");
                txtEtiquetaRU3.setVisibility(View.GONE);
                break;
            }
        }
    }

    private void setInvisible(){
        txtEtiquetaRU1.setVisibility(View.GONE);
        txtEtiquetaRU2.setVisibility(View.GONE);
        txtEtiquetaRU3.setVisibility(View.GONE);
        btnAddLabelR1.setVisibility(View.GONE);
        btnAddLabelR2.setVisibility(View.GONE);
        btnAddLabelR3.setVisibility(View.GONE);
    }

    private void moveUp(int id){

        for (int i = 0; i < dataEtiquetas.size(); i++) {
            if(dataEtiquetas.get(i).getId() == id){
                EditText editText = dataEtiquetas.get(i);
                LinearLayout ll = dataLinear.get(i);

                String ultimo = dataEtiquetas.get(dataEtiquetas.size()-1).getText().toString();
                editText.setText(ultimo);


                linearLayout.removeView(dataLinear.get(dataLinear.size()-1));

                dataLinear.remove(ll);
                dataEtiquetas.remove(dataEtiquetas.size()-1);
                dataButtons.remove(dataButtons.size()-1);


                linearLayout.setWeightSum(linearLayout.getWeightSum()-1);
                break;
            }
        }
    }




    private void checkBtn(int id) {

        for (Button btn : dataButtons) {
            if(btn.getId() == id){
                moveUp(id-100);
                break;
            }
        }
    }

    private void addToEtiquetas(String lbl){
        for (EditText et: dataEtiquetas) {
            if(et.getVisibility() == View.GONE){
                et.setText(lbl);
                et.setVisibility(View.VISIBLE);
                break;
            }
        }
    }
    /*
    private void addToEtiquetas(String lbl){
        LinearLayout llrow = new LinearLayout(this);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0, 1);
        llrow.setWeightSum(1f);
        llrow.setOrientation(LinearLayout.HORIZONTAL);
        llrow.setLayoutParams(llParams);

        EditText nET = new EditText(this);
        nET.setEnabled(false);
        nET.setText(lbl);
        nET.setLayoutParams(new TableLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT, 1f));

        llrow.addView(nET);
        dataEtiquetas.add(nET);
        Button btn = new Button(this);
        btn.setOnClickListener(this);
        btn.setBackgroundColor(Color.WHITE);
        Drawable img = this.getResources().getDrawable( R.mipmap.ic_minus);

        img.setBounds( 0, 0, 120, 120 );

        btn.setCompoundDrawables(null, null, img, null );


        llrow.addView(btn);
        dataButtons.add(btn);
        lLayoutEtiquetas.setWeightSum(lLayoutEtiquetas.getWeightSum()+1);
        lLayoutEtiquetas.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,
                lLayoutEtiquetas.getWeightSum()+1));
        lLayoutEtiquetas.addView(llrow);
    }*/


    /**
     *
     * @param campo
     * @return
     */
    private boolean campEmpty(String campo){
        return TextUtils.isEmpty(campo);
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
