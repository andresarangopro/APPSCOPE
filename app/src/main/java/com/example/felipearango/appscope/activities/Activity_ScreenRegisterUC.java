package com.example.felipearango.appscope.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.felipearango.appscope.R;
import com.example.felipearango.appscope.models.RecyclerAddRemoveAdapter;
import com.example.felipearango.appscope.models.UsuarioCorriente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Activity_ScreenRegisterUC extends AppCompatActivity implements View.OnClickListener{

    ///////////////////////////////
    //Variables
    //////////////////////////////

    private EditText txtName,txtSNombre, txtPrimerA, txtSegundoA,txtUniversidad,
            txtPass, txtPhone;
    private EditText txtEtiquetaRU;

    private TextView lblDisponibilidad,lblDateBorn;
    private DatabaseReference databaseReference;
    private Button btnAddLabelR;
    private RadioGroup rGDisponibilidadViaje;
    private Spinner spOcupation;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private ArrayList<EditText> field = new ArrayList<>();
    private ArrayList<String> dataEtiquetas = new ArrayList<>();

    private LinearLayout lLayoutEtiquetas;
    private RecyclerView rvEtiquetas;
    private RecyclerAddRemoveAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    public static final String ESTADO_ACTIVA = "ACTIVA";

    ////////////////////////////////
    //Oncreate
    ////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_register);
        instanceXml();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        initializedDR();
        chooseDate();
    }



    ////////////////////////////////
    //Metodos
    ////////////////////////////////

    private void initializedDR() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    /**
     *
     */
    private void instanceXml(){
        txtName = (EditText) findViewById(R.id.txtName);
        txtSNombre = (EditText) findViewById(R.id.txtSNombre);
        txtPrimerA = (EditText) findViewById(R.id.txtPrimerA);
        txtSegundoA = (EditText) findViewById(R.id.txtSegundoA);
        txtUniversidad = (EditText) findViewById(R.id.txtUniversidad);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtEtiquetaRU = (EditText) findViewById(R.id.txtEtiquetasRU);
        lblDisponibilidad = (TextView) findViewById(R.id.lblDisponibilidad);
        lblDateBorn = (TextView) findViewById(R.id.lblDateBorn);


        rGDisponibilidadViaje = (RadioGroup) findViewById(R.id.rGDisponibilidadViaje);

        spOcupation = (Spinner) findViewById(R.id.spOcupacion);

        btnRegister = (Button) findViewById(R.id.btnRegistrar);
        btnAddLabelR = (Button) findViewById(R.id.btnAddLabelR);

        btnAddLabelR.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        lLayoutEtiquetas = (LinearLayout) findViewById(R.id.lLayoutEtiquetas);


        field.add(txtName);
        field.add(txtPrimerA);
        field.add(txtPhone);


        mLinearLayoutManager = new LinearLayoutManager(this);
        rvEtiquetas = (RecyclerView)findViewById(R.id.rv_add);
        rvEtiquetas.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecyclerAddRemoveAdapter(this, dataEtiquetas);
        rvEtiquetas.setAdapter(mAdapter);

    }

    private boolean checkEditText() {
        boolean valido = true;
        for (EditText et : field) {
            if (et.getText().toString().equals("")) {
                et.setError("Ingrese este campo por favor");
                valido = false;
            }
        }
        return valido;
    }
    /**
     *
     */
    @Override
    public void onClick(View v) {
        if(v == btnRegister){
            if(checkEditText()){
                if(dataEtiquetas.size() > 2){
                    updateDatesUser();
                }else{
                    txtEtiquetaRU.setError("Ingrese mínimo tres etiquetas!");
                }

            }
        }else if(v == btnAddLabelR){
            if(!txtEtiquetaRU.getText().toString().equals("")){
                addToEtiquetas(getTxtEdit(txtEtiquetaRU));
                txtEtiquetaRU.setText("");
            } else{
                txtEtiquetaRU.setError("Campo vacío");
            }
        }
    }


    private void addToEtiquetas(String lbl){
        int position = 0;

        dataEtiquetas.add(position,lbl);
        mAdapter.notifyItemInserted(position);
        mAdapter.notifyDataSetChanged();
        rvEtiquetas.scrollToPosition(position);
        Toast.makeText(this, "Etiqueta Agregada", Toast.LENGTH_SHORT).show();

    }



    private void chooseDate(){
        final Calendar calendar = Calendar.getInstance();
        final int yy = calendar.get(Calendar.YEAR);
        final int mm = calendar.get(Calendar.MONTH);
        final int dd = calendar.get(Calendar.DAY_OF_MONTH);
        lblDateBorn.setText(dd+"/"+mm+"/"+yy);
        lblDateBorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(Activity_ScreenRegisterUC.this, new DatePickerDialog.OnDateSetListener() {
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
        lblDateBorn.setText(fecha);
    }

    private boolean comprobarCampos(){
        String name =getTxtEdit(txtName);
        String apellido = getTxtEdit(txtPrimerA);
        String dateBorn = lblDateBorn.getText().toString();
        String universidad = getTxtEdit(txtUniversidad);
        String celular = getTxtEdit(txtPhone);

        return !campEmpty(name) && !campEmpty(apellido) && !campEmpty(dateBorn) && !campEmpty(universidad)
                    && !campEmpty(celular);
    }

    public void updateDatesUser(){
        String id = "";
        String name =getTxtEdit(txtName);
        String sName = getTxtEdit(txtSNombre);
        String apellido = getTxtEdit(txtPrimerA);
        String sApellido = getTxtEdit(txtSegundoA);
        String ocupacion = "";
        String dateBorn = lblDateBorn.getText().toString();
        String universidad = getTxtEdit(txtUniversidad);
        String celular = getTxtEdit(txtPhone);
        String foto = "";
        String frase = "";
        String hobbies = "";
        String conocimientosInf = "";
        int disponibilidadV = 0;
        String anexos = "";
        String idiomas = "";
        String expProfesionaless ="";
        String refEmpleo ="";
        String formacion = "";
        String mail = " ";
        String etiquetas= arrayEditTxtToStr(dataEtiquetas);
        float rating = 0;
        FirebaseUser user = firebaseAuth.getCurrentUser();
        id = user.getUid();
        mail = user.getEmail();
        UsuarioCorriente uC = new UsuarioCorriente(id,name+" "+sName,apellido+" "+sApellido,ocupacion,
                dateBorn,universidad,celular,mail,foto,frase,hobbies,conocimientosInf,ESTADO_ACTIVA,
                rating,anexos,idiomas,expProfesionaless,refEmpleo,formacion, etiquetas);

        if(uC != null){
            insertarUsCFireBase(uC,user);
        }
    }

    /**
     *
     * @param uC
     */
    private void insertarUsCFireBase(UsuarioCorriente uC,FirebaseUser user){
        Log.e("INFO", "NO...");
        databaseReference.child("CorrientsUsers").child(user.getUid()).setValue(uC);
        Log.e("INFO", "SI...");
        finish();
        startActivity(new Intent(getApplicationContext(),Activity_Perfil.class));
    }

    private boolean campEmpty(String campo){
        return TextUtils.isEmpty(campo);
    }

    private String getTxtEdit(EditText txt){
        return txt.getText().toString();
    }

    private String arrayEditTxtToStr(ArrayList<String> etiquetas){
        String etiq = "";
        for (int i = 0; i <etiquetas.size() ; i++) {
            if(i+1 >= etiquetas.size()){
                etiq += etiquetas.get(i);
            }else{
                 etiq += etiquetas.get(i)+",";
            }
        }
        Toast.makeText(this,etiq,Toast.LENGTH_LONG).show();
        return etiq;
    }

}
