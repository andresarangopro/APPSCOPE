package com.example.felipearango.appscope;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.example.felipearango.appscope.Login.TIPO_USUARIO;

public class Perfil extends MainActivity {

    private ImageView imVPerfil;
    private TabHost tHData;
    private Object obj;
    private TextView  tVNamep, tVOcupacion, tVFrase;
    private int[] listTabs = {R.id.tab1,R.id.tab2,R.id.tab3,R.id.tab4,R.id.tab5};

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
        tabmade(listTabs);
        putDataUser();
    }


    protected void putDataUser(){
        if(TIPO_USUARIO == 0){
            eventPD("CorrientsUsers");
        }else{
            eventPD("EmpresaUsers");
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
        }else{
            tVNamep.setText(((Empresa)obj).getNombre());
            tVOcupacion.setText(((Empresa)obj).getMail());
        }
    }

    private void putImg(Object obj){
        if(obj instanceof UsuarioCorriente){
            if(!(((UsuarioCorriente)obj).getFoto().equals(""))){
                Picasso.with(Perfil.this)
                        .load(((UsuarioCorriente)obj).getFoto())
                        .transform(new CircleTransform())
                        .into(imVPerfil);
            }
        }else{
            if(!(((Empresa)obj).getFoto().equals(""))){
                Picasso.with(Perfil.this)
                        .load(((Empresa)obj).getFoto())
                        .transform(new CircleTransform())
                        .into(imVPerfil);
            }
        }
    }

    private void startComponents(){
        tHData = (TabHost) findViewById(R.id.tHData);
        tVNamep = (TextView) findViewById(R.id.tVNameP);
        tVOcupacion = (TextView) findViewById(R.id.tVOcupacionP);
        tVFrase = (TextView) findViewById(R.id.tVFrase);
        imVPerfil = (ImageView) findViewById(R.id.imVPerfil);
    }


    private void tabmade(int[] list){
        tHData.setup();
        for (int i = 0; i < list.length; i++) {
            tHData.addTab(tabSpecMade("tab"+(i+1),"DATA"+(i+1),list[i]));
        }
    }

    private TabHost.TabSpec tabSpecMade(String data, String indicador, int rId){
        TabHost.TabSpec ts = tHData.newTabSpec(data);
        ts.setIndicator(indicador);
        ts.setContent(rId);
        return ts;
    }

    private void initializedDR() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}