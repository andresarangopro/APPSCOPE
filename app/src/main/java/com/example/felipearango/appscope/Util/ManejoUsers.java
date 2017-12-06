package com.example.felipearango.appscope.Util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.felipearango.appscope.activities.Activity_Admin;
import com.example.felipearango.appscope.activities.Activity_Perfil;
import com.example.felipearango.appscope.activities.Activity_ScreenRegisterE;
import com.example.felipearango.appscope.activities.Activity_ScreenRegisterUC;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.felipearango.appscope.Util.Util.usuario_administrador;
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
        databaseReference.child("CorrientsUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(dataSnapshot.child(Util.castMailToKey(user.getEmail())).child("tipoUser").getValue() != null){
                        int tipouser = Integer.parseInt(dataSnapshot.child(Util.castMailToKey(user.getEmail())).child("tipoUser").getValue().toString());
                        TIPO_USUARIO = tipouser;
                    if(tipouser == usuario_administrador){
                        Log.e("UA", user.getUid());
                        Toast.makeText(context,"ADMIN",Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Activity_Admin.class));
                    }
                }else if(dataSnapshot.child(user.getUid()).child("tipoUser").getValue() != null){

                    int tipoUser = Integer.parseInt(dataSnapshot.child(user.getUid()).child("tipoUser").getValue().toString());
                    String estadoCuenta = dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class).getEstadoCuenta().toString();
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

    public Task<Void> updateEstadoEmpresa(String idEmpresa, String idNodo, int valor){
        return databaseReference.child("CorrientsUsers").child(idEmpresa).child(idNodo).setValue(valor);
    }

    public void updateEstadoC (String idJob, String idOfertante,String idEmpresa, int calificacion){
        databaseReference.child("Jobs").child(idJob).child("Ofertas").child(idOfertante).child("EstadoCalif").setValue(calificacion);
        updateRating(idEmpresa,calificacion);
    }


    public void updateRating (final String idEmpresa, final int rating){
        getDataRating(idEmpresa).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int ratinNew = Integer.parseInt(dataSnapshot.child("rating").getValue().toString());
                int votosNew = Integer.parseInt(dataSnapshot.child("votos").getValue().toString());
                ratinNew += rating;
                votosNew ++;

                databaseReference.child("CorrientsUsers").child(idEmpresa).child("rating").setValue(ratinNew);
                databaseReference.child("CorrientsUsers").child(idEmpresa).child("votos").setValue(votosNew);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public DatabaseReference getDataRating(String idEmpresa){
        return databaseReference.child("CorrientsUsers").child(idEmpresa);
    }



}