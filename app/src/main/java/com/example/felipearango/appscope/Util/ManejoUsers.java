package com.example.felipearango.appscope.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.felipearango.appscope.activities.Activity_Admin;
import com.example.felipearango.appscope.activities.Activity_AgregarAdmin;
import com.example.felipearango.appscope.activities.Activity_Perfil;
import com.example.felipearango.appscope.activities.Activity_ScreenRegisterE;
import com.example.felipearango.appscope.activities.Activity_ScreenRegisterUC;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;
import static com.example.felipearango.appscope.activities.Activity_Login.calledAlready;

/**
 * Created by Felipe Arango on 29/11/2017.
 */

public class ManejoUsers {

    public DatabaseReference databaseReference;
    public FirebaseAuth firebaseAuth;
    public FirebaseDatabase firebaseDatabase;


    public void account(final Context context){
        // TIPO_USUARIO  = 0;
        databaseReference.child("CorrientsUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.e("UA", user.getUid());
                if(dataSnapshot.child(user.getUid()).child("tipoUser").getValue() == null){

                }else{
                    //int tipoUser = dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class).getTipoUser();
                    int tipoUser = Integer.parseInt(dataSnapshot.child(user.getUid()).child("tipoUser").getValue().toString());
                    String estadoCuenta = dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class).getEstadoCuenta();
                    Log.e("account",tipoUser+" - "+estadoCuenta);
                    TIPO_USUARIO = tipoUser;
                    if(tipoUser == 0){
                        if(estadoCuenta.equals("NUEVA")){
                            context.startActivity(new Intent(context,Activity_ScreenRegisterUC.class));
                        }else{
                            context.startActivity(new Intent(context,Activity_Perfil.class));
                        }
                    }else if(tipoUser == 1){
                        if(estadoCuenta.equals("NUEVA")){
                            context.startActivity(new Intent(context,Activity_ScreenRegisterE.class));
                        }else{
                            context.startActivity(new Intent(context,Activity_Perfil.class));
                        }
                    }else{
                        context.startActivity(new Intent(context, Activity_Admin.class));
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     *
     */
    public void inicializatedFireBase(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (!calledAlready) {
            firebaseDatabase.setPersistenceEnabled(true);
            calledAlready = true;
        }
        databaseReference = firebaseDatabase.getReference();
    }

    public void updateEstadoEmpresa(String idEmpresa, String idNodo, int valor){
        databaseReference.child("CorrientsUsers").child(idEmpresa).child(idNodo).setValue(valor);
    }


    public void getUser(Context context){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jane Q. User")
                .build();

    }

}