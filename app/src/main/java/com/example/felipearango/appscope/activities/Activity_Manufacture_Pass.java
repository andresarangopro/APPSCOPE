package com.example.felipearango.appscope.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_Manufacture_Pass extends AppCompatActivity implements View.OnClickListener {

    private EditText etRecuperarPass;
    private Button btnRecuperar;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__manufacture__pass);
        initXml();
    }


    private void initXml(){
        etRecuperarPass = (EditText) findViewById(R.id.etRecuperarPass);
        btnRecuperar = (Button) findViewById(R.id.btnRecuperar);
        btnRecuperar.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
    }


    @Override
    public void onClick(View v) {
        int vista=  v.getId();
        switch (vista){
            case R.id.btnRecuperar:{
                if(!Util.emptyCampMSG(etRecuperarPass,"Ingrese correo")){
                    if(Util.validarCorreo(etRecuperarPass)){
                        recuperarPass(Util.getTxt(etRecuperarPass));
                    }else{
                        etRecuperarPass.setError("Ingrese un correo válido");
                    }
                }
                break;
            }
        }
    }

    private void recuperarPass(String mail){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress =mail;
        progressDialog.setMessage("Enviando correo electrónico de restablecimiento de contraseña");
        progressDialog.show();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Activity_Manufacture_Pass.this,"Se ha enviado un correo de restablecimiento a su correo",Toast.LENGTH_LONG).show();
                            etRecuperarPass.setText("");
                        }else{
                            Toast.makeText(Activity_Manufacture_Pass.this,"Ups! algo salio mal, ¿ya estas registrado?",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}
