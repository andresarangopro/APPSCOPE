package com.example.felipearango.appscope.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.Util;
import com.example.felipearango.appscope.models.Administrador;
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

import static com.example.felipearango.appscope.Util.Util.usuario_administrador;
import static com.example.felipearango.appscope.activities.Activity_Login.calledAlready;


public class Activity_AgregarAdmin extends MainActivity implements View.OnClickListener{

    private EditText etNombre, etEmailAgregar, etContraseña, etConfirmarContraseña, etEmailEliminar;
    private Button btnAddadmin, btneliminar;
  
    public DatabaseReference databaseReference;
    public FirebaseAuth firebaseAuth;
    public FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;
    private String passAux, mailAux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_agregar_admin, null, false);
        mDrawer.addView(contentView, 0);
        initComponents();
        firebaseAuth = FirebaseAuth.getInstance();
        inicializatedFireBase();
        progressDialog = new ProgressDialog(this);
        getUserAux();
    }

    private void initComponents(){
        etNombre = (EditText) findViewById(R.id.etNombre);
        etEmailAgregar  = (EditText) findViewById(R.id.etEmailAgregar);
        etContraseña  = (EditText) findViewById(R.id.etContraseña);
        etConfirmarContraseña  = (EditText) findViewById(R.id.etConfirmarContraseña);
        etEmailEliminar  = (EditText) findViewById(R.id.etEmailEliminar);

        btnAddadmin = (Button) findViewById(R.id.btnAddadmin);
        btneliminar = (Button) findViewById(R.id.btneliminar);

        btnAddadmin.setOnClickListener(this);
        btneliminar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vista = v.getId();
        switch (vista){
            case R.id.btnAddadmin:{
                makeAdmin();
                break;
            }
            case R.id.btneliminar:{
                if(!Util.emptyCampMSG(etEmailEliminar,"Ingrese correo a eliminar")){
                   getPassMailtoDelete(Util.getTxt(etEmailEliminar));
                }
                break;
            }
        }
    }

    private void getUserAux(){
        databaseReference.child("CorrientsUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Administrador admin = dataSnapshot.child(Util.castMailToKey(user.getEmail())).getValue(Administrador.class);
                passAux = admin.getPass();
                mailAux = admin.getCorreo();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void registerUser(final Object obj,String mail, String pass,final String key) {
        final String mail1 = mail;
        final String pass1 = pass;
        progressDialog.setMessage("Registrando administrador");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(Activity_AgregarAdmin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            insertAdmin(key,obj);
                        } else {
                            Toast.makeText(Activity_AgregarAdmin.this, "COULD NOT REGISTER. PLEASE TRY AGAIN", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void makeAdmin(){
        String correo = Util.getTxt(etEmailAgregar);
        String nombre = Util.getTxt(etNombre);
        String pass = Util.getTxt(etContraseña);
        String key = Util.castMailToKey(correo);
        Administrador admin = new Administrador(nombre, correo, pass,usuario_administrador);
        registerUser(admin,correo,pass, key);
    }

    public void insertAdmin(String key, Object valor){
        databaseReference.child("CorrientsUsers").child(key).setValue(valor).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                etNombre.setText("");
                etContraseña.setText("");
                etConfirmarContraseña.setText("");
                etEmailAgregar.setText("");
                progressDialog.dismiss();
                loginUser(mailAux,passAux);

            }
        });
    }

    private void loginUser(String mail, String pass){
       firebaseAuth.signInWithEmailAndPassword(mail,pass);
    }


    protected void inicializatedFireBase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (!calledAlready) {
            firebaseDatabase.setPersistenceEnabled(true);
            calledAlready = true;
        }
        databaseReference = firebaseDatabase.getReference();
    }

    private void getPassMailtoDelete(final String mail){
        progressDialog.setMessage("Borrando administrador");
        progressDialog.show();
        databaseReference.child("CorrientsUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Administrador admin = dataSnapshot.child(Util.castMailToKey(mail)).getValue(Administrador.class);
                if(!user.getEmail().equals(mail)&& admin != null){
                       borrarUser(admin.getCorreo(),admin.getPass());
                    Log.e("user",admin.getCorreo()+" -A borrar "+admin.getPass());
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(Activity_AgregarAdmin.this,"No se pudo completar el borrado del registro",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void borrarUser(String mail, String pass){
        firebaseAuth.signOut();
        firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                deletes();
            }
        });

    }

    private void deletes(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final  String id = Util.castMailToKey(user.getEmail());
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                loginUserComplete(mailAux, id,passAux);

            }
        });
    }

    private void loginUserComplete(String mail,final String id, String pass){

        firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                databaseReference.child("CorrientsUsers").child(id).removeValue();
                etEmailEliminar.setText("");
                progressDialog.dismiss();
            }
        });
    }
}
