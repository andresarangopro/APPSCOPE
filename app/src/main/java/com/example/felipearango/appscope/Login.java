package com.example.felipearango.appscope;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    //////////////////////////
    //Variables
    /////////////////////////

    private EditText txtMail, txtPass;
    private TextView lblForgotPass, lblRegister;
    private Button signIn;

    //////////////////////////
    //Oncreate
    /////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instances();
        onclickList();
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

    private void onclickList(){
        lblRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ScreenRegister.class));
            }
        });
    }


}
