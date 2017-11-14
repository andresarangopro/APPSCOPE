package com.example.felipearango.appscope.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private EditText titulo, detalles, txtEtiquetaRU,txtEtiquetaRU1,txtEtiquetaRU2,txtEtiquetaRU3;
    private Button btnAddLabelR1, btnAddLabelR2, btnAddLabelR3;
    private TextView lblDisponibilidad,lblDateBorn;
    private DatabaseReference databaseReference;
    private Button btnAddLabelR;
    private RadioGroup rGDisponibilidadViaje;
    private Spinner spOcupation;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private int day,monthS,yearS;
    private ArrayList<EditText> field = new ArrayList<>();
    private ArrayList<EditText> dataEtiquetas = new ArrayList<>();
    private ArrayList<Button> dataButtons = new ArrayList<>();
    private LinearLayout lLayoutEtiquetas;
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

        txtEtiquetaRU1 = (EditText) findViewById(R.id.txtEtiquetasRU1);
        btnAddLabelR1 = (Button) findViewById(R.id.btnAddLabelR1);
        txtEtiquetaRU2 = (EditText) findViewById(R.id.txtEtiquetasRU2);
        btnAddLabelR2 = (Button) findViewById(R.id.btnAddLabelR2);
        txtEtiquetaRU3 = (EditText) findViewById(R.id.txtEtiquetasRU3);
        btnAddLabelR3 = (Button) findViewById(R.id.btnAddLabelR3);
        btnAddLabelR.setOnClickListener(this);
        btnAddLabelR1.setOnClickListener(this);
        btnAddLabelR2.setOnClickListener(this);
        btnAddLabelR3.setOnClickListener(this);
        dataEtiquetas.add(txtEtiquetaRU1);
        dataEtiquetas.add(txtEtiquetaRU2);
        dataEtiquetas.add(txtEtiquetaRU3);
        setInvisible();


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

        switch (v.getId()){
            case R.id.btnAddLabelR1:{
                txtEtiquetaRU1.setText("");
                txtEtiquetaRU1.setVisibility(View.GONE);
                break;
            }
            case R.id.btnAddLabelR2:{
                txtEtiquetaRU2.setText("");
                txtEtiquetaRU2.setVisibility(View.GONE);
                break;
            }
            case R.id.btnAddLabelR3:{
                txtEtiquetaRU3.setText("");
                txtEtiquetaRU3.setVisibility(View.GONE);
                break;
            }
        }

    }

    private void setInvisible(){
        txtEtiquetaRU1.setVisibility(View.GONE);
        txtEtiquetaRU2.setVisibility(View.GONE);
        txtEtiquetaRU3.setVisibility(View.GONE);
        btnAddLabelR1.setVisibility(View.GONE);
        btnAddLabelR2.setVisibility(View.GONE);
        btnAddLabelR3.setVisibility(View.GONE);
    }

    private void addToEtiquetas(String lbl){
        for (EditText et: dataEtiquetas) {
            if(et.getVisibility() == View.GONE){
                et.setText(lbl);
                et.setVisibility(View.VISIBLE);
                break;
            }
        }
    }
    /*
    private void addToEtiquetas(String lbl){
        LinearLayout llrow = new LinearLayout(this);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0, 1);
        llrow.setWeightSum(1f);
        llrow.setOrientation(LinearLayout.HORIZONTAL);
        llrow.setLayoutParams(llParams);

        EditText nET = new EditText(this);
        nET.setEnabled(false);
        nET.setText(lbl);
        nET.setLayoutParams(new TableLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT, 1f));

        llrow.addView(nET);
        dataEtiquetas.add(nET);
        Button btn = new Button(this);
        btn.setOnClickListener(this);
        btn.setBackgroundColor(Color.WHITE);
        Drawable img = this.getResources().getDrawable( R.mipmap.ic_minus);

        img.setBounds( 0, 0, 120, 120 );

        btn.setCompoundDrawables(null, null, img, null );


        llrow.addView(btn);
        dataButtons.add(btn);
        lLayoutEtiquetas.setWeightSum(lLayoutEtiquetas.getWeightSum()+1);
        lLayoutEtiquetas.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,
                lLayoutEtiquetas.getWeightSum()+1));
        lLayoutEtiquetas.addView(llrow);
    }*/



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

    private String arrayEditTxtToStr(ArrayList<EditText> etiquetas){
        String etiq = "";
        for (int i = 0; i <etiquetas.size() ; i++) {
            if(i+1 >= etiquetas.size()){
                etiq += etiquetas.get(i).getText().toString();
            }else{
                 etiq += etiquetas.get(i).getText().toString()+",";
            }
        }
        Toast.makeText(this,etiq,Toast.LENGTH_LONG).show();
        return etiq;
    }

}
