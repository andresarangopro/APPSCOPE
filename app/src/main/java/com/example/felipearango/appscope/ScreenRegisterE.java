package com.example.felipearango.appscope;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.felipearango.appscope.ScreenRegisterUC.ESTADO_ACTIVA;

public class ScreenRegisterE extends AppCompatActivity implements View.OnClickListener {

    private EditText txtnameE,txtRazonSoc, txtNIT, txtUrl, txtSocial1, txtSocial2, txtSocial3;
    private Button btnRegisterE, btnAgregarNIT, btnAgregarRedSocial1, btnAgregarRedSocial2, btnAgregarRedSocial3;
    private LinearLayout llRedesSociales;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private int counter = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_register_e);
        instanceXml();
        firebaseAuth = FirebaseAuth.getInstance();
        initializedDR();
        addToSocialNetwork();
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
        txtSocial1 = (EditText) findViewById(R.id.txtSocial1);
        txtSocial2 = (EditText) findViewById(R.id.txtSocial2);
        txtSocial3 = (EditText) findViewById(R.id.txtSocial3);
        llRedesSociales = (LinearLayout) findViewById(R.id.llRedesSociales);

        btnRegisterE = (Button) findViewById(R.id.btnRegisterE);
        btnRegisterE.setOnClickListener(this);

        btnAgregarRedSocial1 = (Button) findViewById(R.id.btnAgregarRedesSociales1);
        btnAgregarRedSocial1.setOnClickListener(this);

        btnAgregarRedSocial2 = (Button) findViewById(R.id.btnAgregarRedesSociales2);
        btnAgregarRedSocial2.setOnClickListener(this);

        btnAgregarRedSocial3 = (Button) findViewById(R.id.btnAgregarRedesSociales3);
        btnAgregarRedSocial3.setOnClickListener(this);

        btnAgregarNIT = (Button) findViewById(R.id.btnAgregarNit);
        btnAgregarNIT.setOnClickListener(this);


    }

    private void addToSocialNetwork(){

        txtSocial2.setVisibility(View.INVISIBLE);
        btnAgregarRedSocial2.setVisibility(View.INVISIBLE);
        txtSocial3.setVisibility(View.GONE);
        btnAgregarRedSocial3.setVisibility(View.GONE);

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
        startActivity(new Intent(getApplicationContext(),Perfil.class));
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
            case R.id.btnAgregarRedesSociales1: {
                counter++;
                if(counter == 2){
                    txtSocial2.setVisibility(View.VISIBLE);
                    btnAgregarRedSocial2.setVisibility(View.VISIBLE);
                }else if(counter == 3){
                    txtSocial3.setVisibility(View.VISIBLE);
                    btnAgregarRedSocial3.setVisibility(View.VISIBLE);
                }
                break;
            }
            case R.id.btnAgregarRedesSociales2: {
                counter--;
                txtSocial2.setVisibility(View.GONE);
                btnAgregarRedSocial2.setVisibility(View.GONE);
                break;
            }
            case R.id.btnAgregarRedesSociales3: {
                counter--;
                txtSocial3.setVisibility(View.GONE);
                btnAgregarRedSocial3.setVisibility(View.GONE);
                break;
            }
            default:{

            }
        }
    }

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
