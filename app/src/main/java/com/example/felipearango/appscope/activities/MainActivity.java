package com.example.felipearango.appscope.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.CircleTransform;
import com.example.felipearango.appscope.Util.Util;
import com.example.felipearango.appscope.models.Administrador;
import com.example.felipearango.appscope.models.Empresa;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.felipearango.appscope.Util.Util.usuario_administrador;
import static com.example.felipearango.appscope.Util.Util.usuario_corriente;
import static com.example.felipearango.appscope.Util.Util.usuario_empresa;
import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;
import static com.example.felipearango.appscope.activities.Activity_Login.calledAlready;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    protected static FirebaseAuth firebaseAuth;
    protected DrawerLayout mDrawer;
    private TextView txtNavMail,txtNavName;
    private ImageView iVNavPerfil;
    protected static DatabaseReference databaseReference;
    protected static FirebaseDatabase firebaseDatabase;
    private MenuView.ItemView nav_gallery;
    protected ValueEventListener valueEventListener ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        inicializatedComponents();
        inicializatedFireBase();
        datosUser();
        /////////////////////////
        //////Test!!!!
        //////////////////////

    }
    /**
     *
     */
    protected void inicializatedFireBase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (!calledAlready) {
            firebaseDatabase.setPersistenceEnabled(true);
            calledAlready = true;
        }
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(TIPO_USUARIO == 0){
            getMenuInflater().inflate(R.menu.main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_work) {
            if(TIPO_USUARIO == 0){
                startActivity(new Intent(getApplicationContext(), Activity_Ofertas.class));
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notificaciones) {
            startActivity(new Intent(getApplicationContext(), Activity_notificaciones.class));
            finish();
        } else if (id == R.id.nav_trabajos) {
            startActivity(new Intent(getApplicationContext(), Activity_Ofertas.class));
            finish();
        } else if (id == R.id.nav_perfil) {
            startActivity(new Intent(getApplicationContext(),Activity_Perfil.class));
            finish();
        } else if (id == R.id.nav_configuracion) {
            startActivity(new Intent(getApplicationContext(),Activity_Settings.class));
            finish();
        }else if (id == R.id.nav_notificaciones_admin) {
            startActivity(new Intent(getApplicationContext(),Activity_Admin.class));
            finish();
        }else if (id == R.id.nav_add_admin) {
            startActivity(new Intent(getApplicationContext(),Activity_AgregarAdmin.class));
            finish();
        } else if (id == R.id.nav_ofertar_trabajo) {
            startActivity(new Intent(getApplicationContext(),Activity_OfertarTrabajo.class));
            finish();
        } else if (id == R.id.nav_logout) {
            signout();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void inicializatedComponents(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        Menu nav_Menu = navigationView.getMenu();
        txtNavMail = (TextView) headerView.findViewById(R.id.txtNavMail);
        txtNavName = (TextView) headerView.findViewById(R.id.txtNavName);
        iVNavPerfil = (ImageView) headerView.findViewById(R.id.iVNavPerfil);
      // android.view.View action_work = (android.view.View) headerView.findViewById(R.id.action_work);
        if(TIPO_USUARIO == usuario_corriente){
            nav_Menu.findItem(R.id.nav_add_admin).setVisible(false);
            nav_Menu.findItem(R.id.nav_notificaciones_admin).setVisible(false);
        }
        if(TIPO_USUARIO == usuario_empresa){
            nav_Menu.findItem(R.id.nav_trabajos).setVisible(false);
            nav_Menu.findItem(R.id.nav_add_admin).setVisible(false);
            nav_Menu.findItem(R.id.nav_notificaciones_admin).setVisible(false);
         //   nav_Menu.findItem(R.id.action_work).setVisible(false);
        }
        if(TIPO_USUARIO == usuario_administrador){
            nav_Menu.findItem(R.id.nav_trabajos).setVisible(false);
            nav_Menu.findItem(R.id.nav_notificaciones).setVisible(false);
            nav_Menu.findItem(R.id.nav_perfil).setVisible(false);
            nav_Menu.findItem(R.id.nav_ofertar_trabajo).setVisible(false);
            nav_Menu.findItem(R.id.nav_configuracion).setVisible(false);
        }

    }


    protected void datosUser(){
        try {
            eventPDU("CorrientsUsers");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void eventPDU(String usChildString)throws Exception{
        databaseReference.child(usChildString).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (TIPO_USUARIO == usuario_corriente) {
                    UsuarioCorriente uC = dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class);
                    putDatesUsC(uC);
                    putImg(uC);
                } else if (TIPO_USUARIO == usuario_empresa) {
                    Empresa uE = dataSnapshot.child(user.getUid()).getValue(Empresa.class);
                    putDatesUsE(uE);
                    putImg(uE);
                } else {
                    Administrador admin = dataSnapshot.child(Util.castMailToKey(user.getEmail())).getValue(Administrador.class);
                    putDatesAdmin(admin);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void putImg(Object obj){
        if(obj instanceof UsuarioCorriente){
            if(!(((UsuarioCorriente)obj).getFoto().equals(""))){
                Glide.with(this)
                        .load(((UsuarioCorriente)obj).getFoto())
                        .apply(RequestOptions
                                .circleCropTransform()).into(iVNavPerfil);

             /*  Picasso.with(MainActivity.this)
                        .load(((UsuarioCorriente)obj).getFoto())
                        .transform(new CircleTransform())
                        .into(iVNavPerfil);*/

            }
        }else{
            if(!(((Empresa)obj).getFoto().equals(""))){
                Glide.with(this)
                        .load(((Empresa)obj).getFoto())
                        .apply(RequestOptions
                                .circleCropTransform()).into(iVNavPerfil);
               /* Picasso.with(MainActivity.this)
                        .load(((Empresa)obj).getFoto())
                        .transform(new CircleTransform())
                        .into(iVNavPerfil);*/

            }
        }
    }
    private void putDatesUsC(UsuarioCorriente uC){
        txtNavName.setText(uC.getNombre()+" "+uC.getApellido());
        txtNavMail.setText(uC.getCorreo());
    }

    private void putDatesUsE(Empresa uE){
        txtNavName.setText(uE.getNombre());
        txtNavMail.setText(uE.getMail());
    }

    private void putDatesAdmin(Administrador admin){
       if(admin != null){
           txtNavName.setText(admin.getNombre());
           txtNavMail.setText(admin.getCorreo());

       }
    }


    private void signout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this, Activity_Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
    protected ArrayList<String> quitarRepetidos(ArrayList<String> strRe){
        for (int i = 0; i < strRe.size() ; i++) {
            for (int j = 0; j <strRe.size() ; j++) {
                if(strRe.get(i).toString().equals(strRe.get(j).toString()) && i != j){
                    strRe.set(j,"");
                }
            }
        }
        return strRe;
    }

    protected String getTxtEdit(EditText txt){
        return txt.getText().toString();
    }

}