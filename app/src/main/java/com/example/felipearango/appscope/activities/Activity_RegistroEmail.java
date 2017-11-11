package com.example.felipearango.appscope.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.Empresa;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Activity_RegistroEmail extends AppCompatActivity implements View.OnClickListener{

    private EditText email, contraseña, confirmar;
    private TextView alreadyRegister;
    private Button btnRegister;
    private ArrayList<EditText> field = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Button iBtnCompany, iBtnUser;
    private ProgressDialog progressDialog;
    private static final String ESTADO_NUEVA = "NUEVA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_email);
        startComponents();
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    private void startComponents(){
        email = (EditText) findViewById(R.id.mail);
        contraseña = (EditText)findViewById(R.id.contraseña);
        confirmar = (EditText)findViewById(R.id.contraseñaC);
        alreadyRegister = (TextView)findViewById(R.id.btnAlreadyRegister);
        alreadyRegister.setOnClickListener(this);
        iBtnCompany = (Button) findViewById(R.id.iBtnCompany);
        iBtnCompany.setOnClickListener(this);
        iBtnUser = (Button) findViewById(R.id.iBtnUser);
        iBtnUser.setOnClickListener(this);

        field.add(email);
        field.add(contraseña);
        field.add(confirmar);
    }

    @Override
    public void onClick(View view) {
        if(checkEditText()){
            int vista = view.getId();
            switch(vista){
                case R.id.iBtnCompany:{
                    Activity_Login.TIPO_USUARIO = 1;
                   registerUser(email.getText().toString(), contraseña.getText().toString());
                   // startActivity(new Intent(getApplicationContext(), Activity_ScreenRegisterUC.class));
                    break;
                }
                case R.id.iBtnUser: {
                    Activity_Login.TIPO_USUARIO = 0;
                    //startActivity(new Intent(getApplicationContext(), Activity_ScreenRegisterE.class));
                    registerUser(email.getText().toString(), contraseña.getText().toString());
                    break;
                }
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
     * Metodo register metodo que registra un usuario inexistente
     * @param mail Correo del usuario nuevo a registrar
     * @param pass Contraseña del usuario que se esta registrando
     */
    private void registerUser(String mail, String pass) {
        final String mail1 = mail;
        final String pass1 = pass;
        progressDialog.setMessage("Register user, please wait...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(Activity_RegistroEmail.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Activity_RegistroEmail.this, "REGISTER SUCCESFULLY", Toast.LENGTH_SHORT).show();
                            // finish();
                            //startActivity(new Intent(Activity_ScreenRegisterUC.this, MainActivity.class));
                            loginUser(mail1,pass1, Activity_Login.TIPO_USUARIO);
                        } else {
                            Toast.makeText(Activity_RegistroEmail.this, "COULD NOT REGISTER. PLEASE TRY AGAIN", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     *
     * @param mail
     * @param pass
     */
    private void loginUser(String mail, String pass, final int tipoUser){
        firebaseAuth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(tipoUser == 0){
                                creaUsuario();
                            }else{
                                creaUEmpresa();
                            }

                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(Activity_RegistroEmail.this,"Datos errados",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void creaUsuario (){
        String id = "";
        String name ="";
        String apellido = "";
        String ocupacion = "";
        String dateBorn = "";
        String universidad ="";
        String celular = "";
        String foto = "";
        String frase = "";
        String hobbies = "";
        String conocimientosInf = "";
        String anexos = "";
        String idiomas = "";
        String expProfesionaless ="";
        String refEmpleo ="";
        String formacion = "";
        String mail = "";
        String etiquetas = "";
        float rating = 0;
        FirebaseUser user = firebaseAuth.getCurrentUser();
        id = user.getUid();
        mail = user.getEmail();
        UsuarioCorriente uC = new UsuarioCorriente(id,name,apellido,ocupacion,dateBorn,universidad
                ,celular,mail,foto,frase,hobbies,conocimientosInf,ESTADO_NUEVA,rating,
                anexos,idiomas,expProfesionaless,refEmpleo,formacion, etiquetas);
        if(uC != null){
            insertarUsCFireBase(uC,user);
        }
    }

    /**
     *
     */
    public void creaUEmpresa(){
        String id = "";
        String nombre="";
        String razonSocial="";
        String urlEmpresa="";
        String nit = "";
        String mail="";
        String foto = "";
        String redesSociales = "";
        float rating = 0;
        FirebaseUser user = firebaseAuth.getCurrentUser();
        id = user.getUid();
        mail = user.getEmail();
        Empresa uE = new Empresa(id,nombre,razonSocial,urlEmpresa,nit,mail,ESTADO_NUEVA,foto,rating,redesSociales);
        if(uE != null){
            insertarUsEmFireBase(uE,user);
        }
    }

    /**
     *
     * @param uC
     * @param user
     */
    private void insertarUsCFireBase(UsuarioCorriente uC,FirebaseUser user){
        databaseReference.child("CorrientsUsers").child(user.getUid()).setValue(uC);
        finish();
        startActivity(new Intent(getApplicationContext(), Activity_ScreenRegisterUC.class));
    }

    /**
     *
     * @param uE
     * @param user
     */
    private void insertarUsEmFireBase(Empresa uE,FirebaseUser user){
        databaseReference.child("EmpresaUsers").child(user.getUid()).setValue(uE);
        finish();
        startActivity(new Intent(getApplicationContext(), Activity_ScreenRegisterE.class));
    }
}