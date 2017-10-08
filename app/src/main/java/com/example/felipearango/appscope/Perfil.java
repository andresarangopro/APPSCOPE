package com.example.felipearango.appscope;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Perfil extends MainActivity {


    private UsuarioCorriente user;
    private TextView tvNombre, tvSegundo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_perfil, null, false);
        mDrawer.addView(contentView, 0);
        firebaseAuth = FirebaseAuth.getInstance();
        initializedDR();
        startComponents();
        datosUser();
    }

    private void startComponents(){

    }

    private void initializedDR() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }


    private void setUser(UsuarioCorriente us){
        user = us;
    }

    private void setTextViews(Object object){

        if(object instanceof UsuarioCorriente){
            UsuarioCorriente us = (UsuarioCorriente)object;
            tvNombre.setText(us.getNombre()+" "+us.getApellido());
        }else{

        }

    }
}
