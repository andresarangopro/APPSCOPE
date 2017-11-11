package com.example.felipearango.appscope.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.Util.CircleTransform;
import com.example.felipearango.appscope.models.Empresa;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.felipearango.appscope.activities.Activity_Login.TIPO_USUARIO;

public class Activity_Settings extends MainActivity implements View.OnClickListener {


    private ImageView iVSettingsPerfil;
    private StorageReference mStorage;
    private Object obj;
    private Button btnSendImg;
    private Uri descargarFoto = null;
    private EditText txtNameCP,txtOcupacionCP,txtEdadCP,txtFraseCP,txtUniversidadCP,txtCelularCP,txtUbicacionCP;
    private ArrayList<EditText> field = new ArrayList<>();
    protected static final int GALLERY_INTENT= 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_settings, null, false);
        mDrawer.addView(contentView, 0);
        firebaseAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        initComponents();
        initializedDR();
        putDataUser();

        chooseDate();
    }
    private void initComponents(){
        iVSettingsPerfil = (ImageView) findViewById(R.id.iVSettingsPerfil);
        btnSendImg = (Button) findViewById(R.id.btnSendImg);

        btnSendImg.setOnClickListener(this);
        iVSettingsPerfil.setOnClickListener(this);

        txtNameCP = (EditText) findViewById(R.id.txtNameCP);
        txtFraseCP = (EditText) findViewById(R.id.txtFraseCP);
        txtOcupacionCP = (EditText) findViewById(R.id.txtOcupacionCP);
        txtEdadCP = (EditText) findViewById(R.id.txtEdadCP);
        txtUniversidadCP = (EditText) findViewById(R.id.txtUniversidadCP);
        txtCelularCP = (EditText) findViewById(R.id.txtCelularCP);
        txtUbicacionCP = (EditText) findViewById(R.id.txtUbicacionCP);

        field.add(txtNameCP);
        field.add(txtEdadCP);
        field.add(txtCelularCP);
    }

    @Override
    public void onClick(View v) {
        if(v == iVSettingsPerfil){
            Intent intentGallery = new Intent(Intent.ACTION_PICK);
            intentGallery.setType("image/*");
            startActivityForResult(intentGallery,GALLERY_INTENT);
        }else if(v == btnSendImg){
           if(descargarFoto != null){
               if(TIPO_USUARIO == 0){
                   updateFoto("CorrientsUsers",obj,descargarFoto+"");
               }else{
                   updateFoto("EmpresaUsers",obj,descargarFoto+"");
               }
           }
        }
    }

    protected void putDataUser(){
        if(TIPO_USUARIO == 0){
            eventPD("CorrientsUsers");
        }else{
            eventPD("EmpresaUsers");
        }
    }

    private void chooseDate(){

        final Calendar calendar = Calendar.getInstance();
        final int yy = calendar.get(Calendar.YEAR);
        final int mm = calendar.get(Calendar.MONTH);
        final int dd = calendar.get(Calendar.DAY_OF_MONTH);
        txtEdadCP.setText(dd+"/"+mm+"/"+yy);
        txtEdadCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(Activity_Settings.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        actualizarFecha(selectedday+"/"+selectedmonth+"/"+selectedyear);

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
        databaseReference.child(usChildString).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //listUsers.clear();
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

                // putImage(userIn);
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

    private void putImg(Object obj){
        if(obj instanceof UsuarioCorriente){
            if(!(((UsuarioCorriente)obj).getFoto().equals(""))){
                Picasso.with(Activity_Settings.this)
                        .load(((UsuarioCorriente)obj).getFoto())
                        .transform(new CircleTransform())
                        .into(iVSettingsPerfil);
            }
        }else{
            if(!((Empresa)obj).getFoto().equals("")){
                Log.i("IMGSF",((Empresa)obj).getFoto());
                Picasso.with(Activity_Settings.this)
                        .load(((Empresa)obj).getFoto())
                        .transform(new CircleTransform())
                        .into(iVSettingsPerfil);
            }
        }
    }

    private void putTxt(Object obj){
        if(obj instanceof UsuarioCorriente){
          UsuarioCorriente user =  ((UsuarioCorriente)obj);
            txtNameCP.setText(user.getNombre());
            //txtOcupacionCP.setText("");
            txtEdadCP.setText(user.getFechaNacimiento());
            txtFraseCP.setText(user.getFrase());
            txtUniversidadCP.setText(user.getUniversidad());
            txtCelularCP.setText(user.getCelular());
            txtUbicacionCP.setText("GPS");
        }else{
            Empresa user =  ((Empresa)obj);
            txtNameCP.setText(user.getNombre());
            //txtOcupacionCP.setText("");
            txtEdadCP.setText(user.getRazonSocial());
            txtFraseCP.setText(user.getUrlEmpresa());
            txtUbicacionCP.setText("GPS");
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
     *Metodo que convierte un uri
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
}
