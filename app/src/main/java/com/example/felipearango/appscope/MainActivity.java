package com.example.felipearango.appscope;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.felipearango.appscope.Login.TIPO_USUARIO;
import static com.example.felipearango.appscope.Login.calledAlready;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btn;
    private TextView txtNameP;
    protected FirebaseAuth firebaseAuth;
    protected DrawerLayout mDrawer;
    private TextView txtNavMail,txtNavName;
    protected DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
       // startActivity(new Intent(getApplicationContext(), Perfil.class));
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(getApplicationContext(),Perfil.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(getApplicationContext(),OfertaTrabajo.class));
        } else if (id == R.id.nav_send) {
            signout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void inicializatedComponents(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        txtNavMail = (TextView) headerView.findViewById(R.id.txtNavMail);
        txtNavName = (TextView) headerView.findViewById(R.id.txtNavName);
    }

    protected void datosUser(){
        if(TIPO_USUARIO == 0){
           eventPDU("CorrientsUsers");
        }else{
            eventPDU("EmpresaUsers");
        }
    }

    public void eventPDU(String usChildString){
        databaseReference.child(usChildString).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //listUsers.clear();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.e(""+TIPO_USUARIO,"HOLAAAA");
                if(TIPO_USUARIO == 0){
                    UsuarioCorriente uC =  dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class);
                    putDatesUsC(uC);
                }else{
                   Empresa uE =  dataSnapshot.child(user.getUid()).getValue(Empresa.class);
                    putDatesUsE(uE);
                }
                // putImage(userIn);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void putDatesUsC(UsuarioCorriente uC){
        Log.e("USER",TIPO_USUARIO+" ---- "+uC.getId().toString());
        txtNavName.setText(uC.getNombre()+" "+uC.getApellido());
        txtNavMail.setText(uC.getCorreo());
        txtNameP.setText(uC.getNombre());
    }

    private void putDatesUsE(Empresa uE){
        Log.e("USER",TIPO_USUARIO+" ---- "+uE.getId().toString());
        txtNavName.setText(uE.getNombre());
        txtNavMail.setText(uE.getMail());
    }

    private void signout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this, Login.class));
    }

}
