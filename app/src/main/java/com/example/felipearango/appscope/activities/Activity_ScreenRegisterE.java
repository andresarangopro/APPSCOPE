package com.example.felipearango.appscope.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.Util;
import com.example.felipearango.appscope.models.Empresa;
import com.example.felipearango.appscope.models.RecyclerAddRemoveAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static com.example.felipearango.appscope.Util.Util.cuenta_espera_certif;
import static com.example.felipearango.appscope.Util.Util.usuario_empresa;
import static com.example.felipearango.appscope.activities.Activity_ScreenRegisterUC.ESTADO_ACTIVA;

public class Activity_ScreenRegisterE extends AppCompatActivity implements View.OnClickListener {

    private EditText txtnameE,txtRazonSoc, txtNIT, txtUrl, txtSocial1;
    private Button btnRegisterE, btnAgregarNIT, btnAgregarRedSocial1, btnInfo;
    private EditText txtEtiquetaRU;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ArrayList<String> dataEtiquetasRedes = new ArrayList<>();
    private RecyclerView rvEtiquetasRedes;
    private RecyclerAddRemoveAdapter redesAdapter;
    private LinearLayoutManager mLinearLayoutManagerRedes;
    private StorageReference mStorage;
    private Uri descargarPDF = null;
    private Uri uri = null;
    private  final static int PICK_PDF_CODE = 2342;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_register_e);
        instanceXml();
        firebaseAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        initializedDR();
    }

    ////////////////////////////////
    //Metodos
    ////////////////////////////////

    private void initializedDR() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void instanceXml(){
        txtnameE = (EditText) findViewById(R.id.txtNameE);
        txtRazonSoc = (EditText) findViewById(R.id.txtRazonSoc);
        txtNIT = (EditText) findViewById(R.id.txtNIT);
        txtUrl = (EditText) findViewById(R.id.txtUrl);

        btnInfo = (Button) findViewById(R.id.btnInfo);
       btnInfo.setVisibility(View.INVISIBLE);
        //linearLayout = (LinearLayout) findViewById(R.id.llLayout);

        btnRegisterE = (Button) findViewById(R.id.btnRegisterE);
        btnRegisterE.setOnClickListener(this);

        btnAgregarRedSocial1 = (Button) findViewById(R.id.btnAddLabelR);
        btnAgregarRedSocial1.setOnClickListener(this);


        btnAgregarNIT = (Button) findViewById(R.id.btnAgregarNit);
        btnAgregarNIT.setOnClickListener(this);


        txtEtiquetaRU = (EditText) findViewById(R.id.txtEtiquetasRU);
        txtEtiquetaRU.setHint("Redes sociales");

        mLinearLayoutManagerRedes = new LinearLayoutManager(this);
        rvEtiquetasRedes = (RecyclerView)findViewById(R.id.rv_add);
        rvEtiquetasRedes.setLayoutManager(mLinearLayoutManagerRedes);
        redesAdapter = new RecyclerAddRemoveAdapter(this, dataEtiquetasRedes);
        rvEtiquetasRedes.setAdapter(redesAdapter);
    }



    private void takeDates(){
        String id = "";
        String nombre = getTxtEdit(txtnameE);
        String razonSocial = getTxtEdit(txtRazonSoc);
        String urlEmpresa = getTxtEdit(txtUrl);
        String nitEmpresa = getTxtEdit(txtNIT);
        String mail="";
        String foto = "";
        String redesSociales = Util.arrayToString(dataEtiquetasRedes);
        float rating = 0;
        FirebaseUser user = firebaseAuth.getCurrentUser();
        id = user.getUid();
        mail = user.getEmail();
        Empresa uE = new Empresa(id, nombre, razonSocial, urlEmpresa,nitEmpresa,
                mail,ESTADO_ACTIVA,foto,rating,usuario_empresa, cuenta_espera_certif,redesSociales);
        if(uE != null){
            insertarUsEFireBase(uE,user);
        }
    }

    private boolean comprobarcampos(){
        return !Util.emptyCampMSG(txtnameE,"Ingrese nombre de la empresa") &&
                !Util.emptyCampMSG(txtRazonSoc,"Ingrese razón de la empresa");
    }

    private void insertarUsEFireBase(Empresa uE,FirebaseUser user){
        databaseReference.child("CorrientsUsers").child(user.getUid()).setValue(uE);
        finish();
        startActivity(new Intent(getApplicationContext(),Activity_Perfil.class));
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAgregarNit:{
                getPDF();
                break;
            }
            case R.id.btnRegisterE: {
                if(comprobarcampos()){
                   if(uri != null){
                       addNit(uri);
                   }
                    takeDates();
                }

                break;
            }
            case R.id.btnAddLabelR: {
                if(!txtEtiquetaRU.getText().toString().equals("")){
                    addToEtiquetas(getTxtEdit(txtEtiquetaRU));
                    txtEtiquetaRU.setText("");
                } else{
                    txtEtiquetaRU.setError("Campo vacío");
                }
                break;
            }

        }
    }

    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }
        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uri = data.getData();
                txtNIT.setText(uri.getPath());
                txtNIT.setEnabled(false);
            }else{
                Toast.makeText(this, "No se escohio ningún archivo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addNit(Uri path){
        StorageReference filePath = mStorage.child("archivos").child(path.getLastPathSegment());
        filePath.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                descargarPDF = taskSnapshot.getDownloadUrl();

            }
        });
    }

    private void addToEtiquetas(String lbl){
        int position = 0;
        dataEtiquetasRedes.add(position,lbl);
        redesAdapter.notifyItemInserted(position);
        redesAdapter.notifyDataSetChanged();
        rvEtiquetasRedes.scrollToPosition(position);
        Toast.makeText(this, "Etiqueta Agregada", Toast.LENGTH_SHORT).show();

    }






    /**
     *
     * @param txt
     * @return
     */
    private String getTxtEdit(EditText txt){
        return txt.getText().toString();
    }
}
