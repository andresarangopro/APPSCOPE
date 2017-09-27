package com.example.felipearango.appscope;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegistroEmail extends AppCompatActivity implements View.OnClickListener{

    private EditText email, contraseña, confirmar;
    private TextView alreadyRegister;
    private Button btnRegister;
    private ArrayList<EditText> field = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_email);
        startComponents();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    private void startComponents(){
        email = (EditText) findViewById(R.id.mail);
        contraseña = (EditText)findViewById(R.id.contraseña);
        confirmar = (EditText)findViewById(R.id.contraseñaC);
        alreadyRegister = (TextView)findViewById(R.id.btnAlreadyRegister);
        alreadyRegister.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.btnRegistrar);
        btnRegister.setOnClickListener(this);

        field.add(email);
        field.add(contraseña);
        field.add(confirmar);
    }

    @Override
    public void onClick(View view) {
        int vista = view.getId();
        switch(vista){
            case R.id.btnRegistrar:{
                if(checkEditText()){
                    registerUser(email.getText().toString(),contraseña.getText().toString());
                }
                break;
            }
            case R.id.btnAlreadyRegister: {

                break;
            }
        }
    }

    private boolean checkEditText(){
        boolean valido = true;
        for (EditText et: field) {
            if(et.getText().toString().equals("")){
                et.setError("Ingrese este campo por favor");
                valido = false;
            }
        }
        if(valido){
            if(!contraseña.getText().toString().equals(confirmar.getText().toString())){
                confirmar.setError("Contraseñas no concuerdan");
                valido = false;
            }
        }
        return valido;
    }

    /**
     * @param mail
     * @param pass
     */
    private void loginUser(String mail, String pass){
        // progressDialog.setMessage("Login user, please wait...");
        //progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // progressDialog.dismiss();
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),MiddleLR.class));
                        }else{
                            Toast.makeText(getApplicationContext(),"Datos errados",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void registerUser(String mail, String pass) {
        final String mail1 = mail;
        final String pass1 = pass;
        firebaseAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(RegistroEmail.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistroEmail.this, "REGISTER SUCCESFULLY", Toast.LENGTH_SHORT).show();
                            loginUser(mail1,pass1);
                        } else {
                            Toast.makeText(RegistroEmail.this, "COULD NOT REGISTER. PLEASE TRY AGAIN", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
