package com.example.felipearango.appscope;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class OfertaTrabajo extends MainActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_perfil, null, false);
        mDrawer.addView(contentView, 0);
        iniciar();
        getUser();
    }

    private void getUser(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
    }

    private void iniciar(){

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void addJob(String nombre, String desc, String etiquetas, Object usr){

        ArrayList<String> ets = new ArrayList<>(Arrays.asList(etiquetas.split(",")));

        if(usr instanceof Empresa){
            Empresa emp = (Empresa) usr;
        }else{

        }
    }

    private void addJobToET(ArrayList<String> ets, Trabajo tb){

        for (String et: ets) {
            nodeExist(et, tb);
        }
    }

    public void nodeExist(final String et, final Trabajo tbr){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(et).getValue() == null){
                    databaseReference.child("Etiquetas").setValue(et);
                }
                databaseReference.child("Etiquetas").child(et).setValue(tbr);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
