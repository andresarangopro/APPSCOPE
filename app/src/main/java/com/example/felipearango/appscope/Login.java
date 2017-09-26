package com.example.felipearango.appscope;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    //////////////////////////
    //Variables
    /////////////////////////

    private EditText txtMail, txtPass;
    private TextView lblForgotPass, lblRegister;
    private Button signIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    //////////////////////////
    //Oncreate
    /////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instances();
        onclickList();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        verificaSignIn();
        btnTest();
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
    }

    /**
     * Metodo que verifica si hay un usuario loggeado,
     * si lo hay entre directmente al Main activity,
     * en caso contrario lo deja en el intent Login
     */
    private void verificaSignIn(){
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
    /**
     *
     */
    private void onclickList(){
        lblRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Login.this,RegistroEmail.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = getTxtEdit(txtMail);
                String pass = getTxtEdit(txtPass);
                if(!campEmpty(mail) && !campEmpty(pass)){
                    loginUser(mail, pass);
                }
            }
        });
    }

    /**
     *
     * @param mail
     * @param pass
     */
    private void loginUser(String mail, String pass){
        progressDialog.setMessage("Login user, please wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this,"Datos errados",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private boolean campEmpty(String campo){
       return TextUtils.isEmpty(campo);
    }

    private String getTxtEdit(EditText txt){
        return txt.getText().toString();
    }

    private void btnTest(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( getApplicationContext(), Perfil.class));
            }
        });
    }

}
