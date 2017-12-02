package com.example.felipearango.appscope.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.Util;
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

import static com.example.felipearango.appscope.Util.Util.cuenta_espera_certif;
import static com.example.felipearango.appscope.Util.Util.usuario_corriente;
import static com.example.felipearango.appscope.Util.Util.usuario_empresa;
import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;

public class Activity_RegistroEmail extends AppCompatActivity implements View.OnClickListener{

    private EditText etEmail, etContraseña, etConfirmar;
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        startComponents();
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    private void startComponents(){
        etEmail = (EditText) findViewById(R.id.mail);
        etContraseña = (EditText)findViewById(R.id.contraseña);
        etConfirmar = (EditText)findViewById(R.id.contraseñaC);
        alreadyRegister = (TextView)findViewById(R.id.tvAlreadyRegister);
        alreadyRegister.setOnClickListener(this);
        iBtnCompany = (Button) findViewById(R.id.iBtnCompany);
        iBtnCompany.setOnClickListener(this);
        iBtnUser = (Button) findViewById(R.id.iBtnUser);
        iBtnUser.setOnClickListener(this);

        field.add(etEmail);
        field.add(etContraseña);
        field.add(etConfirmar);
    }

    @Override
    public void onClick(View view) {
        if(checkEditText()){
            int vista = view.getId();
            switch(vista){
                case R.id.iBtnCompany:{
                    TIPO_USUARIO = 1;
                    if(Util.validarCorreo(etEmail)){
                        registerUser(etEmail.getText().toString(), etContraseña.getText().toString());
                    }

                   // startActivity(new Intent(getApplicationContext(), Activity_ScreenRegisterUC.class));
                    break;
                }
                case R.id.iBtnUser: {
                    TIPO_USUARIO = 0;
                    //startActivity(new Intent(getApplicationContext(), Activity_ScreenRegisterE.class));
                    if(Util.validarCorreo(etEmail)) {
                        registerUser(etEmail.getText().toString(), etContraseña.getText().toString());
                    }
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
            String pass = Util.getTxt(etContraseña);
            if(pass.length() < 6 ){
                etContraseña.setError("La contraseña debe contener almenos 6 caracteres");
                valido = false;
            } else  if(!etContraseña.getText().toString().equals(etConfirmar.getText().toString())){
                etConfirmar.setError("Contraseñas no concuerdan");
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
                            Toast.makeText(Activity_RegistroEmail.this, "SE HA REGISTRO EXITOSAMENTE!GOO", Toast.LENGTH_SHORT).show();
                            // finish();
                            //startActivity(new Intent(Activity_ScreenRegisterUC.this, MainActivity.class));
                            loginUser(mail1,pass1, TIPO_USUARIO);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Activity_RegistroEmail.this, "NO SE PUDO COMPLETAR EL REGISTRO. POR FAVOR VUELVA A INTENTAR", Toast.LENGTH_LONG).show();
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
                ,celular,mail,foto,frase,hobbies,conocimientosInf,ESTADO_NUEVA,rating,usuario_corriente,
                anexos,idiomas,expProfesionaless,refEmpleo,formacion, etiquetas);
        if(uC != null){
            insertarUs(uC,user);
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
        Empresa uE = new Empresa(id,nombre,razonSocial,urlEmpresa,nit,mail,ESTADO_NUEVA,foto,rating,
                usuario_empresa, cuenta_espera_certif,redesSociales);
        if(uE != null){
            insertarUs(uE,user);
        }
    }

    /**
     *
     * @param uC
     * @param user
     */
    private void insertarUs(Object uC,FirebaseUser user){
        databaseReference.child("CorrientsUsers").child(user.getUid()).setValue(uC);
        finish();
        if(TIPO_USUARIO == 0){
            startActivity(new Intent(getApplicationContext(), Activity_ScreenRegisterUC.class));
        }else{
            startActivity(new Intent(getApplicationContext(), Activity_ScreenRegisterE.class));
        }

    }


}
