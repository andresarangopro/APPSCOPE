package com.example.felipearango.appscope.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.CircleTransform;
import com.example.felipearango.appscope.Util.Util;
import com.example.felipearango.appscope.models.Empresa;
import com.example.felipearango.appscope.models.RecyclerAdapterInfo;
import com.example.felipearango.appscope.models.RecyclerAddRemoveAdapter;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.felipearango.appscope.Util.Util.usuario_corriente;
import static com.example.felipearango.appscope.Util.Util.usuario_empresa;
import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;
import static com.example.felipearango.appscope.activities.Activity_Login.calledAlready;


public class Activity_Settings extends MainActivity implements View.OnClickListener {


    private static final int PICK_PDF_CODE = 2342;
    private ImageView iVSettingsPerfil;
    private StorageReference mStorage;
    private Object obj;
    private Button btnSendImg,btnAddFile, btnInfo;
    private Uri descargarFoto = null;
    private EditText txtNameCP,txtOcupacionCP,txtEdadCP,txtFraseCP,txtUniversidadCP,
            txtCelularCP, etHojaVidaCP,et_doc;
    private TextView tVChangeImgP;
    private ArrayList<EditText> field = new ArrayList<>();
    private ArrayList<String> dataEtiquetas = new ArrayList<>();
    private ArrayList<String> listEtiquetasFirebase = new ArrayList<>();

    private  FirebaseAuth.AuthStateListener listener;
    protected static final int GALLERY_INTENT= 1;
    private Uri descargarPDF = null;
    private LinearLayout lLayoutEtiquetas;
    private RecyclerView rvEtiquetas;
    private RecyclerAddRemoveAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Uri pathUriLocal = null;
    private ProgressDialog progressDialog;
    private LinearLayout ll;
    private ValueEventListener valueEventListenerSettings ;
    RecyclerView recycler;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_settings, null, false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mDrawer.addView(contentView, 0);
        firebaseAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        ll = (LinearLayout)findViewById(R.id.llsettings);
        initComponents();
        progressDialog = new ProgressDialog(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        rvEtiquetas = (RecyclerView)findViewById(R.id.rv_add);
        rvEtiquetas.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerAddRemoveAdapter(this, dataEtiquetas);
        rvEtiquetas.setAdapter(mAdapter);
        initializedDR();
        inicializatedFireBase();
        putDataUser();
        chooseDate();
        getEtiquetas();


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
    private void initComponents(){
        iVSettingsPerfil = (ImageView) findViewById(R.id.iVSettingsPerfil);
        btnSendImg = (Button) findViewById(R.id.btnSendImg);
        btnAddFile = (Button) findViewById(R.id.btnAddFile);
        btnInfo = (Button)findViewById(R.id.btnInfo);
        btnSendImg.setOnClickListener(this);
        btnAddFile.setOnClickListener(this);
        iVSettingsPerfil.setOnClickListener(this);


        txtNameCP = (EditText) findViewById(R.id.txtNameCP);
        txtFraseCP = (EditText) findViewById(R.id.txtFraseCP);
        txtOcupacionCP = (EditText) findViewById(R.id.txtOcupacionCP);
        txtEdadCP = (EditText) findViewById(R.id.txtEdadCP);
        txtUniversidadCP = (EditText) findViewById(R.id.txtUniversidadCP);
        txtCelularCP = (EditText) findViewById(R.id.txtCelularCP);
        etHojaVidaCP = (EditText) findViewById(R.id.etHojaVida);
        et_doc = (EditText)findViewById(R.id.et_doc);

       // txtUniversidadCP.setOnClickListener(this);
        tVChangeImgP = (TextView) findViewById(R.id.tVChangeImgP);
        if(TIPO_USUARIO == usuario_empresa){
            etHojaVidaCP.setVisibility(View.INVISIBLE);
            txtCelularCP.setVisibility(View.INVISIBLE);
            txtUniversidadCP.setHint("Nit");
            txtUniversidadCP.setOnClickListener(this);
            txtUniversidadCP.setKeyListener(null);
            txtOcupacionCP.setHint("Razón Social");
            txtFraseCP.setHint("Url Empresa");
            txtEdadCP.setVisibility(View.INVISIBLE);
            et_doc.setHint("Redes Sociales");
            tVChangeImgP.setText("Redes");
            btnInfo.setVisibility(View.INVISIBLE);
        }else if(TIPO_USUARIO == usuario_corriente){
            etHojaVidaCP.setHint("Hoja de vida");
            etHojaVidaCP.setKeyListener(null);
            etHojaVidaCP.setOnClickListener(this);
            tVChangeImgP.setText("Etiquetas");
            btnInfo.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnAddFile){
            if(!Util.emptyCampMSG(et_doc,"Campo vacío")){
                Util.agregarEtiquetaS(Util.parametrizacionEtiqueta(getTxtEdit(et_doc)),dataEtiquetas,mAdapter,rvEtiquetas,et_doc);
            }
        }else if(v == iVSettingsPerfil){
            Intent intentGallery = new Intent(Intent.ACTION_PICK);
            intentGallery.setType("image/*");
            startActivityForResult(intentGallery,GALLERY_INTENT);
        }else if(v == btnSendImg){
            if(TIPO_USUARIO == usuario_corriente){
                if(!Util.emptyCampMSG(txtNameCP,getString(R.string.empty_camp)) &&
                        !Util.emptyCampMSG(txtEdadCP,getString(R.string.empty_camp)) &&
                        !Util.emptyCampMSG(txtCelularCP,getString(R.string.empty_camp)) ){

                        if(dataEtiquetas.size() >= 3){
                            addNit(pathUriLocal);
                        }else{
                            et_doc.setError("Ingrese almenos tres etiquetas");
                        }
                }
            }else{
                if(!Util.emptyCampMSG(txtNameCP,getString(R.string.empty_camp)) ){
                    addNit(pathUriLocal);



                }


            }
        }else if(v.getId() == R.id.txtUniversidadCP){
            getPDF();
        }else if(v.getId() == R.id.etHojaVida){
            getPDF();
        }else if(v.getId() == R.id.btnInfo){
            showPopUp();
        }


    }



    protected void putDataUser(){
            eventPD("CorrientsUsers");
    }

    private void chooseDate(){

       // txtEdadCP.setText(dtStart);
        txtEdadCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth =mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(Activity_Settings.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        actualizarFecha(selectedday+"/"+(selectedmonth+1)+"/"+selectedyear);

                    }
                },mYear, mMonth, mDay);

                mDatePicker.setTitle("Seleccione su fecha de nacimiento");
                mDatePicker.show();
            }
        });
    }

    private void actualizarFecha(String fecha){
        txtEdadCP.setText(fecha);
    }



    public void eventPD(String usChildString){
        databaseReference.child(usChildString).addListenerForSingleValueEvent(valueEventListenerSettings = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(TIPO_USUARIO == 0){
                    UsuarioCorriente uC =  dataSnapshot.child(user.getUid()).getValue(UsuarioCorriente.class);
                    obj = uC;
                }else{
                    Empresa uE =  dataSnapshot.child(user.getUid()).getValue(Empresa.class);
                    obj = uE;
                }
                putImg(obj);
                putTxt(obj);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getEtiquetas(){
        databaseReference.child("Etiqueta").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //listUsers.clear();
                if(listEtiquetasFirebase.size() >= 1){
                    listEtiquetasFirebase.clear();
                }
                for (DataSnapshot etiqueta:dataSnapshot.getChildren()) {
                    String eti = etiqueta.getKey();
                    listEtiquetasFirebase.add(eti);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
           final Uri uri = data.getData();
            StorageReference filePath = mStorage.child("fotos").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    descargarFoto = taskSnapshot.getDownloadUrl();
                    setImageInImV(uri);
                   // putImg(obj);
                }
            });
        }

        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                pathUriLocal = data.getData();

                if(TIPO_USUARIO == usuario_empresa){
                    txtUniversidadCP.setText(pathUriLocal.getPath());
                }else {
                    etHojaVidaCP.setText(pathUriLocal.getPath());
                }
                // txtNIT.setEnabled(false);
            }else{
                Toast.makeText(this, "No se escohio ningún archivo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateFoto(String kindUser,Object obja,String img){
        if(obja instanceof UsuarioCorriente){
            UsuarioCorriente uC =((UsuarioCorriente)obja);
            databaseReference.child(kindUser).child(uC.getId()).child("foto").setValue(img);

        }else{
            Empresa uE =((Empresa)obja);
            databaseReference.child(kindUser).child(uE.getId()).child("foto").setValue(img);

        }

    }

    private void updatePDF(String kindUser,Object obja,String nitOrPdf,String pdf){
        if(obja instanceof UsuarioCorriente){
            UsuarioCorriente uC =((UsuarioCorriente)obja);
                databaseReference.child(kindUser).child(uC.getId()).child(nitOrPdf).setValue(pdf).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if( descargarFoto != null){
                            updateFoto("CorrientsUsers",obj,descargarFoto+"");
                        }
                        updateData("CorrientsUsers",obj);

                    }
                });

        }else {
            Empresa uE = ((Empresa) obja);
            databaseReference.child(kindUser).child(uE.getId()).child(nitOrPdf).setValue(pdf).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(descargarFoto != null){
                        updateFoto("CorrientsUsers",obj,descargarFoto+"");
                    }

                    updateData("CorrientsUsers",obj);
                }
            });
        }

    }


    private void updateData(String kindUser,Object obj){
        if(obj instanceof UsuarioCorriente){
            UsuarioCorriente uC =((UsuarioCorriente)obj);
            databaseReference.child(kindUser).child(uC.getId()).child("nombre").setValue(Util.getTxt(txtNameCP));
            databaseReference.child(kindUser).child(uC.getId()).child("frase").setValue(Util.getTxt(txtFraseCP));
            databaseReference.child(kindUser).child(uC.getId()).child("ocupacion").setValue(Util.getTxt(txtOcupacionCP));
            databaseReference.child(kindUser).child(uC.getId()).child("universidad").setValue(Util.getTxt(txtUniversidadCP));
            databaseReference.child(kindUser).child(uC.getId()).child("celular").setValue(Util.getTxt(txtCelularCP));
            databaseReference.child(kindUser).child(uC.getId()).child("etiquetas").setValue(dataEtiquetas);
            databaseReference.child(kindUser).child(uC.getId()).child("fechaNacimiento").setValue(Util.getTxt(txtEdadCP));


        }else{
            Empresa uE =((Empresa)obj);
            databaseReference.child(kindUser).child(uE.getId()).child("nombre").setValue(Util.getTxt(txtNameCP));
            databaseReference.child(kindUser).child(uE.getId()).child("razonSocial").setValue(Util.getTxt(txtOcupacionCP));
            databaseReference.child(kindUser).child(uE.getId()).child("urlEmpresa").setValue(Util.getTxt(txtFraseCP));
            databaseReference.child(kindUser).child(uE.getId()).child("redesSociales").setValue(dataEtiquetas);

        }
        startActivity(new Intent(Activity_Settings.this,Activity_Perfil.class));
        finish();

    }

    private ArrayList<String> listEDToString(ArrayList<EditText> listEd){
        ArrayList<String> listString = new ArrayList<>();
        for (EditText ed: listEd ) {
            listString.add(getTxtEdit(ed));
        }
        return listString;
    }

    private void putImg(Object obj){
        if(obj instanceof UsuarioCorriente){
            if(!(((UsuarioCorriente)obj).getFoto().equals(""))){
                Glide.with(Activity_Settings.this)
                        .load(((UsuarioCorriente)obj).getFoto())
                        .apply(RequestOptions
                                .circleCropTransform()).into(iVSettingsPerfil);
                /*Picasso.with(Activity_Settings.this)
                        .load(((UsuarioCorriente)obj).getFoto())
                        .transform(new CircleTransform())
                        .into(iVSettingsPerfil);*/
            }
        }else{
            if(!((Empresa)obj).getFoto().equals("")){
                Log.i("IMGSF",((Empresa)obj).getFoto());
                Glide.with(Activity_Settings.this)
                        .load(((Empresa)obj).getFoto())
                        .apply(RequestOptions
                                .circleCropTransform()).into(iVSettingsPerfil);
              /*  Picasso.with(Activity_Settings.this)
                        .load(((Empresa)obj).getFoto())
                        .transform(new CircleTransform())
                        .into(iVSettingsPerfil);*/
            }
        }
    }

    private void putTxt(Object obj){
        if(obj instanceof UsuarioCorriente){
          UsuarioCorriente user =  ((UsuarioCorriente)obj);
            txtNameCP.setText(user.getNombre());
            txtOcupacionCP.setText(user.getOcupacion());
            txtEdadCP.setText(user.getFechaNacimiento());
            txtFraseCP.setText(user.getFrase());
            txtUniversidadCP.setText(user.getUniversidad());
            txtCelularCP.setText(user.getCelular());
            if(!user.getAnexos().equals("")){
                etHojaVidaCP.setText("Hoja de vida anexada");
            }

            for (String etiquetas: user.getEtiquetas()) {
                Util.agregarEtiquetaS(etiquetas,dataEtiquetas,mAdapter,rvEtiquetas,et_doc);
            }
        }else{
            Empresa user =  ((Empresa)obj);
            txtNameCP.setText(user.getNombre());
            txtOcupacionCP.setText(user.getRazonSocial());
            txtFraseCP.setText(user.getUrlEmpresa());
            if(!user.getNit().equals("")){
                txtUniversidadCP.setText("Nit anexado");
            }
            if(user.getRedesSociales().size() >= 1 && !user.getRedesSociales().get(0).equals("")){
                for (String redSocial:user.getRedesSociales()) {
                    Util.agregarEtiquetaS(redSocial,dataEtiquetas,mAdapter,rvEtiquetas,et_doc);
                }
            }
        }
    }

    private void setImageInImV(Uri uri){
        Bitmap bit;
        CircleTransform ct = new CircleTransform() ;
        try {
            bit = ct.transform(getBitmapFromUri(uri));
            iVSettingsPerfil.setImageBitmap(bit);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initializedDR() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /********************************
     *Metodo que convierte un pathUriLocal
     * en bitmp
     * @param uri
     * @return
     * @throws IOException
     ******************************/
    public Bitmap getBitmapFromUri(Uri uri) throws IOException{
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri,"r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return bitmap;
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

    public void addNit(Uri path){
        if(path != null){
            if(TIPO_USUARIO == usuario_corriente){
                progressDialog.setMessage("Actualizando hoja de vida, por favor espera");
            }else if(TIPO_USUARIO == usuario_empresa){
                progressDialog.setMessage("Actualizando nit, por favor espera");
            }
            progressDialog.show();

            StorageReference filePath = mStorage.child("archivos").child(path.getLastPathSegment());
            filePath.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    descargarPDF = taskSnapshot.getDownloadUrl();
                    if (TIPO_USUARIO == usuario_corriente){
                        updatePDF("CorrientsUsers",obj,"anexos",descargarPDF+"");
                    }else if(TIPO_USUARIO == usuario_empresa){
                        updatePDF("CorrientsUsers",obj,"nit",descargarPDF+"");
                    }

                }
            });
        }else{
            if( descargarFoto != null){
                updateFoto("CorrientsUsers",obj,descargarFoto+"");
            }
            updateData("CorrientsUsers",obj);

        }


    }

    public void showPopUp() {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_info, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // Obtener el Recycler
        recycler = (RecyclerView) popupView.findViewById(R.id.rv_Info);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new RecyclerAdapterInfo(this,listEtiquetasFirebase,dataEtiquetas,mAdapter,rvEtiquetas,et_doc);
        recycler.setAdapter(adapter);


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
    }



}
