package com.example.felipearango.appscope.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.EmpresaSolicitud;
import com.example.felipearango.appscope.models.Notificacion;
import com.example.felipearango.appscope.models.RecyclerAdapterEmpresa;
import com.example.felipearango.appscope.models.RecyclerAdapterNotificaciones;
import com.example.felipearango.appscope.models.Trabajo;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;

public class Activity_notificaciones extends MainActivity {

    private RecyclerAdapterNotificaciones mAdapter;
    private RecyclerAdapterEmpresa mAdapterEmp;
    private RecyclerView mRecyclerAccounts;
    private ArrayList<Notificacion> notificacion = new ArrayList<>();
    private ArrayList<EmpresaSolicitud> notificaciones = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<String> idJobs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_notificaciones, null, false);
        mDrawer.addView(contentView, 0);

      //  Notificacion noti = new Notificacion("Trabajo 1", "Empresa 1", "Validado");
      //  Notificacion noti2 = new Notificacion("Trabajo 2", "Empresa 2", "Validado");
      //  Notificacion noti3 = new Notificacion("Trabajo 3", "Empresa 3", "Validado");

      //  notificacion.add(noti);
      //  notificacion.add(noti2);
       // notificacion.add(noti3);

        iniciar();
        inicializatedFireBase();
    }

    private void iniciar(){
        mRecyclerAccounts = (RecyclerView) findViewById(R.id.rv_Noti);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerAccounts.setLayoutManager(mLinearLayoutManager);
       // mAdapterEmp = new RecyclerAdapterEmpresa(notificaciones);
        mAdapter = new RecyclerAdapterNotificaciones(notificacion);
        mRecyclerAccounts.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerAccounts.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerAccounts.addItemDecoration(dividerItemDecoration);
        startArrayJobs();
        startnotifiOferts(idJobs);
    }

    private void startArrayJobs(){
        databaseReference.child("Etiqueta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(TIPO_USUARIO == 0){
                }else{
                    for (DataSnapshot etiquetasChild: dataSnapshot.getChildren()) {
                        for (DataSnapshot empresasChild: etiquetasChild.getChildren() ) {
                            if(user.getUid().equals(empresasChild.getKey())){
                                for (DataSnapshot jobsEtiqueta: empresasChild.getChildren()) {
                                    idJobs.add(jobsEtiqueta.getKey());
                                }
                            }
                        }
                    }
                    idJobs = quitarRepetidos(idJobs);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void startnotifiOferts(final ArrayList<String> strIdJob){
        databaseReference.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Trabajo tb = null;
                for (DataSnapshot jobsChild: dataSnapshot.getChildren()) {
                    for(String idOfJob: strIdJob){
                        if(idOfJob.equals(jobsChild.getKey())){
                            tb = jobsChild.getValue(Trabajo.class);
                            Log.e("ETERZ",tb.getId());
                            Notificacion noti = new Notificacion(tb.getTitulo(),
                                    tb.getId(),jobsChild.child("Ofertas").getChildrenCount());
                            //EmpresaSolicitud emp = new EmpresaSolicitud("Nombre");
                            //notificaciones.add(emp);
                            notificacion.add(noti);


                        }
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
