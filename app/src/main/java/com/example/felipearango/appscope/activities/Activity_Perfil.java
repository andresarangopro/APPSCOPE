package com.example.felipearango.appscope.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.CircleTransform;
import com.example.felipearango.appscope.models.Empresa;
import com.example.felipearango.appscope.models.EmpresaSolicitud;
import com.example.felipearango.appscope.models.RecyclerAdapterEmpresa;
import com.example.felipearango.appscope.models.RecyclerShowInfo;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;

public class Activity_Perfil extends MainActivity {

    private ImageView imVPerfil;
    private TabHost tHData;
    private Object obj;
    private TextView  tVNamep, tVOcupacion, tVFrase;
    private RecyclerView mRecyclerAccounts;
    private ArrayList<String> notificaciones = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerShowInfo mAdapterInf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_perfil, null, false);
        mDrawer.addView(contentView, 0);
        firebaseAuth = FirebaseAuth.getInstance();
        initializedDR();
        startComponents();
        datosUser();

        putDataUser();
    }


    protected void putDataUser(){
        if(TIPO_USUARIO == 0){
            eventPD("CorrientsUsers");
        }else{
            eventPD("CorrientsUsers");
        }
    }

    public void eventPD(String usChildString){
        databaseReference.child(usChildString).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(TIPO_USUARIO == 0){
                    UsuarioCorriente uC =  dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class);
                    putTxt(uC);
                    obj = uC;
                }else{
                    Empresa uE =  dataSnapshot.child(user.getUid()).getValue(Empresa.class);
                    putTxt(uE);
                    obj = uE;
                }
                putImg(obj);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void putTxt(Object obj){
        if(obj instanceof UsuarioCorriente){
            tVNamep.setText(((UsuarioCorriente)obj).getNombre());
            tVOcupacion.setText(((UsuarioCorriente) obj).getOcupacion());
            tVFrase.setText("Frase: "+((UsuarioCorriente) obj).getFrase());
        }else{
            tVNamep.setText(((Empresa)obj).getNombre());
            tVOcupacion.setText(((Empresa)obj).getMail());
        }
    }

    private void putImg(Object obj){
        if(obj instanceof UsuarioCorriente){
            if(!(((UsuarioCorriente)obj).getFoto().equals(""))){
                Picasso.with(Activity_Perfil.this)
                        .load(((UsuarioCorriente)obj).getFoto())
                        .transform(new CircleTransform())
                        .into(imVPerfil);
            }
        }else{
            if(!(((Empresa)obj).getFoto().equals(""))){
                Picasso.with(Activity_Perfil.this)
                        .load(((Empresa)obj).getFoto())
                        .transform(new CircleTransform())
                        .into(imVPerfil);
            }
        }
    }

    private void startComponents(){

        tVNamep = (TextView) findViewById(R.id.tVNameP);
        tVOcupacion = (TextView) findViewById(R.id.tVOcupacionP);
        tVFrase = (TextView) findViewById(R.id.tVFrase);
        imVPerfil = (ImageView) findViewById(R.id.imVPerfil);


        mRecyclerAccounts = (RecyclerView) findViewById(R.id.rv_Info);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerAccounts.setLayoutManager(mLinearLayoutManager);
        mAdapterInf = new RecyclerShowInfo( this, notificaciones);

        mRecyclerAccounts.setAdapter(mAdapterInf);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerAccounts.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerAccounts.addItemDecoration(dividerItemDecoration);
    }





    private void initializedDR() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}