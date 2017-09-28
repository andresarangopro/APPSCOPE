package com.example.felipearango.appscope;

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

public class Login extends AppCompatActivity {
    public static int TIPO_USUARIO = 0;

    //////////////////////////
    //Variables
    /////////////////////////

    private EditText txtMail, txtPass;
    private TextView lblForgotPass, lblRegister;
    private Button signIn;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    public static boolean calledAlready = false;
    private UsuarioCorriente uC = null;

    //////////////////////////
    //Oncreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instances();
        onclickList();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        inicializatedFireBase();
        verificaSignIn();

       // btnTest();
    }

    /////////////////////////

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
            //finish();
           // startActivity(new Intent(getApplicationContext(),MainActivity.class));
           existIsCorrientU();
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
                            existIsCorrientU();
                           // finish();
                          //  startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this,"Datos errados",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     *
     */
    private void inicializatedFireBase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (!calledAlready) {
            firebaseDatabase.setPersistenceEnabled(true);
            calledAlready = true;
        }
        databaseReference = firebaseDatabase.getReference();
    }


    private void existIsCorrientU(){
        databaseReference.child("CorrientsUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                uC = dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class);
                Log.e("DATE",dataSnapshot.child(user.getUid()).toString());
              if(dataSnapshot.child(user.getUid()).getValue() == null){
                    existIsEmpres();
                }else if(uC.getEstadoCuenta().equals("NUEVA")){
                  TIPO_USUARIO = 0;
                    finish();
                    startActivity(new Intent(Login.this,ScreenRegisterUC.class));
                }else{
                  TIPO_USUARIO = 0;
                    finish();
                    startActivity(new Intent(Login.this,MainActivity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void existIsEmpres(){
        databaseReference.child("EmpresaUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Empresa uE = dataSnapshot.child(user.getUid()).getValue(Empresa.class);
                Log.e("DATE",dataSnapshot.child(user.getUid()).toString());
                if(dataSnapshot.child(user.getUid()).getValue() == null){
                   // finish();
                   // startActivity(new Intent(Login.this,MiddleLR.class));
                    Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
                }else if(uE.getEstadoCuenta().equals("NUEVA")){
                    TIPO_USUARIO = 1;
                    finish();
                    startActivity(new Intent(Login.this,ScreenRegisterE.class));
                }else{
                    TIPO_USUARIO = 1;
                    finish();
                    startActivity(new Intent(Login.this,MainActivity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
