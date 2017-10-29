    package com.example.felipearango.appscope;

    import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Oferta extends MainActivity implements View.OnClickListener{

    private TextView tvTrabajo, tvEmpresa, tvTitulo;
    private Button btnOfertar;
    private LinearLayout ll;
    private ArrayList<Trabajo> trabajos = new ArrayList<>();
    private ArrayList<Etiqueta> etiquetas = new ArrayList<>();
    private int counter = 0;
    private boolean empresa = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_ofertas, null, false);
        mDrawer.addView(contentView, 0);

        iniciarComponentes();
        startArrayEtiquetas("Ingeniero");
        ll = (LinearLayout) findViewById(R.id.llLayout);
        ll.setOnTouchListener(new OnSwipeTouchListener(Oferta.this) {
            public void onSwipeTop() {
                empresa = true;
                Toast.makeText(Oferta.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
           //   showJob(++counter);
                Toast.makeText(Oferta.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                showJob(++counter);
                Toast.makeText(Oferta.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(Oferta.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void showEmpresa(int counter){
        databaseReference.child("EmpresaUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Empresa empresa = dataSnapshot.child(trabajos.get(0).getTitulo()).getValue(UsuarioCorriente.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showJob(int pos){
        Log.e("pos",pos+" - "+trabajos.size());
        if(trabajos.size() > pos){
            showJob(trabajos.get(pos));

        }else{
           // tvEmpresa.setText(":'c");
            tvTrabajo.setText("Sin trabajo!");
            tvTitulo.setText("Se han agotado los trabajos");
        }
    }

    private void iniciarComponentes(){
        tvEmpresa = (TextView)findViewById(R.id.tvEmpresa);
        tvTrabajo = (TextView)findViewById(R.id.tvDetalles);
        tvTitulo = (TextView)findViewById(R.id.tvTitulo);
        tvEmpresa = (TextView)findViewById(R.id.tvEmpresa);
        btnOfertar = (Button)findViewById(R.id.btnAplicar);
        btnOfertar.setOnClickListener(this);
    }

    private void startArrayEtiquetas(final String nameEtiqueta){
        databaseReference.child("Etiqueta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot: dataSnapshot.child(nameEtiqueta).getChildren()){
                    Log.e("WE", objSnapshot.getKey()+"");
                    for (DataSnapshot objSnaps: dataSnapshot.child(nameEtiqueta).
                            child(objSnapshot.getKey()).getChildren()){
                        Log.e("WE2", objSnaps.getKey()+"");
                        Etiqueta etiqueta = objSnaps.getValue(Etiqueta.class);
                        etiquetas.add(etiqueta);
                    }
                }
                startArrayJobs();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void startArrayJobs(){
        databaseReference.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i = 0; i < etiquetas.size(); i++) {
                   Trabajo job =  dataSnapshot.child(etiquetas.get(i).getIdTrabajo()).getValue(Trabajo.class);
                   trabajos.add(job);
                }
                showJob(counter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showJob(Trabajo trabajo){
        tvEmpresa.setText("");
        tvTrabajo.setText(trabajo.getDescripción());
        tvTitulo.setText(trabajo.getTitulo());
    }

    @Override
    public void onClick(View view) {
        if(view == btnOfertar){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            insertarOferta(trabajos.get(counter), user);
        }
    }

    private void insertarOferta(Trabajo job, FirebaseUser user){
        databaseReference.child("Jobs").child(job.getId()).
                child("Ofertas").child(user.getUid()).child("Estado").setValue("En espera");
    }

}
