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

    private EditText txtnameE,txtRazonSoc, txtNIT, txtUrl, txtSocial;
    private Button btnRegisterE, btnAgregarNIT, btnAgregarRedSocial;
    private LinearLayout llRedesSociales;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

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
        txtSocial = (EditText) findViewById(R.id.txtSocial);
        llRedesSociales = (LinearLayout) findViewById(R.id.llRedesSociales);

        btnRegisterE = (Button) findViewById(R.id.btnRegisterE);
        btnRegisterE.setOnClickListener(this);

        btnAgregarRedSocial = (Button) findViewById(R.id.btnAgregarRedesSociales);
        btnAgregarRedSocial.setOnClickListener(this);

        btnAgregarNIT = (Button) findViewById(R.id.btnAgregarNit);
        btnAgregarNIT.setOnClickListener(this);

        txtSocial.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                Log.e("DATE","estas aquí");
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
    }

    private void addToSocialNetwork(){

        LinearLayout llrow = new LinearLayout(this);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                0, 1);
        llrow.setWeightSum(5f);
        llrow.setLayoutParams(llParams);

        EditText nET = new EditText(this);
        nET.setLayoutParams(new TableLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT, 4f));
        llrow.addView(nET);

        Button btn = new Button(this);

        btn.setOnClickListener(this);
        btn.setText("+");
        btn.setTextSize(40);
        btn.setBackgroundResource(R.drawable.mybutton);
        llrow.addView(btn);
        llRedesSociales.setWeightSum(llRedesSociales.getWeightSum()+1);
        llRedesSociales.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,
                llRedesSociales.getWeightSum()+1));
        llRedesSociales.addView(llrow);
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
            case R.id.btnAgregarRedesSociales:{
                addToSocialNetwork();
                break;
            }
            case R.id.btnRegisterE: {
                takeDates();
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
