package com.example.felipearango.appscope.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.ManejoUsers;
import com.example.felipearango.appscope.Util.Util;
import com.example.felipearango.appscope.models.Empresa;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_Login extends AppCompatActivity implements View.OnClickListener{


    //////////////////////////
    //Variables
    /////////////////////////

    private EditText txtMail, txtPass;
    private TextView lblForgotPass, lblRegister;
    private Button signIn;
    private ProgressDialog progressDialog;
    public static boolean calledAlready = false;
    private ManejoUsers mn = new ManejoUsers();
    public static int TIPO_USUARIO = 1;

    //////////////////////////
    //Oncreate
    ///////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instances();
        mn.inicializatedFireBase();
        progressDialog = new ProgressDialog(this);
        verificaSignIn();
       // btnTest();
    }

    ////////////////////////
    //Metodos
    ////////////////////////

    /**
     * Metodo que instancia objetos xml a objetos java
     */
    private void instances(){
        txtMail = (EditText) findViewById(R.id.txtMail);
        txtPass = (EditText) findViewById(R.id.txtPass);
        lblForgotPass= (TextView) findViewById(R.id.lblForgotPass);
        lblRegister= (TextView) findViewById(R.id.lblregister);
        signIn = (Button) findViewById(R.id.btnSignIn);

        lblForgotPass.setOnClickListener(this);
        lblRegister.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    /**
     * Metodo que verifica si hay un usuario loggeado,
     * si lo hay entre directmente al Main activity,
     * en caso contrario lo deja en el intent Activity_Login
     */
    private void verificaSignIn(){
        if(mn.firebaseAuth.getCurrentUser() != null){
            mn.account(Activity_Login.this);
            finish();
        }
    }

    /**
     *
     * @param mail
     * @param pass
     */
    private void loginUser(String mail, String pass){
        progressDialog.setMessage("Ingresando, por favor espera");
        progressDialog.show();

        mn.firebaseAuth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            mn.account(Activity_Login.this);
                            finish();
                        }else{
                            Toast.makeText(Activity_Login.this,"Datos errados",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int vista = v.getId();
        switch (vista){
            case R.id.lblForgotPass:{
                finish();
                startActivity(new Intent(Activity_Login.this,Activity_Manufacture_Pass.class));
                break;
            }
            case R.id.lblregister:{
                finish();
                startActivity(new Intent(Activity_Login.this,Activity_RegistroEmail.class));
                break;
            }
            case R.id.btnSignIn:{
                String mail = Util.getTxt(txtMail);
                String pass = Util.getTxt(txtPass);
                if(!Util.emptyCampMSG(txtMail,"Correo vacío") && !Util.emptyCampMSG(txtPass, "Contraseña vacía")){
                    loginUser(mail, pass);
                }
                break;
            }
        }
    }
}
