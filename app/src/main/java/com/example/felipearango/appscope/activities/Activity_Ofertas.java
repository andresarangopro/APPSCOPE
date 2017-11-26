package com.example.felipearango.appscope.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.CircleTransform;
import com.example.felipearango.appscope.models.Empresa;
import com.example.felipearango.appscope.models.Etiqueta;
import com.example.felipearango.appscope.models.OnSwipeTouchListener;
import com.example.felipearango.appscope.models.Trabajo;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Activity_Ofertas extends MainActivity implements View.OnClickListener{

    private TextView tvTrabajo, tvEmpresa, tvTitulo;
    private Button btnOfertar;
    private LinearLayout ll, llMove;
    private ArrayList<Trabajo> trabajos = new ArrayList<>();
    private ArrayList<Etiqueta> etiquetas = new ArrayList<>();
    private ArrayList<String> listStrEtiq = new ArrayList<>();
    private Empresa empres = null;
    private int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_ofertas, null, false);
        mDrawer.addView(contentView, 0);

        iniciarComponentes();
        inicializatedFireBase();
        eventETRE("CorrientsUsers");


        (findViewById(R.id.llMove)).setOnTouchListener(new OnSwipeTouchListener(Activity_Ofertas.this) {
            public void onSwipeTop() {
                if(counter == 0){
                    showEmpresa(trabajos.get(counter).getIdEmpresa());
                }
                showPopUp();
            }
            public void onSwipeRight() {

                //  Toast.makeText(Activity_Ofertas.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                showJob(++counter);
                showEmpresa(trabajos.get(counter).getIdEmpresa());
                // Toast.makeText(Activity_Ofertas.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                //  Toast.makeText(Activity_Ofertas.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });


    }

    private void showPopUp(){
        if(trabajos.size() > counter){

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_empresa, null);
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true;
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            //////////////////////////////////////////////////////////////
            ////////Inicialización de los dos componentes de el pop up
            //////////////////////////////////////////////////////////////

            TextView tvEmpresa = ((TextView)popupWindow.getContentView().findViewById(R.id.tvEmpresa));
             TextView tvDetalles = ((TextView)popupWindow.getContentView().findViewById(R.id.tVNameP));
             TextView tVFrase= ((TextView)popupWindow.getContentView().findViewById(R.id.tVFrase));
             tVFrase.setVisibility(View.INVISIBLE);
            ImageView imVPerfil = ((ImageView) popupWindow.getContentView().findViewById(R.id.imVPerfil));
            //  tVOcupacionP
            //tVFrase
             //TextView  = ((TextView)popupWindow.getContentView().findViewById(R.id.tVNameP));
            tvDetalles.setText(empres.getNombre());
            if(!empres.getFoto().equals("")){
                Picasso.with(this)
                        .load(empres.getFoto())
                        .transform(new CircleTransform())
                        .into(imVPerfil);
            }

            //////////////////////////////////////////////////////////////
            ////Esto muestra el pop Up window
            ////////////////////////////////////////////////////////////

            popupWindow.showAtLocation(ll, Gravity.CENTER, 0, 0);
            //////////////////////////////////////////////////
            ////////Listener que oculta el pop Up
            ////////////////////////////////////////////////
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();

                    return true;
                }
            });
        }else{
            Toast.makeText(this,"No hay empresas que mostrar", Toast.LENGTH_LONG).show();
        }

    }

    private void showEmpresa(final String idEmpresa){
        databaseReference.child("EmpresaUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                empres = dataSnapshot.child(idEmpresa).getValue(Empresa.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void showJob(int pos){
        //   Log.e("pos",pos+" - "+trabajos.size());
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
        ll = (LinearLayout)findViewById(R.id.llPrincipal);
        btnOfertar = (Button)findViewById(R.id.btnAplicar);
        ll = (LinearLayout) findViewById(R.id.llLayout);
        llMove = (LinearLayout) findViewById(R.id.llMove);
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

    private void cargarEtiquetas(ArrayList<String> strEtiqueta){
        for (int i = 0; i < strEtiqueta.size(); i++) {
            startArrayEtiquetas(strEtiqueta.get(i).toString());
        }
        startArrayJobs();

    }

    public void eventETRE(String usChildString){
        databaseReference.child(usChildString).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                UsuarioCorriente uC =  dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class);
                for (String strE : uC.getEtiquetas()) {
                    listStrEtiq.add(strE);
                }
                cargarEtiquetas(listStrEtiq);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
